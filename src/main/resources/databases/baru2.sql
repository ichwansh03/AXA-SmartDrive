USE [SmartDrive]
GO
SET IDENTITY_INSERT [users].[business_entity] ON 

INSERT [users].[business_entity] ([entityid], [entity_modified_date]) VALUES (1, CAST(N'2023-05-07T00:00:00.000' AS DateTime))
INSERT [users].[business_entity] ([entityid], [entity_modified_date]) VALUES (2, CAST(N'2023-10-10T00:00:00.000' AS DateTime))
INSERT [users].[business_entity] ([entityid], [entity_modified_date]) VALUES (4, CAST(N'2023-12-01T11:13:14.820' AS DateTime))
INSERT [users].[business_entity] ([entityid], [entity_modified_date]) VALUES (5, CAST(N'2023-12-06T12:53:11.943' AS DateTime))
INSERT [users].[business_entity] ([entityid], [entity_modified_date]) VALUES (6, CAST(N'2023-12-06T13:55:55.987' AS DateTime))
INSERT [users].[business_entity] ([entityid], [entity_modified_date]) VALUES (7, CAST(N'2023-12-06T18:26:50.587' AS DateTime))
INSERT [users].[business_entity] ([entityid], [entity_modified_date]) VALUES (1011, CAST(N'2023-12-07T14:25:42.423' AS DateTime))
INSERT [users].[business_entity] ([entityid], [entity_modified_date]) VALUES (1021, CAST(N'2023-12-07T14:29:30.807' AS DateTime))
INSERT [users].[business_entity] ([entityid], [entity_modified_date]) VALUES (1026, CAST(N'2023-12-07T14:29:38.360' AS DateTime))
SET IDENTITY_INSERT [users].[business_entity] OFF
GO
INSERT [users].[users] ([user_entityid], [user_name], [user_password], [user_full_name], [user_email], [user_birth_place], [user_birth_date], [user_national_id], [user_npwp], [user_photo], [user_modified_date]) VALUES (1, N'bara', N'123', N'muhammadbara', N'bara@gmail.com', N'jakarta', CAST(N'2001-01-27T00:00:00.000' AS DateTime), N'malay', N'1234bara', NULL, CAST(N'2023-11-19T00:00:00.000' AS DateTime))
INSERT [users].[users] ([user_entityid], [user_name], [user_password], [user_full_name], [user_email], [user_birth_place], [user_birth_date], [user_national_id], [user_npwp], [user_photo], [user_modified_date]) VALUES (2, N'adi', N'321', N'adiadi', N'adi@gmail.com', N'jakarta', CAST(N'2001-10-10T00:00:00.000' AS DateTime), N'indo', N'1234adi', NULL, CAST(N'2023-10-10T00:00:00.000' AS DateTime))
INSERT [users].[users] ([user_entityid], [user_name], [user_password], [user_full_name], [user_email], [user_birth_place], [user_birth_date], [user_national_id], [user_npwp], [user_photo], [user_modified_date]) VALUES (1021, N'00000000000TEST', NULL, N'TEST', N'00000000000TEST@gmail.com', NULL, NULL, N'089999999998', N'089999999998', NULL, CAST(N'2023-12-07T14:29:30.813' AS DateTime))
GO
INSERT [hr].[job_type] ([job_code], [job_modified_date], [job_desc], [job_rate_min], [job_rate_max]) VALUES (N'1', CAST(N'2008-10-10T00:00:00.000' AS DateTime), NULL, NULL, NULL)
GO
INSERT [hr].[employees] ([emp_entityid], [emp_name], [emp_join_date], [emp_type], [emp_status], [emp_graduate], [emp_net_salary], [emp_account_number], [emp_modified_date], [emp_job_code]) VALUES (1, N'BARA', CAST(N'2009-10-10T00:00:00.000' AS DateTime), N'PERMANENT', N'ACTIVE', N'S1', 2000000.0000, N'BARA123', CAST(N'2023-11-19T00:00:00.000' AS DateTime), N'1')
GO
INSERT [customer].[customer_request] ([creq_entityid], [creq_create_date], [creq_status], [creq_type], [creq_modified_date], [creq_cust_entityid], [creq_agen_entityid]) VALUES (2, CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'OPEN', N'CLAIM', CAST(N'2023-10-10T00:00:00.000' AS DateTime), 2, 1)
GO
SET IDENTITY_INSERT [so].[services] ON 

INSERT [so].[services] ([serv_id], [serv_created_on], [serv_type], [serv_insuranceNo], [serv_vehicleNo], [serv_startdate], [serv_enddate], [serv_status], [serv_serv_id], [serv_cust_entityid], [serv_creq_entityid]) VALUES (1, CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'CLAIM', N'002-20231010', N'BE 2090 OE', CAST(N'2023-12-07T19:27:46.267' AS DateTime), CAST(N'2023-12-14T19:27:46.267' AS DateTime), N'ACTIVE', NULL, 2, 2)
SET IDENTITY_INSERT [so].[services] OFF
GO
SET IDENTITY_INSERT [mtr].[zones] ON 

INSERT [mtr].[zones] ([zones_id], [zones_name]) VALUES (1, N'Wilayah 1')
INSERT [mtr].[zones] ([zones_id], [zones_name]) VALUES (2, N'Wilayah 2')
INSERT [mtr].[zones] ([zones_id], [zones_name]) VALUES (3, N'Wilayah 3')
SET IDENTITY_INSERT [mtr].[zones] OFF
GO
SET IDENTITY_INSERT [mtr].[provinsi] ON 

INSERT [mtr].[provinsi] ([prov_id], [prov_name], [prov_zones_id]) VALUES (1, N'DKI Jakarta', 1)
INSERT [mtr].[provinsi] ([prov_id], [prov_name], [prov_zones_id]) VALUES (2, N'Jawa Barat', 2)
SET IDENTITY_INSERT [mtr].[provinsi] OFF
GO
SET IDENTITY_INSERT [mtr].[cities] ON 

INSERT [mtr].[cities] ([city_id], [city_name], [city_prov_id]) VALUES (1, N'Bandung', 2)
INSERT [mtr].[cities] ([city_id], [city_name], [city_prov_id]) VALUES (2, N'Bogor', 2)
INSERT [mtr].[cities] ([city_id], [city_name], [city_prov_id]) VALUES (3, N'Cianjur', 2)
INSERT [mtr].[cities] ([city_id], [city_name], [city_prov_id]) VALUES (4, N'Cirebon', 2)
INSERT [mtr].[cities] ([city_id], [city_name], [city_prov_id]) VALUES (5, N'Bekasi', 2)
SET IDENTITY_INSERT [mtr].[cities] OFF
GO
INSERT [mtr].[area_workgroup] ([arwg_code], [arwg_desc], [arwg_city_id]) VALUES (N'BCI-0001', N'Bandung Cimahi', 1)
INSERT [mtr].[area_workgroup] ([arwg_code], [arwg_desc], [arwg_city_id]) VALUES (N'BCY-0001', N'Bandung Cileunyi', 1)
INSERT [mtr].[area_workgroup] ([arwg_code], [arwg_desc], [arwg_city_id]) VALUES (N'BPA-0001', N'Bandung Padalarang', 1)
GO
INSERT [so].[service_orders] ([sero_id], [sero_ordt_type], [sero_status], [sero_reason], [serv_claim_no], [serv_claim_startdate], [serv_claim_enddate], [sero_serv_id], [sero_sero_id], [sero_agent_entityid], [sero_arwg_code], [sero_part_id]) VALUES (N'CL0001-20231010', NULL, N'OPEN', NULL, NULL, CAST(N'2023-12-07T19:27:46.267' AS DateTime), CAST(N'2024-12-07T19:27:46.267' AS DateTime), 1, NULL, NULL, NULL, NULL)
GO
INSERT [payment].[banks] ([bank_entityid], [bank_name], [bank_desc]) VALUES (1, N'BBARA', N'DESC')
GO
INSERT [payment].[fintech] ([fint_entityid], [fint_name], [fint_desc]) VALUES (2, N'FIADI', N'desc')
GO
INSERT [users].[roles] ([role_name], [role_description]) VALUES (N'CU', N'customers')
INSERT [users].[roles] ([role_name], [role_description]) VALUES (N'EM', N'employees')
INSERT [users].[roles] ([role_name], [role_description]) VALUES (N'PC', N'potential customer')
INSERT [users].[roles] ([role_name], [role_description]) VALUES (N'PR', N'partner')
GO
INSERT [users].[user_roles] ([usro_entityid], [usro_role_name], [usro_status], [usro_modified_date]) VALUES (1, N'EM', N'ACTIVE', CAST(N'2023-11-19T00:00:00.000' AS DateTime))
INSERT [users].[user_roles] ([usro_entityid], [usro_role_name], [usro_status], [usro_modified_date]) VALUES (2, N'CU', N'ACTIVE', CAST(N'2023-10-10T00:00:00.000' AS DateTime))
INSERT [users].[user_roles] ([usro_entityid], [usro_role_name], [usro_status], [usro_modified_date]) VALUES (1021, N'PR', N'ACTIVE', CAST(N'2023-12-07T14:29:30.817' AS DateTime))
GO
SET IDENTITY_INSERT [payment].[user_accounts] ON 

INSERT [payment].[user_accounts] ([usac_id], [usac_accountno], [usac_debet], [usac_credit], [usac_type], [usac_bank_entityid], [usac_fint_entityid], [usac_user_entityid]) VALUES (1, N'ACC', 10000.0000, 1000.0000, N'BANK', 1, 2, 1)
SET IDENTITY_INSERT [payment].[user_accounts] OFF
GO
INSERT [users].[user_address] ([usdr_id], [usdr_entityid], [usdr_address1], [usdr_address2], [usdr_modified_date], [usdr_city_id]) VALUES (1, 1, N'JAKARTA', N'JAKARTA', CAST(N'2023-10-10T00:00:00.000' AS DateTime), 2)
GO
INSERT [users].[user_phone] ([usph_entityid], [usph_phone_number], [usph_phone_type], [usph_mime], [usph_status], [usph_modified_date]) VALUES (1, N'123455677', N'HOME', N'WKWKWK', N'NORMAL', CAST(N'2023-10-10T00:00:00.000' AS DateTime))
INSERT [users].[user_phone] ([usph_entityid], [usph_phone_number], [usph_phone_type], [usph_mime], [usph_status], [usph_modified_date]) VALUES (1021, N'089999999998', NULL, NULL, NULL, CAST(N'2023-12-07T14:29:30.817' AS DateTime))
GO
SET IDENTITY_INSERT [mtr].[car_brands] ON 

INSERT [mtr].[car_brands] ([cabr_id], [cabr_name]) VALUES (1, N'Honda')
INSERT [mtr].[car_brands] ([cabr_id], [cabr_name]) VALUES (3, N'Mistubishi')
INSERT [mtr].[car_brands] ([cabr_id], [cabr_name]) VALUES (4, N'Suzuki')
INSERT [mtr].[car_brands] ([cabr_id], [cabr_name]) VALUES (2, N'Toyota')
SET IDENTITY_INSERT [mtr].[car_brands] OFF
GO
SET IDENTITY_INSERT [mtr].[car_models] ON 

INSERT [mtr].[car_models] ([carm_id], [carm_name], [carm_cabr_id]) VALUES (1, N'HRV', 1)
INSERT [mtr].[car_models] ([carm_id], [carm_name], [carm_cabr_id]) VALUES (2, N'BRIO', 1)
INSERT [mtr].[car_models] ([carm_id], [carm_name], [carm_cabr_id]) VALUES (3, N'Mobilio', 1)
SET IDENTITY_INSERT [mtr].[car_models] OFF
GO
SET IDENTITY_INSERT [mtr].[car_series] ON 

INSERT [mtr].[car_series] ([cars_id], [cars_name], [cars_passenger], [cars_carm_id]) VALUES (1, N'MT-A', 5, 1)
SET IDENTITY_INSERT [mtr].[car_series] OFF
GO
INSERT [mtr].[insurance_type] ([inty_name], [inty_desc]) VALUES (N'Comprehensive', N'Jaminan Tambahan Komprehensive')
INSERT [mtr].[insurance_type] ([inty_name], [inty_desc]) VALUES (N'Total Loss Only', N'Jaminan Tambahan Kerugian Total')
GO
INSERT [customer].[customer_insc_assets] ([cias_creq_entityid], [cias_police_number], [cias_year], [cias_startdate], [cias_enddate], [cias_current_price], [cias_insurance_price], [cias_total_premi], [cias_paid_type], [cias_isNewChar], [cias_cars_id], [cias_inty_name], [cias_city_id]) VALUES (2, N'BE 2090 OE', N'2021', CAST(N'2023-12-01T00:00:00.000' AS DateTime), CAST(N'2024-12-01T00:00:00.000' AS DateTime), 120000.0000, 400000.0000, 500000.0000, N'CREDIT', N'Y', 1, N'Comprehensive', 1)
GO
INSERT [customer].[customer_claim] ([cucl_creq_entityid], [cucl_create_date], [cucl_event_price], [cucl_subtotal], [cucl_reason], [cucl_events]) VALUES (2, CAST(N'2023-10-10T00:00:00.000' AS DateTime), 100000.0000, 100000.0000, N'wkwkwkwk', 0)
GO
INSERT [hr].[employee_are_workgroup] ([eawg_id], [eawg_entityid], [eawg_status], [eawg_arwg_code], [eawg_modified_date]) VALUES (1, 1, NULL, N'BCI-0001', CAST(N'2023-12-06T00:00:00.000' AS DateTime))
GO
SET IDENTITY_INSERT [so].[service_order_tasks] ON 

INSERT [so].[service_order_tasks] ([seot_id], [seot_name], [seot_startdate], [seot_enddate], [seot_actual_startdate], [seot_actual_enddate], [seot_status], [seot_arwg_code], [seot_sero_id]) VALUES (1, N'CHECK CUSTOMER PREMI', CAST(N'2023-12-07T19:27:46.267' AS DateTime), CAST(N'2023-12-08T19:27:46.267' AS DateTime), NULL, NULL, N'INPROGRESS', NULL, N'CL0001-20231010')
INSERT [so].[service_order_tasks] ([seot_id], [seot_name], [seot_startdate], [seot_enddate], [seot_actual_startdate], [seot_actual_enddate], [seot_status], [seot_arwg_code], [seot_sero_id]) VALUES (2, N'SETTLE CUSTOMER PREMI', CAST(N'2023-12-07T19:27:46.267' AS DateTime), CAST(N'2023-12-08T19:27:46.267' AS DateTime), NULL, NULL, N'INPROGRESS', NULL, N'CL0001-20231010')
INSERT [so].[service_order_tasks] ([seot_id], [seot_name], [seot_startdate], [seot_enddate], [seot_actual_startdate], [seot_actual_enddate], [seot_status], [seot_arwg_code], [seot_sero_id]) VALUES (3, N'CHECK VEHICLE CONDITION', CAST(N'2023-12-07T19:27:46.267' AS DateTime), CAST(N'2023-12-08T19:27:46.267' AS DateTime), NULL, NULL, N'INPROGRESS', NULL, N'CL0001-20231010')
INSERT [so].[service_order_tasks] ([seot_id], [seot_name], [seot_startdate], [seot_enddate], [seot_actual_startdate], [seot_actual_enddate], [seot_status], [seot_arwg_code], [seot_sero_id]) VALUES (4, N'CLAIM DOCUMENT APPROVED', CAST(N'2023-12-07T19:27:46.267' AS DateTime), CAST(N'2023-12-08T19:27:46.267' AS DateTime), NULL, NULL, N'INPROGRESS', NULL, N'CL0001-20231010')
INSERT [so].[service_order_tasks] ([seot_id], [seot_name], [seot_startdate], [seot_enddate], [seot_actual_startdate], [seot_actual_enddate], [seot_status], [seot_arwg_code], [seot_sero_id]) VALUES (5, N'NOTIFY PARTNER TO REPAIR', CAST(N'2023-12-07T19:27:46.267' AS DateTime), CAST(N'2023-12-08T19:27:46.267' AS DateTime), NULL, NULL, N'INPROGRESS', NULL, N'CL0001-20231010')
INSERT [so].[service_order_tasks] ([seot_id], [seot_name], [seot_startdate], [seot_enddate], [seot_actual_startdate], [seot_actual_enddate], [seot_status], [seot_arwg_code], [seot_sero_id]) VALUES (6, N'CALCULATE SPARE PART', CAST(N'2023-12-07T19:27:46.267' AS DateTime), CAST(N'2023-12-08T19:27:46.267' AS DateTime), NULL, NULL, N'INPROGRESS', NULL, N'CL0001-20231010')
INSERT [so].[service_order_tasks] ([seot_id], [seot_name], [seot_startdate], [seot_enddate], [seot_actual_startdate], [seot_actual_enddate], [seot_status], [seot_arwg_code], [seot_sero_id]) VALUES (7, N'NOTIFY CUSTOMER VEHICLE REPAIRED', CAST(N'2023-12-07T19:27:46.267' AS DateTime), CAST(N'2023-12-08T19:27:46.267' AS DateTime), NULL, NULL, N'INPROGRESS', NULL, N'CL0001-20231010')
INSERT [so].[service_order_tasks] ([seot_id], [seot_name], [seot_startdate], [seot_enddate], [seot_actual_startdate], [seot_actual_enddate], [seot_status], [seot_arwg_code], [seot_sero_id]) VALUES (8, N'NOTIFY AGENT CLAIM', CAST(N'2023-12-07T19:27:46.267' AS DateTime), CAST(N'2023-12-08T19:27:46.267' AS DateTime), NULL, NULL, N'INPROGRESS', NULL, N'CL0001-20231010')
INSERT [so].[service_order_tasks] ([seot_id], [seot_name], [seot_startdate], [seot_enddate], [seot_actual_startdate], [seot_actual_enddate], [seot_status], [seot_arwg_code], [seot_sero_id]) VALUES (9, N'FEEDBACK CUSTOMER', CAST(N'2023-12-07T19:27:46.267' AS DateTime), CAST(N'2023-12-08T19:27:46.267' AS DateTime), NULL, NULL, N'INPROGRESS', NULL, N'CL0001-20231010')
INSERT [so].[service_order_tasks] ([seot_id], [seot_name], [seot_startdate], [seot_enddate], [seot_actual_startdate], [seot_actual_enddate], [seot_status], [seot_arwg_code], [seot_sero_id]) VALUES (10, N'CLOSE ORDER', CAST(N'2023-12-07T19:27:46.267' AS DateTime), CAST(N'2023-12-08T19:27:46.267' AS DateTime), NULL, NULL, N'INPROGRESS', NULL, N'CL0001-20231010')
SET IDENTITY_INSERT [so].[service_order_tasks] OFF
GO
SET IDENTITY_INSERT [mtr].[category] ON 

INSERT [mtr].[category] ([cate_id], [cate_name]) VALUES (1, N'Kategori 1')
INSERT [mtr].[category] ([cate_id], [cate_name]) VALUES (2, N'Kategori 2')
INSERT [mtr].[category] ([cate_id], [cate_name]) VALUES (3, N'Kategori 3')
INSERT [mtr].[category] ([cate_id], [cate_name]) VALUES (4, N'Kategori 4')
INSERT [mtr].[category] ([cate_id], [cate_name]) VALUES (5, N'Kategori Extend')
SET IDENTITY_INSERT [mtr].[category] OFF
GO
SET IDENTITY_INSERT [mtr].[template_insurance_premi] ON 

INSERT [mtr].[template_insurance_premi] ([temi_id], [temi_name], [temi_rate_min], [temi_rate_max], [temi_nominal], [temi_type], [temi_zones_id], [temi_inty_name], [temi_cate_id]) VALUES (1, N'0 s/d RP.125.000.00', 3.82, 4.2, NULL, NULL, 1, N'Comprehensive', 1)
INSERT [mtr].[template_insurance_premi] ([temi_id], [temi_name], [temi_rate_min], [temi_rate_max], [temi_nominal], [temi_type], [temi_zones_id], [temi_inty_name], [temi_cate_id]) VALUES (2, N'0 s/d RP.125.000.00', 3.26, 3.59, NULL, NULL, 2, N'Comprehensive', 1)
INSERT [mtr].[template_insurance_premi] ([temi_id], [temi_name], [temi_rate_min], [temi_rate_max], [temi_nominal], [temi_type], [temi_zones_id], [temi_inty_name], [temi_cate_id]) VALUES (3, N'0 s/d RP.125.000.00', 2.53, 2.78, NULL, NULL, 3, N'Comprehensive', 1)
INSERT [mtr].[template_insurance_premi] ([temi_id], [temi_name], [temi_rate_min], [temi_rate_max], [temi_nominal], [temi_type], [temi_zones_id], [temi_inty_name], [temi_cate_id]) VALUES (4, N'0 s/d RP.125.000.00', 0.47, 0.56, NULL, NULL, 1, N'Total Loss Only', 1)
INSERT [mtr].[template_insurance_premi] ([temi_id], [temi_name], [temi_rate_min], [temi_rate_max], [temi_nominal], [temi_type], [temi_zones_id], [temi_inty_name], [temi_cate_id]) VALUES (5, N'0 s/d RP.125.000.00', 0.65, 0.78, NULL, NULL, 2, N'Total Loss Only', 1)
INSERT [mtr].[template_insurance_premi] ([temi_id], [temi_name], [temi_rate_min], [temi_rate_max], [temi_nominal], [temi_type], [temi_zones_id], [temi_inty_name], [temi_cate_id]) VALUES (6, N'0 s/d RP.125.000.00', 0.51, 0.56, NULL, NULL, 3, N'Total Loss Only', 1)
INSERT [mtr].[template_insurance_premi] ([temi_id], [temi_name], [temi_rate_min], [temi_rate_max], [temi_nominal], [temi_type], [temi_zones_id], [temi_inty_name], [temi_cate_id]) VALUES (7, N'0 s/d RP.125.000.00', 0.2, NULL, 400, NULL, 2, N'Comprehensive', 5)
INSERT [mtr].[template_insurance_premi] ([temi_id], [temi_name], [temi_rate_min], [temi_rate_max], [temi_nominal], [temi_type], [temi_zones_id], [temi_inty_name], [temi_cate_id]) VALUES (8, N'0 s/d RP.125.000.00', NULL, NULL, 1, NULL, NULL, N'Comprehensive', 5)
INSERT [mtr].[template_insurance_premi] ([temi_id], [temi_name], [temi_rate_min], [temi_rate_max], [temi_nominal], [temi_type], [temi_zones_id], [temi_inty_name], [temi_cate_id]) VALUES (9, N'0 s/d RP.125.000.00', NULL, NULL, 100, NULL, NULL, N'Comprehensive', 5)
INSERT [mtr].[template_insurance_premi] ([temi_id], [temi_name], [temi_rate_min], [temi_rate_max], [temi_nominal], [temi_type], [temi_zones_id], [temi_inty_name], [temi_cate_id]) VALUES (10, N'0 s/d RP.125.000.00', NULL, NULL, 750, NULL, NULL, N'Comprehensive', 5)
SET IDENTITY_INSERT [mtr].[template_insurance_premi] OFF
GO
INSERT [mtr].[region_plat] ([regp_name], [regp_desc], [regp_prov_id]) VALUES (N'B', NULL, 1)
INSERT [mtr].[region_plat] ([regp_name], [regp_desc], [regp_prov_id]) VALUES (N'D', NULL, 2)
INSERT [mtr].[region_plat] ([regp_name], [regp_desc], [regp_prov_id]) VALUES (N'E', NULL, 2)
INSERT [mtr].[region_plat] ([regp_name], [regp_desc], [regp_prov_id]) VALUES (N'F', NULL, 2)
INSERT [mtr].[region_plat] ([regp_name], [regp_desc], [regp_prov_id]) VALUES (N'T', NULL, 2)
INSERT [mtr].[region_plat] ([regp_name], [regp_desc], [regp_prov_id]) VALUES (N'Z', NULL, 2)
GO
SET IDENTITY_INSERT [mtr].[template_type] ON 

INSERT [mtr].[template_type] ([tety_id], [tety_name], [tety_group]) VALUES (1, N'FEASIBILITY', N'SERVICES')
INSERT [mtr].[template_type] ([tety_id], [tety_name], [tety_group]) VALUES (2, N'CREATE POLIS', N'SERVICES')
INSERT [mtr].[template_type] ([tety_id], [tety_name], [tety_group]) VALUES (3, N'CLAIM', N'SERVICES')
INSERT [mtr].[template_type] ([tety_id], [tety_name], [tety_group]) VALUES (4, N'CREATE', N'ORDER TYPE')
INSERT [mtr].[template_type] ([tety_id], [tety_name], [tety_group]) VALUES (5, N'MODIFY', N'ORDER TYPE')
INSERT [mtr].[template_type] ([tety_id], [tety_name], [tety_group]) VALUES (6, N'OPEN', N'STATUS')
INSERT [mtr].[template_type] ([tety_id], [tety_name], [tety_group]) VALUES (7, N'PENDING', N'STATUS')
INSERT [mtr].[template_type] ([tety_id], [tety_name], [tety_group]) VALUES (8, N'CANCELLED', N'STATUS')
INSERT [mtr].[template_type] ([tety_id], [tety_name], [tety_group]) VALUES (9, N'CLOSED', N'STATUS')
SET IDENTITY_INSERT [mtr].[template_type] OFF
GO
SET IDENTITY_INSERT [mtr].[template_service_task] ON 

INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (1, N'REVIEW & CHECK CUSTOMER REQUEST', 1, N'WORKGROUP', NULL, 1)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (2, N'PROSPEK CUSTOMER POTENTIAL', 1, N'WORKGROUP', NULL, 2)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (3, N'PREMI SCHEMA', 1, N'WORKGROUP', NULL, 3)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (4, N'LEGAL DOCUMENT SIGNED', 1, N'WORKGROUP', NULL, 4)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (5, N'CREATE POLIS', NULL, N'SYSTEM', NULL, NULL)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (6, N'CLOSE ORDER', NULL, N'SYSTEM', NULL, NULL)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (7, N'GENERATE POLIS NUMBER', 2, N'SYSTEM', N'generatePolis', 1)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (8, N'GENERATE PREMI', 2, N'SYSTEM', N'generatePremi', 2)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (9, N'GENERATE VIRTUAL ACCOUNT', 2, N'SYSTEM', N'generateVa', 3)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (10, N'NOTIFY TO AGENT', 2, N'SYSTEM', NULL, 4)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (11, N'NOTIFY TO CUSTOMER', 2, N'SYSTEM', NULL, 5)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (12, N'CLOSE ORDER', 2, N'SYSTEM', NULL, 6)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (13, N'CHECK CUSTOMER PREMI', 3, N'WORKGROUP', NULL, 1)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (14, N'SETTLE CUSTOMER PREMI', 3, N'WORKGROUP', NULL, 2)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (15, N'CHECK VEHICLE CONDITION', 3, N'WORKGROUP', NULL, 3)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (16, N'CLAIM DOCUMENT APPROVED', 3, N'WORKGROUP', NULL, 4)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (17, N'NOTIFY PARTNER TO REPAIR', 3, N'SYSTEM', NULL, 5)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (18, N'CALCULATE SPARE PART', 3, N'WORKGROUP', NULL, 6)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (19, N'NOTIFY CUSTOMER VEHICLE REPAIRED', 3, N'WORKGROUP', NULL, 7)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (20, N'NOTIFY AGENT CLAIM', 3, N'SYSTEM', NULL, 8)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (21, N'FEEDBACK CUSTOMER', 3, N'WORKGROUP', NULL, 9)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (22, N'CLOSE ORDER', 3, N'SYSTEM', NULL, 10)
SET IDENTITY_INSERT [mtr].[template_service_task] OFF
GO
SET IDENTITY_INSERT [mtr].[template_task_workorder] ON 

INSERT [mtr].[template_task_workorder] ([tewo_id], [tewo_name], [tewo_value], [tewo_testa_id]) VALUES (1, N'CHECK UMUR', NULL, 1)
INSERT [mtr].[template_task_workorder] ([tewo_id], [tewo_name], [tewo_value], [tewo_testa_id]) VALUES (2, N'RELATE GOVERNMENT', NULL, 1)
INSERT [mtr].[template_task_workorder] ([tewo_id], [tewo_name], [tewo_value], [tewo_testa_id]) VALUES (3, N'PREMI SCHEMA', N'COMPREHENSIVE', 3)
INSERT [mtr].[template_task_workorder] ([tewo_id], [tewo_name], [tewo_value], [tewo_testa_id]) VALUES (4, N'DOCUMENT DISETUJUI', N'OK', 3)
INSERT [mtr].[template_task_workorder] ([tewo_id], [tewo_name], [tewo_value], [tewo_testa_id]) VALUES (5, N'COPY VALUE', N'Y', NULL)
SET IDENTITY_INSERT [mtr].[template_task_workorder] OFF
GO
INSERT [payment].[payment_transactions] ([patr_trxno], [patr_created_on], [patr_debet], [patr_credit], [patr_usac_accountNo_from], [patr_usac_accountNo_to], [patr_type], [patr_invoice_no], [patr_notes], [patr_trxno_rev]) VALUES (N'TRANSAKSI1', CAST(N'2023-10-10T00:00:00.000' AS DateTime), 10000.0000, 1000.0000, 1, 2, N'SALARY', N'123', N'NOTES', N'TRANSAKSI1')
GO
SET IDENTITY_INSERT [so].[service_premi_credit] ON 

INSERT [so].[service_premi_credit] ([secr_id], [secr_serv_id], [secr_year], [secr_premi_debet], [secr_premi_credit], [secr_trx_date], [secr_duedate], [secr_patr_trxno]) VALUES (1, 1, CAST(N'2023-10-10T00:00:00.000' AS DateTime), 10000.0000, 10000.0000, CAST(N'2023-10-10T00:00:00.000' AS DateTime), CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'TRANSAKSI1')
SET IDENTITY_INSERT [so].[service_premi_credit] OFF
GO
SET IDENTITY_INSERT [hr].[template_salary] ON 

INSERT [hr].[template_salary] ([tesal_id], [tesal_name], [tesal_nominal], [tesal_rate_min], [tesal_rate_max]) VALUES (1, N'net salary', NULL, NULL, NULL)
INSERT [hr].[template_salary] ([tesal_id], [tesal_name], [tesal_nominal], [tesal_rate_min], [tesal_rate_max]) VALUES (2, N'komisi awal', NULL, 0.05, 0.1)
INSERT [hr].[template_salary] ([tesal_id], [tesal_name], [tesal_nominal], [tesal_rate_min], [tesal_rate_max]) VALUES (3, N'komisi pemeliharaan', NULL, 0.02, 0.05)
INSERT [hr].[template_salary] ([tesal_id], [tesal_name], [tesal_nominal], [tesal_rate_min], [tesal_rate_max]) VALUES (4, N'komisi persestensi', NULL, 0.02, 0.05)
INSERT [hr].[template_salary] ([tesal_id], [tesal_name], [tesal_nominal], [tesal_rate_min], [tesal_rate_max]) VALUES (5, N'komisi bonus', NULL, 0.05, 0.1)
INSERT [hr].[template_salary] ([tesal_id], [tesal_name], [tesal_nominal], [tesal_rate_min], [tesal_rate_max]) VALUES (6, N'komisi renewal', NULL, 0.02, 0.05)
INSERT [hr].[template_salary] ([tesal_id], [tesal_name], [tesal_nominal], [tesal_rate_min], [tesal_rate_max]) VALUES (7, N'komisi overriding', NULL, 0.02, 0.05)
SET IDENTITY_INSERT [hr].[template_salary] OFF
GO
