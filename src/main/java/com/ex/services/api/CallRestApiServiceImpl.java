package com.ex.services.api;

import com.ex.exceptions.ServiceException;
import com.ex.models.dto.pma.PmaCurrencyDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
@Slf4j
public class CallRestApiServiceImpl implements CallRestApiService {
    private final RestTemplate restTemplate;

    @Override
    public PmaCurrencyDataDTO getCurrencyData() {
        String apiUrl = "https://wcur.pma.ps/ar/webtools/currency/json?";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("fbclid", "IwAR2eLJ7ve7OrVePtz4v814dZQEI3NdZsxcBQ48G2dKVJWbHBxBF3RD2zmwo");
        try {
            ResponseEntity<PmaCurrencyDataDTO> response = restTemplate.getForEntity(uriBuilder.toUriString(), PmaCurrencyDataDTO.class);
            return response.getBody();
        } catch (Exception e) {
            log.error(e.getMessage(),e.fillInStackTrace());
            throw new ServiceException("حدث خطأ أثناء تحديث أسعار العملات من سلطة النقد");
        }

    }
}
