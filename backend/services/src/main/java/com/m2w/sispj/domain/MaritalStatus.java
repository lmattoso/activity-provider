package com.m2w.sispj.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = MaritalStatus.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = MaritalStatus.SEQUENCE, allocationSize = 1)
public class MaritalStatus extends BaseAuditEntity {

	public static final String TABLE = "marital_status";
	public static final String SEQUENCE = "marital_status_seq";
	public static final String NAME = "name";

	@Column(name = MaritalStatus.NAME)
	private String name;
}