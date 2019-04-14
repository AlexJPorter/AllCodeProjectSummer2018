SPOOL ddl.out
SET ECHO ON
--
DROP TABLE Employee CASCADE CONSTRAINTS;
DROP TABLE AptUnit CASCADE CONSTRAINTS;
DROP TABLE WorkOrder CASCADE CONSTRAINTS;
DROP TABLE maintenanceHistory CASCADE CONSTRAINTS;
DROP TABLE Resident CASCADE CONSTRAINTS;
DROP TABLE Parts CASCADE CONSTRAINTs;
DROP TABLE PartLocations CASCADE CONSTRAINTS;
DROP TABLE Lives_at CASCADE CONSTRAINTS;
DROP TABLE AptFloor CASCADE CONSTRAINTS;
--
CREATE TABLE Employee (
eID		INTEGER PRIMARY KEY,
firstName	VARCHAR(20) NOT NULL,
lastName	VARCHAR(20) NOT NULL,
phoneNum 	INTEGER NOT NULL
);
--
CREATE TABLE AptUnit (
aptNum		INTEGER PRIMARY KEY,
rent 		INTEGER NOT NULL,
rooms 		INTEGER NOT NULL
);
--
CREATE TABLE AptFloor (
floorNum	INTEGER PRIMARY KEY,
empID		INTEGER NOT NULL
);
--
CREATE TABLE WorkOrder (
wID 		INTEGER PRIMARY KEY,
dateFiled	DATE NOT NULL,
dateCompleted 	DATE,
description 	VARCHAR(40) NOT NULL,
empID 		INTEGER NOT NULL,
woResidentSSN 	INTEGER NOT NULL,
hoursWorked	INTEGER NOT NULL
);
--
CREATE TABLE Resident (
ssn 		INTEGER PRIMARY KEY,
firstName 	VARCHAR(20) NOT NULL,
lastName 	VARCHAR(20) NOT NULL,
sex 		VARCHAR(1) NOT NULL,
age 		INTEGER NOT NULL,
aptNum 		INTEGER NOT NULL
);
--
CREATE TABLE Lives_at (
aptNum		INTEGER NOT NULL,
resSSN		INTEGER NOT NULL,
rent		INTEGER NOT NULL,
moveInDate	DATE NOT NULL,
moveOutDate	DATE,
PRIMARY KEY (aptNum, resSSN)
);
--
CREATE TABLE maintenanceHistory (
year 			INTEGER,
aptNum 		INTEGER,
description 	VARCHAR(40),
PRIMARY KEY (aptNum, year)
);
--
CREATE TABLE Parts (
partType 	VARCHAR(30),
quantity 	INTEGER NOT NULL,
pricePerUnit 	INTEGER NOT NULL,
wId		INTEGER NOT NULL,
PRIMARY KEY (partType, wID),
--
CONSTRAINT wIDPART FOREIGN KEY (wId) REFERENCES 
WorkOrder(wID)
);
--
CREATE TABLE partLocations (
partDesc	VARCHAR(30) NOT NULL,
wID 		INTEGER NOT NULL,
location	VARCHAR(30) NOT NULL,
PRIMARY KEY (partDesc,wID,location),
--
CONSTRAINT ICPARTDEP FOREIGN KEY (partDesc,wID) REFERENCES
Parts (partType,wID)
);
--
--
--CONSTRAINTS
--
/* AptUnit
	Rooms has a minimum of 2, bedroom and regular room
 	Rent is structured this way:
         1-2 - 400 (a month)
         3-4 - 500
         5+  - 700
**Fulfills 2 attribute 1 row check IC**
*/
--
ALTER TABLE AptUnit ADD (
	CONSTRAINT ICROOMNUM CHECK (rooms > 1),
	CONSTRAINT ICROOMLOW CHECK (rooms <= 2 OR rent > 400),
	CONSTRAINT ICROOMMID CHECK ((rooms > 2 AND rooms < 5) OR rent <> 500),
	CONSTRAINT ICROOMHIGH CHECK (rooms < 5 OR rent=700)
	);
--
/* AptFloor
	Foreign key to employee who maintains that floor
*/
ALTER TABLE AptFloor ADD 
	CONSTRAINT apt_fl_emp_fk FOREIGN KEY (empID) REFERENCES Employee(eID);
--
/* WorkOrder
	eID is a foreign key
	Resident ssn is a foreign key
	? Has dependent entity PartsNeeded 
*/
ALTER TABLE WorkOrder ADD (
	CONSTRAINT wempfk FOREIGN KEY (empID) REFERENCES Employee(eID),
	CONSTRAINT wresfk FOREIGN KEY (woResidentSSN) REFERENCES Resident(ssn)
	);
--
/* Resident
	Every resident that files a report must be over the age of 18
**Fulfills  IC #2, 1 attribute check**
*/
ALTER TABLE Resident ADD (
	CONSTRAINT ICAGE CHECK (age >= 18),
	CONSTRAINT resapt FOREIGN KEY (aptNum) REFERENCES AptUnit(aptNum)
DEFERRABLE INITIALLY DEFERRED
	);
--
/* Lives_at 
	Foreign key
*/
ALTER TABLE Lives_at ADD (
	CONSTRAINT apt_fk FOREIGN KEY (aptNum) REFERENCES AptUnit(aptNum),
        CONSTRAINT res_fk FOREIGN KEY (resSSN) REFERENCES Resident(ssn)	
);
--
/* maintenanceHistory
	Is dependent to apartment number 
*/
ALTER TABLE maintenanceHistory ADD 
	CONSTRAINT hisapt FOREIGN KEY (aptNum) REFERENCES AptUnit(aptNum);
--
/* Parts
	Quantity must be over 0
*/
ALTER TABLE Parts ADD 
	CONSTRAINT ICNEGQUAN CHECK (quantity > 0);
--
COMMIT;
----------------------------------------------------------
INSERT INTO Employee VALUES (1,'Michelle', 'Vu',9999999);
INSERT INTO Employee VALUES (2,'Alex','Porter',1111111);
INSERT INTO Employee VALUES (3,'Allison','Bickford',2222222);
SELECT * FROM Employee;
--
----------------------------------------------------------
--
INSERT INTO Resident VALUES (11,'Harry','Potter','m',25, 1);
INSERT INTO Resident VALUES (12,'Ginny','Potter','f',23, 1);
INSERT INTO Resident VALUES (22,'Hermione','Granger','f',24, 2);
INSERT INTO Resident VALUES (33,'Ron','Weasley','m',25, 3);
INSERT INTO Resident VALUES (44,'Argus','Filch','m',68,4);
SELECT * FROM Resident;
----------------------------------------------------------
--
INSERT INTO AptUnit VALUES (1,400, 2);
INSERT INTO AptUnit VALUES (2,500, 3);
INSERT INTO AptUnit VALUES (3,700, 6);
INSERT INTO AptUnit VALUES (4,700, 6);
SELECT * FROM AptUnit;
--
---------------------------------------------------------
INSERT INTO WorkOrder VALUES (111,'13-NOV-11','13-NOV-12','broken clock', 3, 11, 6);
INSERT INTO WorkOrder VALUES (112,'13-NOV-11','13-NOV-12','broken chair', 3, 11, 2);
INSERT INTO WorkOrder VALUES (113,'13-NOV-11','13-NOV-12','broken sink', 3, 11, 4);
--
INSERT INTO WorkOrder VALUES (222,'13-NOV-23', NULL, 'broken dishwasher', 2, 22, 4);
INSERT INTO WorkOrder VALUES (333,'13-OCT-12','13-OCT-16','broken table', 2, 22, 10);
INSERT INTO WorkOrder VALUES (555,'13-OCT-13','13-OCT-16','broken door handle', 2, 22, 4);
INSERT INTO WorkOrder VALUES (777,'13-OCT-13','13-OCT-16','broken door bell', 2, 22, 3);
--
INSERT INTO WorkOrder VALUES (444,'13-OCT-29','13-OCT-29','clogged drain', 1, 33, 1);
INSERT INTO WorkORder VALUES (616, '13-OCT-31', NULL, 'circuit failure', 2, 33, 14); 
SELECT * FROM WorkOrder;
----------------------------------------------------------
--
INSERT INTO Parts VALUES ('screw',3,1,111);
INSERT INTO Parts VALUES ('lightbulb', 20, 5,111);
INSERT INTO Parts VALUES ('drain snake', 4, 10,113);

SELECT * FROM Parts;
--
-------------------------------------------------------------
INSERT INTO maintenanceHistory VALUES (1999,2, 'broken stove');
INSERT INTO maintenanceHistory VALUES (1999,2, 'ant infestation');
INSERT INTO maintenanceHistory VALUES (1999,3, 'broken clock');
SELECT * FROM maintenanceHistory;
--
SET ECHO OFF
SPOOL OFF
--