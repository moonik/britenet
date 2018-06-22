INSERT INTO person(id, name, surname, gender, birth_date, pesel) VALUES (1, 'Agent', 'Smith', 'M', '1989-12-12', '12345678901')

INSERT INTO person(id, name, surname, gender, birth_date, pesel) VALUES (2, 'Taylor', 'Swift', 'F', '2000-12-12', '12345678902')

INSERT INTO person(id, name, surname, gender, birth_date, pesel) VALUES (3, 'Lana del', 'Rey', 'F', '1999-12-12', '12345678903')

INSERT INTO Contact(type, id, value, person_id) VALUES ('phone', 1, '123456789', 1)
INSERT INTO Contact(type, id, value, person_id) VALUES ('email', 2, 'smith777@gmail.com', 1)

INSERT INTO Contact(type, id, value, person_id) VALUES ('email', 3, 'swift@yahoo.com', 2)

INSERT INTO Contact(type, id, value, person_id) VALUES ('email', 4, 'lana.banana@gmail.com', 3)