INSERT INTO tb_role(authority) VALUES('ROLE_VISITOR')
INSERT INTO tb_role(authority) VALUES('ROLE_MEMBER')
INSERT INTO tb_role(authority) VALUES('ROLE_ADM')

INSERT INTO tb_user (name, password, email, role_id) VALUES ('Bob', '$2a$10$cpTBuvzYW2ANFy0zPCcQxu/qosnI/AVmPX.6tQqaA6roYtLhwo78S','limap16wesley@gmail.com',2);
INSERT INTO tb_user (name, password, email, role_id) VALUES ('Ana', '$2a$10$xDE4XkmTgPoZsBuGQWYggevIzMhRwR1aT.jwi.sJkUS7gxRGG0pAO','ana@gmail.com', 1); //12345
INSERT INTO tb_user (name, password, email, role_id) VALUES ('wesley', '$2a$10$v3Q.85TaVdV5i5TFbnTnNeZ1NK7Pyt8vHQOxd34JhBxo6L2bPeXYO','wesley@gmail.com', 3); //123456

INSERT INTO tb_genre (name) VALUES ('Comédia');
INSERT INTO tb_genre (name) VALUES ('Ação');
INSERT INTO tb_genre (name) VALUES ('Terror');
INSERT INTO tb_genre (name) VALUES ('Drama');
INSERT INTO tb_genre (name) VALUES ('Animação');
INSERT INTO tb_genre (name) VALUES ('Aventura');
INSERT INTO tb_genre (name) VALUES ('Crime');
INSERT INTO tb_genre (name) VALUES ('Documentário');
INSERT INTO tb_genre (name) VALUES ('Família');
INSERT INTO tb_genre (name) VALUES ('Musical');
INSERT INTO tb_genre (name) VALUES ('Mistério');
INSERT INTO tb_genre (name) VALUES ('Guerra');
INSERT INTO tb_genre (name) VALUES ('Ficção ciêntifica');

INSERT INTO tb_movie (title, sub_title, movie_year, img_Url, synopsis, trailer ) VALUES ('Bob Esponja', 'O Incrível Resgate', 2020,  'https://image.tmdb.org/t/p/w533_and_h300_bestv2/wu1uilmhM4TdluKi2ytfz8gidHf.jpg', 'Onde está Gary? Segundo Bob Esponja, Gary foi "caracolstrado" pelo temível Rei Poseidon e levado para a cidade perdida de Atlantic City. Junto a Patrick Estrela, ele sai em uma missão de resgate ao querido amigo, e nesta jornada os dois vão conhecer novos personagens e viver inimagináveis aventuras.', 'https://www.youtube.com/watch?v=NORzSapZfVc' );
INSERT INTO tb_movie (title, sub_title, movie_year, img_url, synopsis, trailer  ) VALUES ('O Orfanato', null, 2007, 'https://image.tmdb.org/t/p/w533_and_h300_bestv2/2AlVaQDH67RgulE2AqXBSPr2POF.jpg', 'Laura (Belén Rueda) passou os anos mais felizes de sua vida em um orfanato, onde recebeu os cuidados de uma equipe e de outros companheiros órfãos, a quem considerava como se fossem seus irmãos e irmãs verdadeiros. Agora, 30 anos depois, ela retornou ao local com seu marido Carlos (Fernando Cayo) e seu filho Simón (Roger Príncep), de 7 anos. Ela deseja restaurar e reabrir o orfanato, que está abandonado há vários anos. O local logo desperta a imaginação de Simón, que passa a criar contos fantásticos. Entretanto à medida que os contos ficam mais estranhos Laura começa a desconfiar que há algo à espreita na casa.', 'https://www.youtube.com/watch?v=UkuKtS-N1rM');
INSERT INTO tb_movie (title, sub_title, movie_year, img_url, synopsis, trailer  ) VALUES ('O Labirinto do Fauno', null, 2006, 'https://image.tmdb.org/t/p/w500_and_h282_face/oXMfT5OM6HAgQ9sGANB8cs1ifCG.jpg', 'Em 1944, na Espanha, a jovem Ofélia e sua mãe doente chegam ao posto do novo marido de sua mãe, um sádico oficial do exército que está tentando reprimir uma guerrilheira. Enquanto explorava um labirinto antigo, Ofélia encontra o Pan fauno, que diz que a menina é uma lendária princesa perdida e que ela precisa completar três tarefas perigosas a fim de se tornar imortal.', 'https://www.youtube.com/watch?v=jVZRnnVSQ8k');
INSERT INTO tb_movie (title, sub_title, movie_year, img_url, synopsis, trailer  ) VALUES ('Your Name', null, 2016, 'https://image.tmdb.org/t/p/w533_and_h300_bestv2/wqZapHpXyZEaCkpsLVszmEQcDIy.jpg', 'Mitsuha é a filha do prefeito de uma pequena cidade, mas sonha em tentar a sorte em Tóquio. Taki trabalha em um restaurante em Tóquio e deseja largar o seu emprego. Os dois não se conhecem, mas estão conectados pelas imagens de seus sonhos.', 'https://www.youtube.com/watch?v=soQXM3XVvIU');
INSERT INTO tb_movie (title, sub_title, movie_year, img_url, synopsis, trailer  ) VALUES ('Código de Conduta', null , 2009, 'https://image.tmdb.org/t/p/w533_and_h300_bestv2/mwlLjL3jTDmTdLWe2PyUVqYQTuK.jpg', 'Quando um dos suspeitos do assassinato de sua mulher e filha é solto, Clyde quer vingança e decide fazer justiça com as próprias mãos. Clyde é preso e dentro da cadeia organiza uma matança para desmascarar o sistema judicial corrupto.', 'https://www.youtube.com/watch?v=n34wVRu0-rQ' );
INSERT INTO tb_movie (title, sub_title, movie_year, img_url, synopsis, trailer  ) VALUES ('A Voz do Silêncio', 'Koe no Katachi', 2016, 'https://image.tmdb.org/t/p/w533_and_h300_bestv2/5lAMQMWpXMsirvtLLvW7cJgEPkU.jpg', 'Nishimiya Shouko é uma estudante com deficiência auditiva. Durante o ensino fundamental, após se transferir para uma nova escola, Shouko passa a ser alvo de bullying e em pouco tempo precisa se transferir. O que ela não esperava é que alguns anos depois, Ishida Shouya, um dos valentões que tanto a fez sofrer no passado surgisse de novo em sua vida com um novo propósito.', 'https://www.youtube.com/watch?v=nfK6UgLra7g');
INSERT INTO tb_movie (title, sub_title, movie_year, img_url, synopsis, trailer  ) VALUES ('Kingsman', 'Serviço Secreto', 2014, 'https://image.tmdb.org/t/p/w533_and_h300_bestv2/qzUIOTk0E3F1zjvYjcBRTKUTgf9.jpg','Eggsy (Taron Egerton) é um jovem com problemas de disciplina que parece perto de se tornar um criminoso. Determinado dia, ele entra em contato com Harry (Colin Firth), que lhe apresenta à agência de espionagem Kingsman. O jovem se une a um time de recrutas em busca de uma vaga na agência. Ao mesmo tempo, Harry tenta impedir a ascensão do vilão Valentine (Samuel L. Jackson). Adaptação da série de quadrinhos criada por Mark Millar e Dave Gibbons.', 'https://www.youtube.com/watch?v=ydox4Iy8pCY' );
INSERT INTO tb_movie (title, sub_title, movie_year, img_url, synopsis, trailer  ) VALUES ('Sonic', 'O Filme', 2020, 'https://image.tmdb.org/t/p/w533_and_h300_bestv2/diFNHa3SXaGSSFovGatNWxLz2tn.jpg','Sonic, o porco-espinho azul mais famoso do mundo, se junta com os seus amigos para derrotar o terrível Doutor Eggman, um cientista louco que planeja dominar o mundo, e o Doutor Robotnik, responsável por aprisionar animais inocentes em robôs.', 'https://www.youtube.com/watch?v=szby7ZHLnkA');
INSERT INTO tb_movie (title, sub_title, movie_year, img_url, synopsis, trailer  ) VALUES ('Uma Noite de Crime', 'Anarquia', 2014, 'https://image.tmdb.org/t/p/w500_and_h282_face/ecD9hT8odHzFCDeGDy4N2IKh0LN.jpg', 'O governo dos Estados Unidos sanciona uma lei em que os assassinatos são permitidos durante uma noite, para que os cidadãos liberem seus instintos violentos. Cinco desconhecidos se unem para tentar sobreviver a essa verdadeira noite de terror.', 'https://www.youtube.com/watch?v=I_0nuvAnxTM' );
INSERT INTO tb_movie (title, sub_title, movie_year, img_url, synopsis, trailer  ) VALUES ('O Segredo da Cabana', null, 2012, 'https://image.tmdb.org/t/p/w533_and_h300_bestv2/5iiVfPS6LsAqmVQVOzhyCHhCFgU.jpg', 'Cinco amigos fazem uma pausa em uma cabana remota, onde conseguem mais do que esperavam, descobrindo a verdade atrás da cabana na floresta.', 'https://www.youtube.com/watch?v=uDvD4uWJ-B8 ' );

-- Bob Esponja (id: 1)
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (1, 1); -- Comédia
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (1, 4); -- Animação
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (1, 5); -- Aventura
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (1, 8); -- Família

-- O Orfanato (id: 2)
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (2, 2); -- Terror
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (2, 3); -- Drama
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (2, 10); -- Mistério

-- O Labirinto do Fauno (id: 3)
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (3, 3); -- Drama
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (3, 5); -- Aventura
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (3, 11); -- Guerra
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (3, 12); -- Ficção científica

-- Your Name (id: 4)
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (4, 3); -- Drama
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (4, 4); -- Animação
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (4, 8); -- Família
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (4, 12); -- Ficção científica

-- Código de Conduta (id: 5)
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (5, 6); -- Crime
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (5, 3); -- Drama
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (5, 10); -- Mistério

-- A Voz do Silêncio (id: 6)
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (6, 3); -- Drama
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (6, 4); -- Animação
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (6, 8); -- Família

-- Kingsman (id: 7)
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (7, 1); -- Comédia
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (7, 5); -- Aventura
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (7, 6); -- Crime

-- Sonic (id: 8)
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (8, 4); -- Animação
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (8, 5); -- Aventura
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (8, 8); -- Família

-- Uma Noite de Crime (id: 9)
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (9, 2); -- Terror
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (9, 6); -- Crime
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (9, 10); -- Mistério

-- O Segredo da Cabana (id: 10)
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (10, 2); -- Terror
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (10, 5); -- Aventura
INSERT INTO tb_movie_genre (movie_id, genre_id) VALUES (10, 10); -- Mistério


INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Meh, filme OK', 1, 1, 5.5);
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Gostei e recomendo!', 1, 1, 5.5);
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Que Filme!!!', 2, 1, 5.5);


-- Filme 1 - Bob Esponja
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Uma experiência engraçada e divertida', 1, 2, 6.0);

-- Filme 2 - O Orfanato
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Efeito arrepiante, excelente direção', 2, 2, 7.5);

-- Filme 3 - O Labirinto do Fauno
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Um conto sombrio com beleza visual', 3, 2, 8.0);
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Fascinante e perturbador', 3, 3, 7.8);

-- Filme 4 - Your Name
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Emocionante e encantador. Um deleite para os olhos.', 4, 2, 8.5);
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('A animação e a história se entrelaçam de maneira perfeita.', 4, 3, 8.0);

-- Filme 5 - Código de Conduta
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Uma trama intensa e cheia de reviravoltas', 5, 2, 7.0);
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('A ação combinada com drama cria uma experiência única', 5, 3, 7.5);

-- Filme 6 - A Voz do Silêncio
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Uma narrativa sensível e comovente', 6, 2, 8.2);
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Consegue despertar empatia e reflexão', 6, 3, 8.0);

-- Filme 7 - Kingsman
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('O equilíbrio perfeito entre ação e humor', 7, 2, 8.0);
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Dinâmico e surpreendente', 7, 3, 8.3);

-- Filme 8 - Sonic
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Divertido para todas as idades', 8, 2, 7.0);
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Ação e humor se combinam bem', 8, 3, 7.2);

-- Filme 9 - Uma Noite de Crime
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Caótico e intenso, uma experiência única', 9, 2, 6.5);
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Tensão e adrenalina do começo ao fim', 9, 3, 6.8);

-- Filme 10 - O Segredo da Cabana
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Mistério envolvente e ambiente assustador', 10, 2, 7.0);
INSERT INTO tb_review (text, movie_id, user_id, rating) VALUES ('Uma reviravolta surpreendente que prende o espectador', 10, 3, 7.3);


