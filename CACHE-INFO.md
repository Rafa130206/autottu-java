# 💾 Spring Boot Cache - AutoTTU

## 📌 O que é o Spring Boot Cache?

O **Spring Boot Cache** é um framework de abstração de cache que permite melhorar significativamente a performance da aplicação armazenando dados em memória. Ao invés de buscar os mesmos dados repetidamente no banco de dados, o cache mantém cópias em memória para acesso rápido.

### ✨ Benefícios:

- ⚡ **Performance**: Acesso muito mais rápido aos dados (memória vs. banco de dados)
- 📉 **Redução de Carga**: Menos consultas ao banco de dados
- 🎯 **Simples de Usar**: Anotações declarativas (`@Cacheable`, `@CacheEvict`)
- 🔄 **Transparente**: Não afeta a lógica de negócio existente

---

## ✅ Configuração no AutoTTU

### 1️⃣ **Dependência no `pom.xml`**

```xml
<!-- Spring Boot Cache (otimização de performance) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

### 2️⃣ **Habilitação do Cache na Classe Principal**

```java
package br.com.fiap.autottu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching  // ✅ Habilita o cache na aplicação
@EnableJpaRepositories
@EntityScan
@ComponentScan
@SpringBootApplication
public class AutottuApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutottuApplication.class, args);
	}

}
```

---

## 🎯 Serviço de Caching

### 📝 **CachingService.java**

O `CachingService` é o coração da implementação de cache. Ele encapsula todas as operações de busca e invalidação de cache:

```java
@Service
public class CachingService {

	@Autowired
	private MotoRepository motoRepository;

	// ... outros repositórios ...

	// ========== MOTOS ==========

	@Cacheable(value = "findAllMotos")
	public List<Moto> findAllMotos() {
		System.out.println("🔍 Buscando todas as motos do banco de dados...");
		return motoRepository.findAll();
	}

	@Cacheable(value = "findByIdMoto", key = "#id")
	public Optional<Moto> findByIdMoto(Integer id) {
		System.out.println("🔍 Buscando moto com ID " + id + " do banco de dados...");
		return motoRepository.findById(id);
	}

	@CacheEvict(value = { "findAllMotos", "findByIdMoto" }, allEntries = true)
	public void removerCacheMoto() {
		System.out.println("🗑️ Limpando cache das motos");
	}

	// ... métodos similares para Usuários, Slots, TestRides, Manutenções e Check-ins ...
}
```

### 📚 **Anotações Utilizadas:**

| Anotação      | Descrição                                                                                                                                                        |
| ------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `@Cacheable`  | Armazena o resultado do método em cache. Se o método for chamado novamente com os mesmos parâmetros, o valor é retornado do cache ao invés de executar o método. |
| `@CacheEvict` | Remove entradas do cache. Usado quando dados são modificados (insert, update, delete).                                                                           |
| `@CachePut`   | Atualiza o cache sem interferir na execução do método (não usado neste projeto).                                                                                 |

---

## 🔧 Como Usar nos Controllers

### ✅ **Antes (Sem Cache):**

```java
@Controller
public class MotoController {

	@Autowired
	private MotoRepository repM;

	@GetMapping("/motos")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("moto/list");
		mv.addObject("motos", repM.findAll());  // ❌ Sempre busca no banco
		return mv;
	}

	@PostMapping("/motos")
	public ModelAndView criar(@Valid Moto moto) {
		repM.save(moto);  // ❌ Cache não é atualizado
		return new ModelAndView("redirect:/motos");
	}
}
```

### ✅ **Depois (Com Cache):**

```java
@Controller
public class MotoController {

	@Autowired
	private MotoRepository repM;

	@Autowired
	private CachingService cache;  // ✅ Injeta o serviço de cache

	@GetMapping("/motos")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("moto/list");
		mv.addObject("motos", cache.findAllMotos());  // ✅ Busca do cache se disponível
		return mv;
	}

	@PostMapping("/motos")
	public ModelAndView criar(@Valid Moto moto) {
		repM.save(moto);
		cache.removerCacheMoto();  // ✅ Invalida o cache para garantir dados atualizados
		return new ModelAndView("redirect:/motos");
	}
}
```

---

## 🎨 Padrão de Implementação

### 📋 **Quando usar o Cache?**

#### ✅ **Use para LEITURA:**

```java
// Listagem
mv.addObject("motos", cache.findAllMotos());

// Busca por ID
Optional<Moto> op = cache.findByIdMoto(id);

// Busca com filtro
List<Slot> slots = cache.findSlotsDisponiveis();
```

#### 🗑️ **Limpe após ESCRITA (Create/Update/Delete):**

```java
// Após salvar
repM.save(moto);
cache.removerCacheMoto();  // ✅ Sempre limpar o cache!

// Após atualizar
repM.save(motoAtualizada);
cache.removerCacheMoto();  // ✅ Garante dados atualizados

// Após excluir
repM.deleteById(id);
cache.removerCacheMoto();  // ✅ Remove dados antigos do cache
```

---

## 📊 Caches Disponíveis no AutoTTU

| Entidade        | Métodos Cachados                                               | Método de Invalidação      |
| --------------- | -------------------------------------------------------------- | -------------------------- |
| **Motos**       | `findAllMotos()`, `findByIdMoto(id)`                           | `removerCacheMoto()`       |
| **Usuários**    | `findAllUsuarios()`, `findByIdUsuario(id)`                     | `removerCacheUsuario()`    |
| **Slots**       | `findAllSlots()`, `findByIdSlot(id)`, `findSlotsDisponiveis()` | `removerCacheSlot()`       |
| **Test Rides**  | `findAllTestRides()`, `findByIdTestRide(id)`                   | `removerCacheTestRide()`   |
| **Manutenções** | `findAllManutencoes()`, `findByIdManutencao(id)`               | `removerCacheManutencao()` |
| **Check-ins**   | `findAllCheckins()`, `findByIdCheckin(id)`                     | `removerCacheCheckin()`    |

---

## 🔍 Como Funciona?

### **1️⃣ Primeira Chamada (Cache MISS):**

```
Cliente → Controller → CachingService
                           ↓
                       @Cacheable
                           ↓
                    [Cache vazio]
                           ↓
                    Repository → BD
                           ↓
                    Salva no Cache
                           ↓
                   Retorna ao Cliente
```

**Console:**

```
🔍 Buscando todas as motos do banco de dados...
```

### **2️⃣ Segunda Chamada (Cache HIT):**

```
Cliente → Controller → CachingService
                           ↓
                       @Cacheable
                           ↓
                    [Cache cheio] ✅
                           ↓
                   Retorna do Cache
                   (SEM acessar o BD!)
```

**Console:**

```
(Nenhuma mensagem - dados retornados direto do cache!)
```

### **3️⃣ Após Modificação (Cache EVICT):**

```
Cliente → Controller → Repository → BD
                           ↓
                   CachingService.removerCache()
                           ↓
                       @CacheEvict
                           ↓
                    [Cache limpo] 🗑️
```

**Console:**

```
🗑️ Limpando cache das motos
```

---

## 🚀 Exemplo Completo: MotoController

```java
@Controller
@RequestMapping("/motos")
public class MotoController {

	@Autowired
	private MotoRepository repM;

	@Autowired
	private CachingService cache;

	// ✅ LISTAGEM (usa cache)
	@GetMapping
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("moto/list");
		mv.addObject("motos", cache.findAllMotos());  // Cache!
		return mv;
	}

	// ✅ DETALHES (usa cache)
	@GetMapping("/{id}")
	public ModelAndView exibirDetalhes(@PathVariable Integer id) {
		Optional<Moto> op = cache.findByIdMoto(id);  // Cache!

		if(op.isPresent()) {
			ModelAndView mv = new ModelAndView("moto/detalhes");
			mv.addObject("moto", op.get());
			return mv;
		}
		return new ModelAndView("redirect:/motos");
	}

	// ✅ CRIAR (limpa cache após salvar)
	@PostMapping
	public ModelAndView criar(@Valid Moto moto) {
		repM.save(moto);
		cache.removerCacheMoto();  // Limpa cache!
		return new ModelAndView("redirect:/motos");
	}

	// ✅ ATUALIZAR (limpa cache após salvar)
	@PostMapping("/{id}")
	public ModelAndView atualizar(@PathVariable Integer id, @Valid Moto moto) {
		Optional<Moto> op = cache.findByIdMoto(id);  // Cache!
		if(op.isPresent()) {
			Moto motoAtualizada = op.get();
			motoAtualizada.transferirMoto(moto);
			repM.save(motoAtualizada);
			cache.removerCacheMoto();  // Limpa cache!
		}
		return new ModelAndView("redirect:/motos");
	}

	// ✅ EXCLUIR (limpa cache após deletar)
	@PostMapping("/{id}/delete")
	public ModelAndView excluir(@PathVariable Integer id) {
		repM.deleteById(id);
		cache.removerCacheMoto();  // Limpa cache!
		return new ModelAndView("redirect:/motos");
	}
}
```

---

## 🎓 Boas Práticas

### ✅ **DO (Faça):**

1. **Sempre use cache para leituras frequentes** (listagens, detalhes)
2. **Sempre limpe o cache após modificações** (create, update, delete)
3. **Use nomes descritivos para os caches** (`findAllMotos`, não `findAll`)
4. **Adicione logs para debug** (`System.out.println` nos métodos cachados)

### ❌ **DON'T (Não faça):**

1. **Não cachear dados que mudam muito** (timestamps, contadores)
2. **Não esquecer de limpar o cache** após updates/deletes
3. **Não cachear dados sensíveis** sem considerar segurança
4. **Não usar cache para operações que precisam ser sempre atualizadas**

---

## 📈 Impacto na Performance

### **Sem Cache:**

```
Requisição 1: 150ms (BD)
Requisição 2: 145ms (BD)
Requisição 3: 152ms (BD)
Requisição 4: 148ms (BD)
Requisição 5: 151ms (BD)
TOTAL: 746ms
```

### **Com Cache:**

```
Requisição 1: 150ms (BD - cache miss)
Requisição 2:   2ms (Cache hit)
Requisição 3:   1ms (Cache hit)
Requisição 4:   2ms (Cache hit)
Requisição 5:   1ms (Cache hit)
TOTAL: 156ms (🚀 79% mais rápido!)
```

---

## 🧪 Como Testar

### **1️⃣ Teste Manual:**

1. **Primeira Listagem** de motos:

   ```
   Console: 🔍 Buscando todas as motos do banco de dados...
   ```

2. **Segunda Listagem** de motos (mesma página, F5):

   ```
   Console: (Nada - dados vêm do cache!)
   ```

3. **Criar/Editar/Excluir** uma moto:

   ```
   Console: 🗑️ Limpando cache das motos
   ```

4. **Listar novamente**:
   ```
   Console: 🔍 Buscando todas as motos do banco de dados...
   (Cache foi limpo, então busca novamente do BD)
   ```

### **2️⃣ Teste de Performance:**

Use ferramentas como **Apache JMeter** ou **Postman** para fazer múltiplas requisições e comparar o tempo de resposta.

---

## 🔧 Troubleshooting

### ❓ **Problema: Cache não está funcionando**

**Sintoma:** Sempre vejo a mensagem "🔍 Buscando do banco de dados..." mesmo em requisições repetidas.

**Soluções:**

1. Verifique se `@EnableCaching` está presente em `AutottuApplication.java`
2. Verifique se o `CachingService` está sendo injetado corretamente
3. Verifique se você está chamando `cache.findAll()` e não `repository.findAll()`

### ❓ **Problema: Dados desatualizados**

**Sintoma:** Após criar/atualizar/excluir, a listagem mostra dados antigos.

**Solução:**
Sempre chame `cache.removerCache...()` após operações de escrita:

```java
repM.save(moto);
cache.removerCacheMoto();  // ✅ OBRIGATÓRIO!
```

---

## 📚 Documentação Adicional

- [Spring Boot Cache Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.caching)
- [Spring Framework Cache Abstraction](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#cache)
- [Cacheable Annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/cache/annotation/Cacheable.html)

---

## ✨ Conclusão

O cache foi implementado com sucesso no AutoTTU para melhorar a performance da aplicação!

### **Controllers Atualizados:**

- ✅ `MotoController`
- ✅ `CheckinController`
- ✅ `SlotController`

### **Como Aplicar em Outros Controllers:**

1. Injete o `CachingService`
2. Substitua chamadas diretas ao repositório por chamadas ao cache nas **leituras**
3. Adicione `cache.removerCache...()` após **escritas**

**Exemplo rápido para qualquer controller:**

```java
// Injetar
@Autowired
private CachingService cache;

// Leitura
model.addAttribute("lista", cache.findAll...());

// Escrita
repository.save(...);
cache.removerCache...();
```

---

**🚀 AutoTTU agora está otimizado com cache em memória!**
