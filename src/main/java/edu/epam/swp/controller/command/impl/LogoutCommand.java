package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to logout.
 * @author romab
 */
public class LogoutCommand implements Command {

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return PagePath.SERVLET_HOME;
    }
}
