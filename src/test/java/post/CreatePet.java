package post;

import groovyjarjarantlr4.v4.misc.Utils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import pojo.PetPojo;
import utils.PayloadUtils;

public class CreatePet {

    @Test
    public void test(){

        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(PayloadUtils.getPetPayload("98123", "pet name", "poor"))
                .when().post()
                .then().contentType(ContentType.JSON)
                .statusCode(200).log().all().extract().response();
    }

    @Test
    public void test2(){
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        PetPojo petPojo = new PetPojo();
        petPojo.setId(12376);
        petPojo.setName("pet name 2");
        petPojo.setStatus("available");

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(petPojo)
                .when().post()
                .then().statusCode(200)
                .log().all().extract().response();


    }

}
