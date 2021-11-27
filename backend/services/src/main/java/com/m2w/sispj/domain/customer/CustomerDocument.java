package com.m2w.sispj.domain.customer;

import com.m2w.sispj.domain.BaseAuditEntity;
import com.m2w.sispj.domain.DocumentType;
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
@Table(name = CustomerDocument.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = CustomerDocument.SEQUENCE, allocationSize = 1)
public class CustomerDocument extends BaseAuditEntity {

	public static final String TABLE = "customer_document";
	public static final String SEQUENCE = "customer_document_seq";
	public static final String CUSTOMER_ID = "customer_id";
	public static final String DOCUMENT_TYPE_ID = "document_type_id";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String KEY_PATH = "key_path";

	@ManyToOne
	@JoinColumn(name = CustomerDocument.CUSTOMER_ID)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = CustomerDocument.DOCUMENT_TYPE_ID)
	private DocumentType type;

	@Column(name = CustomerDocument.NAME)
	private String name;

	@Column(name = CustomerDocument.DESCRIPTION)
	private String description;

	@Column(name = CustomerDocument.KEY_PATH)
	private String key;
}