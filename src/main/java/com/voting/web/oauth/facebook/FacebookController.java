package com.voting.web.oauth.facebook;

import com.voting.web.oauth.AbstractOauthController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.voting.web.oauth.facebook.FacebookOauthData.*;

@Controller
@RequestMapping("/oauth/facebook")
public class FacebookController extends AbstractOauthController {

    @RequestMapping("/authorize")
    public String authorize(HttpServletRequest request) {
        String callbackUrl = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1) + "callback";
        return "redirect:" + AUTHORIZE_URL + "?client_id=" + CLIENT_ID +
                 "&redirect_uri=" + callbackUrl + "&state=" + CODE;
    }

    @RequestMapping("/callback")
    public ModelAndView authenticate(@RequestParam String code, @RequestParam String state, RedirectAttributes attr, HttpServletRequest request) {
        return super.authenticate(code, state, attr, request, ACCESS_TOKEN_URL, CLIENT_ID, CLIENT_SECRET, GET_LOGIN_URL);
    }


}
