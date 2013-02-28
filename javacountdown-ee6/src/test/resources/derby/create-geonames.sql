CREATE TABLE GEONAMES3 (
 country   VARCHAR(200) NOT NULL PRIMARY KEY,
 alpha2      CHAR(2) NOT NULL,
alpha3      CHAR(3) NOT NULL,
 numcode  int NOT NULL,
latitude  double NOT NULL,
longitude  double NOT NULL
);
