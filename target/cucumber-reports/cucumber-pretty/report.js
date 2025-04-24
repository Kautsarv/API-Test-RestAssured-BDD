$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/features/pet.feature");
formatter.feature({
  "name": "Everything About Pets",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "Successfully Add New Pet To Store",
  "description": "",
  "keyword": "Scenario"
});
formatter.step({
  "name": "I have pet data to add",
  "keyword": "Given "
});
formatter.match({
  "location": "stepdefinitions.petStepDef.iHavePetDataToAdd()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I request to add a new pet",
  "keyword": "When "
});
formatter.match({
  "location": "stepdefinitions.petStepDef.iSubmitRequestAddNewPet()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "The client should receive HTTP 200 response status",
  "keyword": "Then "
});
formatter.match({
  "location": "stepdefinitions.petStepDef.theClientShouldReceiveHTTPResponseStatus(int)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I verify add new pet response already correct",
  "keyword": "And "
});
formatter.match({
  "location": "stepdefinitions.petStepDef.iVerifyAddNewPetResponseAlreadyCorrect()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Successfully Update Existing Pet Data",
  "description": "",
  "keyword": "Scenario"
});
formatter.step({
  "name": "I have an existing pet",
  "keyword": "Given "
});
formatter.match({
  "location": "stepdefinitions.petStepDef.iHaveAnExistingPet()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I request to update the pet data",
  "keyword": "When "
});
formatter.match({
  "location": "stepdefinitions.petStepDef.iRequestToUpdateThePetData()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "The client should receive HTTP 200 response status",
  "keyword": "Then "
});
formatter.match({
  "location": "stepdefinitions.petStepDef.theClientShouldReceiveHTTPResponseStatus(int)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I verify pet data already updated",
  "keyword": "And "
});
formatter.match({
  "location": "stepdefinitions.petStepDef.iVerifyPetDataAlreadyUpdated()"
});
formatter.result({
  "status": "passed"
});
});