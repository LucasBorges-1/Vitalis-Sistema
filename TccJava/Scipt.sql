
CREATE TABLE clinica (
                id_clinica integer AUTO_INCREMENT NOT NULL,
                cnpj VARCHAR(45) NOT NULL,
                nome VARCHAR(45) NOT NULL,
                numero VARCHAR(45) NOT NULL,
                Endereco  VARCHAR(45) NOT NULL,
                senha VARCHAR(255) NOT NULL,
                PRIMARY KEY (id_clinica)
);




CREATE TABLE pessoa (
                id_pessoa integer AUTO_INCREMENT NOT NULL,
                email VARCHAR(45)  NOT NULL,
                data_nascimento DATE NOT NULL,
                nome VARCHAR(45)  NOT NULL,
                senha VARCHAR(255) NOT NULL,
                cpf VARCHAR(45) NOT NULL,
                PRIMARY KEY (id_pessoa)
);


CREATE TABLE medico (
                id_medico integer NOT NULL,
                crm VARCHAR(45) NOT NULL,
                PRIMARY KEY (id_medico)
);


CREATE TABLE clinica_medico (
                id_clinica integer NOT NULL,
                id_medico integer NOT NULL,
                PRIMARY KEY (id_clinica, id_medico)
);


CREATE TABLE usuario (
                id_usuario integer NOT NULL,
                data_cadastro DATE NOT NULL,
                endereco VARCHAR(45) NOT NULL,
                PRIMARY KEY (id_usuario)
);


CREATE TABLE consulta (
                id_consulta integer AUTO_INCREMENT NOT NULL,
                id_usuario integer NOT NULL,
                data_consulta  DATETIME NOT NULL,
                caminho_documentos VARCHAR(45) NOT NULL,
                id_medico integer NOT NULL,
                PRIMARY KEY (id_consulta)
);

CREATE TABLE Horarios (
    id_horario INT AUTO_INCREMENT PRIMARY KEY,
   id_medico INT NOT NULL,
	dia DATETIME NOT NULL,
    inicio TIME NOT NULL,
    fim TIME NOT NULL,
    FOREIGN KEY (id_medico) REFERENCES medico(id_medico)
);


CREATE TABLE tokens_recuperacao (
                id_token INT AUTO_INCREMENT NOT NULL,
                id_pessoa integer NOT NULL,
                token VARCHAR(45) NOT NULL,
                usado BOOLEAN DEFAULT FALSE NOT NULL,
                expiracao DATETIME NOT NULL,
                PRIMARY KEY (id_token)
);


ALTER TABLE clinica_medico ADD CONSTRAINT clinica_clinica_medico_fk
FOREIGN KEY (id_clinica)
REFERENCES clinica (id_clinica)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE usuario ADD CONSTRAINT pessoa_usuario_fk
FOREIGN KEY (id_usuario)
REFERENCES pessoa (id_pessoa)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE medico ADD CONSTRAINT pessoa_medico_fk
FOREIGN KEY (id_medico)
REFERENCES pessoa (id_pessoa)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE tokens_recuperacao ADD CONSTRAINT pessoa_tokens_recuperacao_fk
FOREIGN KEY (id_pessoa)
REFERENCES pessoa (id_pessoa)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE clinica_medico ADD CONSTRAINT medico_clinica_medico_fk
FOREIGN KEY (id_medico)
REFERENCES medico (id_medico)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE consulta ADD CONSTRAINT medico_consulta_fk
FOREIGN KEY (id_medico)
REFERENCES medico (id_medico)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE consulta ADD CONSTRAINT usuario_consulta_fk
FOREIGN KEY (id_usuario)
REFERENCES usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION;


