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

create table so.service_orders(
	sero_id varchar(25),
	sero_ordt_type varchar(15),
    sero_status varchar(15),
    sero_reason varchar(256),
    serv_claim_no varchar(12),
    serv_claim_startdate datetime,
    serv_claim_enddate datetime,
	sero_serv_id integer,
    sero_sero_id integer,
    sero_agent_entityid integer,
    sero_arwg_code varchar(15),
	constraint pk_sero_id primary key(sero_id),
	constraint fk_sero_serv_id foreign key (sero_serv_id) references so.services(serv_id),
	constraint fk_sero_sero_id foreign key (sero_sero_id) references so.service_orders(sero_id),
	constraint fk_sero_arwg_code foreign key (arwg_code) references mtr.area_workgroup(arwg_code),
	--STILL ASKING!
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
	secr_year varchar(4),
	secr_premi_debet money,
    secr_premi_credit money,
    secr_trx_date datetime,
    secr_duedate datetime,
	secr_patr_trxno varchar(55),
	constraint pk_secr primary key (secr_id, secr_serv_id, secr_year),
	constraint fk_secr_serv_id foreign key (secr_serv_id) references so.services(serv_id),
	--STILL ASKING!
	constraint fk_secr_year foreign key (secr_year) references so.service_premi(semi_modified_date),
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
	seot_sero_id integer,
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
    caev_sero_id integer,
	constraint pk_caev_id primary key (caev_id),
	constraint fk_caev_part_entityid foreign key (caev_part_entityid) references partner.partners(part_entityid),
	constraint fk_caev_sero_id foreign key (caev_sero_id) references so.service_orders(sero_id)
)

create table so.claim_asset_sparepart(
	casp_id integer not null identity(1,1),
	casp_item_name varchar(55),
    casp_quantity integer,
    casp_item_price money,
    casp_subtotal money,
	casp_part_entityid integer,
    casp_sero_id integer,
	constraint pk_casp_id primary key (casp_id),
	constraint fk_casp_part_entityid foreign key (casp_part_entityid) references partner.partners(part_entityid),
	constraint fk_casp_sero_id foreign key (casp_sero_id) references so.service_orders(sero_id)
)