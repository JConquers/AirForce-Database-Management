CREATE DATABASE AirForceDB;

USE AirForceDB;

create table AIRCRAFT(
    AID char(5) NOT NULL,
    _Type_ tinyint,
    Crew tinyint,
    constraint PK_AIRCRAFT PRIMARY KEY(AID)
);


create table AVIATOR(
    Service_num char(5),
    _Rank_ tinyint,
    Flying_hours int,
    constraint PK_AVIATOR PRIMARY KEY(Service_num)
);

CREATE TABLE BASE(
    Location VARCHAR(20),
    Capacity TINYINT,
    Holdings TINYINT,
    Commander CHAR(5),
    CONSTRAINT PK_BASE PRIMARY KEY (Location),
    CONSTRAINT FK_COMMANDER FOREIGN KEY (Commander) REFERENCES AVIATOR(Service_num) ON DELETE SET NULL,
    CONSTRAINT HOLDINGS_CONSTRAINT CHECK (Holdings <= Capacity) -- Corrected constraint name
);


CREATE TABLE DEPLOYED_AT(
    AID CHAR(5) NOT NULL,
    Base VARCHAR(20),
    CONSTRAINT PK_DEPLOYED_AT PRIMARY KEY (AID),
    CONSTRAINT FK_DEPLOYED_AT FOREIGN KEY (Base) REFERENCES BASE(Location) ON DELETE CASCADE
);

CREATE TABLE FLIES(
    AID CHAR(5),
    Service_num CHAR(5),
    Hours INT,
    CONSTRAINT PK_FLIES PRIMARY KEY (AID, Service_num),
    CONSTRAINT FK_FLIES FOREIGN KEY (Service_num) REFERENCES AVIATOR(Service_num)
);


