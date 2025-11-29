CREATE TABLE jogos (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    genero VARCHAR(50),
    estudio VARCHAR(100)
);

CREATE TABLE inimigos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    especie VARCHAR(100),
    dificuldade VARCHAR(50),
    ataque_especial VARCHAR(150),
    jogo_id BIGINT NOT NULL,

    CONSTRAINT fk_inimigo_jogo
        FOREIGN KEY (jogo_id)
        REFERENCES jogos (id)
        ON DELETE RESTRICT
);
