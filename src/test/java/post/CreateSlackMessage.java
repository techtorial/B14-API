package post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import utils.PayloadUtils;

public class CreateSlackMessage {

    @Test
    public void test(){
        RestAssured.baseURI = "https://slack.com";
        RestAssured.basePath = "api/chat.postMessage";

        Response response = RestAssured.given()
                .accept(ContentType.JSON).contentType(ContentType.JSON)
                // we need a token to be able to send this request
                .header("Authorization","Bearer xoxb-3937169151185-4657698207665-G0qDo8nuYkl8m7ShtPoSBShI")
                .body(PayloadUtils.getSlackPayload(":smile:"))
                .when().post()
                .then().statusCode(200)
                .body("ok", Matchers.is(true))
                // and means we're still verifying if it comes after then()
                .and().body("channel",Matchers.is("C04JYTRQTRR"))
                .and().body("message.type",Matchers.is("message"))
                .log().all().extract().response();
    }

    @Test
    public void test2(){
        RestAssured.baseURI = "https://slack.com";
        RestAssured.basePath = "api/conversations.history";


        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer xoxb-3937169151185-4657698207665-G0qDo8nuYkl8m7ShtPoSBShI")
                .param("channel","C04JYTRQTRR") // short for parameter (params in postman)
                .when().get()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("ok",Matchers.is(true))
                .log().all()
                .extract().response();
    }

}
