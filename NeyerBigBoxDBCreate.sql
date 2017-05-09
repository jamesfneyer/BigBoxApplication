DROP DATABASE IF EXISTS bigbox;
CREATE DATABASE bigbox;
USE bigbox;

DROP TABLE IF EXISTS Stores;

CREATE TABLE divisions
(
  ID    INT            PRIMARY KEY  AUTO_INCREMENT,
  DivNumber varchar(3)		NOT NULL,
  StoreName VARCHAR(255)		NOT NULL,
  Address varchar(255)		NOT NULL,
  City varchar(255)			NOT NULL,
  State varchar(2)			NOT NULL,
  ZipCode varchar(5)		NOT NULL
);
CREATE TABLE Stores
(
  ID    INT            PRIMARY KEY  AUTO_INCREMENT,
  DivisionID INT  			NOT NULL,
  StoreNumber  VARCHAR(5)		NOT NULL,  
  StoreName VARCHAR(30)		NOT NULL,
  Address varchar(30)		NOT NULL,
  City varchar(15)			NOT NULL,
  State varchar(2)			NOT NULL,
  ZipCode varchar(5)		NOT NULL,
  Foreign Key (DivisionID) references divisions(ID)
);
CREATE TABLE store_sales	
(
  ID    	INT            PRIMARY KEY  AUTO_INCREMENT,
  StoreID	INT				NOT NULL,
  Year 		INT  			NOT NULL,
  Week 		INT				NOT NULL,
  Sales 	DECIMAL(10,2)	NOT NULL,
  CONSTRAINT store_year_week unique (StoreID,Year,Week),
  Foreign Key (StoreID) references stores(ID)
);
INSERT INTO divisions VALUES 
(1, '001', 'Cincinnati Division Office', '2200 Fields Ertel Rd.', 'Mason', 'OH', '45249'),
(2, '111', 'Louisville Division Office', '4000 Liberty St.', 'Louisville', 'KY', '40204');

INSERT INTO stores VALUES 
(1, 1, '00011', 'Mason Big Box', '5711 Fields Ertel Rd.', 'Mason', 'OH', '45249'),
(2, 1, '00255', 'Downtown Big Box','9330 Main St.', 'Cincinnati', 'OH', '45249'),
(3, 1, '00172', 'Goshen Big Box','6777 Goshen Rd.', 'Goshen', 'OH', '45249'),
(4, 1, '00075', 'Bridgetown Big Box','3888 Race Rd.', 'Cincinnati', 'OH', '45249'),
(5, 2, '00001', 'Louisville Big Box','1111 Washington St.', 'Louisville', 'KY', '40206'),
(6, 2, '00044', 'Riverfront Big Box','8000 Liberty St.', 'Louisville', 'KY', '40204');

INSERT INTO store_sales (StoreID, Year, Week, Sales) VALUES
(1, 2017, 2, 3445555.89),
(1, 2017, 3, 3445555.89),
(1, 2017, 4, 3445555.89),
(1, 2017, 5, 3445555.89),
(2, 2017, 10, 3444433.33),
(2, 2017, 11, 3444433.33),
(2, 2017, 12, 3444433.33),
(2, 2017, 13, 3444433.33),
(2, 2017, 14, 3444433.33),
(3, 2017, 21, 4555543.66),
(3, 2017, 22, 4555543.66),
(3, 2017, 23, 4555543.66),
(3, 2017, 24, 4555543.66),
(3, 2017, 25, 4555543.66),
(3, 2017, 26, 4555543.66),
(3, 2017, 27, 4555543.66),
(4, 2017, 2, 3444555.99),
(4, 2017, 3, 3444555.99),
(5, 2017, 11, 7668944.33),
(5, 2017, 12, 7668944.33),
(5, 2017, 13, 7668944.33),
(5, 2017, 14, 7668944.33),
(6, 2017, 16, 8777655.33),
(6, 2017, 17, 8777655.33),
(6, 2017, 18, 8777655.33);
GRANT SELECT, INSERT, DELETE, UPDATE
ON bigbox.*
TO bigbox_user@localhost
IDENTIFIED BY 'sesame';