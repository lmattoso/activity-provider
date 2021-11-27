package com.m2w.sispj.dto.budget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BudgetItemDTO {
	private Long id;
	private String request;
	private BigDecimal value;
	private BigDecimal iva;
	private Integer order;

	public BigDecimal getTotal() {
		return value.add(Optional.ofNullable(iva).orElse(BigDecimal.ZERO));
	}
}