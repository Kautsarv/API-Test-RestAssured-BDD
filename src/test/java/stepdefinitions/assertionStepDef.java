package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class assertionStepDef {

    @Then("The client should receive HTTP {int} response status")
    public void theClientShouldReceiveHTTPResponseStatus(int httpResponse) {
        assertThat(baseStepDef.response.getStatusCode(), equalTo(httpResponse));
    }

    @And("The response body should contain {string} with value {string}")
    public void iVerifyResponseBodyIdValueShouldBeID(String path, String value) {
        String result = JsonPath.from(baseStepDef.response.asString()).getString(path);
        if (value.equals("NULL")) {
            value = "";
        }
        assertThat(result, containsString(value));
    }

}
