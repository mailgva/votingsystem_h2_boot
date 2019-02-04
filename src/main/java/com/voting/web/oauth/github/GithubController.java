package com.voting.web.oauth.github;

import com.fasterxml.jackson.databind.JsonNode;
import com.voting.to.UserTo;
import com.voting.web.oauth.AbstractOauthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import static com.voting.web.oauth.github.GitHubOauthData.*;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Controller
@RequestMapping("/oauth/github")
public class GithubController extends AbstractOauthController {
    @Autowired
    private RestTemplate template;

      @RequestMapping("/authorize")
    public String authorize(HttpServletRequest request) {
        String callbackUrl = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1) + "callback";
        return "redirect:" + AUTHORIZE_URL + "?client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET + "&redirect_uri=" + callbackUrl + "&state=" + CODE;
    }

    @RequestMapping("/callback")
    public ModelAndView authenticate(@RequestParam String code, @RequestParam String state, RedirectAttributes attr) {
        if (state.equals("voting_csrf_token_auth")) {
            String accessToken = getAccessToken(code);
            String login = getLogin(accessToken);
            String email = getEmail(accessToken);
            if(email.equals("null")) {
                email = "test@test.com";
            }
            return authorizeAndRedirect(login, email, attr);
        }
        return null;
    }

    private String getAccessToken(String code) {
        UriComponentsBuilder builder = fromHttpUrl(ACCESS_TOKEN_URL)
                .queryParam("client_id", CLIENT_ID)
                .queryParam("client_secret", CLIENT_SECRET)
                .queryParam("code", code)
                .queryParam("redirect_uri", REDIRECT_URI);
        ResponseEntity<JsonNode> tokenEntity = template.postForEntity(builder.build().encode().toUri(), null, JsonNode.class);
        return tokenEntity.getBody().get("access_token").asText();
    }

    private String getEmail(String accessToken) {
        UriComponentsBuilder builder = fromHttpUrl(GET_LOGIN_URL).queryParam("access_token", accessToken);
        ResponseEntity<JsonNode> entityUser = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
        return entityUser.getBody().get("email").asText();
    }

    private String getLogin(String accessToken) {
        UriComponentsBuilder builder = fromHttpUrl(GET_LOGIN_URL).queryParam("access_token", accessToken);
        ResponseEntity<JsonNode> entityUser = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
        return entityUser.getBody().get("name").asText();
    }


}
