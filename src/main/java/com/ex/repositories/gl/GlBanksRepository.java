package com.ex.repositories.gl;

import com.ex.models.entities.gl.GlBanks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlBanksRepository extends JpaRepository<GlBanks,Long> , RevisionRepository<GlBanks,Long,Integer> {

    GlBanks findByBankId(Long bankId);
}
