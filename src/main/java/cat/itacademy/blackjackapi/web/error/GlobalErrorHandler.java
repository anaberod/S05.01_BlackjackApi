package cat.itacademy.blackjackapi.web.error;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Manejador global de errores para controladores WebFlux.
 * Estandariza las respuestas de error en un JSON común.
 *
 * Modelo de error: { timestamp, path, status, error, message }
 */
@Hidden
@RestControllerAdvice(basePackages = "cat.itacademy.blackjackapi.web.controller")
public class GlobalErrorHandler {

    // ===================== 400 BAD REQUEST: @Valid @RequestBody =====================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            ServerWebExchange exchange
    ) {
        final String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> "%s: %s".formatted(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        return buildError(exchange, HttpStatus.BAD_REQUEST, "Validation failed", message);
    }

    // ===================== 400 BAD REQUEST: @Validated (params/path) =====================
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            ServerWebExchange exchange
    ) {
        final String message = ex.getConstraintViolations().stream()
                .map(cv -> "%s: %s".formatted(cv.getPropertyPath(), cv.getMessage()))
                .collect(Collectors.joining("; "));

        return buildError(exchange, HttpStatus.BAD_REQUEST, "Constraint violation", message);
    }

    // ===================== 404 NOT FOUND =====================
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElement(
            NoSuchElementException ex,
            ServerWebExchange exchange
    ) {
        return buildError(exchange, HttpStatus.NOT_FOUND, "Not Found", nullSafeMessage(ex));
    }

    // ===================== RESPONSE STATUS (400/404/409/…) =====================
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatus(
            ResponseStatusException ex,
            ServerWebExchange exchange
    ) {
        final HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        final String reason = ex.getReason() != null ? ex.getReason() : status.getReasonPhrase();
        return buildError(exchange, status, status.getReasonPhrase(), reason);
    }

    // ===================== 400 BAD REQUEST: IllegalArgumentException =====================
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            ServerWebExchange exchange
    ) {
        return buildError(exchange, HttpStatus.BAD_REQUEST, "Bad Request", nullSafeMessage(ex));
    }

    // ===================== 500 INTERNAL SERVER ERROR (Fallback) =====================
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(
            Throwable ex,
            ServerWebExchange exchange
    ) {
        return buildError(exchange, HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error", nullSafeMessage(ex));
    }

    // ---------- Utilidades ----------

    private ResponseEntity<ErrorResponse> buildError(
            ServerWebExchange exchange,
            HttpStatus status,
            String error,
            String message
    ) {
        String path = exchange.getRequest().getPath().value();
        ErrorResponse body = new ErrorResponse(
                Instant.now(),
                path,
                status.value(),
                error,
                (message == null || message.isBlank()) ? status.getReasonPhrase() : message
        );
        return ResponseEntity.status(status).body(body);
    }

    private String nullSafeMessage(Throwable ex) {
        return (ex.getMessage() == null || ex.getMessage().isBlank())
                ? ex.getClass().getSimpleName()
                : ex.getMessage();
    }

    /** Modelo compacto e inmutable de respuesta de error. */
    public record ErrorResponse(
            Instant timestamp,
            String path,
            int status,
            String error,
            String message
    ) {}
}
