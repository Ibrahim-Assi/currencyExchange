package com.ex.models.dto.gl;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlCurrencyRatesDTO {

    private String  fromCurrCode;
    private String  fromCurrName;
    private String  toCurrCode;
    private String  toCurrName;
    private Double  saleRate;
    private Double  purchaseRate;
    private Double  avgRate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date    rateDate;

    public GlCurrencyRatesDTO(Double saleRate, Double purchaseRate, Double avgRate) {
        this.saleRate = saleRate;
        this.purchaseRate = purchaseRate;
        this.avgRate = avgRate;
    }
}


