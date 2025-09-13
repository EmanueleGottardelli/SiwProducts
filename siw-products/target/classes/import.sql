insert into products (id,name,description,tipe,price) values (nextval('hibernate_sequence'),'Cuffiette','Cuffiette bluetooth','Elettronica','99.99');
insert into products (id,name,description,tipe,price) values (nextval('hibernate_sequence'),'OnePlus Nord 3','Telefono di ultima generazione in 3 colorazioni, 256 GB','Telefonia','449.00');
insert into products (id,name,description,tipe,price) values (nextval('hibernate_sequence'),'Spazzola','Spazzola per capelli ricci','Cura del corpo','15.49');
insert into products (id,name,description,tipe,price) values (nextval('hibernate_sequence'),'Mensola','Mensola di legno di acacia','Casa','30.00');
insert into products (id,name,description,tipe,price) values (nextval('hibernate_sequence'),'Deodorante','Deodorante uomo protezione 48h','Cura del corpo','3.50');
insert into products (id,name,description,tipe,price) values (nextval('hibernate_sequence'),'London','Profumo Burberry uomo','Cura del corpo 100ml','45.00');

insert into product_similar(product_id,similar_id) values('5','6');
insert into product_similar(product_id,similar_id) values('6','5');
insert into product_similar(product_id,similar_id) values('1','2');
insert into product_similar(product_id,similar_id) values('2','1');

insert into users (id,name,surname,email) values(1,'Emanuele','Gottardelli','ema.gotta@ex.it');
insert into users (id,name,surname,email) values(2,'Franca','Verdi','franca.v1998@ex.com');

insert into credentials(id, username, password, role, user_id) values(1,'SuperAdmin','$2b$12$sMe4cEfpceel5ijYC5gWh.m.tMs0rT2pOCtvj99B11GzNg7Fara/C','ADMIN',1);
-- PasswordSicura123!
insert into credentials(id, username, password, role, user_id) values(2,'Franca','$2b$12$sMe4cEfpceel5ijYC5gWh.m.tMs0rT2pOCtvj99B11GzNg7Fara/C','DEFAULT',2);


insert into comment (id,product_id,author_id,text) values (nextval('hibernate_sequence'),'1','1','Ottime cuffiette! Consigliate');
insert into comment (id,product_id,author_id,text) values (nextval('hibernate_sequence'),'2','1','Buon telefono, soprattutto per rapporto qualità/prezzo');
insert into comment (id,product_id,author_id,text) values (nextval('hibernate_sequence'),'6','2','Buonissimo profumo, regalato al mio compagno!');


