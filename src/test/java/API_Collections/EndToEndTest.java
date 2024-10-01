package API_Collections;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.List;

public class EndToEndTest {
    callURI CURL = new callURI();
    JsonPath jsPath;
    String emp_id;

    @Test
    public void TC01_GetAllEmployee() {
        CURL.baseURI("http://localhost:3000/employees");
        jsPath = CURL.response.jsonPath();
        List<String> emp_name = jsPath.get("name");
        System.out.println("========= Employees ========== ");
        for (String name : emp_name) {
            System.out.println(name);
        }
        System.out.println("========= The response code is " + CURL.response.statusCode());
    }
    @Test
    public void TC02_GetSingleEmployee() {
        CURL.baseURI("http://localhost:3000/employees/1");
        jsPath = CURL.response.jsonPath();
        Assert.assertEquals(CURL.response.statusCode(), 200);
        Assert.assertEquals(jsPath.get("name"), "Tiger Nixon");
        System.out.println("========= The response body is " + CURL.response.getBody().asString() + " " + "\n========= The Employee Name is   " + jsPath.get("name") + "\n========= The response code is   " + CURL.response.statusCode());
    }
    @Test
    public void TC03_CreateEmployee() {
        HashMap<String, String> emp = new HashMap<String, String>();
        emp.put("id", "15");
        emp.put("name", "Rakesh");
        emp.put("salary", "7000");
        CURL.baseURI("http://localhost:3000/employees");
        CURL.response = CURL.request.contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(emp)
                .post();
        this.emp_id = CURL.response.jsonPath().get("id");
        Assert.assertEquals(CURL.response.statusCode(), 201);
        System.out.println("========= Create new employee, " + CURL.response.getBody().asString() + " " + "========= The response code is   " + CURL.response.statusCode());
        TTC04_DeleteEmployee();
    }
    void TTC04_DeleteEmployee() {
        CURL.baseURI("http://localhost:3000/employees/");
        CURL.response = CURL.request.delete(emp_id);
        Assert.assertEquals(CURL.response.statusCode(), 200);
        System.out.println("========= Successfully! deleted the record" + ", The response code is   " + CURL.response.statusCode());
        TC05_TestDeletedEmployee();
    }
    void TC05_TestDeletedEmployee() {
        CURL.baseURI(String.join("", "http://localhost:3000/employees/", emp_id));
        CURL.response = CURL.request.get();
        Assert.assertEquals(CURL.response.statusCode(), 404);
        System.out.println("========= No record found, The response code is   " + CURL.response.statusCode());
    }
}
