SPOOL apt.out
SET ECHO ON

--
-- SELF JOIN --
--
/*
For every resident that lives with at least one other person and find the apartment number and the last name of the eldest resident.
*/

SELECT a.aptNum r.lastName
FROM Resident r, Resident r2, AptUnit a
WHERE r.aptNum = r2.aptNum AND a.aptNum = r.aptNum AND r.age > r1.age;

--
SELECT *
FROM Residents;

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
/*Give the employee ID and last name of the employee that worked the most hours on any one WorkOrder, but only if it's more than 8*/
SELECT E.eID, E.lastname, MAX(W.hoursWorked)
FROM Employee E, WorkOrder W
WHERE E.eID=W.empID
GROUP BY E.eID, E.lastname
HAVING W.hoursWorked > 8;
-- 5 --
/*Give me the count of all the work orders filed by the resident with Id [SOME ID]*/
-- 7 -- 
/* Give me the names and ids of all the residents that have not filed work orders who pay more than $400 in rent*/
-- 6 --
/*Give me the names and IDS of all employees that have completed work orders after [SOME DATE]
SET ECHO OFF
SPOOL OFF
