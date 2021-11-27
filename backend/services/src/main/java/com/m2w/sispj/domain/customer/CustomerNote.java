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
@Table(name = CustomerNote.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = CustomerNote.SEQUENCE, allocationSize = 1)
public class CustomerNote extends BaseAuditEntity {

	public static final String TABLE = "customer_note";
	public static final String SEQUENCE = "customer_note_seq";
	public static final String CUSTOMER_ID = "customer_id";
	public static final String DESCRIPTION = "description";

	@ManyToOne
	@JoinColumn(name = CustomerNote.CUSTOMER_ID)
	private Customer customer;

	@Column(name = CustomerNote.DESCRIPTION)
	private String description;
}