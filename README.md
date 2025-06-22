# DESAFIO: Projeto Spring Boot estruturado

### Curso Java Spring Essential
### Capítulo: Login e controle de acesso 
### https://devsuperior.com.br

## Descrição

O **DSCommerce** é uma API REST para sistema de e-commerce desenvolvida com Spring Boot. O sistema permite gerenciar produtos, categorias, pedidos e usuários, implementando funcionalidades completas de CRUD com autenticação e autorização baseadas em OAuth2 e JWT.

O projeto implementa um sistema de comércio eletrônico com diferentes níveis de acesso (ADMIN e CLIENT), validação de dados, tratamento de exceções e persistência de dados usando JPA/Hibernate com banco H2.

## Pré-requisitos

Para compilar e executar este projeto, você precisa ter instalado:

- **Java 17 JDK** ou superior
- **Maven 3.6+** para gerenciamento de dependências
- **IDE** de sua preferência (IntelliJ IDEA, Eclipse, VS Code, etc.)
- **Banco de dados H2** (incluído como dependência no projeto)

## Compilação

Para compilar o projeto, execute o seguinte comando na raiz do projeto:

```bash
mvn clean install
```

Para compilar sem executar os testes:

```bash
mvn clean install -DskipTests
```

## Execução

### Opção 1: Usando Maven Spring Boot Plugin (Recomendado)

```bash
mvn spring-boot:run
```

### Opção 2: Executando o JAR gerado

```bash
java -jar target/dscommerce-0.0.1-SNAPSHOT.jar
```

### Opção 3: Executando via IDE

Execute a classe principal `DscommerceApplication.java` diretamente pela sua IDE.

**Porta padrão:** A aplicação será executada na porta `8080` por padrão.

**URL base:** `http://localhost:8080`

## Utilização / Endpoints da API

### Autenticação

O sistema utiliza OAuth2 com JWT para autenticação. Para acessar os endpoints protegidos, é necessário obter um token de acesso.

#### Obter Token de Acesso

```http
POST /oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=password&client_id=myclientid&client_secret=myclientsecret&username=alex@gmail.com&password=123456
```

**Response:**

```json
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
  "token_type": "Bearer",
  "expires_in": 86400
}
```

### Produtos

#### Listar Produtos (Público)

```http
GET /products
GET /products?name=Macbook&page=0&size=10
```

**Response:**

```json
{
  "content": [
    {
      "id": 1,
      "name": "The Lord of the Rings",
      "price": 90.5,
      "imgUrl": "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 25
}
```

#### Buscar Produto por ID (Público)

```http
GET /products/{id}
```

**Response:**

```json
{
  "id": 1,
  "name": "The Lord of the Rings",
  "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
  "price": 90.5,
  "imgUrl": "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg",
  "categories": [
    {
      "id": 1,
      "name": "Livros"
    }
  ]
}
```

#### Criar Produto (Apenas ADMIN)

```http
POST /products
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Produto Novo",
  "description": "Descrição do produto com pelo menos 10 caracteres",
  "price": 100.0,
  "imgUrl": "https://exemplo.com/imagem.jpg",
  "categories": [
    {
      "id": 1
    }
  ]
}
```

#### Atualizar Produto (Apenas ADMIN)

```http
PUT /products/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Produto Atualizado",
  "description": "Nova descrição do produto",
  "price": 150.0,
  "imgUrl": "https://exemplo.com/nova-imagem.jpg",
  "categories": [
    {
      "id": 1
    }
  ]
}
```

#### Deletar Produto (Apenas ADMIN)

```http
DELETE /products/{id}
Authorization: Bearer {token}
```

### Categorias

#### Listar Todas as Categorias (Público)

```http
GET /categories
```

**Response:**

```json
[
  {
    "id": 1,
    "name": "Livros"
  },
  {
    "id": 2,
    "name": "Eletrônicos"
  }
]
```

### Pedidos

#### Buscar Pedido por ID (ADMIN ou CLIENT)

```http
GET /orders/{id}
Authorization: Bearer {token}
```

**Response:**

```json
{
  "id": 1,
  "moment": "2022-07-25T13:00:00Z",
  "status": "PAID",
  "client": {
    "id": 1,
    "name": "Maria Brown"
  },
  "items": [
    {
      "productId": 1,
      "name": "The Lord of the Rings",
      "price": 90.5,
      "quantity": 2,
      "imgUrl": "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"
    }
  ],
  "payment": {
    "id": 1,
    "moment": "2022-07-25T15:00:00Z"
  }
}
```

#### Criar Pedido (Apenas CLIENT)

```http
POST /orders
Authorization: Bearer {token}
Content-Type: application/json

{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 3,
      "quantity": 1
    }
  ]
}
```

### Usuários

#### Obter Dados do Usuário Logado (ADMIN ou CLIENT)

```http
GET /users/me
Authorization: Bearer {token}
```

**Response:**

```json
{
  "id": 1,
  "name": "Alex Green",
  "email": "alex@gmail.com",
  "phone": "977777777",
  "birthDate": "1987-12-13",
  "roles": [
    {
      "id": 1,
      "authority": "ROLE_CLIENT"
    }
  ]
}
```

## Configuração

### Banco de Dados

O projeto utiliza o banco H2 em memória por padrão. As configurações estão no arquivo `application-test.properties`:

```properties
# Datasource
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# H2 Console (para desenvolvimento)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA, SQL
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.defer-datasource-initialization=true
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Variáveis de Ambiente

O projeto utiliza as seguintes variáveis de ambiente (com valores padrão):

- `CLIENT_ID`: ID do cliente OAuth2 (padrão: `myclientid`)
- `CLIENT_SECRET`: Secret do cliente OAuth2 (padrão: `myclientsecret`)
- `JWT_DURATION`: Duração do token JWT em segundos (padrão: `86400`)
- `CORS_ORIGINS`: Origens permitidas para CORS (padrão: `http://localhost:3000,http://localhost:5173`)

### Perfis de Execução

O projeto está configurado para executar com o perfil `test` por padrão, que utiliza o banco H2 em memória e dados de teste pré-carregados.

### Console H2

Durante o desenvolvimento, você pode acessar o console H2 para visualizar os dados:

- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** _(deixe em branco)_

### Usuários de Teste

O sistema vem com usuários pré-cadastrados para teste:

- **Admin:** `alex@gmail.com` / `123456`
- **Client:** `maria@gmail.com` / `123456`

### Estrutura de Roles

- **ROLE_ADMIN**: Acesso completo (CRUD de produtos, visualização de pedidos)
- **ROLE_CLIENT**: Acesso limitado (criar pedidos, visualizar próprios dados)

## Tecnologias Utilizadas

- **Spring Boot 3.4.2**
- **Spring Security + OAuth2**
- **Spring Data JPA**
- **Bean Validation**
- **H2 Database**
- **Maven**
- **Java 17**
