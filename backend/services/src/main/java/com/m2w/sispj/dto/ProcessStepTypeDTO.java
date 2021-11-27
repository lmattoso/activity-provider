package com.m2w.sispj.dto;

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
public class ProcessStepTypeDTO extends BaseAuditDTO {
	private String name;
}