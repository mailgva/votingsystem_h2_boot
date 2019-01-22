package com.voting.web.interceptor;

import com.voting.web.SecurityUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ModelInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !modelAndView.isEmpty()) {
            modelAndView.getModelMap().addAttribute("localeMessages", createLocalMessages(request));
            modelAndView.getModelMap().addAttribute("request_uri", request.getRequestURL());
            modelAndView.getModelMap().addAttribute("date", LocalDate.now().plusDays((LocalDateTime.now().getHour() < 11 ? 0 : 1)).toString());
            try{
                modelAndView.getModelMap().addAttribute("isAdmin", SecurityUtil.isAdmin());
                modelAndView.getModelMap().addAttribute("userName", SecurityUtil.authUserName());
            } catch (Exception e) {}
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
