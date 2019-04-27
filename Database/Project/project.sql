SPOOL project.out
SET ECHO ON
/*
CIS 353 - Database Design Maintenance - Apartment Maintenance
Michelle Vu
Alex Porter
Allison Bickford
*/
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
rent 			INTEGER NOT NULL,
rooms 		INTEGER NOT NULL,
floor 		INTEGER NOT NULL
);
--
CREATE TABLE AptFloor (
floorNum	INTEGER PRIMARY KEY,
empID		INTEGER NOT NULL
);
--
CREATE TABLE WorkOrder (
wID 		    INTEGER PRIMARY KEY,
dateFiled	    DATE NOT NULL,
dateCompleted 	DATE,
description 	VARCHAR(100) NOT NULL,
empID 		    INTEGER NOT NULL,
woResidentSSN 	INTEGER NOT NULL,
hoursWorked	    INTEGER NOT NULL
);
--
CREATE TABLE Resident (
ssn 		INTEGER PRIMARY KEY,
firstName 	VARCHAR(20) NOT NULL,
lastName 	VARCHAR(20) NOT NULL,
sex 		VARCHAR(1) NOT NULL,
age 		INTEGER NOT NULL
);
--
CREATE TABLE Lives_at (
aptNum		INTEGER NOT NULL,
resSSN		INTEGER NOT NULL,
moveInDate	DATE NOT NULL,
moveOutDate	DATE,
PRIMARY KEY (aptNum, resSSN)
);
--
CREATE TABLE maintenanceHistory (
year 			INTEGER,
aptNum 		    INTEGER,
description 	VARCHAR(100),
PRIMARY KEY (aptNum, year)
);
--
CREATE TABLE Parts (
partType 	    VARCHAR(30),
quantity 	    INTEGER NOT NULL,
pricePerUnit 	INTEGER NOT NULL,
wId		        INTEGER NOT NULL,
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
 	Rent is structured this way, determined by AptUnit:
         Simple (Units 1-4) - 400 (a month)
         Family (Units 5-10) - 500
         Deluxe (Units 11+)  - 700
**Fulfills 2 attribute 1 row check IC**
*/
--
ALTER TABLE AptUnit ADD (
	CONSTRAINT ICROOMNUM CHECK (rooms > 1),
	CONSTRAINT ICROOMLOW CHECK (aptNum > 5 OR rent = 400),
	CONSTRAINT ICROOMMID CHECK ((aptNum > 4 AND aptNum < 11 AND rent=500) OR (aptNum <=4 OR aptNum >= 11)),
	CONSTRAINT ICROOMHIGH CHECK (aptNum < 11 OR rent=700),
CONSTRAINT ICFLOORFK FOREIGN KEY (floor) REFERENCES AptFloor(floorNum)DEFERRABLE INITIALLY DEFERRED 
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
*/
ALTER TABLE WorkOrder ADD (
	CONSTRAINT wempfk FOREIGN KEY (empID) REFERENCES Employee(eID),
	CONSTRAINT wresfk FOREIGN KEY (woResidentSSN) REFERENCES Resident(ssn)
	);
--
/* Resident
	Every resident that files a report must be over the age of 18
**Fulfills  IC #2, 1 attribute check**
-Note - As the maintenance crew of the apartment, we are not concerned with the 
children of the residents. As such, even if a Resident has a child living with them,
they will not exist in our database.
*/
ALTER TABLE Resident ADD (
	CONSTRAINT ICAGE CHECK (age >= 18)
	);
--
/* Lives_at 
	Foreign key
*/
ALTER TABLE Lives_at ADD (
CONSTRAINT res_fk FOREIGN KEY (resSSN) REFERENCES Resident(ssn),
CONSTRAINT ICAPTNUMFK FOREIGN KEY (aptNum) REFERENCES AptUnit(aptNum) 	
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
SET FEEDBACK OFF
COMMIT;
----------------------------------------------------------
INSERT INTO Employee VALUES (1,'Michelle', 'Vu',9999999);
INSERT INTO Employee VALUES (2,'Alex','Porter',1111111);
INSERT INTO Employee VALUES (3,'Allison','Bickford',2222222);
--
----------------------------------------------------------
--
INSERT INTO Resident VALUES (11,'Harry','Potter','m',25);
INSERT INTO Resident VALUES (12,'Ginny','Potter','f',23);
INSERT INTO Resident VALUES (22,'Hermione','Granger','f',24);
INSERT INTO Resident VALUES (33,'Ron','Weasley','m',25);
INSERT INTO Resident VALUES (44,'Argus','Filch','m',68);
--
----------------------------------------------------------
--
INSERT INTO AptUnit VALUES (1,400, 2, 1);
INSERT INTO AptUnit VALUES (4,400, 2, 1);
INSERT INTO AptUnit VALUES (6,500, 3, 1);
INSERT INTO AptUnit VALUES (12,700, 6, 2);
INSERT INTO AptUnit VALUES (13,700, 6, 3);
--
---------------------------------------------------------
INSERT INTO Lives_at VALUES (1, 11, '10-JAN-02', NULL);
INSERT INTO Lives_at VALUES (1, 12, '11-MAR-15', NULL);
INSERT INTO Lives_at VALUES (4, 22, '10-OCT-4', '12-AUG-17');
INSERT INTO Lives_at VALUES (6, 22, '12-AUG-18', NULL);
INSERT INTO Lives_at VALUES (12, 33, '13-APR-12', NULL);
INSERT INTO Lives_at VALUES (13, 44, '13-FEB-20', NULL);
--
----------------------------------------------------------
--
INSERT INTO AptFloor VALUES (1, 1);
INSERT INTO AptFloor VALUES (2, 2);
INSERT INTO AptFloor VALUES (3, 3);
--
--------------------------------------------------------------
--
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
----------------------------------------------------------
--
INSERT INTO Parts VALUES ('screw',3,1,111);
INSERT INTO Parts VALUES ('lightbulb', 20, 5,111);
INSERT INTO Parts VALUES ('drain snake', 4, 10,113);
--
-------------------------------------------------------------
INSERT INTO maintenanceHistory VALUES (1999,1, 'broken stove');
INSERT INTO maintenanceHistory VALUES (2000,1, 'ant infestation');
INSERT INTO maintenanceHistory VALUES (2000,12, 'broken clock');
INSERT INTO maintenanceHistory VALUES (2004, 4, 'burnt fuse');
INSERT INTO maintenanceHistory VALUES (2002, 6, 'broken outlet');
INSERT INTO maintenanceHistory VALUES (2010, 13, 'broken air conditioning');
INSERT INTO maintenanceHistory VALUES (2009, 6, 'replaced carpet');
INSERT INTO maintenanceHistory VALUES (2009, 12, 'painted walls');
--
--
--------------------------------------------------------------
SET FEEDBACK ON
COMMIT;
SELECT * FROM Employee;
SELECT * FROM AptUnit;
SELECT * FROM WorkOrder;
SELECT * FROM maintenanceHistory;
SELECT * FROM Resident;
SELECT * FROM Parts;
SELECT * FROM PartLocations;
SELECT * FROM Lives_at;
SELECT * FROM AptFloor;
--
--Testing the ICS
--1 row 2 attributes--
INSERT INTO AptUnit VALUES (3, 500, 2, 4);
INSERT INTO AptUnit VALUES (15, 400, 6, 5);
INSERT INTO AptUnIt VALUES (8, 700, 3, 6);
--
--1 row--
INSERT INTO Resident VALUES (55,'Albus','Potter','m',8);
--
--Foreign Key--
INSERT INTO Lives_at VALUES (100, 11, '10-JAN-02', NULL);
--
--Primary Key--
INSERT INTO Employee VALUES (1,'Identity', 'Theif',6163313255);
--
--
-- QUERIES
--
/*
1. A join involving at least four relations.
2. A self-join.
3. UNION, INTERSECT, and/or MINUS.
4. SUM, AVG, MAX, and/or MIN.
5. GROUP BY, HAVING, and ORDER BY, all appearing in the same query
6. A correlated subquery.
7. A non-correlated subquery.
8. A relational DIVISION query.
9. An outer join query. */

--
--1 Join involving at least four relations--
--
/*Give me the employee eid, the floor they work on, the resident's ssn, and the WorkOrder Id the any resident with the last name 'Potter' filed */
SELECT E.eID, F.floorNum, W.wID, R.ssn
FROM Employee E, AptFloor F, WorkOrder W, Resident R
WHERE E.eid=F.empID AND E.eid=W.empID AND 
W.woResidentSSN=R.ssn AND R.lastName='Potter';
--
--2 Self Join--
--
/*For every resident that lives with at least one other person and find the apartment number and the last name of the eldest resident.*/
SELECT L1.aptNum, r1.ssn, r1.lastName
FROM Resident r1, Resident r2, Lives_at L1, Lives_at L2
WHERE r1.ssn <> r2.ssn AND r1.ssn = L1.resSSN AND r2.ssn = L2.resSSN AND L1.aptNum = L2.aptNum AND r1.age > r2.age;
--
--3 Union, Intersect, and/or Minus--
--
/*Give me the IDs of all the employees that have worked on any project for more than 4 hours*/
(SELECT E.eID
From Employee E)
INTERSECT
(SELECT W.empID
FROM WorkOrder W
WHERE hoursWorked > 3);
--
--4 Max--
--
/*Give the employee ID and last name of the employee that worked the most hours on any one WorkOrder*/
SELECT E.eID, E.lastname, W.hoursWorked
FROM Employee E, WorkOrder W
WHERE E.eID=W.empID AND W.hoursWorked=
(SELECT MAX (T.hoursWorked)
FROM WorkOrder T);
--
--5 GROUP BY, HAVING, and ORDER BY, all appearing in the same query--
--
/* Find the ssn of residents that have filed more than two work orders and find the total number of work orders. Order by highest count.
*/
SELECT r.ssn, count(*)
FROM Resident r, WorkOrder w
WHERE r.ssn = w.woResidentSSN
GROUP BY r.ssn
HAVING COUNT(*) > 2
ORDER BY COUNT(*) DESC;
--
--6 A correlated subquery--
--
/*Give me the names and IDS of all employees that have incomplete work orders*/
SELECT E.eid, E.lastname
FROM Employee E
WHERE E.eid IN 
		(SELECT W.empID
		 FROM WorkOrder W
		 WHERE E.eid=W.empID AND W.dateCOmpleted IS NULL);
--
--
--7 A non-correlated subquery-- 
--
/* Give me the names and ssns of all the residents that have not filed work orders who pay more than $400 in rent*/
SELECT DISTINCT R.ssn, R.lastname, R.firstName
FROM Resident R, AptUnit A, Lives_at L
WHERE A.rent > 400 AND R.ssn = L.resSSN AND L.aptNum=A.aptNum AND R.ssn NOT IN 
							(SELECT W.woResidentSSN
							FROM WorkOrder W);
--
--8 Division query--
--
/*For every employee who works on every work order that is filed by Apartment 3: Find the employee ID*/
SELECT E.eid
FROM Employee E
WHERE NOT EXISTS (
(SELECT L.resSSn
FROM Lives_at L
WHERE L.aptNum=12)
MINUS
(SELECT W.woResidentSSN
FROM WorkOrder W
WHERE W.empID=E.eid));
--
--9 An outer join query--
--
/*Give me the ssn, first name, and last name of all the residents plus any work orders they have submitted.*/
SELECT R.ssn, R.firstName, R.lastName, W.wID, w.hoursWorked
FROM Resident R LEFT OUTER JOIN WorkOrder W ON R.ssn=W.woResidentSSN
ORDER BY R.ssn;
--
--Null--
--
/* Find the wID of the work order that hasn't been completed. */
SELECT W.wID
FROM WorkOrder W
WHERE W.dateCompleted IS NULL;
--
--Joining 3 Tables--
--
/*Find the eID, wID and ssn where the resident is a female and the work order has been completed */
SELECT e.eid,w.wid,r.ssn
FROM Employee e, WorkOrder w, Resident r
WHERE r.sex='f' AND w.dateCompleted IS NOT NULL AND w.empID=e.eid AND w.woResidentSSN=r.ssn;
--
SET ECHO OFF
SPOOL OFF
