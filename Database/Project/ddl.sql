SPOOL ddl.out
SET ECHO ON

DROP TABLE Employee CASCADE CONSTRAINTS;
DROP TABLE AptUnit CASCADE CONSTRAINTS;
DROP TABLE WorkOrder CASCADE CONSTRAINTS;
DROP TABLE AptHistory CASCADE CONSTRAINTS;
DROP TABLE Resident CASCADE CONSTRAINTS;
DROP TABLE PartsNeeded CASCADE CONSTRAINT;

CREATE TABLE Employee
(
eID	INTEGER PRIMARY KEY,
name	char(10) NOT NULL,
phoneNum INTEGER NOT NULL,

CONSTRAINT

);

CREATE TABLE AptUnit 
(
aptNum	INTEGER PRIMARY KEY,
floorNum INTEGER NOT NULL,
rent INTEGER NOT NULL,

CONSTRAINT
);

CREATE TABLE WorkOrder
(
wID INTEGER PRIMARY KEY,
date INTEGER NOT NULL,
description char(20) NOT NULL,

CONSTRAINT
);

CREATE TABLE Resident
(
ssn INTEGER PRIMARY KEY,
name CHAR(10) NOT NULL,
sex CHAR(1) NOT NULL,
age INTEGER NOT NULL,

CONSTRAINT
);

CREATE TABLE AptHistory
(
year INTEGER,
aptNum INTEGER,
PRIMARY KEY (aptNum, year),

CONSTRAINT
);

CREATE TABLE PartsNeeded
(
partType CHAR(10) PRIMARY KEY,
quantity INTEGER NOT NULL,
location CHAR (10) NOT NULL,

CONSTRAINT
);

----------------------------------------------------------
INSERT INTO Employee VALUES (1,'Michelle',9999999);
INSERT INTO Employee VALUES (2,'Alex',1111111);
INSERT INTO Employee VALUES (3,'Allison',2222222);
SELECT * FROM Employee;

----------------------------------------------------------

INSERT INTO Resident VALUES (369,'Harry','m',25);
INSERT INTO Resident VALUES (321,'Hermione','f',24);
INSERT INTO Resident VALUES (111,'Ron','m',25);
SELECT * FROM Resident;
----------------------------------------------------------

INSERT INTO AptUnit VALUES (1,2);
INSERT INTO AptUnit VALUES (2,2);
INSERT INTO AptUnit VALUES (3,1);
SELECT * FROM AptUnit;

---------------------------------------------------------
INSERT INTO WorkOrder VALUES (245,12-23-13,'broken clock');
INSERT INTO WorkOrder VALUES (22,12-24-13,'broken dishwasher');
INSERT INTO WorkOrder VALUES (24,12-25-13,'broken table');
SELECT * FROM WorkOrder;
----------------------------------------------------------

INSERT INTO PartsNeeded VALUES ('screw',3,'Lowes');
SELECT * FROM PartsNeeded;

-------------------------------------------------------------
INSERT INTO AptHistory VALUES (1,1999);
INSERT INTO AptHistory VALUES (2,1999);
INSERT INTO AptHistory VALUES (3,1999);
SELECT * FROM AptHistory;

SET ECHO OFF
SPOOL OFF
