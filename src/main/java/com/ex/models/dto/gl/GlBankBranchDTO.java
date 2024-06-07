package com.ex.models.dto.gl;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlBankBranchDTO {
	private Long     branchId ;
	private Long     bankId;
	private String   branchArName;
	private String   branchEnName;
	private String   branchAddress;
	private Boolean  isActive;
}
