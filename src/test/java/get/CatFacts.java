package get;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CatFacts {


    @Before
    public void setup() {
        //https://catfact.ninja/facts
        RestAssured.baseURI = "https://catfact.ninja";
        RestAssured.basePath = "facts";
    }

    @Test
    public void catFactTest() {
        Response response = RestAssured.given().accept("application/json")
                .when().get().then().statusCode(200).extract().response();

        JsonPath parsedResponse = response.jsonPath();
        int currentPage = parsedResponse.getInt("current_page");
        Assert.assertEquals("Failed to validate current page", 1, currentPage);

        String firstCatFact = parsedResponse.getString("data[0].fact");
        System.out.println(firstCatFact);

        List<Object> data = parsedResponse.getList("data");

        for (int i = 0; i < data.size(); i++){
            String temp = parsedResponse.getString("data[" + i + "].fact"); // "data[0].fact", "data[1].fact"
            System.out.println(temp);
        }
    }


}
