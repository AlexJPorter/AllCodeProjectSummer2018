-- File: PLh10.sql
-- Author: <<<Alex Porter>>>
-- ----------------------------------
SET SERVEROUTPUT ON
SET VERIFY OFF
-- ----------------------------------
ACCEPT traineeID NUMBER PROMPT 'Enter a trainee ID: '
ACCEPT increment NUMBER PROMPT 'Enter an increment for his trainers: '
DECLARE
 sr sailors%ROWTYPE;
 noneFound EXCEPTION;

 CURSOR tCursor IS
 SELECT S.sid, S.sname, S.rating, S.age, S.trainee
 FROM Sailors S
 WHERE S.trainee='&traineeID';
BEGIN
 OPEN tCursor;
 LOOP
 -- Fetch the qualifying rows one by one
	FETCH tCursor INTO sr;
	EXIT WHEN tCursor%NOTFOUND;
 -- Print the sailor' old record
 DBMS_OUTPUT.PUT_LINE('+++++ old row: '||sr.sid||' '||sr.sname||sr.rating||' '||sr.age||' '||sr.trainee); 
 -- Increment the trainers' rating
 sr.rating := sr.rating + &increment;
 UPDATE Sailors
 set rating = sr.rating
 WHERE sailors.sid = sr.sid;
 -- Print the sailor' new record
 DBMS_OUTPUT.PUT_LINE ('+++++ new row: '||sr.sid||' '||sr.sname||sr.rating||' '||sr.age||' '||sr.trainee);
 END LOOP;
 -- test whether the sailor has no trainers (Hint: test tCursor%ROWCOUNT)
 IF tCursor%ROWCOUNT=0
 THEN RAISE noneFound;
 END IF;
 CLOSE tCursor;
EXCEPTION
WHEN noneFound THEN
 DBMS_OUTPUT.PUT_LINE('+++++ '||' &traineeID'||' is either not a sailor, or has no trainer');
 CLOSE tCursor;
WHEN OTHERS THEN
 DBMS_OUTPUT.PUT_LINE('+++++'||SQLCODE||'...'||SQLERRM);
 END;
/
-- Let's see what happened to the database
SELECT *
FROM sailors S
WHERE S.trainee = '&traineeID';
UNDEFINE traineeID
UNDEFINE increment

 
