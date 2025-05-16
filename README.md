# Documentación del Proyect0

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

---

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

---

## 2. Diagramas y Modelo de Datos
### Justificación y explicación del modelo de datos
El modelo de datos se basa en la gestión de usuarios, actividades y deportes, estableciendo relaciones que permiten una organización eficiente de la información y la interacción entre los usuarios.

#### Esquema de Entidad Relación

![Esquema de Entidad Relación](https://github.com/raulJD13/Backend/blob/f5bb77210a058bf36bee2c9979ed985e3f059e23/Images-Github/Captura%20de%20pantalla%202025-02-16%20a%20las%2013.18.48.png)

**Diagrama básico MER:**
- Usuario (1) ↔ (M) UsuarioActividad (M) ↔ (1) Actividad
- Deporte (1) ↔ (M) Actividad
- Actividad (1) ↔ (M) Comentario

#### Diagrama UML
Imagen de Diagrama uml

![Diagrama uml](https://github.com/raulJD13/Backend/blob/3787604e639cc4983983ae30dd837bbb9412c2f1/Images-Github/Captura%20de%20pantalla%202025-02-16%20a%20las%2013.20.40.png)


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

### Documentación del Modelo de Datos
### Deporte
| Atributo  | Tipo de Dato | Restricciones |
|-----------|-------------|--------------|
| idDeporte | Long | Auto-generado, clave primaria |
| nombre    | String | No nulo |
| tipo      | String | No nulo |
| imagen    | String | URL de la imagen |
| actividades | List | Relación uno a muchos |

### Actividad
| Atributo  | Tipo de Dato | Restricciones |
|-----------|-------------|--------------|
| idActividad | Long | Auto-generado, clave primaria |
| nombre      | String | No nulo |
| valoracion  | Float | Opcional |
| precio      | Double | Opcional |
| descripcion | String | Opcional |
| imagen      | String | URL de la imagen |
| disponibilidad | Boolean | Por defecto true |
| deporte    | Deporte | Relación muchos a uno |

### Usuario
| Atributo  | Tipo de Dato | Restricciones |
|-----------|-------------|--------------|
| idUsuario | Int | Auto-generado, clave primaria |
| email     | String | No nulo |
| localizacion | String | No nulo |
| contraseña | String | No nulo |
| foto_perfil | String | URL de la imagen |
| token | String | Autenticación |

### 3.4. Comentario
| Atributo  | Tipo de Dato | Restricciones |
|-----------|-------------|--------------|
| id_comentario | Int | Auto-generado, clave primaria |
| texto         | String | No nulo |
| fecha         | Date | No nulo |
| id_usuario    | Int | Relación con Usuario |
| id_actividad  | Int | Relación con Actividad |


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

### **Producto resultante: listado de requisitos generales y casos de uso iniciales**
- **Registro/Login**: Autenticación mediante email/contraseña o redes sociales.
- **Exploración de actividades**: Lista de eventos deportivos según ubicación e interés.
- **Creación de eventos**: Formulario para definir actividad, ubicación, fecha, dificultad, etc.
- **Chat en tiempo real**: Canal de comunicación entre participantes.
- **Valoraciones**: Sistema de puntuación de usuarios y actividades.

---
## 4. Casos de Uso

### Caso de Uso 1: Registro de Usuario
- **Actor:** Usuario nuevo
- **Objetivo:** Crear una cuenta en la aplicación
- **Flujo principal:**  
  1. El usuario accede a la pantalla de registro.  
  2. Ingresa su email, contraseña, ubicación y otros datos opcionales.  
  3. Confirma la contraseña y acepta los términos y condiciones.  
  4. El sistema valida los datos y crea la cuenta.  
  5. Se envía un correo de confirmación.  
- **Flujo alternativo:**  
  - Si el correo ya está registrado, se muestra un mensaje de error.  
  - Si la contraseña no cumple con los requisitos, se solicita una nueva.  
- **Resultado esperado:**  
  - El usuario recibe un correo para confirmar su cuenta y puede iniciar sesión.  

### Caso de Uso 2: Iniciar Sesión
- **Actor:** Usuario registrado
- **Objetivo:** Acceder a su cuenta en la aplicación
- **Flujo principal:**  
  1. El usuario accede a la pantalla de inicio de sesión.  
  2. Ingresa su email y contraseña.  
  3. El sistema valida las credenciales.  
  4. Si son correctas, el usuario accede a su perfil.  
- **Flujo alternativo:**  
  - Si el email o la contraseña son incorrectos, se muestra un mensaje de error.  
  - Si el usuario olvida la contraseña, puede solicitar un restablecimiento.  
- **Resultado esperado:**  
  - El usuario accede correctamente a la aplicación.  

### Caso de Uso 3: Creación de Actividad
- **Actor:** Usuario registrado
- **Objetivo:** Crear una nueva actividad en la aplicación
- **Flujo principal:**  
  1. El usuario accede a la sección "Crear Actividad".  
  2. Ingresa los detalles de la actividad: categoría (tierra o agua), nombre, ubicación, fecha y hora, número de participantes, nivel de dificultad y precio (si aplica).  
  3. El usuario confirma la creación de la actividad.  
  4. La actividad se publica y otros usuarios pueden verla y unirse.  
- **Flujo alternativo:**  
  - Si falta información obligatoria, se solicita completarla.  
  - Si hay un error en la validación de datos, se muestra un mensaje de corrección.  
- **Resultado esperado:**  
  - La actividad queda registrada y visible para otros usuarios.

### Caso de Uso 4: Unirse a una Actividad
- **Actor:** Usuario registrado
- **Objetivo:** Unirse a una actividad existente
- **Flujo principal:**  
  1. El usuario accede a la lista de actividades disponibles.  
  2. Selecciona una actividad de interés.  
  3. Confirma su participación.  
  4. Se le agrega a la lista de participantes y obtiene acceso al chat del grupo.  
- **Flujo alternativo:**  
  - Si la actividad ya está llena, se muestra un mensaje de cupo completo.  
  - Si la actividad es de pago, se solicita la confirmación del pago antes de unirse.  
- **Resultado esperado:**  
  - El usuario se une exitosamente a la actividad y puede interactuar con otros participantes.


### Caso de Uso 5: Calificación y Comentarios
- **Actor:** Usuario que ha participado en una actividad
- **Objetivo:** Calificar la actividad y a otros participantes
- **Flujo principal:**  
  1. El usuario accede a la actividad finalizada.  
  2. Asigna una calificación del 1 al 5.  
  3. Opcionalmente, deja un comentario sobre la experiencia.  
  4. La calificación y comentarios se guardan y se reflejan en la actividad y en el perfil de los participantes.  
- **Flujo alternativo:**  
  - Si el usuario no participó en la actividad, no puede calificarla.  
- **Resultado esperado:**  
  - La actividad y los participantes reciben calificaciones y comentarios para mejorar la experiencia.

---

## 5. Descripción del Funcionamiento y Especificaciones Técnicas

### Explicación del sistema
El sistema permite la creación, gestión y participación en actividades deportivas al aire libre. Los usuarios pueden registrarse, iniciar sesión, buscar y unirse a actividades, comunicarse con otros participantes a través de un chat en tiempo real y calificar las actividades.

### Especificaciones técnicas
- **Backend:** Desarrollado en Java 17 con Spring Boot 2.7.
- **Base de datos:** MySQL con Hibernate (JPA) para la persistencia de datos.
- **Frontend Web:** Angular con Ionic para una experiencia responsive.
- **Mobile:** Aplicación híbrida con Ionic Angular.
- **Autenticación:** Implementada con JWT para asegurar el acceso a la plataforma.
- **Seguridad:** Configuración de CORS para permitir peticiones desde clientes externos.
- **Almacenamiento de Archivos:** Soporte para subir y acceder a imágenes y otros archivos.

### Tecnologías utilizadas
- Java 17
- Spring Boot 2.7
- JPA (Hibernate)
- MySQL
- Maven
- Ionic
- Angular

![](https://github.com/raulJD13/Backend/blob/b8653979b7a39a9074c45d4337009f1aacb607a2/Images-Github/0.png)

###Documentación de la API - Introducción

Esta API está diseñada para gestionar actividades deportivas y conectar usuarios interesados en deportes al aire libre. Permite a los usuarios registrarse, crear y unirse a actividades, gestionar comentarios y subir archivos.

## Documentación de Endpoints (ERS)
### Endpoints de Deportes
**Base URL:** `/api/deportes`

| Método | Endpoint  | Descripción  |
|---------|----------|--------------|
| GET     | `/`      | Obtiene la lista de todos los deportes  |
| GET     | `/{id}`  | Obtiene un deporte por su ID  |
| POST    | `/`      | Crea un nuevo deporte  |
| PUT     | `/{id}`  | Actualiza un deporte existente  |
| DELETE  | `/{id}`  | Elimina un deporte por su ID  |

### Endpoints de Actividades
**Base URL:** `/api/actividades`

| Método | Endpoint  | Descripción  |
|---------|----------|--------------|
| GET     | `/deporte/{idDeporte}` | Obtiene las actividades asociadas a un deporte |
| GET     | `/{id}`  | Obtiene una actividad por su ID |
| POST    | `/`      | Crea una nueva actividad |
| PUT     | `/{id}`  | Actualiza los datos de una actividad existente |
| DELETE  | `/{id}`  | Borra una actividad por su ID |

### Endpoints de Usuarios
**Base URL:** `/api/usuarios`

| Método | Endpoint  | Descripción  |
|---------|----------|--------------|
| POST    | `/login` | Inicia sesión y devuelve un token |
| GET     | `/`      | Obtiene la lista de usuarios creados |
| GET     | `/{id}`  | Obtiene un usuario por su ID |
| POST    | `/`      | Crea un nuevo usuario |
| PUT     | `/{id}`  | Actualiza los datos de un usuario existente |
| DELETE  | `/{id}`  | Borra a un usuario por su ID |

### 2.4. Endpoints de Comentarios
**Base URL:** `/api/comentarios`

| Método | Endpoint  | Descripción  |
|---------|----------|--------------|
| GET     | `/`      | Obtiene la lista de comentarios |
| GET     | `/{id}`  | Obtiene un comentario por su ID |
| POST    | `/`      | Crea un nuevo comentario |
| PUT     | `/{id}`  | Actualiza un comentario existente |
| DELETE  | `/{id}`  | Borra un comentario por su ID |

### Endpoints para Archivos
**Base URL:** `/api/files`

| Método | Endpoint  | Descripción  |
|---------|----------|--------------|
| POST    | `/upload` | Sube un archivo (imagen) |
| GET     | `/uploads/{filename}` | Accede a un archivo subido por su nombre |


---

## 6. Interfaces
### 6.1 Diseño Inicial: Mokups y Prototipado

- [Enlace compartido con la herramienta usada para el prototipado.](https://www.figma.com/proto/GR2rNyKmLXYsix9RUzXMuJ?node-id=0-1&t=yzrgLzM9ZPkakEQV-6)
- [Enlace compartido del pdf.](https://github.com/raulJD13/Backend/blob/951501b03148325d79a4fc6e1ad77662254e0fbc/Images-Github/Outdoor.pdf)
  ![](https://github.com/raulJD13/Backend/blob/951501b03148325d79a4fc6e1ad77662254e0fbc/Images-Github/Captura%20de%20pantalla%202025-02-16%20a%20las%2013.25.06.png)
    
  ![](https://github.com/raulJD13/Backend/blob/37f223461334e0a30ae81e5f3f3c16af6f3a43ee/Images-Github/image.png)
  
### 6.2 Usabilidad y Accesibilidad

#### Aspectos clave de usabilidad considerados
Para garantizar una experiencia de usuario óptima, se han considerado los siguientes principios de usabilidad:
- **Diseño intuitivo**: La interfaz sigue patrones conocidos en aplicaciones similares para facilitar la navegación.
- **Flujo de usuario claro**: Se han diseñado procesos simples y directos para el registro, inicio de sesión, creación y participación en actividades.
- **Minimización de la carga cognitiva**: Se utilizan iconos, colores y textos explicativos para guiar al usuario sin necesidad de largas instrucciones.
- **Feedback inmediato**: Cada acción relevante proporciona una respuesta visual o auditiva para confirmar su ejecución.
- **Consistencia visual y funcional**: Se mantiene un diseño homogéneo en todas las pantallas para evitar confusión.
- **Optimización para dispositivos móviles**: La aplicación está diseñada para ofrecer una experiencia fluida en smartphones y tablets.

#### Justificación del diseño en base a estos aspectos
El diseño de la aplicación ha sido desarrollado con base en principios de diseño centrado en el usuario (User-Centered Design). La selección de colores, tipografías y distribución de elementos se ha realizado asegurando una lectura clara y un acceso rápido a las funcionalidades clave. Se priorizó la accesibilidad y simplicidad para facilitar su uso tanto a usuarios experimentados como a nuevos.

#### Proceso de estudio previo de usabilidad y accesibilidad
Antes del desarrollo, se llevaron a cabo las siguientes acciones:
- **Análisis de aplicaciones similares**: Se estudiaron plataformas existentes para identificar mejores prácticas y evitar errores comunes.
- **Creación de wireframes y prototipos**: Se diseñaron prototipos interactivos en Figma para evaluar la experiencia de usuario.
- **Pruebas con usuarios potenciales**: Se realizaron pruebas de usabilidad con un grupo reducido de personas interesadas en actividades al aire libre para obtener retroalimentación.
- **Aplicación de estándares de accesibilidad**: Se siguieron las directrices WCAG 2.1 para asegurar que la aplicación fuera accesible para personas con discapacidades visuales y motoras.

#### Evaluación post-desarrollo con ejemplos visuales
Tras el desarrollo de la aplicación, se ejecutaron pruebas de usabilidad y accesibilidad en entornos reales, con los siguientes resultados:
- **Pruebas A/B**: Se compararon diferentes versiones de la interfaz para identificar la más efectiva.
- **Encuestas de satisfacción**: Se recogieron opiniones de los primeros usuarios para realizar ajustes en la experiencia.
- **Pruebas de accesibilidad**: Se validó el uso de lectores de pantalla, contraste de colores y navegación con teclado para asegurar compatibilidad con diferentes necesidades.
- **Corrección de puntos débiles**: Se ajustaron elementos como el tamaño de botones, la claridad de mensajes de error y la disposición de información en pantallas clave.

Los resultados obtenidos han permitido optimizar la experiencia general, asegurando que la aplicación sea inclusiva y fácil de usar para todos los usuarios.

---

## 7. Manuales

### 7.1 Manual de Instalación
#### Para desarrolladores (servidor y cliente)
1. **Requisitos previos**:
   - Node.js y npm para el cliente.
   - Java y Spring Boot para el servidor.
   - Base de datos SQL configurada y accesible.
   - Git para gestionar versiones del código.
2. **Instalación del servidor**:
   - Clonar el repositorio con `git clone <repo>`.
   - Acceder a la carpeta del backend.
   - Configurar el archivo `application.properties` con las credenciales de la base de datos.
   - Ejecutar `mvn clean install` y `mvn spring-boot:run`.
3. **Instalación del cliente**:
   - Acceder a la carpeta del frontend.
   - Instalar dependencias con `npm install`.
   - Ejecutar `npm start` para iniciar el cliente en modo desarrollo.

#### Para técnicos que instalan la aplicación
1. **Requisitos previos**:
   - Servidor con Java y Spring Boot configurados.
   - Base de datos SQL en ejecución.
   - Entorno de ejecución Node.js para el cliente.
2. **Despliegue**:
   - Copiar los archivos del servidor al entorno de producción.
   - Configurar variables de entorno necesarias.
   - Iniciar el servidor con `java -jar app.jar`.
   - Subir el frontend a un servidor web y configurar el dominio.

### 7.2 Manual de Usuario
#### Explicación del uso de la aplicación
1. **Registro e inicio de sesión**:
   - Crear una cuenta ingresando email, contraseña y ubicación.
   - Confirmar el correo electrónico para activar la cuenta.
   - Iniciar sesión con las credenciales registradas.
2. **Exploración y participación en actividades**:
   - Navegar por la lista de actividades disponibles.
   - Filtrar por categoría, ubicación y fecha.
   - Unirse a actividades con un solo clic.
3. **Creación de actividades**:
   - Acceder a la sección "Crear actividad".
   - Completar los detalles como nombre, ubicación, fecha y número de participantes.
   - Publicar la actividad para que otros usuarios se unan.
4. **Interacción con otros usuarios**:
   - Acceder al chat en tiempo real de cada actividad.
   - Enviar mensajes y compartir detalles con los participantes.
5. **Calificación y comentarios**:
   - Valorar la actividad y los participantes una vez finalizada.
   - Escribir comentarios sobre la experiencia.

Este manual proporciona una guía clara tanto para la instalación de la aplicación como para su uso por parte de los usuarios.

### 7.3. Manual de Instalación(API)
#### Requisitos Previos
- Java 17 instalado
- Maven instalado
- MySQL configurado

#### Pasos de Instalación
1. Clona el repositorio:
   ```sh
   git clone https://github.com/raulJD13/Backend.git
   cd TerraSplash
   ```
2. Configura `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/terrasplash
   spring.datasource.username=<TU_USUARIO>
   spring.datasource.password=<TU_CONTRASEÑA>
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Ejecuta el proyecto con Maven:
   ```sh
   mvn spring-boot:run
   ```
4. Accede a la API en `http://localhost:8083`

---

### 7.4. Manual de Usuario API
#### Crear un nuevo deporte
**Método:** POST  
**Endpoint:** `/api/deportes`

**Cuerpo de la solicitud (JSON):**
```json
{
    "nombre": "yoga",
    "tipo": "tierra",
    "imagen": "yoga.jpg"
}
```

**Respuesta:**
```json
{
    "idDeporte": 18,
    "nombre": "yoga",
    "tipo": "tierra",
    "imagen": "yoga.jpg"
}
```

#### Ver todos los deportes
**Método:** GET  
**Endpoint:** `/api/deportes`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/3.png)

#### Modificar un deporte
**Método:** PUT  
**Endpoint:** `/api/deportes/{idDeporte}`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/4.png) 

#### Eliminar un deporte
**Método:** DELETE  
**Endpoint:** `/api/deportes/{idDeporte}`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/6.png) 

---

#### Ver actividades de un deporte específico
**Método:** GET  
**Endpoint:** `/api/actividades/deporte/{idDeporte}`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/8.png)

#### Ver una actividad específica
**Método:** GET  
**Endpoint:** `/api/actividades/deporte/{id}`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/9.png)

#### Crear una nueva actividad
**Método:** POST  
**Endpoint:** `/api/actividades`


**Cuerpo de la solicitud (JSON):**
```json
{
    "nombre": "Senderismo en la montaña",
    "descripcion": "Ruta de 10 km con vistas panorámicas.",
    "deporteId": 5,
    "fecha": "2025-06-15T10:00:00Z"
}
```
![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/10.png)


#### Actualizar una actividad existente
**Método:** PUT  
**Endpoint:** `/api/actividades/{id}`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/12.png)

#### Borrar una actividad existente
**Método:** DELETE  
**Endpoint:** `/api/actividades/{id}`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/14.png)

#### Ver todas las actividades
**Método:** GET
**Endpoint** `/api/actividades`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/15.png) 

---

#### Iniciar sesión y obtener token de autenticación
**Método:** POST  
**Endpoint:** `/api/usuarios/login`

**Cuerpo de la solicitud (JSON):**
```json
{
    "email": "usuario@example.com",
    "password": "123456"
}
```

**Respuesta:**
```json
{
    "token": "eyJhbGciOiJIUzI1..."
}
```
![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/16.png)

#### Obtener lista de usuarios
**Método:** GET  
**Endpoint:** `/api/usuarios`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/17.png)

#### Obtener un usuario específico
**Método:** GET  
**Endpoint:** `/api/usuarios/{id}`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/18.png)

#### Crear un nuevo usuario con su token
**Método:** POST  
**Endpoint:** `/api/usuarios`

**Cuerpo de la solicitud (JSON):**
```json
{
    "nombre": "Juan Pérez",
    "email": "juan@example.com",
    "password": "segura123"
}
```
![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/19.png)

#### Actualizar un usuario existente
**Método:** PUT  
**Endpoint:** `/api/usuarios/{id}`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/20.png)

#### Eliminar un usuario existente
**Método:** DELETE  
**Endpoint:** `/api/usuarios/{id}`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/21.png)

---

#### Obtener lista de comentarios
**Método:** GET  
**Endpoint:** `/api/comentarios`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/23.png)

#### Obtener un comentario específico
**Método:** GET  
**Endpoint:** `/api/comentarios/{id}`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/24.png)

#### Crear un nuevo comentario asociado a un usuario
**Método:** POST  
**Endpoint:** `/api/comentarios?idUsuario={idUsuario}`

**Cuerpo de la solicitud (JSON):**
```json
{
    "contenido": "Excelente actividad, muy recomendada!",
    "fecha": "2025-02-17T12:34:56Z"
}
```

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/25.png)

#### Actualizar un comentario existente
**Método:** PUT  
**Endpoint:** `/api/comentarios/{id}`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/26.png)

#### Eliminar un comentario existente
**Método:** DELETE  
**Endpoint:** `/api/comentarios/{id}`

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/27.png)

![](https://github.com/raulJD13/Backend/blob/2ed0f8aab1b6092c4344cddf597b7e58b4d4f718/Images-Github/28.png)

---

Este documento cubre la documentación esencial para el backend de la API. Para más detalles, consultar el repositorio oficial.


### 7.5 Ayuda dentro de la App
- Instrucciones accesibles desde la propia aplicación.

#### DOCUMENTACIÓN DE AYUDA TERRASPLASH

**Índice:**

1. Introducción  
2. Capturas de pantalla y funcionamiento de la documentación  
3. Integración con la aplicación  
4. Código fuente  
5. Conclusiones  
6. Referencias  

**Introducción**

La aplicación está diseñada para facilitar la conexión entre personas que desean participar en actividades deportivas al aire libre sin tener que hacerlo solas. Los usuarios pueden unirse a grupos que planifican actividades juntas, promoviendo la socialización y la compañía en diversos entornos deportivos. Las actividades abarcan desde el senderismo y ciclismo hasta la escalada, kayak y snorkel, asegurando que los usuarios encuentren compañía para sus intereses específicos y disfruten de estas actividades de manera segura y divertida.

Para crear el sistema de ayuda de la aplicación, se ha elegido HelpNDoc, una herramienta robusta y eficiente para la creación de documentación. HelpNDoc permite generar ayudas contextuales, manuales de usuario y guías en diversos formatos, facilitando así el acceso a la información para los usuarios. La elección de HelpNDoc se debe a su facilidad de uso, capacidad de integrar multimedia y la posibilidad de generar documentación en múltiples idiomas, asegurando una experiencia de usuario inclusiva y completa.

**Capturas de pantalla y funcionamiento de la documentación**

Al iniciar la aplicación, si han pasado 3 segundos y no se ha realizado ninguna acción, aparecerá un ícono de información. Al presionarlo, el usuario será llevado al menú de manual de usuario, donde podrá buscar y aclarar todas sus dudas.

El manual de usuario se presenta con una estructura clara:

- A la izquierda, un índice con secciones detalladas sobre cómo ingresar a la aplicación, navegar por las páginas y utilizar el perfil de usuario.
- Instrucciones paso a paso con imágenes para una comprensión más intuitiva.
- Un buscador integrado para acceder rápidamente a temas específicos.

**Integración con la aplicación**

El sistema de ayuda está integrado de forma sencilla y accesible:
- Al permanecer 3 segundos en la pantalla de inicio, aparece un ícono de información en la esquina superior izquierda.
- Al presionarlo, el usuario es dirigido al manual de usuario, donde podrá consultar información detallada.

**Código fuente**

Para actualizar los estilos del manual de usuario, se han realizado ajustes en el código HTML generado por HelpNDoc:

```html
<style type="text/css">
      nav {
        width: 350px;
      }
      @media screen and (min-width: 769px) {
        body.md-nav-expanded div#main {
          margin-left: 350px;
        }
        body.md-nav-expanded header {
          padding-left: 364px;
        }
      }

      .navigation #inline-toc {
        width: auto !important;
      }
      body {
        background-color: #f8f8f8 !important;
      }
      header {
        background-color: #f0f8ff; 
        padding: 15px;
        text-align: center;
      }
      nav#panel-left {
        background-color: #fff0f5 !important;
      }
</style>
```

Para integrar la documentación en la aplicación:
- Se convirtió la documentación de HelpNDoc a HTML.
- Se copió el archivo HTML a la carpeta `public` de la aplicación.
- Se enlazó mediante un `href`, nombrando el archivo como "DocumentacionAyuda" para facilitar su acceso.

**Conclusiones**

Es fundamental reconocer la importancia de la documentación como herramienta clave para la capacitación y el soporte al usuario. Se han seguido buenas prácticas como:
- Uso de lenguaje claro y conciso.
- Incorporación de gráficos y diagramas.
- Estructuración del contenido de manera lógica y accesible.
- Inclusión de un sistema de búsqueda eficiente.

Además, se recomienda mantener la documentación actualizada y adaptada a las necesidades cambiantes de los usuarios. La retroalimentación de los usuarios puede mejorar significativamente la calidad y la relevancia de los manuales.

**Testimonios**

> "HelpNDoc es una herramienta extremadamente útil para crear documentación sin complicaciones innecesarias. Su estructura de herencia de estilos facilita la organización del contenido, y sin duda la utilizaré en futuros proyectos." – Raúl

> "Tras completar esta tarea, me he dado cuenta de que HelpNDoc simplifica enormemente la generación de documentación sin necesidad de servidores complicados o procesos tediosos. Su integración con nuestra aplicación ha sido fluida y eficaz." – Eduardo Vega

Imagenes:

![](https://github.com/raulJD13/Backend/blob/db0f423164e5cfd7355940a2cdd7894fed19de8b/Images-Github/Captura%20de%20pantalla%202025-02-16%20a%20las%2013.46.51.png)
![](https://github.com/raulJD13/Backend/blob/db0f423164e5cfd7355940a2cdd7894fed19de8b/Images-Github/Captura%20de%20pantalla%202025-02-16%20a%20las%2013.47.12.png)
![](https://github.com/raulJD13/Backend/blob/db0f423164e5cfd7355940a2cdd7894fed19de8b/Images-Github/Captura%20de%20pantalla%202025-02-16%20a%20las%2013.47.38.png)


---

## 8. Tests de Prueba

### 8.1 Backend

Las pruebas realizadas tienen como objetivo garantizar el correcto funcionamiento de los controladores del backend, asegurando que devuelvan los datos esperados y que interactúen correctamente con los servicios correspondientes.

#### 8.1.1 Pruebas en `ActividadController`

- **`testGetAllActividades`**:
  - Prueba que el método `getAllActividades` del controlador retorne la lista completa de actividades.
  - Se verifica que el servicio `ActividadService` es invocado una vez.
  
  ```java
  @Test
  void testGetAllActividades() {
      when(actividadService.getAllActividades()).thenReturn(Arrays.asList(actividad1, actividad2));

      List<Actividad> actividades = actividadController.getAllActividades();

      assertNotNull(actividades);
      assertEquals(2, actividades.size());
      verify(actividadService, times(1)).getAllActividades();
  }
  ```

- **`testGetActividadById`**:
  - Prueba que el método `getActividadById` retorne la actividad correspondiente al ID proporcionado.
  - Se verifica que el servicio es invocado correctamente y que los datos de la actividad coinciden con los esperados.
  
  ```java
  @Test
  void testGetActividadById() {
      when(actividadService.getActividadById(1L)).thenReturn(actividad1);

      Actividad actividad = actividadController.getActividadById(1L);

      assertNotNull(actividad);
      assertEquals("Fútbol", actividad.getNombre());
      verify(actividadService, times(1)).getActividadById(1L);
  }
  ```

#### 8.1.2 Pruebas en `ComentarioController`

- **`testGetAllComentarios`**:
  - Valida que el controlador retorne correctamente todos los comentarios disponibles.
  - Se comprueba que el servicio es llamado una vez y que los datos son correctos.

- **`testGetAllComentarios`**:
  ```java
  @Test
  void testGetAllComentarios() {
      when(comentarioService.getAllComentarios()).thenReturn(Arrays.asList(comentario1, comentario2));

      List<Comentario> comentarios = comentarioController.getAllComentarios();

      assertNotNull(comentarios);
      assertEquals(2, comentarios.size());
      verify(comentarioService, times(1)).getAllComentarios();
  }
  ```

- **`testGetComentarioById`**:
  - **`testGetComentarioById`**:
  - Prueba la recuperación de un comentario por su ID.
  - Se verifica la consistencia de los datos retornados.

  ```java
  @Test
  void testGetComentarioById() {
      when(comentarioService.getComentarioById(1L)).thenReturn(comentario1);

      Comentario comentario = comentarioController.getComentarioById(1L);

      assertNotNull(comentario);
      assertEquals("Texto 1", comentario.getTexto());
      verify(comentarioService, times(1)).getComentarioById(1L);
  }
  ```

#### 8.1.3 Pruebas en `DeporteController`

- **`testGetAllDeportes`**:
  - Prueba que el controlador obtiene todos los deportes correctamente.
  - Se verifica la cantidad de elementos devueltos y la llamada al servicio.

- **`testGetAllDeportes`**:
  ```java
  @Test
  void testGetAllDeportes() {
      when(deporteService.getAllDeportes()).thenReturn(Arrays.asList(deporte1, deporte2));

      List<Deporte> deportes = deporteController.getAllDeportes();

      assertNotNull(deportes);
      assertEquals(2, deportes.size());
      verify(deporteService, times(1)).getAllDeportes();
  }
  ```

- **`testGetDeporteById`**:
  - **`testGetDeporteById`**:
  - Se evalúa la correcta recuperación de un deporte específico.
  - Se valida que los datos devueltos coincidan con los esperados.

  ```java
  @Test
  void testGetDeporteById() {
      when(deporteService.getDeporteById(1L)).thenReturn(deporte1);

      Deporte deporte = deporteController.getDeporteById(1L);

      assertNotNull(deporte);
      assertEquals("Fútbol", deporte.getNombre());
      verify(deporteService, times(1)).getDeporteById(1L);
  }
  ```

#### 8.1.4 Pruebas en `UsuarioController`

- **`testGetAllUsuarios`**:
  - Asegura que el controlador devuelve la lista completa de usuarios.
  - Se verifica la cantidad de elementos y que el servicio es llamado correctamente.

- **`testGetAllUsuarios`**:
  ```java
  @Test
  void testGetAllUsuarios() {
      when(usuarioService.getAllUsuarios()).thenReturn(Arrays.asList(usuario1, usuario2));

      List<Usuario> usuarios = usuarioController.getAllUsuarios();

      assertNotNull(usuarios);
      assertEquals(2, usuarios.size());
      verify(usuarioService, times(1)).getAllUsuarios();
  }
  ```

- **`testGetUsuarioById`**:
  - **`testGetUsuarioById`**:
  - Evalúa la correcta obtención de un usuario por ID.
  - Se verifica que los datos retornados son los esperados.

  ```java
  @Test
  void testGetUsuarioById() {
      when(usuarioService.getUsuarioById(1L)).thenReturn(usuario1);

      Usuario usuario = usuarioController.getUsuarioById(1L);

      assertNotNull(usuario);
      assertEquals("user1@example.com", usuario.getEmail());
      verify(usuarioService, times(1)).getUsuarioById(1L);
  }
  ```

Estos tests garantizan un correcto funcionamiento del backend, asegurando que las respuestas de los controladores sean consistentes con los datos esperados y que las interacciones con los servicios sean adecuadas.


### 8.2 Frontend

#### Pruebas realizadas para asegurar la estabilidad y usabilidad de la interfaz

##### Herramienta de Test

Para realizar las pruebas de la aplicación, se eligió la biblioteca Testing Library junto con Jest.

- **Testing Library**: Una herramienta enfocada en realizar pruebas basadas en la interacción del usuario con la interfaz de usuario. Esta biblioteca proporciona utilidades para seleccionar elementos del DOM, simular eventos y validar resultados, asegurando que la aplicación se comporte como lo haría en un entorno real.
- **Jest**: Es el entorno de pruebas integrado utilizado por defecto con React. Jest proporciona capacidades como mocks, espiado de funciones, pruebas asíncronas y generación de informes de cobertura.

Ambas herramientas, en conjunto, permiten realizar pruebas completas y enfocadas en la experiencia del usuario y la funcionalidad del sistema.

##### Instalación y configuración

La instalación y configuración de las herramientas se realizó de la siguiente manera:

- Instalación de dependencias necesarias:

  El proyecto ya incluye en el archivo `package.json` las bibliotecas relacionadas con pruebas, como:

  - `@testing-library/react`: Proporciona herramientas para realizar pruebas en componentes React.
  - `@testing-library/jest-dom`: Añade funcionalidades adicionales para pruebas de aserciones en el DOM.
  - `@testing-library/user-event`: Facilita la simulación de eventos de usuario.
  - `react-scripts`: Incluye soporte integrado para Jest.

- **Ejecutar las pruebas:**

  Las pruebas se ejecutan con el siguiente comando:

  ```sh
  npm test
  ```

#### Pruebas Realizadas

##### Descripción general de las pruebas

En esta sección se describen de manera general las pruebas realizadas para garantizar el correcto funcionamiento de la aplicación web. Las pruebas abarcan tanto la navegación entre rutas como la interacción con componentes individuales. Se empleó la librería `@testing-library/react` y Jest para la implementación y ejecución de estas pruebas, asegurando que los componentes cumplan con los requisitos funcionales y de diseño.

Se probaron los siguientes aspectos principales:

- **Renderizado correcto de componentes con props proporcionadas.**
- **Comportamiento esperado ante eventos de usuario, como clics y navegación.**
- **Correcta aplicación de estilos y atributos.**
- **Validación de lógica interna de los componentes, como el manejo de estado y funciones callback.**

##### Lista de métodos probados

###### Navegación y Rutas

- **Archivo:** `App.js`
- **Prueba:** Renderiza la ruta `/login` mostrando el componente `LoginPage`.
- **Tecnología:** Mock de `react-router-dom` con `MemoryRouter`.

###### Componentes Individuales

- **Componente:** `EventWeek`

  - **Pruebas:**
    - Renderizado correcto con props básicas (nombre, ubicación, precio, descripción, imagen, etc.).
    - Navegación a la ruta proporcionada al hacer clic en el botón "More Info".
    - Cambio del estado de "bookmark" al hacer clic en el ícono correspondiente.
  - **Mock:** `useNavigate` de `react-router-dom`.

- **Componente:** `ActivitiesCard`

  - **Pruebas:**
    - Renderizado correcto con props básicas (imagen, texto, índice).
    - Llamada a la función `onClick` cuando se hace clic en la tarjeta.

- **Componente:** `Title`

  - **Pruebas:**
    - Renderizado correcto del texto proporcionado.
    - Manejo de casos con texto vacío.

- **Componente:** `PlaceCard`

  - **Pruebas:**
    - Renderizado correcto del nombre, imagen, estrellas de calificación y estado de "bookmark".
    - Navegación al hacer clic en la tarjeta.
    - Llamada a la función `onToggleBookmark` al hacer clic en el ícono de marcador.

- **Componente:** `Header`

  - **Pruebas:**
    - Renderizado correcto del logo y nombre de la aplicación.
    - Navegación a la página de inicio al hacer clic en el logo.
    - Navegación a la página de perfil al hacer clic en el ícono de usuario.
    - Renderizado del ícono de perfil predeterminado o la imagen de perfil del usuario logueado.
  - **Mock:** `useAuth` para simular el estado del usuario.

##### Resultados de las Pruebas

###### Test de renderización de rutas en `App.js`

**Descripción:** Verifica que los componentes correctos se renderizan según la ruta proporcionada.

```js
import React from "react";
import { render, screen } from "@testing-library/react";
import App from "./App";

jest.mock("./pages/loginPage/LoginPage", () => () => <div>Mock LoginPage</div>);
jest.mock("react-router-dom", () => {
  const actual = jest.requireActual("react-router-dom");
  return {
    ...actual,
    BrowserRouter: ({ children }) => (
      <actual.MemoryRouter initialEntries={["/login"]}>
        {children}
      </actual.MemoryRouter>
    ),
  };
});

test("renderiza LoginPage en la ruta /login", () => {
  render(<App />);
  expect(screen.getByText("Mock LoginPage")).toBeInTheDocument();
});
```

###### Test del componente `EventWeek`

- **Test 1:** Verifica la renderización correcta de las props básicas, como nombre, imagen, descripción y precio.
- **Test 2:** Asegura que el botón "More Info" redirige a la ruta especificada.

```js
import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import EventWeek from "./EventWeek";
import { useNavigate } from "react-router-dom";

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: jest.fn(),
}));

test("renderiza correctamente con las props básicas", () => {
  const mockProps = {
    id: 1,
    image: "https://via.placeholder.com/150",
    name: "Evento de prueba",
    location: "Madrid",
    price: 20,
    rating: 4,
    description: "Este es un evento increíble.",
    route: "/event/1",
    isBookmarked: false,
    onBookmarkToggle: jest.fn(),
  };

  render(
    <MemoryRouter>
      <EventWeek {...mockProps} />
    </MemoryRouter>
  );

  expect(screen.getByText("Evento de prueba")).toBeInTheDocument();
});

test("el botón 'More Info' navega a la ruta correcta", () => {
  const mockNavigate = jest.fn();
  jest.mocked(useNavigate).mockReturnValue(mockNavigate);
  
  render(
    <MemoryRouter>
      <EventWeek {...mockProps} />
    </MemoryRouter>
  );

  fireEvent.click(screen.getByText("More Info"));
  expect(mockNavigate).toHaveBeenCalledWith(mockProps.route);
});
```
###### Test del componente `ActivitiesCard`

- **Test 1:** Comprueba que el componente renderiza correctamente el fondo y el texto.
- **Test 2:** Comprueba que el evento `onClick` se dispara al interactuar con la tarjeta.

```js
import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import ActivitesCard from "./ActivitiesCard";

describe("ActivitiesCard", () => {
  const mockProps = {
    imageUrl: "https://example.com/image.jpg",
    text: "Test Activity",
    onClick: jest.fn(),
    index: 2,
  };

  test("se renderiza con la imagen, texto y estilos correctos", () => {
    render(<ActivitesCard {...mockProps} />);

    const card = screen.getByTestId("activities-card");
    expect(card).toHaveStyle(`background-image: url(${mockProps.imageUrl})`);
    expect(card).toHaveStyle(`animation-delay: ${mockProps.index * 0.2}s`);

    const textElement = screen.getByText(mockProps.text);
    expect(textElement).toBeInTheDocument();
  });

  test("llama a la funciÃ³n onClick cuando se hace clic", () => {
    render(<ActivitesCard {...mockProps} />);

    const card = screen.getByTestId("activities-card");
    fireEvent.click(card);

    expect(mockProps.onClick).toHaveBeenCalled();
  });
});
```

###### Test del componente `Header`

- **Test 1:** Verifica la navegaciÃ³n al hacer clic en el logo para redirigir a la pÃ¡gina de inicio.
- **Test 2:** Comprueba que el Ã­cono de perfil navega correctamente a la pÃ¡gina de perfil del usuario.

```js
import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import Header from "./Header";
import "@testing-library/jest-dom/extend-expect";
import { BrowserRouter as Router } from "react-router-dom";

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: jest.fn(),
}));

jest.mock("../../contexts/AuthContext", () => ({
  useAuth: jest.fn(),
}));

describe("Componente Header", () => {
  const navigateMock = jest.fn();
  const currentUserMock = {
    profileImage: "/images/user-profile.jpg",
  };

  beforeEach(() => {
    jest.clearAllMocks();
    require("react-router-dom").useNavigate.mockReturnValue(navigateMock);
    require("../../contexts/AuthContext").useAuth.mockReturnValue({
      currentUser: currentUserMock,
    });
  });

  test("navega a la pÃ¡gina de inicio al hacer clic en el logo", () => {
    render(
      <Router>
        <Header />
      </Router>
    );
    const logoElement = screen.getByAltText("TerraSplash Logo");
    fireEvent.click(logoElement);

    expect(navigateMock).toHaveBeenCalledWith("/home");
  });

  test("navega a la pÃ¡gina de perfil al hacer clic en el Ã­cono de usuario", () => {
    render(
      <Router>
        <Header />
      </Router>
    );
    const userProfileImage = screen.getByAltText("User Profile");
    fireEvent.click(userProfileImage);

    expect(navigateMock).toHaveBeenCalledWith("/profile");
  });
});
```

###### Test del componente `PlaceCard`

- **Test 1:** ComprobaciÃ³n que las estrellas de rating se renderizan correctamente.
- **Test 2:** ComprobaciÃ³n que el Ã­cono de bookmark puede activarse o desactivarse.

```js
import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import PlaceCard from "./PlaceCard";
import "@testing-library/jest-dom/extend-expect";

describe("Componente PlaceCard", () => {
  const onClickMock = jest.fn();
  const onToggleBookmarkMock = jest.fn();

  test("renderiza correctamente las estrellas de rating", () => {
    render(<PlaceCard name="Playa Bonita" rating={4} imageUrl="playa.jpg" onClick={onClickMock} isBookmarked={false} onToggleBookmark={onToggleBookmarkMock} />);
    const filledStars = screen.getAllByTestId("star-filled");
    const outlinedStars = screen.getAllByTestId("star-outlined");
    expect(filledStars.length).toBe(4);
    expect(outlinedStars.length).toBe(1);
  });

  test("activa el manejador onToggleBookmark al hacer clic en el Ã­cono de marcador", () => {
    render(<PlaceCard name="Playa Bonita" rating={4} imageUrl="playa.jpg" onClick={onClickMock} isBookmarked={false} onToggleBookmark={onToggleBookmarkMock} />);
    const bookmarkIcon = screen.getByTestId("bookmark-icon");
    fireEvent.click(bookmarkIcon);
    expect(onToggleBookmarkMock).toHaveBeenCalledTimes(1);
  });
});
```

###### Test del componente `Title`

- **Test 1:** Comprobar que renderiza el texto proporcionado correctamente.
- **Test 2:** Comprobar que renderiza correctamente con un texto vacÃ­o.

```js
import React from "react";
import { render, screen } from "@testing-library/react";
import Title from "./Title";

describe("Title Component", () => {
  test("renderiza correctamente el texto proporcionado", () => {
    const testText = "Hola Hila!";
    render(<Title text={testText} />);
    const titleElement = screen.getByTestId("title");
    expect(titleElement).toBeInTheDocument();
    expect(titleElement).toHaveTextContent(testText);
    expect(titleElement).toHaveClass("selection-word");
  });

  test("renderiza correctamente con un texto vacÃ­o", () => {
    render(<Title text="" />);
    const titleElement = screen.getByTestId("title");
    expect(titleElement).toBeInTheDocument();
    expect(titleElement).toHaveTextContent("");
    expect(titleElement).toHaveClass("selection-word");
  });
});
```


---

## 9. Pila Tecnológica

Este proyecto ha sido desarrollado utilizando las siguientes tecnologías:

### 1. **Java 17**
Java 17 es la versión LTS más reciente de Java, lo que proporciona estabilidad a largo plazo. Se eligió Java 17 por su rendimiento, nuevas características como `Pattern Matching`, `Sealed Classes` y mejoras en la gestión de memoria, lo cual es ideal para aplicaciones empresariales de alta demanda. Además, al ser una versión LTS, asegura actualizaciones y soporte durante varios años, lo que la convierte en una opción confiable y segura.

### 2. **Spring Boot 2.7**
Spring Boot 2.7 fue elegido por su capacidad para simplificar la configuración y el desarrollo de aplicaciones Java basadas en Spring. Spring Boot es una de las soluciones más populares para el desarrollo de aplicaciones backend, y permite crear servicios RESTful de forma eficiente, con mínima configuración. La versión 2.7 incluye mejoras de rendimiento, soporte para Java 17, actualizaciones de seguridad, y nuevas funcionalidades para facilitar el desarrollo de aplicaciones robustas y escalables.

### 3. **JPA (Hibernate)**
Hibernate, a través de Java Persistence API (JPA), es el marco de trabajo utilizado para la persistencia de datos. JPA simplifica el trabajo con bases de datos relacionales al permitir que las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) se realicen de manera sencilla mediante objetos Java, sin necesidad de escribir consultas SQL complejas. Hibernate, como implementación de JPA, proporciona características avanzadas como el manejo de transacciones, el caching de entidades y la optimización de las consultas.

### 4. **MySQL**
MySQL fue elegido como sistema de gestión de bases de datos debido a su popularidad, fiabilidad y facilidad de integración con Java y Spring Boot. Es una base de datos relacional de código abierto ampliamente utilizada en aplicaciones web y sistemas de producción. Además, MySQL ofrece alta disponibilidad, replicación de bases de datos y soporte para operaciones transaccionales, lo que lo hace adecuado para aplicaciones que requieren consistencia y robustez.

### 5. **Maven**
Maven es la herramienta de gestión de dependencias utilizada en este proyecto. Permite gestionar las bibliotecas y frameworks necesarios para el proyecto de manera eficiente, así como facilitar la construcción y el empaquetado del proyecto. Maven también se utiliza para automatizar la gestión del ciclo de vida del proyecto (compilación, pruebas, empaquetado, etc.), asegurando que las dependencias estén correctamente gestionadas y actualizadas.

---

## 10. Comparación de Tecnologías

### **Java 17 vs Otras versiones de Java (Java 8, 11, etc.)**
- **Java 8**: Java 8 fue una versión muy importante, introduciendo características como expresiones lambda y Streams. Sin embargo, Java 17 ofrece mejoras significativas en términos de rendimiento, nuevas características y soporte extendido. Java 17 es la versión recomendada para aplicaciones a largo plazo debido a su estabilidad y soporte.
- **Java 11**: Java 11 es también una versión LTS, pero Java 17 trae nuevas características como `sealed classes` y mejoras de rendimiento en el Garbage Collector que no están presentes en Java 11.

**Justificación de Elección**: Optamos por Java 17 por su rendimiento mejorado, características avanzadas y soporte a largo plazo (LTS), lo que asegura que la aplicación se mantenga actualizada y compatible con nuevas versiones del JDK.

### **Spring Boot 2.7 vs Spring Boot 2.5/2.6**
- **Spring Boot 2.5/2.6**: Estas versiones también ofrecen capacidades poderosas para crear aplicaciones web y servicios RESTful, pero no incluyen algunas de las nuevas características y mejoras de rendimiento presentes en la versión 2.7.
- **Spring Boot 2.7**: Ofrece soporte para Java 17, mejoras de seguridad, optimización de rendimiento y nuevas características como `Spring Security 5.8`, lo que permite desarrollar aplicaciones más seguras y rápidas.

**Justificación de Elección**: Elegimos Spring Boot 2.7 debido a su compatibilidad con Java 17 y su conjunto de características adicionales que mejoran la seguridad y el rendimiento, lo que es fundamental para la estabilidad de la API.

### **JPA (Hibernate) vs JDBC**
- **JDBC**: Es una forma de interactuar directamente con bases de datos utilizando SQL en el código Java. Requiere más esfuerzo para la gestión de conexiones y transacciones.
- **JPA (Hibernate)**: JPA simplifica la persistencia de datos mediante la abstracción de las interacciones con la base de datos, permitiendo que las operaciones se realicen con objetos Java en lugar de consultas SQL explícitas. Hibernate ofrece características avanzadas como el manejo de caché, optimización de consultas y facilidad de integración con Spring.

**Justificación de Elección**: JPA con Hibernate fue elegido por su capacidad de simplificar la persistencia de datos y automatizar la gestión de transacciones, lo que reduce la cantidad de código repetitivo y facilita el desarrollo de la API.

### **MySQL vs PostgreSQL**
- **PostgreSQL**: Es una base de datos relacional muy potente, con un enfoque en la consistencia de los datos y la compatibilidad con transacciones ACID. Es ideal para aplicaciones que requieren características avanzadas como el soporte de JSON y tipos de datos personalizados.
- **MySQL**: Si bien PostgreSQL es muy poderoso, MySQL sigue siendo una opción más ligera y fácil de gestionar para aplicaciones con menos necesidades complejas de bases de datos. MySQL también ofrece una excelente integración con Java y Spring.

**Justificación de Elección**: Se eligió MySQL por su simplicidad, fiabilidad y facilidad de integración con Java y Spring, además de ser ampliamente utilizado y documentado.

### **Maven vs Gradle**
- **Gradle**: Gradle es otra herramienta popular de gestión de dependencias y construcción, conocida por su flexibilidad y rendimiento superior en proyectos más grandes.
- **Maven**: Maven es más estructurado y tiene una curva de aprendizaje más baja, siendo ampliamente utilizado en proyectos de Java y Spring Boot.

**Justificación de Elección**: Maven fue elegido debido a su simplicidad, la vasta comunidad que lo respalda, y su integración sin problemas con Spring Boot, permitiendo una configuración sencilla y efectiva.

---

Esta comparación de tecnologías fue realizada para garantizar la elección de las herramientas más adecuadas y eficientes para el desarrollo de esta API. Las elecciones fueron tomadas con el objetivo de ofrecer una solución robusta, escalable y fácil de mantener en el futuro.


---

## 11. Repositorios
- FrontEnd: https://github.com/raulJD13/Frontend.git
- BackEnd: https://github.com/raulJD13/Backend.git
- Proyecto en react: https://github.com/raulJD13/TerraSplash2.0.git

---
## 12. Planificación y Organización

En el desarrollo de este proyecto, hemos adoptado metodologías ágiles para asegurar una gestión eficiente y flexible a lo largo del ciclo de vida del proyecto. Esto nos permite adaptarnos rápidamente a los cambios y asegurar que el producto final cumpla con los requisitos y expectativas del cliente.

### Metodología Ágil
Utilizamos **Scrum** como metodología ágil para gestionar el proyecto. Scrum nos permite dividir el trabajo en **sprints**, que son ciclos de desarrollo cortos (generalmente de dos semanas). En cada sprint, establecemos objetivos claros que deben cumplirse al final del ciclo. Este enfoque iterativo y incremental nos permite entregar funcionalidad de manera continua, mientras mantenemos un alto nivel de calidad.

#### Principales elementos de Scrum:
- **Sprint Planning**: Al inicio de cada sprint, se realiza una planificación para definir las tareas que se abordarán durante ese ciclo. Las tareas se desglosan en historias de usuario y se priorizan en función de su importancia y el valor que aportan al proyecto.
- **Daily Standups**: Reuniones diarias donde los miembros del equipo informan sobre el progreso de sus tareas, los obstáculos encontrados y las tareas que realizarán al día siguiente. Esto ayuda a detectar posibles problemas y solucionarlos rápidamente.
- **Sprint Review**: Al final de cada sprint, se realiza una revisión del trabajo realizado. Se presenta el avance y se recibe retroalimentación para ajustar la dirección del proyecto.
- **Sprint Retrospective**: Después de la revisión, el equipo reflexiona sobre el proceso, identifica áreas de mejora y establece acciones para mejorar la eficiencia y calidad en el próximo sprint.

### Herramientas Utilizadas
Para gestionar el proyecto y mantener la colaboración eficiente, utilizamos varias herramientas que nos ayudan a organizar el trabajo, gestionar el código fuente y hacer seguimiento del progreso:

#### 1. **Git**
Usamos **Git** como sistema de control de versiones para gestionar el código fuente. Git nos permite realizar un seguimiento detallado de todos los cambios en el código, facilitando la colaboración entre los miembros del equipo y asegurando que las versiones del proyecto se mantengan organizadas.

Las principales prácticas que seguimos con Git incluyen:
- **Ramas (branches)**: Creamos ramas para cada característica o tarea, asegurando que los cambios se realicen de manera aislada y evitando conflictos entre diferentes funcionalidades.
- **Commits frecuentes**: Realizamos commits pequeños y frecuentes para garantizar que los cambios sean fácilmente rastreables y revertibles si es necesario.
- **Pull Requests (PRs)**: Cuando una tarea o característica está lista, se crea un pull request para revisión. Esto permite que otros miembros del equipo revisen el código antes de fusionarlo en la rama principal.

#### 2. **GitHub**
**GitHub** es la plataforma de desarrollo que usamos para alojar el código y gestionar el repositorio. Además de ser una plataforma de alojamiento de código, GitHub facilita la colaboración entre los miembros del equipo a través de características como:
- **Issues**: Utilizamos los "issues" para hacer un seguimiento de las tareas y problemas pendientes. Cada tarea se describe como un "issue" con su prioridad y la persona asignada para su resolución.
- **Proyectos (GitHub Projects)**: Utilizamos GitHub Projects para organizar el trabajo de manera visual. Esta herramienta nos permite crear tableros Kanban, donde cada tarea puede moverse entre las diferentes etapas del proyecto (Pendiente, En progreso, Completada).
- **Wiki de GitHub**: Usamos la sección Wiki de GitHub para documentar aspectos importantes del proyecto, como las decisiones de diseño, instrucciones de uso, configuración y pruebas, asegurando que todos los miembros del equipo tengan acceso a la información relevante.

#### 3. **Jira (Opcional)**
En algunos casos, podemos complementar la gestión con herramientas como **Jira** para una gestión más detallada de las tareas y el seguimiento del progreso en un entorno más estructurado, si el equipo lo considera necesario.

#### 4. **Slack**
La comunicación constante es clave en las metodologías ágiles, por lo que utilizamos **Slack** como herramienta principal de comunicación en el equipo. Slack facilita la colaboración en tiempo real a través de canales temáticos y mensajes directos, lo que permite que todos los miembros del equipo estén al tanto de los avances, dudas y posibles problemas.

### Planificación de Tareas y Seguimiento
Las tareas se organizan en el backlog del proyecto, donde se priorizan según su importancia y su valor para el negocio. Durante cada sprint, se seleccionan las tareas del backlog que se abordarán en el ciclo, y al final del sprint se evalúa el progreso. Las historias de usuario se descomponen en tareas más pequeñas que pueden completarse dentro de un sprint.


![](https://github.com/raulJD13/Backend/blob/37f223461334e0a30ae81e5f3f3c16af6f3a43ee/Images-Github/Captura%20de%20pantalla%202025-02-16%20a%20las%2015.50.35.png)

Cada tarea tiene un estimado de esfuerzo, generalmente calculado en horas o puntos de historia. Esto permite a los desarrolladores gestionar su tiempo y garantizar que se cumplan los objetivos del sprint.

---

Este enfoque de planificación y organización basado en metodologías ágiles nos permite mantener un control claro sobre el progreso del proyecto, adaptarnos rápidamente a los cambios y entregar un producto de alta calidad en cada sprint.

---

## 13. Conclusiones y Reflexiones
Desarrollar este proyecto ha sido una experiencia enriquecedora, especialmente al trabajar en grupo por primera vez en un proyecto de esta magnitud. Desde el inicio, enfrentamos el reto de coordinar nuestras ideas, dividir tareas y encontrar una forma eficiente de comunicarnos. Aprendimos la importancia de la organización y el uso de herramientas colaborativas para mantener un flujo de trabajo claro y ordenado.

Uno de los mayores aprendizajes fue la necesidad de adaptarnos a diferentes formas de trabajo y solucionar problemas en equipo. No siempre fue fácil ponernos de acuerdo, pero al final, logramos integrar nuestras habilidades y conocimientos para construir una aplicación funcional. Además, trabajar con tecnologías como Spring Boot, Angular, y bases de datos SQL nos permitió reforzar conceptos y enfrentar desafíos técnicos reales.

Más allá de lo técnico, este proyecto nos dejó la enseñanza de que el desarrollo de software no es solo escribir código, sino también comunicación, planificación y toma de decisiones. Sin duda, esta experiencia nos preparó para futuros proyectos y nos dio una mejor perspectiva sobre el trabajo en equipo dentro del mundo del desarrollo de software.

---

## 14. Enlaces y Referencias
- [CertiDevs - Tutorial Spring Boot ReactJS](https://certidevs.com/tutorial-spring-boot-reactjs)
- [TheServerSide - Hibernate SessionFactory Example](https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/3-ways-to-build-a-Hibernate-SessionFactory-in-Java-by-example)
- [YouTube - Video sobre Hibernate y Java](https://www.youtube.com/watch?v=74sClDEYSQ4)
- [Parasoft - Tutorial de JUnit](https://es.parasoft.com/blog/junit-tutorial-setting-up-writing-and-running-java-unit-tests/)
- [Softtek - Testing unitario en Java](https://blog.softtek.com/es/testing-unitario)
- [Chuidiang - Ejemplo de JUnit](https://old.chuidiang.org/java/herramientas/test-automaticos/ejemplo-junit.php)
- [DataArt - Testing en Java](https://www.dataart.team/es/articles/testing-en-java)
- [Funciona en mi máquina - Pruebas unitarias con JUnit y Mockito](https://funcionaenmimaquina.com/aprende-a-crear-pruebas-unitarias-con-junit-y-mockito-en-15-minutos/)


## Anexos
- Plantilla de funciones del sistema (modelo ERS).
- Documentos adicionales de referencia.
- Guía rápida de uso de la aplicación.
- Especificaciones técnicas del sistema.
- Manual de instalación y configuración.
- Notas de la última reunión del equipo.
- Lista de control de calidad.
- Plan de pruebas y casos de uso.
- Diagramas de flujo del proceso.
- Informe de análisis de riesgos.
