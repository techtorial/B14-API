package get;

import io.restassured.RestAssured;
import org.junit.Test;

public class Petstore {


    /**
     * 1. Define URL
     * 2. Add query params(if needed)
     * 3. Add headers(if needed)
     * 4. Send the request
     */
    @Test
    public void getPetTest(){
        RestAssured.given().when().get("https://petstore.swagger.io/v2/pet/1056789")
                .then().statusCode(200).log().body();
    }








}
