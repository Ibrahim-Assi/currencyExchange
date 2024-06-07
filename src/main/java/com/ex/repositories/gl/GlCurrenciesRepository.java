package com.ex.repositories.gl;

import com.ex.models.entities.gl.GlCurrencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlCurrenciesRepository extends JpaRepository<GlCurrencies,Long> , RevisionRepository<GlCurrencies,Long,Integer> {

    GlCurrencies findByCurrCode(String currCode);

}
