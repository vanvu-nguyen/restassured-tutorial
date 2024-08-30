package test.body;

import test.untils.ProjectInfo;

public class JiraAPI {
    public static void main(String[] args) {
        String baseUri = "https://sdetvu.atlassian.net";
        String basePath = "/rest/api/3/project/SDET";

        ProjectInfo projectInfo = new ProjectInfo(baseUri, basePath);
        projectInfo.getProjectIdByName("subtask");
    }
}
