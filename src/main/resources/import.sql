INSERT INTO TB_MUSICO (nome) VALUES ('Luis Henrique');
INSERT INTO TB_MUSICO (nome) VALUES ('Isabella Silva');
INSERT INTO TB_MUSICO (nome) VALUES ('Cassiano');
INSERT INTO TB_MUSICO (nome) VALUES ('João');
INSERT INTO TB_MUSICO (nome) VALUES ('Ana Elisa');
INSERT INTO TB_MUSICO (nome) VALUES ('Gabriel');
INSERT INTO TB_MUSICO (nome) VALUES ('Caio');
INSERT INTO TB_MUSICO (nome) VALUES ('Fábio');
INSERT INTO TB_MUSICO (nome) VALUES ('Cícero');
INSERT INTO TB_MUSICO (nome) VALUES ('Peterson');
INSERT INTO TB_INSTRUMENTO (nome) VALUES ('Violão');
INSERT INTO TB_INSTRUMENTO (nome) VALUES ('Guitarra');
INSERT INTO TB_INSTRUMENTO (nome) VALUES ('Baixo');
INSERT INTO TB_INSTRUMENTO (nome) VALUES ('Teclado');
INSERT INTO TB_INSTRUMENTO (nome) VALUES ('Bateria');
INSERT INTO TB_INSTRUMENTO (nome) VALUES ('Vocal');
INSERT INTO TB_MUSICA (nome, autor) VALUES ('Ousado Amor','Isaías Saad');
INSERT INTO TB_MUSICA (nome, autor) VALUES ('Eu Navegarei','Adoração e Adoradores');
INSERT INTO TB_MUSICA (nome, autor) VALUES ('Santo Espírito','Laura Souguellis');
INSERT INTO TB_MUSICA (nome, autor) VALUES ('Pai Nosso','Ministério Zoe');
INSERT INTO TB_MUSICA (nome, autor) VALUES ('Em Teus Braços','Laura Souguellis');
INSERT INTO TB_MUSICA (nome, autor) VALUES ('A Casa É Sua','Casa Worship');
INSERT INTO TB_MUSICA (nome, autor) VALUES ('Pela Cruz','Ministério Zoe');
INSERT INTO TB_MUSICA (nome, autor) VALUES ('Que Amor É Esse','Adoração e Adoradores');
INSERT INTO TB_MUSICA (nome, autor) VALUES ('Meu Abrigo','Ministério Zoe');
INSERT INTO TB_MUSICA (nome, autor) VALUES ('O Nome de Jesus','Gabriela Rocha');
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
INSERT INTO TB_MUSICOINSTRUMENTO (id_musico_fk, id_instrumento_fk) VALUES (7, 5);
INSERT INTO TB_MUSICOINSTRUMENTO (id_musico_fk, id_instrumento_fk) VALUES (8, 3);
INSERT INTO TB_MUSICOINSTRUMENTO (id_musico_fk, id_instrumento_fk) VALUES (9, 1);
INSERT INTO TB_MUSICOINSTRUMENTO (id_musico_fk, id_instrumento_fk) VALUES (10, 1);
INSERT INTO TB_MUSICOINSTRUMENTO (id_musico_fk, id_instrumento_fk) VALUES (10, 6);

INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-11-01', 4);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-11-05', 2);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-11-06', 1);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-11-08', 3);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-11-11', 1);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-11-12', 3);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-11-15', 2);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-11-18', 3);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-11-19', 4);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-11-22', 1);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-11-25', 4);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-11-26', 1);
INSERT INTO TB_NOITEDEAPRESENTACAO (data,id_banda_fk) VALUES ('2023-11-29', 2);

INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (1, 1);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (2, 1);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (3, 1);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (4, 1);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (5, 1);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (6, 1);


INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (7, 2);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (1, 2);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (1, 2);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (8, 2);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (6, 2);

INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (9, 3);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (7, 3);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (1, 3);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (1, 3);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (8, 3);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (6, 3);

INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (10, 4);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (11, 4);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (7, 4);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (1, 4);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (1, 4);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (8, 4);
INSERT INTO TB_MUSICOINSTRUMENTOBANDA (musico_instrumento_id,banda_id) VALUES (6, 4);



INSERT INTO TB_musicasdanoitedeapresentacao (noite_apresentacao_id,musica_id) VALUES (1, 2);
INSERT INTO TB_musicasdanoitedeapresentacao (noite_apresentacao_id,musica_id) VALUES (1, 1);
