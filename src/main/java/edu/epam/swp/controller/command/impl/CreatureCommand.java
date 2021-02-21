package edu.epam.swp.controller.command.impl;
import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.entity.Review;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.service.CreatureService;
import edu.epam.swp.model.service.ReviewService;
import edu.epam.swp.model.service.impl.CreatureServiceImpl;
import edu.epam.swp.model.service.impl.ReviewServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class CreatureCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreatureCommand.class);
    private static final CreatureService creatureService = CreatureServiceImpl.getInstance();
    private static final ReviewService reviewService = ReviewServiceImpl.getInstance();

    //todo : FIX ERROR PAGE?
    @Override
    public String execute(HttpServletRequest request) {
        String creatureIdParameter = request.getParameter(ParameterName.ID);
        long creatureId = Long.parseLong(creatureIdParameter);
        Optional<Creature> creature;
        String page;
        try {
            creature = creatureService.get(creatureId);
            if (creature.isPresent()) {
                User user = (User) request.getSession().getAttribute(AttributeName.USER);
                long accountId = user.getAccountId();
                List<Review> reviews = reviewService.findCreatureReviews(creatureId);
                Optional<Review> userReview = reviewService.findUserReview(accountId,creatureId);
                request.setAttribute(AttributeName.CREATURE,creature.get());
                request.setAttribute(AttributeName.REVIEWS,reviews);
                userReview.ifPresent(review -> request.setAttribute(AttributeName.USER_REVIEW,review));
                page = PagePath.CREATURE;
            } else {
                page = PagePath.ERROR;
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while connecting to database",e);
            request.setAttribute(AttributeName.DATABASE_ERROR_MESSAGE, true);
            page = PagePath.HOME;
        }
        return page;
    }
}
