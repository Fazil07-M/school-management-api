
package az.iktex.organization.structure.testders;
import org.springframework.transaction.IllegalTransactionStateException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.CannotAcquireLockException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalState(
            IllegalStateException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(
            EntityNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", exception.getMessage()));
    }


    @ExceptionHandler(IllegalTransactionStateException.class)
    public ResponseEntity<Map<String, String>> handleTransactionState(
            IllegalTransactionStateException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error",
                        "Bu əməliyyat yalnız mövcud tranzaksiya daxilində çağırıla bilər"));
    }

        @ExceptionHandler(CannotAcquireLockException.class)
        public ResponseEntity<Map<String, String>> handleConcurrencyConflict(CannotAcquireLockException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", "Transaction conflict",
                        "message", "Əməliyyat paralel yenilənmə səbəbindən tamamlanmadı. Yenidən cəhd edin."
                ));
        }

}
