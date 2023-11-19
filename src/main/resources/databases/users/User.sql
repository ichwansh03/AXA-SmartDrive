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