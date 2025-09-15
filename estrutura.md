# 📱 Estrutura do Projeto Android - Tarefas Diárias

Este documento explica a estrutura completa do projeto Android desenvolvido em Kotlin, seguindo as melhores práticas de arquitetura MVVM.

## 🏗️ Arquitetura Geral

O projeto segue o padrão **MVVM (Model-View-ViewModel)** com as seguintes camadas:
- **Model**: Entidades de dados e acesso ao banco
- **View**: Interface do usuário (Activities, Layouts)
- **ViewModel**: Lógica de apresentação e estado
- **Repository**: Camada de abstração para acesso aos dados

---

## 📁 Estrutura de Pastas e Arquivos

### 🔧 **Configuração do Projeto**

```
📁 TAREFAS-KOTLIN/
├── 📄 build.gradle.kts                    # Configurações do projeto principal
├── 📄 settings.gradle.kts                 # Configurações do Gradle
├── 📄 gradle.properties                   # Propriedades do Gradle
├── 📄 local.properties                    # Configurações locais (caminhos, etc.)
├── 📄 gradlew                            # Script Gradle para Unix/Linux
├── 📄 gradlew.bat                        # Script Gradle para Windows
└── 📄 estrutura.md                       # Este arquivo de documentação
```

**Descrições:**
- `build.gradle.kts`: Define plugins e versões do Android Gradle Plugin e Kotlin
- `settings.gradle.kts`: Configura quais módulos fazem parte do projeto
- `gradle.properties`: Propriedades globais do Gradle (memória, encoding, etc.)
- `local.properties`: Caminhos específicos da máquina (SDK, etc.)
- `gradlew`/`gradlew.bat`: Scripts para executar o Gradle sem instalação local

### 📱 **Módulo da Aplicação**

```
📁 app/
├── 📄 build.gradle.kts                   # Configurações específicas do app
└── 📁 src/main/
    ├── 📄 AndroidManifest.xml            # Manifesto da aplicação
    ├── 📁 java/com/example/tarefasdiarias/
    │   ├── 📄 MainActivity.kt            # Activity principal da aplicação
    │   ├── 📁 data/                      # Camada de dados (Model)
    │   │   ├── 📄 Tarefa.kt             # Entidade principal do app
    │   │   ├── 📄 TarefaDao.kt          # Interface de acesso ao banco
    │   │   └── 📄 TarefaDatabase.kt     # Configuração do banco Room
    │   ├── 📁 viewmodel/                 # Camada de apresentação
    │   │   └── 📄 TarefaViewModel.kt    # ViewModel com lógica de negócio
    │   ├── 📁 repository/                # Camada de abstração
    │   │   └── 📄 TarefaRepository.kt   # Repository para acesso aos dados
    │   └── 📁 adapter/                   # Adaptadores para RecyclerView
    │       └── 📄 TarefaAdapter.kt       # Adapter para lista de tarefas
    └── 📁 res/                           # Recursos da aplicação
        ├── 📁 drawable/                  # Ícones e imagens
        │   ├── 📄 ic_add.xml            # Ícone de adicionar
        │   ├── 📄 ic_clear_all.xml      # Ícone de limpar tudo
        │   ├── 📄 ic_delete.xml         # Ícone de deletar
        │   └── 📄 ic_launcher_foreground.xml # Ícone do app
        ├── 📁 layout/                    # Layouts XML da interface
        │   ├── 📄 activity_main.xml     # Layout da tela principal
        │   ├── 📄 dialog_adicionar_tarefa.xml # Dialog para adicionar tarefa
        │   └── 📄 item_tarefa.xml       # Layout de cada item da lista
        ├── 📁 menu/                      # Menus da aplicação
        │   └── 📄 menu_main.xml         # Menu da toolbar
        ├── 📁 mipmap-*/                  # Ícones do launcher (diferentes densidades)
        │   ├── 📄 ic_launcher.xml       # Ícone principal do app
        │   └── 📄 ic_launcher_round.xml # Ícone redondo do app
        ├── 📁 values/                    # Valores e recursos
        │   ├── 📄 colors.xml            # Cores da aplicação
        │   ├── 📄 strings.xml           # Textos da aplicação
        │   └── 📄 themes.xml            # Temas e estilos
        └── 📁 xml/                       # Arquivos XML de configuração
            ├── 📄 backup_rules.xml      # Regras de backup
            └── 📄 data_extraction_rules.xml # Regras de extração de dados
```

---

## 📋 **Descrição Detalhada dos Arquivos**

### 🔧 **Arquivos de Configuração**

#### `build.gradle.kts` (Raiz)
```kotlin
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}
```
**Função**: Define as versões dos plugins principais do projeto (Android Gradle Plugin, Kotlin, KSP).

#### `app/build.gradle.kts`
```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}
```
**Função**: Configurações específicas do app - dependências, versões do Android, configurações de compilação.

### 📱 **Arquivos de Código Kotlin**

#### `MainActivity.kt`
**Função**: Activity principal que gerencia a interface do usuário.
- **Responsabilidades**:
  - Configurar RecyclerView e FAB
  - Observar mudanças no ViewModel
  - Mostrar dialogs para adicionar/editar tarefas
  - Gerenciar cliques e interações do usuário

#### `Tarefa.kt`
```kotlin
@Entity(tableName = "tarefas")
data class Tarefa(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val titulo: String,
    val descricao: String = "",
    val concluida: Boolean = false,
    val dataCriacao: Date = Date(),
    val prioridade: Prioridade = Prioridade.MEDIA
)
```
**Função**: Entidade principal que representa uma tarefa no banco de dados.
- **Anotações**: `@Entity` define a tabela, `@PrimaryKey` define a chave primária
- **Enum Prioridade**: Define os níveis de prioridade (BAIXA, MÉDIA, ALTA)

#### `TarefaDao.kt`
```kotlin
@Dao
interface TarefaDao {
    @Query("SELECT * FROM tarefas ORDER BY concluida ASC, prioridade DESC, dataCriacao DESC")
    fun getAllTarefas(): LiveData<List<Tarefa>>
    // ... outros métodos
}
```
**Função**: Interface de acesso aos dados (Data Access Object).
- **Anotações**: `@Dao` marca como interface de acesso, `@Query` define consultas SQL
- **Métodos**: CRUD completo (Create, Read, Update, Delete)

#### `TarefaDatabase.kt`
```kotlin
@Database(
    entities = [Tarefa::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TarefaDatabase : RoomDatabase()
```
**Função**: Configuração do banco de dados Room.
- **Singleton**: Garante uma única instância do banco
- **TypeConverters**: Converte tipos complexos (Date, Enum) para o banco

#### `TarefaViewModel.kt`
```kotlin
class TarefaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TarefaRepository
    val allTarefas: LiveData<List<Tarefa>>
    // ... métodos de negócio
}
```
**Função**: Gerencia o estado da UI e lógica de apresentação.
- **LiveData**: Dados observáveis que atualizam a UI automaticamente
- **Coroutines**: Operações assíncronas para não bloquear a UI

#### `TarefaRepository.kt`
```kotlin
class TarefaRepository(private val tarefaDao: TarefaDao) {
    val allTarefas: LiveData<List<Tarefa>> = tarefaDao.getAllTarefas()
    // ... métodos de acesso aos dados
}
```
**Função**: Camada de abstração entre ViewModel e banco de dados.
- **Single Source of Truth**: Centraliza o acesso aos dados
- **Facilita testes**: Pode ser mockado facilmente

#### `TarefaAdapter.kt`
```kotlin
class TarefaAdapter(
    private val onTarefaClick: (Tarefa) -> Unit,
    private val onCheckboxClick: (Tarefa) -> Unit,
    private val onDeleteClick: (Tarefa) -> Unit
) : ListAdapter<Tarefa, TarefaAdapter.TarefaViewHolder>(TarefaDiffCallback())
```
**Função**: Adapta dados para exibição no RecyclerView.
- **DiffUtil**: Otimiza atualizações da lista
- **Callbacks**: Comunica cliques para a Activity

### 🎨 **Arquivos de Interface (XML)**

#### `activity_main.xml`
```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout>
    <com.google.android.material.appbar.AppBarLayout>
        <androidx.appcompat.widget.Toolbar />
    </com.google.android.material.appbar.AppBarLayout>
    <androidx.recyclerview.widget.RecyclerView />
    <com.google.android.material.floatingactionbutton.FloatingActionButton />
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```
**Função**: Layout principal da aplicação.
- **CoordinatorLayout**: Coordena animações entre componentes
- **AppBarLayout**: Barra superior com toolbar
- **RecyclerView**: Lista de tarefas
- **FAB**: Botão flutuante para adicionar tarefas

#### `item_tarefa.xml`
```xml
<com.google.android.material.card.MaterialCardView>
    <LinearLayout>
        <View android:id="@+id/viewPrioridade" />
        <CheckBox android:id="@+id/checkboxConcluida" />
        <LinearLayout>
            <TextView android:id="@+id/textTitulo" />
            <TextView android:id="@+id/textDescricao" />
            <LinearLayout>
                <TextView android:id="@+id/textPrioridade" />
                <TextView android:id="@+id/textData" />
            </LinearLayout>
        </LinearLayout>
        <ImageButton android:id="@+id/buttonDelete" />
    </LinearLayout>
</com.google.android.material.card.MaterialCardView>
```
**Função**: Layout de cada item da lista de tarefas.
- **MaterialCardView**: Card com elevação e cantos arredondados
- **ViewPrioridade**: Barra colorida indicando prioridade
- **CheckBox**: Para marcar como concluída
- **TextViews**: Título, descrição, prioridade e data

#### `dialog_adicionar_tarefa.xml`
**Função**: Layout do dialog para adicionar/editar tarefas.
- **EditTexts**: Campos para título e descrição
- **Spinner**: Seleção de prioridade

### 🎨 **Arquivos de Recursos**

#### `colors.xml`
```xml
<resources>
    <color name="orange_200">#FFFFCC80</color>
    <color name="orange_500">#FFFF9800</color>
    <color name="orange_700">#FFF57C00</color>
    <color name="prioridade_alta">#F44336</color>
    <color name="prioridade_media">#FF9800</color>
    <color name="prioridade_baixa">#4CAF50</color>
</resources>
```
**Função**: Define todas as cores usadas na aplicação.

#### `strings.xml`
```xml
<resources>
    <string name="app_name">Tarefas Diárias</string>
    <string name="nova_tarefa">Nova Tarefa</string>
    <string name="editar_tarefa">Editar Tarefa</string>
    <!-- ... outros textos -->
</resources>
```
**Função**: Centraliza todos os textos da aplicação para facilitar tradução.

#### `themes.xml`
```xml
<style name="Theme.TarefasDiarias" parent="Theme.MaterialComponents.DayNight.DarkActionBar">
    <item name="colorPrimary">@color/orange_500</item>
    <item name="colorPrimaryVariant">@color/orange_700</item>
    <item name="colorOnPrimary">@color/white</item>
</style>
```
**Função**: Define o tema visual da aplicação (cores, estilos, etc.).

### 🖼️ **Arquivos de Ícones**

#### `ic_launcher_foreground.xml`
**Função**: Ícone principal do aplicativo (vector drawable).
- **Design**: Lista de tarefas com cores laranja
- **Compatibilidade**: Funciona em todas as densidades de tela

#### `ic_add.xml`, `ic_delete.xml`, `ic_clear_all.xml`
**Função**: Ícones para botões e ações da interface.

### 📱 **Arquivos de Manifesto**

#### `AndroidManifest.xml`
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.TarefasDiarias">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```
**Função**: Define configurações da aplicação, permissões e componentes.

---

## 🔄 **Fluxo de Dados (Como Funciona)**

1. **Usuário interage** com a UI (MainActivity)
2. **MainActivity** chama métodos do **ViewModel**
3. **ViewModel** executa lógica e chama o **Repository**
4. **Repository** acessa o **DAO** para operações no banco
5. **Room Database** persiste os dados
6. **LiveData** notifica mudanças automaticamente
7. **UI** é atualizada automaticamente

---

## 🎯 **Tecnologias Utilizadas**

- **Kotlin**: Linguagem principal
- **Room**: Banco de dados local
- **LiveData**: Dados observáveis
- **Coroutines**: Programação assíncrona
- **ViewBinding**: Binding type-safe para views
- **Material Design**: Design system do Google
- **KSP**: Processamento de anotações (substituto do KAPT)

---

## 📚 **Conceitos Importantes para Iniciantes**

### **MVVM (Model-View-ViewModel)**
- **Model**: Dados e regras de negócio
- **View**: Interface do usuário
- **ViewModel**: Conecta Model e View

### **Room Database**
- **Entity**: Classe que representa uma tabela
- **DAO**: Interface para operações no banco
- **Database**: Configuração do banco

### **LiveData**
- Dados que podem ser observados
- Atualiza a UI automaticamente quando mudam
- Lifecycle-aware (para quando a tela está ativa)

### **ViewBinding**
- Gera classes para acessar views de forma type-safe
- Substitui findViewById() tradicional
- Mais seguro e eficiente

---

Este projeto é um excelente exemplo de como estruturar um aplicativo Android moderno seguindo as melhores práticas! 🚀
