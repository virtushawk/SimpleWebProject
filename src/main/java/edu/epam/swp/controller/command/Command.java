package edu.epam.swp.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface Command.
 * @author romab
 */
public interface Command {

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    String execute(HttpServletRequest request);
}
