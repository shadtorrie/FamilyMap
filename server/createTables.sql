CREATE TABLE Users(
	username varchar(255)  PRIMARY KEY,
	password varchar(255) , 
	email varchar(255) 
);

CREATE TABLE Person(
	person_id varchar(255)  PRIMARY KEY,
	username varchar(255) ,
	first_name varchar(255) ,
	last_name varchar(255) ,
	gender varchar(1) ,
	father_id varchar(255),
	mother_id varchar(255),
	spouse_id varchar(255)
);

CREATE TABLE Authorization_Token(
	auth_token varchar(255)  PRIMARY KEY, 
	username varchar(255)
);

CREATE TABLE Event (
	event_id varchar(255)  PRIMARY KEY,
	person varchar(255) ,
	latitude float,
	longitude float,
	country varchar(255), 
	city varchar(255),
	event_type varchar(255) , 
	year int 
);
