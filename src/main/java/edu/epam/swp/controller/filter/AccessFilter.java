package edu.epam.swp.controller.filter;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.CommandType;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "accessFilter",urlPatterns = {"/controller"})
public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String commandType = request.getParameter(ParameterName.COMMAND);
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        if (user == null) {
            user = new User.UserBuilder().withRole(AccountRole.GUEST).build();
            request.getSession().setAttribute(AttributeName.USER,user);
        }
        AccountRole accountRole = user.getRole();
        try {
            CommandType command = CommandType.valueOf(commandType.toUpperCase());
            boolean flag = AccessMap.COMMAND_MAP.get(accountRole).contains(command);
            if (flag) {
                filterChain.doFilter(servletRequest,servletResponse);
            } else {
                response.sendRedirect(request.getContextPath() + PagePath.ERROR_403);
            }
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + PagePath.ERROR);
        }
    }

    @Override
    public void destroy() {

    }
}
