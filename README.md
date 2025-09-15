# App de Tarefas Diárias - Android Kotlin

Um aplicativo Android simples e elegante para gerenciar tarefas diárias, desenvolvido em Kotlin nativo.

## Funcionalidades

-  Adicionar novas tarefas com título, descrição e prioridade
-  Marcar tarefas como concluídas/pendentes
-  Editar tarefas existentes
-  Excluir tarefas individuais
-  Limpar todas as tarefas concluídas
-  Sistema de prioridades (Alta, Média, Baixa) com cores
-  Persistência de dados com Room Database
-  Interface moderna com Material Design
-  Ordenação inteligente (pendentes primeiro, por prioridade e data)

## Tecnologias Utilizadas

- **Kotlin** - Linguagem principal
- **Room Database** - Persistência local
- **LiveData & ViewModel** - Arquitetura MVVM
- **Material Design Components** - Interface moderna
- **RecyclerView** - Lista de tarefas
- **ViewBinding** - Binding de views

## Estrutura do Projeto

```
app/
├── src/main/java/com/example/tarefasdiarias/
│   ├── data/
│   │   ├── Tarefa.kt              # Entidade da tarefa
│   │   ├── TarefaDao.kt           # Data Access Object
│   │   └── TarefaDatabase.kt      # Configuração do banco
│   ├── repository/
│   │   └── TarefaRepository.kt    # Repositório de dados
│   ├── viewmodel/
│   │   └── TarefaViewModel.kt     # ViewModel
│   ├── adapter/
│   │   └── TarefaAdapter.kt       # Adapter do RecyclerView
│   └── MainActivity.kt            # Activity principal
└── src/main/res/
    ├── layout/                    # Layouts XML
    ├── values/                    # Strings, cores, temas
    ├── drawable/                  # Ícones
    └── menu/                      # Menus
```

## Como Usar

1. **Importar no Android Studio:**
   - Abra o Android Studio
   - Selecione "Open an existing project"
   - Navegue até a pasta do projeto e selecione

2. **Sincronizar dependências:**
   - O Android Studio irá automaticamente sincronizar as dependências do Gradle

3. **Executar o app:**
   - Conecte um dispositivo Android ou inicie um emulador
   - Clique em "Run" ou pressione Shift+F10

## Funcionalidades Detalhadas

### Adicionar Tarefa
- Toque no botão flutuante (+)
- Preencha título (obrigatório), descrição e prioridade
- Toque em "Salvar"

### Gerenciar Tarefas
- **Marcar como concluída:** Toque no checkbox
- **Editar:** Toque na tarefa
- **Excluir:** Toque no ícone de lixeira
- **Limpar concluídas:** Menu → "Limpar concluídas"

### Sistema de Prioridades
- **Alta:** Vermelho (#F44336)
- **Média:** Laranja (#FF9800)  
- **Baixa:** Verde (#4CAF50)

## Requisitos

- Android Studio Arctic Fox ou superior
- Android SDK 24+ (Android 7.0)
- Kotlin 1.9.10+

## Arquitetura

O app segue o padrão **MVVM (Model-View-ViewModel)** recomendado pelo Google:

- **Model:** Entidades Room (Tarefa)
- **View:** Activities e Layouts
- **ViewModel:** TarefaViewModel com LiveData
- **Repository:** Camada de abstração para dados

## Banco de Dados

Utiliza **Room Database** para persistência local:
- Tabela `tarefas` com campos: id, titulo, descricao, concluida, dataCriacao, prioridade
- Queries otimizadas para ordenação e filtragem
- Conversores de tipo para Date e Enum

## Interface

Design baseado em **Material Design 3**:
- Cards para cada tarefa
- FAB para adicionar tarefas
- Cores indicativas de prioridade
- Animações e feedback visual
- Suporte a tema claro/escuro

---

Desenvolvido com ❤️ em Kotlin para Android
