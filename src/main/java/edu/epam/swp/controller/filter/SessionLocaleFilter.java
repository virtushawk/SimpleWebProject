package edu.epam.swp.controller.filter;

import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter controls client's locale.
 * @author romab
 */
@WebFilter(filterName = "SessionLocaleFilter", urlPatterns = {"/*"})
public class SessionLocaleFilter implements Filter {

    /**
     * checks for request parameter and if its not null set's value to session attribute.
     * @param servletRequest ServletRequest
     * @param servletResponse object
     * @param filterChain object
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * This class is the general class of exceptions produced by failed or interrupted I/O operations.
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getParameter(ParameterName.LOCALE) != null) {
            req.getSession().setAttribute(AttributeName.LANGUAGE,req.getParameter(ParameterName.LOCALE));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}