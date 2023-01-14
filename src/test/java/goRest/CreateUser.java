package goRest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.Test;
import pojo.GoRestPojo;

import java.io.File;

public class CreateUser {

    @Test
    public void test(){
        RestAssured.baseURI = "https://gorest.co.in/public-api";
        RestAssured.basePath = "users";

        File goRestCreateUserFile = new File("src/test/java/payloads/goRestUserBody.json");
        Response response = RestAssured.given().accept(ContentType.JSON)
                .header("Authorization", "Bearer 106b8f21995c73c87f315a314df2a751097151c10820b7bf28bed937c94a191f")
                .contentType(ContentType.JSON)
                .body(goRestCreateUserFile)
                .when().post()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("code", Matchers.equalTo(201))
                .extract().response();

        GoRestPojo goRestPojo = response.as(GoRestPojo.class);
        Integer id = (Integer) goRestPojo.getData().get("id");
        System.out.println(id);
        // 49962
    }

}
