package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.constant.AttributeConstant;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute(AttributeConstant.ATTRIBUTE_AUTHORISED,false);
        request.getSession().removeAttribute(AttributeConstant.ATTRIBUTE_USERNAME);
        return PagePath.HOME;
    }
}
