
CREATE DATABASE SmartDrive_Payment;

USE SmartDrive_Payment;


CREATE SCHEMA PAYMENT;
GO


-------------------- PAYMENT ------------------------------

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
	usac_id INT IDENTITY,
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



