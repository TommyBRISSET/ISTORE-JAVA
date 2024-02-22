-- Create the istore database
CREATE DATABASE IF NOT EXISTS istore;

-- Use the istore database
USE istore;

-- Create User table
CREATE TABLE User (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    pseudo VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Create Whitelist table
CREATE TABLE Whitelist (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL
);

-- Create Store table
CREATE TABLE Store (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

-- Create Inventory table
CREATE TABLE Inventory (
    id INT PRIMARY KEY AUTO_INCREMENT,
    store_id INT NOT NULL,
    FOREIGN KEY (store_id) REFERENCES Store(id)
);

-- Create Item table
CREATE TABLE Item (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 0),
    inventory_id INT NOT NULL,
    FOREIGN KEY (inventory_id) REFERENCES Inventory(id)
);

-- Create EmployeeStoreAccess table for tracking employees with access to stores
CREATE TABLE EmployeeStoreAccess (
    id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT NOT NULL,
    store_id INT NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES User(id),
    FOREIGN KEY (store_id) REFERENCES Store(id)
);

-- Create Message table
CREATE TABLE Message (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    title VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (sender_id) REFERENCES User(id),
    FOREIGN KEY (receiver_id) REFERENCES User(id)
);
