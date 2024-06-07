package com.ex.repositories.gl;

import com.ex.models.entities.gl.GlBankBranches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlBankBranchesRepository extends JpaRepository<GlBankBranches,  Long>, RevisionRepository<GlBankBranches,Long,Integer> {
    List findAllByBankIdOrderByBranchArName(long bankId);

    GlBankBranches findByBranchArName(String branchArName);
    GlBankBranches findByBranchEnName(String branchEnName);
}
