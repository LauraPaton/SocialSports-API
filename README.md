# INTRODUCCIÓN

Un Servicio Web RESTful proporciona recursos mediante URIs que se pueden usar para realizar solicitudes y recibir respuestas a través del protocolo HTTP. El formato más usado para devolver y recibir los datos es JSON, ya que es más ligero y legible que XML, pero este tipo de servicios son muy flexibles ya que permiten consumir y producir diferentes tipos MIME.

# CREACIÓN

Se ha usado JAX-RS, la API de java para la creación de servicios restful y su implementación en Jersey, además de Maven para la gestión de dependencias.

- Cada recurso estará anotado con @Path para indicar su ruta ``@Path("/ruta")`` y se accederá a cada recurso por medio de su URI, por ejemplo: http://socialsports.ddns.net:8081/rest/perfil/datosusuario (protocolo, IP o dominio, puerto, context root, ruta de recursos rest, y ruta de recurso).
- Los métodos de petición indicarán el tipo de orperación a realizar: ``@POST, @PUT, @GET, @DELETE, @PATCH``
- Para las respuestas los códigos de estado HTTP más usados han sido:
``
  200 OK, 
  201 CREATED,
  204 NO CONTENT,
  401 Unauthorized,
  404 NOT FOUND
 ``
 - Para indicar el tipo de datos a consumir o producir se indican mediante las anotaciones ``@Consumes`` y ``@Produces`` respectivamente seguida del tipo MIME.
 
- Para pasarle los parámetros se han usado las siguientes anotaciones:

  @QueryParam: Estructura de la petición: http://socialsports.ddns.net:8081/rest/eventos/buscar?deporte=futbol
  
  @PathParam: Estructura de la petición: http://socialsports.ddns.net:8081/rest/perfil/amigos/laura@hotmail.com
  
  @FormParam: Debe ir acompañada del tipo APPLICATION_FORM_URLENCODED y solo puede usarse en los métodos con body como @POST y @PUT, y suele usarse en formularios HTML.

# SEGURIDAD 

La mayoría de las rutas de este Servicio Web estarán protegidas y será necesario un token para poder acceder a su contenido. 

### FILTROS JAX-RS

Los filtros nos permiten modificar las propiedades de las solicitudes y respuestas. En este proyecto se ha creado un filtro ``SecurityFilter.java`` para modificar las solicitudes y que solo sean aceptadas aquellas que tienen un token válido en su cabecera, si la solicitud es rechazada, se enviará una respuesta 401 UNATHORIZED y no se le permitirá acceder al recurso solicitado. 
Para ello se ha implementado ContainerRequestFilter en la clase además de registrarla como Proveedor en el archivo ``web.xml``. 

Este filtro sería global y se ejecutaría para cada solicitud, para hacer que se ejecute solo en ciertos métodos de recursos específicos se ha tenido que crear una interfaz ``Secured.java`` con un "enlace de nombre" ``@NameBinding``.

#### TOKEN

Para el Token se ha usado Java Json Web Token (JJWT) una de las librerías más usadas para la creación de JWT en java: https://github.com/jwtk/jjwt

  - La estructura de un token es la siguiente: ```header.payload.signature```

     Header: El encabezado donde se indicará el algoritmo y el tipo de token, en nuestro caso será el algoritmo HS256 y de tipo Json Web Token.
     
     Payload: Datos de usuario, privilegios y otro tipo de datos que queramos añadir.
     
     Signature: La firma que nos permite verificar si el token es válido.

Cada una de las partes está codificada por Base64 y separada por un punto, por lo tanto, podemos decodificar el token facilmente para poder ver su contenido. Lo que proporciona seguridad al token es la firma ya que solo el servidor podrá usar su clave privada para comprobar el HASH.

#### ALMACENAMIENTO DE LA CONTRASEÑA

Se ha escogido PBKDF2 con HMAC Sha512, que proporciona un hash irreversible de 512 bits, además de añadirle sal y varias iteraciones.
Para ello se ha usado el paquete ``javax.crypto``, que proporciona clases e interfaces para operaciones criptográficas.

Las iteraciones sirven para hacer que el proceso de hash sea más lento y así hacer menos efectivo un ataque de fuerza bruta (Key stretching).

Y la sal añade un valor aleatorio a la contraseña de cada usuario para que así, si dos usuarios tienen la misma contraseña, el hash generado sea diferente. Las sales también ayudan contra las [tablas arcoíris](https://es.wikipedia.org/wiki/Tabla_arco%C3%ADris) y contra los ataques de fuerza bruta en caso de robo de la BBDD.

## VENTAJAS DE UN SERVICIO BASADO EN REST

- Nos permite separar el cliente del servidor.
- Nos da escalabilidad: Podemos aumentar la capacidad de clientes y servidores por separado. Este servicio puede ser usado por distintos dispositivos como aplicaciones móviles, páginas web, cualquier cliente desarrollado con cualquier tipo de lenguaje.





..Incompleto..
