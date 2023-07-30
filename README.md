# M3P2-BackEnd-Squad5

## JWT Secret

Está disponível no [card do trello](https://trello.com/c/j3N4jA3l).

Coloque a _secret key_ disponível no card no arquivo `.env` na raiz do projeto:

```
JWT_SECRET=[secret]
```

Substitua `[secret]` pela chave disponível no card.


## Iniciando o DB

1) Inicialize o Docker


2) Abra um terminal no diretório raiz do projeto e inicialize o container utilizando o comando:

```
docker-compose -f src/main/resources/db/docker-compose.yml -p db-labmedicine up
```

3) Inicialize uma conexão com o banco de dados utilizando o gerenciador de sua preferência (ex.: JetBrains DataGrip), utilizando a seguinte url de conexão:

```
jdbc:oracle:thin:@localhost:1521:xe
```
    
- **user**: sys as sysdba
- **password**: oracle


4) Utilizando o gerenciador de banco de dados, crie um novo usuário **LABMEDICINE** com senha **labmed**:

```
CREATE USER labmedicine IDENTIFIED BY labmed DEFAULT tablespace users;
GRANT ALL PRIVILEGES TO labmedicine;
```
 
5) Crie um arquivo `.env` no diretório root da aplicação com o seguinte conteúdo:
```
DB_USERNAME=labmedicine
DB_PASSWORD=labmed
```

6) Agora é só iniciar a aplicação e ela deve conseguir se conectar ao banco e criar automaticamente todas as tabelas necessárias!