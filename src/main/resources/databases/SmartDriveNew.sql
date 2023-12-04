USE [master]
GO
/****** Object:  Database [SmartDrive]    Script Date: 11/30/2023 2:13:06 PM ******/
CREATE DATABASE [SmartDrive]
 CONTAINMENT = NONE
 ON  PRIMARY
( NAME = N'SmartDrive', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\SmartDrive.mdf' , SIZE = 73728KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON
( NAME = N'SmartDrive_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\SmartDrive_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [SmartDrive] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [SmartDrive].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [SmartDrive] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [SmartDrive] SET ANSI_NULLS OFF
GO
ALTER DATABASE [SmartDrive] SET ANSI_PADDING OFF
GO
ALTER DATABASE [SmartDrive] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [SmartDrive] SET ARITHABORT OFF
GO
ALTER DATABASE [SmartDrive] SET AUTO_CLOSE ON
GO
ALTER DATABASE [SmartDrive] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [SmartDrive] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [SmartDrive] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [SmartDrive] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [SmartDrive] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [SmartDrive] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [SmartDrive] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [SmartDrive] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [SmartDrive] SET  ENABLE_BROKER
GO
ALTER DATABASE [SmartDrive] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [SmartDrive] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [SmartDrive] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [SmartDrive] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [SmartDrive] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [SmartDrive] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [SmartDrive] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [SmartDrive] SET RECOVERY SIMPLE
GO
ALTER DATABASE [SmartDrive] SET  MULTI_USER
GO
ALTER DATABASE [SmartDrive] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [SmartDrive] SET DB_CHAINING OFF
GO
ALTER DATABASE [SmartDrive] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF )
GO
ALTER DATABASE [SmartDrive] SET TARGET_RECOVERY_TIME = 60 SECONDS
GO
ALTER DATABASE [SmartDrive] SET DELAYED_DURABILITY = DISABLED
GO
ALTER DATABASE [SmartDrive] SET ACCELERATED_DATABASE_RECOVERY = OFF
GO
ALTER DATABASE [SmartDrive] SET QUERY_STORE = ON
GO
ALTER DATABASE [SmartDrive] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [SmartDrive]
GO
/****** Object:  Schema [customer]    Script Date: 11/30/2023 2:13:07 PM ******/
CREATE SCHEMA [customer]
GO
/****** Object:  Schema [hr]    Script Date: 11/30/2023 2:13:07 PM ******/
CREATE SCHEMA [hr]
GO
/****** Object:  Schema [mtr]    Script Date: 11/30/2023 2:13:07 PM ******/
CREATE SCHEMA [mtr]
GO
/****** Object:  Schema [partners]    Script Date: 11/30/2023 2:13:07 PM ******/
CREATE SCHEMA [partners]
GO
/****** Object:  Schema [payment]    Script Date: 11/30/2023 2:13:07 PM ******/
CREATE SCHEMA [payment]
GO
/****** Object:  Schema [so]    Script Date: 11/30/2023 2:13:07 PM ******/
CREATE SCHEMA [so]
GO
/****** Object:  Schema [users]    Script Date: 11/30/2023 2:13:07 PM ******/
CREATE SCHEMA [users]
GO
USE [SmartDrive]
GO
/****** Object:  Sequence [dbo].[cadoc_cuex_id]    Script Date: 11/30/2023 2:13:07 PM ******/
CREATE SEQUENCE [dbo].[cadoc_cuex_id]
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE
GO
USE [SmartDrive]
GO
/****** Object:  Sequence [hr].[employee_are_workgroup_seq]    Script Date: 11/30/2023 2:13:07 PM ******/
CREATE SEQUENCE [hr].[employee_are_workgroup_seq]
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 CACHE
GO
USE [SmartDrive]
GO
/****** Object:  Sequence [users].[user_address_seq]    Script Date: 11/30/2023 2:13:07 PM ******/
CREATE SEQUENCE [users].[user_address_seq]
 AS [bigint]
 START WITH 2
 INCREMENT BY 1
 MINVALUE 2
 MAXVALUE 9223372036854775807
 CACHE
GO
/****** Object:  Table [customer].[customer_claim]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [customer].[customer_claim](
	[cucl_creq_entityid] [int] NOT NULL,
	[cucl_create_date] [datetime] NULL,
	[cucl_event_price] [money] NULL,
	[cucl_subtotal] [money] NULL,
	[cucl_reason] [varchar](256) NULL,
PRIMARY KEY CLUSTERED
(
	[cucl_creq_entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [customer].[customer_insc_assets]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [customer].[customer_insc_assets](
	[cias_creq_entityid] [int] NOT NULL,
	[cias_police_number] [varchar](15) NOT NULL,
	[cias_year] [varchar](4) NOT NULL,
	[cias_startdate] [datetime] NULL,
	[cias_enddate] [datetime] NULL,
	[cias_current_price] [money] NULL,
	[cias_insurance_price] [money] NULL,
	[cias_total_premi] [money] NULL,
	[cias_paid_type] [varchar](15) NULL,
	[cias_isNewChar] [char](1) NULL,
	[cias_cars_id] [int] NULL,
	[cias_inty_name] [varchar](25) NULL,
	[cias_city_id] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[cias_creq_entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [customer].[customer_insc_doc]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [customer].[customer_insc_doc](
	[cadoc_id] [int] NOT NULL,
	[cadoc_creq_entityid] [int] NOT NULL,
	[cadoc_filename] [varchar](15) NULL,
	[cadoc_filetype] [varchar](15) NULL,
	[cadoc_filesize] [int] NULL,
	[cadoc_category] [varchar](15) NULL,
	[cadoc_modified_date] [datetime] NULL,
 CONSTRAINT [PK_CADOC] PRIMARY KEY CLUSTERED
(
	[cadoc_id] ASC,
	[cadoc_creq_entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [customer].[customer_insc_extend]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [customer].[customer_insc_extend](
	[cuex_id] [int] NOT NULL,
	[cuex_creq_entityid] [int] NOT NULL,
	[cuex_name] [varchar](256) NULL,
	[cuex_total_item] [int] NULL,
	[cuex_nominal] [money] NULL,
 CONSTRAINT [PK_CUEX] PRIMARY KEY CLUSTERED
(
	[cuex_id] ASC,
	[cuex_creq_entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [customer].[customer_request]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [customer].[customer_request](
	[creq_entityid] [int] NOT NULL,
	[creq_create_date] [datetime] NULL,
	[creq_status] [varchar](15) NULL,
	[creq_type] [varchar](15) NULL,
	[creq_modified_date] [datetime] NULL,
	[creq_cust_entityid] [int] NULL,
	[creq_agen_entityid] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[creq_entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [hr].[batch_employee_salary]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [hr].[batch_employee_salary](
	[besa_emp_entity_id] [int] NOT NULL,
	[besa_created_date] [date] NOT NULL,
	[ems_trasfer_Date] [datetime] NULL,
	[besa_total_salary] [money] NULL,
	[besa_account_number] [varchar](35) NULL,
	[besa_status] [varchar](15) NULL,
	[besa_patr_trxno] [varchar](55) NULL,
	[besa_paid_date] [datetime] NULL,
	[besa_modified_date] [datetime] NULL,
PRIMARY KEY CLUSTERED
(
	[besa_emp_entity_id] ASC,
	[besa_created_date] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [hr].[employee_are_workgroup]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [hr].[employee_are_workgroup](
	[eawg_id] [int] NOT NULL,
	[eawg_entityid] [int] NOT NULL,
	[eawg_status] [varchar](15) NULL,
	[eawg_arwg_code] [varchar](15) NULL,
	[eawg_modified_date] [datetime] NULL,
PRIMARY KEY CLUSTERED
(
	[eawg_entityid] ASC,
	[eawg_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [hr].[employee_salary_detail]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [hr].[employee_salary_detail](
	[emsa_id] [int] IDENTITY(1,1) NOT NULL,
	[emsa_emp_entityid] [int] NOT NULL,
	[emsa_create_date] [date] NOT NULL,
	[emsa_name] [varchar](55) NULL,
	[emsa_subtotal] [money] NULL,
PRIMARY KEY CLUSTERED
(
	[emsa_id] ASC,
	[emsa_emp_entityid] ASC,
	[emsa_create_date] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [hr].[employees]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [hr].[employees](
	[emp_entityid] [int] NOT NULL,
	[emp_name] [varchar](85) NULL,
	[emp_join_date] [datetime] NULL,
	[emp_type] [varchar](15) NULL,
	[emp_status] [varchar](15) NULL,
	[emp_graduate] [varchar](15) NULL,
	[emp_net_salary] [money] NULL,
	[emp_account_number] [varchar](15) NULL,
	[emp_modified_date] [datetime] NULL,
	[emp_job_code] [varchar](15) NULL,
PRIMARY KEY CLUSTERED
(
	[emp_entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [hr].[job_type]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [hr].[job_type](
	[job_code] [varchar](15) NOT NULL,
	[job_modified_date] [datetime] NULL,
	[job_desc] [varchar](50) NULL,
	[job_rate_min] [decimal](18, 0) NULL,
	[job_rate_max] [decimal](18, 0) NULL,
PRIMARY KEY CLUSTERED
(
	[job_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [hr].[template_salary]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [hr].[template_salary](
	[tesal_id] [int] IDENTITY(1,1) NOT NULL,
	[tesal_name] [varchar](55) NULL,
	[tesal_nominal] [money] NULL,
	[tesal_rate_min] [float] NULL,
	[tesal_rate_max] [float] NULL,
PRIMARY KEY CLUSTERED
(
	[tesal_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[area_workgroup]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[area_workgroup](
	[arwg_code] [varchar](15) NOT NULL,
	[arwg_desc] [varchar](55) NULL,
	[arwg_city_id] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[arwg_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[car_brands]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[car_brands](
	[cabr_id] [int] IDENTITY(1,1) NOT NULL,
	[cabr_name] [varchar](55) NULL,
PRIMARY KEY CLUSTERED
(
	[cabr_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[car_models]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[car_models](
	[carm_id] [int] IDENTITY(1,1) NOT NULL,
	[carm_name] [varchar](55) NULL,
	[carm_cabr_id] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[carm_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[car_series]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[car_series](
	[cars_id] [int] IDENTITY(1,1) NOT NULL,
	[cars_name] [varchar](55) NULL,
	[cars_passenger] [int] NULL,
	[cars_carm_id] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[cars_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[category]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[category](
	[cate_id] [int] IDENTITY(1,1) NOT NULL,
	[cate_name] [varchar](55) NULL,
PRIMARY KEY CLUSTERED
(
	[cate_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[cities]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[cities](
	[city_id] [int] IDENTITY(1,1) NOT NULL,
	[city_name] [varchar](85) NULL,
	[city_prov_id] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[city_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[insurance_type]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[insurance_type](
	[inty_name] [varchar](25) NOT NULL,
	[inty_desc] [varchar](55) NULL,
PRIMARY KEY CLUSTERED
(
	[inty_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[provinsi]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[provinsi](
	[prov_id] [int] IDENTITY(1,1) NOT NULL,
	[prov_name] [varchar](85) NULL,
	[prov_zones_id] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[prov_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[region_plat]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[region_plat](
	[regp_name] [varchar](3) NOT NULL,
	[regp_desc] [varchar](35) NULL,
	[regp_prov_id] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[regp_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[template_insurance_premi]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[template_insurance_premi](
	[temi_id] [int] IDENTITY(1,1) NOT NULL,
	[temi_name] [varchar](256) NULL,
	[temi_rate_min] [float] NULL,
	[temi_rate_max] [float] NULL,
	[temi_nominal] [float] NULL,
	[temi_type] [varchar](15) NULL,
	[temi_zones_id] [int] NULL,
	[temi_inty_name] [varchar](25) NULL,
	[temi_cate_id] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[temi_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[template_service_task]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[template_service_task](
	[testa_id] [int] IDENTITY(1,1) NOT NULL,
	[testa_name] [varchar](55) NULL,
	[testa_tety_id] [int] NULL,
	[testa_group] [varchar](50) NULL,
	[testa_callmethod] [varchar](100) NULL,
	[testa_seqorder] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[testa_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[template_task_workorder]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[template_task_workorder](
	[tewo_id] [int] IDENTITY(1,1) NOT NULL,
	[tewo_name] [varchar](55) NULL,
	[tewo_value] [varchar](50) NULL,
	[tewo_testa_id] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[tewo_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[template_type]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[template_type](
	[tety_id] [int] IDENTITY(1,1) NOT NULL,
	[tety_name] [varchar](25) NULL,
	[tety_group] [varchar](15) NULL,
PRIMARY KEY CLUSTERED
(
	[tety_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[zones]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[zones](
	[zones_id] [int] IDENTITY(1,1) NOT NULL,
	[zones_name] [varchar](55) NULL,
PRIMARY KEY CLUSTERED
(
	[zones_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [partners].[batch_partner_invoice]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [partners].[batch_partner_invoice](
	[bpin_invoiceNo] [varchar](30) NOT NULL,
	[bpin_created_on] [datetime] NULL,
	[bpin_subtotal] [money] NULL,
	[bpin_tax] [money] NULL,
	[bpin_accountNo] [varchar](30) NULL,
	[bpin_status] [varchar](15) NULL,
	[bpin_paid_date] [datetime] NULL,
	[bpin_serv_id] [int] NULL,
	[bpin_patrn_entityid] [int] NULL,
	[bpin_patr_trxno] [varchar](55) NULL,
 CONSTRAINT [pk_bpin_invoiceNo] PRIMARY KEY CLUSTERED
(
	[bpin_invoiceNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [partners].[partner_area_workgroup]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [partners].[partner_area_workgroup](
	[pawo_patr_entityid] [int] NOT NULL,
	[pawo_arwg_code] [varchar](15) NOT NULL,
	[pawo_user_entityid] [int] NOT NULL,
	[pawo_status] [varchar](15) NULL,
	[pawo_modified_date] [datetime] NULL,
 CONSTRAINT [pk_pawo_patr_arwg_user] PRIMARY KEY CLUSTERED
(
	[pawo_patr_entityid] ASC,
	[pawo_arwg_code] ASC,
	[pawo_user_entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [partners].[partner_contacts]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [partners].[partner_contacts](
	[paco_patrn_entityid] [int] NOT NULL,
	[paco_user_entityid] [int] NOT NULL,
	[paco_status] [varchar](15) NULL,
 CONSTRAINT [pk_paco_patrn_user] PRIMARY KEY CLUSTERED
(
	[paco_patrn_entityid] ASC,
	[paco_user_entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [partners].[partners]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [partners].[partners](
	[part_entityid] [int] NOT NULL,
	[part_name] [varchar](25) NULL,
	[part_address] [varchar](255) NULL,
	[part_join_date] [datetime] NULL,
	[part_accountNo] [varchar](35) NULL,
	[part_npwp] [varchar](25) NULL,
	[part_status] [varchar](15) NULL,
	[part_modified_date] [datetime] NULL,
	[part_city_id] [int] NOT NULL,
 CONSTRAINT [pk_part_entityid] PRIMARY KEY CLUSTERED
(
	[part_entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [payment].[banks]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [payment].[banks](
	[bank_entityid] [int] NOT NULL,
	[bank_name] [varchar](5) NULL,
	[bank_desc] [varchar](55) NULL,
 CONSTRAINT [pk_bank_entityid] PRIMARY KEY CLUSTERED
(
	[bank_entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [payment].[fintech]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [payment].[fintech](
	[fint_entityid] [int] NOT NULL,
	[fint_name] [varchar](5) NULL,
	[fint_desc] [varchar](55) NULL,
 CONSTRAINT [pk_fint_entityid] PRIMARY KEY CLUSTERED
(
	[fint_entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [payment].[payment_transactions]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [payment].[payment_transactions](
	[patr_trxno] [varchar](55) NOT NULL,
	[patr_created_on] [datetime] NULL,
	[patr_debet] [money] NULL,
	[patr_credit] [money] NULL,
	[patr_usac_accountNo_from] [int] NULL,
	[patr_usac_accountNo_to] [int] NULL,
	[patr_type] [varchar](15) NULL,
	[patr_invoice_no] [varchar](55) NULL,
	[patr_notes] [varchar](125) NULL,
	[patr_trxno_rev] [varchar](55) NULL,
 CONSTRAINT [pk_patr_trxno] PRIMARY KEY CLUSTERED
(
	[patr_trxno] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [payment].[user_accounts]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [payment].[user_accounts](
	[usac_id] [int] IDENTITY(1,1) NOT NULL,
	[usac_accountno] [varchar](30) NULL,
	[usac_debet] [money] NULL,
	[usac_credit] [money] NULL,
	[usac_type] [varchar](15) NULL,
	[usac_bank_entityid] [int] NULL,
	[usac_fint_entityid] [int] NULL,
	[usac_user_entityid] [int] NULL,
 CONSTRAINT [pk_usac_id] PRIMARY KEY CLUSTERED
(
	[usac_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [so].[claim_asset_evidence]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [so].[claim_asset_evidence](
	[caev_id] [int] IDENTITY(1,1) NOT NULL,
	[caev_filename] [varchar](55) NULL,
	[caev_filesize] [int] NULL,
	[caev_filetype] [varchar](15) NULL,
	[caev_url] [varchar](255) NULL,
	[caev_note] [varchar](15) NULL,
	[caev_part_entityid] [int] NULL,
	[caev_sero_id] [varchar](25) NULL,
 CONSTRAINT [pk_caev_id] PRIMARY KEY CLUSTERED
(
	[caev_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [so].[claim_asset_sparepart]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [so].[claim_asset_sparepart](
	[casp_id] [int] IDENTITY(1,1) NOT NULL,
	[casp_item_name] [varchar](55) NULL,
	[casp_quantity] [int] NULL,
	[casp_item_price] [money] NULL,
	[casp_subtotal] [money] NULL,
	[casp_part_entityid] [int] NULL,
	[casp_sero_id] [varchar](25) NULL,
 CONSTRAINT [pk_casp_id] PRIMARY KEY CLUSTERED
(
	[casp_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [so].[service_order_tasks]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [so].[service_order_tasks](
	[seot_id] [int] IDENTITY(1,1) NOT NULL,
	[seot_name] [varchar](256) NULL,
	[seot_startdate] [datetime] NULL,
	[seot_enddate] [datetime] NULL,
	[seot_actual_startdate] [datetime] NULL,
	[seot_actual_enddate] [datetime] NULL,
	[seot_status] [varchar](15) NULL,
	[seot_arwg_code] [varchar](15) NULL,
	[seot_sero_id] [varchar](25) NULL,
 CONSTRAINT [pk_seot_id] PRIMARY KEY CLUSTERED
(
	[seot_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [so].[service_order_workorder]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [so].[service_order_workorder](
	[sowo_id] [int] IDENTITY(1,1) NOT NULL,
	[sowo_name] [varchar](256) NULL,
	[sowo_modified_date] [datetime] NULL,
	[sowo_status] [varchar](15) NULL,
	[sowo_seot_id] [int] NULL,
 CONSTRAINT [pk_sowo_id] PRIMARY KEY CLUSTERED
(
	[sowo_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [so].[service_orders]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [so].[service_orders](
	[sero_id] [varchar](25) NOT NULL,
	[sero_ordt_type] [varchar](15) NULL,
	[sero_status] [varchar](15) NULL,
	[sero_reason] [varchar](256) NULL,
	[serv_claim_no] [varchar](12) NULL,
	[serv_claim_startdate] [datetime] NULL,
	[serv_claim_enddate] [datetime] NULL,
	[sero_serv_id] [int] NULL,
	[sero_sero_id] [varchar](25) NULL,
	[sero_agent_entityid] [int] NULL,
	[sero_arwg_code] [varchar](15) NULL,
 CONSTRAINT [pk_sero_id] PRIMARY KEY CLUSTERED
(
	[sero_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [so].[service_premi]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [so].[service_premi](
	[semi_serv_id] [int] NOT NULL,
	[semi_premi_debet] [money] NULL,
	[semi_premi_credit] [money] NULL,
	[semi_paid_type] [varchar](15) NULL,
	[semi_status] [varchar](15) NULL,
	[semi_modified_date] [datetime] NULL,
 CONSTRAINT [pk_semi_serv_id] PRIMARY KEY CLUSTERED
(
	[semi_serv_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [so].[service_premi_credit]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [so].[service_premi_credit](
	[secr_id] [int] IDENTITY(1,1) NOT NULL,
	[secr_serv_id] [int] NOT NULL,
	[secr_year] [datetime] NULL,
	[secr_premi_debet] [money] NULL,
	[secr_premi_credit] [money] NULL,
	[secr_trx_date] [datetime] NULL,
	[secr_duedate] [datetime] NULL,
	[secr_patr_trxno] [varchar](55) NULL,
 CONSTRAINT [pk_secr] PRIMARY KEY CLUSTERED
(
	[secr_id] ASC,
	[secr_serv_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [so].[services]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [so].[services](
	[serv_id] [int] IDENTITY(1,1) NOT NULL,
	[serv_created_on] [datetime] NULL,
	[serv_type] [varchar](15) NULL,
	[serv_insuranceNo] [varchar](12) NULL,
	[serv_vehicleNo] [varchar](12) NULL,
	[serv_startdate] [datetime] NULL,
	[serv_enddate] [datetime] NULL,
	[serv_status] [varchar](15) NULL,
	[serv_serv_id] [int] NULL,
	[serv_cust_entityid] [int] NULL,
	[serv_creq_entityid] [int] NULL,
 CONSTRAINT [pk_serv_id] PRIMARY KEY CLUSTERED
(
	[serv_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [users].[business_entity]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [users].[business_entity](
	[entityid] [int] IDENTITY(1,1) NOT NULL,
	[entity_modified_date] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [users].[roles]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [users].[roles](
	[role_name] [char](2) NOT NULL,
	[role_description] [varchar](35) NOT NULL,
 CONSTRAINT [pk_roles] PRIMARY KEY CLUSTERED
(
	[role_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [users].[user_address]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [users].[user_address](
	[usdr_id] [int] NOT NULL,
	[usdr_entityid] [int] NOT NULL,
	[usdr_address1] [varchar](255) NULL,
	[usdr_address2] [varchar](255) NULL,
	[usdr_modified_date] [datetime] NULL,
	[usdr_city_id] [int] NULL,
 CONSTRAINT [pk_entity_address] PRIMARY KEY CLUSTERED
(
	[usdr_id] ASC,
	[usdr_entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [users].[user_phone]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [users].[user_phone](
	[usph_entityid] [int] NOT NULL,
	[usph_phone_number] [varchar](15) NOT NULL,
	[usph_phone_type] [varchar](15) NULL,
	[usph_mime] [varchar](512) NULL,
	[usph_status] [varchar](15) NULL,
	[usph_modified_date] [datetime] NULL,
 CONSTRAINT [pk_entity_phone] PRIMARY KEY CLUSTERED
(
	[usph_entityid] ASC,
	[usph_phone_number] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [users].[user_roles]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [users].[user_roles](
	[usro_entityid] [int] NOT NULL,
	[usro_role_name] [char](2) NOT NULL,
	[usro_status] [varchar](15) NULL,
	[usro_modified_date] [datetime] NULL,
 CONSTRAINT [pk_usro] PRIMARY KEY CLUSTERED
(
	[usro_entityid] ASC,
	[usro_role_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [users].[users]    Script Date: 11/30/2023 2:13:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [users].[users](
	[user_entityid] [int] NOT NULL,
	[user_name] [varchar](15) NOT NULL,
	[user_password] [varchar](256) NULL,
	[user_full_name] [varchar](85) NULL,
	[user_email] [varchar](25) NOT NULL,
	[user_birth_place] [varchar](55) NULL,
	[user_birth_date] [datetime] NULL,
	[user_national_id] [varchar](20) NOT NULL,
	[user_npwp] [varchar](35) NULL,
	[user_photo] [varchar](256) NULL,
	[user_modified_date] [datetime] NULL,
PRIMARY KEY CLUSTERED
(
	[user_entityid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [customer].[customer_claim] ([cucl_creq_entityid], [cucl_create_date], [cucl_event_price], [cucl_subtotal], [cucl_reason]) VALUES (2, CAST(N'2023-10-10T00:00:00.000' AS DateTime), 100000.0000, 100000.0000, N'wkwkwkwk')
GO
INSERT [customer].[customer_request] ([creq_entityid], [creq_create_date], [creq_status], [creq_type], [creq_modified_date], [creq_cust_entityid], [creq_agen_entityid]) VALUES (2, CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'OPEN', N'POLIS', CAST(N'2023-10-10T00:00:00.000' AS DateTime), NULL, NULL)
GO
INSERT [hr].[employees] ([emp_entityid], [emp_name], [emp_join_date], [emp_type], [emp_status], [emp_graduate], [emp_net_salary], [emp_account_number], [emp_modified_date], [emp_job_code]) VALUES (1, N'BARA', CAST(N'2009-10-10T00:00:00.000' AS DateTime), N'PERMANENT', N'ACTIVE', N'S1', 2000000.0000, N'BARA123', CAST(N'2023-11-19T00:00:00.000' AS DateTime), N'1')
GO
INSERT [hr].[job_type] ([job_code], [job_modified_date], [job_desc], [job_rate_min], [job_rate_max]) VALUES (N'1', CAST(N'2008-10-10T00:00:00.000' AS DateTime), NULL, NULL, NULL)
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
INSERT [mtr].[area_workgroup] ([arwg_code], [arwg_desc], [arwg_city_id]) VALUES (N'BCI-0001', N'Bandung Cimahi', 1)
INSERT [mtr].[area_workgroup] ([arwg_code], [arwg_desc], [arwg_city_id]) VALUES (N'BCY-0001', N'Bandung Cileunyi', 1)
INSERT [mtr].[area_workgroup] ([arwg_code], [arwg_desc], [arwg_city_id]) VALUES (N'BPA-0001', N'Bandung Padalarang', 1)
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
SET IDENTITY_INSERT [mtr].[category] ON

INSERT [mtr].[category] ([cate_id], [cate_name]) VALUES (1, N'Kategori 1')
INSERT [mtr].[category] ([cate_id], [cate_name]) VALUES (2, N'Kategori 2')
INSERT [mtr].[category] ([cate_id], [cate_name]) VALUES (3, N'Kategori 3')
INSERT [mtr].[category] ([cate_id], [cate_name]) VALUES (4, N'Kategori 4')
INSERT [mtr].[category] ([cate_id], [cate_name]) VALUES (5, N'Kategori Extend')
SET IDENTITY_INSERT [mtr].[category] OFF
GO
SET IDENTITY_INSERT [mtr].[cities] ON

INSERT [mtr].[cities] ([city_id], [city_name], [city_prov_id]) VALUES (1, N'Bandung', 2)
INSERT [mtr].[cities] ([city_id], [city_name], [city_prov_id]) VALUES (2, N'Bogor', 2)
INSERT [mtr].[cities] ([city_id], [city_name], [city_prov_id]) VALUES (3, N'Cianjur', 2)
INSERT [mtr].[cities] ([city_id], [city_name], [city_prov_id]) VALUES (4, N'Cirebon', 2)
INSERT [mtr].[cities] ([city_id], [city_name], [city_prov_id]) VALUES (5, N'Bekasi', 2)
SET IDENTITY_INSERT [mtr].[cities] OFF
GO
INSERT [mtr].[insurance_type] ([inty_name], [inty_desc]) VALUES (N'Comprehensive', N'Jaminan Tambahan Komprehensive')
INSERT [mtr].[insurance_type] ([inty_name], [inty_desc]) VALUES (N'Total Loss Only', N'Jaminan Tambahan Kerugian Total')
GO
SET IDENTITY_INSERT [mtr].[provinsi] ON

INSERT [mtr].[provinsi] ([prov_id], [prov_name], [prov_zones_id]) VALUES (1, N'DKI Jakarta', 1)
INSERT [mtr].[provinsi] ([prov_id], [prov_name], [prov_zones_id]) VALUES (2, N'Jawa Barat', 2)
SET IDENTITY_INSERT [mtr].[provinsi] OFF
GO
INSERT [mtr].[region_plat] ([regp_name], [regp_desc], [regp_prov_id]) VALUES (N'B', NULL, 1)
INSERT [mtr].[region_plat] ([regp_name], [regp_desc], [regp_prov_id]) VALUES (N'D', NULL, 2)
INSERT [mtr].[region_plat] ([regp_name], [regp_desc], [regp_prov_id]) VALUES (N'E', NULL, 2)
INSERT [mtr].[region_plat] ([regp_name], [regp_desc], [regp_prov_id]) VALUES (N'F', NULL, 2)
INSERT [mtr].[region_plat] ([regp_name], [regp_desc], [regp_prov_id]) VALUES (N'T', NULL, 2)
INSERT [mtr].[region_plat] ([regp_name], [regp_desc], [regp_prov_id]) VALUES (N'Z', NULL, 2)
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
SET IDENTITY_INSERT [mtr].[template_service_task] ON

INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (1, N'REVIEW & CHECK CUSTOMER REQUEST', 1, N'WORKGROUP', NULL, 1)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (2, N'PROSPEK CUSTOMER POTENTIAL', 1, N'WORKGROUP', NULL, 2)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (3, N'PREMI SCHEMA', 1, N'WORKGROUP', NULL, 3)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (4, N'LEGAL DOCUMENT SIGNED', 1, N'WORKGROUP', NULL, 4)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (5, N'CREATE POLIS', NULL, N'SYSTEM', NULL, NULL)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (6, N'CLOSE ORDER', NULL, N'SYSTEM', NULL, NULL)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (7, N'GENERATE POLIS NUMBER', 2, N'SYSTEM', NULL, 1)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (8, N'GENERATE PREMI', 2, N'SYSTEM', NULL, 2)
INSERT [mtr].[template_service_task] ([testa_id], [testa_name], [testa_tety_id], [testa_group], [testa_callmethod], [testa_seqorder]) VALUES (9, N'GENERATE VIRTUAL ACCOUNT', 2, N'SYSTEM', NULL, 3)
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

INSERT [mtr].[template_task_workorder] ([tewo_id], [tewo_name], [tewo_value], [tewo_testa_id]) VALUES (1, N'CHECK UMUR', NULL, NULL)
INSERT [mtr].[template_task_workorder] ([tewo_id], [tewo_name], [tewo_value], [tewo_testa_id]) VALUES (2, N'RELATE GOVERNMENT', NULL, NULL)
INSERT [mtr].[template_task_workorder] ([tewo_id], [tewo_name], [tewo_value], [tewo_testa_id]) VALUES (3, N'PREMI SCHEMA', N'COMPREHENSIVE', NULL)
INSERT [mtr].[template_task_workorder] ([tewo_id], [tewo_name], [tewo_value], [tewo_testa_id]) VALUES (4, N'DOCUMENT DISETUJUI', N'OK', NULL)
INSERT [mtr].[template_task_workorder] ([tewo_id], [tewo_name], [tewo_value], [tewo_testa_id]) VALUES (5, N'COPY VALUE', N'Y', NULL)
SET IDENTITY_INSERT [mtr].[template_task_workorder] OFF
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
SET IDENTITY_INSERT [mtr].[zones] ON

INSERT [mtr].[zones] ([zones_id], [zones_name]) VALUES (1, N'Wilayah 1')
INSERT [mtr].[zones] ([zones_id], [zones_name]) VALUES (2, N'Wilayah 2')
INSERT [mtr].[zones] ([zones_id], [zones_name]) VALUES (3, N'Wilayah 3')
SET IDENTITY_INSERT [mtr].[zones] OFF
GO
INSERT [partners].[batch_partner_invoice] ([bpin_invoiceNo], [bpin_created_on], [bpin_subtotal], [bpin_tax], [bpin_accountNo], [bpin_status], [bpin_paid_date], [bpin_serv_id], [bpin_patrn_entityid], [bpin_patr_trxno]) VALUES (N'1', CAST(N'2023-10-10T00:00:00.000' AS DateTime), 100000.0000, 10000.0000, N'1', N'ACTIVE', CAST(N'2023-10-10T00:00:00.000' AS DateTime), 1, 3, N'TRANSAKSI1')
GO
INSERT [partners].[partner_area_workgroup] ([pawo_patr_entityid], [pawo_arwg_code], [pawo_user_entityid], [pawo_status], [pawo_modified_date]) VALUES (3, N'BCI-0001', 3, N'status', CAST(N'2023-10-10T00:00:00.000' AS DateTime))
GO
INSERT [partners].[partner_contacts] ([paco_patrn_entityid], [paco_user_entityid], [paco_status]) VALUES (3, 3, N'status')
GO
INSERT [partners].[partners] ([part_entityid], [part_name], [part_address], [part_join_date], [part_accountNo], [part_npwp], [part_status], [part_modified_date], [part_city_id]) VALUES (3, N'tengku', N'jakarta', CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'082123456', N'1234', N'ACTIVE', CAST(N'2023-10-10T00:00:00.000' AS DateTime), 2)
GO
INSERT [payment].[banks] ([bank_entityid], [bank_name], [bank_desc]) VALUES (1, N'BBARA', N'DESC')
GO
INSERT [payment].[fintech] ([fint_entityid], [fint_name], [fint_desc]) VALUES (2, N'FIADI', N'desc')
GO
INSERT [payment].[payment_transactions] ([patr_trxno], [patr_created_on], [patr_debet], [patr_credit], [patr_usac_accountNo_from], [patr_usac_accountNo_to], [patr_type], [patr_invoice_no], [patr_notes], [patr_trxno_rev]) VALUES (N'TRANSAKSI1', CAST(N'2023-10-10T00:00:00.000' AS DateTime), 10000.0000, 1000.0000, 1, 2, N'SALARY', N'123', N'NOTES', N'TRANSAKSI1')
GO
SET IDENTITY_INSERT [payment].[user_accounts] ON

INSERT [payment].[user_accounts] ([usac_id], [usac_accountno], [usac_debet], [usac_credit], [usac_type], [usac_bank_entityid], [usac_fint_entityid], [usac_user_entityid]) VALUES (1, N'ACC', 10000.0000, 1000.0000, N'BANK', 1, 2, 1)
SET IDENTITY_INSERT [payment].[user_accounts] OFF
GO
SET IDENTITY_INSERT [so].[service_premi_credit] ON

INSERT [so].[service_premi_credit] ([secr_id], [secr_serv_id], [secr_year], [secr_premi_debet], [secr_premi_credit], [secr_trx_date], [secr_duedate], [secr_patr_trxno]) VALUES (1, 1, CAST(N'2023-10-10T00:00:00.000' AS DateTime), 10000.0000, 10000.0000, CAST(N'2023-10-10T00:00:00.000' AS DateTime), CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'TRANSAKSI1')
SET IDENTITY_INSERT [so].[service_premi_credit] OFF
GO
SET IDENTITY_INSERT [so].[services] ON

INSERT [so].[services] ([serv_id], [serv_created_on], [serv_type], [serv_insuranceNo], [serv_vehicleNo], [serv_startdate], [serv_enddate], [serv_status], [serv_serv_id], [serv_cust_entityid], [serv_creq_entityid]) VALUES (1, CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'POLIS', N'D 123 AB', N'1', CAST(N'2023-10-10T00:00:00.000' AS DateTime), CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'ACTIVE', NULL, 2, 2)
INSERT [so].[services] ([serv_id], [serv_created_on], [serv_type], [serv_insuranceNo], [serv_vehicleNo], [serv_startdate], [serv_enddate], [serv_status], [serv_serv_id], [serv_cust_entityid], [serv_creq_entityid]) VALUES (2, CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'POLIS', N'D 123 AB', N'1', CAST(N'2023-10-10T00:00:00.000' AS DateTime), CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'ACTIVE', 1, 2, 2)
SET IDENTITY_INSERT [so].[services] OFF
GO
SET IDENTITY_INSERT [users].[business_entity] ON

INSERT [users].[business_entity] ([entityid], [entity_modified_date]) VALUES (1, CAST(N'2023-05-07T00:00:00.000' AS DateTime))
INSERT [users].[business_entity] ([entityid], [entity_modified_date]) VALUES (2, CAST(N'2023-10-10T00:00:00.000' AS DateTime))
INSERT [users].[business_entity] ([entityid], [entity_modified_date]) VALUES (3, CAST(N'2023-10-10T00:00:00.000' AS DateTime))
SET IDENTITY_INSERT [users].[business_entity] OFF
GO
INSERT [users].[roles] ([role_name], [role_description]) VALUES (N'CU', N'customers')
INSERT [users].[roles] ([role_name], [role_description]) VALUES (N'EM', N'employees')
INSERT [users].[roles] ([role_name], [role_description]) VALUES (N'PC', N'potential customer')
INSERT [users].[roles] ([role_name], [role_description]) VALUES (N'PR', N'partner')
GO
INSERT [users].[user_address] ([usdr_id], [usdr_entityid], [usdr_address1], [usdr_address2], [usdr_modified_date], [usdr_city_id]) VALUES (1, 1, N'JAKARTA', N'JAKARTA', CAST(N'2023-10-10T00:00:00.000' AS DateTime), 2)
GO
INSERT [users].[user_phone] ([usph_entityid], [usph_phone_number], [usph_phone_type], [usph_mime], [usph_status], [usph_modified_date]) VALUES (1, N'123455677', N'HOME', N'WKWKWK', N'NORMAL', CAST(N'2023-10-10T00:00:00.000' AS DateTime))
GO
INSERT [users].[user_roles] ([usro_entityid], [usro_role_name], [usro_status], [usro_modified_date]) VALUES (1, N'EM', N'ACTIVE', CAST(N'2023-11-19T00:00:00.000' AS DateTime))
INSERT [users].[user_roles] ([usro_entityid], [usro_role_name], [usro_status], [usro_modified_date]) VALUES (2, N'CU', N'ACTIVE', CAST(N'2023-10-10T00:00:00.000' AS DateTime))
GO
INSERT [users].[users] ([user_entityid], [user_name], [user_password], [user_full_name], [user_email], [user_birth_place], [user_birth_date], [user_national_id], [user_npwp], [user_photo], [user_modified_date]) VALUES (1, N'bara', N'123', N'muhammadbara', N'bara@gmail.com', N'jakarta', CAST(N'2001-01-27T00:00:00.000' AS DateTime), N'malay', N'1234bara', NULL, CAST(N'2023-11-19T00:00:00.000' AS DateTime))
INSERT [users].[users] ([user_entityid], [user_name], [user_password], [user_full_name], [user_email], [user_birth_place], [user_birth_date], [user_national_id], [user_npwp], [user_photo], [user_modified_date]) VALUES (2, N'adi', N'321', N'adiadi', N'adi@gmail.com', N'jakarta', CAST(N'2001-10-10T00:00:00.000' AS DateTime), N'indo', N'1234adi', NULL, CAST(N'2023-10-10T00:00:00.000' AS DateTime))
INSERT [users].[users] ([user_entityid], [user_name], [user_password], [user_full_name], [user_email], [user_birth_place], [user_birth_date], [user_national_id], [user_npwp], [user_photo], [user_modified_date]) VALUES (3, N'tengku', N'231', N'tengkudev', N'tengku@gmail.com', N'jakarta', CAST(N'2002-10-10T00:00:00.000' AS DateTime), N'thai', N'3214tngku', NULL, CAST(N'2023-10-10T00:00:00.000' AS DateTime))
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__customer__E9035C582CEBD193]    Script Date: 11/30/2023 2:13:07 PM ******/
ALTER TABLE [customer].[customer_insc_assets] ADD UNIQUE NONCLUSTERED
(
	[cias_police_number] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__car_bran__750DD7D53FC5B5BD]    Script Date: 11/30/2023 2:13:07 PM ******/
ALTER TABLE [mtr].[car_brands] ADD UNIQUE NONCLUSTERED
(
	[cabr_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__car_mode__D13ADFA2D65A8CCF]    Script Date: 11/30/2023 2:13:07 PM ******/
ALTER TABLE [mtr].[car_models] ADD UNIQUE NONCLUSTERED
(
	[carm_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__car_seri__92361ED86403F9C7]    Script Date: 11/30/2023 2:13:07 PM ******/
ALTER TABLE [mtr].[car_series] ADD UNIQUE NONCLUSTERED
(
	[cars_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__cities__1AA4F7B5BFF5F69F]    Script Date: 11/30/2023 2:13:07 PM ******/
ALTER TABLE [mtr].[cities] ADD UNIQUE NONCLUSTERED
(
	[city_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__provinsi__852498461F4E2B81]    Script Date: 11/30/2023 2:13:07 PM ******/
ALTER TABLE [mtr].[provinsi] ADD UNIQUE NONCLUSTERED
(
	[prov_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__template__F5145B120C8AADBC]    Script Date: 11/30/2023 2:13:07 PM ******/
ALTER TABLE [mtr].[template_type] ADD UNIQUE NONCLUSTERED
(
	[tety_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__banks__AEBE0980CB139021]    Script Date: 11/30/2023 2:13:07 PM ******/
ALTER TABLE [payment].[banks] ADD UNIQUE NONCLUSTERED
(
	[bank_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__fintech__96EC427444C847DA]    Script Date: 11/30/2023 2:13:07 PM ******/
ALTER TABLE [payment].[fintech] ADD UNIQUE NONCLUSTERED
(
	[fint_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__user_acc__87A4C64B6DA57024]    Script Date: 11/30/2023 2:13:07 PM ******/
ALTER TABLE [payment].[user_accounts] ADD UNIQUE NONCLUSTERED
(
	[usac_accountno] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__users__58883F8627CBD45D]    Script Date: 11/30/2023 2:13:07 PM ******/
ALTER TABLE [users].[users] ADD UNIQUE NONCLUSTERED
(
	[user_npwp] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__users__60A5BA8FC0EEDF16]    Script Date: 11/30/2023 2:13:07 PM ******/
ALTER TABLE [users].[users] ADD UNIQUE NONCLUSTERED
(
	[user_national_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__users__7C9273C4B7B5A88D]    Script Date: 11/30/2023 2:13:07 PM ******/
ALTER TABLE [users].[users] ADD UNIQUE NONCLUSTERED
(
	[user_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [customer].[customer_claim]  WITH CHECK ADD  CONSTRAINT [FK_CUCLCREQ] FOREIGN KEY([cucl_creq_entityid])
REFERENCES [customer].[customer_request] ([creq_entityid])
GO
ALTER TABLE [customer].[customer_claim] CHECK CONSTRAINT [FK_CUCLCREQ]
GO
ALTER TABLE [customer].[customer_insc_assets]  WITH CHECK ADD  CONSTRAINT [FK_CIASCARS] FOREIGN KEY([cias_cars_id])
REFERENCES [mtr].[car_series] ([cars_id])
GO
ALTER TABLE [customer].[customer_insc_assets] CHECK CONSTRAINT [FK_CIASCARS]
GO
ALTER TABLE [customer].[customer_insc_assets]  WITH CHECK ADD  CONSTRAINT [FK_CIASCITY] FOREIGN KEY([cias_city_id])
REFERENCES [mtr].[cities] ([city_id])
GO
ALTER TABLE [customer].[customer_insc_assets] CHECK CONSTRAINT [FK_CIASCITY]
GO
ALTER TABLE [customer].[customer_insc_assets]  WITH CHECK ADD  CONSTRAINT [FK_CIASCREQ] FOREIGN KEY([cias_creq_entityid])
REFERENCES [customer].[customer_request] ([creq_entityid])
GO
ALTER TABLE [customer].[customer_insc_assets] CHECK CONSTRAINT [FK_CIASCREQ]
GO
ALTER TABLE [customer].[customer_insc_assets]  WITH CHECK ADD  CONSTRAINT [FK_CIASINTY] FOREIGN KEY([cias_inty_name])
REFERENCES [mtr].[insurance_type] ([inty_name])
GO
ALTER TABLE [customer].[customer_insc_assets] CHECK CONSTRAINT [FK_CIASINTY]
GO
ALTER TABLE [customer].[customer_insc_doc]  WITH CHECK ADD  CONSTRAINT [FK_CADOCCREQ] FOREIGN KEY([cadoc_creq_entityid])
REFERENCES [customer].[customer_insc_assets] ([cias_creq_entityid])
GO
ALTER TABLE [customer].[customer_insc_doc] CHECK CONSTRAINT [FK_CADOCCREQ]
GO
ALTER TABLE [customer].[customer_insc_extend]  WITH CHECK ADD  CONSTRAINT [FK_CUEXCREQ] FOREIGN KEY([cuex_creq_entityid])
REFERENCES [customer].[customer_insc_assets] ([cias_creq_entityid])
GO
ALTER TABLE [customer].[customer_insc_extend] CHECK CONSTRAINT [FK_CUEXCREQ]
GO
ALTER TABLE [customer].[customer_request]  WITH CHECK ADD  CONSTRAINT [FK_CREQAGEN] FOREIGN KEY([creq_agen_entityid])
REFERENCES [hr].[employees] ([emp_entityid])
GO
ALTER TABLE [customer].[customer_request] CHECK CONSTRAINT [FK_CREQAGEN]
GO
ALTER TABLE [customer].[customer_request]  WITH CHECK ADD  CONSTRAINT [FK_CREQCUST_ENTITY] FOREIGN KEY([creq_entityid])
REFERENCES [users].[business_entity] ([entityid])
GO
ALTER TABLE [customer].[customer_request] CHECK CONSTRAINT [FK_CREQCUST_ENTITY]
GO
ALTER TABLE [customer].[customer_request]  WITH CHECK ADD  CONSTRAINT [FK_CREQENTITY] FOREIGN KEY([creq_cust_entityid])
REFERENCES [users].[users] ([user_entityid])
GO
ALTER TABLE [customer].[customer_request] CHECK CONSTRAINT [FK_CREQENTITY]
GO
ALTER TABLE [hr].[employee_are_workgroup]  WITH CHECK ADD FOREIGN KEY([eawg_entityid])
REFERENCES [hr].[employees] ([emp_entityid])
GO
ALTER TABLE [hr].[employee_are_workgroup]  WITH CHECK ADD FOREIGN KEY([eawg_arwg_code])
REFERENCES [mtr].[area_workgroup] ([arwg_code])
GO
ALTER TABLE [hr].[employee_salary_detail]  WITH CHECK ADD FOREIGN KEY([emsa_emp_entityid])
REFERENCES [hr].[employees] ([emp_entityid])
GO
ALTER TABLE [hr].[employees]  WITH CHECK ADD FOREIGN KEY([emp_entityid])
REFERENCES [users].[users] ([user_entityid])
GO
ALTER TABLE [hr].[employees]  WITH CHECK ADD FOREIGN KEY([emp_job_code])
REFERENCES [hr].[job_type] ([job_code])
GO
ALTER TABLE [mtr].[area_workgroup]  WITH CHECK ADD FOREIGN KEY([arwg_city_id])
REFERENCES [mtr].[cities] ([city_id])
GO
ALTER TABLE [mtr].[car_models]  WITH CHECK ADD FOREIGN KEY([carm_cabr_id])
REFERENCES [mtr].[car_brands] ([cabr_id])
GO
ALTER TABLE [mtr].[car_series]  WITH CHECK ADD FOREIGN KEY([cars_carm_id])
REFERENCES [mtr].[car_models] ([carm_id])
GO
ALTER TABLE [mtr].[cities]  WITH CHECK ADD FOREIGN KEY([city_prov_id])
REFERENCES [mtr].[provinsi] ([prov_id])
GO
ALTER TABLE [mtr].[provinsi]  WITH CHECK ADD FOREIGN KEY([prov_zones_id])
REFERENCES [mtr].[zones] ([zones_id])
GO
ALTER TABLE [mtr].[region_plat]  WITH CHECK ADD FOREIGN KEY([regp_prov_id])
REFERENCES [mtr].[provinsi] ([prov_id])
GO
ALTER TABLE [mtr].[template_insurance_premi]  WITH CHECK ADD FOREIGN KEY([temi_zones_id])
REFERENCES [mtr].[zones] ([zones_id])
GO
ALTER TABLE [mtr].[template_insurance_premi]  WITH CHECK ADD FOREIGN KEY([temi_inty_name])
REFERENCES [mtr].[insurance_type] ([inty_name])
GO
ALTER TABLE [mtr].[template_insurance_premi]  WITH CHECK ADD FOREIGN KEY([temi_cate_id])
REFERENCES [mtr].[category] ([cate_id])
GO
ALTER TABLE [mtr].[template_service_task]  WITH CHECK ADD FOREIGN KEY([testa_tety_id])
REFERENCES [mtr].[template_type] ([tety_id])
GO
ALTER TABLE [mtr].[template_task_workorder]  WITH CHECK ADD FOREIGN KEY([tewo_testa_id])
REFERENCES [mtr].[template_service_task] ([testa_id])
GO
ALTER TABLE [partners].[batch_partner_invoice]  WITH CHECK ADD  CONSTRAINT [fk_bpin_patr_trxno] FOREIGN KEY([bpin_patr_trxno])
REFERENCES [payment].[payment_transactions] ([patr_trxno])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [partners].[batch_partner_invoice] CHECK CONSTRAINT [fk_bpin_patr_trxno]
GO
ALTER TABLE [partners].[batch_partner_invoice]  WITH CHECK ADD  CONSTRAINT [fk_bpin_patrn_entityid] FOREIGN KEY([bpin_patrn_entityid])
REFERENCES [partners].[partners] ([part_entityid])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [partners].[batch_partner_invoice] CHECK CONSTRAINT [fk_bpin_patrn_entityid]
GO
ALTER TABLE [partners].[batch_partner_invoice]  WITH CHECK ADD  CONSTRAINT [fk_bpin_serv_id] FOREIGN KEY([bpin_serv_id])
REFERENCES [so].[services] ([serv_id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [partners].[batch_partner_invoice] CHECK CONSTRAINT [fk_bpin_serv_id]
GO
ALTER TABLE [partners].[partner_area_workgroup]  WITH CHECK ADD  CONSTRAINT [fk_pawo_arwg_code] FOREIGN KEY([pawo_arwg_code])
REFERENCES [mtr].[area_workgroup] ([arwg_code])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [partners].[partner_area_workgroup] CHECK CONSTRAINT [fk_pawo_arwg_code]
GO
ALTER TABLE [partners].[partner_area_workgroup]  WITH CHECK ADD  CONSTRAINT [fk_pawo_patr_user] FOREIGN KEY([pawo_patr_entityid], [pawo_user_entityid])
REFERENCES [partners].[partner_contacts] ([paco_patrn_entityid], [paco_user_entityid])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [partners].[partner_area_workgroup] CHECK CONSTRAINT [fk_pawo_patr_user]
GO
ALTER TABLE [partners].[partner_contacts]  WITH CHECK ADD  CONSTRAINT [fk_paco_patrn_entityid] FOREIGN KEY([paco_patrn_entityid])
REFERENCES [partners].[partners] ([part_entityid])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [partners].[partner_contacts] CHECK CONSTRAINT [fk_paco_patrn_entityid]
GO
ALTER TABLE [partners].[partner_contacts]  WITH CHECK ADD  CONSTRAINT [fk_paco_user_entityid] FOREIGN KEY([paco_user_entityid])
REFERENCES [users].[users] ([user_entityid])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [partners].[partner_contacts] CHECK CONSTRAINT [fk_paco_user_entityid]
GO
ALTER TABLE [partners].[partners]  WITH CHECK ADD  CONSTRAINT [fk_part_city_id] FOREIGN KEY([part_city_id])
REFERENCES [mtr].[cities] ([city_id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [partners].[partners] CHECK CONSTRAINT [fk_part_city_id]
GO
ALTER TABLE [partners].[partners]  WITH CHECK ADD  CONSTRAINT [fk_part_entityid] FOREIGN KEY([part_entityid])
REFERENCES [users].[business_entity] ([entityid])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [partners].[partners] CHECK CONSTRAINT [fk_part_entityid]
GO
ALTER TABLE [payment].[banks]  WITH CHECK ADD  CONSTRAINT [fk_bank_entityid] FOREIGN KEY([bank_entityid])
REFERENCES [users].[business_entity] ([entityid])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [payment].[banks] CHECK CONSTRAINT [fk_bank_entityid]
GO
ALTER TABLE [payment].[fintech]  WITH CHECK ADD  CONSTRAINT [fk_fint_entityid] FOREIGN KEY([fint_entityid])
REFERENCES [users].[business_entity] ([entityid])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [payment].[fintech] CHECK CONSTRAINT [fk_fint_entityid]
GO
ALTER TABLE [payment].[payment_transactions]  WITH CHECK ADD  CONSTRAINT [fk_patr_trxno_rev] FOREIGN KEY([patr_trxno_rev])
REFERENCES [payment].[payment_transactions] ([patr_trxno])
GO
ALTER TABLE [payment].[payment_transactions] CHECK CONSTRAINT [fk_patr_trxno_rev]
GO
ALTER TABLE [payment].[user_accounts]  WITH CHECK ADD  CONSTRAINT [fk_usac_bank_entityid] FOREIGN KEY([usac_bank_entityid])
REFERENCES [payment].[banks] ([bank_entityid])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [payment].[user_accounts] CHECK CONSTRAINT [fk_usac_bank_entityid]
GO
ALTER TABLE [payment].[user_accounts]  WITH CHECK ADD  CONSTRAINT [fk_usac_fint_entityid] FOREIGN KEY([usac_fint_entityid])
REFERENCES [payment].[fintech] ([fint_entityid])
GO
ALTER TABLE [payment].[user_accounts] CHECK CONSTRAINT [fk_usac_fint_entityid]
GO
ALTER TABLE [payment].[user_accounts]  WITH CHECK ADD  CONSTRAINT [fk_user_entityid] FOREIGN KEY([usac_user_entityid])
REFERENCES [users].[users] ([user_entityid])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [payment].[user_accounts] CHECK CONSTRAINT [fk_user_entityid]
GO
ALTER TABLE [so].[claim_asset_evidence]  WITH CHECK ADD  CONSTRAINT [fk_caev_part_entityid] FOREIGN KEY([caev_part_entityid])
REFERENCES [partners].[partners] ([part_entityid])
GO
ALTER TABLE [so].[claim_asset_evidence] CHECK CONSTRAINT [fk_caev_part_entityid]
GO
ALTER TABLE [so].[claim_asset_evidence]  WITH CHECK ADD  CONSTRAINT [fk_caev_sero_id] FOREIGN KEY([caev_sero_id])
REFERENCES [so].[service_orders] ([sero_id])
GO
ALTER TABLE [so].[claim_asset_evidence] CHECK CONSTRAINT [fk_caev_sero_id]
GO
ALTER TABLE [so].[claim_asset_sparepart]  WITH CHECK ADD  CONSTRAINT [fk_casp_part_entityid] FOREIGN KEY([casp_part_entityid])
REFERENCES [partners].[partners] ([part_entityid])
GO
ALTER TABLE [so].[claim_asset_sparepart] CHECK CONSTRAINT [fk_casp_part_entityid]
GO
ALTER TABLE [so].[claim_asset_sparepart]  WITH CHECK ADD  CONSTRAINT [fk_casp_sero_id] FOREIGN KEY([casp_sero_id])
REFERENCES [so].[service_orders] ([sero_id])
GO
ALTER TABLE [so].[claim_asset_sparepart] CHECK CONSTRAINT [fk_casp_sero_id]
GO
ALTER TABLE [so].[service_order_tasks]  WITH CHECK ADD  CONSTRAINT [fk_seot_arwg_code] FOREIGN KEY([seot_arwg_code])
REFERENCES [mtr].[area_workgroup] ([arwg_code])
GO
ALTER TABLE [so].[service_order_tasks] CHECK CONSTRAINT [fk_seot_arwg_code]
GO
ALTER TABLE [so].[service_order_tasks]  WITH CHECK ADD  CONSTRAINT [fk_seot_sero_id] FOREIGN KEY([seot_sero_id])
REFERENCES [so].[service_orders] ([sero_id])
GO
ALTER TABLE [so].[service_order_tasks] CHECK CONSTRAINT [fk_seot_sero_id]
GO
ALTER TABLE [so].[service_order_workorder]  WITH CHECK ADD  CONSTRAINT [fk_sowo_seot_id] FOREIGN KEY([sowo_seot_id])
REFERENCES [so].[service_order_tasks] ([seot_id])
GO
ALTER TABLE [so].[service_order_workorder] CHECK CONSTRAINT [fk_sowo_seot_id]
GO
ALTER TABLE [so].[service_orders]  WITH CHECK ADD  CONSTRAINT [fk_sero_agent_entityid] FOREIGN KEY([sero_agent_entityid])
REFERENCES [hr].[employees] ([emp_entityid])
GO
ALTER TABLE [so].[service_orders] CHECK CONSTRAINT [fk_sero_agent_entityid]
GO
ALTER TABLE [so].[service_orders]  WITH CHECK ADD  CONSTRAINT [fk_sero_arwg_code] FOREIGN KEY([sero_arwg_code])
REFERENCES [mtr].[area_workgroup] ([arwg_code])
GO
ALTER TABLE [so].[service_orders] CHECK CONSTRAINT [fk_sero_arwg_code]
GO
ALTER TABLE [so].[service_orders]  WITH CHECK ADD  CONSTRAINT [fk_sero_sero_id] FOREIGN KEY([sero_sero_id])
REFERENCES [so].[service_orders] ([sero_id])
GO
ALTER TABLE [so].[service_orders] CHECK CONSTRAINT [fk_sero_sero_id]
GO
ALTER TABLE [so].[service_orders]  WITH CHECK ADD  CONSTRAINT [fk_sero_serv_id] FOREIGN KEY([sero_serv_id])
REFERENCES [so].[services] ([serv_id])
GO
ALTER TABLE [so].[service_orders] CHECK CONSTRAINT [fk_sero_serv_id]
GO
ALTER TABLE [so].[service_premi]  WITH CHECK ADD  CONSTRAINT [fk_semi_serv_id] FOREIGN KEY([semi_serv_id])
REFERENCES [so].[services] ([serv_id])
GO
ALTER TABLE [so].[service_premi] CHECK CONSTRAINT [fk_semi_serv_id]
GO
ALTER TABLE [so].[service_premi_credit]  WITH CHECK ADD  CONSTRAINT [fk_secr_patr_trxno] FOREIGN KEY([secr_patr_trxno])
REFERENCES [payment].[payment_transactions] ([patr_trxno])
GO
ALTER TABLE [so].[service_premi_credit] CHECK CONSTRAINT [fk_secr_patr_trxno]
GO
ALTER TABLE [so].[service_premi_credit]  WITH CHECK ADD  CONSTRAINT [fk_secr_serv_id] FOREIGN KEY([secr_serv_id])
REFERENCES [so].[services] ([serv_id])
GO
ALTER TABLE [so].[service_premi_credit] CHECK CONSTRAINT [fk_secr_serv_id]
GO
ALTER TABLE [so].[services]  WITH CHECK ADD  CONSTRAINT [fk_serv_creq_entityid] FOREIGN KEY([serv_creq_entityid])
REFERENCES [customer].[customer_request] ([creq_entityid])
GO
ALTER TABLE [so].[services] CHECK CONSTRAINT [fk_serv_creq_entityid]
GO
ALTER TABLE [so].[services]  WITH CHECK ADD  CONSTRAINT [fk_serv_cust_entityid] FOREIGN KEY([serv_cust_entityid])
REFERENCES [users].[users] ([user_entityid])
GO
ALTER TABLE [so].[services] CHECK CONSTRAINT [fk_serv_cust_entityid]
GO
ALTER TABLE [so].[services]  WITH CHECK ADD  CONSTRAINT [fk_serv_serv_id] FOREIGN KEY([serv_serv_id])
REFERENCES [so].[services] ([serv_id])
GO
ALTER TABLE [so].[services] CHECK CONSTRAINT [fk_serv_serv_id]
GO
ALTER TABLE [users].[user_address]  WITH CHECK ADD  CONSTRAINT [fk_address_cities] FOREIGN KEY([usdr_city_id])
REFERENCES [mtr].[cities] ([city_id])
GO
ALTER TABLE [users].[user_address] CHECK CONSTRAINT [fk_address_cities]
GO
ALTER TABLE [users].[user_address]  WITH CHECK ADD  CONSTRAINT [fk_entity_address_users] FOREIGN KEY([usdr_entityid])
REFERENCES [users].[users] ([user_entityid])
GO
ALTER TABLE [users].[user_address] CHECK CONSTRAINT [fk_entity_address_users]
GO
ALTER TABLE [users].[user_phone]  WITH CHECK ADD  CONSTRAINT [fk_entityid_phone] FOREIGN KEY([usph_entityid])
REFERENCES [users].[users] ([user_entityid])
GO
ALTER TABLE [users].[user_phone] CHECK CONSTRAINT [fk_entityid_phone]
GO
ALTER TABLE [users].[user_roles]  WITH CHECK ADD FOREIGN KEY([usro_role_name])
REFERENCES [users].[roles] ([role_name])
GO
ALTER TABLE [users].[user_roles]  WITH CHECK ADD  CONSTRAINT [fk_entity_usro_users] FOREIGN KEY([usro_entityid])
REFERENCES [users].[users] ([user_entityid])
GO
ALTER TABLE [users].[user_roles] CHECK CONSTRAINT [fk_entity_usro_users]
GO
ALTER TABLE [users].[users]  WITH CHECK ADD FOREIGN KEY([user_entityid])
REFERENCES [users].[business_entity] ([entityid])
GO
ALTER TABLE [customer].[customer_insc_assets]  WITH CHECK ADD CHECK  (([cias_isNewChar]='N' OR [cias_isNewChar]='Y'))
GO
ALTER TABLE [mtr].[template_insurance_premi]  WITH CHECK ADD CHECK  (([temi_type]='Extend' OR [temi_type]='Category'))
GO
ALTER TABLE [payment].[user_accounts]  WITH CHECK ADD CHECK  (([usac_type]='FINTECH' OR [usac_type]='BANK'))
GO
ALTER TABLE [so].[services]  WITH CHECK ADD CHECK  (([serv_status]='INACTIVE' OR [serv_status]='ACTIVE'))
GO
ALTER TABLE [users].[user_phone]  WITH CHECK ADD CHECK  (([usph_phone_type]='HOME' OR [usph_phone_type]='HP'))
GO
ALTER TABLE [users].[user_roles]  WITH CHECK ADD CHECK  (([usro_status]='INACTIVE' OR [usro_status]='ACTIVE'))
GO
ALTER TABLE so.service_orders ADD sero_part_id int
GO
ALTER TABLE so.service_orders ADD CONSTRAINT fk_sero_part_entityid
FOREIGN KEY (sero_part_id) REFERENCES partners.partners(part_entityid)
GO
USE [master]
GO
ALTER DATABASE [SmartDrive] SET  READ_WRITE
GO
