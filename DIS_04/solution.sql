-- connects to DB2
connect to VSISP user vsisp15
-- Password: eCULVnyP

attach to vsisls4 user vsisp15


-- reads the current isolation level
values current isolation

-- set the iso level
set current isolation CS -- UR, RESET ...

-- CS: Nicht so viel Performanceverlust,
-- aber immer noch genügend sicherheit

-- Schreib-Sperre wird immer bis zum Ende des commits gehalten => kein Dirty Read möglich bei CS

-- create the test table
create table OPK (ID integer, NAME varchar(50));

-- insert test data
insert into OPK (ID, NAME) values (1, 'Joanna');
insert into OPK (ID, NAME) values (2, 'Tobi');
insert into OPK (ID, NAME) values (3, 'Corny');
insert into OPK (ID, NAME) values (4, 'Steffen');
insert into OPK (ID, NAME) values (5, 'Norbert');
insert into OPK (ID, NAME) values (6, 'Carl Adam');
insert into OPK (ID, NAME) values (7, 'Albert');

-- query test data
select * from OPK;

list applications | grep VSISP15;

-- create the test table
create table MPK (ID integer not null primary key, NAME varchar(50))
insert into MPK (ID, NAME) values (1, 'Joanna')
insert into MPK (ID, NAME) values (2, 'Tobi')
insert into MPK (ID, NAME) values (3, 'Corny')
insert into MPK (ID, NAME) values (4, 'Steffen')
insert into MPK (ID, NAME) values (5, 'Norbert')
insert into MPK (ID, NAME) values (6, 'Carl Adam')
insert into MPK (ID, NAME) values (7, 'Albert')

create table LOL (ID integer not null primary key, NAME varchar(50))

-- Ex 43
set current isolation RR

SELECT * FROM OPK WHERE id=1 -- conn 1
SELECT * FROM OPK WHERE id=2 -- conn 2

-- conn 1:
get snapshot for locks for application agentid 24843
-- conn 2:
get snapshot for locks for application agentid 24847
