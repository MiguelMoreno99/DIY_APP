# DIY App

![Kotlin](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)
![Room](https://img.shields.io/badge/Room-Database-blue?style=for-the-badge)
![Dagger Hilt](https://img.shields.io/badge/Dagger_Hilt-DI-orange?style=for-the-badge)

Una aplicación móvil para Android diseñada para la comunidad creativa. DIY App permite a los usuarios descubrir, inspirarse y compartir sus propios proyectos "Do It Yourself" (Hazlo tú mismo).

## 📖 Sobre el Proyecto

Esta aplicación nace con la idea de crear un espacio donde la creatividad no tenga límites. Ya sea carpintería, electrónica, manualidades o reparaciones en casa, los usuarios pueden documentar sus procesos y mostrar el resultado final al mundo, así como explorar el feed para ver las increíbles creaciones de otros miembros de la comunidad.

## ✨ Características Principales

* **📱 Feed de Exploración:** Visualiza los proyectos DIY de otros usuarios en tiempo real.
* **🛠️ Comparte tus Creaciones:** Publica tus propios proyectos con detalles, pasos e imágenes.
* **💾 Modo Offline (Caché):** Guarda tus proyectos favoritos para verlos sin conexión.
* **👤 Perfil de Usuario:** Administra tus publicaciones y creaciones.

## 🏗️ Arquitectura y Tecnologías

El proyecto está desarrollado completamente en **Kotlin** y sigue las mejores prácticas y directrices recomendadas por Google para el desarrollo en Android:

* **Patrón de Arquitectura:** `MVVM` (Model-View-ViewModel) para una clara separación de responsabilidades.
* **Patrones de Diseño:**
    * `Singleton`: Para instancias únicas y gestión eficiente de recursos.
    * `DAO` (Data Access Object): Para abstraer y encapsular el acceso a la base de datos.
* **Inyección de Dependencias:** `Dagger Hilt` para un código más modular, testable y escalable.
* **Base de Datos Local:** `Room Database` para la persistencia de datos y el manejo de información offline.
* **Asincronía:** Corrutinas de Kotlin (`Coroutines`) para el manejo fluido de hilos.
