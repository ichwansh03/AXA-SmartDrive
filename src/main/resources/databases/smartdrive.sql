CREATE DATABASE SmartDrive
GO
USE SmartDrive
GO
CREATE SCHEMA  mtr AUTHORIZATION dbo;

go


CREATE TABLE mtr.category (
    cate_id INT IDENTITY(1,1) PRIMARY KEY,
    cate_name VARCHAR(55)
);


CREATE TABLE mtr.insurance_type (
    inty_name VARCHAR(25) PRIMARY KEY,
    inty_desc VARCHAR(55)
);

CREATE TABLE mtr.zones (
    zones_id INT IDENTITY(1,1) PRIMARY KEY,
    zones_name VARCHAR(55)
);

CREATE TABLE mtr.template_type (
    tety_id INT IDENTITY(1,1) PRIMARY KEY,
    tety_name VARCHAR(25) UNIQUE,
    tety_group VARCHAR(15) UNIQUE
);

CREATE TABLE mtr.car_brands (
    cabr_id INT IDENTITY(1,1) PRIMARY KEY,
    cabr_name VARCHAR(55) UNIQUE
);

CREATE TABLE mtr.car_models (
    carm_id INT IDENTITY(1,1) PRIMARY KEY,
    carm_name VARCHAR(55) UNIQUE,
    carm_cabr_id INT,
    FOREIGN KEY (carm_cabr_id) REFERENCES mtr.car_brands(cabr_id)
);

CREATE TABLE mtr.car_series (
    cars_id INT IDENTITY(1,1) PRIMARY KEY,
    cars_name VARCHAR(55) UNIQUE,
    cars_passenger INT,
    cars_carm_id INT,
    FOREIGN KEY (cars_carm_id) REFERENCES mtr.car_models(carm_id)
);

CREATE TABLE mtr.template_service_task (
    testa_id INT IDENTITY(1,1) PRIMARY KEY,
    testa_name VARCHAR(55),
    testa_group INT,
    FOREIGN KEY (testa_group) REFERENCES mtr.template_type(tety_id)
);

CREATE TABLE mtr.template_task_workorder (
    tewo_id INT IDENTITY(1,1) PRIMARY KEY,
    tewo_name VARCHAR(55),
    tewo_testa_id INT,
    FOREIGN KEY (tewo_testa_id) REFERENCES mtr.template_service_task(testa_id)
);

CREATE TABLE mtr.provinsi (
    prov_id INT IDENTITY(1,1) PRIMARY KEY,
    prov_name VARCHAR(85) UNIQUE,
    prov_zones_id INT,
    FOREIGN KEY (prov_zones_id) REFERENCES mtr.zones(zones_id)
);

CREATE TABLE mtr.cities (
    city_id INT IDENTITY(1,1) PRIMARY KEY,
    city_name VARCHAR(85) UNIQUE,
    city_prov_id INT,
    FOREIGN KEY (city_prov_id) REFERENCES mtr.provinsi(prov_id)
);

CREATE TABLE mtr.region_plat (
    regp_name VARCHAR(3) PRIMARY KEY,
    regp_desc VARCHAR(35),
    regp_prov_id INT,
    FOREIGN KEY (regp_prov_id) REFERENCES mtr.provinsi(prov_id)
);

CREATE TABLE mtr.area_workgroup (
    arwg_code VARCHAR(15) PRIMARY KEY,
    arwg_desc VARCHAR(55),
    arwg_city_id INT,
    FOREIGN KEY (arwg_city_id) REFERENCES mtr.cities(city_id)
);

CREATE TABLE mtr.template_insurance_premi (
    temi_id INT IDENTITY(1,1) PRIMARY KEY,
    temi_name VARCHAR(256),
    temi_rate_min FLOAT,
    temi_rate_max FLOAT,
    temi_nominal float,
    temi_type VARCHAR(15) check(temi_type IN ('Category','Extend')),
    temi_zones_id INT,
    temi_inty_name VARCHAR(25),
    temi_cate_id INT,
    FOREIGN KEY (temi_zones_id) REFERENCES mtr.zones(zones_id),
    FOREIGN KEY (temi_inty_name) REFERENCES mtr.insurance_type(inty_name),
    FOREIGN KEY (temi_cate_id) REFERENCES mtr.category(cate_id)
);
go
CREATE SCHEMA users AUTHORIZATION dbo;
GO

CREATE TABLE users.business_entity(
	entityid INT IDENTITY(1,1) PRIMARY KEY,
	entity_modified_date DATETIME NOT NULL,
	)

CREATE TABLE users.users(
	user_entityid INT PRIMARY KEY,
	user_name VARCHAR(15) UNIQUE NOT NULL,
	user_password VARCHAR(256),
	user_full_name VARCHAR(85),
	user_email VARCHAR(25) NOT NULL,
	user_birth_place VARCHAR(55),
	user_birth_date DATETIME,
	user_national_id VARCHAR(20) UNIQUE NOT NULL,
	user_npwp VARCHAR(35) UNIQUE,
	user_photo VARCHAR(256),
	user_modified_date DATETIME,
	FOREIGN KEY(user_entityid) REFERENCES users.business_entity(entityid)
	)

CREATE TABLE users.user_phone(
	usph_entityid INT,
	usph_phone_number VARCHAR(15),
	usph_phone_type VARCHAR(15) CHECK (usph_phone_type IN ('HP', 'HOME')),
	usph_mime VARCHAR(512),
	usph_status VARCHAR(15),
	usph_modified_date DATETIME,

	CONSTRAINT pk_entity_phone PRIMARY KEY(usph_entityid,usph_phone_number),
	CONSTRAINT fk_entityid_phone FOREIGN KEY(usph_entityid)
	REFERENCES users.users(user_entityid)
	)

CREATE TABLE users.user_address(
	usdr_id INT,
	usdr_entityid INT,
	usdr_address1 VARCHAR(255),
	usdr_address2 VARCHAR(255),
	usdr_modified_date DATETIME,
	usdr_city_id INT,
	CONSTRAINT pk_entity_address PRIMARY KEY(usdr_id, usdr_entityid),
	CONSTRAINT fk_entity_address_users FOREIGN KEY(usdr_entityid)
		REFERENCES users.users(user_entityid),
	CONSTRAINT fk_address_cities FOREIGN KEY(usdr_city_id)
		REFERENCES mtr.cities(city_id)
	)

CREATE TABLE users.roles(
	role_name CHAR(2) NOT NULL,
	role_description VARCHAR(35) NOT NULL,
	CONSTRAINT pk_roles PRIMARY KEY(role_name)
)

CREATE TABLE users.user_roles(
	usro_entityid INTEGER,
	usro_role_name CHAR(2),
	usro_status VARCHAR(15) CHECK(usro_status IN ('ACTIVE','INACTIVE')),
	usro_modified_date DATETIME,
	CONSTRAINT pk_usro PRIMARY KEY(usro_entityid,usro_role_name),
	CONSTRAINT fk_entity_usro_users FOREIGN KEY(usro_entityid)
		REFERENCES users.users(user_entityid),
	FOREIGN KEY(usro_role_name) REFERENCES users.roles(role_name)
)
go

CREATE SCHEMA hr AUTHORIZATION dbo;

GO

CREATE TABLE hr.job_type(
job_code VARCHAR(15) PRIMARY KEY,
job_modified_date DATETIME
);

CREATE TABLE hr.batch_employee_salary(
besa_emp_entity_id INT ,
besa_created_date DATE ,
ems_trasfer_Date DATETIME,
besa_total_salary MONEY,
besa_account_number VARCHAR(35),
besa_status varchar(15),
besa_patr_trxno VARCHAR(55),
besa_paid_date DATETIME,
besa_modified_date DATETIME,
PRIMARY KEY (besa_emp_entity_id,besa_created_date)
)

CREATE TABLE hr.employees(
emp_entityid INT PRIMARY KEY,
emp_name VARCHAR(85),
emp_join_date DATETIME,
emp_type VARCHAR(15),
emp_status VARCHAR(15),
emp_graduate VARCHAR(15),
emp_net_salary MONEY,
emp_account_number VARCHAR(15),
emp_modified_date DATETIME,
emp_job_code VARCHAR(15),
FOREIGN KEY (emp_entityid) REFERENCES users.users(user_entityid),
FOREIGN KEY (emp_job_code) REFERENCES hr.job_type(job_code)
)

CREATE TABLE hr.employee_are_workgroup (
    eawag_id INT,
    eawg_entityid INT,
    eawg_status VARCHAR(15),
    eawg_arwg_code VARCHAR(15),
    eawg_modified_date DATETIME,
    PRIMARY KEY(eawg_entityid, eawag_id),
    FOREIGN KEY (eawg_entityid) REFERENCES hr.employees(emp_entityid),
	FOREIGN KEY (eawg_arwg_code) REFERENCES mtr.area_workgroup(arwg_code)
);

CREATE TABLE hr.employee_salary_detail(
emsa_id INT,
emsa_emp_entityid INT,
emsa_create_date DATE,
emsa_name VARCHAR(55),
emsa_subtotal MONEY
PRIMARY KEY(emsa_id,emsa_emp_entityid,emsa_create_date),
FOREIGN KEY (emsa_emp_entityid) REFERENCES hr.employees(emp_entityid)
)

CREATE TABLE hr.template_salary(
tesal_id INT PRIMARY KEY,
tesal_name VARCHAR(55) UNIQUE,
tesal_nominal MONEY,
tesal_rate_min FLOAT,
tesal_rate_max FLOAT
)
go
CREATE SCHEMA customer AUTHORIZATION dbo;

go

CREATE TABLE customer.customer_request(
	creq_entityid int PRIMARY KEY,
	creq_create_date DATETIME,
	creq_status VARCHAR(15),
	creq_type VARCHAR(15),
	creq_modified_date DATETIME,
	creq_cust_entityid int,
	creq_agen_entityid int,
	CONSTRAINT FK_CREQENTITY FOREIGN KEY(creq_cust_entityid)
	REFERENCES users.users(user_entityid),
	CONSTRAINT FK_CREQCUST_ENTITY FOREIGN KEY(creq_entityid)
	REFERENCES users.business_entity(entityId),
	CONSTRAINT FK_CREQAGEN FOREIGN KEY(creq_agen_entityid)
	REFERENCES hr.employees(emp_entityid)
)

CREATE TABLE customer.customer_claim(
	cucl_creq_entityid int PRIMARY KEY,
	cucl_create_date DATETIME,
	cucl_event_price money,
	cucl_subtotal money,
	cucl_reason VARCHAR(256),
	CONSTRAINT FK_CUCLCREQ FOREIGN KEY(cucl_creq_entityid)
	REFERENCES customer.customer_request(creq_entityid)
)

CREATE TABLE customer.customer_insc_assets(
	cias_creq_entityid int IDENTITY PRIMARY KEY,
	cias_police_number VARCHAR(15) UNIQUE NOT NULL,
	cias_year VARCHAR(4) NOT NULL,
	cias_startdate DATETIME,
	cias_enddate DATETIME,
	cias_current_price money,
	cias_insurance_price money,
	cias_total_premi money,
	cias_paid_type VARCHAR(15),
	cias_isNewChar CHAR(1) CHECK(cias_isNewChar IN('Y', 'N')),
	cias_cars_id int,
	cias_inty_name VARCHAR(25),
	cias_city_id int,
	CONSTRAINT FK_CIASCREQ FOREIGN KEY(cias_creq_entityid)
	REFERENCES customer.customer_request(creq_entityid),
	CONSTRAINT FK_CIASCARS FOREIGN KEY(cias_cars_id)
	REFERENCES mtr.car_series(cars_id),
	CONSTRAINT FK_CIASINTY FOREIGN KEY(cias_inty_name)
	REFERENCES mtr.insurance_type(inty_name),
	CONSTRAINT FK_CIASCITY FOREIGN KEY(cias_city_id)
	REFERENCES mtr.cities(city_id)
)

CREATE TABLE customer.customer_insc_doc(
	cadoc_id int IDENTITY,
	cadoc_creq_entityid int,
	cadoc_filename VARCHAR(15),
	cadoc_filetype VARCHAR(15),
	cadoc_filesize int,
	cadoc_category VARCHAR(15),
	cadoc_modified_date DATETIME,
	CONSTRAINT PK_CADOC PRIMARY KEY(cadoc_id, cadoc_creq_entityid),
	CONSTRAINT FK_CADOCCREQ FOREIGN KEY(cadoc_creq_entityid)
	REFERENCES customer.customer_insc_assets(cias_creq_entityid)
)



CREATE TABLE customer.customer_insc_extend(
	cuex_id int IDENTITY,
	cuex_creq_entityid int,
	cuex_name VARCHAR(256),
	cuex_total_item int,
	cuex_nominal money,
	CONSTRAINT PK_CUEX PRIMARY KEY(cuex_id, cuex_creq_entityid),
	CONSTRAINT FK_CUEXCREQ FOREIGN KEY(cuex_creq_entityid)
	REFERENCES customer.customer_insc_assets(cias_creq_entityid)
)
go
CREATE SCHEMA payment;
go

CREATE TABLE PAYMENT.banks(
	bank_entityid INT,
	bank_name VARCHAR(5) UNIQUE,
	bank_desc VARCHAR(55),
	CONSTRAINT pk_bank_entityid PRIMARY KEY(bank_entityid),
	CONSTRAINT fk_bank_entityid FOREIGN KEY(bank_entityid)
		REFERENCES USERS.business_entity(entityid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE PAYMENT.fintech(
	fint_entityid INT,
	fint_name VARCHAR(5) UNIQUE,
	fint_desc VARCHAR(55),
	CONSTRAINT pk_fint_entityid PRIMARY KEY(fint_entityid),
	CONSTRAINT fk_fint_entityid FOREIGN KEY(fint_entityid)
		REFERENCES USERS.business_entity(entityid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE PAYMENT.user_accounts(
	usac_id INT IDENTITY(1,1),
	usac_accountno VARCHAR(30) UNIQUE,
	usac_debet MONEY,
	usac_credit MONEY,
	usac_type VARCHAR(15) CHECK (usac_type IN ('BANK','FINTECH')),
	usac_bank_entityid INT,
	usac_fint_entityid INT,
	usac_user_entityid INT,
	CONSTRAINT pk_usac_id PRIMARY KEY(usac_id),
	CONSTRAINT fk_usac_bank_entityid FOREIGN KEY(usac_bank_entityid)
		REFERENCES PAYMENT.banks(bank_entityid) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_usac_fint_entityid FOREIGN KEY(usac_fint_entityid)
		REFERENCES PAYMENT.fintech(fint_entityid),
	CONSTRAINT fk_user_entityid FOREIGN KEY(usac_user_entityid)
		REFERENCES USERS.users(user_entityid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE PAYMENT.payment_transactions(
	patr_trxno VARCHAR(55),
	patr_created_on DATETIME,
	patr_debet MONEY,
	patr_credit MONEY,
	patr_usac_accountNo_from INT,
	patr_usac_accountNo_to INT,
	patr_type VARCHAR(15),
	patr_invoice_no VARCHAR(55),
	patr_notes VARCHAR(125),
	patr_trxno_rev VARCHAR(55)
	CONSTRAINT pk_patr_trxno PRIMARY KEY(patr_trxno),
	CONSTRAINT fk_patr_trxno_rev FOREIGN KEY(patr_trxno_rev)
		REFERENCES PAYMENT.payment_transactions(patr_trxno)
);
go
create schema so authorization dbo;
go

create table so.services(
	serv_id int not null identity(1,1),
	serv_created_on datetime,
	serv_type varchar(15),
    serv_insuranceNo varchar(12),
    serv_vehicleNo varchar(12),
    serv_startdate datetime,
    serv_enddate datetime,
	serv_status varchar(15) check (serv_status in ('ACTIVE', 'INACTIVE')),
	serv_serv_id integer,
	serv_cust_entityid integer,
    serv_creq_entityid integer,
	constraint pk_serv_id primary key (serv_id),
	constraint fk_serv_serv_id foreign key (serv_serv_id) references so.services(serv_id),
	constraint fk_serv_creq_entityid foreign key (serv_creq_entityid) references customer.customer_request(creq_entityid),
	constraint fk_serv_cust_entityid foreign key (serv_cust_entityid) references users.users(user_entityid)
)




go

CREATE SCHEMA partners AUTHORIZATION dbo;

GO

CREATE TABLE partners.partners(
	part_entityid INT NOT NULL,
	part_name VARCHAR(25),
	part_address VARCHAR(255),
	part_join_date DATETIME,
	part_accountNo VARCHAR(35),
	part_npwp VARCHAR(25),
	part_status VARCHAR(15),
	part_modified_date DATETIME,
	part_city_id INT NOT NULL,
	CONSTRAINT pk_part_entityid PRIMARY KEY (part_entityid),
	CONSTRAINT fk_part_entityid FOREIGN KEY (part_entityid)
		REFERENCES users.business_entity (entityid) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_part_city_id FOREIGN KEY (part_city_id)
		REFERENCES mtr.cities (city_id) ON DELETE CASCADE ON UPDATE CASCADE
)


CREATE TABLE partners.partner_contacts(
	paco_patrn_entityid INT NOT NULL,
	paco_user_entityid INT NOT NULL,
	paco_status VARCHAR(15),
	CONSTRAINT pk_paco_patrn_user PRIMARY KEY (paco_patrn_entityid, paco_user_entityid),
	CONSTRAINT fk_paco_patrn_entityid FOREIGN KEY (paco_patrn_entityid)
		REFERENCES partners.partners (part_entityid) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_paco_user_entityid FOREIGN KEY (paco_user_entityid)
		REFERENCES users.users (user_entityid) ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE partners.partner_area_workgroup(
	pawo_patr_entityid INT NOT NULL,
	pawo_arwg_code VARCHAR(15) NOT NULL,
	pawo_user_entityid INT NOT NULL,
	pawo_status VARCHAR(15),
	pawo_modified_date DATETIME,
	CONSTRAINT pk_pawo_patr_arwg_user PRIMARY KEY (pawo_patr_entityid, pawo_arwg_code, pawo_user_entityid),
	CONSTRAINT fk_pawo_arwg_code FOREIGN KEY (pawo_arwg_code)
		REFERENCES mtr.area_workgroup(arwg_code) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_pawo_patr_user FOREIGN KEY (pawo_patr_entityid, pawo_user_entityid)
		REFERENCES partners.partner_contacts(paco_patrn_entityid, paco_user_entityid) ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE partners.batch_partner_invoice(
	bpin_invoiceNo VARCHAR(30),
	bpin_created_on DATETIME,
	bpin_subtotal MONEY,
	bpin_tax MONEY,
	bpin_accountNo VARCHAR(30),
	bpin_status VARCHAR(15),
	bpin_paid_date DATETIME,
	bpin_serv_id INT,
	bpin_patrn_entityid INT,
	bpin_patr_trxno VARCHAR(55),
	CONSTRAINT pk_bpin_invoiceNo PRIMARY KEY (bpin_invoiceNo),
	CONSTRAINT fk_bpin_serv_id FOREIGN KEY (bpin_serv_id)
		REFERENCES so.services(serv_id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_bpin_patrn_entityid FOREIGN KEY (bpin_patrn_entityid)
		REFERENCES partners.partners(part_entityid) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_bpin_patr_trxno FOREIGN KEY (bpin_patr_trxno)
		REFERENCES PAYMENT.payment_transactions(patr_trxno) ON DELETE CASCADE ON UPDATE CASCADE
)



create table so.service_orders(
	sero_id varchar(25),
	sero_ordt_type varchar(15),
    sero_status varchar(15),
    sero_reason varchar(256),
    serv_claim_no varchar(12),
    serv_claim_startdate datetime,
    serv_claim_enddate datetime,
	sero_serv_id integer,
    sero_sero_id varchar(25),
    sero_agent_entityid integer,
    sero_arwg_code varchar(15),
	constraint pk_sero_id primary key(sero_id),
	constraint fk_sero_serv_id foreign key (sero_serv_id) references so.services(serv_id),
	constraint fk_sero_sero_id foreign key (sero_sero_id) references so.service_orders(sero_id),
	constraint fk_sero_arwg_code foreign key (sero_arwg_code) references mtr.area_workgroup(arwg_code),
	constraint fk_sero_agent_entityid foreign key (sero_agent_entityid) references hr.employees(emp_entityid)
)

create table so.service_premi(
	semi_serv_id integer,
	semi_premi_debet money,
    semi_premi_credit money,
    semi_paid_type varchar(15),
    semi_status varchar(15),
	semi_modified_date datetime,
	constraint pk_semi_serv_id primary key (semi_serv_id),
	constraint fk_semi_serv_id foreign key (semi_serv_id) references so.services(serv_id)
)

create table so.service_premi_credit(
	secr_id integer not null identity,
	secr_serv_id integer,
	secr_year datetime,
	secr_premi_debet money,
    secr_premi_credit money,
    secr_trx_date datetime,
    secr_duedate datetime,
	secr_patr_trxno varchar(55),
	constraint pk_secr primary key (secr_id, secr_serv_id),
	constraint fk_secr_serv_id foreign key (secr_serv_id) references so.services(serv_id),
	--BUGFIX
	--constraint fk_secr_year foreign key (secr_year) references so.service_premi(semi_modified_date),
	constraint fk_secr_patr_trxno foreign key (secr_patr_trxno) references payment.payment_transactions(patr_trxno)
)

create table so.service_order_tasks(
	seot_id integer not null identity(1,1),
	seot_name varchar(256),
    seot_startdate datetime,
    seot_enddate datetime,
    seot_actual_startdate datetime,
    seot_actual_enddate datetime,
    seot_status varchar(15),
	seot_arwg_code varchar(15),
	seot_sero_id varchar(25),
	constraint pk_seot_id primary key (seot_id),
	constraint fk_seot_arwg_code foreign key (seot_arwg_code) references mtr.area_workgroup(arwg_code),
	constraint fk_seot_sero_id  foreign key (seot_sero_id) references so.service_orders(sero_id)
)

create table so.service_order_workorder(
	sowo_id integer not null identity(1,1),
	sowo_name varchar(256),
    sowo_modified_date datetime,
    sowo_status varchar(15),
	sowo_seot_id integer,
	constraint pk_sowo_id primary key (sowo_id),
	constraint fk_sowo_seot_id foreign key (sowo_seot_id) references so.service_order_tasks(seot_id)
)

create table so.claim_asset_evidence(
	caev_id integer not null identity(1,1),
	caev_filename varchar(55),
    caev_filesize integer,
    caev_filetype varchar(15),
    caev_url varchar(255),
    caev_note varchar(15),
	caev_part_entityid integer,
    caev_sero_id varchar(25),
	constraint pk_caev_id primary key (caev_id),
	constraint fk_caev_part_entityid foreign key (caev_part_entityid) references partners.partners(part_entityid),
	constraint fk_caev_sero_id foreign key (caev_sero_id) references so.service_orders(sero_id)
)

create table so.claim_asset_sparepart(
	casp_id integer not null identity(1,1),
	casp_item_name varchar(55),
    casp_quantity integer,
    casp_item_price money,
    casp_subtotal money,
	casp_part_entityid integer,
    casp_sero_id varchar(25),
	constraint pk_casp_id primary key (casp_id),
	constraint fk_casp_part_entityid foreign key (casp_part_entityid) references partners.partners(part_entityid),
	constraint fk_casp_sero_id foreign key (casp_sero_id) references so.service_orders(sero_id)
)