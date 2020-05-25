DELETE FROM register;
DELETE FROM client_ticket_story;
DELETE FROM client_ticket;
DELETE FROM tickets;
DELETE FROM clients;
DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM companys;

ALTER SEQUENCE global_seq   RESTART WITH 10000;
ALTER SEQUENCE register_seq RESTART WITH 1000;

INSERT INTO companys (name, contact_name, contact_phone) VALUES
('WakeBro', 'ООО ТестКомпани', '+7(911)111-11-11');

INSERT INTO users (company_id, firstname, lastname, middlename, telnumber, login, password, email, created_by) VALUES
(10000, 'Иван', 'Иванов', 'Иванович', '+7(911)111-11-12', 'admin', '12345', 'admin@mail.ru', null),
(10000, 'Антон', 'Иванов', 'Иванович', '+7(911)111-11-13', 'manager', '123456', 'manager@mail.ru', 10001);

INSERT INTO user_roles (user_id, role) VALUES
(10001, 'ADMIN'),
(10002, 'MANAGER');

INSERT INTO clients (company_id, firstname, lastname, middlename, telnumber, city, created_by) VALUES
(10000, 'Олег', 'Иванов', 'Иванович', '+7(911)111-11-14', 'Анапа', 10002),
(10000, 'Александр', 'Иванов', 'Иванович', '+7(911)111-11-15', 'Новороссийск', 10002)
