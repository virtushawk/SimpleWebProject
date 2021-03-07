package edu.epam.swp.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter prohibits caching pages.
 * @author romab
 */
@WebFilter(filterName = "CacheFilter", urlPatterns = {"/*"})
public class CacheFilter implements Filter {

    /**
     * Prohibits caching pages by setting response header.
     * @param servletRequest object
     * @param servletResponse object
     * @param filterChain object
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * This class is the general class of exceptions produced by failed or interrupted I/O operations.
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Cache-control","no-cache,no-store,must-revalidate");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires",0);
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
