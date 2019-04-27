SPOOL apt.out
SET ECHO ON

--
-- SELF JOIN --
--
/*
For every resident that lives with at least one other person and find the apartment number and the last name of the eldest resident.
*/

SELECT a.aptNum, r.ssn, r.lastName
FROM Resident r, Resident r2, AptUnit a
WHERE r.aptNum = r2.aptNum AND a.aptNum = r.aptNum AND r.age > r2.age;

/*
1. A join involving at least four relations.
2. A self-join.
3. UNION, INTERSECT, and/or MINUS.
4. SUM, AVG, MAX, and/or MIN.
5. GROUP BY, HAVING, and ORDER BY, all appearing in the same query
6. A correlated subquery.
7. A non-correlated subquery.
8. A relational DIVISION query.
9. An outer join query. 
*/

-- 4 Max --
/*Give the employee ID and last name of the employee that worked the most hours on any one WorkOrder*/
SELECT E.eID, E.lastname, W.hoursWorked
FROM Employee E, WorkOrder W
WHERE E.eID=W.empID AND W.hoursWorked=
(SELECT MAX (T.hoursWorked)
FROM WorkOrder T);

-- 5 --
/* Find the ssn of residents that have filed more than two work orders and find the total number of work orders. Order by highest count.
*/
SELECT r.ssn, count(*)
FROM Resident r, WorkOrder w
WHERE r.ssn = w.woResidentSSN
GROUP BY r.ssn
HAVING COUNT(*) > 2
ORDER BY COUNT(*) DESC;

-- 7 -- 
/* Give me the names and ssns of all the residents that have not filed work orders who pay more than $400 in rent*/
SELECT DISTINCT R.ssn, R.lastname, R.firstName
FROM Resident R, AptUnit A
WHERE A.rent > 400 AND R.aptNum=A.aptNum AND R.ssn NOT IN 
							(SELECT W.woResidentSSN
							FROM WorkOrder W);
-- 6 --
/*Give me the names and IDS of all employees that have incomplete work orders*/
SELECT E.eid, E.lastname
FROM Employee E
WHERE E.eid IN 
		(SELECT W.empID
		 FROM WorkOrder W
		 WHERE E.eid=W.empID AND W.dateCOmpleted IS NULL);
--9--
/*Give me the ssn, first name, and last name of all the residents plus any work orders they have submitted.*/
SELECT R.ssn, R.firstName, R.lastName, W.wID, w.hoursWorked
FROM Resident R LEFT OUTER JOIN WorkOrder W ON R.ssn=W.woResidentSSN
ORDER BY R.ssn;
--1--
/*Give me the employee eid, the floor they work on, the resident's ssn, and the WorkOrder Id the any resident with the last name 'Potter' filed */
SELECT E.eid, F.floorNum, W.wID, R.ssn
FROM Employee E, AptFloor F, WorkOrder W, Resident R
WHERE E.eid=F.empID AND F.empID=W.empID AND W.woResidentSSN=R.ssn AND R.lastName = 'Potter'; 
--3--
/*Give me the IDs of all the employees that have worked on any project for more than 4 hours*/
(SELECT E.eID
From Employee E)
INTERSECT
(SELECT W.empID
FROM WorkOrder W
WHERE hoursWorked > 3);

SET ECHO OFF
SPOOL OFF
