
package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.entity.Employee;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class SalaryService {

    // İşçinin maaşını faizlə artırır.
    @Transactional(propagation = Propagation.REQUIRED)
    public void raise(Employee employee, BigDecimal percent) {

        BigDecimal increase = employee.getSalary()
                .multiply(percent)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        BigDecimal newSalary = employee.getSalary().add(increase);

        if (newSalary.compareTo(new BigDecimal("10000")) > 0) {
            throw new IllegalStateException(
                    "Maaş 10000-dən çox ola bilməz");
        }

        employee.setSalary(newSalary);
    }
}
