package com.passpnu.passwordmanager.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;
    @Size(min = 4, max = 16)
    private String password;
    @NotBlank
    private String encryptionKey;
    //@NotBlank
    private Role role;
}
