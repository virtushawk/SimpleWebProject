package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.constant.AttributeConstant;
import edu.epam.swp.model.constant.ParameterConstant;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static final UserService service = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String password = request.getParameter(ParameterConstant.PARAMETER_PASSWORD);
        String email = request.getParameter(ParameterConstant.PARAMETER_EMAIL);
        logger.info("password : {}, email : {}",password,email);
        Optional<User> optional = service.findUser(email,password);
        if (optional.isPresent()) {
            User user = optional.get();
            String username = user.getUsername();
            request.getSession().setAttribute(AttributeConstant.ATTRIBUTE_AUTHORISED,true);
            request.getSession().setAttribute(AttributeConstant.ATTRIBUTE_USERNAME,username);
            return PagePath.HOME;
        } else {
            request.setAttribute(AttributeConstant.ATTRIBUTE_ERROR_MESSAGE, true);
            return PagePath.LOGIN;
        }
    }
}
