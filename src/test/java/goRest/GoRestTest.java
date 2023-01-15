package goRest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import pojo.GoRestPojo;

public class GoRestTest {

    @Test
    public void endToEndTest(){
        int userId = createUser("majd","jegiohdr848768945@jiog.com","male","Active");
        GoRestPojo userPojo = getUser(userId);
        Assert.assertEquals(userId,userPojo.getData().get("id"));
        GoRestPojo updatePojo = updateUser(userId, "inactive");
        Assert.assertNotEquals(userPojo.getData().get("status"),updatePojo.getData().get("status"));

    }




    public int createUser(String name, String email, String gender, String status){

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 106b8f21995c73c87f315a314df2a751097151c10820b7bf28bed937c94a191f")
                .body("{\n" +
                        "  \"name\": \""+name+"\",\n" +
                        "  \"email\": \""+email+"\",\n" +
                        "  \"gender\": \""+gender+"\",\n" +
                        "  \"status\": \""+status+"\"\n" +
                        "}")
                .when().post("https://gorest.co.in/public-api/users")
                .then().statusCode(200)
                .log().all()
                .extract().response();
        GoRestPojo goRestPojo = response.as(GoRestPojo.class);
        int id = (int) goRestPojo.getData().get("id");

        return id;
    }

    public GoRestPojo getUser(int id){
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 106b8f21995c73c87f315a314df2a751097151c10820b7bf28bed937c94a191f")
                .when().get("https://gorest.co.in/public-api/users/" + id)
                .then().statusCode(200)
                .body("code", Matchers.is(200))
                .body("data.id", Matchers.equalTo(id))
                .log().all()
                .extract().response();
        GoRestPojo goRestPojo = response.as(GoRestPojo.class);

        return goRestPojo;
    }

    public GoRestPojo updateUser(int id, String status){

        RestAssured.baseURI = "https://gorest.co.in/public-api";
        RestAssured.basePath = "users/"+id;

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 106b8f21995c73c87f315a314df2a751097151c10820b7bf28bed937c94a191f")
                .body("{\n" +
                        "  \"status\": \""+status+"\"\n" +
                        "}")
                .when().put()
                .then().statusCode(200)
                .log().all()
                .extract().response();

        GoRestPojo goRestPojo = response.as(GoRestPojo.class);
        return goRestPojo;
    }



}
