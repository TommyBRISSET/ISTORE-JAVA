-- Create the istore_test database
CREATE DATABASE IF NOT EXISTS istore_test;

-- Use the istore_test database
USE istore_test;

-- Create User table
CREATE TABLE User (
        id INT PRIMARY KEY AUTO_INCREMENT,
        email VARCHAR(255) UNIQUE NOT NULL,
        pseudo VARCHAR(255) NOT NULL,
        password_hash VARCHAR(255) NOT NULL,
        role VARCHAR(50) NOT NULL
);

-- Insert data 
INSERT INTO User (email, pseudo, password_hash, role) VALUES
        ('user1@example.com', 'user1', 'hash1', 'user'),
        ('user2@example.com', 'user2', 'hash2', 'user'),
        ('admin@example.com', 'admin', 'hashadmin', 'admin');

-- Create Whitelist table
CREATE TABLE Whitelist (
        id INT PRIMARY KEY AUTO_INCREMENT,
        email VARCHAR(255) UNIQUE NOT NULL
);

-- Insert data 
INSERT INTO Whitelist (email) VALUES
        ('user1@example.com'),
        ('admin@example.com');

-- Create Store table
CREATE TABLE Store (
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(255) NOT NULL
);

-- Insert data 
INSERT INTO Store (name) VALUES
        ('Store1'),
        ('Store2');

-- Create Inventory table
CREATE TABLE Inventory (
        id INT PRIMARY KEY AUTO_INCREMENT,
        store_id INT NOT NULL,
        FOREIGN KEY (store_id) REFERENCES Store(id)
);

-- Insert data 
INSERT INTO Inventory (store_id) VALUES
        (1),
        (2);

-- Create Item table
CREATE TABLE Item (
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(255) NOT NULL,
        price DECIMAL(10, 2) NOT NULL,
        quantity INT NOT NULL CHECK (quantity >= 0),
        inventory_id INT NOT NULL,
        FOREIGN KEY (inventory_id) REFERENCES Inventory(id)
);

-- Insert data 
INSERT INTO Item (name, price, quantity, inventory_id) VALUES
        ('Item1', 10.99, 100, 1),
        ('Item2', 5.99, 50, 2);

-- Create EmployeeStoreAccess table 
CREATE TABLE EmployeeStoreAccess (
        id INT PRIMARY KEY AUTO_INCREMENT,
        employee_id INT NOT NULL,
        store_id INT NOT NULL,
        FOREIGN KEY (employee_id) REFERENCES User(id),
        FOREIGN KEY (store_id) REFERENCES Store(id)
);

-- Insert data
INSERT INTO EmployeeStoreAccess (employee_id, store_id) VALUES
        (1, 1),
        (2, 2);

-- Create Message table
CREATE TABLE Message (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (sender_id) REFERENCES User(id),
    FOREIGN KEY (receiver_id) REFERENCES User(id)
);

-- Insert data
INSERT INTO Message (sender_id, receiver_id, title, content) VALUES (1, 2, 'Titre du message', 'Contenu du message');
INSERT INTO Message (sender_id, receiver_id, title, content) VALUES (1, 3, 'Titre du message1', 'Contenu du message1');
