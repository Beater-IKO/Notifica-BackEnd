# Notifica - Sistema de Notificações

Sistema de gerenciamento de notificações desenvolvido em Spring Boot.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA**
- **MySQL**
- **Maven**
- **Lombok**

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- MySQL 8.0+

## Configuração do Banco de Dados

1. Instale o MySQL
2. Crie um banco de dados chamado `notifica`
3. Configure as credenciais no arquivo `application.properties`

## Como Executar

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd notifica
```

### 2. Configure o banco de dados
Edite o arquivo `src/main/resources/application.properties` com suas configurações:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/notifica
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 3. Execute o projeto
```bash
mvn spring-boot:run
```

Ou compile e execute o JAR:
```bash
mvn clean package
java -jar target/NotificaCrud-0.0.1-SNAPSHOT.jar
```

## Endpoints da API

A aplicação estará disponível em `http://localhost:8080`

### Autenticação
- `POST /auth/login` - Login do usuário

### Usuários
- `GET /users` - Listar usuários
- `POST /users` - Criar usuário
- `PUT /users/{id}` - Atualizar usuário
- `DELETE /users/{id}` - Deletar usuário

### Tickets
- `GET /tickets` - Listar tickets
- `POST /tickets` - Criar ticket
- `PUT /tickets/{id}` - Atualizar ticket
- `DELETE /tickets/{id}` - Deletar ticket

## Testes

Execute os testes com:
```bash
mvn test
```

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/br/com/bd_notifica/
│   │   ├── config/          # Configurações
│   │   ├── controllers/     # Controllers REST
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── entities/       # Entidades JPA
│   │   ├── enums/          # Enumerações
│   │   ├── repositories/   # Repositórios
│   │   └── services/       # Serviços
│   └── resources/
│       └── application.properties
└── test/                   # Testes unitários
```