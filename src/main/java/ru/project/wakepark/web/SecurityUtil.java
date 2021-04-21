package ru.project.wakepark.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.project.wakepark.AuthorizedUser;

import static java.util.Objects.requireNonNull;

public class SecurityUtil {

    protected static final Logger log = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil() {
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            log.debug("No authorized user found");
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

    public static int authUserId() {
        return get().getUserTo().id();
    }

    public static int authCompanyId() {
        return get().getUserTo().getCompanyId();
    }

}