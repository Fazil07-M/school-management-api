package az.iktex.organization.structure.testders.controller;

import az.iktex.organization.structure.testders.service.EnrollmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tx-demo")
@RequiredArgsConstructor
public class TransactionDemoController {

    private final EnrollmentService enrollmentService;

    // Tranzaksiya olmadan MANDATORY metodunu çağırır.
    @PostMapping("/reserve-seat/{courseId}")
    public ResponseEntity<String> reserveSeat(
            @PathVariable Long courseId) {

        enrollmentService.reserveSeat(courseId);

        return ResponseEntity.ok("Seat reserved successfully");
    }
}