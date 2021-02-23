package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.Correction;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.entity.Review;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.CorrectionService;
import edu.epam.swp.model.service.CreatureService;
import edu.epam.swp.model.service.ReviewService;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.service.impl.CorrectionServiceImpl;
import edu.epam.swp.model.service.impl.CreatureServiceImpl;
import edu.epam.swp.model.service.impl.ReviewServiceImpl;
import edu.epam.swp.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AdminPanelCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AdminPanelCommand.class);
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final ReviewService reviewService = ReviewServiceImpl.getInstance();
    private static final CreatureService creatureService = CreatureServiceImpl.getInstance();
    private static final CorrectionService correctionService = CorrectionServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        try {
            List<User> users = userService.findAll();
            List<Review> reviews = reviewService.findAll();
            List<Creature> creatures = creatureService.findAll();
            List<Creature> usersCreatures = creatureService.findUncheckedCreatures();
            List<Correction> usersCorrections = correctionService.findAll();
            request.setAttribute(AttributeName.USERS,users);
            request.setAttribute(AttributeName.REVIEWS,reviews);
            request.setAttribute(AttributeName.CREATURES,creatures);
            request.setAttribute(AttributeName.UNCHECKED_CREATURES,usersCreatures);
            request.setAttribute(AttributeName.CORRECTIONS,usersCorrections);
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        String page = PagePath.ADMIN_PANEL;
        return page;
    }
}
