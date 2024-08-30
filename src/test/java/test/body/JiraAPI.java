package test.body;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;
import test.modal.RequestCapability;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JiraAPI {
    public static void main(String[] args) {
        String baseUri = "https://sdetvu.atlassian.net";
        String basePath = "/rest/api/3/project/SDET";

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.basePath(basePath);
        request.header(RequestCapability.getHeaderCofig());

        String email = RequestCapability.getEmailAdr();
        String apiToken = RequestCapability.getApiToken();
        String cred = email.concat(":").concat(apiToken);
        byte[] encodedCred = Base64.encodeBase64(cred.getBytes());
        String encodedCredString = new String(encodedCred);
        request.header(RequestCapability.getAuthentication(encodedCredString));

        Response response = request.get();
        response.prettyPrint();

        Map<String, List<Map<String, String>>> responseParam = JsonPath.from(response.asString()).get();
        List<Map<String, String>> issueTypes = responseParam.get("issueTypes");
        for (Map<String, String> issueType: issueTypes) {
            System.out.println(issueType.get("id"));
            System.out.println(issueType.get("name"));
            System.out.println("------------");
        }
    }
}
