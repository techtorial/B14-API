package goRest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

public class GetUser {

    @Test
    public void test(){
        RestAssured.baseURI = "https://gorest.co.in/public-api";
        RestAssured.basePath = "users/49962";

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 106b8f21995c73c87f315a314df2a751097151c10820b7bf28bed937c94a191f")
                .when().get()
                .then().statusCode(200)
                .body("code", Matchers.is(200))
                .body("data.name",Matchers.is("majd"))
                .extract().response();
    }
}
