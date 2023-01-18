package postpost;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class Airport {

    @Test
    public void airportDistanceTest() {

        RestAssured.baseURI = "https://airportgap.dev-tester.com";
        RestAssured.basePath = "api/airports/distance";

        Response response = RestAssured.given()
                .accept("application/json")
                .contentType("application/json")
                .body("{\n" +
                        "    \"from\": \"ORD\",\n" +
                        "    \"to\": \"MIA\"\n" +
                        "}")
                .when().post().then().statusCode(200).extract().response();

        Map<String, Map<String, Object>> parsedResponse =
                response.as(new TypeRef<Map<String, Map<String, Object>>>() {
                });
        Map<String, Object> data = parsedResponse.get("data");
        Map<String, Object> attributes = (Map<String, Object>) data.get("attributes");
        double miles = (double) attributes.get("miles");

        Assert.assertEquals( 1198.4365657701198, miles, 0);

    }


}
