package com.dimitar.ppmtool.security;

public class SecurityConstants {
    public static final String SIGN_UP_URL = "/api/users/**";
    public static final String H2_URL = "/h2-console/**";
    public static final String SECRET =  "U2VjcmV0S2V5VG9HZW5lcmF0ZUpXVHM=";
    public static final String TOKEN_PREFIX = "Bearer "; // Space at the end needed!
    public static final String HEADER_STRING = "Authorization";
    public static final Long EXPIRATION_TIME = 30_000L;
}
