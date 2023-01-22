package step_defs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.PayloadUtils;

public class PetStepDefs {

    private String petId = "7899877";
    private String petName = "Zeus";
    private String petStatus = "playing";
    private Response response;

    @Given("user has valid URL")
    public void user_has_valid_url() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";
    }

    @When("user sends POST request to create a pet")
    public void user_sends_post_request_to_create_a_pet() {
        response = RestAssured.given().accept("application/json").contentType("application/json")
                .body(PayloadUtils.getPetPayload(petId, petName, petStatus))
                .when().post();
    }

    @Then("status code is {int}")
    public void status_code_is(Integer int1) {

    }

    @Then("pet name, pet id, pet status are correct")
    public void pet_name_pet_id_pet_status_are_correct() {

    }


}
