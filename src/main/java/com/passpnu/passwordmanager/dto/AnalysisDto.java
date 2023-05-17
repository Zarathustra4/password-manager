package com.passpnu.passwordmanager.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class AnalysisDto {
    private String password;
    private Long serviceId;
    private boolean isStrong;
    private List<SimilarPasswordDto> similarPasswords;

    public void addSimilarPassword(SimilarPasswordDto similarPassword){
        similarPasswords.add(similarPassword);
    }
}
