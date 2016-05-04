CREATE TABLE TICKETSERVICE.FLIGHTS (
	id 					INT PRIMARY KEY, 
    airline 			VARCHAR(200) NOT NULL, 
    departureTime 		DATETIME NOT NULL, 
    originAirport		VARCHAR(3) NOT NULL, 
    destinationAirport	VARCHAR(3) NOT NULL, 
    tripDurationMins	INT NOT NULL, 
    pricePerSeatEuro	INT NOT NULL, 
    seatsAvailable		INT NOT NULL
); 

CREATE TABLE TICKETSERVICE.AUDIT_LOG (
	reqID 			BIGINT NOT NULL auto_increment, 
    reqDate 		DATE NOT NULL, 
    reqDateTime		DATETIME, 
    fromAirport		VARCHAR(200), 
    toAirport		VARCHAR(200), 
    departureDate	DATE, 
    seatsRequested	INT, 
    PRIMARY KEY (reqID, reqDate)

); 

CREATE TABLE TICKETSERVICE.RESERVATION_AUDIT_LOG (
	resID 			BIGINT NOT NULL auto_increment, 
    resDate 		DATE NOT NULL, 
    resDateTime		DATETIME, 
    flightID		INTEGER, 
    seatNumber		INTEGER, 
    reserveeName 	VARCHAR(200), 
    reserveePhone	VARCHAR(200), 
    reserveeEmail 	VARCHAR(200), 
    passengers 		VARCHAR(5000), 
    status 			INTEGER DEFAULT 1, 
    PRIMARY KEY (resID, resDate)
); 

CREATE TABLE TICKETSERVICE.USERS (	
	userName 		VARCHAR(200) NOT NULL PRIMARY KEY, 
    passwordHash	VARCHAR(500), 
	hashSalt		VARCHAR(100),
    role			VARCHAR(50)
); 

