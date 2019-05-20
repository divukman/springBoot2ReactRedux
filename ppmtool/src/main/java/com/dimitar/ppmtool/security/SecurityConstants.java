package com.dimitar.ppmtool.security;

public class SecurityConstants {
    public static final String SIGN_UP_URL = "api/users/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET =  "SecretKeyToGenerateJWTs!@#$%^&1233!@@@1.";
    public static final String TOKEN_PREFIX = "Bearer "; // Space!
    public static final String HEADER_STRING = "Authorization";
    public static final Long EXPIRATION_TIME = 30_000L;
}
