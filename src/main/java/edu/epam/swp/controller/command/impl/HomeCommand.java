package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class HomeCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = PagePath.HOME;
        return page;
    }
}
