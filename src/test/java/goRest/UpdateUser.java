package goRest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

public class UpdateUser {

    @Test
    public void test(){
        RestAssured.baseURI = "https://gorest.co.in/public-api";
        RestAssured.basePath = "users/49962";

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 106b8f21995c73c87f315a314df2a751097151c10820b7bf28bed937c94a191f")
                .body("{\n" +
                        "  \"status\": \"Inactive\"\n" +
                        "}")
                .when().put()
                .then().statusCode(200)
                .log().all()
                .extract().response();



    }

}
