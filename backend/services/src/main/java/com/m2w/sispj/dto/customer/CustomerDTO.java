package com.m2w.sispj.dto.customer;

import com.m2w.sispj.domain.enums.Genre;
import com.m2w.sispj.dto.BaseAuditDTO;
import com.m2w.sispj.dto.ProcessTypeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@SuperBuilder
public class CustomerDTO extends BaseAuditDTO {
	private Long id;
	private String name;
	private Genre genre;
	private String address;
	private String postalCode;
	private String addressNumber;
	private String addressFloor;
	private String locality;
	private String phone;
	private Long maritalStatusId;
	private Long countryId;
	private String countryName;
	private String profession;
	private String qualification;
	private String nif;
	private String nifPassword;
	private String niss;
	private String nissPassword;
	private String emailSapa;
	private String emailSapaPassword;
	private String emailSef;
	private String emailSefPassword;
	private String email;
	private String whattsapp;
	private String fatherName;
	private String motherName;
	private String observation;
	private List<ProcessTypeDTO> services;
	private List<CustomerProcessDTO> servicesCustomer;
	private String parish;
	private String county;
	private String number1;
	private String password1;
	private String observation1;
	private String number2;
	private String password2;
	private String observation2;
}