package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ImageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PagePath.IMAGE;
    }
}
