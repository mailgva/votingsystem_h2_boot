package com.voting.web.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.voting.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

public abstract class AbstractOauthController {
    @Autowired
    private UserDetailsService service;

    @Autowired
    private RestTemplate template;

    protected ModelAndView authorizeAndRedirect(String login, String email, RedirectAttributes attr) {
        try {
            UserDetails userDetails = service.loadUserByUsername(email);
            getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            return new ModelAndView("redirect:/voting");
        } catch (UsernameNotFoundException ex) {
            attr.addFlashAttribute("userTo", new UserTo(login, email));
            return new ModelAndView("redirect:/register");
        }
    }

    protected String getAccessToken(String code, final String ACCESS_TOKEN_URL, final String CLIENT_ID,
                                    final String CLIENT_SECRET, final String REDIRECT_URI) {
        UriComponentsBuilder builder = fromHttpUrl(ACCESS_TOKEN_URL)
                .queryParam("client_id", CLIENT_ID)
                .queryParam("client_secret", CLIENT_SECRET)
                .queryParam("code", code)
                .queryParam("redirect_uri", REDIRECT_URI);
        ResponseEntity<JsonNode> tokenEntity = template.postForEntity(builder.build().encode().toUri(), null, JsonNode.class);
        return tokenEntity.getBody().get("access_token").asText();
    }

    protected String getEmail(String accessToken, final String GET_LOGIN_URL) {
        UriComponentsBuilder builder = fromHttpUrl(GET_LOGIN_URL).queryParam("access_token", accessToken);
        ResponseEntity<JsonNode> entityUser = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
        return entityUser.getBody().get("email").asText();
    }

    protected String getLogin(String accessToken, final String GET_LOGIN_URL) {
        UriComponentsBuilder builder = fromHttpUrl(GET_LOGIN_URL).queryParam("access_token", accessToken);
        ResponseEntity<JsonNode> entityUser = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
        return entityUser.getBody().get("name").asText();
    }

    protected ModelAndView authenticate(String code, String state, RedirectAttributes attr, HttpServletRequest request,
                                        final String ACCESS_TOKEN_URL, final String CLIENT_ID,
                                        final String CLIENT_SECRET, final String GET_LOGIN_URL, final String REDIRECT_URI) {
        if (state.equals("voting_csrf_token_auth")) {
            //String callbackUrl = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1) + "callback";
            String accessToken = getAccessToken(code, ACCESS_TOKEN_URL, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);
            String login = getLogin(accessToken, GET_LOGIN_URL);
            String email = getEmail(accessToken, GET_LOGIN_URL);
            if(email.equals("null")) {
                email = "test@test.com";
            }
            return authorizeAndRedirect(login, email, attr);
        }
        return null;
    }
}
