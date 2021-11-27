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
@Table(name = DocumentType.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = DocumentType.SEQUENCE, allocationSize = 1)
public class DocumentType extends BaseAuditEntity {

	public static final String TABLE = "document_type";
	public static final String SEQUENCE = "document_type_seq";
	public static final String NAME = "name";

	@Column(name = DocumentType.NAME)
	private String name;
}