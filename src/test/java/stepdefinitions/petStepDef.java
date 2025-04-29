package stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class petStepDef {
    private static final String endpointPetUploadImage = "/uploadImage";
    private static final String endpointPet = "/pet/";
    private static final String endpointPetFindByStatus = "/pet/findByStatus";
    public static JsonNode jsonNode1, jsonNode2;
    public static String petID, petStatus, newPet, existingPet;

    private final baseStepDef base = new baseStepDef();
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
        newPet = base.getPayload("src/test/resources/payload/addNewPet.json");
    }

    @Given("I have an existing pet")
    public void iHaveAnExistingPet() throws IOException {
        existingPet = base.getPayload("src/test/resources/payload/defaultPet.json");
        jsonNode1 = objectMapper.readTree(existingPet);
        petID = jsonNode1.get("id").asText();
        iRequestToFindThePetByID();
        int statusCode = baseStepDef.response.getStatusCode();
        if(statusCode != 200){
            base.callEndpointHttpMethodPostString(endpointPet, base.emptyHeader, base.emptyParameter, existingPet);
            assertThat(baseStepDef.response.getStatusCode(), equalTo(200));
            iRequestToFindThePetByID();
        }
        String result = JsonPath.from(baseStepDef.response.asString()).getString("id");
        assertThat(result, equalTo(petID));
    }

    @When("I request to find the pet by ID")
    public void iRequestToFindThePetByID() throws IOException {
        base.callEndpointHttpMethodGetString(endpointPet + petID, base.emptyHeader, base.emptyParameter);
    }

    @When("I request to find pets by status")
    public void iWantToFindPetByStatus() throws IOException {
        HashMap<String, String> statusParam = new HashMap<>();
        statusParam.put("status", petStatus);
        base.callEndpointHttpMethodGetString(endpointPetFindByStatus, base.emptyHeader, statusParam);
    }

    @When("I request to find pets status with method {string}")
    public void iRequestToFindPetsStatusWithMethod(String method) throws IOException {
        HashMap<String, String> statusParam = new HashMap<>();
        statusParam.put("status", petStatus);
        switch(method) {
            case "POST":
                base.callEndpointHttpMethodPostString(endpointPetFindByStatus, base.emptyHeader, statusParam, "");
                break;
            case "PUT":
                base.callEndpointHttpMethodPutString(endpointPetFindByStatus, base.emptyHeader, statusParam, "");
                break;
            case "PATCH":
                base.callEndpointHttpMethodPatchString(endpointPetFindByStatus, base.emptyHeader, statusParam);
                break;
            case "DELETE":
                base.callEndpointHttpMethodDeleteString(endpointPetFindByStatus, base.emptyHeader, statusParam);
                break;
        }
    }

    @When("I request to add a new pet")
    public void iSubmitRequestAddNewPet() throws IOException {
        base.callEndpointHttpMethodPostString(endpointPet, base.emptyHeader, base.emptyParameter, newPet);
        jsonNode1 = objectMapper.readTree(newPet);
        jsonNode2 = objectMapper.readTree(baseStepDef.response.getBody().asString());
    }

    @When("I request to add a new pet by ID")
    public void iRequestToAddANewPetByID() throws IOException {
        Map<String, String> request = new LinkedHashMap<>();
        request.put("id", petID);
        base.callEndpointHttpMethodPostMap(endpointPet, base.emptyHeader, base.emptyParameter, request);
    }

    @When("I request to update the pet data")
    public void iRequestToUpdateThePetData() throws IOException {
        String payload = base.getPayload("src/test/resources/payload/updatePet.json");
        base.callEndpointHttpMethodPutString(endpointPet, base.emptyHeader, base.emptyParameter, payload);
        jsonNode2 = objectMapper.readTree(payload);
    }

    @When("I request to delete the pet by ID")
    public void iRequestToDeleteThePetByID() throws IOException {
        Map<String, String> header = new LinkedHashMap<>();
        header.put("api_key","special-key");
        base.callEndpointHttpMethodDeleteString(endpointPet + petID, header, base.emptyParameter);
    }


    @And("I verify add new pet response already correct")
    public void iVerifyAddNewPetResponseAlreadyCorrect() {
        assertThat(jsonNode1, equalTo(jsonNode2));
    }

    @And("I verify pet data already updated")
    public void iVerifyPetDataAlreadyUpdated() throws IOException {
        petID = jsonNode2.get("id").asText();
        iRequestToFindThePetByID();
        String result = JsonPath.from(baseStepDef.response.asString()).getString("id");
        assertThat(result, equalTo(petID));
    }

    @And("I verify pet data already deleted")
    public void iVerifyPetDataAlreadyDeleted() throws IOException {
        iRequestToFindThePetByID();
        String result = JsonPath.from(baseStepDef.response.asString()).getString("message");
        assertThat(result, equalTo("Pet not found"));
    }

    @And("I revert deleted data")
    public void iRevertDeletedData() throws IOException {
        String payload = base.getPayload("src/test/resources/payload/defaultPet.json");
        base.callEndpointHttpMethodPostString(endpointPet, base.emptyHeader, base.emptyParameter, payload);
    }

    @When("I request to upload pet image")
    public void iRequestToUploadPetImage() throws IOException {
        String filepath = "src/test/resources/payload/cat-image.jpeg";
        base.callEndpointHttpMethodPostUploadFile(endpointPet+petID+endpointPetUploadImage, filepath, base.emptyHeader, base.emptyParameter, "");
        System.out.println(baseStepDef.response.asString());
    }
}
