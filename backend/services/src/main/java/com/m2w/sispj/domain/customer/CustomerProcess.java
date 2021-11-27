package com.m2w.sispj.domain.customer;

import com.m2w.sispj.domain.BaseAuditEntity;
import com.m2w.sispj.domain.ProcessType;
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
@Table(name = CustomerProcess.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = CustomerProcess.SEQUENCE, allocationSize = 1)
public class CustomerProcess extends BaseAuditEntity {

	public static final String TABLE = "customer_process_type";
	public static final String SEQUENCE = "customer_process_type_seq";
	public static final String STATUS = "status";
	public static final String CUSTOMER_ID = "customer_id";
	public static final String PROCESS_TYPE_ID = "process_type_id";
	public static final String ARCHIVE_PATH = "archive_path";
	public static final String CANCELLATION_REASON = "cancellation_reason";
	public static final String START_DATE = "start_date";
	public static final String END_DATE = "end_date";

	@ManyToOne
	@JoinColumn(name = CustomerProcess.CUSTOMER_ID)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = CustomerProcess.PROCESS_TYPE_ID)
	private ProcessType type;

	@Column(name = CustomerProcess.ARCHIVE_PATH)
	private String archivePath;

	@Column(name = CustomerProcess.CANCELLATION_REASON)
	private String cancellationReason;

	@Column(name = CustomerProcess.STATUS)
	private ProcessStatus status;

	@Column(name = START_DATE)
	private LocalDateTime startDate;

	@Column(name = END_DATE)
	private LocalDateTime endDate;
}