# Spring Boot Application

List of API exposed for [parents](https://github.com/paoloseccia/spring_boot/blob/master/src/main/java/org/paolo/springboot/controller/ParentsController.java)


```sh
 POST /parents - creates a new parent
 GET /parents/{id} - gets parent by id
 PUT /parents/{id} - updates an existing parent by id
 DELETE /parents/{id} - removes an existing parent by id
```

List of API exposed for [children](https://github.com/paoloseccia/spring_boot/blob/master/src/main/java/org/paolo/springboot/controller/ChildrenController.java)


```sh
 PUT /children/{id} - updates an existing children by id
```

### TEST

- [ParentsApplicationTest](https://github.com/paoloseccia/spring_boot/blob/master/src/test/java/org/paolo/springboot/ParentsApplicationTests.java) - integration test
- [ParentsControllerTest](https://github.com/paoloseccia/spring_boot/blob/master/src/test/java/org/paolo/springboot/ParentsControllerTest.java) - controller parent unit test
- [ChildrenControllerTest](https://github.com/paoloseccia/spring_boot/blob/master/src/test/java/org/paolo/springboot/ChildrenControllerTest.java) - controller children unit test
- [ParentsRepositoryTest](https://github.com/paoloseccia/spring_boot/blob/master/src/test/java/org/paolo/springboot/ParentsRepositoryTest.java) - parent repository unit test

