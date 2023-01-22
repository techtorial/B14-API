package get;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Countries {
String token;

    @Before
    public void setup() {
        //https://restcountries.com/v3.1/all
        RestAssured.baseURI = "https://restcountries.com";
        RestAssured.basePath = "v3.1/all";
        token =""; //call to generate token;

    }

    @Test
    public void allCountriesTest() {


        Response response = RestAssured.given().accept("application/json")
                .when().get()
                .then().statusCode(200).extract().response();

        boolean myCountryIsThere = false;
        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> objectList = jsonPath.getList(".");
        System.out.println("Number of all countries: " + objectList.size());
        for (Map<String, Object> country : objectList) {
            Map<String, Object> nameMap = (Map<String, Object>) country.get("name");
            String temp = String.valueOf(nameMap.get("common"));
            if (temp.equalsIgnoreCase("Guinea")) {
                System.out.println(nameMap.get("common"));
                myCountryIsThere = true;
            }
        }

        Assert.assertTrue("Failed to validate my country, country name: Guinea", myCountryIsThere);
    }

    @Test
    public void countryAndCapitalPairsTest() {

        Response response = RestAssured.given().accept("application/json")
                .when().get()
                .then().statusCode(200).extract().response();

        JsonPath parsedResponse = response.jsonPath();
        List<Map<String, Object>> countryList = parsedResponse.getList(".");
        Map<String, String> countryCapitalMap = new HashMap<>();
        for (Map<String, Object> country : countryList) {
            //retrieving country name
            Map<String, Object> countryNameMap = (Map<String, Object>) country.get("name");
            String countryName = countryNameMap.get("common").toString();

            //retrieving country capital
            List<String> capitalList = (List<String>) country.get("capital");
            String countryCapital;
            if (capitalList == null) {
                System.out.println("Country that doesn't have capital: " + countryName);
                countryCapital = "unknown";
            } else {
                countryCapital = capitalList.get(0);
            }
            //store country name and country capital in map
            countryCapitalMap.put(countryName, countryCapital);
        }
        System.out.println(countryCapitalMap);
    }


    @Test
    public void test() {
        RestAssured.given().accept("application/json")
                .when().get().then().statusCode(200)
                .body("[0].name.common", Matchers.equalTo("United Kingdom"))
                .body("[0].capital[0]", Matchers.is("London"));
    }


}
