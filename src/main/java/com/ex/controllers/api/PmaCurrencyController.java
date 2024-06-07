package com.ex.controllers.api;

import com.ex.models.dto.pma.PmaCurrencyDataDTO;
import com.ex.services.api.CallRestApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PmaCurrencyController {
    private final CallRestApiService callRestApiService;

    @GetMapping("/currencies")
    public PmaCurrencyDataDTO getCurrencies() {
        PmaCurrencyDataDTO pmaCurrencyDataDTO = callRestApiService.getCurrencyData();
        return pmaCurrencyDataDTO;
    }
}
