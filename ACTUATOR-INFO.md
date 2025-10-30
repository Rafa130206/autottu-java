# 📊 Spring Boot Actuator - AutoTTU

## 📌 O que é o Spring Boot Actuator?

O **Spring Boot Actuator** fornece endpoints prontos para **monitoramento e gerenciamento** da aplicação. Ele expõe métricas importantes como uso de CPU, memória, status de saúde, e muito mais.

---

## ✅ Configuração no AutoTTU

### 1️⃣ **Dependência no `pom.xml`**

```xml
<!-- Spring Boot Actuator (monitoramento e métricas) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### 2️⃣ **Configuração no `application.properties`**

```properties
# Configurações do Spring Boot Actuator
# Expõe todos os endpoints do Actuator via web
management.endpoints.web.exposure.include=*
server.port=8080

# Desabilita os health checks do RabbitMQ e Kafka
# (necessário quando estão em modo mock ou não instalados)
management.health.rabbit.enabled=false
management.health.kafka.enabled=false

# Mostra detalhes do health check (opcional, útil para debug)
management.endpoint.health.show-details=always
```

> **📝 Nota Importante:** Os health checks do RabbitMQ e Kafka foram desabilitados porque a aplicação está rodando em **modo mock** (sem RabbitMQ/Kafka instalados). Quando você tiver RabbitMQ e Kafka rodando, você pode remover essas linhas para habilitar os health checks novamente.

---

## 🎯 Como Acessar o Actuator

### **Opção 1: Via Interface Gráfica (Recomendado)**

1. Faça login na aplicação como **ADMIN**
2. No menu principal, clique no card **"Telemetria"**
3. Visualize as métricas em tempo real:
   - ✅ Status da aplicação
   - 🖥️ Uso da CPU (%)
   - 💾 Memória JVM utilizada (MB)
   - 📦 Memória JVM máxima (GB)

**URL:** `http://localhost:8080/telemetria`

---

### **Opção 2: Via Endpoints REST (Direto)**

Acesse os endpoints diretamente no navegador ou via Postman:

#### 📋 **Principais Endpoints:**

| Endpoint                             | Descrição                                | URL                                                     |
| ------------------------------------ | ---------------------------------------- | ------------------------------------------------------- |
| `/actuator`                          | Lista todos os endpoints disponíveis     | http://localhost:8080/actuator                          |
| `/actuator/health`                   | Status de saúde da aplicação             | http://localhost:8080/actuator/health                   |
| `/actuator/metrics`                  | Lista de todas as métricas disponíveis   | http://localhost:8080/actuator/metrics                  |
| `/actuator/metrics/system.cpu.usage` | Uso da CPU                               | http://localhost:8080/actuator/metrics/system.cpu.usage |
| `/actuator/metrics/jvm.memory.used`  | Memória JVM utilizada                    | http://localhost:8080/actuator/metrics/jvm.memory.used  |
| `/actuator/metrics/jvm.memory.max`   | Memória JVM máxima                       | http://localhost:8080/actuator/metrics/jvm.memory.max   |
| `/actuator/env`                      | Variáveis de ambiente                    | http://localhost:8080/actuator/env                      |
| `/actuator/info`                     | Informações da aplicação                 | http://localhost:8080/actuator/info                     |
| `/actuator/beans`                    | Lista de todos os beans Spring           | http://localhost:8080/actuator/beans                    |
| `/actuator/mappings`                 | Lista de todos os endpoints da aplicação | http://localhost:8080/actuator/mappings                 |

---

## 🔍 Exemplo de Resposta JSON

### `/actuator/health`

```json
{
  "status": "UP"
}
```

### `/actuator/metrics/system.cpu.usage`

```json
{
  "name": "system.cpu.usage",
  "description": "The recent CPU usage of the system",
  "baseUnit": null,
  "measurements": [
    {
      "statistic": "VALUE",
      "value": 0.15234567890123456
    }
  ]
}
```

---

## 🛠️ Testando o Actuator

### **1. Inicie a aplicação:**

```bash
mvn spring-boot:run
```

### **2. Acesse a interface de Telemetria:**

**URL:** http://localhost:8080/telemetria

**Credenciais de ADMIN padrão:**

- **Usuário:** `admin`
- **Senha:** `admin123`

### **3. Ou acesse diretamente os endpoints REST:**

```bash
# Ver status de saúde
curl http://localhost:8080/actuator/health

# Ver todas as métricas disponíveis
curl http://localhost:8080/actuator/metrics

# Ver uso da CPU
curl http://localhost:8080/actuator/metrics/system.cpu.usage
```

---

## 📊 Métricas Disponíveis (Exemplos)

O Actuator fornece **dezenas de métricas** prontas. Algumas das mais úteis:

### **Sistema:**

- `system.cpu.usage` - Uso da CPU
- `system.cpu.count` - Número de CPUs
- `disk.free` - Espaço livre em disco
- `disk.total` - Espaço total em disco

### **JVM:**

- `jvm.memory.used` - Memória utilizada
- `jvm.memory.max` - Memória máxima
- `jvm.threads.live` - Threads ativas
- `jvm.gc.pause` - Pausas do Garbage Collector

### **Aplicação:**

- `http.server.requests` - Métricas de requisições HTTP
- `jdbc.connections.active` - Conexões JDBC ativas
- `hikaricp.connections.active` - Conexões HikariCP ativas

---

## 🎨 Interface Gráfica Personalizada

O AutoTTU possui uma interface gráfica personalizada (`/telemetria`) que:

✅ Exibe as métricas mais importantes de forma visual  
✅ Atualiza em tempo real  
✅ Está integrada ao sistema de internacionalização (PT 🇧🇷 / EN 🇺🇸)  
✅ Possui design moderno com Bootstrap 5 e ícones  
✅ Restringe acesso apenas para usuários **ADMIN**

---

## 🔒 Segurança

**⚠️ IMPORTANTE em Produção:**

Por padrão, o AutoTTU expõe **TODOS** os endpoints do Actuator (`management.endpoints.web.exposure.include=*`).

Em **produção**, considere:

1. **Restringir endpoints expostos:**

```properties
# Expor apenas health e info
management.endpoints.web.exposure.include=health,info
```

2. **Usar autenticação:**

```properties
# Configurar Spring Security para proteger endpoints
management.endpoints.web.base-path=/actuator
```

3. **Usar uma porta diferente:**

```properties
# Expor Actuator em porta separada
management.server.port=9090
```

---

## 🔧 Troubleshooting

### ❌ Problema: Status "DOWN" (503 Service Unavailable)

**Sintoma:**

```
org.springframework.web.client.HttpServerErrorException$ServiceUnavailable: 503
"status":"DOWN"
```

**Causa:**
Os health checks do RabbitMQ e/ou Kafka estão falhando porque esses serviços não estão rodando (aplicação em modo mock).

**Solução:**
Desabilite os health checks no `application.properties`:

```properties
management.health.rabbit.enabled=false
management.health.kafka.enabled=false
```

**✅ Após aplicar a solução, reinicie a aplicação e o status mudará para "UP".**

---

### ⚠️ Problema: Página de Telemetria mostra "N/A"

**Sintoma:**
A página `/telemetria` carrega, mas todas as métricas aparecem como "N/A".

**Causa:**
O ActuatorController não conseguiu acessar os endpoints do Actuator.

**Solução:**

1. Verifique se o Actuator está ativo: http://localhost:8080/actuator
2. Verifique se a porta está correta (padrão: 8080)
3. Verifique os logs da aplicação para mensagens de erro

---

## 📚 Documentação Oficial

- [Spring Boot Actuator Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Production-ready Features](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints)

---

## 🎯 Resumo

✅ **Dependência adicionada** ao `pom.xml`  
✅ **Configuração** no `application.properties`  
✅ **Controller personalizado** (`ActuatorController.java`)  
✅ **Interface gráfica** (`telemetria.html`)  
✅ **Internacionalização** (PT/EN)  
✅ **Integração** com o menu principal (apenas ADMIN)

**Acesso rápido:** http://localhost:8080/telemetria (após login como ADMIN)

---

**🚀 Pronto para monitorar sua aplicação em tempo real!**
