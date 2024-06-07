package com.ex.models.entities.gl;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Embeddable

public class GlCurrencyRatesPK implements Serializable {

    @Column(name = "from_curr_code", length = 10, nullable = false)
    private String fromCurrCode;
    @Column(name ="to_curr_code",  length = 10, nullable = false)
    private String toCurrCode;
    @Column(name = "rate_date", length = 40, nullable = false)
    private Date rateDate;


}
