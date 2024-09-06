package test.untils;

import org.apache.commons.codec.binary.Base64;

public class AuthenticationHandler {
    public static String encodedCredStr(String email, String apiToken) {
        String cred = email.concat(":").concat(apiToken);
        byte[] encodedCred = Base64.encodeBase64(cred.getBytes());
        return new String(encodedCred);
    }
}
