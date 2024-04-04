package com.failedsaptrainees.onlinestore.eventlisteners;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationSuccessListener extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        System.out.println(authentication.getName() + " logged in!");
        System.out.println(session.getId());

        mergeCartFromSession();
        super.onAuthenticationSuccess(request, response, authentication);
    }

    //TODO: Check if the user has a cart in his session, if so merge it with an existing cart on his account or add a new one
    private void mergeCartFromSession()
    {

    }

}
