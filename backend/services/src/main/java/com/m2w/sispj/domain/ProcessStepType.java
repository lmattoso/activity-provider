package com.m2w.sispj.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@FieldNameConstants
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = ProcessStepType.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = ProcessStepType.SEQUENCE, allocationSize = 1)
public class ProcessStepType extends BaseAuditEntity {

	public static final String TABLE = "process_step_type";
	public static final String SEQUENCE = "process_step_type_seq";
	public static final String NAME = "name";

	@Column(name = ProcessStepType.NAME)
	private String name;
}