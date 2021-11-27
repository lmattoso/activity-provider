package com.m2w.sispj.domain;

import com.m2w.sispj.domain.customer.Customer;
import com.m2w.sispj.domain.customer.CustomerProcess;
import com.m2w.sispj.domain.enums.ActivityType;
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
@Table(name = Activity.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = Activity.SEQUENCE, allocationSize = 1)
public class Activity extends BaseAuditEntity {

	public static final String TABLE = "activity";
	public static final String SEQUENCE = "activity_seq";
	public static final String CUSTOMER_ID = "customer_id";
	public static final String CUSTOMER_PROCESS_TYPE_ID = "customer_process_type_id";
	public static final String DESCRIPTION = "description";
	public static final String TYPE = "type";

	@ManyToOne
	@JoinColumn(name = Activity.CUSTOMER_ID)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = Activity.CUSTOMER_PROCESS_TYPE_ID)
	private CustomerProcess process;

	@Column(name = Activity.DESCRIPTION)
	private String description;

	@Column(name = Activity.TYPE)
	private ActivityType type;
}