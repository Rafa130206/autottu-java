insert into AUT_T_USUARIO (id_usuario, nome, email, telefone, username, senha, perfil) values
    (10000, 'Administrador', 'admin@example.com', '11999990000', 'admin', '$2b$10$W68p3EtP0ND9s9zj9zyMfuSiQXax1aEM3Ouh7L2FgnbKr3mwQ1PUO', 'ADMIN');

insert into AUT_T_USUARIO (id_usuario, nome, email, telefone, username, senha, perfil) values
    (20000, 'Usuário Padrão', 'user@example.com', '11888880000', 'usuario', '$2b$10$3Csbwts1ikcIyHA4gvRo.umcwmjN8aki5M8RCQVAHgTWqsNQGIygS', 'USUARIO');

ALTER TABLE AUT_T_USUARIO ALTER COLUMN id_usuario RESTART WITH 20001;

insert into AUT_T_MOTO (id_moto, placa, modelo, marca, status, url_foto) values
    (1, 'ABC1D23', 'CB 500X', 'Honda', 'DISPONIVEL', 'https://via.placeholder.com/200x120?text=CB500X'),
    (2, 'XYZ4E56', 'MT-07', 'Yamaha', 'DISPONIVEL', 'https://via.placeholder.com/200x120?text=MT-07'),
    (3, 'JKL7M89', 'Ninja 400', 'Kawasaki', 'MANUTENCAO', 'https://via.placeholder.com/200x120?text=Ninja400');

ALTER TABLE AUT_T_MOTO ALTER COLUMN id_moto RESTART WITH 4;

insert into AUT_T_SLOT (id_slot, ocupado, AUT_T_MOTO_id_moto) values
    (1, 'N', null),
    (2, 'S', 3),
    (3, 'N', null);

ALTER TABLE AUT_T_SLOT ALTER COLUMN id_slot RESTART WITH 4;

ALTER TABLE AUT_T_CHECKIN ALTER COLUMN id_checkin RESTART WITH 1;
ALTER TABLE AUT_T_MANUTENCAO ALTER COLUMN id_manutencao RESTART WITH 1;
ALTER TABLE AUT_T_TEST_RIDE ALTER COLUMN id_test_ride RESTART WITH 1;
