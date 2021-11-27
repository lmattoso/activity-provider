package com.m2w.sispj.domain.customer;

import com.m2w.sispj.domain.BaseAuditEntity;
import com.m2w.sispj.domain.DocumentType;
import com.m2w.sispj.domain.enums.ProcessStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@FieldNameConstants
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = CustomerProcessDocument.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = CustomerProcessDocument.SEQUENCE, allocationSize = 1)
public class CustomerProcessDocument extends BaseAuditEntity {

	public static final String TABLE = "customer_process_document";
	public static final String SEQUENCE = "customer_process_document_seq";
	public static final String STATUS = "status";
	public static final String CUSTOMER_PROCESS_TYPE_ID = "customer_process_type_id";
	public static final String DOCUMENT_TYPE_ID = "document_type_id";
	public static final String CHECK_DATE = "check_date";

	@ManyToOne
	@JoinColumn(name = CustomerProcessDocument.CUSTOMER_PROCESS_TYPE_ID)
	private CustomerProcess process;

	@ManyToOne
	@JoinColumn(name = CustomerProcessDocument.DOCUMENT_TYPE_ID)
	private DocumentType type;

	@Column(name = CustomerProcessDocument.STATUS)
	private ProcessStatus status;

	@Column(name = CHECK_DATE)
	private LocalDateTime checkDate;
}