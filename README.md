# concurrentReadTable

## Descrição
Aplicação que deve, de tempo em tempo, buscar por tarefas com data de criação muito antiga  
e excluí-la da base.

## Objetivo
O objetivo desse projeto é aprender como posso fazer para que uma aplicação, que tenha réplicas  
e leia registros do banco de dados, não processe os mesmos registros em cada réplica.

## Resultados
A solução encontrada foi fazer o uso de lock pessimista nos registros da tabela, assim  
a 2ª aplicação fica impedida de ler aquele registro enquanto a 1ª não finalizar sua transação.  

### Pontos de duvidas em aberto:
Não ficou claro pra mim se quando a 2ª aplicação tenta ler o registro "lockado", ela espera o  
registro ficar disponivel ou se ela ignora aquele registro e busca o proximo disponível.  
Pesquisando alguns materiais, me parece que a 2ª aplicação espera o registro ficar disponível,  
mas é algo que não consegui confirmar com esse projeto.

## Tecnologias Utilizadas

- Java
- Spring boot
- Postgres
- Docker

## Como Executar o Projeto

### Pré-requisitos

- Docker
- Alguma ferramenta de Database Workbench

### Passos para Execução
1. Clone o repositório:
   ```bash
   git clone https://github.com/Brunno98/concurrentReadTable.git
   ```
2. Execute o comando `docker compose up -d` para buildar e subir a aplicação e o banco de dados.
3. Use a ferramente de database workbench para executar os SQLs localizados na pasta `src/main/resources/db/migration` no banco de dados.
