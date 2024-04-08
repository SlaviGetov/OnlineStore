package com.failedsaptrainees.onlinestore.eventlisteners;

import com.failedsaptrainees.onlinestore.models.CartProductModel;
import com.failedsaptrainees.onlinestore.services.CartProductService;
import com.failedsaptrainees.onlinestore.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationSuccessListener extends SavedRequestAwareAuthenticationSuccessHandler {


    @Autowired
    private CartProductService cartProductService;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        System.out.println(authentication.getName() + " logged in!");
        System.out.println(session.getId());

        mergeCartFromSession(authentication, session);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private void mergeCartFromSession(Authentication authentication, HttpSession httpSession)
    {
        List<CartProductModel> cartProductModelListInSession = cartProductService.getCartFromSession(httpSession);
        List<CartProductModel> cartProductModelListInDB = cartProductService.getCartFromDB();

        if(cartProductModelListInSession.isEmpty())
        {
            return;
        }

        for (CartProductModel cartProductModel : cartProductModelListInSession) {
            cartProductModel.setUser(userService.getUserByEmail(authentication.getName()));
        }

        cartProductModelListInDB.addAll(cartProductModelListInSession);
        cartProductService.saveCart(cartProductModelListInDB, httpSession);
    }

}
