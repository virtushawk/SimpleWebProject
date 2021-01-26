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
        proceedRequest(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        proceedRequest(request,response);
    }

    private void proceedRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandType = request.getParameter(ParameterName.PARAMETER_COMMAND);
        Command command = CommandType.valueOf(commandType.toUpperCase()).getCommand();
        String page = command.execute(request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request,response);
    }

    @Override
    public void destroy() {
        pool.destroyPool();
    }
}