package com.voting.web;

import com.voting.AuthorizedUser;
import com.voting.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.requireNonNull;

public class SecurityUtil {

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    private SecurityUtil() {
    }

    public static int authUserId() {
        return get().getUserTo().getId();
    }

    public static String authUserName() {
        return get().getUserTo().getName();
    }

    public static boolean isAdmin() {
        return get().getAuthorities().contains(Role.ROLE_ADMIN);
    }


}

