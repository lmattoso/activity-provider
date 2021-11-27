package com.m2w.sispj.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProvisionResumeDTO {
	private List<CustomerProvisionDTO> provisions;
	private BigDecimal total;
	private BigDecimal totalPayed;
	private BigDecimal totalDue;
}