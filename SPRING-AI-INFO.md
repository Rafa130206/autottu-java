# 🤖 Spring AI - AutoTTU

## 📌 O que é o Spring AI?

O **Spring AI** é um framework que integra modelos de Inteligência Artificial (como GPT da OpenAI) diretamente em aplicações Spring Boot. Ele permite criar funcionalidades inteligentes sem complexidade, usando uma API simples e integrada ao ecossistema Spring.

### ✨ Benefícios:

- 🧠 **Inteligência Integrada**: IA nativa na sua aplicação
- 🎯 **Fácil de Usar**: API simples e familiar para desenvolvedores Spring
- 💬 **Assistência Contextual**: Respostas especializadas no domínio de motos
- ⚡ **Alta Performance**: Integração otimizada com provedores de IA
- 🔄 **Flexível**: Suporta diferentes modelos (OpenAI, Hugging Face, etc.)

---

## ✅ Configuração no AutoTTU

### 1️⃣ **Dependências no `pom.xml`**

```xml
<properties>
    <spring-ai.version>0.8.1</spring-ai.version>
</properties>

<repositories>
    <repository>
        <id>spring-milestones</id>
        <name>Spring Milestones</name>
        <url>https://repo.spring.io/milestone</url>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
</repositories>

<dependencies>
    <!-- Spring AI OpenAI (inteligência artificial) -->
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
    </dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-bom</artifactId>
            <version>${spring-ai.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 2️⃣ **Configuração no `application.properties`**

```properties
# Configurações do Spring AI (OpenAI)
# IMPORTANTE: Substitua pela sua própria chave API da OpenAI em: https://platform.openai.com/api-keys
spring.ai.openai.api-key=YOUR_OPENAI_API_KEY_HERE
spring.autoconfigure.exclude=org.springframework.cloud.function.context.config.ContextFunctionCatalogAutoConfiguration
```

> **⚠️ IMPORTANTE:** Nunca comite sua chave API no Git! Use variáveis de ambiente ou arquivos de configuração locais.

### 3️⃣ **Obtendo sua API Key da OpenAI**

1. Acesse [https://platform.openai.com/](https://platform.openai.com/)
2. Crie uma conta (se não tiver)
3. Vá para **API Keys**: [https://platform.openai.com/api-keys](https://platform.openai.com/api-keys)
4. Clique em **"Create new secret key"**
5. Copie a chave e cole no `application.properties`

> 💡 **Dica:** A OpenAI oferece créditos gratuitos para novos usuários. Consulte os preços em [https://openai.com/pricing](https://openai.com/pricing)

---

## 🎯 Funcionalidades Implementadas

O AutoTTU possui 6 funcionalidades inteligentes baseadas em IA:

### 1. 💬 **Assistente Virtual**

**Descrição:** Chatbot que responde qualquer pergunta sobre motos, manutenção, segurança, modelos, etc.

**Como usar:**

- Acesse: `/ia/assistente`
- Digite sua pergunta
- Receba resposta contextual e detalhada

**Exemplos de perguntas:**

- "Qual é a diferença entre uma moto naked e uma touring?"
- "Com que frequência devo trocar o óleo da moto?"
- "Quais são os equipamentos de segurança essenciais?"
- "Honda CB 500X ou Yamaha MT-03: qual é melhor?"

---

### 2. ⭐ **Recomendação de Motos**

**Descrição:** Sistema inteligente que recomenda a moto ideal baseado no perfil, experiência e uso do usuário.

**Como usar:**

- Acesse: `/ia/recomendar`
- Preencha:
  - **Perfil**: Iniciante, Intermediário, Avançado
  - **Experiência**: Tempo de experiência
  - **Uso pretendido**: Cidade, estrada, ambos, esportivo, etc.
- Receba recomendações personalizadas

**O que a IA fornece:**

1. Tipo de moto recomendado (street, esportiva, custom, touring)
2. Cilindrada ideal
3. 2-3 modelos específicos sugeridos
4. Pontos importantes a considerar
5. Dicas para iniciantes (se aplicável)

---

### 3. 🔧 **Análise Preditiva de Manutenção**

**Descrição:** IA analisa dados da moto e prevê quando ela precisará de manutenção.

**Como usar:**

- Acesse: `/ia/manutencao`
- Forneça:
  - **Modelo da moto**
  - **Quilometragem atual**
  - **Data da última manutenção**
  - **Histórico de problemas** (opcional)
- Receba análise preditiva

**O que a IA fornece:**

1. Itens que provavelmente precisam de manutenção
2. Urgência (baixa/média/alta)
3. Estimativa de quilometragem para próxima revisão
4. Custos aproximados
5. Recomendações preventivas

---

### 4. 🏍️ **Assistente para Test Ride**

**Descrição:** Guia inteligente que prepara o usuário para fazer um test ride de moto.

**Como usar:**

- Acesse: `/ia/testride`
- Informe:
  - **Modelo da moto** a ser testada
  - **Seu nível de experiência**
- Receba assistência completa

**O que a IA fornece:**

1. O que observar durante o test ride
2. Aspectos técnicos importantes a testar
3. Perguntas a fazer ao vendedor
4. Sinais de alerta (red flags)
5. Checklist de segurança
6. Dicas específicas para o seu nível de experiência

---

### 5. 📊 **Comparador de Motos**

**Descrição:** Compara 2 ou mais modelos de motos com análise detalhada.

**Como usar:**

- Acesse: `/ia/comparar`
- Digite os modelos separados por vírgula
  - Ex: `Honda CB 500X, Yamaha MT-03, Kawasaki Z400`
- Receba comparação completa

**O que a IA fornece:**

1. Ficha técnica resumida de cada moto
2. Prós e contras de cada modelo
3. Comparação de desempenho
4. Comparação de custo-benefício
5. Recomendação por perfil de usuário
6. Veredicto final

---

### 6. 📝 **Gerador de Descrição**

**Descrição:** Gera descrições comerciais atrativas para motos automaticamente.

**Como usar:**

- Acesse: `/ia/gerar-descricao`
- Forneça:
  - **Marca**
  - **Modelo**
  - **Status** (nova, usada, seminova)
- Receba descrição profissional

**O que a IA fornece:**

1. Características principais
2. Público-alvo
3. Diferenciais
4. Aplicações ideais
5. Texto com tom profissional e acessível (até 150 palavras)

---

## 🎨 Arquitetura da Implementação

```
┌─────────────────────────────────────────────────────────────┐
│                        FRONT-END                            │
│  (Thymeleaf Templates - /templates/ia/*.html)              │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────────┐
│                     CONTROLLER                              │
│  SpringAIController.java                                    │
│  - /ia (index)                                              │
│  - /ia/assistente                                           │
│  - /ia/recomendar                                           │
│  - /ia/manutencao                                           │
│  - /ia/testride                                             │
│  - /ia/comparar                                             │
│  - /ia/gerar-descricao                                      │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────────┐
│                       SERVICE                               │
│  SpringAIService.java                                       │
│  - perguntarIA()                                            │
│  - recomendarMoto()                                         │
│  - analisarManutencao()                                     │
│  - assistenteTestRide()                                     │
│  - compararMotos()                                          │
│  - gerarDescricaoMoto()                                     │
│  - responderDuvidaMoto()                                    │
│  - resumirFeedbacks()                                       │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────────┐
│                   SPRING AI CLIENT                          │
│  OpenAiChatClient                                           │
│  (Injetado automaticamente pelo Spring Boot Starter)       │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────────┐
│                     OPENAI API                              │
│  (GPT-3.5 / GPT-4)                                          │
└─────────────────────────────────────────────────────────────┘
```

---

## 💻 Exemplo de Código

### **Serviço Spring AI**

```java
@Service
public class SpringAIService {

    @Autowired
    private OpenAiChatClient chatClient;

    public String recomendarMoto(String perfil, String experiencia, String uso) {
        String prompt = String.format(
            "Com base no seguinte perfil, recomende uma moto ideal:\n" +
            "- Perfil: %s\n" +
            "- Experiência: %s\n" +
            "- Uso pretendido: %s\n\n" +
            "Forneça uma recomendação detalhada incluindo:\n" +
            "1. Tipo de moto recomendado\n" +
            "2. Cilindrada ideal\n" +
            "3. 2-3 modelos sugeridos",
            perfil, experiencia, uso
        );

        return chatClient.call(prompt);
    }
}
```

### **Controller**

```java
@Controller
@RequestMapping("/ia")
public class SpringAIController {

    @Autowired
    private SpringAIService springAIService;

    @PostMapping("/recomendar")
    public ModelAndView recomendarMoto(
            @RequestParam String perfil,
            @RequestParam String experiencia,
            @RequestParam String uso) {

        ModelAndView mv = new ModelAndView("ia/recomendar");
        String recomendacao = springAIService.recomendarMoto(perfil, experiencia, uso);
        mv.addObject("recomendacao", recomendacao);
        return mv;
    }
}
```

---

## 🚀 Como Usar

### **1. Configure a API Key**

Edite o arquivo `application.properties`:

```properties
spring.ai.openai.api-key=sk-proj-XXXXXXXXXXXXXXXXXXXXXXX
```

### **2. Acesse o Menu IA**

1. Faça login no AutoTTU
2. No menu principal, clique em **"Assistente IA"** (card com ícone de robô 🤖)
3. Escolha a funcionalidade desejada

### **3. Use as Funcionalidades**

- **Assistente Virtual**: Faça perguntas gerais
- **Recomendação**: Encontre a moto ideal
- **Manutenção**: Análise preditiva
- **Test Ride**: Prepare-se para testar motos
- **Comparador**: Compare modelos
- **Gerador**: Crie descrições profissionais

---

## 🎓 Boas Práticas

### ✅ **DO (Faça):**

1. **Use prompts claros e específicos** para melhores respostas
2. **Forneça contexto adequado** para análises precisas
3. **Monitore os custos** da API OpenAI (cada chamada tem custo)
4. **Trate erros adequadamente** (API pode estar indisponível)
5. **Cache respostas frequentes** para reduzir custos

### ❌ **DON'T (Não faça):**

1. **Não exponha sua API Key** (nunca comite no Git)
2. **Não faça requisições excessivas** (custos acumulam)
3. **Não confie 100% nas respostas** (IA pode errar)
4. **Não use em produção sem rate limiting**
5. **Não armazene dados sensíveis** sem criptografia

---

## 💰 Custos e Limites

### **Modelo GPT-3.5 Turbo** (Padrão)

- **Custo:** ~$0.002 por 1K tokens
- **Performance:** Rápida
- **Qualidade:** Boa para maioria dos casos

### **Modelo GPT-4** (Opcional)

- **Custo:** ~$0.03 por 1K tokens
- **Performance:** Mais lenta
- **Qualidade:** Excelente para tarefas complexas

### **Dicas para Reduzir Custos:**

1. Cache respostas frequentes
2. Limite o tamanho das respostas
3. Use GPT-3.5 sempre que possível
4. Implemente rate limiting
5. Monitore uso com dashboards da OpenAI

---

## 🔧 Troubleshooting

### ❓ **Problema: Erro de API Key Inválida**

**Sintoma:**

```
Erro ao comunicar com a IA: Invalid API Key
```

**Solução:**

1. Verifique se a chave está correta em `application.properties`
2. Confirme que a chave é válida em [https://platform.openai.com/api-keys](https://platform.openai.com/api-keys)
3. Verifique se há créditos disponíveis na sua conta OpenAI

---

### ❓ **Problema: Timeout ou Resposta Lenta**

**Sintoma:** A IA demora muito para responder ou retorna timeout.

**Solução:**

1. Verifique sua conexão com a internet
2. Simplifique os prompts (perguntas mais curtas)
3. Considere usar GPT-3.5 ao invés de GPT-4
4. Implemente timeout nas requisições HTTP

---

### ❓ **Problema: Respostas Imprecisas ou Genéricas**

**Sintoma:** A IA não responde adequadamente ou fornece informações genéricas.

**Solução:**

1. **Melhore o prompt**: Seja mais específico e detalhado
2. **Adicione contexto**: Forneça mais informações na pergunta
3. **Use exemplos**: Mostre o formato de resposta desejado

**Exemplo de prompt melhorado:**

❌ **Ruim:**

```
"Me fale sobre motos"
```

✅ **Bom:**

```
"Explique as diferenças técnicas entre motos naked e sport touring,
incluindo ergonomia, motor, peso e casos de uso ideais.
Use exemplos de modelos populares no Brasil."
```

---

## 📊 Monitoramento e Métricas

### **Dashboard da OpenAI**

Monitore seu uso em tempo real:

- Acesse: [https://platform.openai.com/usage](https://platform.openai.com/usage)
- Visualize:
  - Número de requisições
  - Tokens consumidos
  - Custos acumulados
  - Gráficos de uso

### **Logs da Aplicação**

O serviço registra informações úteis:

```java
System.out.println("🤖 Chamando IA para: " + pergunta);
System.out.println("💰 Tokens estimados: " + pergunta.length() / 4);
```

---

## 🌐 Modelos Alternativos

Embora o AutoTTU use OpenAI por padrão, o Spring AI suporta outros provedores:

### **Azure OpenAI**

```properties
spring.ai.azure.openai.api-key=YOUR_AZURE_KEY
spring.ai.azure.openai.endpoint=YOUR_ENDPOINT
```

### **Hugging Face**

```properties
spring.ai.huggingface.api-key=YOUR_HF_KEY
```

### **Ollama (Local - Gratuito!)**

```properties
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.model=llama2
```

> **💡 Dica:** Ollama permite rodar modelos localmente sem custos de API!

---

## 📚 Recursos Adicionais

- **Spring AI Docs:** [https://docs.spring.io/spring-ai/reference/](https://docs.spring.io/spring-ai/reference/)
- **OpenAI API Docs:** [https://platform.openai.com/docs](https://platform.openai.com/docs)
- **Spring AI GitHub:** [https://github.com/spring-projects/spring-ai](https://github.com/spring-projects/spring-ai)
- **Comunidade Spring AI:** [https://discord.gg/spring](https://discord.gg/spring)

---

## ✨ Conclusão

O Spring AI foi implementado com sucesso no AutoTTU, oferecendo **6 funcionalidades inteligentes** que transformam a experiência do usuário:

1. ✅ Assistente Virtual
2. ✅ Recomendação de Motos
3. ✅ Análise Preditiva de Manutenção
4. ✅ Assistente para Test Ride
5. ✅ Comparador de Motos
6. ✅ Gerador de Descrição

### **Próximos Passos:**

- 🔑 Obtenha sua API Key da OpenAI
- 📝 Configure no `application.properties`
- 🚀 Acesse `/ia` e explore as funcionalidades
- 💡 Experimente diferentes perguntas e cenários

---

**🤖 AutoTTU agora é inteligente com Spring AI!**
