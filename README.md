# Ritmo Interno — Prueba técnica final (Android, Kotlin)

App nativa Android con login/registro y un CRUD completo de **Rutinas**,
construida en Kotlin con arquitectura **MVVM** y persistencia local en
**Room**. El contenido de referencia (Conceptos, Tips,
Videos, Recursos) de idea y diseño original.

## Requisitos
- Android Studio Meerkat | 2024.3.1 Patch 1, o más reciente.
- SDK de Android: `compileSdk 34`, `minSdk 24`, `targetSdk 34`.
- JDK 17 (el que trae Android Studio por defecto sirve).

## Cómo compilar y ejecutar (3 pasos)
1. Android Studio → **Open** → seleccionar la carpeta `RitmoInterno`.
2. Esperar a que termine el **Gradle Sync** (si pide generar el wrapper,
   dejar que lo haga; el proyecto no trae el jar del wrapper porque es
   binario).
3. Ejecutar (▶) sobre un emulador o celular conectado.

No se necesita backend ni conexión a internet: todo el login/registro y el
CRUD de rutinas funcionan 100% local con Room.

## Flujo para probar la app
1. Se abre el splash y pasa a **Login** (si es la primera vez, no hay
   sesión activa).
2. Tocar "¿No tienes cuenta? Regístrate" → crear una cuenta (nombre,
   correo, contraseña de mínimo 6 caracteres).
3. Iniciar sesión con esa cuenta.
4. En la pestaña **Mis Rutinas** (primera pestaña): tocar el botón "+"
   para crear una rutina (título, descripción, duración en minutos,
   fecha, favorito opcional).
5. Tocar una rutina de la lista para ver el **detalle**: desde ahí se
   puede editar, eliminar o marcar/desmarcar como favorita.
6. Editar una rutina y guardar → el cambio se refleja al instante en la
   lista, sin reiniciar la app.
7. Eliminar una rutina (pide confirmación) → desaparece de la lista.
8. Menú de 3 puntos (arriba a la derecha) → **Cerrar sesión** → vuelve a
   Login.

## Arquitectura (resumen — ver `documento_tecnico.md` para el detalle)
- **MVVM**: `ViewModel` (estado y validaciones) + `Repository`
  (intermediario) + `Room` (persistencia), sin lógica de negocio en las
  Activities/Fragments.
- **Entidad CRUD**: `Rutina` (título, descripción, duración, fecha,
  favorito), asociada al usuario dueño (`userId`).
- **Autenticación**: usuarios guardados en Room (`UserEntity`, contraseña
  con hash SHA-256); la sesión activa se guarda en `SharedPreferences`
  (`SessionManager`), sin datos sensibles.

## Estructura
```
app/src/main/java/com/ritmointerno/app/
├── SplashActivity.kt              → decide Login o Main según sesión activa
├── MainActivity.kt                → toolbar + 6 tabs + menú (incluye Cerrar sesión)
├── data/
│   ├── db/
│   │   ├── UserEntity.kt / UserDao.kt       → usuarios (Room)
│   │   ├── RutinaEntity.kt / RutinaDao.kt   → rutinas, la entidad CRUD (Room)
│   │   └── AppDatabase.kt                   → base de datos Room
│   ├── AuthRepository.kt          → registro / login
│   ├── RutinaRepository.kt        → CRUD de rutinas
│   ├── SessionManager.kt          → sesión activa (SharedPreferences)
│   ├── PasswordHasher.kt          → hash SHA-256 de contraseñas
│   ├── ContentItem.kt / ContentType.kt / ContentRepository.kt  → contenido de referencia
│   └── FavoritesManager.kt        → favoritos del contenido de referencia
└── ui/
    ├── auth/
    │   ├── LoginActivity.kt / RegisterActivity.kt   → Vista 1 (autenticación)
    │   └── AuthViewModel.kt (+ Factory)
    ├── rutinas/
    │   ├── RutinaListFragment.kt / RutinaAdapter.kt → Vista 2 (listado, RecyclerView)
    │   ├── RutinaDetailActivity.kt                  → Vista 3 (detalle)
    │   ├── RutinaFormActivity.kt                    → Vista 4 (crear/editar)
    │   └── RutinaListViewModel.kt / RutinaFormViewModel.kt / RutinaDetailViewModel.kt (+ Factories)
    ├── ContentListFragment.kt / FavoritosFragment.kt / ContentAdapter.kt / SectionPagerAdapter.kt
    │                                  → pestañas de contenido de referencia
```

## 🛠️ Tecnologías y Herramientas Utilizadas

*   **Desarrollo:** Android Studio Meerkat.
*   **Asistencia de Inteligencia Artificial:** Se utilizó **IA Claude (Anthropic)** como tutor de código y 
*	apoyo técnico para estructurar y depurar la aplicación, optimizando el proceso de aprendizaje 
* 	para una disciplina ajena a mi carrera principal (Psicología).

