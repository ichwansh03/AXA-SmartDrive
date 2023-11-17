
CREATE SCHEMA hr AUTHORIZATION dbo;

go



CREATE TABLE hr.job_type(
job_code VARCHAR(15) PRIMARY KEY,
job_modified_date DATETIME
);



CREATE TABLE hr.batch_employee_salary(
besa_emp_entity_id INT IDENTITY,
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
emp_entityid INT  IDENTITY PRIMARY KEY,
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
    eawag_id INT IDENTITY,
    eawg_entityid INT,
    eawg_status VARCHAR(15),
    eawg_arwg_code VARCHAR(15),
    eawg_modified_date DATETIME,
    PRIMARY KEY(eawg_entityid, eawag_id),
    FOREIGN KEY (eawg_entityid) REFERENCES hr.employees(emp_entityid),
	FOREIGN KEY (eawg_arwg_code) REFERENCES mtr.area_workgroup(arwg_code)
);



CREATE TABLE hr.employee_salary_detail(
emsa_id INT IDENTITY,
emsa_emp_entityid INT,
emsa_create_date DATE,
emsa_name VARCHAR(55),
emsa_subtotal MONEY
PRIMARY KEY(emsa_id,emsa_emp_entityid,emsa_create_date),
FOREIGN KEY (emsa_emp_entityid) REFERENCES hr.employees(emp_entityid)
)

CREATE TABLE hr.template_salary(
tesal_id INT IDENTITY(1,1),
tesal_name VARCHAR(55) UNIQUE,
tesal_nominal MONEY,
tesal_rate_min FLOAT,
tesal_rate_max FLOAT
)

