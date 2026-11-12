
package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.dto.response.StudentResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.dao.PessimisticLockingFailureException;
@Service
@RequiredArgsConstructor
public class EnrollmentRetryService {

    private final StudentService studentService;
    private final PlatformTransactionManager transactionManager;

    // SERIALIZABLE tranzaksiyasını maksimum 3 dəfə təkrar edir.
    public StudentResponse enrollWithRetry(
            Long studentId,
            Long courseId,
            long delayMs) {

        TransactionTemplate transactionTemplate =
                new TransactionTemplate(transactionManager);

        transactionTemplate.setIsolationLevel(
                TransactionDefinition.ISOLATION_SERIALIZABLE);

        transactionTemplate.setPropagationBehavior(
                TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        for (int attempt = 1; attempt <= 3; attempt++) {

            try {
                System.out.println("RETRY ATTEMPT: " + attempt);

                return transactionTemplate.execute(status ->
                        studentService.enrollWithSerializable(
                                studentId, courseId, delayMs));

            } catch (PessimisticLockingFailureException ex) {

                System.out.println(
                        "TRANSACTION CONFLICT: attempt " + attempt);

                if (attempt == 3) {
                    throw ex;
                }
            }
        }

        throw new IllegalStateException("Retry failed");
    }
}
