CREATE TABLE Users(
	username varchar(255) NOT NULL PRIMARY KEY,
	password varchar(255) NOT NULL, 
	email varchar(255) NOT NULL
);

CREATE TABLE Person(
	person_id varchar(255) NOT NULL PRIMARY KEY,
	username varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	gender varchar(1) NOT NULL,
	father_id varchar(255),
	mother_id varchar(255),
	spouse_id varchar(255)
);

CREATE TABLE Authorization_Token(
	auth_token varchar(255) NOT NULL PRIMARY KEY, 
	username varchar(255)
);

CREATE TABLE Event (
	event_id varchar(255) NOT NULL PRIMARY KEY,
	person varchar(255) NOT NULL,
	latitude float,
	longitude float,
	country varchar(255), 
	city varchar(255),
	event_type varchar(255) NOT NULL, 
	year int NOT NULL
);
