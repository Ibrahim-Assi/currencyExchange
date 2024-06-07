package com.ex.models.dto.gl;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GlCurrenciesDTO {

    private String currCode;
    private String currArName;
    private String currEnName;
    private String currPartEnName;
    private Double minRate;
    private Double maxRate;
    private String currPartArName;

}
