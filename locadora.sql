CREATE TABLE cliente (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE,
    cnh VARCHAR(20) NOT NULL,
    avaliacao DOUBLE PRECISION
);

CREATE TABLE filial (
    cnpj VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(200),
    telefone VARCHAR(20)
);

CREATE TABLE veiculo (
    placa VARCHAR(10) PRIMARY KEY,
    tipo VARCHAR(30) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    ano INTEGER NOT NULL,
    preco_diaria DOUBLE PRECISION NOT NULL,
    kmanterior DOUBLE PRECISION,
    alugado BOOLEAN DEFAULT FALSE,
    historico TEXT,
    cnpj_filial VARCHAR(14),
    FOREIGN KEY (cnpj_filial) REFERENCES filial(cnpj)
    ON DELETE SET NULL
);

CREATE TABLE funcionario (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(200),
    salario DOUBLE PRECISION,
    cargo VARCHAR(50),
    cnpj_filial VARCHAR(14),
    FOREIGN KEY (cnpj_filial) REFERENCES filial(cnpj)
    ON DELETE SET NULL
);

CREATE TABLE locacao (
    id_locacao SERIAL PRIMARY KEY,
    cpf_cliente VARCHAR(11),
    placa_veiculo VARCHAR(10),
    cnpj_filial_retirada VARCHAR(14),
    cnpj_filial_retorno VARCHAR(14),
    data_inicial DATE NOT NULL,
    data_final DATE,
    combust_inicial DOUBLE PRECISION,
    combust_final DOUBLE PRECISION,
    km_percorrido DOUBLE PRECISION,
    valor DOUBLE PRECISION,
    FOREIGN KEY (cpf_cliente) REFERENCES cliente(cpf)
    ON DELETE CASCADE,
    FOREIGN KEY (placa_veiculo) REFERENCES veiculo(placa)
    ON DELETE NO ACTION,
    FOREIGN KEY (cnpj_filial_retirada) REFERENCES filial(cnpj)
    ON DELETE SET NULL,
    FOREIGN KEY (cnpj_filial_retorno) REFERENCES filial(cnpj)
    ON DELETE SET NULL
);

CREATE TABLE chamada (
    id_locacao INTEGER PRIMARY KEY,
    cpf_funcionario VARCHAR(11),
    FOREIGN KEY (id_locacao) REFERENCES locacao(id_locacao)
    ON DELETE CASCADE,
    FOREIGN KEY (cpf_funcionario) REFERENCES funcionario(cpf)
    ON DELETE SET NULL
);

CREATE TABLE aprovacao (
    id_locacao INTEGER PRIMARY KEY,
    cpf_funcionario VARCHAR(11),
    FOREIGN KEY (id_locacao) REFERENCES locacao(id_locacao)
    ON DELETE CASCADE,
    FOREIGN KEY (cpf_funcionario) REFERENCES funcionario(cpf)
    ON DELETE SET NULL
);

CREATE TABLE ocorrencia (
    id_ocorrencia SERIAL PRIMARY KEY,
    id_locacao INTEGER,
    tipo VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    descricao TEXT
);

ALTER TABLE ocorrencia
ADD CONSTRAINT fk_ocorrencia_locacao
FOREIGN KEY (id_locacao)
REFERENCES locacao(id_locacao)
ON DELETE CASCADE;


-- Populando banco de dados

-- FILIAIS
INSERT INTO filial (cnpj, nome, endereco, telefone) VALUES
('12345678000190', 'Filial Centro', 'Rua Central, 100', '1111-1111'),
('98765432000100', 'Filial Norte', 'Avenida Norte, 200', '2222-2222');

-- CLIENTES
INSERT INTO cliente (cpf, nome, data_nascimento, cnh, avaliacao) VALUES
('12345678901', 'João Silva', '1985-05-10', 'CNH12345', 4.5),
('23456789012', 'Maria Souza', '1990-08-20', 'CNH23456', 4.0),
('34567890123', 'Carlos Pereira', '1978-12-15', 'CNH34567', 3.8);

-- FUNCIONARIOS
INSERT INTO funcionario (cpf, nome, endereco, salario, cargo, cnpj_filial) VALUES
('11122233344', 'Ana Paula', 'Rua A, 100', 2500.00, 'Atendente', '12345678000190'),
('55566677788', 'Bruno Lima', 'Rua B, 200', 3000.00, 'Gerente', '12345678000190'),
('99988877766', 'Carla Santos', 'Avenida C, 300', 2200.00, 'Atendente', '98765432000100');

-- VEICULOS
INSERT INTO veiculo (placa, tipo, modelo, ano, preco_diaria, kmanterior, alugado, cnpj_filial) VALUES
('ABC1234', 'Sedan', 'Toyota Corolla', 2020, 150.00, 30000.0, false, '12345678000190'),
('DEF5678', 'SUV', 'Honda CRV', 2019, 200.00, 45000.0, false, '12345678000190'),
('GHI9012', 'Hatch', 'Ford Fiesta', 2021, 120.00, 15000.0, false, '98765432000100');

-- LOCACOES
INSERT INTO locacao (cpf_cliente, placa_veiculo, cnpj_filial_retirada, cnpj_filial_retorno, data_inicial, data_final, combust_inicial, combust_final, km_percorrido, valor) VALUES
('12345678901', 'ABC1234', '12345678000190', '12345678000190', '2025-12-01', '2025-12-05', 50.0, 45.0, 500.0, 750.0),
('23456789012', 'DEF5678', '12345678000190', '12345678000190', '2025-12-10', NULL, 60.0, NULL, NULL, NULL);

-- OCORRENCIAS
INSERT INTO ocorrencia (id_locacao, tipo, data_ocorrencia, descricao) VALUES
(1, 'Arranhão', '2025-12-02 14:30:00', 'Arranhão no para-choque traseiro'),
(1, 'Combustível', '2025-12-05 10:00:00', 'Abastecimento feito parcialmente'),
(2, 'Atraso', '2025-12-11 09:00:00', 'Cliente atrasou na devolução');