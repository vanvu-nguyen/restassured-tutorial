package test.untils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;
import test.modal.RequestCapability;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ProjectInfo extends RequestCapability {
    static String baseUri;
    static String projectKey;

    public ProjectInfo(String baseUri, String projectKey) {
        this.baseUri = baseUri;
        this.projectKey = projectKey;
    }

    public String getIssueTypeId(String issueType) {
        Map<String, List<Map<String, String>>> responseParam = JsonPath.from(getProjectInfo().asString()).get();
        List<Map<String, String>> issueTypes = responseParam.get("issueTypes");
        String projectId = null;
        for (Map<String, String> issue: issueTypes) {
            if (issue.get("name").equalsIgnoreCase(issueType)) {
                projectId = issue.get("id");
                break;
            }
        }
        return projectId;
    }

    private static Response getProjectInfo() {
        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.basePath("/rest/api/3/project/" + projectKey);
        request.header(DEFAULT_HEADER);

        String cred = EMAIL.concat(":").concat(TOKEN);

        byte[] encodedCred = Base64.encodeBase64(cred.getBytes());
        String encodedCredString = new String(encodedCred);
        request.header(RequestCapability.getAuthentication(encodedCredString));
        request.header(ACCEPT_JSON_HEADER);

        return request.get();
    }

}
