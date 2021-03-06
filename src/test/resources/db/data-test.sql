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

INSERT INTO clients (company_id, firstname, lastname, middlename, telnumber, city, created_by, created_on) VALUES
(10000, 'Олег', 'Иванов', 'Иванович', '+7(911)111-11-16', 'Анапа', 10004, '2020-06-02 11:00:00'),                       /*10_005*/
(10000, 'Александр', 'Иванов', 'Иванович', '+7(911)111-11-17', 'Новороссийск', 10004, '2020-06-02 10:00:00'),           /*10_006*/
(10000, 'Сергей', 'Иванов', 'Иванович', '+7(911)111-11-18', 'Новороссийск', 10004, '2020-06-02 12:00:00');              /*10_007*/

INSERT INTO tickets (company_id, pass, name, enable, equipment, duration, start_date, end_date, start_time, end_time, month, day, year, cost, weekendcost, created_by, created_on) VALUES
(10000, 'SEASON', 'Сезоный абонемент',true, true, 5, '2020-06-01', '2020-10-01', null, null, 0, 0, 2020, 100, 120, 10004, '2020-06-02 11:00:00'), /*10_008*/
(10000, 'SINGLE', 'Разовый абонемент',true, true, 5, null, null, '10:00', '18:00', 0, 0, 2020, 10, 11, 10004, '2020-06-02 12:00:00'),             /*10_009*/
(10000, 'SEASON', 'Абонемент на месяц',false, false, 5, null, null, '09:00', '18:00', 1, 0, 2020, 90, 95, 10004, '2020-06-02 13:00:00');          /*10_010*/

INSERT INTO client_ticket (company_id, client_id, ticket_id, date_start, date_end, active, created_on, created_by) VALUES
(10000, 10005, 10009, null, null, true, '2020-06-02 10:00:00', 10002),
(10000, 10005, 10009, null, null, true, '2020-06-02 11:00:00', 10002),
(10000, 10006, 10008, '2020-06-01', '2020-10-01', true, '2020-06-02 12:00:00', 10003),
(10000, 10005, 10008, null, null, false, '2020-06-02 13:00:00', 10002);