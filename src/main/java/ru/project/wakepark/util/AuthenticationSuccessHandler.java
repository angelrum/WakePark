package ru.project.wakepark.util;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    public AuthenticationSuccessHandler() {
        super();
        setUseReferer(true);
    }
}
