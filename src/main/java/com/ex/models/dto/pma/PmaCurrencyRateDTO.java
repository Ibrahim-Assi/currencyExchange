package com.ex.models.dto.pma;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PmaCurrencyRateDTO {
    @JsonProperty("from-to")
    private String fromTo;

    @JsonProperty("from")
    private PmaCurrencyInfoDTO fromCurrency;

    @JsonProperty("to")
    private PmaCurrencyInfoDTO toCurrency;
    @JsonProperty("ask")
    private String ask;
    @JsonProperty("bid")
    private String bid;
    @JsonProperty("mid_rate")
    private String midRate;

    public String getFromCurrencyCode() {
        return fromCurrency.getCode();
    }
    public String getToCurrencyCode() {
        return toCurrency.getCode();
    }
    public String getMrate() {
        return midRate;
    }
}
