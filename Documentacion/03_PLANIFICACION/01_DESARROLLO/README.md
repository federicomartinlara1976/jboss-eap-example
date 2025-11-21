# Plan de desarrollo y pruebas (PL-000_DOC_GENERAL_DESARROLLO)

La documentación técnica resultante se encuentra en la carpeta **/Documentacion/04_DOC_TECNICA**.
Para la correcta interpretación y lectura de la documentación técnica, leer el
documento **README.md** de dicha carpeta. 

### 1. Persistencia con JPA/Hibernate (Plan PL-001_JPA_HIBERNATE)

* Conexión a base de datos
  - Documentación técnica: **CONF-001_DOC_CONFIGURACION_BD** 
* Entidades JPA
  - Documentación técnica: **DES-001_DOC_DESARROLLO_JPA**
* Repositorios con Panache
  - Documentación técnica: **DES-002_DOC_DESARROLLO_REPO**

### 2. Seguridad con Keycloak (Plan PL-002_SEC_KEYCLOACK)

* Plan de implementación de Keycloak
  - Documentación técnica: 
    - **CONF-002_DOC_CONFIGURACION_KEYCLOAK**
    - **CONF-003_DOC_CONFIGURACION_REALM** 
* Autenticación OAuth2
* Roles y permisos
* SSO (Single Sign-On)

### 3. Mensajería con JMS

* Colas y Topics
* Mensajes asíncronos
* Integración con MDB

### 4. Caché distribuido con Infinispan

* Cache de segundo nivel
* Sesiones distribuidas
* Datos en cluster

### 5. WebSockets

* Comunicación bidireccional
* Aplicaciones en tiempo real
* Notificaciones push

### 6. Batch Processing

* Jobs programados
* Procesamiento asíncrono
* JBatch

### 7. Monitoreo y Métricas

* Endpoints de health check
* Métricas de performance
* Integración con Prometheus 
