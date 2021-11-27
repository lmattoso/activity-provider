package com.m2w.sispj.domain.customer;

import com.m2w.sispj.domain.BaseAuditEntity;
import com.m2w.sispj.domain.ProcessStepType;
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
@Table(name = CustomerProcessStep.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = CustomerProcessStep.SEQUENCE, allocationSize = 1)
public class CustomerProcessStep extends BaseAuditEntity {

	public static final String TABLE = "customer_process_step";
	public static final String SEQUENCE = "customer_process_step_seq";
	public static final String STATUS = "status";
	public static final String CUSTOMER_PROCESS_TYPE_ID = "customer_process_type_id";
	public static final String PROCESS_STEP_TYPE_ID = "process_step_type_id";
	public static final String ORDER = "step_order";
	public static final String START_DATE = "start_date";
	public static final String END_DATE = "end_date";

	@ManyToOne
	@JoinColumn(name = CustomerProcessStep.CUSTOMER_PROCESS_TYPE_ID)
	private CustomerProcess process;

	@ManyToOne
	@JoinColumn(name = CustomerProcessStep.PROCESS_STEP_TYPE_ID)
	private ProcessStepType type;

	@Column(name = CustomerProcessStep.STATUS)
	private ProcessStatus status;

	@Column(name = CustomerProcessStep.ORDER)
	private Integer order;

	@Column(name = START_DATE)
	private LocalDateTime startDate;

	@Column(name = END_DATE)
	private LocalDateTime endDate;
}