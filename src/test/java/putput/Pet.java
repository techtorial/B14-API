package putput;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetPojo;
import utils.PayloadUtils;

public class Pet {

    @Test
    public void updatePetTest() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        Response response = RestAssured.given().accept("application/json")
                .contentType("application/json")
                .body(PayloadUtils.getPetPayload("3453434", "hatiko", "waiting"))
                .when().put().then().statusCode(200).extract().response();

        PetPojo parsedResponse = response.as(PetPojo.class);

        Assert.assertEquals("doggie", parsedResponse.getName());

    }

    @Test
    public void end2EndTest() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        /**
         * 1. Create a pet => POST
         * 2. Update a pet => PUT
         * 3. List pet => GET
         */

        String petName = "Hooch";
        String petId = "56789";
        String petStatus = "barking";


        RequestSpecification reqSpec = new RequestSpecBuilder().setAccept("application/json")
                .setContentType("application/json").build();

        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

        // 1. Create a pet => POST
        Response response = RestAssured.given().spec(reqSpec)
                .body(PayloadUtils.getPetPayload(petId, petName, petStatus))
                .when().post().then().spec(responseSpec).extract().response();

        PetPojo postResponse = response.as(PetPojo.class);
        Assert.assertEquals(petName, postResponse.getName());
        Assert.assertEquals(petId, String.valueOf(postResponse.getId()));
        Assert.assertEquals(petStatus, postResponse.getStatus());


        //2. Update a pet => PUT
        Response putResponse = RestAssured.given().spec(reqSpec)
                .body(PayloadUtils.getPetPayload(petId, petName, "waiting"))
                .when().put()
                .then().spec(responseSpec).extract().response();

        PetPojo parsedPutResponse = putResponse.as(PetPojo.class);
        Assert.assertEquals("waiting", parsedPutResponse.getStatus());

        //3. List pet => GET
        //https://petstore.swagger.io/v2/pet/39439
        Response getResponse = RestAssured.given().accept("application/json")
                .when().get(petId)
                .then().spec(responseSpec).extract().response();

        PetPojo parsedGetResponse = getResponse.as(PetPojo.class);
        Assert.assertEquals(petId, String.valueOf(parsedGetResponse.getId()));
        Assert.assertEquals(petName, parsedGetResponse.getName());

    }


}
