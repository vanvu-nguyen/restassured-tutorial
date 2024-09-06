package test.test;

import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;
import test.modal.RequestCapability;

import static io.restassured.RestAssured.given;

public class GETTransitions {
    public static void main(String[] args) {
        String baseUri = "https://sdetvu.atlassian.net";

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.basePath("/rest/api/3/issue/SDET-19/transitions");
        request.header(RequestCapability.defaultHeader());
        request.header(new Header("Accept", "application/json"));

        String email = RequestCapability.getEmailAdr();
        String apiToken = RequestCapability.getApiToken();
        String cred = email.concat(":").concat(apiToken);
        byte[] encodedCred = Base64.encodeBase64(cred.getBytes());
        String encodedCredString = new String(encodedCred);
        request.header(RequestCapability.getAuthentication(encodedCredString));

        Response response = request.get();
        response.prettyPrint();


    }
}
