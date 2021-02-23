package edu.epam.swp.controller.filter;

import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "SessionLocaleFilter", urlPatterns = {"/*"})
public class SessionLocaleFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getParameter(ParameterName.LOCALE) != null) {
            req.getSession().setAttribute(AttributeName.LANGUAGE, req.getParameter(ParameterName.LOCALE));
        }
        chain.doFilter(request, response);
    }
    public void destroy() {}

    public void init(FilterConfig arg0) throws ServletException {}
}