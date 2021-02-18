package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.service.CreatureService;
import edu.epam.swp.model.service.impl.CreatureServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteCreatureCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteCreatureCommand.class);
    private static final CreatureService service = CreatureServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        boolean flag;
        String page;
        try {
            if (user.getRole().equals(AccountRole.ADMIN)) {
                flag = service.delete(id);
                page = PagePath.SERVLET_ADMIN_PAGE;
            } else {
                long accountId = user.getId();
                flag = service.delete(accountId,id);
                page = PagePath.SERVLET_PROFILE + "&id=" + accountId;
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
            page = PagePath.HOME;
        }
        return page;
    }

}
