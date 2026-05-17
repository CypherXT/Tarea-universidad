================================================================================
SISTEMA DE CONSULTA DE CARRERAS UASD - PROYECTO FINAL
================================================================================

VERSION: 1.0
FECHA: Mayo 2026
BASE DE DATOS: SQL Server
JDK REQUERIDO: Java 8 o superior

================================================================================
1. REQUISITOS DEL SISTEMA
================================================================================

1.1 Software Necesario:
- Java JDK 8 o superior (Descargar de oracle.com/java)
- SQL Server 2017, 2019, 2022 o SQL Server Express
- SQL Server Management Studio (SSMS) - Opcional pero recomendado

1.2 Espacio en disco minimo: 500 MB

================================================================================
2. DEPENDENCIAS REQUERIDAS
================================================================================

2.1 Driver JDBC para SQL Server
Archivo necesario: mssql-jdbc-12.4.2.jre11.jar

Como obtenerlo:
Paso 1: Ir a https://learn.microsoft.com/en-us/sql/connect/jdbc/download-mssql-jdbc
Paso 2: Descargar Microsoft JDBC Driver for SQL Server
Paso 3: Extraer el archivo .jar de la carpeta descargada
Paso 4: Copiar el archivo a la carpeta "lib" del proyecto

================================================================================
3. ESTRUCTURA DEL PROYECTO
================================================================================

PROYECTOFIN/
├── lib/
│   └── mssql-jdbc-12.4.2.jre11.jar
├── src/
│   ├── datos/
│   │   └── ConexionBD.java
│   ├── modelo/
│   │   ├── Carrera.java
│   │   └── Escuela.java
│   └── vista/
│       └── VentanaPrincipal.java
├── App.java
├── script_sqlserver.sql
└── README.txt

================================================================================
4. CONFIGURACION DE LA BASE DE DATOS
================================================================================

4.1 Verificar SQL Server:
- Abrir SQL Server Configuration Manager
- Verificar que el servicio "SQL Server (MSSQLSERVER)" este ejecutandose
- Verificar que el protocolo TCP/IP este habilitado
- Puerto predeterminado: 1433

4.2 Crear la base de datos:
Paso 1: Abrir SQL Server Management Studio (SSMS)
Paso 2: Conectarse a su instancia de SQL Server
Paso 3: Abrir el archivo "script_sqlserver.sql"
Paso 4: Presionar F5 para ejecutar el script completo
Paso 5: Verificar que no aparezcan errores en los mensajes

4.3 Verificar los datos insertados:
Ejecutar en SSMS:
USE uasd_carreras;
SELECT COUNT(*) as TotalCarreras FROM carreras;
SELECT COUNT(*) as TotalEscuelas FROM escuelas;

Resultado esperado:
- TotalCarreras: 25
- TotalEscuelas: 5

================================================================================
5. CONFIGURACION DEL PROYECTO EN VS CODE
================================================================================

5.1 Instalar extensiones necesarias:
- Extension Pack for Java (Microsoft)
- SQL Server (mssql) - Opcional

5.2 Agregar el driver JDBC al classpath:
Metodo 1 - Usando VS Code:
- Abrir la carpeta del proyecto en VS Code
- Ir a "JAVA PROJECTS" en la barra lateral
- Buscar "Referenced Libraries"
- Hacer clic en "+" y seleccionar lib/mssql-jdbc-12.4.2.jre11.jar

Metodo 2 - Manual:
Crear carpeta .vscode y dentro el archivo settings.json con:
{
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
}

5.3 Configurar credenciales de conexion:
Abrir el archivo src/datos/ConexionBD.java y modificar las lineas 8-10:

private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=uasd_carreras;encrypt=true;trustServerCertificate=true";
private static final String USUARIO = "sa";
private static final String CONTRASENA = "tu_contraseña";

Importante: Reemplazar "tu_contraseña" con su contraseña real de SQL Server.

Opciones para diferentes tipos de instalacion:

Si usa SQL Server Express:
jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=uasd_carreras;encrypt=true;trustServerCertificate=true

Si usa autenticacion de Windows:
jdbc:sqlserver://localhost:1433;databaseName=uasd_carreras;integratedSecurity=true;encrypt=true;trustServerCertificate=true

================================================================================
6. COMPILACION Y EJECUCION
================================================================================

6.1 Compilar el proyecto:

Desde VS Code:
- Abrir cualquier archivo .java
- Presionar Ctrl + Shift + B
- Seleccionar "Java: Compile Workspace"

Desde linea de comandos (Windows):
cd ruta/del/proyecto
javac -cp "lib/mssql-jdbc-12.4.2.jre11.jar;src" src/datos/*.java src/modelo/*.java src/vista/*.java App.java

6.2 Ejecutar el programa:

Desde VS Code:
- Abrir App.java
- Hacer clic derecho -> "Run Java"
- O presionar F5

Desde linea de comandos:
java -cp "lib/mssql-jdbc-12.4.2.jre11.jar;src" App

6.3 Verificacion de funcionamiento:
Al ejecutar correctamente, debe aparecer en consola:
"Conexion exitosa a SQL Server - Base de datos: uasd_carreras"

Luego se abrira la ventana del programa automaticamente.

================================================================================
7. COMO USAR EL PROGRAMA
================================================================================

Paso 1: Ejecutar el programa App.java
Paso 2: En la ventana principal, seleccionar una escuela del menu desplegable:
        - Ingenieria y Arquitectura
        - Ciencias Economicas y Sociales
        - Artes
        - Ciencias de la Salud
        - Humanidades
Paso 3: Hacer clic en el boton "Consultar Carreras"
Paso 4: Los resultados apareceran en la tabla con los siguientes datos:
        - ID de la carrera
        - Nombre completo
        - Duracion en semestres
        - Creditos totales
        - Descripcion de la carrera

================================================================================
8. SOLUCION DE PROBLEMAS COMUNES
================================================================================

Problema 1: "No suitable driver found"
Causa: El driver JDBC no esta en el classpath
Solucion: 
- Verificar que el archivo .jar este en la carpeta lib
- Verificar que este agregado a Referenced Libraries
- Revisar el nombre exacto del archivo

Problema 2: "Login failed for user 'sa'"
Causa: Credenciales incorrectas
Solucion:
- Verificar usuario y contraseña en ConexionBD.java
- En SSMS, verificar que la autenticacion mixta este habilitada
- Reiniciar el servicio de SQL Server

Problema 3: "Cannot open database 'uasd_carreras'"
Causa: La base de datos no existe o el nombre es incorrecto
Solucion:
- Ejecutar el script script_sqlserver.sql
- Verificar el nombre exacto de la base de datos
- Verificar que la base de datos aparezca en SSMS

Problema 4: "Connection refused: connect"
Causa: SQL Server no esta ejecutandose
Solucion:
- Abrir Services (services.msc)
- Buscar "SQL Server (MSSQLSERVER)"
- Iniciar el servicio
- Verificar que el puerto 1433 no este bloqueado por firewall

Problema 5: "SSL/TLS error"
Causa: Problemas de encriptacion
Solucion: Agregar a la URL: ;encrypt=true;trustServerCertificate=true

Problema 6: "ClassNotFoundException"
Causa: El driver no esta correctamente referenciado
Solucion:
- Verificar el nombre exacto del driver en Class.forName()
- Asegurarse que el archivo .jar este en la carpeta correcta

================================================================================
9. LISTA DE VERIFICACION PARA ENTREGA
================================================================================

Antes de entregar, verificar:

[ ] El codigo compila sin errores
[ ] La conexion a SQL Server funciona correctamente
[ ] Al seleccionar una escuela se muestran carreras
[ ] La tabla muestra los datos correctamente
[ ] Los mensajes de error son claros y amigables
[ ] El archivo mssql-jdbc-x.x.x.jreXX.jar esta en la carpeta lib
[ ] El script SQL se ejecuta sin errores
[ ] La base de datos contiene 25 carreras y 5 escuelas

================================================================================
10. ARCHIVOS A ENTREGAR
================================================================================

La entrega debe incluir:

PROYECTOFIN.zip (o .rar)
├── src/
│   ├── datos/
│   │   └── ConexionBD.java
│   ├── modelo/
│   │   ├── Carrera.java
│   │   └── Escuela.java
│   └── vista/
│       └── VentanaPrincipal.java
├── lib/
│   └── mssql-jdbc-12.4.2.jre11.jar
├── App.java
├── script_sqlserver.sql
└── README.txt

================================================================================
11. NOTAS IMPORTANTES
================================================================================

- El proyecto debe corresponder exactamente a la version presentada en clase
- La entrega debe realizarse unicamente a traves de la plataforma establecida
- Respetar la fecha limite de entrega
- Verificar que todas las credenciales esten correctamente configuradas
- Probar el programa en otra computadora antes de entregar si es posible

================================================================================
FIN DEL DOCUMENTO
================================================================================