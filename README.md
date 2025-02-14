# Documentación del Proyecto

## Índice
1. Introducción / Descripción
2. Diagramas y Modelo de Datos
3. Requisitos de Usuario
4. Casos de Uso
5. Descripción del Funcionamiento y Especificaciones Técnicas
6. Interfaces
    - Diseño Inicial: Mokups y Prototipado
    - Usabilidad y Accesibilidad
7. Manuales
    - Manual de Instalación para Desarrolladores
    - Manual de Instalación para Técnicos
    - Manual de Usuario
    - Ayuda dentro de la App
8. Tests de Prueba
    - Backend
    - Frontend
9. Pila Tecnológica
10. Comparación de Tecnologías
11. Repositorios
12. Planificación y Organización
13. Conclusiones y Reflexiones
14. Enlaces y Referencias

## 1. Introducción / Descripción

### Origen de la necesidad
Muchas personas disfrutan de actividades al aire libre, como senderismo, ciclismo, escalada, kayak y esnórquel, pero no siempre tienen compañía para realizarlas. Esto puede desmotivar a algunos, especialmente en deportes que requieren compañía por seguridad o disfrute. Para resolver este problema, se ha desarrollado una aplicación que permite a los usuarios encontrar grupos con intereses similares y unirse a actividades deportivas en distintos entornos.

### Empresa destinataria
Esta aplicación está diseñada para entusiastas de los deportes al aire libre, agencias de turismo, empresas de actividades recreativas y cualquier persona interesada en socializar a través del deporte. También está dirigida a turistas y nuevos residentes que buscan explorar su entorno y participar en eventos locales.

### Resumen del desarrollo
Esta aplicación facilita la conexión entre personas que desean participar en actividades deportivas en grupo. Los usuarios pueden registrarse, personalizar su perfil, unirse a actividades existentes o crear nuevas, interactuar mediante chats en tiempo real y recibir notificaciones sobre cambios en sus eventos. Además, las empresas pueden anunciar actividades de pago, permitiendo a los usuarios reservar y pagar directamente desde la plataforma.

La aplicación cuenta con funcionalidades clave como registro e inicio de sesión, personalización de perfil, organización de eventos, chat en tiempo real y un sistema de valoración de actividades y usuarios. Todo esto con el objetivo de fomentar la socialización y mejorar la experiencia de quienes disfrutan de actividades al aire libre.

### Otros aspectos relevantes
- **Seguridad y confianza**: La aplicación incluye un sistema de valoraciones y comentarios para mejorar la confianza entre los usuarios.
- **Flexibilidad**: Se pueden organizar eventos tanto gratuitos como de pago, ampliando las opciones para los usuarios y las empresas.
- **Interfaz intuitiva**: Diseñada para ser fácil de usar, con opciones rápidas para encontrar y unirse a actividades.
- **Accesibilidad**: Disponible para dispositivos móviles, con integración de redes sociales para un acceso rápido y sencillo.

## 2. Diagramas y Modelo de Datos
### Justificación y explicación del modelo de datos
El modelo de datos se basa en la gestión de usuarios, actividades y deportes, estableciendo relaciones que permiten una organización eficiente de la información y la interacción entre los usuarios.

#### Esquema de Entidad Relación
Imagen Esquema de Entidad Relacion: 

**Diagrama básico MER:**
- Usuario (1) ↔ (M) UsuarioActividad (M) ↔ (1) Actividad
- Deporte (1) ↔ (M) Actividad
- Actividad (1) ↔ (M) Comentario

#### Diagrama UML
Imagen de Diagrama uml


#### Modelo Relacional
**Usuario**
- id_usuario (PK)
- email
- localizacion
- contraseña
- foto_perfil
- foto_fondo_perfil
- username
- Relaciones:
  - Participa en muchas actividades (muchos a muchos, usa una tabla intermedia UsuarioActividad).
  - Puede comentar en actividades (uno a muchos, relación con Comentario).

**Deporte**
- id_deporte (PK)
- nombre
- tipo (enum: agua o tierra)
- imagen
- Relaciones:
  - Un deporte tiene muchas actividades (uno a muchos, relación con Actividad).

**Actividad**
- id_actividad (PK)
- nombre
- valoracion
- precio
- descripcion
- tendencia
- evento
- bookmark
- favoritas
- unido
- latitud
- longitud
- dificultad (enum: facil, intermedia, difícil)
- imagen
- disponibilidad
- Relaciones:
  - Pertenecer a un deporte (muchos a uno, relación con Deporte).
  - Tener usuarios participantes (muchos a muchos, usa UsuarioActividad).
  - Puede tener comentarios (uno a muchos, relación con Comentario).
  - Puede ser destacada (atributo booleano o enum).

**UsuarioActividad** (Tabla intermedia para evitar problemas muchos a muchos)
- id_usuario_actividad (PK)
- usuario_id (FK a Usuario)
- actividad_id (FK a Actividad)
- Fecha de inscripción.

**Comentario**
- id_comentario (PK)
- texto
- fecha
- usuario_id (FK a Usuario)
- actividad_id (FK a Actividad)
- Relaciones:
  - Relación con Usuario (muchos a uno).
  - Relación con Actividad (muchos a uno).

### Creación de base de datos
```sql
CREATE TABLE Usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    localizacion VARCHAR(255),
    contraseña VARCHAR(255) NOT NULL,
    foto_perfil VARCHAR(255),
    foto_fondo_perfil VARCHAR(255)
);

CREATE TABLE Deporte (
    id_deporte BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    tipo ENUM('agua', 'tierra') NOT NULL,
    imagen VARCHAR(255)
);

CREATE TABLE Actividad (
    id_actividad BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    valoracion FLOAT CHECK(valoracion >= 0 AND valoracion <= 5),
    precio DECIMAL(10, 2) CHECK(precio >= 0),
    descripcion TEXT,
    tendencia BOOLEAN DEFAULT FALSE,
    evento BOOLEAN DEFAULT FALSE,
    bookmark BOOLEAN DEFAULT FALSE,
    favoritas BOOLEAN DEFAULT FALSE,
    unido BOOLEAN DEFAULT FALSE,
    latitud DECIMAL(10, 8),
    longitud DECIMAL(11, 8),
    dificultad ENUM('facil', 'intermedia', 'dificil'),
    imagen VARCHAR(255),
    disponibilidad BOOLEAN DEFAULT TRUE,
    id_deporte BIGINT NOT NULL,
    FOREIGN KEY (id_deporte) REFERENCES Deporte(id_deporte) ON DELETE CASCADE
);

CREATE TABLE Comentario (
    id_comentario BIGINT AUTO_INCREMENT PRIMARY KEY,
    texto TEXT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_usuario BIGINT NOT NULL,
    id_actividad BIGINT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_actividad) REFERENCES Actividad(id_actividad) ON DELETE CASCADE
);

CREATE TABLE UsuarioActividad (
    id_usuario_actividad BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    actividad_id BIGINT NOT NULL,
    fecha_inscripcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (actividad_id) REFERENCES Actividad(id_actividad) ON DELETE CASCADE,
    UNIQUE (usuario_id, actividad_id)
);
```


- Referencia a archivos del módulo "Acceso a Datos".

---

## 3. Requisitos de Usuario

### **Definición de requisitos generales y especificación inicial de casos de uso**
1. Los usuarios deben poder registrarse e iniciar sesión.
2. Los usuarios pueden buscar y unirse a actividades deportivas.
3. Los usuarios pueden crear nuevas actividades e invitar a otros.
4. Se debe permitir la comunicación en tiempo real entre los participantes.
5. El sistema debe permitir la valoración de actividades y usuarios.
6. Empresas pueden promocionar eventos pagos.

### **Evolución de los requisitos a lo largo del proceso**
Los requisitos pueden evolucionar según la retroalimentación de los usuarios, permitiendo la adición de nuevas funcionalidades como:
- Filtros avanzados para la búsqueda de actividades.
- Integración con dispositivos wearables para el monitoreo de actividad física.

### **Producto resultante: listado de requisitos generales y casos de uso iniciales**
- **Registro/Login**: Autenticación mediante email/contraseña o redes sociales.
- **Exploración de actividades**: Lista de eventos deportivos según ubicación e interés.
- **Creación de eventos**: Formulario para definir actividad, ubicación, fecha, dificultad, etc.
- **Chat en tiempo real**: Canal de comunicación entre participantes.
- **Valoraciones**: Sistema de puntuación de usuarios y actividades.

---
## 4. Casos de Uso
- Descripción detallada de los casos de uso del sistema.

## 5. Descripción del Funcionamiento y Especificaciones Técnicas
- Explicación del sistema.
- Especificaciones técnicas del servidor y de las aplicaciones web y móvil.
- Elementos visuales como mapas conceptuales.

## 6. Interfaces
### 6.1 Diseño Inicial: Mokups y Prototipado
- Enlace compartido con la herramienta usada para el prototipado.

### 6.2 Usabilidad y Accesibilidad
- Aspectos clave de usabilidad considerados.
- Justificación del diseño en base a estos aspectos.
- Proceso de estudio previo de usabilidad y accesibilidad.
- Evaluación post-desarrollo con ejemplos visuales.

## 7. Manuales
### 7.1 Manual de Instalación
- Para desarrolladores (servidor y cliente).
- Para técnicos que instalan la aplicación.

### 7.2 Manual de Usuario
- Explicación del uso de la aplicación.

### 7.3 Ayuda dentro de la App
- Instrucciones accesibles desde la propia aplicación.

## 8. Tests de Prueba
### 8.1 Backend
- Pruebas realizadas para garantizar el correcto funcionamiento del servidor.

### 8.2 Frontend
- Pruebas realizadas para asegurar la estabilidad y usabilidad de la interfaz.

## 9. Pila Tecnológica
- Tecnologías utilizadas en el desarrollo del proyecto.

## 10. Comparación de Tecnologías
- Comparación de alternativas tecnológicas y justificación de las elecciones.

## 11. Repositorios
- FrontEnd: https://github.com/raulJD13/Frontend.git
- BackEnd: https://github.com/raulJD13/Backend.git
- Proyecto en react: https://github.com/raulJD13/TerraSplash2.0.git

## 12. Planificación y Organización
- Explicación sobre la metodología de organización y planificación.

## 13. Conclusiones y Reflexiones
- Opiniones auténticas sobre el desarrollo del proyecto.

## 14. Enlaces y Referencias
- Bibliografía, documentación y recursos utilizados.

## Anexos
- Plantilla de funciones del sistema (modelo ERS).
- Documentos adicionales de referencia.

