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