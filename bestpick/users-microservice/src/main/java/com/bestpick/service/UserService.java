package com.bestpick.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.bestpick.dto.UserRequestDto;
import com.bestpick.dto.UserResponseDto;
import com.bestpick.kafka.KafkaProducer;
import com.bestpick.kafka.events.UserEvent;
import com.bestpick.model.User;
import com.bestpick.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${profileImageUploadPath}")
    private String profileImageUploadPath;

    private final UserRepository userRepository;

    private final KafkaProducer kafkaProducer;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserResponseDto createUser(UserRequestDto userDto) {

        User newUser = new User(userDto);

        if (userDto.password() != null) {
            newUser.setPasswordHash(encoder.encode(userDto.password()));
        }

        String profileImagePath;

        try {
            profileImagePath = saveImageOnS3Server(userDto.profilePhoto());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Something went wrong saving the profile image");
        }

        newUser.setProfileImagePath(profileImagePath);

        try {
            newUser = userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            // Aquí normalmente es porque el username/nickname ya existe
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "The username is already in use",
                    e);
        }

        kafkaProducer.sendCreateUserEvent(new UserEvent(newUser.getId(), newUser.getUsername(),
                newUser.getDescription(), newUser.getProfileImagePath()));

        return newUser.toResponseDto();

    }

    // TODO: complete s3 server uploading
    private String saveImageOnS3Server(MultipartFile profileImage) {

        String imageId = String.valueOf(Math.random());

        return profileImageUploadPath + "/" + imageId;
    }

    public UserResponseDto[] getAllUsers(String username) {

        List<User> users;

        if (username == null || username.isBlank()) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findByUsername(username);
        }

        return users.stream().map(User::toResponseDto).toArray(UserResponseDto[]::new);
    }

    public UserResponseDto getUserById(String id) {

        long userId;

        try {
            userId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Given Id must be a valid number");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"));

        return user.toResponseDto();

    }

    public UserResponseDto updateUser(String id, UserRequestDto userDto) {

        long userId;

        try {
            userId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Given Id must be a valid number");
        }

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"));

        BeanUtils.copyProperties(userDto, existingUser, "id");

        User savedUser = userRepository.save(existingUser);

        return savedUser.toResponseDto();

    }

    public void deleteUserById(String id) {

        long userId;

        try {
            userId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Given Id must be a valid number");
        }

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"));

        userRepository.delete(existingUser);

        kafkaProducer.sendDeleteUserEvent(new UserEvent(existingUser.getId(), existingUser.getUsername(),
                existingUser.getDescription(), existingUser.getProfileImagePath()));

    }

}
