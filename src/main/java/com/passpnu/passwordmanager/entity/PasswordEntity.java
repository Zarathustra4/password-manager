package com.passpnu.passwordmanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name="service_id", nullable = false, updatable = false, insertable = false)
    @Column(name="service_id")
    private Long serviceId;

    @NotBlank
    private String password;

    @JoinColumn(name="user_id", nullable = false, insertable = false, updatable = false)
    @Column(name="user_id")
    private Long userId;

}
