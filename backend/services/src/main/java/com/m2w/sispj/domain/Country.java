package com.m2w.sispj.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@FieldNameConstants
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = Country.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = Country.SEQUENCE, allocationSize = 1)
public class Country extends BaseAuditEntity {

	public static final String TABLE = "country";
	public static final String SEQUENCE = "country_seq";
	public static final String CODE = "code";
	public static final String NAME = "name";

	@JoinColumn(name = Country.CODE)
	private String code;

	@Column(name = Country.NAME)
	private String name;
}