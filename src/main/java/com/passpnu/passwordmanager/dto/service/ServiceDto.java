package com.passpnu.passwordmanager.dto.service;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ServiceDto {
    private String title;
    private String domain;
    private String description;
    private String logoPath;
}
