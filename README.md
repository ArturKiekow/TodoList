# **Todo List**

Este projeto é uma API de lista de tarefas, criado no intuito de aplicar o que estou aprendendo sobre Java e Spring.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) 
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

O projeto foi criado usando Java, Spring, PostgreSQL e Flyway para fazer as alterações no banco de dados.


## **Download do projeto**
Clone o repositório com:
```
    git clone git@github.com:ArturKiekow/TodoList.git
```

## **Pré-requisitos**
- [**Java 21**](https://www.oracle.com/java/technologies/javase-downloads.html)

- [**Maven 3.9.9**](https://maven.apache.org/download.cgi)

- [**PostgreSQL 16**](https://www.postgresql.org/download/)

## **Executar o projeto**

---

| Rotas                         | Descrição                                                         |
|-------------------------------|-------------------------------------------------------------------|
| GET /todolist             | Retorna todas as tasks salvas no banco de dados                   |
| GET /todolist/{id}        | Retorna a task com o id informado se ela existir                  |
| POST /todolist                | Cria uma nova task e salva ela no banco de dados                  |
| PUT /todolist{id}             | Atualiza os atributos da task com o id informado se ela existir   |                                                           |
| PUT /todolist/changeFinished/{id} | Altera o atributo "finished" da task com o id informado           |
| DEL /todolist{id}         | Deleta a task com o id informado do banco de dados                |
| DEL /todolist             | Apaga todas as tasks em que o atributo "finished" seja verdadeiro |


## GET /todolist
### Response
```json
[
    {
        "taskId": 1,
        "title": "title",
        "description": "description",
        "finished": false,
        "priority": "low"
    },
    {
        "taskId": 2,
        "title": "title 2",
        "description": "description 2",
        "finished": false,
        "priority": "urgent"
    }
]
```

## GET /todolist/{id}
### Response
```json
{
   "taskId": 1,
   "title": "title",
   "description": "description",
   "finished": false,
   "priority": "low"
}
```

## POST /todolist
### Request
```json
{
    "title": "title",
    "description": "description",
    "priority": "urgent"
}
```
### Response
```json
{
    "taskId": 1,
    "title": "title",
    "description": "description",
    "finished": false,
    "priority": "urgent"
}
```
## PUT /todolist{id}
### Request

```json
{
    "title": "title update",
    "description": "description update",
    "priority": "low"
}
```

### Response
```json
{
  "taskId": 1,
  "title": "title update",
  "description": "description update",
  "finished": false,
  "priority": "low"
}
```

## PUT /todolist/changeFinished/{id}
### Response
```json
{
  "taskId": 1,
  "title": "title update",
  "description": "description update",
  "finished": true,
  "priority": "low"
}
```

## DEL /todolist{id}
### Response
```
204 No Content
```

## DEL /todolist

```

```