use HospitalBooking
go

create table dbo.Bill
(
    Id           int identity
        constraint Bill_pk
        primary key,
    Price        float       not null,
    Status       varchar(50) not null,
    CreatedAt    datetime,
    UpdatedAt    datetime,
    CheckoutAt   datetime,
    PatientName  nvarchar(255),
    PatientEmail nvarchar(255),
    PatientPhone nvarchar(255),
    Note         nvarchar(1000)
)
    go

create table dbo.Department
(
    Id          int identity
        constraint Department_pk
        primary key,
    Name        nvarchar(255)  not null,
    Description nvarchar(1000) not null
)
    go

create table dbo.Account
(
    Id           int identity
        constraint Account_pk
        primary key,
    Avatar       nvarchar(255),
    FirstName    nvarchar(255),
    LastName     nvarchar(255),
    DepartmentId int
        constraint Account_Department_Id_fk
            references dbo.Department,
    Gender       varchar(50),
    DOB          date,
    PhoneNumber  varchar(255),
    Email        varchar(255) not null,
    Description  nvarchar(1000),
    CreatedAt    datetime,
    UpdatedAt    datetime,
    Role         varchar(50)  not null,
    Token        varchar(255),
    Password     varchar(255),
    Address      nvarchar(500),
    IsActive     bit
)
    go

create table dbo.Shift
(
    Id        int identity
        constraint Shift_pk
        primary key,
    DoctorId  int  not null
        constraint Shift_User_Id_fk
            references dbo.Account,
    Slot      int  not null,
    CreatedAt datetime,
    UpdatedAt datetime,
    Date      date not null
)
    go

create table dbo.Appointment
(
    Id           int identity
        constraint Appointment_pk
        primary key,
    BookerId     int         not null
        constraint Booking_User_Id_booker
            references dbo.Account,
    DoctorId     int         not null
        constraint Booking_User_Id_doctor
            references dbo.Account,
    ShiftId      int         not null
        constraint Appointment_Shift_Id_fk
            references dbo.Shift
            on delete cascade,
    PatientName  nvarchar(255),
    PatientPhone nvarchar(255),
    Status       varchar(50) not null,
    PatientEmail nvarchar(255),
    PatientNote  nvarchar(1000),
    DoctorNote   nvarchar(1000),
    CreatedAt    datetime,
    UpdatedAt    datetime,
    CanceledAt   datetime,
    FinishedAt   datetime,
    BillId       int
        constraint Appointment_Bill_Id_fk
            references dbo.Bill
)
    go

create table dbo.Review
(
    Id            int identity
        constraint Review_pk
        primary key,
    Content       nvarchar(1000) not null,
    Score         int            not null,
    DoctorId      int
        constraint Review_Account_Id_fk
            references dbo.Account,
    CreatedAt     datetime,
    UpdatedAt     datetime,
    ReviewerId    int            not null
        constraint Review_Account_Id_fk2
            references dbo.Account,
    AppointmentId int
        constraint Review_Appointment_Id_fk
            references dbo.Appointment
)
    go

create table dbo.Token
(
    Id        int identity
        constraint Token_pk
        primary key,
    Token     varchar(255) not null,
    CreatedAt datetime     not null,
    AccountId int          not null
        constraint Token_Account_Id_fk
            references dbo.Account,
    ExpiredAt datetime,
    Type      nvarchar(50) not null
)
    go

