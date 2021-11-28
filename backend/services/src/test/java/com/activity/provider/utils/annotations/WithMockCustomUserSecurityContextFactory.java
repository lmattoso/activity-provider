package com.activity.provider.utils.annotations;

import com.activity.provider.domain.Profile;
import com.activity.provider.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    private static final String USER_ADMIN = "admin";
    private static final String USER_USER = "user";
    private static final String USER_INVALID_USER = "invalid_user";
    private static final String AUTH_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlJUZzJOVGc0TnpoQlJVRTBORGd5TkRreE1FWTBNVFk0TmpRMk9ESkVRalJETlRjMVJEbERPQSJ9.eyJodHRwczovL3N1cHBseXN0YWNrLmNvbS9wb3J0YWxDb25maWdzIjoiW3tcInV1aWRcIjpcIjY5MTVlMjg0LTYyOGQtNDhjOS1iYzNiLWZkYjViMWFiMjY0YVwiLFwibmFtZVwiOlwiZGV2X3VtaWNvcmVcIixcInR5cGVcIjpcIklNXCIsXCJhdXRob3JpemF0aW9uXCI6e1wicm9sZXNcIjpbXCJhZG1pblwiXSxcInRlbmFudHNcIjpbXCJ1bWljb3JlXCJdfSxcImludml0ZUlkXCI6XCJmNjdiMjRlZS1hYzU2LTQ3ZDgtYWNlNC1lOTQzZTVmN2ZjYWRcIixcIm5BY2N0SURcIjozfV0iLCJodHRwczovL3N1cHBseXN0YWNrLmNvbS91c2VyX21ldGFkYXRhIjp7ImZpcnN0TG9naW4iOmZhbHNlfSwiaHR0cHM6Ly9zdXBwbHlzdGFjay5jb20vdXNlcl9iYXNpYyI6eyJlbWFpbCI6ImxtYXR0b3NvQHZpc3VhbG51dHMuY29tIiwibmFtZSI6Ikx1w61zIE1hdHRvc28iLCJwaWN0dXJlIjoiaHR0cHM6Ly9hcmd1cy1jbG91ZGZyb250LnMzLWV1LXdlc3QtMS5hbWF6b25hd3MuY29tL3Vtcy9kZWZhdWx0LXByb2ZpbGUtcGljLmpwZyIsImNyZWF0ZWRfYXQiOiIyMDIwLTA0LTEzVDExOjIyOjU5LjQxOFoiLCJleHRlcm5hbF9pZCI6Ijg2ODdhYjc2LTY3MjQtNDIwYy04ZTNhLTcyY2U1ODdmNzA1MiJ9LCJpc3MiOiJodHRwczovL3NsaWNrc3MuZXUuYXV0aDAuY29tLyIsInN1YiI6ImF1dGgwfDVlOTQ0YjkzNDFkYzBlMGJkNzg4NTA0MyIsImF1ZCI6WyJodHRwOi8vdW1zLnN1cHBseXN0YWNrLmNvbSIsImh0dHBzOi8vc2xpY2tzcy5ldS5hdXRoMC5jb20vdXNlcmluZm8iXSwiaWF0IjoxNTk5NTY2NjYzLCJleHAiOjE1OTk2NTMwNjMsImF6cCI6IkRyY2ZzdWFUQk1NTFB4UVVrU2tMbnNFRWg2UTZLY2JsIiwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCBvZmZsaW5lX2FjY2VzcyIsImd0eSI6WyJyZWZyZXNoX3Rva2VuIiwicGFzc3dvcmQiXX0.ywIGiu_rmK92vCRc98RxNqZaMkAY51s6aKReXqko5ik5crDFfHVKvViZ99NI-Mpr9-HnogqN7UZ5RUcrMSdDn7hWnPAKrNzmMS2fhaT3ilHmcSK4I0eJtH8fayEAPFg14y4dS-5V4vowpSCGWlFB7o2qWk4ftT--V7milZbIawF8ONRa9OkkWCpeM1urUnOAxVbd4FpmfcTLu2YU90uXe2A6klPRh7C6DQ5OhQKmxzmMSfDLRyq_CGFizjkC388i0aj0Gx3fNaClU97YCZQ59GhbyEHkGdZEE0QDYgMHQC0lpTpih_ZqLQBRCb4N-uiABc4uqIR3ePyP82JQIy0TYA";

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        if(USER_INVALID_USER.equals(customUser.username())) {
            return context;
        }

        User user = User.builder()
            .email("admin@test.com")
            .id(1l)
            .name("Admin")
            .profile(Profile.builder().code("ADMIN").build())
            .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        context.setAuthentication(authentication);

        return context;
    }
}
