INSERT INTO TB_MUSICO (nome) VALUES ('Luis Henrique');
INSERT INTO TB_MUSICO (nome) VALUES ('Isabella Silva');
INSERT INTO TB_MUSICO (nome) VALUES ('Cassiano');
INSERT INTO TB_MUSICO (nome) VALUES ('João');
INSERT INTO TB_MUSICO (nome) VALUES ('Ana Elisa');
INSERT INTO TB_MUSICO (nome) VALUES ('Gabriel');
INSERT INTO TB_INSTRUMENTO (nome) VALUES ('Violão');
INSERT INTO TB_INSTRUMENTO (nome) VALUES ('Guitarra');
INSERT INTO TB_INSTRUMENTO (nome) VALUES ('Baixo');
INSERT INTO TB_INSTRUMENTO (nome) VALUES ('Teclado');
INSERT INTO TB_INSTRUMENTO (nome) VALUES ('Bateria');
INSERT INTO TB_INSTRUMENTO (nome) VALUES ('Vocal');
INSERT INTO TB_MUSICA (nome, autor) VALUES ('Me Atraiu','Gabriela Rocha');
INSERT INTO TB_MUSICA (nome, autor) VALUES ('Bondade De Deus','Isaías Saad');
INSERT INTO TB_MUSICA (nome, autor) VALUES ('Só Tu És Santo','Morada');
INSERT INTO TB_BANDA (nome) VALUES ('Cassiano');
INSERT INTO TB_BANDA (nome) VALUES ('Caio');
INSERT INTO TB_BANDA (nome) VALUES ('Cícero');
INSERT INTO TB_BANDA (nome) VALUES ('Peterson');

INSERT INTO TB_MUSICOINSTRUMENTO(id_musico_fk, id_instrumento_fk) VALUES (1, 2);
INSERT INTO TB_MUSICOINSTRUMENTO (id_musico_fk, id_instrumento_fk) VALUES (2, 6);
INSERT INTO TB_MUSICOINSTRUMENTO (id_musico_fk, id_instrumento_fk) VALUES (3, 3);
INSERT INTO TB_MUSICOINSTRUMENTO (id_musico_fk, id_instrumento_fk) VALUES (4, 1);
INSERT INTO TB_MUSICOINSTRUMENTO (id_musico_fk, id_instrumento_fk) VALUES (5, 5);
INSERT INTO TB_MUSICOINSTRUMENTO (id_musico_fk, id_instrumento_fk) VALUES (6, 4);

INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-08-23', 1);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-08-26', 2);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-08-27', 3);

INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (1, 1);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (2, 1);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (3, 1);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (4, 1);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (5, 1);

INSERT INTO TB_musicasdanoitedeapresentacao (noite_apresentacao_id,musica_id) VALUES (1, 2);
INSERT INTO TB_musicasdanoitedeapresentacao (noite_apresentacao_id,musica_id) VALUES (1, 1);
