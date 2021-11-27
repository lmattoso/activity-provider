package com.m2w.sispj.dto.customer;

import com.m2w.sispj.domain.enums.ProvisionType;
import com.m2w.sispj.dto.BaseAuditDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@SuperBuilder
public class CustomerProvisionDTO extends BaseAuditDTO {
	private Long customerId;
	private Long customerProcessId;
	private String description;
	private ProvisionType type;
	private BigDecimal serviceValue;
	private BigDecimal paymentValue;
	private String paymentDate;
}