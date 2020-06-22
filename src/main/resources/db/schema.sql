DROP TABLE IF EXISTS register;
DROP TABLE IF EXISTS client_ticket_story;
DROP TABLE IF EXISTS client_ticket;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS companys;
DROP SEQUENCE IF EXISTS GLOBAL_SEQ;
DROP SEQUENCE IF EXISTS REGISTER_SEQ;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 10000;
CREATE SEQUENCE REGISTER_SEQ AS INTEGER START WITH 1000;

CREATE TABLE companys
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
    name          VARCHAR(120)    NOT NULL,
    contact_name  VARCHAR(120),
    contact_phone VARCHAR(20)
);

CREATE TABLE users
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
    company_id  INTEGER                 NOT NULL ,
    firstname   VARCHAR(30)             NOT NULL,
    lastname    VARCHAR(30)             NOT NULL,
    middlename  VARCHAR(30)             NOT NULL,
    telnumber   VARCHAR(20)             NOT NULL,
    login       VARCHAR(20)             NOT NULL,
    password    VARCHAR(20)             NOT NULL,
    email       VARCHAR(30),
    enabled     BOOLEAN   DEFAULT TRUE  NOT NULL,
    created_on  TIMESTAMP DEFAULT now() NOT NULL,
    created_by  INTEGER   NULL,
    changed_on  TIMESTAMP NULL,
    changed_by  INTEGER   NULL,

    FOREIGN KEY (company_id) REFERENCES companys(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (changed_by) REFERENCES users(id),

    CONSTRAINT  user_idx UNIQUE (company_id, login)
);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE clients
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
    company_id  INTEGER                 NOT NULL ,
    firstname   VARCHAR(30)             NOT NULL,
    lastname    VARCHAR(30)             NOT NULL,
    middlename  VARCHAR(30)             NOT NULL,
    telnumber   VARCHAR(20)             NOT NULL,
    email       VARCHAR                 NULL,
    city        VARCHAR(30),
    created_on  TIMESTAMP DEFAULT now() NOT NULL,
    created_by  INTEGER                 NULL,
    changed_on  TIMESTAMP               NULL,
    changed_by  INTEGER                 NULL,

    FOREIGN KEY (company_id) REFERENCES companys(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id),

    CONSTRAINT client_idx UNIQUE (company_id, firstname, lastname, middlename, telnumber)
);

CREATE TABLE tickets
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
    company_id  INTEGER                   NOT NULL ,
    pass        VARCHAR(30)               NOT NULL,
    name        VARCHAR(30)               NOT NULL,
    enable      BOOLEAN DEFAULT TRUE      NOT NULL,
    equipment   BOOLEAN                   NOT NULL,
    duration    INTEGER                   NOT NULL,
    start_date  DATE,
    end_date    DATE,
    start_time  TIME,
    end_time    TIME,
    month       INTEGER,
    day         INTEGER,
    year        INTEGER                   NOT NULL,
    cost        numeric                   NOT NULL,
    weekendcost numeric                   NOT NULL,
    created_on  TIMESTAMP DEFAULT now() NOT NULL,
    created_by  INTEGER   NOT NULL,
    changed_on  TIMESTAMP NULL,
    changed_by  INTEGER   NULL,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (changed_by) REFERENCES users(id),
    FOREIGN KEY (company_id) REFERENCES companys(id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX ticket_unique_index ON tickets(pass, name, year);

CREATE TABLE client_ticket
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
    company_id  INTEGER                         NOT NULL ,
    client_id   INTEGER                         NOT NULL,
    ticket_id   INTEGER                         NOT NULL,
    date_start  DATE,
    date_end    DATE,
    active      BOOLEAN   DEFAULT TRUE          NOT NULL,
    created_on  TIMESTAMP DEFAULT now() NOT NULL,
    created_by  INTEGER   NOT NULL,
    changed_on  TIMESTAMP NULL,
    changed_by  INTEGER   NULL,

    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (changed_by) REFERENCES users(id),
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE,
    FOREIGN KEY (company_id) REFERENCES companys(id) ON DELETE CASCADE
);

CREATE TABLE client_ticket_story
(
    id                INTEGER PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
    company_id        INTEGER                         NOT NULL ,
    client_ticket_id  INTEGER                   NOT NULL,
    date              DATE DEFAULT CURRENT_DATE NOT NULL,
    time_start        TIME,
    time_end          TIME,
    created_on        TIMESTAMP DEFAULT now() NOT NULL,
    created_by        INTEGER   NOT NULL,
    FOREIGN KEY (client_ticket_id) REFERENCES client_ticket(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (company_id) REFERENCES companys(id) ON DELETE CASCADE
);

/*CREATE UNIQUE INDEX client_ticket_story_index ON client_ticket_story(client_ticket_id, date, time_start); */

CREATE TABLE register
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('REGISTER_SEQ'),
    company_id  INTEGER                         NOT NULL ,
    user_id     INTEGER                         NOT NULL,
    open_shift  TIMESTAMP DEFAULT now()         NOT NULL,
    close_shift TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
)
