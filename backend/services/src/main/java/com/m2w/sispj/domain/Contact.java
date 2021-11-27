package com.m2w.sispj.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = Contact.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = Contact.SEQUENCE, allocationSize = 1)
public class Contact extends BaseAuditEntity {

	public static final String TABLE = "contact";
	public static final String SEQUENCE = "contact_seq";
	public static final String NAME = "name";
	public static final String PHONE = "phone";
	public static final String EMAIL = "email";

	@Column(name = Contact.NAME)
	private String name;

	@Column(name = Contact.PHONE)
	private String phone;

	@Column(name = Contact.EMAIL)
	private String email;
}