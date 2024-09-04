package test.modal;

public class IssueFields {

    private Fields fields;

    public IssueFields(Fields fields) {
        this.fields = fields;
    }

    public Fields getFields() {
        return fields;
    }

    public static class Fields {
        private String summary;
        private Project project;
        private IssueType issuetype;

        public Fields(String summary, Project project, IssueType issuetype) {
            this.summary = summary;
            this.project = project;
            this.issuetype = issuetype;
        }
        public Project getProject() {
            return this.project;
        }

        public String getSummary() {
            return this.summary;
        }

        public IssueType getIssuetype() {
            return this.issuetype;
        }
    }

    public static class Project{
        private String key;

        public Project(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public static class IssueType{
        private String id;

        public IssueType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }


}
