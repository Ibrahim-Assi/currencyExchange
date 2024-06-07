package com.ex.services.gl;

import com.ex.exceptions.ServiceResponse;
import com.ex.models.dto.gl.GlCurrencyRatesDTO;
import com.ex.models.dto.gl.GlCurrenciesDTO;

import java.util.List;

public interface GlCurrenciesService {

    // Currency .........
    List<GlCurrenciesDTO> getAllCurrencies();
    ServiceResponse createCurrency(GlCurrenciesDTO currencyDTO);
    ServiceResponse updateCurrency (GlCurrenciesDTO updatedCurrency);
    ServiceResponse deleteCurrency(String currCode);
    GlCurrenciesDTO getCurrencyByCurrCode(String currCode);

    // CurrencyRate .........
    List<GlCurrencyRatesDTO> getAllCurrencyRates();
    GlCurrencyRatesDTO getCurrencyRateFromToCurrCode(String fromCurrCode, String toCurrCode,String voucherDate);
    ServiceResponse creatCurrencyRate(GlCurrencyRatesDTO currencyRatesDTO);
    List<GlCurrencyRatesDTO> getAllCurrencyLastRateList();
}
