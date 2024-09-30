package API_Collections;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;

public class callURI
{
    public RequestSpecification request;
    public Response response;

   // @BeforeMethod
    public void baseURI (String url)
    {
        RestAssured.baseURI = url;
        request = RestAssured.given();
        response = request.get();
     }


}
