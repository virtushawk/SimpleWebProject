package edu.epam.swp.controller;

import edu.epam.swp.controller.command.Command;
import edu.epam.swp.controller.command.CommandType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Servlet is used to show user's images.
 * @author romab
 */
@WebServlet(name = "uploadController", urlPatterns = "/uploadController")
public class  UploadServlet extends HttpServlet {

    private static final String CONTENT_TYPE = "image/jpeg";

    /**
     * Gets path to image from request and displays image.
     * @param request HttpServletRequest object.
     * @param response HttpServletResponse object.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getParameter(ParameterName.URL);
        response.setContentType(CONTENT_TYPE);
        try (ServletOutputStream out = response.getOutputStream();
             FileInputStream fileInputStream = new FileInputStream(url);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out)) {
            int ch;
            while ((ch = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(ch);
            }
        }
    }
    //todo : Проверить на использование и убрать
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        proceedRequest(request,response);
    }

    private void proceedRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String commandType = request.getParameter(ParameterName.COMMAND);
        Command command = CommandType.valueOf(commandType.toUpperCase()).getCommand();
        String page = command.execute(request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request,response);
    }
}
