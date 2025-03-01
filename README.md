<h1 align="center" style="font-size: 240px;">
  To Do List - Java e Spring Boot
</h1>

## 💻 Objetivo

O Todolist é um gerenciador de tarefas simples desenvolvido em Java utilizando o framework Spring Boot. Ele permite que os usuários criem, atualizem, listem e excluam tarefas. Além disso, possui um sistema de autenticação básico para garantir que apenas usuários autorizados possam acessar e manipular suas próprias tarefas.
## ✨ Funcionalidades

- 🔒 **Autenticação de Usuário**: Utiliza autenticação básica com credenciais codificadas em Base64.
- ✅ **Gerenciamento de Tarefas**: Criação, atualização, listagem e exclusão de tarefas.
- 🛡️ **Validação de Dados**: Validação de datas, permissões de usuário e limite de caracteres para o título da tarefa (máximo de 50 caracteres).
- ⚠️ **Tratamento de Exceções**: Manipulação de exceções comuns para fornecer respostas HTTP apropriadas.

## 📂 Estrutura principal do projeto

```
src/
└── main/
    └── java/
        └── br/
            └── com/
                └── rodrigo/
                    └── todolist/
                        ├── tarefa/
                        │   ├── ControllerTarefa.java
                        │   ├── ModelTarefa.java
                        │   └── RepositoryTarefa.java
                        ├── usuario/
                        │   ├── ControllerUsuario.java
                        │   ├── ModelUsuario.java
                        │   └── RepositoryUsuario.java
                        ├── excecoes/
                        │   └── ExceptionHandlerController.java
                        ├── filtro/
                        │   └── FiltroAutenticacao.java
                        ├── TodolistApplication.java
                        └── TesteRota.java
```

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.2**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **BCrypt**

## ⚙️ Configuração do Ambiente

### Pré-requisitos

- Java 17
- Maven

### Passos para Configuração

1. Clone o repositório:
    ```sh
    git clone <URL_DO_REPOSITORIO>
    cd todolist
    ```

2. Compile e empacote o projeto:
    ```sh
    ./mvnw clean package -DskipTests
    ```

3. Execute a aplicação:
    ```sh
    java -jar target/todolist-1.0.0.jar
    ```

4. Acesse a aplicação em `http://localhost:8080`.

## 🔗 Endpoints

### Usuários

- **Criar Usuário**
    ```http
    POST /users
    Content-Type: application/json

    {
        "nickName": "DevRodrigo",
        "nome": "Rodrigo Mazucato",
        "senha": "dev1234"
    }
    ```

### Tarefas

- **Listar Tarefas**
    ```http
    GET /tasks
    Authorization: Basic <credenciais_base64>
    ```

- **Criar Tarefa**
    ```http
    POST /tasks
    Content-Type: application/json
    Authorization: Basic <credenciais_base64>

    {
        "titulo": "Estudar o curso de Java com Spring Boot",
        "dataInicio": "2023-10-01T10:00:00",
        "dataTermino": "2023-10-01T12:00:00",
        "prioridade": "Alta"
    }
    ```

- **Atualizar Tarefa**
    ```http
    PATCH /tasks/{idTarefa}
    Content-Type: application/json
    Authorization: Basic <credenciais_base64>

    {
        "titulo": "Tarefa Atualizada"
    }
    ```

- **Deletar Tarefa**
    ```http
    DELETE /tasks/{idTarefa}
    Authorization: Basic <credenciais_base64>
    ```

## 🐳 Docker

Para executar a aplicação utilizando Docker:

1. Construa a imagem Docker:
    ```sh
    docker build -t todolist:1.0.0 .
    ```

2. Execute o container:
    ```sh
    docker run -p 8080:8080 todolist:1.0.0
    ```

---
Feito com muita dedicação 🎯 por Rodrigo Mazucato 🚀.
