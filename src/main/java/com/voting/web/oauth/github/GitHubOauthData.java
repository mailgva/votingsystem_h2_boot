package com.voting.web.oauth.github;

public class GitHubOauthData {
    protected static final String AUTHORIZE_URL = "https://github.com/login/oauth/authorize";
    protected static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    protected static final String GET_EMAIL_URL = "https://api.github.com/user/emails";
    protected static final String GET_LOGIN_URL = "https://api.github.com/user";
    protected static final String CLIENT_ID = "124edda838cddd271608";
    protected static final String CLIENT_SECRET = "564a9626b21b810f29932b50ce979336f270a89a";
    protected static final String REDIRECT_URI = "https://gva-votingsystem-boot.herokuapp.com/oauth/github/callback";
    protected static final String CODE = "voting_csrf_token_auth";
}
