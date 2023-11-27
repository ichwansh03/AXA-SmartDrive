USE [master]
GO
/****** Object:  Database [SmartDrive]    Script Date: 11/27/2023 10:08:54 AM ******/
CREATE DATABASE [SmartDrive]
 CONTAINMENT = NONE
 ON  PRIMARY
( NAME = N'SmartDrive', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESSCA\MSSQL\DATA\SmartDrive.mdf' , SIZE = 73728KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON
( NAME = N'SmartDrive_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESSCA\MSSQL\DATA\SmartDrive_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
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
/****** Object:  Schema [customer]    Script Date: 11/20/2023 10:08:54 AM ******/
CREATE SCHEMA [customer]
GO
/****** Object:  Schema [hr]    Script Date: 11/20/2023 10:08:54 AM ******/
CREATE SCHEMA [hr]
GO
/****** Object:  Schema [mtr]    Script Date: 11/20/2023 10:08:54 AM ******/
CREATE SCHEMA [mtr]
GO
/****** Object:  Schema [partners]    Script Date: 11/20/2023 10:08:54 AM ******/
CREATE SCHEMA [partners]
GO
/****** Object:  Schema [payment]    Script Date: 11/20/2023 10:08:54 AM ******/
CREATE SCHEMA [payment]
GO
/****** Object:  Schema [so]    Script Date: 11/20/2023 10:08:54 AM ******/
CREATE SCHEMA [so]
GO
/****** Object:  Schema [users]    Script Date: 11/20/2023 10:08:54 AM ******/
CREATE SCHEMA [users]
GO
/****** Object:  Table [customer].[customer_claim]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [customer].[customer_insc_assets]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [customer].[customer_insc_doc]    Script Date: 11/20/2023 10:08:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE SEQUENCE cadoc_cuex_id start with 1 increment by 1
GO
CREATE TABLE [customer].[customer_insc_doc](
	[cadoc_id] [int]  NOT NULL,
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
/****** Object:  Table [customer].[customer_insc_extend]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [customer].[customer_request]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [hr].[batch_employee_salary]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [hr].[employee_are_workgroup]    Script Date: 11/20/2023 10:08:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [hr].[employee_are_workgroup](
	[eawag_id] [int] IDENTITY(1,1) NOT NULL,
	[eawg_entityid] [int] NOT NULL,
	[eawg_status] [varchar](15) NULL,
	[eawg_arwg_code] [varchar](15) NULL,
	[eawg_modified_date] [datetime] NULL,
PRIMARY KEY CLUSTERED
(
	[eawg_entityid] ASC,
	[eawag_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [hr].[employee_salary_detail]    Script Date: 11/20/2023 10:08:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [hr].[employee_salary_detail](
	[emsa_id] [int] NOT NULL,
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
/****** Object:  Table [hr].[employees]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [hr].[job_type]    Script Date: 11/20/2023 10:08:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [hr].[job_type](
	[job_code] [varchar](15) NOT NULL,
	[job_modified_date] [datetime] NULL,
PRIMARY KEY CLUSTERED
(
	[job_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [hr].[template_salary]    Script Date: 11/20/2023 10:08:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [hr].[template_salary](
	[tesal_id] [int] NOT NULL,
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
/****** Object:  Table [mtr].[area_workgroup]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [mtr].[car_brands]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [mtr].[car_models]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [mtr].[car_series]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [mtr].[category]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [mtr].[cities]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [mtr].[insurance_type]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [mtr].[provinsi]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [mtr].[region_plat]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [mtr].[template_insurance_premi]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [mtr].[template_service_task]    Script Date: 11/20/2023 10:08:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[template_service_task](
	[testa_id] [int] IDENTITY(1,1) NOT NULL,
	[testa_name] [varchar](55) NULL,
	testa_tety_id int null,
	[testa_group] varchar(50) NULL,
	testa_callmethod varchar(100) null,
	testa_seqorder int
PRIMARY KEY CLUSTERED
(
	[testa_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[template_task_workorder]    Script Date: 11/20/2023 10:08:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mtr].[template_task_workorder](
	[tewo_id] [int] IDENTITY(1,1) NOT NULL,
	[tewo_name] [varchar](55) NULL,
	tewo_value varchar(50) null,
	[tewo_testa_id] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[tewo_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [mtr].[template_type]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [mtr].[zones]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [partners].[batch_partner_invoice]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [partners].[partner_area_workgroup]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [partners].[partner_contacts]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [partners].[partners]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [payment].[banks]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [payment].[fintech]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [payment].[payment_transactions]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [payment].[user_accounts]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [so].[claim_asset_evidence]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [so].[claim_asset_sparepart]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [so].[service_order_tasks]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [so].[service_order_workorder]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [so].[service_orders]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [so].[service_premi]    Script Date: 11/20/2023 10:08:54 AM ******/
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
/****** Object:  Table [so].[service_premi_credit]    Script Date: 11/20/2023 10:08:55 AM ******/
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
/****** Object:  Table [so].[services]    Script Date: 11/20/2023 10:08:55 AM ******/
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
/****** Object:  Table [users].[business_entity]    Script Date: 11/20/2023 10:08:55 AM ******/
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
/****** Object:  Table [users].[roles]    Script Date: 11/20/2023 10:08:55 AM ******/
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
/****** Object:  Table [users].[user_address]    Script Date: 11/20/2023 10:08:55 AM ******/
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
/****** Object:  Table [users].[user_phone]    Script Date: 11/20/2023 10:08:55 AM ******/
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
/****** Object:  Table [users].[user_roles]    Script Date: 11/20/2023 10:08:55 AM ******/
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
/****** Object:  Table [users].[users]    Script Date: 11/20/2023 10:08:55 AM ******/
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

SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__customer__E9035C58F49FEA92]    Script Date: 11/20/2023 10:08:55 AM ******/
ALTER TABLE [customer].[customer_insc_assets] ADD UNIQUE NONCLUSTERED
(
	[cias_police_number] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__template__F9F5660C40BC369F]    Script Date: 11/20/2023 10:08:55 AM ******/
ALTER TABLE [hr].[template_salary] ADD UNIQUE NONCLUSTERED
(
	[tesal_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__car_bran__750DD7D5836409DD]    Script Date: 11/20/2023 10:08:55 AM ******/
ALTER TABLE [mtr].[car_brands] ADD UNIQUE NONCLUSTERED
(
	[cabr_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__car_mode__D13ADFA240AD2754]    Script Date: 11/20/2023 10:08:55 AM ******/
ALTER TABLE [mtr].[car_models] ADD UNIQUE NONCLUSTERED
(
	[carm_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__car_seri__92361ED84E578612]    Script Date: 11/20/2023 10:08:55 AM ******/
ALTER TABLE [mtr].[car_series] ADD UNIQUE NONCLUSTERED
(
	[cars_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__cities__1AA4F7B57D40E212]    Script Date: 11/20/2023 10:08:55 AM ******/
ALTER TABLE [mtr].[cities] ADD UNIQUE NONCLUSTERED
(
	[city_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__provinsi__85249846F6B13756]    Script Date: 11/20/2023 10:08:55 AM ******/
ALTER TABLE [mtr].[provinsi] ADD UNIQUE NONCLUSTERED
(
	[prov_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__template__F5145B1231F1C760]    Script Date: 11/20/2023 10:08:55 AM ******/
ALTER TABLE [mtr].[template_type] ADD UNIQUE NONCLUSTERED
(
	[tety_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__banks__AEBE09800508AB4A]    Script Date: 11/20/2023 10:08:55 AM ******/
ALTER TABLE [payment].[banks] ADD UNIQUE NONCLUSTERED
(
	[bank_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__fintech__96EC427415626D74]    Script Date: 11/20/2023 10:08:55 AM ******/
ALTER TABLE [payment].[fintech] ADD UNIQUE NONCLUSTERED
(
	[fint_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__user_acc__87A4C64B3E97D05D]    Script Date: 11/20/2023 10:08:55 AM ******/
ALTER TABLE [payment].[user_accounts] ADD UNIQUE NONCLUSTERED
(
	[usac_accountno] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__users__58883F86BA59D7DD]    Script Date: 11/20/2023 10:08:55 AM ******/
ALTER TABLE [users].[users] ADD UNIQUE NONCLUSTERED
(
	[user_npwp] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__users__60A5BA8F6581AE58]    Script Date: 11/20/2023 10:08:55 AM ******/
ALTER TABLE [users].[users] ADD UNIQUE NONCLUSTERED
(
	[user_national_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__users__7C9273C46E1A555F]    Script Date: 11/20/2023 10:08:55 AM ******/
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
ALTER TABLE [mtr].[template_service_task]  WITH CHECK ADD FOREIGN KEY(testa_tety_id)
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
USE [master]
GO
ALTER DATABASE [SmartDrive] SET  READ_WRITE
GO

USE [SmartDrive]
GO
INSERT mtr.car_brands values('Honda')
INSERT mtr.car_brands values('Toyota')
INSERT mtr.car_brands values('Mistubishi')
INSERT mtr.car_brands values('Suzuki')

INSERT mtr.car_models values('HRV',1)
INSERT mtr.car_models values('BRIO',1)
INSERT mtr.car_models values('Mobilio',1)

INSERT mtr.car_series values('MT-A',5,1)

INSERT mtr.category values('Kategori 1')
INSERT mtr.category values('Kategori 2')
INSERT mtr.category values('Kategori 3')
INSERT mtr.category values('Kategori 4')
INSERT mtr.category values('Kategori Extend')

INSERT mtr.zones values('Wilayah 1')
INSERT mtr.zones values('Wilayah 2')
INSERT mtr.zones values('Wilayah 3')

INSERT [mtr].[provinsi] VALUES ('DKI Jakarta', 1)
INSERT [mtr].[provinsi] VALUES ('Jawa Barat', 2)

INSERT [mtr].[cities] VALUES ('Bandung', 2)
INSERT [mtr].[cities] VALUES ('Bogor', 2)
INSERT [mtr].[cities] VALUES ('Cianjur', 2)
INSERT [mtr].[cities] VALUES ('Cirebon', 2)
INSERT [mtr].[cities] VALUES ('Bekasi', 2)

INSERT [mtr].[region_plat] (regp_name,regp_desc,regp_prov_id) VALUES ('D', null , 2)
INSERT [mtr].[region_plat] (regp_name,regp_desc,regp_prov_id) VALUES ('E', null , 2)
INSERT [mtr].[region_plat] (regp_name,regp_desc,regp_prov_id) VALUES ('F', null , 2)
INSERT [mtr].[region_plat] (regp_name,regp_desc,regp_prov_id) VALUES ('Z', null , 2)
INSERT [mtr].[region_plat] (regp_name,regp_desc,regp_prov_id) VALUES ('T', null , 2)
INSERT [mtr].[region_plat] (regp_name,regp_desc,regp_prov_id) VALUES ('B', null , 1)

INSERT [mtr].[area_workgroup] (arwg_code,arwg_desc,arwg_city_id) VALUES ('BCI-0001', 'Bandung Cimahi', 1)
INSERT [mtr].[area_workgroup] (arwg_code,arwg_desc,arwg_city_id) VALUES ('BPA-0001', 'Bandung Padalarang', 1)
INSERT [mtr].[area_workgroup] (arwg_code,arwg_desc,arwg_city_id) VALUES ('BCY-0001', 'Bandung Cileunyi', 1)

INSERT mtr.insurance_type values('Comprehensive', 'Jaminan Tambahan Komprehensive')
INSERT mtr.insurance_type values('Total Loss Only', 'Jaminan Tambahan Kerugian Total')

INSERT [mtr].[template_type] ([tety_name], [tety_group]) VALUES ('FEASIBILITY', N'SERVICES')
INSERT [mtr].[template_type] ([tety_name], [tety_group]) VALUES ('CREATE POLIS', N'SERVICES')
INSERT [mtr].[template_type] ([tety_name], [tety_group]) VALUES ('CLAIM', N'SERVICES')
INSERT [mtr].[template_type] ([tety_name], [tety_group]) VALUES ('CREATE', N'ORDER TYPE')
INSERT [mtr].[template_type] ([tety_name], [tety_group]) VALUES ('MODIFY', N'ORDER TYPE')
INSERT [mtr].[template_type] ([tety_name], [tety_group]) VALUES ('OPEN', N'STATUS')
INSERT [mtr].[template_type] ([tety_name], [tety_group]) VALUES ('PENDING', N'STATUS')
INSERT [mtr].[template_type] ([tety_name], [tety_group]) VALUES ('CANCELLED', N'STATUS')
INSERT [mtr].[template_type] ([tety_name], [tety_group]) VALUES ('CLOSED', N'STATUS')

INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('REVIEW & CHECK CUSTOMER REQUEST', 1,'WORKGROUP',null,1)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('PROSPEK CUSTOMER POTENTIAL', 1,'WORKGROUP',null,2)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('PREMI SCHEMA', 1,'WORKGROUP',null,3)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('LEGAL DOCUMENT SIGNED', 1,'WORKGROUP',null,4)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('CREATE POLIS', null,'SYSTEM',null,null)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('CLOSE ORDER', null,'SYSTEM',null,null)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('GENERATE POLIS NUMBER', 2,'SYSTEM',null,1)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('GENERATE PREMI', 2,'SYSTEM',null,2)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('GENERATE VIRTUAL ACCOUNT', 2,'SYSTEM',null,3)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('NOTIFY TO AGENT', 2,'SYSTEM',null,4)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('NOTIFY TO CUSTOMER', 2,'SYSTEM',null,5)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('CLOSE ORDER', 2,'SYSTEM',null,6)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('CHECK CUSTOMER PREMI',3,'WORKGROUP',null,1)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('SETTLE CUSTOMER PREMI',3,'WORKGROUP',null,2)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('CHECK VEHICLE CONDITION',3,'WORKGROUP',null,3)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('CLAIM DOCUMENT APPROVED',3,'WORKGROUP',null,4)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('NOTIFY PARTNER TO REPAIR',3,'SYSTEM',null,5)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('CALCULATE SPARE PART', 3,'WORKGROUP',null,6)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('NOTIFY CUSTOMER VEHICLE REPAIRED', 3,'WORKGROUP',null,7)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('NOTIFY AGENT CLAIM', 3,'SYSTEM',null,8)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('FEEDBACK CUSTOMER', 3,'WORKGROUP',null,9)
INSERT [mtr].[template_service_task] (testa_name, testa_tety_id, testa_group, testa_callmethod, testa_seqorder) VALUES ('CLOSE ORDER',3,'SYSTEM',null,10)


INSERT [mtr].[template_task_workorder] ([tewo_name], tewo_value) VALUES (N'CHECK UMUR', null)
INSERT [mtr].[template_task_workorder] ([tewo_name], tewo_value) VALUES (N'RELATE GOVERNMENT', null)
INSERT [mtr].[template_task_workorder] ([tewo_name], tewo_value) VALUES (N'PREMI SCHEMA', 'COMPREHENSIVE')
INSERT [mtr].[template_task_workorder] ([tewo_name], tewo_value) VALUES (N'DOCUMENT DISETUJUI', 'OK')
INSERT [mtr].[template_task_workorder] ([tewo_name], tewo_value) VALUES (N'COPY VALUE', 'Y')

INSERT [users].[roles] ([role_name], [role_description]) VALUES (N'CU', N'customers')
INSERT [users].[roles] ([role_name], [role_description]) VALUES (N'EM', N'employees')
INSERT [users].[roles] ([role_name], [role_description]) VALUES (N'PC', N'potential customer')
INSERT [users].[roles] ([role_name], [role_description]) VALUES (N'PR', N'partner')

INSERT [users].[business_entity] ([entity_modified_date]) VALUES (CAST(N'2023-05-07T00:00:00.000' AS DateTime))
INSERT [users].[business_entity] ([entity_modified_date]) VALUES (CAST(N'2023-10-10T00:00:00.000' AS DateTime))
INSERT [users].[business_entity] ([entity_modified_date]) VALUES (CAST(N'2023-10-10T00:00:00.000' AS DateTime))

INSERT [users].[users] ([user_entityid], [user_name], [user_password], [user_full_name], [user_email], [user_birth_place], [user_birth_date], [user_national_id], [user_npwp], [user_photo], [user_modified_date]) VALUES (1,'bara', N'123', N'muhammadbara', N'bara@gmail.com', N'jakarta', CAST(N'2001-01-27T00:00:00.000' AS DateTime), N'malay', N'1234bara', NULL, CAST(N'2023-11-19T00:00:00.000' AS DateTime))
INSERT [users].[users] ([user_entityid], [user_name], [user_password], [user_full_name], [user_email], [user_birth_place], [user_birth_date], [user_national_id], [user_npwp], [user_photo], [user_modified_date]) VALUES (2,'adi', N'321', N'adiadi', N'adi@gmail.com', N'jakarta', CAST(N'2001-10-10T00:00:00.000' AS DateTime), N'indo', N'1234adi', NULL, CAST(N'2023-10-10T00:00:00.000' AS DateTime))
INSERT [users].[users] ([user_entityid], [user_name], [user_password], [user_full_name], [user_email], [user_birth_place], [user_birth_date], [user_national_id], [user_npwp], [user_photo], [user_modified_date]) VALUES (3,'tengku', N'231', N'tengkudev', N'tengku@gmail.com', N'jakarta', CAST(N'2002-10-10T00:00:00.000' AS DateTime), N'thai', N'3214tngku', NULL, CAST(N'2023-10-10T00:00:00.000' AS DateTime))

INSERT [users].[user_roles] ([usro_entityid], [usro_role_name], [usro_status], [usro_modified_date]) VALUES (1, N'EM', N'ACTIVE', CAST(N'2023-11-19T00:00:00.000' AS DateTime))
INSERT [users].[user_roles] ([usro_entityid], [usro_role_name], [usro_status], [usro_modified_date]) VALUES (2, N'CU', N'ACTIVE', CAST(N'2023-10-10T00:00:00.000' AS DateTime))
INSERT [users].[user_phone] ([usph_entityid], [usph_phone_number], [usph_phone_type], [usph_mime], [usph_status], [usph_modified_date]) VALUES (1, N'123455677', N'HOME', N'WKWKWK', N'NORMAL', CAST(N'2023-10-10T00:00:00.000' AS DateTime))

INSERT [users].[user_address] ( [usdr_id],[usdr_entityid], [usdr_address1], [usdr_address2], [usdr_modified_date], [usdr_city_id]) VALUES (1,1, N'JAKARTA', N'JAKARTA', CAST(N'2023-10-10T00:00:00.000' AS DateTime), 2)

INSERT [payment].[payment_transactions] ([patr_trxno], [patr_created_on], [patr_debet], [patr_credit], [patr_usac_accountNo_from], [patr_usac_accountNo_to], [patr_type], [patr_invoice_no], [patr_notes], [patr_trxno_rev]) VALUES (N'TRANSAKSI1', CAST(N'2023-10-10T00:00:00.000' AS DateTime), 10000.0000, 1000.0000, 1, 2, N'SALARY', N'123', N'NOTES', N'TRANSAKSI1')
INSERT [payment].[fintech] ([fint_entityid], [fint_name], [fint_desc]) VALUES (2, N'FIADI', N'desc')
INSERT [payment].[banks] ([bank_entityid], [bank_name], [bank_desc]) VALUES (1, N'BBARA', N'DESC')

INSERT [partners].[partners] ([part_entityid], [part_name], [part_address], [part_join_date], [part_accountNo], [part_npwp], [part_status], [part_modified_date], [part_city_id]) VALUES (3, N'tengku', N'jakarta', CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'082123456', N'1234', N'ACTIVE', CAST(N'2023-10-10T00:00:00.000' AS DateTime), 2)
INSERT [partners].[partner_contacts] ([paco_patrn_entityid], [paco_user_entityid], [paco_status]) VALUES (3, 3, N'status')
INSERT [partners].[partner_area_workgroup] ([pawo_patr_entityid], [pawo_arwg_code], [pawo_user_entityid], [pawo_status], [pawo_modified_date]) VALUES (3, N'BCI-0001', 3, N'status', CAST(N'2023-10-10T00:00:00.000' AS DateTime))

INSERT [payment].[user_accounts] ([usac_accountno], [usac_debet], [usac_credit], [usac_type], [usac_bank_entityid], [usac_fint_entityid], [usac_user_entityid]) VALUES ('ACC', 10000.0000, 1000.0000, N'BANK', 1, 2, 1)

INSERT [hr].[job_type] ([job_code], [job_modified_date]) VALUES (N'1', CAST(N'2008-10-10T00:00:00.000' AS DateTime))

INSERT [hr].[employees] ([emp_entityid], [emp_name], [emp_join_date], [emp_type], [emp_status], [emp_graduate], [emp_net_salary], [emp_account_number], [emp_modified_date], [emp_job_code]) VALUES (1, N'BARA', CAST(N'2009-10-10T00:00:00.000' AS DateTime), N'PERMANENT', N'ACTIVE', N'S1', 2000000.0000, N'BARA123', CAST(N'2023-11-19T00:00:00.000' AS DateTime), N'1')

INSERT [customer].[customer_request] ([creq_entityid], [creq_create_date], [creq_status], [creq_type], [creq_modified_date], [creq_cust_entityid], [creq_agen_entityid]) VALUES (2, CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'OPEN', N'POLIS', CAST(N'2023-10-10T00:00:00.000' AS DateTime), NULL, NULL)

-- INSERT [so].[services] ([serv_created_on], [serv_type], [serv_insuranceNo], [serv_vehicleNo], [serv_startdate], [serv_enddate], [serv_status], [serv_serv_id], [serv_cust_entityid], [serv_creq_entityid]) VALUES (CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'POLIS', N'D 123 AB', N'1', CAST(N'2023-10-10T00:00:00.000' AS DateTime), CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'ACTIVE', NULL, 2, 2)
-- INSERT [so].[services] VALUES (CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'POLIS', N'D 123 AB', N'1', CAST(N'2023-10-10T00:00:00.000' AS DateTime), CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'ACTIVE', 1, 2, 2)

INSERT [partners].[batch_partner_invoice] ([bpin_invoiceNo],[bpin_created_on], [bpin_subtotal], [bpin_tax], [bpin_accountNo], [bpin_status], [bpin_paid_date], [bpin_serv_id], [bpin_patrn_entityid], [bpin_patr_trxno]) VALUES (N'1', CAST(N'2023-10-10T00:00:00.000' AS DateTime), 100000.0000, 10000.0000, N'1', N'ACTIVE', CAST(N'2023-10-10T00:00:00.000' AS DateTime), 1, 3, N'TRANSAKSI1')

INSERT [so].[service_premi_credit] VALUES (1, CAST(N'2023-10-10T00:00:00.000' AS DateTime), 10000.0000, 10000.0000, CAST(N'2023-10-10T00:00:00.000' AS DateTime), CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'TRANSAKSI1')

INSERT [customer].[customer_claim] ([cucl_creq_entityid], [cucl_create_date], [cucl_event_price], [cucl_subtotal], [cucl_reason]) VALUES (2, CAST(N'2023-10-10T00:00:00.000' AS DateTime), 100000.0000, 100000.0000, N'wkwkwkwk')

-- INSERT [customer].[customer_insc_assets] ([cias_creq_entityid], [cias_police_number], [cias_year], [cias_startdate], [cias_enddate], [cias_current_price], [cias_insurance_price], [cias_total_premi], [cias_paid_type], [cias_isNewChar], [cias_cars_id], [cias_inty_name], [cias_city_id]) VALUES (2, N'D 1234 AB', N'2023', CAST(N'2010-10-10T00:00:00.000' AS DateTime), CAST(N'2015-10-10T00:00:00.000' AS DateTime), 200000.0000, 10000.0000, 10000.0000, N'CASH', N'Y', 1, N'medic', 2)

-- INSERT [customer].[customer_request] ([creq_entityid], [creq_create_date], [creq_status], [creq_type], [creq_modified_date], [creq_cust_entityid], [creq_agen_entityid]) VALUES (2, CAST(N'2023-10-10T00:00:00.000' AS DateTime), N'OPEN', N'POLIS', CAST(N'2023-10-10T00:00:00.000' AS DateTime), NULL, NULL)

-- INSERT [customer].[customer_insc_doc] ([cadoc_id], [cadoc_creq_entityid], [cadoc_filename], [cadoc_filetype], [cadoc_filesize], [cadoc_category], [cadoc_modified_date]) VALUES (1, 2, N'adi', N'PDF', 20, N'KTP', CAST(N'2023-10-10T00:00:00.000' AS DateTime))

-- INSERT [customer].[customer_insc_extend] ([cuex_id], [cuex_creq_entityid], [cuex_name], [cuex_total_item], [cuex_nominal]) VALUES (1, 2, N'adi', 3, 100000.0000)
