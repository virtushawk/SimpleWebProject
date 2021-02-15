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

    //todo : check and clean
    @Override
    public String execute(HttpServletRequest request) {
        long creatureId = Long.parseLong(request.getParameter(ParameterName.ID));
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long accountId = user.getId();
        Optional<Creature> creature;
        List<Review> reviews;
        Optional<Review> userReview;
        try {
            creature = creatureService.get(creatureId);
            reviews = reviewService.findReviewsCreature(creatureId);
            userReview = reviewService.findUserReview(accountId,creatureId);
        } catch (ServiceException e) {
            logger.error("Error occurred while finding creature",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE, true);
            return PagePath.HOME;
        }
        if (creature.isPresent()) {
            request.setAttribute(AttributeName.CREATURE,creature.get());
            request.setAttribute(AttributeName.REVIEWS,reviews);
            userReview.ifPresent(review -> request.setAttribute(AttributeName.USER_REVIEW,review));
            return PagePath.CREATURE;
        } else {
            return PagePath.ERROR;
        }
    }
}
