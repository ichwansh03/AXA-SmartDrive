




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
