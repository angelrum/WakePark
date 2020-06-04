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
('WakePark1', 'ООО ТестКомпания1', '+7(911)111-11-11'),
('WakePark2', 'ООО ТестКомпания2', '+7(921)111-11-11');

INSERT INTO users (company_id, firstname, lastname, middlename, telnumber, login, password, email, created_by ,created_on) VALUES
(10000, 'Ivan', 'Ivanov', 'Ivanovich', '+7(911)111-11-13', 'test1', '12345', 'test1@test.ru', null, '2020-06-02 10:00:00'),                                     /*10_002*/
(10000, 'Vova', 'Ivanov', 'Ivanovich', '+7(911)111-11-14', 'test2', '123456', 'test2@test.ru', 10002, '2020-06-02 10:00:00');                                   /*10_003*/

INSERT INTO users (company_id, firstname, lastname, middlename, telnumber, login, password, email, created_by, created_on, changed_by, changed_on) VALUES
(10000, 'Yulia', 'Ivanova', 'Ivanovna', '+7(911)111-11-15', 'test3', '1234567', 'test3@test.ru', 10002, '2020-06-02 10:00:00', 10002, '2020-06-02 12:00:00');   /*10_004*/

INSERT INTO user_roles (user_id, role) VALUES
(10002, 'ADMIN'),
(10003, 'MANAGER'),
(10003, 'USER'),
(10004, 'USER');