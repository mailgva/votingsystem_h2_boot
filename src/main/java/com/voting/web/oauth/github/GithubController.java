package com.voting.web.oauth.github;

import com.voting.web.oauth.AbstractOauthController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import static com.voting.web.oauth.github.GitHubOauthData.*;

@Controller
@RequestMapping("/oauth/github")
public class GithubController extends AbstractOauthController {

    @RequestMapping("/authorize")
    public String authorize() {
        return "redirect:" + AUTHORIZE_URL + "?client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET + "&redirect_uri=" + REDIRECT_URI + "&state=" + CODE;
    }

    @RequestMapping("/callback")
    public ModelAndView authenticate(@RequestParam String code, @RequestParam String state, RedirectAttributes attr) {
        return super.authenticate(code, state, attr, ACCESS_TOKEN_URL, CLIENT_ID, CLIENT_SECRET, GET_LOGIN_URL, REDIRECT_URI);
    }

}
