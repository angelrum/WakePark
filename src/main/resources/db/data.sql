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
('WakeBro', 'ООО ТестКомпани', '+7(911) 111-11-11');

INSERT INTO users (company_id, avatar, firstname, lastname, middlename, telnumber, login, password, email, created_by) VALUES
(10000, 'https://img.icons8.com/bubbles/50/000000/student-male.png', 'Иван', 'Иванов', 'Иванович', '+7(911) 111-11-12', 'admin', '$2a$10$./aq9gPbMpuuzwDtRRPjY.LGRdA4MPnSFWJEf4W/5ehMlWqorvOJm', 'admin@mail.ru', null), /* password = pass */
(10000, 'https://img.icons8.com/bubbles/50/000000/student-male.png', 'Антон', 'Иванов', 'Иванович', '+7(911) 111-11-13', 'manager', '$2a$10$./aq9gPbMpuuzwDtRRPjY.LGRdA4MPnSFWJEf4W/5ehMlWqorvOJm', 'manager@mail.ru', 10001);

INSERT INTO user_roles (user_id, role) VALUES
(10001, 'ADMIN'),
(10002, 'MANAGER');

INSERT INTO clients (company_id, firstname, lastname, middlename, telnumber, city, created_by) VALUES
(10000, 'Олег', 'Иванов', 'Иванович', '+7(911) 111-11-14', 'Анапа', 10002),
(10000, 'Александр', 'Иванов', 'Иванович', '+7(911) 111-11-15', 'Новороссийск', 10002);

INSERT INTO companys (name, contact_name, contact_phone) VALUES
('WakeBroTest', 'ООО ТестКомпани', '+7(921) 111-11-11');

INSERT INTO tickets (company_id, pass, name, equipment, duration, start_date, end_date, start_time, end_time, month, day, year, cost, weekendcost, created_by, created_on) VALUES
(10000, 'SEASON', 'Сезоный абонемент', true, 1, '2020-06-01', '2020-10-01', null, null, 0, 0, 2020, 100, 120, 10002, '2020-06-02 11:00:00'),
(10000, 'SINGLE', 'Разовый абонемент', true, 5, null, null, '10:00', '18:00', 0, 0, 2020, 10, 11, 10002, '2020-06-02 12:00:00'),
(10000, 'SINGLE', 'Утренний сет', true, 5, null, null, '09:00', '13:00', 0, 0, 2020, 100, 110, 10002, '2020-06-02 12:00:00'),
(10000, 'SEASON', 'Абонемент на месяц', false, 5, null, null, '09:00', '18:00', 1, 0, 2020, 90, 95, 10002, '2020-06-02 13:00:00');

INSERT INTO client_ticket (company_id, client_id, ticket_id, date_start, date_end, active, created_on, created_by) VALUES
(10000, 10003, 10007, null, null, true, '2020-06-02 10:00:00', 10002),
(10000, 10003, 10007, null, null, true, '2020-06-02 11:00:00', 10002),
(10000, 10004, 10006, '2020-06-01', '2020-10-01', true, '2020-06-02 12:00:00', 10001),
(10000, 10003, 10007, null, null, false, '2020-06-02 13:00:00', 10002);