# App de Tarefas Diárias - Android Kotlin

Um aplicativo Android simples para gerenciar tarefas diárias, desenvolvido em Kotlin nativo.

## Funcionalidades

-  Adicionar, editar e excluir tarefas
-  Sistema de prioridades com cores (Alta/Média/Baixa)
-  Marcar tarefas como concluídas
-  Limpar todas as concluídas
-  Persistência local com Room Database
-  Interface Material Design

## Tecnologias

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

1. Abra no Android Studio
2. Sincronize as dependências
3. Execute no dispositivo/emulador

![WhatsApp Image 2025-09-15 at 14 59 23](https://github.com/user-attachments/assets/a3485da8-0707-4ecb-80a2-b55632cdf5f0)


## Requisitos

- Android Studio Arctic Fox+
- Android SDK 24+ (Android 7.0)

---

Desenvolvido em Kotlin
