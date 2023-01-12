package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class Starwars {


    /**
     * 1. Define headers
     * 2. Define query string parameters
     * 3. Define URL/endpoint
     * 4. Send request
     */
    @Test
    public void getCharactersTest() {

        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .when().get("https://swapi.dev/api/people")
                .then().statusCode(200).extract().response();


        Map<String, Object> deserializedResponse =
                response.as(new TypeRef<Map<String, Object>>() {
                });
//        deserializedResponse.put("count", 82);
//        deserializedResponse.put("next", "https://swapi.dev/api/people/?page=2");
        System.out.println(deserializedResponse.get("count")); //82
        System.out.println(deserializedResponse.get("next"));
        Object nextValue = deserializedResponse.get("next");
        String next = nextValue.toString();
        String next2 = String.valueOf(nextValue);
        String next3 = nextValue + "";
        String next4 = (String) nextValue;

        Object resultsValue = deserializedResponse.get("results");
        List<Map<String, Object>> results = (List<Map<String, Object>>) resultsValue;

        for (int i = 0; i < results.size(); i++) {
            Map<String, Object> result = results.get(i);
            if (result.get("gender").toString().equalsIgnoreCase("female")) {
                System.out.println(result.get("name"));
            }
        }

    }


    @Test
    public void starWarsCharsCountTest() {


        Response response = RestAssured.given().header("Accept", "application/json")
                .when().get("https://swapi.dev/api/people")
                .then().statusCode(200).extract().response();

        Map<String, Object> parsedResponse = response.as(new TypeRef<Map<String, Object>>() {
        });

        int expectedCount = (int) parsedResponse.get("count");

        String nextPageUrl = (String) parsedResponse.get("next");

        int totalCharacterCount = 0;
        List<Map<String, Object>> results = (List<Map<String, Object>>) parsedResponse.get("results");

        totalCharacterCount += results.size();

        while (nextPageUrl != null) {
            response = RestAssured.given().header("Accept", "application/json")
                    .when().get(nextPageUrl).then().statusCode(200).extract().response(); // GET call

            parsedResponse = response.as(new TypeRef<Map<String, Object>>() {
            }); // deserialization

            nextPageUrl = (String) parsedResponse.get("next"); //after 9 iterations => null
            results = (List<Map<String, Object>>) parsedResponse.get("results");
            totalCharacterCount += results.size(); //adding to total characters count
        }

                                //82                //82
        Assert.assertEquals(totalCharacterCount, expectedCount);

    }


}
