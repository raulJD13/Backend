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

---

## 6. Interfaces
### 6.1 Diseño Inicial: Mokups y Prototipado
- Enlace compartido con la herramienta usada para el prototipado.

### 6.2 Usabilidad y Accesibilidad
- Aspectos clave de usabilidad considerados.
- Justificación del diseño en base a estos aspectos.
- Proceso de estudio previo de usabilidad y accesibilidad.
- Evaluación post-desarrollo con ejemplos visuales.

---

## 7. Manuales
### 7.1 Manual de Instalación
- Para desarrolladores (servidor y cliente).
- Para técnicos que instalan la aplicación.

### 7.2 Manual de Usuario
- Explicación del uso de la aplicación.

### 7.3 Ayuda dentro de la App
- Instrucciones accesibles desde la propia aplicación.

---

## 8. Tests de Prueba
### 8.1 Backend
- Pruebas realizadas para garantizar el correcto funcionamiento del servidor.

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
- Tecnologías utilizadas en el desarrollo del proyecto.

---

## 10. Comparación de Tecnologías
- Comparación de alternativas tecnológicas y justificación de las elecciones.

---

## 11. Repositorios
- FrontEnd: https://github.com/raulJD13/Frontend.git
- BackEnd: https://github.com/raulJD13/Backend.git
- Proyecto en react: https://github.com/raulJD13/TerraSplash2.0.git

---

## 12. Planificación y Organización
- Explicación sobre la metodología de organización y planificación.

---

## 13. Conclusiones y Reflexiones
- Opiniones auténticas sobre el desarrollo del proyecto.

---

## 14. Enlaces y Referencias
- Bibliografía, documentación y recursos utilizados.

## Anexos
- Plantilla de funciones del sistema (modelo ERS).
- Documentos adicionales de referencia.

