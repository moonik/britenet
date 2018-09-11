
This is a contact book application. In this application You can manage people and their contacts.
This application is developed using such tools as: Spring Boot, Maven, JPA, Spring Web Services, JUnit, Mockito, lombok. 

To get started you will need to do some configuration.

# **1. Configuration.**

First of all you need to install **JDK 8** and **Maven** to run this application.
After installing **JDK**, you are going to need a database.
This application is using a PostgreSQL database.

# **Development database:**
    `name: contacts`
    **username:** admin
    **password:** admin

Also for repository tests you are going to need a test database.

# **Test database:**
    **name:** contacts_test
    **username:** test
    **password:** test

# **3. Run application:**
Before running the application you need to run the next command **mvn clean install**
This command will install all necessary dependecies and also it will generate domain classes for SOAP WS

To run application use command in command line **mvn spring-boot:run**

# **4. Run tests:**
To run all tests use command in command line **mvn test**

# **5. Other information:**
You can also find a file in the root directory named contacts.postman_collection.json which contains sample REST and SOAP requests.
