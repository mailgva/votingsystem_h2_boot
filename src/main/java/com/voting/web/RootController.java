package com.voting.web;

import com.voting.AuthorizedUser;
import com.voting.to.UserTo;
import com.voting.util.UserUtil;
import com.voting.web.user.AbstractUserController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.voting.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;

@Controller
public class RootController extends AbstractUserController {
    @GetMapping("/")
    public String root() {
        return "redirect:voting";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String users(/*Model model, HttpServletRequest request*/) {
        //model = setModelAttrs(model, request);
        return "users";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/voting")
    public String voting(/*Model model, HttpServletRequest request*/) {
        //model = setModelAttrs(model, request);
        return "dailymenu";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/votes")
    public String votes(/*Model model, HttpServletRequest request*/) {
        //model = setModelAttrs(model, request);
        return "votes";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dishes")
    public String dishes(/*Model model, HttpServletRequest request*/) {
        //model = setModelAttrs(model, request);
        return "dishes";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/restaurants")
    public String restaurants(/*Model model, HttpServletRequest request*/) {
        //model = setModelAttrs(model, request);
        return "restaurants";
    }

    @GetMapping("/profile")
    public String profile(ModelMap model, @AuthenticationPrincipal AuthorizedUser authUser) {
        model.addAttribute("userTo", authUser.getUserTo());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status, @AuthenticationPrincipal AuthorizedUser authUser) {
        if (result.hasErrors()) {
            return "profile";
        }
        try {
            super.update(userTo, authUser.getId());
            authUser.update(userTo);
            status.setComplete();
            return "redirect:voting";
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("email", EXCEPTION_DUPLICATE_EMAIL);
            return "profile";
        }
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        }
        try {
            super.create(UserUtil.createNewFromTo(userTo));
            status.setComplete();
            return "redirect:login?message=app.registered&username=" + userTo.getEmail();
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("email", EXCEPTION_DUPLICATE_EMAIL);
            model.addAttribute("register", true);
            return "profile";
        }
    }



    @ModelAttribute("localeMessages")
    private String getLocaleMessages(HttpServletRequest request){
        return createLocalMessages(request);
    }

    @ModelAttribute("request_uri")
    private String getRequestUri(HttpServletRequest request){
        return request.getRequestURL().toString();
    }

    @ModelAttribute("date")
    private String getDate(){
        return LocalDate.now().plusDays((LocalDateTime.now().getHour() < 11 ? 0 : 1)).toString();
    }

    @ModelAttribute("isAdmin")
    private boolean isAdmin(){
        try {
            return SecurityUtil.isAdmin();
        } catch (Exception e) {
            return false;
        }
    }

    @ModelAttribute("userName")
    private String getUserName(){
        try {
            return SecurityUtil.authUserName();
        } catch (Exception e) {
            return null;
        }
    }

    private String createLocalMessages(HttpServletRequest request) {
        String localeMessages = "var i18n = {";

        String[] mesArray =
                new String[]{"common.deleted", "common.saved", "common.selectSave", "common.noPresentMenuDay", "common.enabled",
                        "common.disabled", "common.errorStatus", "dish.name", "dish.price", "common.menuWasGenerated", "common.confirmDelete",
                        "common.dataTable.search", "common.dataTable.noDataAvalaible", "common.dataTable.infoEmpty", "common.dataTable.info"};

        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages/app", request.getLocale());

        localeMessages += List.of(mesArray).stream()
                .map(key -> ("'" + key + "' : '" + resourceBundle.getString(key) + "'"))
                .collect(Collectors.joining(",\n"));

        String pageName =  request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/") + 1);
        if(pageName.equalsIgnoreCase("restaurants") ||
                pageName.equalsIgnoreCase("dishes") ||
                pageName.equalsIgnoreCase("users")) {
            if(pageName.equalsIgnoreCase("restaurants")) {pageName = "resto";}
            if(pageName.equalsIgnoreCase("dishes"))      {pageName = "dish";}
            if(pageName.equalsIgnoreCase("users"))       {pageName = "user";}
            localeMessages += (", 'addTitle' : '" + resourceBundle.getString(pageName + ".add") + "',");
            localeMessages += ("'editTitle' : '" + resourceBundle.getString(pageName + ".edit") + "'");
        }

        localeMessages += "};";

        return localeMessages;
    }

}
