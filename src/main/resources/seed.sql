INSERT INTO USER (id, username, password) VALUES
(1, 'admin', '$2a$12$PD9BYaK1yxzPwDww60UG.OIWbc1mp/uV9tNPpdgrRt5vnxz1OhpCq'),
(2, 'user1', '$2a$12$gbcxyvH22dpsjVr0L4fQIehboIS3xpRK.vD1kumSgav2F/eZPSDgq'),
(3, 'user2', '$2a$12$cLqqO5HPZvYgb2JJJ/qp2O4Ak.x9GKh.BJLyiFfg6WsVB5.yBpHRi');

INSERT INTO ROLE (id, role_name, description) VALUES
(1, 'ROLE_ADMIN', 'Administrator'),
(2, 'ROLE_USER', 'User');

INSERT INTO USER_ROLE (user_id, role_id) VALUES
(1,1),
(2,2),
(3,2);