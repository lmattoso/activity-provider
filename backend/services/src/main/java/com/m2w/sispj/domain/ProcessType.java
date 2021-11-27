package com.m2w.sispj.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@FieldNameConstants
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = ProcessType.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = ProcessType.SEQUENCE, allocationSize = 1)
public class ProcessType extends BaseAuditEntity {

	public static final String TABLE = "process_type";
	public static final String SEQUENCE = "process_type_seq";
	public static final String NAME = "name";
	public static final String PRICE = "price";
	public static final String PUBLISH_DATE = "publish_date";
	public static final String PUBLISH_BY = "publish_by";
	public static final String DELETED = "deleted";
	public static final String VERSION_NUMBER = "version_number";

	@Column(name = NAME)
	private String name;

	@Column(name = PRICE)
	private BigDecimal price;

	@Column(name = PUBLISH_DATE)
	private LocalDateTime publishDate;

	@ManyToOne
	@JoinColumn(name = PUBLISH_BY)
	private User publishBy;

	@Column(name = DELETED)
	private boolean deleted;

	@Column(name = VERSION_NUMBER)
	private String versionNumber;

	@ManyToMany
	@JoinTable(name = "process_type_document_type",
		joinColumns = {@JoinColumn(name = "process_type_id")},
		inverseJoinColumns = {@JoinColumn(name = "document_type_id")}
	)
	private List<DocumentType> documents;

	@ManyToMany
	@JoinTable(name = "process_type_process_step_type",
		joinColumns = {@JoinColumn(name = "process_type_id")},
		inverseJoinColumns = {@JoinColumn(name = "process_step_type_id")}
	)
	private List<ProcessStepType> steps;
}