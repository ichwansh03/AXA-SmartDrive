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
		REFERENCES partners.partners(part_entityid) ON DELETE CASCADE ON UPDATE CASCADE
	CONSTRAINT fk_bpin_patr_trxno FOREIGN KEY (bpin_patr_trxno)
		REFERENCES PAYMENT.payment_transactions(patr_trxno) ON DELETE CASCADE ON UPDATE CASCADE
)

