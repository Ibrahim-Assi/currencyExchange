package com.ex.models.dto.pma;

import lombok.Data;

import java.util.List;
@Data
public class PmaCurrencyDataDTO {
    private String date;
    private List<PmaCurrencyRateDTO> data;
}
