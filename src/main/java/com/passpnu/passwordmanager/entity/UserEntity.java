package com.passpnu.passwordmanager.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;


import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="\"user\"")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Size(min = 4)
    @Column(nullable = false)
    private String password;

    @Size(min = 8, max = 24)
    @Column(nullable = false)
    private String encryptionKey;

    @Column(nullable = false)
    private Role role;


}
