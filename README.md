<b>ING_ATM_DEMO</b>

A web application to list ING ATMs in various dutch cities and APIs to add new ATMs.

The web application is based on the standard Servlet spec and uses frameworks such as Camel and Spring. The web app runs on Jetty runtime and can be built and run using Apache maven.

The app uses an external webservice call to gather a superset of data. It also exposes few RESTful APIs that can be consumed by entities with appropriate Authorization. Finally, it displays the data (list of ATMs) on a page using AngularJS.


Apache Maven

Apache Maven (3.x) is used for build management of the application. Maven helps in managing dependency management, versioning and build lifecycle using various plugins available.
Dependencies:
- Testing related 
    - junit (4.12)
    - hamcrest (1.3)
    - spring-test (4.3.7.RELEASE)
    - camel-test-spring (4.0.4.RELEASE)
- Apache Camel framework (2.18.4)
    - camel-restlet
    - camel-core
    - camel-spring
    - camel-spring-javaconfig
    - camel-spring-security
    - camel-jackson
- Spring framework (4.3.7.RELEASE)
    - spring-webmvc
    - spring-context
    - spring-security-web (4.0.4.RELEASE)
    - spring-security-config (4.0.4.RELEASE)
- Misc
    - jackson-mapper (1.9.13)
    - jackson-binding (2.7.7)
    - json (20160810)
    - javax-servlet (3.1.0)
    - commons-logging (1.2)

Build Plugins
- maven-compiler-plugin: This plugin is used to compile the sources of the project.
- maven-war-plugin: This plugin is responsible for collecting all artifact dependencies, classes and resources of the web application and packaging them into a web application archive.
- jetty-maven-plugin: A useful plugin for rapid development and testing. It can auto redeploy the webapp upon detecting any changes in the project.

Maven goals
- “mvn clean install” - use this goal to clean, compile and create a war file that can be deployed to an independent servlet container such as tomcat.
- “mvn jetty:run” - use this goal to dynamically deploy and run the webapp in embedded Jetty runtime.


Apache Camel

Camel’s rest DSL (based on Restlet) is used to map REST apis to route endpoints. Uses Spring Java config to define and integrate the CamelContext with Web application. ComponentScan on the configuration file “CamelConfig” defines the package to scan for camel routes.

Camel routes use various HTTP methods for REST API endpoints and route the input to appropriate service method.
/demo/api/atms GET - “Retrieve all ATMs”
/demo/api/atms POST - “Create ATM”
/demo/api/atms/{city} GET - “Retrieve ATMs for the given city”

Using camel and spring-security integration, an AuthorizationPolicy is defined to evaluate basic authentication associated with every API call that requires authenticated access. The users defined in Spring security configuration file (“applicationContext-security) can be used as examples to understand the role based authorization.

Auth processor is written to simulate a simplified version of Authentication Manager.


Spring

Spring security is configured and used with Camel to secure access to urls as per business requirement.
Spring framework is used to bootstrap the application and manage dependency injection of components within the context. Both XML and Java DSL is used to define the Spring configuration.

The entry point to the application is class “com.mobiquity.backbase.AppInitializer” that extends WebApplicationInitializer. This class binds all config files to the servlet context.


Services, Models and Data Store

AtmService (implemented as AtmServiceImpl) provides the abstraction between data store and web view/REST endpoint.

AtmDataStore is a makeshift datastore (in-memory) that makes a one time call to the external webservice to get the entire data superset and store in a map (with “city” being the key).

Atm is a Java POJO that represents the data model structure of the Atm JSON received in the data superset.


AngularJS

Instead of traditional JSTL based view using SpringMVC, the GUI is a Ajax screen based on AngularJS and JQuery. Rendered as a response to the default REST API, this page makes a REST call to /demo/api/atms to get a list of all ATMs. These ATMs are then displayed using an angular module “ng-grid” in tabular format.
