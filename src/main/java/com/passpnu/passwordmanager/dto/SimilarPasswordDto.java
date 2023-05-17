package com.passpnu.passwordmanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimilarPasswordDto {
    private Long serviceId;
    private String serviceDomain;
    private Double similarity;
    private String password;
}
