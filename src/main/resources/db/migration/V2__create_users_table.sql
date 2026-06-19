CREATE TABLE users (
   id       SERIAL             NOT NULL,
   user_name VARCHAR(128)    NOT NULL,
   email VARCHAR(128)    NOT NULL UNIQUE,
   password VARCHAR(512)    NOT NULL,
   profile VARCHAR(512)  NOT NULL,
   team  VARCHAR(128)   NOT NULL,
   Job title  VARCHAR(128),
   PRIMARY KEY (id)
);