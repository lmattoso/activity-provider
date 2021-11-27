package com.m2w.sispj.domain.customer;

import com.m2w.sispj.domain.BaseAuditEntity;
import com.m2w.sispj.domain.Country;
import com.m2w.sispj.domain.MaritalStatus;
import com.m2w.sispj.domain.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = Customer.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = Customer.SEQUENCE, allocationSize = 1)
public class Customer extends BaseAuditEntity {

	public static final String TABLE = "customer";
	public static final String SEQUENCE = "customer_seq";
	public static final String NAME = "name";
	public static final String PHONE = "phone";
	public static final String ADDRESS = "address";
	public static final String ADDRESS_POSTAL_CODE = "address_postal_code";
	public static final String ADDRESS_NUMBER = "address_number";
	public static final String ADDRESS_FLOOR = "address_floor";
	public static final String ADDRESS_LOCALITY = "address_locality";
	public static final String MARITAL_STATUS_ID = "marital_status_id";
	public static final String COUNTRY_ID = "country_id";
	public static final String PROFESSION = "profession";
	public static final String QUALIFICATION = "qualification";
	public static final String NIF = "nif";
	public static final String NIF_PASSWORD = "nif_password";
	public static final String NISS = "niss";
	public static final String NISS_PASSWORD = "niss_password";
	public static final String EMAIL_SAPA = "email_sapa";
	public static final String EMAIL_SAPA_PASSWORD = "email_sapa_password";
	public static final String EMAIL_SEF = "email_sef";
	public static final String EMAIL_SEF_PASSWORD = "email_sef_password";
	public static final String EMAIL = "email";
	public static final String WHATTSAPP = "whattsapp";
	public static final String FATHER_NAME = "father_name";
	public static final String MOTHER_NAME = "mother_name";
	public static final String OBSERVATION = "observation";
	public static final String GENRE = "genre";
	public static final String PARISH = "parish";
	public static final String COUNTY = "county";

	public static final String NUMBER_1 = "first_additional_number";
	public static final String PASSWORD_1 = "first_additional_password";
	public static final String OBSERVATION_1 = "first_additional_observation";
	public static final String NUMBER_2 = "second_additional_number";
	public static final String PASSWORD_2 = "second_additional_password";
	public static final String OBSERVATION_2 = "second_additional_observation";

	@Column(name = Customer.NAME)
	private String name;

	@Column(name = Customer.PHONE)
	private String phone;

	@Column(name = Customer.ADDRESS)
	private String address;

	@Column(name = Customer.ADDRESS_POSTAL_CODE)
	private String postalCode;

	@Column(name = Customer.ADDRESS_NUMBER)
	private String addressNumber;

	@Column(name = Customer.ADDRESS_FLOOR)
	private String addressFloor;

	@Column(name = Customer.ADDRESS_LOCALITY)
	private String locality;

	@ManyToOne
	@JoinColumn(name = Customer.MARITAL_STATUS_ID)
	private MaritalStatus maritalStatus;

	@ManyToOne
	@JoinColumn(name = Customer.COUNTRY_ID)
	private Country country;

	@JoinColumn(name = Customer.PROFESSION)
	private String profession;

	@JoinColumn(name = Customer.QUALIFICATION)
	private String qualification;

	@Column(name = Customer.NIF)
	private String nif;

	@Column(name = Customer.NIF_PASSWORD)
	private String nifPassword;

	@Column(name = Customer.NISS)
	private String niss;

	@Column(name = Customer.NISS_PASSWORD)
	private String nissPassword;

	@Column(name = Customer.EMAIL_SAPA)
	private String emailSapa;

	@Column(name = Customer.EMAIL_SAPA_PASSWORD)
	private String emailSapaPassword;

	@Column(name = Customer.EMAIL_SEF)
	private String emailSef;

	@Column(name = Customer.EMAIL_SEF_PASSWORD)
	private String emailSefPassword;

	@Column(name = Customer.EMAIL)
	private String email;

	@Column(name = Customer.WHATTSAPP)
	private String whattsapp;

	@Column(name = Customer.FATHER_NAME)
	private String fatherName;

	@Column(name = Customer.MOTHER_NAME)
	private String motherName;

	@Column(name = Customer.OBSERVATION)
	private String observation;

	@Column(name = Customer.GENRE)
	private Genre genre;

	@Column(name = Customer.PARISH)
	private String parish;

	@Column(name = Customer.COUNTY)
	private String county;

	@Column(name = Customer.NUMBER_1)
	private String number1;

	@Column(name = Customer.PASSWORD_1)
	private String password1;

	@Column(name = Customer.OBSERVATION_1)
	private String observation1;

	@Column(name = Customer.NUMBER_2)
	private String number2;

	@Column(name = Customer.PASSWORD_2)
	private String password2;

	@Column(name = Customer.OBSERVATION_2)
	private String observation2;

	@OneToMany(targetEntity = CustomerProcess.class, mappedBy = CustomerProcess.Fields.customer)
	private List<CustomerProcess> customerProcesses;
}