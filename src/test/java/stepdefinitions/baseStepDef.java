package stepdefinitions;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class baseStepDef {
    public static Response response;
    private final Properties configProperties = new Properties();
    public Map<String,String> emptyHeader = new HashMap<>();
    public Map<String,String> emptyParameter = new HashMap<>();
    public Map<String,String> emptyBody = new HashMap<>();

    private String getBaseURL() throws IOException {
        configProperties.load(new FileInputStream("endpoint.properties"));
        return configProperties.getProperty("baseURL");
    }

    public String getPayload(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public void callEndpointHttpMethodGetString(String url, Map<String,String> header, Map<String,String> parameter) throws IOException {
        String fullURL = getBaseURL() + url;
        response = RestAssured.given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .headers(header)
                .queryParams(parameter)
                .get(fullURL);
    }

    public void callEndpointHttpMethodPostString(String url, Map<String,String> header, Map<String,String> parameter, String body) throws IOException {
        String fullURL = getBaseURL() + url;
        response = RestAssured.given()
                        .contentType("application/json")
                        .relaxedHTTPSValidation()
                        .headers(header)
                        .queryParams(parameter)
                        .body(body)
                        .when()
                        .post(fullURL);
    }

    public void callEndpointHttpMethodPostMap(String url, Map<String,String> header, Map<String,String> parameter, Map<String,String> body) throws IOException {
        String fullURL = getBaseURL() + url;
        response = RestAssured.given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .headers(header)
                .queryParams(parameter)
                .body(body)
                .when()
                .post(fullURL);
    }

    public void callEndpointHttpMethodPostUploadFile(String url, String filePath, Map<String,String> header, Map<String,String> parameter, String body) throws IOException {
        String fullURL = getBaseURL() + url;
        response = RestAssured.given()
                .multiPart("file", new File(filePath),"multipart/form-data")
                .relaxedHTTPSValidation()
                .headers(header)
                .queryParams(parameter)
                .body(body)
                .when()
                .post(fullURL);
    }

    public void callEndpointHttpMethodPutString(String url, Map<String,String> header, Map<String,String> parameter, String body) throws IOException {
        String fullURL = getBaseURL() + url;
        response = RestAssured.given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .headers(header)
                .queryParams(parameter)
                .body(body)
                .when()
                .put(fullURL);
    }

    public void callEndpointHttpMethodDeleteString(String url, Map<String,String> header, Map<String,String> parameter) throws IOException {
        String fullURL = getBaseURL() + url;
        response = RestAssured.given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .headers(header)
                .queryParams(parameter)
                .when()
                .delete(fullURL);
    }

    public void callEndpointHttpMethodPatchString(String url, Map<String,String> header, Map<String,String> parameter) throws IOException {
        String fullURL = getBaseURL() + url;
        response = RestAssured.given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .headers(header)
                .queryParams(parameter)
                .when()
                .patch(fullURL);
    }
}
