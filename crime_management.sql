create database crimemanagement;

use crimemanagement;

create table crime(
crime_id int AUTO_INCREMENT primary key,
incident_type varchar(255) not null,
incident_date date,
location varchar(255),
description text,
status varchar(255) 
);

create table victim(
victim_id int AUTO_INCREMENT primary key,
crime_id int,
name varchar(255) not null,
contact_info varchar(255),
injuries varchar(255),
foreign key (crime_id) references crime(crime_id)
);

create table suspect(
suspect_id int AUTO_INCREMENT primary key,
crime_id int,
name varchar(255),
description text,
criminal_history text,
foreign key (crime_id) references crime(crime_id) 
);

INSERT INTO crime (incident_type, incident_date, location, description, status)
VALUES
('Robbery', '2023-09-15', '123 Main St, Cityville', 'Armed robbery at a convenience store', 'Open'),
('Homicide', '2023-09-20', '456 Elm St, Townsville', 'Investigation into a murder case', 'Under
Investigation'),
('Theft', '2023-09-10', '789 Oak St, Villagetown', 'Shoplifting incident at a mall', 'Closed');

INSERT INTO Victim (crime_id, Name, contact_info, injuries)
VALUES
(1, 'John Doe', 'johndoe@example.com', 'Minor injuries'),
(2, 'Jane Smith', 'janesmith@example.com', 'Deceased'),
(3, 'Alice Johnson', 'alicejohnson@example.com', 'None');

INSERT INTO suspect (crime_id, name, description, criminal_history)
VALUES
(1, 'Robber 1', 'Armed and masked robber', 'Previous robbery convictions'),
(2, 'Unknown', 'Investigation ongoing', NULL),
(3, 'Suspect 1', 'Shoplifting suspect', 'Prior shoplifting arrests');

select * from crime where status = 'Open';  #1

select count(*) 'no_of_incident' from crime; #2

select distinct incident_type 'incident_type' from crime; #3

select * from crime where incident_date between '2023-09-01' and '2023-09-10'; #4

 select incident_type , count(*) 'counts' from crime where status = 'Open' group by incident_type; #7
 
SELECT Name FROM Victim WHERE Name LIKE '%Doe%'
UNION
SELECT Name FROM Suspect WHERE Name LIKE '%Doe%';  #8

SELECT v.Name, c.Status
FROM Victim v
JOIN Crime c using (crime_id)
WHERE c.Status IN ('Open', 'Closed')
UNION
SELECT s.Name, c.Status
FROM Suspect s
JOIN Crime c using (crime_id)
WHERE c.Status IN ('Open', 'Closed'); #9

SELECT v.Name
FROM Victim v
JOIN Crime c using (crime_id)
WHERE c.incident_type = 'Robbery'
UNION
SELECT s.Name
FROM Suspect s
JOIN Crime c using (crime_id)
WHERE c.incident_type = 'Robbery'; #11

SELECT incident_type, COUNT(*) AS OpenCases
FROM Crime
WHERE Status = 'Open'
GROUP BY incident_type
HAVING COUNT(*) > 1;  #12

SELECT DISTINCT c.*
FROM Crime c
JOIN Suspect s using (crime_id)
WHERE s.Name IN (
    SELECT Name FROM Victim
);  #13

SELECT c.*, v.Name AS VictimName, s.Name AS SuspectName
FROM Crime c
LEFT JOIN Victim v using (crime_id)
LEFT JOIN Suspect s using (crime_id); #14

SELECT Name, COUNT(*) AS IncidentCount
FROM Suspect
GROUP BY Name
HAVING COUNT(*) > 1; #16

SELECT * FROM Crime
WHERE crime_id NOT IN (SELECT DISTINCT crime_id FROM Suspect); #17

SELECT * FROM Crime
WHERE EXISTS (
    SELECT 1 FROM Crime 
    WHERE incident_type in ('Homicide')
)
AND EXISTS (
    SELECT 1 FROM Crime 
    WHERE incident_type in ('Robbery', 'Homicide')
);  #18

SELECT c.crime_id, c.incident_type, IFNULL(s.Name, 'No Suspect') AS SuspectName
FROM Crime c
LEFT JOIN Suspect s using (crime_id); #19

SELECT DISTINCT s.Name
FROM Suspect s
JOIN Crime c using (crime_id)
WHERE c.incident_type IN ('Robbery', 'Assault'); #20


TRUNCATE TABLE Victim;
TRUNCATE TABLE Suspect;
alter table victim add column Age int;

INSERT INTO victim (Crime_id, Name, contact_info, injuries, Age)
VALUES
    (1, 'John Doe', 'johndoe@example.com', 'Minor injuries', 35),
    (2, 'Jane Smith', 'janesmith@example.com', 'Deceased', 30),
    (3, 'Alice Johnson', 'alicejohnson@example.com', 'None', 25);

ALTER TABLE Suspect ADD Age INT;

INSERT INTO Suspect (Crime_id, Name, Description, Criminal_history, Age)
VALUES
    (1, 'Robber 1', 'Armed and masked robber', 'Previous robbery convictions', 40),
    (2, 'Unknown', 'Investigation ongoing', NULL, 32),
    (3, 'Suspect 1', 'Shoplifting suspect', 'Prior shoplifting arrests', 30);
    
SELECT Name, Age, 'Victim' AS Role FROM Victim
UNION ALL
SELECT Name, Age, 'Suspect' AS Role FROM Suspect
ORDER BY Age DESC; #5

SELECT AVG(Age) AS AverageAge FROM (
    SELECT Age FROM Victim
    UNION ALL
    SELECT Age FROM Suspect
) AS AllPersons;  #6

SELECT DISTINCT c.Incident_type
FROM Crime c
JOIN Victim v using(crime_id)
WHERE v.Age IN (30, 35)
UNION
SELECT DISTINCT c.Incident_type
FROM Crime c
JOIN Suspect s using (crime_id)
WHERE s.Age IN (30, 35); #10

SELECT DISTINCT c.*
FROM Crime c
JOIN Suspect s using(crime_id)
WHERE s.Age > ALL (
    SELECT v.Age FROM Victim v WHERE v.Crime_id = c.Crime_id
);  #15
