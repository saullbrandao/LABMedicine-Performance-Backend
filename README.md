<h1 align="center">
  LABMedicine Performance
</h1>

<h2 align="center">
  Segundo Projeto do Módulo 3 do Devin[Philips]
</h2>
<br />


<h2 align="center">Backend</h2>
<br />

# 📑 Tabela de Conteúdo

- [Sobre o projeto](#-sobre-o-projeto)
- [Tecnologias](#-tecnologias)
- [Iniciando](#-iniciando)
  - [Requisitos](#requisitos)
  - [Como rodar](#como-rodar)

# 📃 Sobre o projeto

- API para [sistema de gestão hospitalar](https://github.com/DEVin-Philips/M3P2-FrontEnd-Squad5/tree/main)
- Autenticação usando email e senha com retorno de token JWT
- Usuário pode cadastrar, editar ou deletar pacientes, consultas, exames, medicamentos, exercícios e dietas
- Apenas o usuário Admin pode cadastrar novos usuários e consultar a lista de logs do sistema 

### Resources

- cadastro de usuário `/cadastrar` (somente para Admin)
- usuários `/usuarios`
- login `/usuarios/login`
- consultas `/consultas` (Admin e Médico)
- exames `/exames` (Admin e Médico)
- medicamentos `/medicamentos`
- dietas `/dietas`
- exercícios `exercicios`

# 💻 Tecnologias

- [Java](https://www.java.com/)
- [SpringBoot](https://spring.io/projects/spring-boot)
- [JWT Authentication](https://jwt.io/)
- [Docker](https://www.docker.com/)
- [Liquibase](https://www.liquibase.org/)
- [Oracle Database](https://www.oracle.com/)

# 🚀 Iniciando

## Requisitos

- [Docker](https://www.docker.com/)

## Como rodar

1) Clonar repositório e instalar os pacotes com `mvn clean install`

2) Inicialize o Docker 

3) Abra um terminal no diretório raiz do projeto e inicialize o container utilizando o comando:<br>

	`docker-compose -f src/main/resources/db/docker-compose.yml -p db-labmedicine up`

4) Inicialize uma conexão com o banco de dados utilizando o gerenciador de sua preferência (ex.: JetBrains DataGrip), utilizando a seguinte url de conexão:<br>

	`jdbc:oracle:thin:@localhost:1521:xe`
	- **user**: sys as sysdba
	- **password**: oracle

5) Utilizando o gerenciador de banco de dados, crie um novo usuário **LABMEDICINE** com senha **labmed**:<br>
	```
	CREATE USER labmedicine IDENTIFIED BY labmed DEFAULT tablespace users;
	GRANT ALL PRIVILEGES TO labmedicine;
	```

6) Crie um arquivo `.env` no diretório root da aplicação com o seguinte conteúdo:<br>
	```
	DB_USERNAME=labmedicine
	DB_PASSWORD=labmed
	JWT_SECRET=IB6ThR2yn3CcEa9bExzYo4GCF79pbhI3Ol24PfFDh44
	```
7) Agora é só iniciar a aplicação e ela deve conseguir se conectar ao banco e criar automaticamente todas as tabelas necessárias!

<br>

- Acesso através do http://localhost:8080/
- Um usuário admin está setado pra facilitar o login:
  - email: devs@labmedicine.com
  - senha: 123456
