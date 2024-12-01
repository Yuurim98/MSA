package com.sparta.msa_exam.auth.entity;

public enum Role {
    MANAGER(Authority.MANAGER),
    USER(Authority.USER);

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String MANAGER = "ROLE_MANAGER";
    }
}
