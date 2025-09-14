-- Inserimento utenti
INSERT INTO users (id, name, surname, email) VALUES (1, 'Emanuele', 'Gottardelli', 'ema.gotta@ex.it');
INSERT INTO users (id, name, surname, email) VALUES (2, 'Franca', 'Verdi', 'franca.v1998@ex.com');
-- PasswordSicura123!

-- Inserimento credenziali
INSERT INTO credentials (id, username, password, role, user_id) VALUES (1, 'SuperAdmin', '$2b$12$sMe4cEfpceel5ijYC5gWh.m.tMs0rT2pOCtvj99B11GzNg7Fara/C', 'ADMIN', 1);
INSERT INTO credentials (id, username, password, role, user_id) VALUES (2, 'Franca', '$2b$12$sMe4cEfpceel5ijYC5gWh.m.tMs0rT2pOCtvj99B11GzNg7Fara/C', 'DEFAULT', 2);
