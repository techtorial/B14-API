package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class Airports {


    @Test
    public void getAirportsTest() {
        Response response = RestAssured.given().header("Accept", "application/json")
                .when().get("https://airportgap.dev-tester.com/api/airports")
                .then().statusCode(200).extract().response();

        Map<String, Object> deserializedResponse =
                response.as(new TypeRef<Map<String, Object>>() {
                });


        List<Map<String, Object>> data =
                (List<Map<String, Object>>) deserializedResponse.get("data");

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> airport = data.get(i);
            System.out.println(airport.get("id"));
        }


    }


}
