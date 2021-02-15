package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.entity.Review;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.CreatureService;
import edu.epam.swp.model.service.ReviewService;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.service.impl.CreatureServiceImpl;
import edu.epam.swp.model.service.impl.ReviewServiceImpl;
import edu.epam.swp.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class ProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ProfileCommand.class);
    private static final CreatureService creatureService = CreatureServiceImpl.getInstance();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final ReviewService reviewService = ReviewServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        Optional<User> user;
        List<Review> reviews;
        List<Creature> creatures;
        List<Creature> suggestedCreatures;
        try {
            user = userService.get(id);
            reviews = reviewService.findReviewsUser(id);
            creatures = creatureService.findUserCreatures(id);
            suggestedCreatures = creatureService.findUserSuggestedCreatures(id);
        } catch (ServiceException e) {
            logger.error("Error occurred while finding user",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE, true);
            return PagePath.HOME;
        }
        if (user.isPresent()) {
            request.setAttribute(AttributeName.USER,user.get());
            request.setAttribute(AttributeName.REVIEWS,reviews);
            request.setAttribute(AttributeName.CREATURES,creatures);
            request.setAttribute(AttributeName.UNCHECKED_CREATURES,suggestedCreatures);
            return PagePath.PROFILE;
        } else {
            return PagePath.ERROR;
        }
    }
}
