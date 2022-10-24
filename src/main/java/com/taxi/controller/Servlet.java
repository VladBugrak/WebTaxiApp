package com.taxi.controller;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.log4j.Logger;

@WebServlet(name = "servlet", value = "/servlet")
public class Servlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Servlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        LOGGER.info("Servlet init");
        servletConfig.getServletContext().setAttribute("loggedUsers", new ConcurrentHashMap<String, HttpSession>());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Servlet doGet");
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Servlet doPost");
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        LOGGER.debug("Servlet processRequest");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String page ;

        ActionFactory client = new ActionFactory();
        Command command = client.defineCommand(request);
        LOGGER.debug("Command ActionFactory page: "+command.getClass());
        page = command.execute(request, response);
        LOGGER.debug("Command return page: "+page);
        if (page != null) {
            if(page.contains("redirect")){
                response.sendRedirect(page.replace(":redirect", ""));
            }else {
                request.getRequestDispatcher(page).forward(request, response);
            }
        }


    }