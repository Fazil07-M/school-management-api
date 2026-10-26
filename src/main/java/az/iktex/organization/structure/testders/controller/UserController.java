package az.iktex.organization.structure.testders.controller;

import az.iktex.organization.structure.testders.dto.request.UserRequest;
import az.iktex.organization.structure.testders.dto.request.PassportRequest;
import az.iktex.organization.structure.testders.dto.response.UserResponse;
import az.iktex.organization.structure.testders.dto.response.PassportResponse;
import az.iktex.organization.structure.testders.entity.Gender;
import az.iktex.organization.structure.testders.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Yeni istifadəçi yaradır.
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody UserRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }

    // ID üzrə istifadəçini tapır.
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Email üzrə istifadəçini tapır.
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(
            @PathVariable String email) {

        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // Bütün istifadəçiləri qaytarır.
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Gender üzrə istifadəçiləri filtrləyir.
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<UserResponse>> getUsersByGender(
            @PathVariable Gender gender) {

        return ResponseEntity.ok(userService.getUsersByGender(gender));
    }

    // Pasportu olmayan istifadəçiləri qaytarır.
    @GetMapping("/without-passport")
    public ResponseEntity<List<UserResponse>> getUsersWithoutPassport() {

        return ResponseEntity.ok(userService.getUsersWithoutPassport());
    }

    // İstifadəçi məlumatlarını yeniləyir.
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest request) {

        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    // İstifadəçini silir.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    // İstifadəçiyə pasport əlavə edir.
    @PostMapping("/{userId}/passport")
    public ResponseEntity<PassportResponse> addPassport(
            @PathVariable Long userId,
            @RequestBody PassportRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.addPassport(userId, request));
    }

    // İstifadəçinin pasportunu qaytarır.
    @GetMapping("/{userId}/passport")
    public ResponseEntity<PassportResponse> getPassport(
            @PathVariable Long userId) {

        return ResponseEntity.ok(userService.getPassport(userId));
    }

    // İstifadəçinin pasportunu silir.
    @DeleteMapping("/{userId}/passport")
    public ResponseEntity<Void> deletePassport(
            @PathVariable Long userId) {

        userService.deletePassport(userId);

        return ResponseEntity.noContent().build();
    }
}