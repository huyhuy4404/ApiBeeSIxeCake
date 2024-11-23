package com.poly.apibeesixecake.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.util.Date;

@Controller
public class CheckoutController {

    private final PayOS payOS;

    public CheckoutController() {
        // Khởi tạo trực tiếp PayOS ở đây
        this.payOS = new PayOS(
                "3ecdfe5f-e475-4b4b-8311-1f82628837d4", // Client ID
                "9ae2bce0-63fa-4fbe-bf07-9b7e75687735", // API Key
                "ac46c054e82498ce02f6246d225990b527bcaeadbaeaed739b857ec75b97c9fc" // Checksum Key
        );
    }

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/success")
    public String success() {
        return "success";
    }

    @RequestMapping(value = "/cancel")
    public String cancel() {
        return "cancel";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create-payment-link", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void checkout(HttpServletRequest request, HttpServletResponse response) {
        try {
            final String baseUrl = getBaseUrl(request);
            final String productName = "Sản phẩm mẫu";
            final String description = "Thanh toán đơn hàng";
            final String returnUrl = baseUrl + "/success";
            final String cancelUrl = baseUrl + "/cancel";
            final int price = 50000;

            // Gen mã đơn hàng
            String currentTimeString = String.valueOf(new Date().getTime());
            long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

            // Dữ liệu sản phẩm
            ItemData item = ItemData.builder()
                    .name(productName)
                    .quantity(1)
                    .price(price)
                    .build();

            // Dữ liệu thanh toán
            PaymentData paymentData = PaymentData.builder()
                    .orderCode(orderCode)
                    .amount(price)
                    .description(description)
                    .returnUrl(returnUrl)
                    .cancelUrl(cancelUrl)
                    .item(item)
                    .build();

            // Tạo link thanh toán
            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            // Redirect tới link thanh toán
            String checkoutUrl = data.getCheckoutUrl();
            response.setHeader("Location", checkoutUrl);
            response.setStatus(302); // Redirect
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String url = scheme + "://" + serverName;
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            url += ":" + serverPort;
        }
        url += contextPath;
        return url;
    }
}
