package stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class petStepDef {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static Response response;
    public static JsonNode jsonNode1, jsonNode2;
    public static String petID, petStatus, newPet, existingPet;

    ObjectMapper objectMapper = new ObjectMapper();

    @Given("I have a pet with ID {string}")
    public void iHaveAPetWithID(String ID) {
        if (!ID.equals("NULL")) {
            petID = ID;
        }
        else{
            petID = "";
        }
    }

    @Given("There are pets with status {string}")
    public void thereArePetsWithStatusStatus(String status) {
        if(status.equals("available") || status.equals("pending") || status.equals("sold")){
            petStatus = status;
        }
        else{
            petStatus = "";
        }
    }

    @Given("I have pet data to add")
    public void iHavePetDataToAdd() throws IOException {
        newPet = new String(Files.readAllBytes(Paths.get("src/test/resources/payload/addNewPet.json")));
    }

    @Given("I have an existing pet")
    public void iHaveAnExistingPet() throws IOException {
        existingPet = new String(Files.readAllBytes(Paths.get("src/test/resources/payload/defaultPet.json")));
        jsonNode1 = objectMapper.readTree(existingPet);
        petID = jsonNode1.get("id").asText();
        iRequestToFindThePetByID();
        String result = JsonPath.from(response.asString()).getString("id");
        assertThat(result, equalTo(petID));
    }

    @When("I request to find the pet by ID")
    public void iRequestToFindThePetByID() {
        RestAssured.baseURI = BASE_URL;
        response = RestAssured.given()
                .get("/pet/" + petID);
    }


    @When("I request to find pets by status")
    public void iWantToFindPetByStatus() {
        RestAssured.baseURI = BASE_URL;
        response = RestAssured.given()
                                .get("/pet/findByStatus?status=" + petStatus);
    }

    @When("I request to find pets status with method {string}")
    public void iRequestToFindPetsStatusWithMethodMethod(String method) {
        RestAssured.baseURI = BASE_URL;
        switch(method) {
            case "POST":
                response = RestAssured.given()
                        .post("/pet/findByStatus?status=" + petStatus);
                break;
            case "PUT":
                response = RestAssured.given()
                        .put("/pet/findByStatus?status=" + petStatus);
                break;
            case "PATCH":
                response = RestAssured.given()
                        .patch("/pet/findByStatus?status=" + petStatus);
                break;
            case "DELETE":
                response = RestAssured.given()
                        .delete("/pet/findByStatus?status=" + petStatus);
                break;
        }
    }

    @When("I request to add a new pet")
    public void iSubmitRequestAddNewPet() throws IOException {
        RestAssured.baseURI = BASE_URL;
        response =
                RestAssured.given()
                        .contentType("application/json")
                        .relaxedHTTPSValidation()
                        .body(newPet)
                        .when()
                        .post("/pet/");
        jsonNode1 = objectMapper.readTree(newPet);
        jsonNode2 = objectMapper.readTree(response.getBody().asString());
    }

    @When("I request to add a new pet by ID")
    public void iRequestToAddANewPetByID() {
        Map<String, Object> request = new LinkedHashMap<>();
        request.put("id", petID);
        RestAssured.baseURI = BASE_URL;
        response =
                RestAssured.given()
                        .contentType("application/json")
                        .relaxedHTTPSValidation()
                        .body(request)
                        .when()
                        .post("/pet/");
    }

    @When("I request to update the pet data")
    public void iRequestToUpdateThePetData() throws IOException {
        RestAssured.baseURI = BASE_URL;
        String payload = new String(Files.readAllBytes(Paths.get("src/test/resources/payload/updatePet.json")));
        response = RestAssured.given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .body(payload)
                .when()
                .put("/pet/");
        jsonNode2 = objectMapper.readTree(payload);
    }

    @When("I request to delete the pet by ID")
    public void iRequestToDeleteThePetByID() {
        RestAssured.baseURI = BASE_URL;
        response = RestAssured.given()
                .contentType("application/json")
                .header("api_key", "special-key")
                .relaxedHTTPSValidation()
                .when()
                .delete("/pet/"+petID);
    }

    @Then("The client should receive HTTP {int} response status")
    public void theClientShouldReceiveHTTPResponseStatus(int httpResponse) {
        assertThat(response.getStatusCode(), equalTo(httpResponse));
    }

    @And("The response body should contain {string} with value {string}")
    public void iVerifyResponseBodyIdValueShouldBeID(String path, String value) {
        String result = JsonPath.from(response.asString()).getString(path);
        if (value.equals("NULL")) {
            value = "";
        }
        assertThat(result, equalTo(value));
    }

    @And("I verify add new pet response already correct")
    public void iVerifyAddNewPetResponseAlreadyCorrect() {
        assertThat(jsonNode1, equalTo(jsonNode2));
    }

    @And("I verify pet data already updated")
    public void iVerifyPetDataAlreadyUpdated() {
        petID = jsonNode2.get("id").asText();
        iRequestToFindThePetByID();
        String result = JsonPath.from(response.asString()).getString("id");
        assertThat(result, equalTo(petID));
    }

    @And("I verify pet data already deleted")
    public void iVerifyPetDataAlreadyDeleted() {
        iRequestToFindThePetByID();
        String result = JsonPath.from(response.asString()).getString("message");
        assertThat(result, equalTo("Pet not found"));
    }

    @And("I revert deleted data")
    public void iRevertDeletedData() throws IOException {
        RestAssured.baseURI = BASE_URL;
        String payload = new String(Files.readAllBytes(Paths.get("src/test/resources/payload/defaultPet.json")));
        response =
                RestAssured.given()
                        .contentType("application/json")
                        .relaxedHTTPSValidation()
                        .body(payload)
                        .when()
                        .post("/pet/");
    }
}
