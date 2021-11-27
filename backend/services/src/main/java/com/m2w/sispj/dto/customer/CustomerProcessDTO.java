package com.m2w.sispj.dto.customer;

import com.m2w.sispj.domain.enums.ProcessStatus;
import com.m2w.sispj.dto.BaseAuditDTO;
import com.m2w.sispj.dto.ProcessTypeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@SuperBuilder
public class CustomerProcessDTO extends BaseAuditDTO {
	private Long customerId;
	private String startDate;
	private String endDate;
	private String createDate;
	private ProcessStatus status;
	private String statusName;
	private String archivePath;
	private String cancellationReason;
	private ProcessTypeDTO type;
	private List<CustomerProcessStepDTO> steps;
	private List<CustomerProcessDocumentDTO> documents;
}