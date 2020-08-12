# pauta-service
Projeto de Controle de Pautas

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=morasene_pauta-service&metric=alert_status)](https://sonarcloud.io/dashboard?id=morasene_pauta-service)

### Instalação Banco de Dados
Para execução deste projeto foi utilizado o banco de dados mysql. 

Você pode encontrar o instalador nestes links:
- https://www.mysql.com/downloads/
- http://www.wampserver.com/en/

### Criação do database
Para o melhor andamento do projeto será necessário criar o banco de dados.

teste

```bash
create database pauta;
```


### Criação das tabelas
Com o banco de dados criado, executar os comandos abaixo:

```bash
create table associado (id_associado integer not null auto_increment, cpf varchar(255), nome varchar(255), primary key (id_associado)) engine=MyISAM;
create table pauta (id_pauta integer not null auto_increment, quantidade_votos_nao integer, quantidade_votos_sim integer, tema varchar(255), primary key (id_pauta)) engine=MyISAM;
create table sessao (id_sessao integer not null auto_increment, data_fim datetime, data_inicio datetime, pauta_id_pauta integer, primary key (id_sessao)) engine=MyISAM;
create table voto (id_voto integer not null auto_increment, voto varchar(255), associado_id_associado integer, sessao_id_sessao integer, primary key (id_voto)) engine=MyISAM;
alter table associado add constraint UK_36jthrl900uiv144jxj9jiem unique (cpf);
alter table pauta add constraint UK_m4kam44wepm4d5lbwqsoi6e9g unique (tema);
alter table sessao add constraint FK79bayxe7qchimmy0duavfgw1s foreign key (pauta_id_pauta) references pauta (id_pauta);
alter table voto add constraint FKbnsat9gmsynnxwopvwnul3akc foreign key (associado_id_associado) references associado (id_associado);
alter table voto add constraint FKe70bal6mhwmudln105u1f27gs foreign key (sessao_id_sessao) references sessao (id_sessao);
```	

### Clean  Code
Neste projeto tentei utilizar ao máximo as premissas do Clean Code pois acredito que o projeto fica mais claro e organizado utilizando as técnicas descritas.

### Mockito
Para os testes unitários foi utilizado o framework mockito, visto a minha familiaridade com  o mesmo.

### Execução do Projeto
Para executar o projeto basta compilar o mesmo utilizando o comando abaixo. Será necessário ter o maven instalado na maquina ou abrir o projeto no eclipse e rodar o comando.
```bash
mvn clean install
```

Para execução via linha de comando pegar o jar que se encontra dentro da pasta target e rodar o comando abaixo:

```bash
java -jar pauta-service-1.0.0.jar
```

### Documentação do Projeto
A documentação do projeto encontra-se em:

```bash
http://localhost:8080/swagger-ui.html#/
```
