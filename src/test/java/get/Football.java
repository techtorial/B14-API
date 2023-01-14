package get;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Football {

    @Test
    public void test(){

        Response response = RestAssured.given().accept(ContentType.JSON)
                .header("X-Auth-Token","c55b7a64e8424d46a52051bce36d1c0a")// "application/json"
                .when().get("http://api.football-data.org/v2/competitions")
                .then().statusCode(200).contentType(ContentType.JSON)
                .extract().response();

        Map<String,Object> responseMap = response.as(new TypeRef<Map<String, Object>>() {
        });

        // find the competition with name = Premier Liga
        // print the id of this competition

        List<Map<String,Object>> competitions = (List<Map<String,Object>>) responseMap.get("competitions");
        for (int i = 0; i < competitions.size(); i++) {
            if (competitions.get(i).get("name").equals("Premier Liga")){
                System.out.println(competitions.get(i).get("id"));
            }
        }
    }

    @Test
    public void test2(){
        RestAssured.baseURI = "http://api.football-data.org";
        RestAssured.basePath = "v2/competitions";

        Response response = RestAssured.given().accept(ContentType.JSON)
                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when().get()
                .then().statusCode(200).contentType(ContentType.JSON)
                .extract().response();

        response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        // Map<String,Object> responseMap = response.as(new TypeRef<Map<String, Object>>() {
        //        });
        List<Map<String,Object>> competitions = jsonPath.getList("competitions");

        for (int i = 0; i < competitions.size(); i++) {
            Map<String, Object> competitionsMap = competitions.get(i);
            if (competitionsMap.get("name").equals("Premier Liga")){
                int id = (int) competitionsMap.get("id");
                Assert.assertEquals(id,2035);
            }
        }
    }

    @Test
    public void test3(){
        RestAssured.baseURI = "http://api.football-data.org";
        RestAssured.basePath = "v2/competitions";

        RestAssured.given().accept(ContentType.JSON)
                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when().get()
                .then().statusCode(200)
                .body("competitions.find {it.name == 'Premier Liga'}.id", Matchers.is(2035))
                .extract().response();
    }

    @Test
    public void test4(){
        // find the country code for Australia
        // assert that country code is AUS
        //use JsonPath Object to deserialize

        RestAssured.baseURI = "http://api.football-data.org";
        RestAssured.basePath = "v2/competitions";

        Response response = RestAssured.given().accept(ContentType.JSON)
                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when().get()
                .then().statusCode(200)
                .extract().response();

        JsonPath parsedResponse = response.jsonPath();

        List<Map<String,Object>> competitions = parsedResponse.getList("competitions");

        for (int i = 0; i < competitions.size(); i++) {
            Map<String, Object> countryMap = competitions.get(i);
            Map<String,Object> areaMap = (Map<String,Object>) countryMap.get("area");
            if (areaMap.get("name").equals("Australia")){
                Assert.assertEquals("AUS", areaMap.get("countryCode"));
            }
        }

        String countryCode = response
                .path("competitions.find{ it.area.name == 'Australia'}.area.countryCode");
        Assert.assertEquals("AUS",countryCode);
    }
}
