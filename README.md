
This is a contact book application. In this application You can manage people and their contacts.
This application is developed using such tools as: Spring Boot, Maven, JPA, Spring Web Services, JUnit, Mockito, lombok. 

To get started you will need to do some configuration.

**1. Configuration.**

First of all you need to install **JDK 8** and **Maven** to run this application.
After installing **JDK**, you are going to need a database.
This application is using a PostgreSQL database.

**Development database:**
    **name:** contacts
    **username:** admin
    **password:** admin

Also for repository tests you are going to need a test database.

**Test database:**
    **name:** contacts_test
    **username:** test
    **password:** test

**3. Build application:**
To build application use command in command line **mvn clean install**

**4. Run application:**
To run application use command in command line **mvn spring-boot:run**

**5. Run tests:**
To run all tests use command in command line **mvn test**

**6. Other information:**
You can also find a file in the root directory named contacts.postman_collection.json which contains sample REST requests.