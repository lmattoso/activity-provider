package com.m2w.sispj.domain.customer;

import com.m2w.sispj.domain.BaseAuditEntity;
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
@Table(name = CustomerProcessNote.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = CustomerProcessNote.SEQUENCE, allocationSize = 1)
public class CustomerProcessNote extends BaseAuditEntity {

	public static final String TABLE = "customer_process_type_note";
	public static final String SEQUENCE = "customer_process_type_note_seq";
	public static final String CUSTOMER_PROCESS_TYPE_ID = "customer_process_type_id";
	public static final String DESCRIPTION = "description";

	@ManyToOne
	@JoinColumn(name = CustomerProcessDocument.CUSTOMER_PROCESS_TYPE_ID)
	private CustomerProcess process;

	@Column(name = CustomerProcessNote.DESCRIPTION)
	private String description;
}