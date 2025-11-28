# 📘 **DOCUMENTAÇÃO OFICIAL — Padrão DTO + SERVICE no Spring Boot**

## Guia de boas práticas aplicado no projeto _Bestiário_

### *Autor: Yuri Abe*
#### *Documentação e projeto realizado com auxílio de um agente de IA para análise de debugs e implementação de boas práticas durante a refatoração para conseguir usar as camadas DTO e Service.*

---

# 🧩 1. Introdução

Durante o desenvolvimento do projeto _Bestiário_, realizamos uma jornada importante de refatoração, cujo objetivo principal foi:

✔ Separar responsabilidades
✔ Reduzir acoplamento
✔ Facilitar manutenção futura
✔ Padronizar fluxos de CRUD
✔ Evitar exposição direta de Models para a camada Web
✔ Preparar o sistema para usar AJAX, modais, validações, etc.

O resultado final foi a adoção do padrão:

```
Controller → DTO → Service → Repository → Model → DB
```

Esse padrão é considerado **a arquitetura mais limpa e comum em projetos modernos Spring Boot**, oferecendo excelente modularidade e testabilidade.

---

# 🧱 2. Problemas Identificados no Código Original

Antes da refatoração, seu código apresentava:

### ❌ Controllers carregando regras de negócio

### ❌ Models indo direto para a view

### ❌ Falta de uma camada clara para conversões

### ❌ Diferenças entre o padrão usado em Jogos e Inimigos

### ❌ Dificuldade de estender funcionalidades (ex.: futuramente AJAX)

A refatoração corrige tudo isso.

---

# 📐 3. Arquitetura Aplicada Após a Refatoração

A estrutura final segue este padrão:

```
controller/
    JogoController.java
    InimigoController.java

service/
    JogoService.java
    InimigoService.java

dto/
    JogoDTO.java
    InimigoDTO.java

model/
    JogoModel.java
    InimigoModel.java

repository/
    JogoRepository.java
    InimigoRepository.java
```

---

# 🎯 4. Objetivo de cada camada

## 🔵 **DTO (Data Transfer Object)**

Representa os dados que entram e saem do Controller.

**Não possui regras de negócio**
**Não possui lógica de persistência**
**Serve apenas para transportar dados entre camadas**

Os DTOs:

- evitam expor entidades diretamente
- previnem ciclos de serialização
- trazem dados já formatados para o front-end
- permitem isolar campos sensíveis

Exemplo:

```java
public class JogoDTO {
    private Long id;
    private String titulo;
    private String genero;
    private String estudio;
}
```

---

## 🟣 **Service (Regra de Negócio)**

Responsável por:

✔ Validar
✔ Processar regras de negócio
✔ Consultar/Alterar dados no DB via Repository
✔ Converter entre Model ↔ DTO
✔ Lançar exceções adequadas
✔ Evitar duplicação de lógica nos controllers

Exemplo do fluxo:

```java
public JogoDTO save(JogoDTO dto) {
    JogoModel model = toModel(dto);
    JogoModel saved = jogoRepository.save(model);
    return toDTO(saved);
}
```

---

## 🟠 **Controller (Camada Web)**

Controle das rotas e interação com o usuário.

O controller deve:

✔ Ser fino
✔ Chamar métodos do service
✔ Tratar erros leves
✔ Retornar views ou JSON
✔ Preparar DTOs para as páginas Thymeleaf

Ele NÃO deve:

❌ acessar diretamente o banco
❌ fazer regras de negócio
❌ fazer validações pesadas
❌ converter Model ↔ DTO

Exemplo da controller final:

```java
@PostMapping
public String save(@Valid @ModelAttribute("jogo") JogoDTO jogoDTO,
                   RedirectAttributes redirectAttributes) {

    jogoService.save(jogoDTO);
    redirectAttributes.addFlashAttribute("message", "Jogo salvo!");
    return "redirect:/jogos";
}
```

---

# 🔧 5. Conversões toDTO() e toModel()

A conversão entre camadas é um dos pontos fundamentais da arquitetura.

### Converte a entidade do banco para DTO:

```java
private JogoDTO toDTO(JogoModel model) {
    JogoDTO dto = new JogoDTO();
    dto.setId(model.getId());
    dto.setTitulo(model.getTitulo());
    dto.setGenero(model.getGenero());
    dto.setEstudio(model.getEstudio());
    return dto;
}
```

### Converte o DTO da View para entidade JPA:

```java
private JogoModel toModel(JogoDTO dto) {
    JogoModel model = new JogoModel();
    model.setId(dto.getId());
    model.setTitulo(dto.getTitulo());
    model.setGenero(dto.getGenero());
    model.setEstudio(dto.getEstudio());
    return model;
}
```

---

# 🧪 6. Fluxo Completo de um CRUD com DTO + Service

Abaixo o fluxo consolidado e padronizado que você poderá reutilizar em qualquer projeto.

---

### ▶ CREATE (Form → Controller → Service → Repository → DB)

1. O usuário abre a página `/create`
2. Controller envia um DTO vazio para a view
3. Usuário preenche formulário
4. Controller recebe DTO via `@ModelAttribute`
5. Controller chama `service.save(dto)`
6. Service converte para Model e salva no DB
7. Retorna DTO atualizado
8. Redirecionamento com mensagem de sucesso

---

### ▶ READ (Lista ou detalhe)

1. Controller chama `service.findAll()`
2. Service busca Model e converte para DTO
3. Controller repassa DTOs para a view

---

### ▶ UPDATE

1. Usuário entra na página `/edit`
2. Controller envia DTO populado para o formulário
3. Usuário altera dados
4. Controller chama `service.update(id, dto)`
5. Service busca o Model original
6. Atualiza os campos
7. Salva no repositório
8. Retorna DTO atualizado

---

### ▶ DELETE

1. Controller recebe o `id`
2. Chama `service.delete(id)`
3. Service faz validação
4. Caso permitido → exclui
5. Caso proibido → lança exceção ou retorna erro

---

# 🚨 7. Tratamento correto de Regras de Negócio

Durante a refatoração, implementamos uma regra importante:

### ❗ "Não permitir excluir um jogo com inimigos associados"

Isso foi movido para o **Service**, não para a Controller.

Boa prática aplicada:

- Service faz a verificação
- Controller exibe uma mensagem amigável via `RedirectAttributes`
- O sistema continua estável

Exemplo:

```java
if (jogo.getInimigos().size() > 0) {
    throw new IllegalStateException("Não é possível excluir jogos com inimigos associados");
}
```

---

# 🧹 8. Benefícios atingidos após a refatoração

### ✔ Código mais limpo

### ✔ Menos acoplamento

### ✔ Mais fácil de testar

### ✔ Melhor preparado para REST + AJAX

### ✔ Fluxo consistente entre Jogos e Inimigos

### ✔ Controllers leves

### ✔ Regras centralizadas

### ✔ Validações claras

### ✔ Erros tratados corretamente

### ✔ Views mais simples

Esse padrão é usado em:

- sistemas bancários
- ERPs
- lojas virtuais
- APIs empresariais
- plataformas de cadastro
- aplicações com múltiplos módulos

Ou seja: **é uma referência real para qualquer projeto profissional**.

---

# 🚀 9. Conclusão

Você agora possui:

- Um código limpo
- Arquitetura profissional
- Controladores enxutos
- Services reutilizáveis
- DTOs seguros
- Regras de negócio isoladas
- Views organizadas
- Validação consistente

E principalmente:

👉 Um **guia completo e reutilizável** para aplicar esse padrão em futuros projetos.