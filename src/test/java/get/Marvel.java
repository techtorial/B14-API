package get;

import io.restassured.RestAssured;
import org.junit.Test;

public class Marvel {

    @Test
    public void marvelCharacterTest(){

        RestAssured.baseURI = "http://gateway.marvel.com";
        RestAssured.basePath = "v1/public/characters";
        
        RestAssured.given().accept("application/json")
                .queryParam("ts", 1)
                .queryParam("apikey", "51d5508b8bb07bc8c3ae4ff030e4a950")
                .queryParam("hash", "819a13c8716cf506571d5461ae73c4ff")
                .when().get()
                .then().statusCode(200).extract().response();





    }




}
