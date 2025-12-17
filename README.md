# Sistema de Locadora de Veículos

Sistema Java para gerenciar uma locadora de veículos com interface gráfica, utilizando PostgreSQL.

## Estrutura do Projeto

```
locadora/
├── lib/
│   └── postgresql-42.7.3.jar
├── src/
│   └── locadora/
│       ├── model/
│       │   ├── Cliente.java
│       │   ├── Filial.java
│       │   ├── Veiculo.java
│       │   ├── Funcionario.java
│       │   ├── Locacao.java
│       │   ├── Ocorrencia.java
│       │   ├── Chamada.java
│       │   └── Aprovacao.java
│       ├── dao/
│       │   ├── ClienteDAO.java
│       │   ├── FilialDAO.java
│       │   ├── VeiculoDAO.java
│       │   ├── FuncionarioDAO.java
│       │   ├── LocacaoDAO.java
│       │   ├── OcorrenciaDAO.java
│       │   ├── ChamadaDAO.java
│       │   └── AprovacaoDAO.java
│       ├── service/
│       │   ├── ClienteService.java
│       │   ├── FilialService.java
│       │   ├── VeiculoService.java
│       │   ├── FuncionarioService.java
│       │   ├── LocacaoService.java
│       │   ├── OcorrenciaService.java
│       │   ├── ChamadaService.java
│       │   └── AprovacaoService.java
│       ├── view/
│       │   ├── MainFrame.java
│       │   ├── ClienteForm.java
│       │   ├── FilialForm.java
│       │   ├── VeiculoForm.java
│       │   ├── FuncionarioForm.java
│       │   └── LocacaoForm.java
│       ├── util/
│       │   └── ConnectionFactory.java
│       └── Main.java
└── README.md
```

## Requisitos

- Java 8 ou superior
- PostgreSQL 10 ou superior
- JDBC Driver PostgreSQL 42.7.3

## Configuração do Banco de Dados

### 1. Criar banco de dados

```sql
CREATE DATABASE locadora;
```

### 2. Criar tabelas

Execute os seguintes comandos no PostgreSQL:

```sql
-- Tabela de Filiais
CREATE TABLE filial (
    cnpj VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    telefone VARCHAR(20) NOT NULL
);

-- Tabela de Clientes
CREATE TABLE cliente (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    cnh VARCHAR(12) NOT NULL,
    avaliacao DECIMAL(3,1) DEFAULT 0.0
);

-- Tabela de Veículos
CREATE TABLE veiculo (
    placa VARCHAR(10) PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    ano INTEGER NOT NULL,
    preco_diaria DECIMAL(10,2) NOT NULL,
    kmanterior DECIMAL(12,2) NOT NULL,
    alugado BOOLEAN DEFAULT false,
    historico TEXT,
    cnpj_filial VARCHAR(14) NOT NULL,
    FOREIGN KEY (cnpj_filial) REFERENCES filial(cnpj) ON DELETE RESTRICT
);

-- Tabela de Funcionários
CREATE TABLE funcionario (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    cnpj_filial VARCHAR(14) NOT NULL,
    FOREIGN KEY (cnpj_filial) REFERENCES filial(cnpj) ON DELETE RESTRICT
);

-- Tabela de Locações
CREATE TABLE locacao (
    id_locacao SERIAL PRIMARY KEY,
    cpf_cliente VARCHAR(11) NOT NULL,
    placa_veiculo VARCHAR(10) NOT NULL,
    cnpj_filial_retirada VARCHAR(14) NOT NULL,
    cnpj_filial_retorno VARCHAR(14) NOT NULL,
    data_inicial DATE NOT NULL,
    data_final DATE NOT NULL,
    combust_inicial DECIMAL(10,2) NOT NULL,
    combust_final DECIMAL(10,2),
    km_percorrido DECIMAL(12,2),
    valor DECIMAL(10,2),
    FOREIGN KEY (cpf_cliente) REFERENCES cliente(cpf) ON DELETE RESTRICT,
    FOREIGN KEY (placa_veiculo) REFERENCES veiculo(placa) ON DELETE RESTRICT,
    FOREIGN KEY (cnpj_filial_retirada) REFERENCES filial(cnpj) ON DELETE RESTRICT,
    FOREIGN KEY (cnpj_filial_retorno) REFERENCES filial(cnpj) ON DELETE RESTRICT
);

-- Tabela de Ocorrências
CREATE TABLE ocorrencia (
    id_ocorrencia SERIAL PRIMARY KEY,
    id_locacao INTEGER NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    data_ocorrencia TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descricao TEXT NOT NULL,
    FOREIGN KEY (id_locacao) REFERENCES locacao(id_locacao) ON DELETE CASCADE
);

-- Tabela de Chamadas (Serviços)
CREATE TABLE chamada (
    id_locacao INTEGER PRIMARY KEY,
    cpf_funcionario VARCHAR(11) NOT NULL,
    FOREIGN KEY (id_locacao) REFERENCES locacao(id_locacao) ON DELETE CASCADE,
    FOREIGN KEY (cpf_funcionario) REFERENCES funcionario(cpf) ON DELETE RESTRICT
);

-- Tabela de Aprovações
CREATE TABLE aprovacao (
    id_locacao INTEGER PRIMARY KEY,
    cpf_funcionario VARCHAR(11) NOT NULL,
    FOREIGN KEY (id_locacao) REFERENCES locacao(id_locacao) ON DELETE CASCADE,
    FOREIGN KEY (cpf_funcionario) REFERENCES funcionario(cpf) ON DELETE RESTRICT
);
```

### 3. Configurar conexão

Atualize o arquivo `ConnectionFactory.java` com suas credenciais:

```java
private static final String URL = "jdbc:postgresql://localhost:5432/locadora";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

## Compilação

```bash
javac -cp lib/postgresql-42.7.3.jar -d bin src/locadora/**/*.java
```

## Execução

```bash
java -cp bin:lib/postgresql-42.7.3.jar locadora.Main
```

## Funcionalidades

### Gestão de Clientes
- Adicionar, atualizar e deletar clientes
- CPF como chave primária
- Armazenar data de nascimento, CNH e avaliação
- Buscar cliente por CPF
- Listar todos os clientes

### Gestão de Filiais
- Gerenciar filiais da locadora
- CNPJ como chave primária
- Adicionar, atualizar e deletar filiais

### Gestão de Veículos
- Adicionar, atualizar e deletar veículos
- Placa como chave primária
- Registrar tipo, modelo, ano, preço diária
- Histórico de uso e km anterior
- Listar todos os veículos
- Visualizar veículos disponíveis
- Buscar por placa

### Gestão de Funcionários
- Cadastrar funcionários
- CPF como chave primária
- Associar a filial
- Definir cargo e salário
- Adicionar, atualizar e deletar

### Gestão de Locações
- Criar novas locações
- Registrar filial de retirada e retorno
- Calcular valor total automaticamente
- Registrar combustível inicial e final
- Registrar KM percorrido
- Listar locações por cliente
- Listar locações por veículo
- Visualizar locações abertas

### Gestão de Ocorrências
- Registrar incidentes durante locação
- Armazenar tipo e descrição
- Timestamp automático
- Consultar por locação ou tipo

### Gestão de Chamadas e Aprovações
- Registrar chamadas de serviço
- Registrar aprovações
- Associar funcionário responsável

## Arquitetura

O projeto segue o padrão MVC (Model-View-Controller):

- **Model**: Representação das entidades (8 classes)
- **DAO**: Camada de acesso aos dados (8 classes)
- **Service**: Lógica de negócio e validações (8 classes)
- **View**: Interface gráfica com Swing (6 formulários)
- **Util**: Utilitários (ConnectionFactory)

## Estratégia de Chaves

- **Chaves Primárias String**: cliente (CPF), filial (CNPJ), veiculo (placa), funcionario (CPF)
- **Chaves Primárias Integer**: locacao (id_locacao SERIAL), ocorrencia (id_ocorrencia SERIAL)
- **Relacionamentos**: Através de Foreign Keys com ON DELETE RESTRICT ou CASCADE

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## Licença

Este projeto está sob a licença MIT.

