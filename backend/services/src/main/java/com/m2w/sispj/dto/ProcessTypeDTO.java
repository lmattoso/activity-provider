package com.m2w.sispj.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@SuperBuilder
public class ProcessTypeDTO extends BaseAuditDTO {
	private String name;
	private BigDecimal price;
	private List<DocumentTypeDTO> documents;
	private List<ProcessStepTypeDTO> steps;
	private String publishDate;
	private SimpleUserDTO publishBy;
	private boolean deleted;
	private String versionNumber;
}