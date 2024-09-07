package test.api_flow;

import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import test.Builder.BodyJSONBuilder;
import test.Builder.IssueContentBuilder;
import test.modal.IssueFields;
import test.modal.IssueTransition;
import test.untils.ProjectInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class IssueFlow {

    private static Map<String, String> transitionTypeMap = new HashMap<>();
    private RequestSpecification request;
    private Response response;
    private String baseUri;
    private String projectKey;
    private String issueTypeStr;
    private IssueFields issueFields;
    private static final String ISSUE_PATH_PREFIX = "/rest/api/3/issue";
    private String createdIssueKey;
    String status;

    static {
        transitionTypeMap.put("11", "To Do");
        transitionTypeMap.put("21", "In Progress");
        transitionTypeMap.put("31", "Done");
    }

    public IssueFlow(RequestSpecification request, String baseUri, String projectKey, String issueTypeStr) {
        this.request = request;
        this.baseUri = baseUri;
        this.projectKey = projectKey;
        this.issueTypeStr = issueTypeStr;
        this.status = "To Do";
    }

    public void createIssue() {
        System.out.println("-------------> CREATE <-------------");
        ProjectInfo projectInfo = new ProjectInfo(baseUri, projectKey);
        String taskTypeId = projectInfo.getIssueTypeId(issueTypeStr);
        String randomSummary = new Faker(new Locale("en")).name().title();
        IssueContentBuilder issueContentBuilder = new IssueContentBuilder();
        String issueFieldsContent = issueContentBuilder.build(projectKey, taskTypeId, randomSummary);
        issueFields = issueContentBuilder.getIssueFields();
        response = request.body(issueFieldsContent).post(ISSUE_PATH_PREFIX);
        Map<String, String> responseBody = JsonPath.from(response.asString()).get();
        createdIssueKey = responseBody.get("key");
    }

    public void verifyIssueDetail(String status) {
        Map<String, String> issueInfo = getIssueInfo();
        String expectedSummary = issueFields.getFields().getSummary();
        String expectedStatus = status;
        String actualSummary = issueInfo.get("summary");
        String actualStatus = issueInfo.get("status");
        System.out.println("expectedSummary: " + expectedSummary);
        System.out.println("actualSummary: " + actualSummary);
        System.out.println("expectedStatus: " + expectedStatus);
        System.out.println("actualStatus: " + actualStatus);
    }

    public void updateIssue(String issueStatusStr) {
        System.out.println("-------------> UPDATE <-------------");
        String targetTransitionId = null;
        for (String transitionId: transitionTypeMap.keySet()) {
            if (transitionTypeMap.get(transitionId).equalsIgnoreCase(issueStatusStr)) {
                targetTransitionId = transitionId;
                break;
            }
        }
        if (targetTransitionId == null) {
            throw new RuntimeException("[ERR] Issue status string provided is not supported!");
        }
        String issueTransitionPath = ISSUE_PATH_PREFIX + "/" + createdIssueKey + "/transitions";
        IssueTransition.Transition transition = new IssueTransition.Transition(targetTransitionId);
        IssueTransition issueTransition = new IssueTransition(transition);
        String transitionBody = BodyJSONBuilder.getJSONContent(issueTransition);

        request.body(transitionBody).post(issueTransitionPath).then().statusCode(204);
        Map<String, String> issueInfo = getIssueInfo();
        String actualIssueStatus = issueInfo.get("status");
        String expectedIssueStatus = transitionTypeMap.get(targetTransitionId);
        System.out.println("expectedIssueStatus: " + expectedIssueStatus);
        System.out.println("actualIssueStatus: " + actualIssueStatus);
    }

    public void deleteIssue() {
        System.out.println("-------------> DELETE <-------------");
        String path = ISSUE_PATH_PREFIX + "/" + createdIssueKey;
        request.delete(path);

        response = request.get(path);
        Map<String, List<String>> notExistingIssueRes = JsonPath.from(response.body().asString()).get();
        List<String> errorMessages = notExistingIssueRes.get("errorMessages");
        System.out.println("Return msg: " + errorMessages.get(0));

    }

    private Map<String, String> getIssueInfo() {
        String getIssuePath = ISSUE_PATH_PREFIX + "/" + createdIssueKey;
        Response response_ = request.get(getIssuePath);

        Map<String, Object> fields = JsonPath.from(response_.getBody().asString()).get("fields");
        String actualSummary = fields.get("summary").toString();
        Map<String, Object> status = (Map<String, Object>) fields.get("status");
        Map<String, Object> statusCategory = (Map<String, Object>) status.get("statusCategory");
        String actualStatus = statusCategory.get("name").toString();

        Map<String, String> issueInfo = new HashMap<>();
        issueInfo.put("summary", actualSummary);
        issueInfo.put("status", actualStatus);
        return issueInfo;


    }

}
