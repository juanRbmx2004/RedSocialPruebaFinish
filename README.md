INTRODUCCIÓN
El presente trabajo tiene la finalidad de proporcionar una aplicación móvil de tipo “Red social”, en un contexto universitario, donde se proporcionarán herramientas a los usuarios, que enriquecerán la vida estudiantil, con apartados para publicaciones generales, eventos, colaborador y otros.
La aplicación se codificó en lenguaje Java, mediante el IDE de “Android Studio”. Para el tratamiento de datos, se incorpora las API de Firebase, para hacer uso del inicio de sesión, storage y base de datos. 
NECESIDAD/JUSTIFICACIÓN
Dados los últimos casos vividos en Bogotá, con la gran cantidad de afectaciones al transporte ya sean por protestas, o los fuertes climas que azotan la ciudad, se pretendió crear un medio por el cual se dinamice la colaboración de los miembros de la universidad, facilitando la comunicación entre las personas, por ejemplo si alguien necesitaba ir hacia Kenedy, y no había transporte, podía publicar que necesitaba transportarse, y podía obtener respuesta de alguien con carro o moto con algún cupo libre.
Ya habiendo comunicación entre la comunidad, dejar ese espacio para solo ese enfoque, era limitarlo, por lo cual se integraron más espacios, abriendo más tópicos, los cuales agregaban diversidad, y complementaban la aplicación, también solucionando otro tipo de problemáticas.
Lo siguiente que se trató, la velocidad de difusión de algunos mensajes, por ejemplo, dar información de los múltiples eventos que hace la universidad, entonces también se abre un espacio para las publicaciones generales, dónde se puede hablar sobre temas de la universidad pero no únicamente de carácter académico.
Ya teniendo un espacio de comunicación, se planteó hacer algo más de entretenimiento, en este caso se implementó el tópico de “Chismes”, con el fin de que los usuarios puedan ser capaces de hacer publicaciones de manera anónima, así poder desahogarse y opinar sobre temas sin tener algún inconveniente.
Es una aplicación integra, que ofrece un amplio abanico de posibilidades para el usuario, y que complementa el día a día de un estudiante, y su vez le permite disfrutar de otras dinámicas para socializar.

REQUISITOS FUNCIONALES
Número de requerimiento	RF001
Nombre de requerimiento	Autenticación de usuarios
Tipo	Requisito
Fuente del requerimiento	Usuario
Proceso	La aplicación permite validar las credenciales del usuario a través de una pantalla de inicio de sesión.
Prioridad del requerimiento	Esencial

Número de requerimiento	RF002
Nombre de requerimiento	Edición Usuarios

Tipo	Requisito
Fuente del requerimiento	Administrador
Proceso	La aplicación permite crear, consultar, editar y eliminar usuarios.
Prioridad del requerimiento	Esencial

Número de requerimiento	RF003
Nombre de requerimiento	Prohibir duplicados
Tipo	Restricción
Fuente del requerimiento	Administrador
Proceso	La aplicación impide la creación de usuarios con el mismo con el mismo correo
Prioridad del requerimiento	Esencial

REQUISITOS NO FUNCIONALES

Número de requerimiento	RNF001
Nombre de requerimiento	Encriptación de claves
Tipo	Requisito
Fuente del requerimiento	Administrador
Proceso	La contraseña se almacena cifrada en la base de datos, esto con el fin de que ninguna persona a parte del usuario la conozca.
Prioridad del requerimiento	Esencial

Número de requerimiento	RNF002
Nombre de requerimiento	Interfaz
Tipo	Requisito
Fuente del requerimiento	Administrador
Proceso	La aplicación cuenta con una interfaz de usuario intuitiva y fácil de usar.
Prioridad del requerimiento	Deseada

Número de requerimiento	RNF003
Nombre de requerimiento	Almacenamiento de información
Tipo	Requisito
Fuente del requerimiento	Administrador
Proceso	Los datos de la aplicación serán administrados mediante un sistema de gestión de bases de datos relacionales.
Prioridad del requerimiento	Esencial

HERRAMIENTAS UTILIZADAS
1.	Java:
La plataforma Java es el nombre de un entorno o plataforma de computación originaria de Sun Microsystems, capaz de ejecutar aplicaciones desarrolladas usando el lenguaje de programación Java u otros lenguajes que compilen a bytecode y un conjunto de herramientas de desarrollo.
![image](https://user-images.githubusercontent.com/101655208/203437901-6ca44206-4bf2-4c88-adee-cd0f437479c7.png)
 
2.	XML:
XML, siglas en inglés de eXtensible Markup Language, traducido como 'Lenguaje de Marcado Extensible' o 'Lenguaje de Marcas Extensible', es un metalenguaje que permite definir lenguajes de marcas desarrollado por el World Wide Web Consortium utilizado para almacenar datos en forma legible.
![image](https://user-images.githubusercontent.com/101655208/203437916-25d11ae9-ca5a-4da2-bd94-8c0b0297fd33.png) 

3.	Gradle:

Gradle es un sistema de automatización de construcción de código de software que construye sobre los conceptos de Apache Ant y Apache Maven e introduce un lenguaje específico del dominio basado en Groovy en vez de la forma XML utilizada por Apache Maven para declarar la configuración de proyecto.
![image](https://user-images.githubusercontent.com/101655208/203437949-66478ce9-91e0-4220-bfbd-49ca34c08297.png)
 
4.	Firabase:

Es una plataforma ubicada en la nube, integrada con Google Cloud Platform, que usa un conjunto de herramientas para la creación y sincronización de proyectos que serán dotados de alta calidad, haciendo posible el crecimiento del número de usuarios y dando resultado también a la obtención de una mayor monetización.
![image](https://user-images.githubusercontent.com/101655208/203437970-f58abaaf-7426-4fcb-ba84-6cd0c40c867c.png)

 
Metodología:
En primer lugar, se abre la pantalla de inicio, donde podrás ingresar con tus credenciales, o si no cuentas con unas, podrás registrarte ya sea por correo, o por google.
![image](https://user-images.githubusercontent.com/101655208/203438105-c8d16987-fef1-43bb-9911-60f500a90c06.png)
 
Si vas a regístrate, entraras en la siguiente pantalla:
![image](https://user-images.githubusercontent.com/101655208/203438165-0235b68f-2397-457c-9d97-769d927aedf4.png)
 
Si iniciaste con google puedes completar tu perfil en la siguiente pantalla:
![image](https://user-images.githubusercontent.com/101655208/203438187-9a9d4ab2-ea7d-48fa-82a0-b18dc7717150.png)
 
Ya al haber ingresado, aparecerá la pantalla de Home, donde verás las publicaciones en tiempo real, además podrás hacer tu propia publicación, también tienes un acceso para editar tu perfil, filtrar las publicaciones y chatear con amigos:
![image](https://user-images.githubusercontent.com/101655208/203438209-f16b7596-69c9-47ba-8d5f-1b4d59a369dc.png)
 
Para realizar una publicación, te aparecerá la siguiente pantalla, donde podrás subir imágenes, poner un título y una descripción, además de poder ponerle un filtro para llegar a un público específico:
![image](https://user-images.githubusercontent.com/101655208/203438254-f608f2a4-aeee-4b65-bec7-0315602f606f.png)
 
Cuando ingresas a tu perfil vez el fondo, tu imagen de perfil, puedes editarlo en el botón de editar perfil:
![image](https://user-images.githubusercontent.com/101655208/203438277-f10d3c49-c5b6-44c8-9954-1d0056527d37.png)
 
Para editar tu perfil, seleccionas una foto desde tu cámara o desde el almacenamiento del dispositivo, también puedes cambiar el nombre y el teléfono:
![image](https://user-images.githubusercontent.com/101655208/203438303-9cae241c-8b64-4bc3-9248-e6739ec34fae.png)
 
Conclusiones:
Es una aplicación que pude ser complemento en la vida universitaria diaria, donde se proporcionan herramientas para hacer a esta más cómodo y dinámica. Además, de aplicar los conocimientos adquiridos a lo largo del semestre.
