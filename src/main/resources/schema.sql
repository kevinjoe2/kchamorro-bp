DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    gender VARCHAR(16),
    date_birth DATE,
    document_number VARCHAR(32) UNIQUE,
    address VARCHAR(255),
    phone VARCHAR(32),
    client_id VARCHAR(32),
    password VARCHAR(32),
    state VARCHAR(32)
);

DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    customer_id INTEGER,
    account_number VARCHAR(255) UNIQUE,
    account_type VARCHAR(16),
    balance DECIMAL(15,2),
    state VARCHAR(32)
);

DROP TABLE IF EXISTS transactions;
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    account_id INTEGER,
    transaction_date TIMESTAMP,
    transaction_type VARCHAR(16),
    transaction_value DECIMAL(15,2),
    account_initial_balance DECIMAL(15,2),
    account_ending_balance DECIMAL(15,2)
);

DROP TABLE IF EXISTS parameters;
CREATE TABLE parameters (
    id SERIAL PRIMARY KEY,
    "name" VARCHAR(128),
    "value" VARCHAR(255)
);

INSERT INTO customers ("name", gender, date_birth, document_number, address, phone, client_id, "password", state)
VALUES ('Kevin Chamorro', 'MALE', '1996-01-21','0401859376','Alejandro de Valdez N24-40 y Avenida la Gasca', '+593996427491', 'CUS001', '123456', 'ENABLE');

INSERT INTO accounts (customer_id, account_number, account_type, balance, state)
VALUES ((SELECT id FROM customers WHERE document_number='0401859376'),
        '049999','Ahorros', 2000, 'ENABLED');

INSERT INTO transactions (account_id, transaction_date, transaction_type, transaction_value, account_initial_balance, account_ending_balance)
VALUES ((SELECT id FROM accounts WHERE account_number='049999'),
        '2022-09-03','DEPOSITO', 10, 2000, 2010);

INSERT INTO parameters (name, value)
VALUES ('LimiteCupoDiarioRetiro','1000');
