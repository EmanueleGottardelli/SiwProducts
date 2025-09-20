-- Inserimento utenti
INSERT INTO users (id, name, surname, email) VALUES (nextval('users_seq'),'Emanuele', 'Gottardelli', 'ema.gotta@ex.it')
INSERT INTO users (id,name, surname, email) VALUES (nextval('users_seq'),'Franca', 'Verdi', 'franca.v1998@ex.com');
-- PasswordSicura123!

-- Inserimento credenziali
INSERT INTO credentials (id, username, password, role, user_id) VALUES (nextval('credentials_seq'), 'SuperAdmin', '$2b$12$sMe4cEfpceel5ijYC5gWh.m.tMs0rT2pOCtvj99B11GzNg7Fara/C', 'ADMIN', 1);
INSERT INTO credentials (id, username, password, role, user_id) VALUES (nextval('credentials_seq'), 'Franca', '$2b$12$sMe4cEfpceel5ijYC5gWh.m.tMs0rT2pOCtvj99B11GzNg7Fara/C', 'DEFAULT', 51);

-- Inserimento Prodotti
INSERT INTO product (id, name, description, type, price) VALUES(nextval('product_seq'), 'One Plus Nord 3', '512 GB, 12GB di Ram, 50 Mpx telecamera posteriore, 5G', 'Elettronica', '449.99');
INSERT INTO product (id, name, description, type, price) VALUES(nextval('product_seq'), 'Star Wars: Aftermath', 'Libro che narra una storia ambientata dopo episodio VI', 'Libreria', '9.99');
INSERT INTO product (id, name, description, type, price) VALUES(nextval('product_seq'), 'Alexa', 'Piccola cassa con assistente vocale Alexa', 'Elettronica', '59.99');
INSERT INTO product (id, name, description, type, price) VALUES(nextval('product_seq'), 'Tazza I love', 'Tazza da latte personalizzabile con citta visitate', 'Cucina', '6.49');