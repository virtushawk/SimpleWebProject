package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.constant.ParameterConstant;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

public class RegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);
    private static final UserService service = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String username = new String(request.getParameter(ParameterConstant.PARAMETER_USERNAME).getBytes(StandardCharsets.ISO_8859_1)
                ,StandardCharsets.UTF_8);
        String password = request.getParameter(ParameterConstant.PARAMETER_PASSWORD);
        String email = request.getParameter(ParameterConstant.PARAMETER_EMAIL);
        service.registerUser(email,username,password);
        return PagePath.HOME;
    }
}
