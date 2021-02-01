package edu.epam.swp.controller;

import edu.epam.swp.controller.command.Command;
import edu.epam.swp.controller.command.CommandType;
import edu.epam.swp.model.pool.ConnectionPool;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "controller", urlPatterns = {"/controller","*.do"})
public class MainServlet extends HttpServlet {
    private static final ConnectionPool pool = ConnectionPool.INSTANCE;

    @Override
    public void init() {}

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String page = proceedRequest(request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String page = proceedRequest(request);
        response.sendRedirect(request.getContextPath() + page);
    }

    private String proceedRequest(HttpServletRequest request) {
        String commandType = request.getParameter(ParameterName.COMMAND);
        Command command = CommandType.valueOf(commandType.toUpperCase()).getCommand();
        String page = command.execute(request);
        return page;
    }

    @Override
    public void destroy() {
        super.destroy();
        pool.destroyPool();
    }
}