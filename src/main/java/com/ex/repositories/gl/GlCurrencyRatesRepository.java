package com.ex.repositories.gl;

import com.ex.models.entities.gl.GlCurrencyRates;
import com.ex.models.entities.gl.GlCurrencyRatesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlCurrencyRatesRepository extends JpaRepository<GlCurrencyRates, GlCurrencyRatesPK> {

}
