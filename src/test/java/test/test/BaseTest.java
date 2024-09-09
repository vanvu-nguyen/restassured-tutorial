package test.test;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import test.modal.RequestCapability;
import test.untils.AuthenticationHandler;

import static io.restassured.RestAssured.given;

public class BaseTest extends RequestCapability {
    protected RequestSpecification request;
    protected String encodedCredStr;
    protected String projectKey;
    protected String baseUri;

    @BeforeSuite
    public void beforeSuite() {
        encodedCredStr = AuthenticationHandler.encodedCredStr(EMAIL, TOKEN);
        baseUri = "https://sdetvu.atlassian.net";
        projectKey = "SDET";
    }

    @BeforeTest
    public void beforeTest() {
        request = given();
        request.baseUri(baseUri);
        request.header(DEFAULT_HEADER);
        request.header(ACCEPT_JSON_HEADER);
        request.header(getAuthentication(encodedCredStr));
    }
}
