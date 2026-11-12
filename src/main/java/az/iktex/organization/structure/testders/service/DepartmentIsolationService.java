package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DepartmentIsolationService {

    private final DepartmentRepository departmentRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> readCommittedReport(Long departmentId, long delayMs) {
        BigDecimal firstAvg = departmentRepository
                .findAverageSalaryByDepartmentId(departmentId);

        sleep(delayMs);

        BigDecimal secondAvg = departmentRepository
                .findAverageSalaryByDepartmentId(departmentId);

        return Map.of(
                "firstAvg", firstAvg,
                "secondAvg", secondAvg,
                "isolation", "READ_COMMITTED"
        );
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Map<String, Object> repeatableReadReport(Long departmentId, long delayMs) {
        BigDecimal firstAvg = departmentRepository
                .findAverageSalaryByDepartmentId(departmentId);

        sleep(delayMs);

        BigDecimal secondAvg = departmentRepository
                .findAverageSalaryByDepartmentId(departmentId);

        return Map.of(
                "firstAvg", firstAvg,
                "secondAvg", secondAvg,     // Entity yox, aggregate sorğu istifadə olunur ki, First-Level Cache nəticəni gizlətməsin.
                "isolation", "REPEATABLE_READ"
        );
    }

    private void sleep(long delayMs) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Gözləmə zamanı xəta baş verdi", e);
        }
    }
    }