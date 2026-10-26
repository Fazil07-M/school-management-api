package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.dto.request.UserRequest;
import az.iktex.organization.structure.testders.dto.request.PassportRequest;
import az.iktex.organization.structure.testders.dto.response.UserResponse;
import az.iktex.organization.structure.testders.dto.response.PassportResponse;
import az.iktex.organization.structure.testders.entity.User;
import az.iktex.organization.structure.testders.entity.Passport;
import az.iktex.organization.structure.testders.mapper.UserMapper;
import az.iktex.organization.structure.testders.mapper.PassportMapper;
import az.iktex.organization.structure.testders.repository.UserRepository;
import az.iktex.organization.structure.testders.repository.PassportRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PassportRepository passportRepository;
    private final UserMapper userMapper;
    private final PassportMapper passportMapper;

    // Yeni istifadəçi yaradır.
    @Transactional
    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("Bu email artıq mövcuddur");
        }

        User user = userMapper.toEntity(request);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    // ID üzrə istifadəçini və pasportunu tapır.
    public UserResponse getUserById(Long id) {

        User user = userRepository.findByIdWithPassport(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("İstifadəçi tapılmadı"));

        return userMapper.toResponse(user);
    }

    // Email üzrə istifadəçini tapır.
    public UserResponse getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new EntityNotFoundException("İstifadəçi tapılmadı"));

        return userMapper.toResponse(user);
    }

    // Bütün istifadəçiləri qaytarır.
    public List<UserResponse> getAllUsers() {

        return userMapper.toResponseList(userRepository.findAll());
    }

    // Gender üzrə istifadəçiləri filtrləyir.
    public List<UserResponse> getUsersByGender(
            az.iktex.organization.structure.testders.entity.Gender gender) {

        return userMapper.toResponseList(
                userRepository.findByGender(gender));
    }

    // Pasportu olmayan istifadəçiləri qaytarır.
    public List<UserResponse> getUsersWithoutPassport() {

        return userMapper.toResponseList(
                userRepository.findUsersWithoutPassport());
    }

    // İstifadəçi məlumatlarını yeniləyir.
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("İstifadəçi tapılmadı"));

        if (userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new IllegalStateException("Bu email artıq istifadə olunur");
        }

        userMapper.updateEntity(request, user);

        return userMapper.toResponse(user);
    }

    // İstifadəçini silir.
    @Transactional
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("İstifadəçi tapılmadı"));

        userRepository.delete(user);
    }

    // İstifadəçiyə pasport əlavə edir.
    @Transactional
    public PassportResponse addPassport(
            Long userId, PassportRequest request) {

        User user = userRepository.findByIdWithPassport(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("İstifadəçi tapılmadı"));

        if (user.getPassport() != null) {
            throw new IllegalStateException(
                    "İstifadəçinin artıq pasportu var");
        }

        if (passportRepository.existsBySerialNumber(request.serialNumber())) {
            throw new IllegalStateException(
                    "Bu pasport seriyası artıq mövcuddur");
        }

        Passport passport = passportMapper.toEntity(request);

        user.setPassport(passport);

        userRepository.save(user);

        return passportMapper.toResponse(passport);
    }

    // İstifadəçinin pasportunu qaytarır.
    public PassportResponse getPassport(Long userId) {

        Passport passport = passportRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pasport tapılmadı"));

        return passportMapper.toResponse(passport);
    }

    // İstifadəçinin pasportunu silir.
    @Transactional
    public void deletePassport(Long userId) {

        User user = userRepository.findByIdWithPassport(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("İstifadəçi tapılmadı"));

        if (user.getPassport() == null) {
            throw new EntityNotFoundException("Pasport tapılmadı");
        }

        user.setPassport(null);
    }
}