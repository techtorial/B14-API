package postpost;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetPojo;

public class Pet {

    @Test
    public void createPetTest() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        Response response = RestAssured.given()
                .accept("application/json") //Me: Server, give me json response
                .contentType("application/json") //Me: Server, I'm sending json data in body
                .body("{\n" +
                        "  \"id\": 837436355,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"string\"\n" +
                        "  },\n" +
                        "  \"name\": \"hatiko\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"https://photos.pet.com\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"waiting\"\n" +
                        "}").when().post().then().statusCode(200).extract().response();


        PetPojo parsedResponse = response.as(PetPojo.class);

        int actualPetId = parsedResponse.getId();
        Assert.assertEquals(837436355, actualPetId);

        String actualPetName = parsedResponse.getName();
        Assert.assertEquals("hatiko", actualPetName);


        //2nd level of validation,
        //sending GET request

        Response response1 = RestAssured.given().accept("application/json")
                .when().get("837436355")
                .then().statusCode(200).extract().response();

    }


}
