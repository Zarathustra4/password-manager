package com.passpnu.passwordmanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="password")
public class PasswordEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank
    @ManyToOne
    @JoinColumn(name="service_id", nullable = false, updatable = false, insertable = false)
    private ServiceEntity service;

    @Column(name="service_id")
    private Long serviceId;

    @NotBlank
    private String password;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;

}
