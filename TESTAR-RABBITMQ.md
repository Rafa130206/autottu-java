# 🐰 Guia de Teste - RabbitMQ no AutoTTU

## ✅ Opções para Testar (ESCOLHA UMA)

### 🎯 **OPÇÃO 1: Modo Mock (MAIS FÁCIL - Sem instalação)**

**Quando usar:** Para testar a lógica da aplicação sem precisar do RabbitMQ real.

#### Passo 1: Ativar o modo mock

Edite o arquivo `src/main/resources/application.properties`:

```properties
# Descomente esta linha:
spring.profiles.active=mock
```

#### Passo 2: Rodar a aplicação

```bash
mvn spring-boot:run
```

#### Passo 3: Testar

1. Acesse: http://localhost:8080
2. Login como admin (admin/admin123)
3. Aprove um test ride
4. Veja a notificação simulada no console

**Resultado esperado no console:**

```
==============================================
🧪 MODO MOCK - Simulando envio para RabbitMQ
==============================================
📩 Notificação que SERIA enviada:
🆔 Test Ride ID: 1
👤 Usuário: João Silva
📧 Email: joao@email.com
🏍️ Moto: Honda CG 160
...
==============================================
```

---

### ☁️ **OPÇÃO 2: CloudAMQP (Grátis na Nuvem)**

**Quando usar:** Quer testar o RabbitMQ de verdade sem instalar nada.

#### Passo 1: Criar conta

1. Acesse: https://www.cloudamqp.com/
2. Cadastre-se (pode usar Google/GitHub)

#### Passo 2: Criar instância

1. "Create New Instance"
2. Nome: `autottu-dev`
3. Plan: **Little Lemur (Free)**
4. Região: US-East
5. "Create instance"

#### Passo 3: Pegar credenciais

Na página da instância, copie a URL AMQP completa:

```
amqps://usuario:senha@jellyfish.rmq.cloudamqp.com/usuario
```

#### Passo 4: Configurar aplicação

Edite `application.properties`:

```properties
# Comente as linhas antigas e adicione:
spring.rabbitmq.addresses=amqps://[COLE SUA URL AQUI]

# Certifique-se que o profile mock está comentado:
# spring.profiles.active=mock
```

#### Passo 5: Testar

```bash
mvn spring-boot:run
```

**Vantagens:**

- ✅ Sem instalação local
- ✅ Funciona de qualquer lugar
- ✅ Painel web incluso
- ✅ 1 milhão de mensagens/mês grátis

---

### 💻 **OPÇÃO 3: Instalação Local (Windows)**

**Quando usar:** Desenvolvimento profissional / Trabalho offline.

#### Método A: Script Automático (Recomendado)

Execute no PowerShell **como Administrador**:

```powershell
cd autottu
.\instalar-rabbitmq.ps1
```

O script vai:

- Verificar Chocolatey
- Instalar RabbitMQ + Erlang
- Configurar e iniciar serviços

#### Método B: Manual

**1. Instalar Erlang:**

- Baixe: https://www.erlang.org/downloads
- Execute o instalador
- Mantenha as configurações padrão

**2. Instalar RabbitMQ:**

- Baixe: https://www.rabbitmq.com/download.html
- Execute o instalador
- Mantenha as configurações padrão

**3. Habilitar Management:**

```powershell
cd "C:\Program Files\RabbitMQ Server\rabbitmq_server-3.13.0\sbin"
.\rabbitmq-plugins.bat enable rabbitmq_management
```

**4. Iniciar serviço:**

```powershell
net start RabbitMQ
```

**5. Acessar painel:**

- URL: http://localhost:15672
- User: `guest`
- Pass: `guest`

#### Método C: Chocolatey

Se você tem o Chocolatey:

```powershell
choco install rabbitmq -y
rabbitmq-plugins enable rabbitmq_management
net start RabbitMQ
```

---

## 🧪 Como Testar a Aplicação

### 1. Compile e execute

```bash
mvn clean install
mvn spring-boot:run
```

### 2. Acesse a aplicação

- URL: http://localhost:8080
- Login admin: `admin` / `admin123`

### 3. Crie um Test Ride (se necessário)

1. Vá em "Test Rides"
2. Clique "Novo Test Ride"
3. Preencha o formulário
4. Salve

### 4. Aprove o Test Ride

1. Na lista de Test Rides
2. Clique em "Aprovar" no test ride pendente

### 5. Verifique os logs

No console você verá:

```
✅ Notificação enviada via RabbitMQ: {...}
📩 ===== NOTIFICAÇÃO DE TEST RIDE APROVADO =====
🆔 Test Ride ID: 1
👤 Usuário: João Silva
...
```

---

## 🔍 Verificar se está funcionando

### Console da Aplicação

Deve mostrar:

- ✅ Producer enviando mensagem
- 📩 Consumer recebendo mensagem

### Painel RabbitMQ (se não for mock)

1. Acesse: http://localhost:15672 (ou CloudAMQP dashboard)
2. Vá em "Queues"
3. Veja a fila: `autottu-testride-fila`
4. Verifique mensagens processadas

---

## ❌ Problemas Comuns

### Erro: Connection refused

**Solução:** RabbitMQ não está rodando

```powershell
# Windows
net start RabbitMQ

# Ou
cd "C:\Program Files\RabbitMQ Server\rabbitmq_server-*\sbin"
.\rabbitmq-server.bat
```

### Erro: Access Refused

**Solução:** Credenciais erradas. Use `guest/guest` para local.

### Erro: Port 5672 already in use

**Solução:** Outra instância está rodando

```powershell
# Parar serviço
net stop RabbitMQ

# Reiniciar
net start RabbitMQ
```

### Modo Mock não funciona

**Solução:** Verifique se adicionou no application.properties:

```properties
spring.profiles.active=mock
```

---

## 📊 Comparação das Opções

| Opção         | Instalação | Internet | Produção    | Facilidade |
| ------------- | ---------- | -------- | ----------- | ---------- |
| **Mock**      | ❌ Não     | ❌ Não   | ❌ Não      | ⭐⭐⭐⭐⭐ |
| **CloudAMQP** | ❌ Não     | ✅ Sim   | ⚠️ Limitado | ⭐⭐⭐⭐   |
| **Local**     | ✅ Sim     | ❌ Não   | ✅ Sim      | ⭐⭐⭐     |

---

## 🎯 Recomendação

**Para apresentação rápida:** Use o **Modo Mock**

**Para testar de verdade:** Use **CloudAMQP**

**Para ambiente produção:** Instale **Localmente** ou use Docker

---

## 📚 Documentação Adicional

- RabbitMQ: https://www.rabbitmq.com/tutorials/tutorial-one-java.html
- Spring AMQP: https://spring.io/projects/spring-amqp
- CloudAMQP: https://www.cloudamqp.com/docs/index.html
