package test.test;

import org.testng.annotations.Test;
import test.api_flow.IssueFlow;

public class JiraIssueCRUD extends BaseTest {

    @Test
    public void testE2EFlow() {
        IssueFlow issueFlow = new IssueFlow(request, baseUri, projectKey, "task");
        issueFlow.createIssue();
        issueFlow.verifyIssueDetail("To Do");
        issueFlow.updateIssue("Done");
        issueFlow.verifyIssueDetail("Done");
        issueFlow.deleteIssue();
    }

    @Test
    public void createIssue() {
        IssueFlow issueFlow = new IssueFlow(request, baseUri, projectKey, "task");
        issueFlow.createIssue();
        issueFlow.verifyIssueDetail("To Do");
    }

    @Test
    public void updateIssue() {
        IssueFlow issueFlow = new IssueFlow(request, baseUri, projectKey, "task");
        issueFlow.createIssue();
        issueFlow.updateIssue("Done");
        issueFlow.verifyIssueDetail("Done");
    }

    @Test
    public void deleteIssue() {
        IssueFlow issueFlow = new IssueFlow(request, baseUri, projectKey, "task");
        issueFlow.createIssue();
        issueFlow.deleteIssue();
        System.out.println("1");
    }
}
