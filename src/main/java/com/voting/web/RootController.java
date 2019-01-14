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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static com.voting.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;

@Controller
public class RootController extends AbstractUserController {
    @GetMapping("/")
    public String root() {
        return "redirect:voting";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String users(Model model) {
        model = setModelAttrs(model);
        return "users";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/voting")
    public String voting(Model model) {
        model.addAttribute("date", LocalDate.now().plusDays((LocalDateTime.now().getHour() < 11 ? 0 : 1)).toString());
        model = setModelAttrs(model);
        return "dailymenu";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/votes")
    public String votes(Model model) {
        model.addAttribute("date", LocalDate.now().toString());
        model = setModelAttrs(model);
        return "votes";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dishes")
    public String dishes(Model model) {
        model = setModelAttrs(model);
        return "dishes";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/restaurants")
    public String restaurants(Model model) {
        model = setModelAttrs(model);
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

    private Model setModelAttrs(Model m) {
        m.addAttribute("isAdmin", SecurityUtil.isAdmin());
        m.addAttribute("userName", SecurityUtil.authUserName());
        return m;
    }


    /*@GetMapping("/upload")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes)  {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {
            String uploadedFolder = System.getenv("VOTING_ROOT") + "/images/dishes/";
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadedFolder + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }*/

}
