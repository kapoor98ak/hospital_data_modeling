use sprint2;
INSERT INTO HospitalClinicLocationMapping (HospitalID, ClinicID, LocationID, Latitude, Longitude, City, State, Country, PINCode, InstanceName, InstanceStatus)
VALUES
(1, 101, 1001, 40.7128, -74.0060, 'New York City', 'New York', 'USA', '10001', 'Instance A', 'Active'),
(2, 102, 1002, 34.0522, -118.2437, 'Los Angeles', 'California', 'USA', '90001', 'Instance B', 'Active'),
(3, 103, 1003, 51.5074, -0.1278, 'London', 'England', 'UK', 'SW1A 1AA', 'Instance A', 'Active'),
(4, 104, 1004, 48.8566, 2.3522, 'Paris', 'Île-de-France', 'France', '75001', 'Instance B', 'Active');
