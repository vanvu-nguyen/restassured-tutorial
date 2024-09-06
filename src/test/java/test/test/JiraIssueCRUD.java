package test.test;

import io.restassured.specification.RequestSpecification;
import test.api_flow.IssueFlow;
import test.modal.RequestCapability;
import test.untils.AuthenticationHandler;

import static io.restassured.RestAssured.given;

public class JiraIssueCRUD {
    public static void main(String[] args) {
        String baseUri = "https://sdetvu.atlassian.net";
        String projectKey = "SDET";

        String email = RequestCapability.getEmailAdr();
        String apiToken = RequestCapability.getApiToken();
        String encodedCredStr = AuthenticationHandler.encodedCredStr(email, apiToken);

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(RequestCapability.defaultHeader());
        request.header(RequestCapability.acceptJSONHeader());
        request.header(RequestCapability.getAuthentication(encodedCredStr));

        IssueFlow issueFlow = new IssueFlow(request, baseUri, projectKey, "task");
        System.out.println("-------------> CREATE <-------------");
        issueFlow.createIssue();
        System.out.println("-------------> READ <-------------");
        issueFlow.verifyIssueDetail("To Do");
        System.out.println("-------------> UPDATE <-------------");
        issueFlow.updateIssue("Done");
        issueFlow.verifyIssueDetail("Done");
        System.out.println("-------------> DELETE <-------------");
        issueFlow.deleteIssue();
    }
}
