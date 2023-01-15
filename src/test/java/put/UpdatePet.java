package put;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import utils.PayloadUtils;

public class UpdatePet {


    @Test
    public void test(){

        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        Response response = RestAssured.given()
                .accept(ContentType.JSON) // this is optional to have
                .contentType(ContentType.JSON)
                .body(PayloadUtils.getPetPayload("9018576759873534", "new pet name", "active"))
                .when().put()
                .then().statusCode(200)
                .log().all()
                .extract().response();

    }



}
