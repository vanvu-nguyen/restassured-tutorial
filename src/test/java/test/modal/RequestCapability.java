package test.modal;

import io.restassured.http.Header;

public class RequestCapability {

    protected static final String EMAIL = "nv.vu29@gmail.com";
    protected static final String TOKEN = "ATATT3xFfGF0etHIuPsy2usOC_MuejNuemSn_WAV_agzFyVWB5sbLRzA0_j0hUek4yxflFDzc9bg3P4kCvQvhWKuz5RP1HiJDHX-ZEz_jGpUW62PRzndxu1OukUN6eWx9bQSAgS6AQWRmhXpu50vvjSkW8_CvBT941zkr8ziyMbsFwXI8T4UZ7U=8499A128";
    protected static final Header DEFAULT_HEADER = new Header("Content-type", "application/json; charset=UTF-8");
    protected static final Header ACCEPT_JSON_HEADER = new Header("Accept", "application/json");

    public static Header getAuthentication(String encodedCredString) {
        return new Header("Authorization", "Basic ".concat(encodedCredString));
    }



}
