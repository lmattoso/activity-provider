--liquibase formatted sql

--changeset initial:1.1
drop sequence if exists customer_seq;
drop sequence if exists customer_process_type_seq;
drop sequence if exists marital_status_seq;
drop sequence if exists activity_seq;
drop sequence if exists process_type_seq;
drop sequence if exists document_type_seq;
drop sequence if exists process_step_type_seq;
drop sequence if exists country_seq;
drop sequence if exists user_seq;
drop sequence if exists customer_process_step_seq;
drop sequence if exists customer_process_document_seq;
drop sequence if exists customer_provision_seq;
drop sequence if exists budget_seq;
drop sequence if exists budget_item_seq;
drop sequence if exists customer_process_type_note_seq;
drop sequence if exists customer_note_seq;
drop sequence if exists contact_seq;
drop sequence if exists customer_document_seq;

create sequence customer_seq start 1 increment 1;
create sequence marital_status_seq start 1 increment 1;
create sequence activity_seq start 1 increment 1;
create sequence process_type_seq start 100 increment 1;
create sequence document_type_seq start 100 increment 1;
create sequence process_step_type_seq start 100 increment 1;
create sequence country_seq start 1 increment 1;
create sequence user_seq start 10 increment 1;
create sequence customer_process_type_seq start 1 increment 1;
create sequence customer_process_step_seq start 1 increment 1;
create sequence customer_process_document_seq start 1 increment 1;
create sequence budget_seq start 1 increment 1;
create sequence budget_item_seq start 1 increment 1;
create sequence customer_process_type_note_seq start 1 increment 1;
create sequence customer_note_seq start 1 increment 1;
create sequence customer_document_seq start 1 increment 1;
create sequence contact_seq start 1 increment 1;
create sequence customer_provision_seq start 1 increment 1;

--changeset initial:1.2
CREATE TABLE sispj_profile (
	id bigint NOT NULL,
	code varchar(20) UNIQUE NOT NULL,
	name varchar(50) UNIQUE NOT NULL,
	CONSTRAINT sispj_profile_pkey PRIMARY KEY (id)
);

CREATE TABLE sispj_user (
	id bigint NOT NULL DEFAULT nextval('user_seq'),
	name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	changePassword boolean NOT NULL,
	profile_id bigint NOT NULL REFERENCES sispj_profile(id),
	deleted boolean NOT NULL DEFAULT false,
    version int4,
	CONSTRAINT sispj_user_pkey PRIMARY KEY (id)
);
CREATE UNIQUE index if not exists sispj_user_email_idx on sispj_user(email) where deleted = false;
CREATE UNIQUE index if not exists sispj_user_name_idx on sispj_user(name) where deleted = false;

--changeset initial:1.3
CREATE TABLE marital_status (
	id bigint NOT NULL DEFAULT nextval('marital_status_seq'),
	name varchar(100) UNIQUE NOT NULL,
	version int4,
	create_date timestamp without time zone NOT NULL,
	create_by bigint NOT NULL REFERENCES sispj_user(id),
	update_date timestamp without time zone NOT NULL,
	update_by bigint NOT NULL REFERENCES sispj_user(id),
	CONSTRAINT marital_status_pkey PRIMARY KEY (id)
);

CREATE TABLE country (
	id bigint NOT NULL DEFAULT nextval('country_seq'),
	name varchar(255) UNIQUE NOT NULL,
	code varchar(255) UNIQUE NOT NULL,
	version int4,
    create_date timestamp without time zone NOT NULL,
    create_by bigint NOT NULL REFERENCES sispj_user(id),
    update_date timestamp without time zone NOT NULL,
    update_by bigint NOT NULL REFERENCES sispj_user(id),
	CONSTRAINT country_pkey PRIMARY KEY (id)
);

--changeset initial:1.4
CREATE TABLE document_type (
	id bigint NOT NULL DEFAULT nextval('document_type_seq'),
	name varchar(255) UNIQUE NOT NULL,
    version int4,
    create_date timestamp without time zone NOT NULL,
    create_by bigint NOT NULL REFERENCES sispj_user(id),
    update_date timestamp without time zone NOT NULL,
    update_by bigint NOT NULL REFERENCES sispj_user(id),
	CONSTRAINT document_type_pkey PRIMARY KEY (id)
);

CREATE TABLE process_step_type (
	id bigint NOT NULL DEFAULT nextval('process_step_type_seq'),
	name varchar(255) UNIQUE NOT NULL,
    version int4,
    create_date timestamp without time zone NOT NULL,
    create_by bigint NOT NULL REFERENCES sispj_user(id),
    update_date timestamp without time zone NOT NULL,
    update_by bigint NOT NULL REFERENCES sispj_user(id),
	CONSTRAINT process_step_type_pkey PRIMARY KEY (id)
);

CREATE TABLE process_type (
	id bigint NOT NULL DEFAULT nextval('process_type_seq'),
	name varchar(255) NOT NULL,
	price numeric(12, 2) NOT NULL,
	publish_date timestamp without time zone NULL,
	publish_by bigint NULL REFERENCES sispj_user(id),
    version int4,
    create_date timestamp without time zone NOT NULL,
    create_by bigint NOT NULL REFERENCES sispj_user(id),
    update_date timestamp without time zone NOT NULL,
    update_by bigint NOT NULL REFERENCES sispj_user(id),
    deleted boolean NOT NULL DEFAULT false,
    version_number varchar(10) NOT NULL DEFAULT 'V-00001',
	CONSTRAINT process_type_pkey PRIMARY KEY (id)
);
CREATE UNIQUE index if not exists process_type_published_idx on process_type(name) where publish_date is null and deleted = false;
CREATE UNIQUE index if not exists process_type_version_number_idx on process_type(name, version_number);

CREATE TABLE process_type_document_type (
    process_type_id bigint NOT NULL REFERENCES process_type(id),
	document_type_id bigint NOT NULL REFERENCES document_type(id)
);

CREATE TABLE process_type_process_step_type (
    process_type_id bigint NOT NULL REFERENCES process_type(id),
	process_step_type_id bigint NOT NULL REFERENCES process_step_type(id),
	order_number bigint NULL
);

--changeset initial:1.5
CREATE TABLE customer (
	id bigint NOT NULL DEFAULT nextval('customer_seq'),
	name varchar(255) NOT NULL,
	genre varchar(1) NULL,
	phone varchar(20) NOT NULL,
	address varchar(255) NOT NULL,
	address_postal_code varchar(50) NULL,
	address_number varchar(50) NULL,
	address_floor varchar(20) NULL,
	address_locality varchar(255) NULL,
	profession varchar(255) NOT NULL,
	marital_status_id bigint NOT NULL REFERENCES marital_status(id),
	country_id bigint NOT NULL REFERENCES country(id),
	qualification varchar(255) NULL,
	nif varchar(255) NULL,
	nif_password varchar(255) NULL,
	niss varchar(255) NULL,
    niss_password varchar(255) NULL,
    email_sapa varchar(255) NULL,
    email_sapa_password varchar(255) NULL,
    email_sef varchar(255) NULL,
    email_sef_password varchar(255) NULL,
    email varchar(255) UNIQUE NULL,
    whattsapp varchar(255) NULL,
    father_name varchar(255) NULL,
    mother_name varchar(255) NULL,
    observation varchar(1000) NULL,
    parish varchar(255) NULL,
    county varchar(255) NULL,
    first_additional_number varchar(255) NULL,
    first_additional_password varchar(255) NULL,
    first_additional_observation varchar(255) NULL,
    second_additional_number varchar(255) NULL,
    second_additional_password varchar(255) NULL,
    second_additional_observation varchar(255) NULL,
	version int4,
	create_date timestamp without time zone NOT NULL,
	create_by bigint NOT NULL REFERENCES sispj_user(id),
	update_date timestamp without time zone NOT NULL,
	update_by bigint NOT NULL REFERENCES sispj_user(id),
	CONSTRAINT customer_pkey PRIMARY KEY (id)
);
CREATE UNIQUE index if not exists customer_nif_idx on customer(nif) where nif is not null;
CREATE UNIQUE index if not exists customer_niss_idx on customer(niss) where niss is not null;

CREATE TABLE customer_note (
    id bigint NOT NULL DEFAULT nextval('customer_note_seq'),
    customer_id bigint NOT NULL REFERENCES customer(id),
    description varchar(2000) NOT NULL,
    version int4,
    create_date timestamp without time zone NOT NULL,
    create_by bigint NOT NULL REFERENCES sispj_user(id),
    update_date timestamp without time zone NOT NULL,
    update_by bigint NOT NULL REFERENCES sispj_user(id),
    CONSTRAINT customer_note_pkey PRIMARY KEY (id)
);

CREATE TABLE customer_document (
    id bigint NOT NULL DEFAULT nextval('customer_document_seq'),
    customer_id bigint NOT NULL REFERENCES customer(id),
    document_type_id bigint NOT NULL REFERENCES document_type(id),
    name varchar(200) NULL,
    description varchar(200) NULL,
    key_path varchar(500) NULL,
    version int4,
    create_date timestamp without time zone NOT NULL,
    create_by bigint NOT NULL REFERENCES sispj_user(id),
    update_date timestamp without time zone NOT NULL,
    update_by bigint NOT NULL REFERENCES sispj_user(id),
    CONSTRAINT customer_document_pkey PRIMARY KEY (id)
);

CREATE TABLE customer_process_type (
    id bigint NOT NULL DEFAULT nextval('customer_process_type_seq'),
    customer_id bigint NOT NULL REFERENCES customer(id),
    process_type_id bigint NOT NULL REFERENCES process_type(id),
    status varchar(50) NOT NULL,
    archive_path varchar(255) NULL,
    start_date timestamp without time zone NULL,
    end_date timestamp without time zone NULL,
    cancellation_reason varchar(500) NULL,
    version int4,
    create_date timestamp without time zone NOT NULL,
    create_by bigint NOT NULL REFERENCES sispj_user(id),
    update_date timestamp without time zone NOT NULL,
    update_by bigint NOT NULL REFERENCES sispj_user(id),
    CONSTRAINT customer_process_type_pkey PRIMARY KEY (id)
);

CREATE TABLE customer_process_type_note (
    id bigint NOT NULL DEFAULT nextval('customer_process_type_note_seq'),
    customer_process_type_id bigint NOT NULL REFERENCES customer_process_type(id),
    description varchar(2000) NOT NULL,
    version int4,
    create_date timestamp without time zone NOT NULL,
    create_by bigint NOT NULL REFERENCES sispj_user(id),
    update_date timestamp without time zone NOT NULL,
    update_by bigint NOT NULL REFERENCES sispj_user(id),
    CONSTRAINT customer_process_type_note_pkey PRIMARY KEY (id)
);

CREATE TABLE customer_process_step (
    id bigint NOT NULL DEFAULT nextval('customer_process_step_seq'),
    customer_process_type_id bigint NOT NULL REFERENCES customer_process_type(id),
    process_step_type_id bigint NOT NULL REFERENCES process_step_type(id),
    status varchar(50) NOT NULL,
    step_order bigint NOT NULL,
    start_date timestamp without time zone NULL,
    end_date timestamp without time zone NULL,
    version int4,
    create_date timestamp without time zone NOT NULL,
    create_by bigint NOT NULL REFERENCES sispj_user(id),
    update_date timestamp without time zone NOT NULL,
    update_by bigint NOT NULL REFERENCES sispj_user(id),
    CONSTRAINT customer_process_step_pkey PRIMARY KEY (id)
);

CREATE TABLE customer_process_document (
    id bigint NOT NULL DEFAULT nextval('customer_process_document_seq'),
    customer_process_type_id bigint NOT NULL REFERENCES customer_process_type(id),
    document_type_id bigint NOT NULL REFERENCES document_type(id),
    status varchar(50) NOT NULL,
    check_date timestamp without time zone NULL,
    version int4,
    create_date timestamp without time zone NOT NULL,
    create_by bigint NOT NULL REFERENCES sispj_user(id),
    update_date timestamp without time zone NOT NULL,
    update_by bigint NOT NULL REFERENCES sispj_user(id),
    CONSTRAINT customer_process_document_pkey PRIMARY KEY (id)
);

CREATE TABLE customer_provision (
    id bigint NOT NULL DEFAULT nextval('customer_provision_seq'),
    customer_id bigint NOT NULL REFERENCES customer(id),
    customer_process_type_id bigint NULL REFERENCES customer_process_type(id),
    description varchar(100) NULL,
    type varchar(20) NOT NULL,
    service_value numeric(12, 2) NULL,
    payment_date timestamp without time zone NULL,
    payment_value numeric(12, 2) NULL,
    version int4,
    create_date timestamp without time zone NOT NULL,
    create_by bigint NOT NULL REFERENCES sispj_user(id),
    update_date timestamp without time zone NOT NULL,
    update_by bigint NOT NULL REFERENCES sispj_user(id),
    CONSTRAINT customer_provision_pkey PRIMARY KEY (id)
);

--changeset initial:1.6
CREATE TABLE budget (
	id bigint NOT NULL DEFAULT nextval('budget_seq'),
	name varchar(255) NOT NULL,
	request_description varchar(2000) NOT NULL,
	service_description varchar(2000) NOT NULL,
	documents_description varchar(2000) NULL,
	comments varchar(2000) NULL,
	phone varchar(50) NOT NULL,
	address varchar(100) NULL,
	nif varchar(20) NULL,
	niss varchar(20) NULL,
	expiration_date timestamp without time zone NOT NULL,
	version int4,
	create_date timestamp without time zone NOT NULL,
	create_by bigint NOT NULL REFERENCES sispj_user(id),
	update_date timestamp without time zone NOT NULL,
	update_by bigint NOT NULL REFERENCES sispj_user(id),
	CONSTRAINT budget_pkey PRIMARY KEY (id)
);

CREATE TABLE budget_item (
	id bigint NOT NULL DEFAULT nextval('budget_item_seq'),
	budget_id bigint NOT NULL REFERENCES budget(id),
	request varchar(255) NOT NULL,
	value numeric(12, 2) NOT NULL,
	iva numeric(12, 2) NULL,
	order_number bigint NOT NULL,
	CONSTRAINT budget_item_pkey PRIMARY KEY (id)
);

--changeset initial:1.7
CREATE TABLE activity (
	id bigint NOT NULL DEFAULT nextval('activity_seq'),
	customer_id bigint NOT NULL REFERENCES customer(id),
	customer_process_type_id bigint NULL REFERENCES customer_process_type(id),
	type varchar(100) NOT NULL,
	description varchar(2000) NOT NULL,
    version int4,
    create_date timestamp without time zone NOT NULL,
    create_by bigint NOT NULL REFERENCES sispj_user(id),
    update_date timestamp without time zone NOT NULL,
    update_by bigint NOT NULL REFERENCES sispj_user(id),
	CONSTRAINT activity_pkey PRIMARY KEY (id)
);

--changeset initial:1.8
CREATE TABLE contact (
	id bigint NOT NULL DEFAULT nextval('contact_seq'),
	name varchar(100) UNIQUE NOT NULL,
	phone varchar(50) NULL,
	email varchar(100) NULL,
    version int4,
    create_date timestamp without time zone NOT NULL,
    create_by bigint NOT NULL REFERENCES sispj_user(id),
    update_date timestamp without time zone NOT NULL,
    update_by bigint NOT NULL REFERENCES sispj_user(id),
	CONSTRAINT contact_pkey PRIMARY KEY (id)
);

--changeset initial:1.9
create sequence refresh_token_seq start 1 increment 1;

CREATE TABLE refresh_token (
	id bigint NOT NULL DEFAULT nextval('refresh_token_seq'),
	token varchar(200) NOT NULL,
	user_id bigint NOT NULL REFERENCES sispj_user(id),
	expiry_date timestamp without time zone NOT NULL,
	CONSTRAINT refresh_token_pkey PRIMARY KEY (id)
);