package com.passpnu.passwordmanager.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PasswordTestAnswer {
    private boolean isStrong;
    private List<String> descriptions;
    public void addDescription(String description) {
        descriptions.add(description);
    }

    public PasswordTestAnswer(boolean isStrong){
        this.isStrong = isStrong;
        this.descriptions = new ArrayList<>();
    }
}
