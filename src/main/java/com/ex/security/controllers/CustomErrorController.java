package com.ex.security.controllers;

import com.ex.exceptions.ServiceException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * User: animra
 * Date: 7/23/2023
 * Time: 10:51 AM
 **/
@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            switch (statusCode) {
                case 400:
                    return "views/common/errorPage400";
                case 401:
                    return "views/common/errorPage401";
                case 403:
                    return "views/common/errorPage403";
                case 404:
                    return "views/common/errorPage404";
                case 405:
                    return "views/common/errorPage405";
                case 500:
                    return "views/common/errorPage500";
                case 503:
                    return "views/common/errorPage503";
                default:
                    throw new ServiceException("حدث خطأ يرجى مراجعة مسؤول النظام");
            }
        }
        return "views/common/errorPage403";
    }
}
