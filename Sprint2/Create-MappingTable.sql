USE sprint2;
DROP TABLE IF EXISTS HospitalClinicLocationMapping;
CREATE TABLE HospitalClinicLocationMapping (
    HospitalID INT NOT NULL,
    ClinicID INT NOT NULL,
    LocationID INT NOT NULL,
    Latitude DECIMAL(10,4),
    Longitude DECIMAL(10,4),
    City VARCHAR(100),
    State VARCHAR(100),
    Country VARCHAR(100),
    PINCode VARCHAR(20),
    InstanceName VARCHAR(50),
    InstanceStatus VARCHAR(50),
    PRIMARY KEY (HospitalID, ClinicID, LocationID)
);
