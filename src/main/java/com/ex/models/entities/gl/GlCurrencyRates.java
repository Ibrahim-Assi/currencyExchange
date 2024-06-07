package com.ex.models.entities.gl;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gl_currencies_rates")
public class GlCurrencyRates {
    @EmbeddedId
    private GlCurrencyRatesPK id;
    @Column(name = "sale_rate", length = 40, nullable = false)
    private Double  saleRate;
    @Column(name = "purchase_rate", length = 40, nullable = false)
    private Double  purchaseRate;
    @Column(name = "avg_rate", length = 40, nullable = false)
    private Double  avgRate;

}
