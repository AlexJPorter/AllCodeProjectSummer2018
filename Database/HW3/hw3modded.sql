
-- File: companyDML-b-solution  
-- SQL/DML HOMEWORK (on the COMPANY database)
/*
Every query is worth 2 point. There is no partial credit for a
partially working query - think of this hwk as a large program and each query is a small part of the program.
--
IMPORTANT SPECIFICATIONS
--
(A)
-- Download the script file company.sql and use it to create your COMPANY database.
-- Dowlnoad the file companyDBinstance.pdf; it is provided for your convenience when checking the results of your queries.
(B)
Implement the queries below by ***editing this file*** to include
your name and your SQL code in the indicated places.   
--
(C)
IMPORTANT:
-- Don't use views
-- Don't use inline queries in the FROM clause - see our class notes.
--
(D)
After you have written the SQL code in the appropriate places:
** Run this file (from the command line in sqlplus).
** Print the resulting spooled file (companyDML-b.out) and submit the printout in class on the due date.
--
*/
-- Please don't remove the SET ECHO command below.
SPOOL companyDML-b.out
SET ECHO ON
-- ------------------------------------------------------------
-- 
-- Name: < ***** Alex Porter ***** >
--
-- -------------------------------------------------------------
--
-- NULL AND SUBSTRINGS -------------------------------
--
/*(10B)
Find the ssn and last name of every employee whose ssn contains two consecutive 8's, and has a supervisor. Sort the results by ssn. *Change order*
*/
SELECT lname, ssn
FROM EMPLOYEE
WHERE ssn LIKE '%88%' AND super_ssn IS NOT NULL
ORDER BY ssn;
--
-- JOINING 3 TABLES ------------------------------
-- 
/*(11B)
For every employee who works for more than 20 hours on any project that is controlled by the research department: Find the ssn, project number,  and number of hours. Sort the results by ssn.
*/
SELECT W.essn, W.pno, W.hours
FROM WORKS_ON W, PROJECT P, DEPARTMENT D
WHERE W.pno = P.pnumber AND W.hours >20 AND D.dname = 'Research' AND D.dnumber = P.dnum
ORDER BY W.essn;
--
-- JOINING 3 TABLES ---------------------------
--
/*(12B)
Write a query that consists of one block only.
For every employee who works less than 10 hours on any project that is controlled by the department he works for: Find the employee's lname, his department number, project number, the number of the department controlling it, and the number of hours he works on that project. Sort the results by lname.
*/
SELECT E.lname, E.dno, W.pno, P.dnum, W.hours
FROM WORKS_ON W, PROJECT P, EMPLOYEE E
WHERE W.hours < 10 AND E.dno = P.dnum AND P.pnumber = W.pno AND E.ssn = W.essn
ORDER BY E.lname; 
--
-- JOINING 4 TABLES -------------------------
--
/*(13B)
For every employee who works on any project that is located in Houston: Find the employees ssn and lname, and the names of his/her dependent(s) and their relationship(s) to the employee. Notice that there will be one row per qualyfing dependent. Sort the results by employee lname.
*/
SELECT DISTINCT E.lname, E.ssn, D.dependent_name, D.relationship
FROM EMPLOYEE E, DEPENDENT D, WORKS_ON W, PROJECT P
WHERE P.plocation = 'Houston' AND W.pno = P.pnumber AND E.ssn = W.essn AND D.essn = E.ssn
ORDER BY E.lname;
--
-- SELF JOIN -------------------------------------------
-- 
/*(14B)
Write a query that consists of one block only.
For every employee who works for a department that is different from his supervisor's department: Find his ssn, lname, department number; and his supervisor's ssn, lname, and department number. Sort the results by ssn.  
*/
SELECT E.ssn, E.lname, E.dno, S.ssn, S.lname, S.dno
FROM EMPLOYEE E, EMPLOYEE S
WHERE E.super_ssn = S.ssn AND E.dno <> S.dno AND S.dno IS NOT NULL
ORDER BY E.ssn;
--
-- USING MORE THAN ONE RANGE VARIABLE ON ONE TABLE -------------------
--
/*(15B)
Find pairs of employee lname's such that the two employees in the pair work on the same project for the same number of hours. List every pair once only. Sort the result by the lname in the left column in the result. 
*/
SELECT E1.lname, E2.lname
FROM WORKS_ON W1, WORKS_ON W2, EMPLOYEE E1, EMPLOYEE E2
WHERE E1.ssn < E2.ssn AND W1.essn < W2.essn AND E1.ssn = W1.essn AND E2.ssn = W2.essn AND W1.pno = W2.pno AND W1.hours = W2.hours
ORDER BY E1.lname;
--
/*(16B)
For every employee who has more than one dependent: Find the ssn, lname, and number of dependents. Sort the result by lname
*/
SELECT E.ssn, E.lname, COUNT(*)
FROM EMPLOYEE E, DEPENDENT D
WHERE E.ssn = D.essn
GROUP BY E.ssn, E.lname
HAVING COUNT(*) > 1
ORDER BY E.lname;
-- 
/*(17B)
For every project that has more than 2 employees working on and the total hours worked on it is less than 40: Find the project number, project name, number of employees working on it, and the total number of hours worked by all employees on that project. Sort the results by project number.
*/
SELECT P.pnumber, P.pname, COUNT(DISTINCT E.ssn), SUM(DISTINCT W.hours)
FROM PROJECT P, EMPLOYEE E, WORKS_ON W
WHERE W.essn = E.ssn AND P.pnumber = W.pno
GROUP BY P.pnumber, P.pname
HAVING COUNT(DISTINCT E.ssn) > 2 AND SUM(W.hours) < 40
ORDER BY P.pnumber;
--
-- CORRELATED SUBQUERY --------------------------------
--
/*(18B)
For every employee whose salary is above the average salary in his department: Find the dno, ssn, lname, and salary. Sort the results by department number. 
*/
SELECT E.dno, E.ssn, E.lname, E.salary
FROM EMPLOYEE E
WHERE E.salary > (SELECT AVG(R.salary)
			FROM Employee R
			WHERE E.dno = R.dno)
ORDER BY E.dno;
--
-- CORRELATED SUBQUERY -------------------------------
--
/*(19B)
For every employee who works for the research department but does not work on any one project for more than 20 hours: Find the ssn and lname. Sort the results by lname
*/
SELECT DISTINCT E.ssn, E.lname
FROM EMPLOYEE E, department D, WORKS_ON W
WHERE E.dno = D.dnumber AND D.dname = 'Research' AND E.ssn = W.essn AND 20 >= (SELECT MAX(Q.hours)
			FROM WORKS_ON Q
			WHERE E.ssn = Q.essn
			)
ORDER BY E.lname;
--
-- DIVISION ---------------------------------------------
--
/*(20B) Hint: This is a DIVISION query
For every employee who works on every project that is controlled by department 4: Find the ssn and lname. Sort the results by lname
*/
SELECT E.ssn, E.lname
FROM EMPLOYEE E
WHERE NOT EXISTS((SELECT P.pnumber
			FROM PROJECT P
			WHERE P.dnum = 4)
			MINUS
			(SELECT W.pno
			FROM WORKS_ON W, PROJECT R
			WHERE E.ssn = W.essn AND W.pno = R.pnumber))
ORDER BY E.lname;
--
SET ECHO OFF
SPOOL OFF


