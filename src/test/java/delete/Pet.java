package delete;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

public class Pet {

    @Test
    public void deletePetTest() {

        //https://petstore.swagger.io/v2/pet/2094388
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        Response response = RestAssured.given().accept("application/json")
                .when().delete("2094388")
                .then().statusCode(200).log().all().extract().response();



    }


}
