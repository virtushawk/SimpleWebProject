package edu.epam.swp.controller;

import edu.epam.swp.controller.command.Command;
import edu.epam.swp.controller.command.CommandType;
import edu.epam.swp.model.pool.ConnectionPool;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * Main servlet of the application
 * @author romab
 */
@WebServlet(name = "controller", urlPatterns = {"/controller"})
public class MainServlet extends HttpServlet {

    private static final ConnectionPool pool = ConnectionPool.INSTANCE;

    /**
     * Processes doGet request and forwards to page specified by command.
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * This class is the general class of exceptions produced by failed or interrupted I/O operations.
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String page = proceedRequest(request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request,response);
    }

    /**
     * Processes doPost request and redirects to page specified by command.
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String page = proceedRequest(request);
        response.sendRedirect(request.getContextPath() + page);
    }

    /**
     * Processes requests and returns page specified by command.
     * @param request HttpServletRequest object.
     * @return String page specified by command.
     */
    private String proceedRequest(HttpServletRequest request) {
        String commandType = request.getParameter(ParameterName.COMMAND);
        Command command = CommandType.valueOf(commandType.toUpperCase()).getCommand();
        String page = command.execute(request);
        return page;
    }


    /**
     * Called before servlet destruction.
     */
    @Override
    public void destroy() {
        super.destroy();
        pool.destroyPool();
    }
}