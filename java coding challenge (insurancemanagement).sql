use insurancemanage;

CREATE TABLE User (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(30) NOT NULL
);
CREATE TABLE Client (
    clientId INT AUTO_INCREMENT PRIMARY KEY,
    clientName VARCHAR(100) NOT NULL,
    contactInfo VARCHAR(150),
    policy VARCHAR(100) NOT NULL
);
CREATE TABLE Claim (
    claimId INT AUTO_INCREMENT PRIMARY KEY,
    claimNumber VARCHAR(50) NOT NULL UNIQUE,
    dateFiled DATE NOT NULL,
    claimAmount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    policy VARCHAR(100) NOT NULL,
    clientId INT,
    FOREIGN KEY (clientId) REFERENCES Client(clientId)
);
CREATE TABLE Payment (
    paymentId INT AUTO_INCREMENT PRIMARY KEY,
    paymentDate DATE NOT NULL,
    paymentAmount DECIMAL(10, 2) NOT NULL,
    clientId INT,
    FOREIGN KEY (clientId) REFERENCES Client(clientId)
);

CREATE TABLE Policy (
    policyId INT AUTO_INCREMENT PRIMARY KEY,
    policyName VARCHAR(100) NOT NULL,
    policyType VARCHAR(50) NOT NULL,
    premiumAmount DOUBLE NOT NULL,
    coverageAmount DOUBLE NOT NULL
);



