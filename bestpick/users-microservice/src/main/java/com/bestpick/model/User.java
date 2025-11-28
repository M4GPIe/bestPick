package com.bestpick.model;

import org.springframework.beans.BeanUtils;

import com.bestpick.dto.UserRequestDto;
import com.bestpick.dto.UserResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    private String passwordHash; // if user logs with external provider will be null
    private String description;
    private String profileImagePath;
    private String iss; // external provider

    @Column(unique = true, nullable = true)
    private String sub; // external provider user id

    public User(UserRequestDto userDto) {
        BeanUtils.copyProperties(userDto, this);
    }

    public UserResponseDto toResponseDto() {
        return new UserResponseDto(
                this.getUsername(),
                this.getPasswordHash(),
                this.getDescription(),
                this.getProfileImagePath(),
                this.getIss(),
                this.getSub());
    }

}
