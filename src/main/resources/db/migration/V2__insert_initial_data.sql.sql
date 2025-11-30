/* :::::::::: INSERINDO DADOS PARA TESTE :::::::::: */

/* DADOS PARA POPULAR O BD DO PROJETO BESTIARIUS */
/* Relacionamento: um JOGO possui vários tipos de INIMIGOS. */

-- DO $$
-- BEGIN
--     /* Verifica se a contagem de linhas é zero */
--     IF (SELECT COUNT(*) FROM inimigos) = 0 THEN
        /* Se estiver vazia, insere todos os seus dados de uma vez */
        				
		/* Inserção da tabela Jogos */
		INSERT INTO jogos (id, titulo, genero, estudio) VALUES
		(1, 'Eternal Blades', 'RPG de Ação', 'Lunar Forge Studios'),
		(2, 'Mecha Horizon', 'Tiro Sci-Fi', 'NovaCore Interactive'),
		(3, 'Legends of Aztlan', 'Aventura / Mitologia', 'Sun Serpent Games'),
		(4, 'Frozen Wasteland', 'Sobrevivência / Terror', 'WhitePeak Entertainment'),
		(5, 'Clockwork Rebellion', 'Steampunk Estratégia', 'Iron Gear Workshop');
		
		/* Inserção da tabela Inimigos */

		/* Jogo 1 — Eternal Blades (RPG de Ação) */
		INSERT INTO inimigos (id, nome, especie, dificuldade, ataque_especial, jogo_id) VALUES
		(1, 'Goblin Saqueador', 'Goblin', 'Fácil', 'Lançar Pedra', 1),
		(2, 'Lobo Sombrio', 'Besta', 'Médio', 'Mordida Ágil', 1),
		(3, 'Guardião de Cristal', 'Construto', 'Médio', 'Raio Prismático', 1),
		(4, 'Espírito Errante', 'Espírito', 'Fácil', 'Lamento Paralizante', 1),
		(5, 'Dragão Carmesim', 'Dragão Ancestral', 'Difícil', 'Chama Ancestral', 1); -- BOSS
		
		/* Jogo 2 — Mecha Horizon (Sci-Fi/Tiro) */
		INSERT INTO inimigos (id, nome, especie, dificuldade, ataque_especial, jogo_id) VALUES
		(6, 'Drone Sentinela', 'Drone', 'Fácil', 'Disparo Rápido', 2),
		(7, 'Mecha Destruidor', 'Robô', 'Médio', 'Golpe Hidráulico', 2),
		(8, 'Torre Autônoma', 'Construção', 'Médio', 'Canhão de Plasma', 2),
		(9, 'Android Fugitivo', 'Androide', 'Médio', 'Lâmina Energética', 2),
		(10, 'Titã Omega MK-IV', 'Titã', 'Difícil', 'Explosão de Íons', 2); -- BOSS
		
		/* Jogo 3 — Legends of Aztlan (Aventura mitológica) */
		INSERT INTO inimigos (id, nome, especie, dificuldade, ataque_especial, jogo_id) VALUES
		(11, 'Serpente de Jade', 'Serpente', 'Fácil', 'Veneno de Jade', 3),
		(12, 'Jaguar Espectral', 'Jaguar', 'Médio', 'Salto Espiritual', 3),
		(13, 'Guerreiro Arcaico', 'Guerreiro', 'Médio', 'Lança Cerimonial', 3),
		(14, 'Águia Solar', 'Ave', 'Médio', 'Rajada Solar', 3),
		(15, 'Quetzal Primordial', 'Ave Primordial', 'Difícil', 'Tempestade Celestial', 3); -- BOSS
		
		/* Jogo 4 — Frozen Wasteland (Survival/Terror) */
		INSERT INTO inimigos (id, nome, especie, dificuldade, ataque_especial, jogo_id) VALUES
		(16, 'Rastejante Gélido', 'Zumbi', 'Fácil', 'Garras Congeladas', 4),
		(17, 'Carniçal da Neve', 'Carniçal', 'Médio', 'Grito Congelante', 4),
		(18, 'Urso Albino Mutante', 'Mutante', 'Médio', 'Investida Brutal', 4),
		(19, 'Andarilho Gélido', 'Zumbi', 'Fácil', 'Toque Entumecido', 4),
		(20, 'Titã do Permafrost', 'Titã', 'Difícil', 'Tremor Glacial', 4); -- BOSS
		
		/* Jogo 5 — Clockwork Rebellion (Steampunk Estratégia) */
		INSERT INTO inimigos (id, nome, especie, dificuldade, ataque_especial, jogo_id) VALUES
		(21, 'Autômato Enferrujado', 'Autômato', 'Fácil', 'Golpe Desajustado', 5),
		(22, 'Soldado a Vapor', 'Robô', 'Médio', 'Descarga de Vapor', 5),
		(23, 'Gárgula Mecânica', 'Gárgula', 'Médio', 'Mordida de Bronze', 5),
		(24, 'Artilheiro Pneumático', 'Robô', 'Médio', 'Rajada Pneumática', 5),
		(25, 'Imperador Relógio', 'Robô', 'Difícil', 'Maré Temporal', 5); -- BOSS

--         /* Mostra uma mensagem de que a inserção ocorreu */
--         RAISE NOTICE 'Tabela estava vazia, dados iniciais inseridos.';
--     ELSE
--         /* Opcional: Mostra uma mensagem de que nada foi feito */
--         RAISE NOTICE 'Tabela já contém dados, nenhuma ação necessária.';
--     END IF;
-- END $$;

/* Os dados foram gerados com auxilio de IA's e totalmente ficcionais*/