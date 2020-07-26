CREATE TABLE Students
(
    id         BIGINT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    nationality  VARCHAR(255) DEFAULT '',
    class_id   BIGINT,
    FOREIGN KEY (class_id) REFERENCES Classes (id)
);

INSERT INTO Students
VALUES (1, 'John', 'Wick', 'United States of America', 1);
INSERT INTO Students
VALUES (2, 'Bill', 'Gates', 'United States of America', 1);
INSERT INTO Students
VALUES (3, 'Jotaro', 'Kujo', 'Japan', 1);
INSERT INTO Students (id, first_name, last_name, class_id)
VALUES (4, 'Giorno', 'Giovanna', 1);