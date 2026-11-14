-- tao database va bang [User] cho project LoginSessionCookie 
IF DB_ID(N'test') IS NULL
    CREATE DATABASE [test];
GO

USE [test];
GO

IF OBJECT_ID(N'dbo.[User]', N'U') IS NOT NULL
    DROP TABLE dbo.[User];
GO

CREATE TABLE dbo.[User] (
    [id]          INT IDENTITY(1,1) PRIMARY KEY,
    [email]       NVARCHAR(255) NOT NULL,
    [username]    NVARCHAR(255) NOT NULL UNIQUE,
    [fullname]    NVARCHAR(255) NULL,
    [password]    NVARCHAR(255) NOT NULL,
    [avatar]      NVARCHAR(255) NULL,
    [roleid]      INT DEFAULT 3,            -- 1: Admin, 2: Manager, 3: User
    [phone]       NVARCHAR(20) NULL,
    [createddate] DATE DEFAULT GETDATE()
);
GO

INSERT INTO dbo.[User](email, username, fullname, password, avatar, roleid, phone, createddate) VALUES
(N'admin@iotstar.vn',   N'admin',   N'Quản trị viên',        N'123456', NULL, 1, N'0901234567', GETDATE()),
(N'manager@iotstar.vn', N'manager', N'Quản lý hệ thống',     N'123456', NULL, 2, N'0907654321', GETDATE()),
(N'khoa@iotstar.vn',    N'khoa',    N'Trần Phong Đăng Khoa', N'123456', NULL, 3, N'0908617108', GETDATE());
GO

SELECT * FROM dbo.[User];
GO
