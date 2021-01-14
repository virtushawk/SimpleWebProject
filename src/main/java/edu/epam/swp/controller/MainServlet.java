package edu.epam.swp.controller;

import edu.epam.swp.controller.command.Command;
import edu.epam.swp.controller.command.CommandType;
import edu.epam.swp.model.constant.ParameterConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "controller", urlPatterns = {"/controller","*.do"})
public class MainServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MainServlet.class);

    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String page = proceedRequest(request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String page = proceedRequest(request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request,response);
    }

    public String proceedRequest(HttpServletRequest request) {
        String commandType = request.getParameter(ParameterConstant.PARAMETER_COMMAND);
        Command command = CommandType.valueOf(commandType.toUpperCase(Locale.ROOT)).getCommand();
        String page = command.execute(request);
        return page;
    }

    public void destroy() {
    }
}