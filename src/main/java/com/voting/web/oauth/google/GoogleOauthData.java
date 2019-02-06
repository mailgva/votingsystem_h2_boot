package com.voting.web.oauth.google;

public class GoogleOauthData {
    protected static final String AUTHORIZE_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    protected static final String ACCESS_TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";
    protected static final String GET_LOGIN_URL = "https://www.googleapis.com/oauth2/v3/userinfo?alt=json";
    protected static final String CLIENT_ID = "926737076593-4cjdlfnvr8i5et1e6lol0r3maa0mt2kb.apps.googleusercontent.com";
    protected static final String CLIENT_SECRET = "lQTAetngmR5qGCHhbROjqttZ";
    protected static final String REDIRECT_URI = "https://gva-votingsystem-boot.herokuapp.com/oauth/google/callback";
    protected static final String CODE = "voting_csrf_token_auth";
}
