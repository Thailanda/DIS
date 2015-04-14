CREATE TABLE EstateAgent(
	id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
	name varchar(255),
 	address varchar(255),
 	login varchar(40) UNIQUE,
 	password varchar(40));
 	
CREATE TABLE Estate(
	id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
	manager_id INTEGER NOT NULL,
	city varchar(255),
 	postal_code varchar(50),
 	street varchar(255),
 	street_number varchar(50),
 	square_area INTEGER,
 	CONSTRAINT FK_MANAGER FOREIGN KEY(manager_id) REFERENCES EstateAgent(id));
 	
CREATE TABLE Apartment(
	estate_id INTEGER NOT NULL PRIMARY KEY,
	floor varchar(50),
 	rent DECIMAL(8,2),
 	rooms INTEGER,
 	balcony TINYINT(1),
 	built_in_kitchen TINYINT(1),
 	CONSTRAINT FK_ESTATE FOREIGN KEY(estate_id) REFERENCES Estate(id));
 	
CREATE TABLE House(
	estate_id INTEGER NOT NULL PRIMARY KEY,
	floors varchar(50),
 	price DECIMAL(8,2),
 	garden TINYINT(1),
 	CONSTRAINT FK_ESTATE FOREIGN KEY(estate_id) REFERENCES Estate(id));
 	
CREATE TABLE Person(
	id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
	first_name VARCHAR(255),
	name VARCHAR(255),
	address VARCHAR(255));
	
CREATE TABLE Sell(
	house_id INTEGER NOT NULL UNIQUE,
	person_id INTEGER NOT NULL,
	contract_id INTEGER NOT NULL,
	CONSTRAINT FK_HOUSE FOREIGN KEY(house_id) REFERENCES House(estate_id),
	CONSTRAINT FK_PERSON FOREIGN KEY(person_id) REFERENCES Person(id),
	CONSTRAINT FK_CONTRACT FOREIGN KEY(contract_id) REFERENCES PurchaseContract(contract_id),
	CONSTRAINT UNIQ_HOUSE UNIQUE(house_id),
	CONSTRAINT UNIQ_CONSTRAINT UNIQUE(contract_id),
	PRIMARY KEY(house_id, person_id, contract_id));
	
CREATE TABLE Rent(
	apartment_id INTEGER NOT NULL UNIQUE,
	person_id INTEGER NOT NULL,
	contract_id INTEGER NOT NULL,
	CONSTRAINT FK_APARTMENT FOREIGN KEY(apartment_id) REFERENCES Apartment(estate_id),
	CONSTRAINT FK_PERSON FOREIGN KEY(person_id) REFERENCES Person(id),
	CONSTRAINT FK_CONTRACT FOREIGN KEY(contract_id) REFERENCES TenancyContract(contract_id),
	CONSTRAINT UNIQ_APARTMENT UNIQUE(apartment_id),
	CONSTRAINT UNIQ_CONSTRAINT UNIQUE(contract_id),
	PRIMARY KEY(apartment_id, person_id, contract_id));
	
-- Contracts with inheritance
CREATE TABLE Contract(
	id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
	contract_no VARCHAR(255),
	date DATETIME,
	place VARCHAR(255)); 
	
CREATE TABLE TenancyContract(
	contract_id INTEGER NOT NULL PRIMARY KEY,
	start_date DATETIME,
	duration INTEGER,
	additional_costs DECIMAL(8,2),
	CONSTRAINT FK_CONTRACT FOREIGN KEY(contract_id) REFERENCES Contract(id));
 	
CREATE TABLE PurchaseContract(
	contract_id INTEGER NOT NULL PRIMARY KEY,
	no_of_installments INTEGER,
	intrest_rate DECIMAL(8,5),
	CONSTRAINT FK_CONTRACT FOREIGN KEY(contract_id) REFERENCES Contract(id));

