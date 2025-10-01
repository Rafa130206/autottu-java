# AutoTTU

Aplicação Spring Boot para gestão de motos, slots, check-ins, manutenções e test rides, com autenticação via Spring Security e Thymeleaf para renderização server-side.

## 🚀 Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.9+

### Passo a passo

```bash
# 1. Clonar o repositório
$ git clone <url-do-repo>
$ cd autottu

# 2. Instalar dependências e compilar
$ mvn clean install

# 3. Aplicar migrações (H2 em memória)
$ mvn flyway:clean flyway:migrate

# 4. Subir a aplicação (porta padrão 8080)
$ mvn spring-boot:run
```

> Banco em memória: `jdbc:h2:mem:testdb` (usuário `DB_USER`, senha `DB_PASS`). Console disponível em `/h2-console`.

## 🔐 Credenciais iniciais

| Perfil  | Usuário | Senha      |
| ------- | ------- | ---------- |
| ADMIN   | admin   | admin123   |
| USUARIO | usuario | usuario123 |

## 🧭 Rotas Principais

### Públicas

| Método | Caminho      | Descrição                                  |
| ------ | ------------ | ------------------------------------------ |
| GET    | `/`          | Landing page com CTA para registrar/entrar |
| GET    | `/login`     | Formulário de autenticação                 |
| GET    | `/registrar` | Cadastro de usuário                        |
| POST   | `/registrar` | Criação de usuário (senha com BCrypt)      |

### Autenticadas

| Método | Caminho   | Descrição                         |
| ------ | --------- | --------------------------------- |
| GET    | `/menu`   | Dashboard pós-login               |
| GET    | `/perfil` | Edição de dados do usuário logado |
| POST   | `/perfil` | Persistência das alterações       |
| POST   | `/logout` | Encerrar sessão                   |

### Recursos de Domínio

- **Motos**: `/motos`, `/motos/novo`, `/motos/{id}`, `/motos/{id}/editar`
- **Slots**: `/slots`, `/slots/novo`
- **Check-ins**: `/checkins`, `/checkins/novo`
- **Manutenções**: `/manutencoes`, `/manutencoes/novo`
- **Test Rides**: `/testrides`, `/testrides/novo`

> Operações de aprovação/rejeição/conclusão de Test Ride exigem perfil ADMIN.
> A página de integrantes (`/integrantes`) também fica visível apenas para administradores.

### Detalhamento CRUD

#### Motos (`/motos`)

| Método | Caminho              | Descrição                     |
| ------ | -------------------- | ----------------------------- |
| GET    | `/motos`             | Lista motos cadastradas.      |
| GET    | `/motos/novo`        | Formulário de criação.        |
| POST   | `/motos`             | Persiste nova moto.           |
| GET    | `/motos/{id}`        | Mostra detalhes da moto.      |
| GET    | `/motos/{id}/editar` | Formulário de edição.         |
| POST   | `/motos/{id}`        | Atualiza (usa `_method=put`). |
| POST   | `/motos/{id}/delete` | Exclui.                       |

#### Slots (`/slots`)

| Método | Caminho              | Descrição                                             |
| ------ | -------------------- | ----------------------------------------------------- |
| GET    | `/slots`             | Lista slots e seu status.                             |
| GET    | `/slots/novo`        | Formulário para criar slot.                           |
| POST   | `/slots`             | Cria slot (verifica se a moto já não está vinculada). |
| GET    | `/slots/{id}/editar` | Formulário de edição.                                 |
| POST   | `/slots/{id}`        | Atualiza relacionamento moto/ocupação.                |
| POST   | `/slots/{id}/delete` | Desvincula moto e remove slot.                        |

#### Check-ins (`/checkins`)

| Método | Caminho                 | Descrição                                              |
| ------ | ----------------------- | ------------------------------------------------------ |
| GET    | `/checkins`             | Lista check-ins registrados.                           |
| GET    | `/checkins/novo`        | Formulário de criação.                                 |
| POST   | `/checkins`             | Cria check-in (DTO `CheckinRequest`).                  |
| GET    | `/checkins/{id}/editar` | Formulário com dados existentes.                       |
| POST   | `/checkins/{id}`        | Atualiza (`_method=put`) ou exclui (`_method=delete`). |

#### Manutenções (`/manutencoes`)

| Método | Caminho                    | Descrição                                    |
| ------ | -------------------------- | -------------------------------------------- |
| GET    | `/manutencoes`             | Lista agendas existentes.                    |
| GET    | `/manutencoes/novo`        | Formulário para agendamento.                 |
| POST   | `/manutencoes`             | Agenda manutenção (valida moto selecionada). |
| POST   | `/manutencoes/{id}/delete` | Cancela manutenção.                          |

#### Test Rides (`/testrides`)

| Método | Caminho                    | Descrição                                            | Perfil      |
| ------ | -------------------------- | ---------------------------------------------------- | ----------- |
| GET    | `/testrides`               | Lista solicitações (botões condicionais para ADMIN). | Autenticado |
| GET    | `/testrides/novo`          | Formulário de solicitação.                           | Autenticado |
| POST   | `/testrides`               | Cria solicitação (usa IDs de moto/usuário).          | Autenticado |
| POST   | `/testrides/{id}/aprovar`  | Aprova solicitação pendente.                         | ADMIN       |
| POST   | `/testrides/{id}/rejeitar` | Rejeita solicitação pendente.                        | ADMIN       |
| POST   | `/testrides/{id}/delete`   | Remove solicitação.                                  | ADMIN       |

## 🗂 Estrutura

```
src/main/java/
├── config/UsuarioLogadoAdvice.java
├── control/ (MVC Controllers)
├── model/ (Entidades JPA)
├── repository/ (Spring Data)
├── security/SegurancaConfig.java
├── service/ (Regras de negócio)
└── shared/converter/YesNoBooleanConverter.java

src/main/resources/templates/
├── fragmentos.html         # Partials (navbar, saudação, etc.)
├── menu.html                # Dashboard pós-login
├── usuario/registrar.html   # Cadastro
└── usuario/perfil.html      # Perfil do usuário logado
```

## 👥 Integrantes

| Nome                         | RM     |
| ---------------------------- | ------ |
| André Luís Mesquita de Abreu | 558159 |
| Maria Eduarda Brigidio       | 558575 |
| Rafael Bompadre Lima         | 556459 |

## 📌 Observações

- Senhas armazenadas com `BCryptPasswordEncoder`.
- Regras de navegação em `fragmentos.html` adaptam ações conforme perfil do usuário.
- Migrações Flyway (`V1__`, `V2__`, `V3__`) recriam o schema a cada inicialização.
- Testes podem ser adicionados em `src/test/java/br/com/fiap/autottu`.
