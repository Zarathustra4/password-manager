package com.passpnu.passwordmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Password {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long serviceId;
    private String password;
    private Long userId;
}
