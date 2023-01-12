package get;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import pojo.PokemonPojo;

public class Pokemon {

    @Test
    public void getPokemonsTest() {

        Response response = RestAssured.given().header("Accept", "application/json")
                .when().get("https://pokeapi.co/api/v2/pokemon")
                .then().statusCode(200).extract().response();

        PokemonPojo deserializedResponse = response.as(PokemonPojo.class);
        System.out.println(deserializedResponse.getNext());

        for (int i = 0; i<deserializedResponse.getResults().size(); i++){
            System.out.println(deserializedResponse.getResults().get(i).getName());
        }
    }


}
