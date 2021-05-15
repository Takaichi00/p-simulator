CREATE TABLE p_result (
    id INT(11) AUTO_INCREMENT NOT NULL PRIMARY KEY,
    p_name VARCHAR(30) NOT NULL,
    first_hit INT (11)
);

INSERT INTO p_result(id, p_name, first_hit) VALUES (1, 'symphogear', 50);
