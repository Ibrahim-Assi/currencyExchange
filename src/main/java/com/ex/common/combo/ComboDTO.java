package com.ex.common.combo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComboDTO {
    private String value;
    private String label;

    public String getValueLabel() {
        return "["+value+"] "+label;
    }
}
