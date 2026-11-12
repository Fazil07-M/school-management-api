
#!/bin/bash

BASE_URL="http://localhost:8080/test-demo"
CONTAINER="school-api-postgres"

echo "======================================"
echo " PREPARING DATABASE"
echo "======================================"

# Test melumatlarini ilkin veziyyete qaytaririq.
docker exec "$CONTAINER" psql -U postgres -d school_db -c "
UPDATE employees
SET salary = 4800.00
WHERE id = 3;

UPDATE courses
SET capacity = 1
WHERE id = 5;

DELETE FROM student_course
WHERE course_id = 5;
"

if [ $? -ne 0 ]; then
    echo "Database preparation failed!"
    exit 1
fi

echo "Database is ready."
echo ""

# Iki HTTP sorğusunu paralel başladır.
run_pair() {
    local URL1="$1"
    local URL2="$2"

    curl -sS -w "\nHTTP %{http_code}\n" "$URL1" &
    PID1=$!

    sleep 1

    curl -sS -w "\nHTTP %{http_code}\n" "$URL2" &
    PID2=$!

    wait "$PID1"
    wait "$PID2"

    echo ""
}

echo "======================================"
echo " D1: NON-REPEATABLE READ"
echo "======================================"

echo "READ_COMMITTED"

run_pair \
"$BASE_URL/api/isolation/departments/1/salary-report?isolation=READ_COMMITTED&delayMs=8000" \
"$BASE_URL/api/employees/3"

echo "REPEATABLE_READ"

run_pair \
"$BASE_URL/api/isolation/departments/1/salary-report?isolation=REPEATABLE_READ&delayMs=8000" \
"$BASE_URL/api/employees/3"

echo "======================================"
echo " D2: LOST UPDATE"
echo "======================================"

echo "READ_COMMITTED"

run_pair \
"$BASE_URL/api/isolation/employees/3/raise?amount=100&isolation=READ_COMMITTED&delayMs=8000" \
"$BASE_URL/api/isolation/employees/3/raise?amount=100&isolation=READ_COMMITTED&delayMs=8000"

echo "REPEATABLE_READ"

run_pair \
"$BASE_URL/api/isolation/employees/3/raise?amount=100&isolation=REPEATABLE_READ&delayMs=8000" \
"$BASE_URL/api/isolation/employees/3/raise?amount=100&isolation=REPEATABLE_READ&delayMs=8000"

echo "======================================"
echo " D3: WRITE SKEW"
echo "======================================"

echo "REPEATABLE_READ"

run_pair \
"$BASE_URL/api/isolation/students/1/courses/5?isolation=REPEATABLE_READ&delayMs=8000" \
"$BASE_URL/api/isolation/students/2/courses/5?isolation=REPEATABLE_READ&delayMs=8000"

# Birinci D3 testinden sonra kursu yeniden bosaldiriq.
docker exec "$CONTAINER" psql -U postgres -d school_db -c "
DELETE FROM student_course
WHERE course_id = 5;
"

echo "SERIALIZABLE"

run_pair \
"$BASE_URL/api/isolation/students/1/courses/5?isolation=SERIALIZABLE&delayMs=8000" \
"$BASE_URL/api/isolation/students/2/courses/5?isolation=SERIALIZABLE&delayMs=8000"

echo "======================================"
echo " ALL TESTS FINISHED"
echo "======================================"
