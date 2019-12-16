# Pet Clinic Web App
Master status: [![CircleCI](https://circleci.com/gh/piotrek19/pet-clinik/tree/master.svg?style=svg&circle-token=da37dfe74512bd91609a6da6317f0c2e7a4a2b99)](https://circleci.com/gh/piotrek19/pet-clinik/tree/master)

### Application stack
Spring 5:
- Spring MVC
- Thymeleaf
- Spring Rest Docs
- BV
- Spring Data JPA

Tests:
- Junit
- AssertJ
- Mockito

### System Requirements
- Java 11 or higher
- Maven 3.5.2 or higher

#### Execution
To launch the app using attached Maven wrapper (environment with embedded Tomcat and H2 in-memory database) do:
- git clone https://github.com/piotrek19/pet-clinik.git
- cd pet-clinik
- ./mvnw clean install -DskipTests
- ./mvnw spring-boot:run -f ./pet-clinic-web/pom.xml

### Contact and Support
In case of any questions please raise [GitHub Issue](https://github.com/piotrek19/pet-clinik/issues)

### Inspired By:
- John's Thompson great Udemy course ["Spring Framework 5: Beginner to Guru"](https://www.udemy.com/spring-framework-5-beginner-to-guru)
- Pet Clinic [Spring reference application](https://github.com/spring-projects/spring-petclinic)
