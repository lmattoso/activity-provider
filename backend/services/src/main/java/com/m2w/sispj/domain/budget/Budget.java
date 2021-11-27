package com.m2w.sispj.domain.budget;

import com.m2w.sispj.domain.BaseAuditEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@FieldNameConstants
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = Budget.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = Budget.SEQUENCE, allocationSize = 1)
public class Budget extends BaseAuditEntity {

	public static final String TABLE = "budget";
	public static final String SEQUENCE = "budget_seq";
	public static final String NAME = "name";
	public static final String REQUEST_DESCRIPTION = "request_description";
	public static final String SERVICE_DESCRIPTION = "service_description";
	public static final String DOCUMENTS_DESCRIPTION = "documents_description";
	public static final String COMMENTS = "comments";
	public static final String PHONE = "phone";
	public static final String ADDRESS = "address";
	public static final String NIF = "nif";
	public static final String NISS = "niss";
	public static final String EXPIRATION_DATE = "expiration_date";

	@Column(name = Budget.NAME)
	private String name;

	@Column(name = Budget.REQUEST_DESCRIPTION)
	private String requestDescription;

	@Column(name = Budget.SERVICE_DESCRIPTION)
	private String serviceDescription;

	@Column(name = Budget.DOCUMENTS_DESCRIPTION)
	private String documentsDescription;

	@Column(name = Budget.COMMENTS)
	private String comments;

	@Column(name = Budget.PHONE)
	private String phone;

	@Column(name = Budget.ADDRESS)
	private String address;

	@Column(name = Budget.NIF)
	private String nif;

	@Column(name = Budget.NISS)
	private String niss;

	@Column(name = EXPIRATION_DATE)
	private ZonedDateTime expirationDate;

	@OneToMany(targetEntity = BudgetItem.class, mappedBy = BudgetItem.Fields.budget)
	private List<BudgetItem> items;
}