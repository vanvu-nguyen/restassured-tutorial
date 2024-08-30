package test.body;

import test.untils.JiraAPI;

public class JiraCreateIssue {
    public static void main(String[] args) {
        String baseUri = "https://sdetvu.atlassian.net";
        String basePath = "/rest/api/3/issue";

        JiraAPI projectInfo = new JiraAPI(baseUri, basePath);
        projectInfo.createIssue();
    }
}
