package edu.epam.swp.controller;

public class PagePath {

    private PagePath() {}

    public static final String HOME = "/pages/home.jsp";
    public static final String LOGIN = "/pages/login.jsp";
    public static final String ERROR = "/pages/error_404.jsp";
    public static final String REGISTER = "/pages/register.jsp";
    public static final String SERVLET_HOME = "/controller?command=home";
    public static final String CATALOG = "/pages/catalog.jsp";
    public static final String CREATE_CREATURE = "/pages/createCreature.jsp";
    public static final String SERVLET_CATALOG = "/controller?command=catalog";
    public static final String CREATURE = "/pages/creature.jsp";
    public static final String PROFILE = "/pages/profile.jsp";
    public static final String SERVLET_PROFILE = "/controller?command=profile";
    public static final String ADMIN_PANEL = "/pages/adminPanel.jsp";
    public static final String SERVLET_ADMIN_PAGE = "/controller?command=admin_panel";
    public static final String ERROR_403 = "/pages/error_403.jsp";
    public static final String SERVLET_CREATURE = "/controller?command=creature";
}
