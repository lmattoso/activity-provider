package com.m2w.sispj.dto.customer;

import com.m2w.sispj.domain.enums.ProcessStatus;
import com.m2w.sispj.dto.BaseAuditDTO;
import com.m2w.sispj.dto.ProcessTypeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@SuperBuilder
public class CustomerProcessStepDTO extends BaseAuditDTO {
	private String startDate;
	private String endDate;
	private ProcessStatus status;
	private ProcessTypeDTO type;
	private Integer order;
}