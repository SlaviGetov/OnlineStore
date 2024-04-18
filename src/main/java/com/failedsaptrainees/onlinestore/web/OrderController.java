package com.failedsaptrainees.onlinestore.web;


import com.failedsaptrainees.onlinestore.DTO.Views.OrderViewDTO;
import com.failedsaptrainees.onlinestore.exceptions.OrderException;
import com.failedsaptrainees.onlinestore.models.CartProductModel;
import com.failedsaptrainees.onlinestore.models.OrderModel;
import com.failedsaptrainees.onlinestore.models.OrderProductModel;
import com.failedsaptrainees.onlinestore.services.CartProductService;
import com.failedsaptrainees.onlinestore.services.OrderService;
import com.failedsaptrainees.onlinestore.utils.RedirectAttributeUtils;
import jakarta.persistence.Tuple;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartProductService cartProductService;

    @RequestMapping("/send")
    public String sendOrder(HttpSession httpSession, RedirectAttributes redirectAttributes)
    {

        List<CartProductModel> cartList = cartProductService.getCart(httpSession);
        try{
            orderService.sendOrder(cartList);
            cartProductService.emptyCart(httpSession);
            redirectAttributes.addFlashAttribute("success", "Your order has been placed successfully!");
        } catch (OrderException orderException)
        {
            RedirectAttributeUtils.addErrorAttribute(redirectAttributes, orderException.getMessage());
        }

        return "redirect:/cart";
    }

    @RequestMapping("/all")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String allOrders(Model model)
    {
        List<OrderModel> allOrdersList = orderService.getAllOrdersOrdered();
        List<OrderViewDTO> orderViewDTOS = new ArrayList<>();

        for (OrderModel orderModel : allOrdersList) {
            orderViewDTOS.add(new OrderViewDTO(
                    orderModel,
                    orderService.getOrderProducts(orderModel)
            ));
        }

        model.addAttribute("orders", orderViewDTOS);

        return "employee/viewOrders";
    }


}
