package com.voting.web.oauth.facebook;

public class FacebookOauthData {
    protected static final String AUTHORIZE_URL = "https://graph.facebook.com/oauth/authorize";
    protected static final String ACCESS_TOKEN_URL = "https://graph.facebook.com/oauth/access_token";
    protected static final String GET_LOGIN_URL = "https://graph.facebook.com/me?fields=name,email";
    protected static final String CLIENT_ID = "753232188383949";
    protected static final String CLIENT_SECRET = "720db3afe09d6fb5cd7ed0b9c409a600";
    protected static final String REDIRECT_URI = "http://gva-votingsystem-boot.herokuapp.com/oauth/facebook/callback";
    protected static final String CODE = "voting_csrf_token_auth";
}
