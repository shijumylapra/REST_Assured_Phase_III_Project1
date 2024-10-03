package API_Collections;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.BasicConfigurator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.logging.Logger;

public class ReqResTest {
    public Logger logger = Logger.getLogger("PostRequest");
    @Test
    public void Test() {

        //Get all employees records
        getAllEmployees();
        //Get an employee record
        getSingleEmployee(3);
        //Post Employee
        postEmployee();
    }
    void getAllEmployees() {
        RestAssured.given()
                .baseUri("https://reqres.in/api/users")
                .when()
                .get()
                .then()
                .log()
                .body()
                .statusCode(200);
    }
    void getSingleEmployee(int empId) {
        RestAssured.given()
                .baseUri("https://reqres.in/api/users/" + empId)
                .when()
                .get()
                .then()
                .log()
                .body()
                .statusCode(200);
    }
     void postEmployee() {
        BasicConfigurator.configure();
        logger.info("**************** Started the post call using BDD ************ ");
        RestAssured.given()
                .baseUri("https://reqres.in/api/users/")
                //.baseUri("http://localhost:3000/employees")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\r\n"
                        + "    \"name\": \"morpheus\",\r\n"
                        + "    \"job\": \"leader\",\r\n"
                        + "    \"id\": 23\r\n"
                        + "}")
                .when()
                .post()
                .then()
                .statusCode(201)
                .log()
                .all();
            logger.info("**************** Post call ended ************ ");
    }
}
