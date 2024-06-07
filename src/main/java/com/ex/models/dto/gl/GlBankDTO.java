package com.ex.models.dto.gl;

import com.ex.models.entities.gl.GlBankBranches;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlBankDTO {
	
	private Long bankId ;
	private String bankArName;
	private String bankEnName;
	private Set<GlBankBranches> glBankBranches = new HashSet<>();

}
