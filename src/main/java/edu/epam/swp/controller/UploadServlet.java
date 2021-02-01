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

@WebServlet(name = "uploadController", urlPatterns = "/uploadController")
public class UploadServlet extends HttpServlet {

    //todo fix and clean
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getParameter("url");
        response.setContentType("image/jpeg");
        ServletOutputStream out;
        out = response.getOutputStream();
        FileInputStream flinp = new FileInputStream(url);
        BufferedInputStream buffinp = new BufferedInputStream(flinp);
        BufferedOutputStream buffoup = new BufferedOutputStream(out);
        int ch=0;
        while ((ch=buffinp.read()) != -1) {

            buffoup.write(ch);

        }
        buffinp.close();
        flinp.close();
        buffoup.close();
        out.close();
    }

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
