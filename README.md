# Android Legacy Rewrite Challenge – Solução Android

## Sobre o desafio
Este projeto propõe a melhoria de um aplicativo existente que apresenta problemas comuns em projetos reais, como perda de estado, crashes relacionados a dados nulos e dificuldades de evolução da arquitetura. O objetivo é reestruturar o projeto para torná-lo mais robusto, escalável e preparado para mudanças futuras.

### Entre os requisitos esperados estão:
- Manter o estado da tela em mudanças de configuração; 
- Prevenir crashes causados por valores nulos e má gestão de ciclo de vida;
- Implementar cache local para otimizar a experiência do usuário;
- Criar uma arquitetura flexível para futuras alterações de lógica e apresentação;
- Garantir cobertura de testes automatizados, incluindo unitários e instrumentados;
- É permitido reorganizar pastas, módulos e dependências conforme necessário.

---

# Arquitetura e Decisões Técnicas

## 1. Arquitetura: MVVM + princípios da Clean Architecture
O projeto segue os princípios da Clean Architecture, dividindo o código em camadas de responsabilidade distintas. Isso promove um baixo acoplamento e torna o aplicativo mais testável, escalável e fácil de manter.

```
app/
├── data/
│   ├── local/           
│   ├── remote/          
│   ├── repository/      
│
├── domain/
│   ├── mapper/          
│   ├── model/           
│   ├── repository/      
│   ├── usecase/         
│
├── ui/
│   ├── user/
│   │   ├── adapter/     
│   │   ├── viewModel/   
│   ├── MainActivity     
│   ├── ViewState        
│
├── di/                  
│
├── MainApplication      

```

#### Sobre a estrutura
A arquitetura foi pensada para:

- Separar responsabilidades com clareza (princípios SOLID).
- Facilitar testes unitários e instrumentados, isolando regras de negócio, data sources e camada de apresentação.
- Permitir trocas fáceis na camada de dados (ex.: mudar Retrofit, trocar a database, etc.).
- Garantir que a camada de UI dependa apenas da camada de domínio — reduzindo crashes relacionados a lifecycle e campos nulos.
- Escalar o projeto conforme a aplicação crescer.

---

## 2. Build System: Migração de Groovy para Kotlin DSL (KTS)
Todo o build system foi migrado de **Groovy** para **Kotlin DSL**, garantindo:

- autocomplete inteligente no Android Studio;
- sintaxe mais segura;
- padronização com projetos modernos;
- melhor integração com versões recentes do Gradle.

Essa migração envolveu ajustes nos arquivos:
- `build.gradle.kts`
- `settings.gradle.kts`
- bloco de plugins
- gerenciamento de dependências

---

## 3. Persistência de Dados e Estratégia de Cache com Room
Implementei **Room** para armazenar localmente os usuários.

- **Estratégia de Cache-First:** Primeiro tenta buscar os dados no banco de dados local (Room). Se o cache estiver vazio, busca na API (Retrofit). Isso melhora a performance, a experiência do usuário e oferece funcionalidade offline básica. 
- **Fonte Única de Verdade (SSoT):** Os dados da API são sempre salvos no banco de dados local antes de serem enviados para as camadas superiores. Isso garante consistência em todo o aplicativo.

---

## 4. Separação de Modelos com o Padrão Mapper (DTO / Data Transfer Object)
Para reforçar o isolamento entre as camadas da arquitetura, foi implementado o padrão Mapper. Ele é responsável por converter (mapear) os modelos de dados.

- **Proteção das Camadas:** O Mapper impede que modelos da camada de dados (como objetos do Retrofit ou Room) "vazem" para a camada de domínio ou UI. Isso protege a aplicação contra mudanças na API.
- **Princípio da Responsabilidade Única:** A lógica de conversão fica centralizada, tornando o código mais limpo e fácil de manter.
- **Modelos Específicos:** Cada camada trabalha com seu próprio modelo (`User` na API, `UserDbModel` no banco de dados, `UserModel` no domínio/UI), otimizado para sua responsabilidade.

---

## 5. Operações Assíncronas com Coroutines
Coroutines foram a escolha principal para gerenciar operações assíncronas, como chamadas de rede e acesso ao banco de dados.

- **viewModelScope:** No ViewModel, as operações são lançadas dentro do viewModelScope. Isso integra as coroutines ao ciclo de vida do ViewModel, garantindo que qualquer operação em andamento seja automaticamente cancelada quando o ViewModel é destruído. Essa prática previne memory leaks e processamento desnecessário.
- **Funções suspend:** As camadas de Repository e UseCase utilizam suspend functions. Isso permite que elas executem operações de longa duração (como I/O de rede ou banco de dados) sem bloquear a thread principal, garantindo que a interface do usuário permaneça sempre responsiva.
- **Legibilidade:** O código assíncrono se torna sequencial e fácil de entender, especialmente com o uso de runCatching, que simplifica o tratamento de sucesso e erro de forma declarativa.

---

## 6. Padrão de Apresentação e Gerenciamento de Estado (MVVM)
A camada de apresentação utiliza o padrão MVVM para separar a lógica da UI da lógica de negócio.

- **ViewModel:** Preserva o estado da UI durante mudanças de configuração (como rotação de tela), evitando a perda de dados e chamadas de rede desnecessárias.
- **LiveData:** O ViewModel expõe os dados para a Activity através do LiveData, que é um observador consciente do ciclo de vida. Isso garante que a UI só seja atualizada quando estiver em um estado seguro (ativo), prevenindo crashes.
- **ViewState com Sealed Class:** Para garantir consistência, o LiveData emite uma `sealed class` `ViewState` que representa todos os estados possíveis da tela (`Loading`, `Success`, `Error`). Essa abordagem força o tratamento de todos os cenários na UI e evita estados contraditórios.

---

## 7. Injeção de Dependência com Koin
A injeção de dependência é gerenciada pelo **Koin**, uma biblioteca leve e pragmática para Kotlin.

- **DSL Declarativa:** A configuração é feita em puro Kotlin, tornando-a simples e legível.
- **Gerenciamento de Escopo:** Permite um controle claro do ciclo de vida dos objetos com `single`, `factory` e `viewModel`.
- **Integração com Android:** Facilita a injeção de `ViewModels` e outros componentes do Android, respeitando seus ciclos de vida.

---

## 8. Estratégia de Tratamento de Erros
Combinei:

- `runCatching` no ViewModel
- `try/catch` no Repository

Assim, nenhum erro estoura para a UI, evito crash e toda falha é tratada de forma segura.

---

# Testes

## Testes Unitários
Foram testados:
- ViewModel
- UseCase
- Mapper
- Repository

Ferramentas:
- JUnit
- MockK
- Coroutines Test

---

## Testes Instrumentados com Espresso
Testei:
- visibilidade das views conforme o estado
- estado de erro com exibição de Toast
- bind do ViewHolder

---

# Tecnologias Utilizadas

- **Kotlin**
- **MVVM**
- **Clean Architecture**
- **LiveData**
- **Coroutines**
- **Room Database**
- **Retrofit**
- **Koin**
- **JUnit + MockK + Espresso**
- **ViewBinding**
- **RecyclerView**
- **Gradle Kotlin DSL (KTS)**

---

# Padrões de Projeto (Design Patterns) Utilizados

- **Repository Pattern** 
- **Use Case (Interactor)**
- **Mapper Pattern**
- **Observer Pattern**
- **Singleton Pattern**
- **Dependency Injection (DI)**
- **State Pattern**
- **ViewHolder Pattern**

