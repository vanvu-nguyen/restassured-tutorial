package test.body;

import test.untils.JiraAPI;

public class GetIssueTypeIdByName {
    public static void main(String[] args) {
        String baseUri = "https://sdetvu.atlassian.net";
        String basePath = "/rest/api/3/project/SDET";

        JiraAPI projectInfo = new JiraAPI(baseUri, basePath);
        projectInfo.getIssueTypeIdByName("task");
    }
}
