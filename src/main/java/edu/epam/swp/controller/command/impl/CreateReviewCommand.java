package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.Review;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.UserStatus;
import edu.epam.swp.model.service.ReviewService;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.service.impl.ReviewServiceImpl;
import edu.epam.swp.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class CreateReviewCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CreateReviewCommand.class);
    private static final ReviewService service = ReviewServiceImpl.getInstance();
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String text = request.getParameter(ParameterName.REVIEW);
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        String ratingString = request.getParameter(ParameterName.RATING);
        int rating = Integer.parseInt(ratingString);
        long accountId = user.getAccountId();
        String id = request.getParameter(ParameterName.ID);
        long creatureId = Long.parseLong(id);
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        Review review = new Review.ReviewBuilder().withCreatureId(creatureId)
                .withAccountId(accountId).withText(text).withDate(date).withRating(rating).build();
        boolean flag;
        String page;
        try {
            flag = service.createReview(review);
            if (flag) {
                int numberReviews = user.getNumberReviews();
                if (numberReviews == 4) {
                    userService.changeUserStatus(accountId,UserStatus.ADVANCED);
                    user.setUserStatus(UserStatus.ADVANCED);
                } else if (numberReviews == 9) {
                    userService.changeUserStatus(accountId,UserStatus.REGULAR);
                    user.setUserStatus(UserStatus.REGULAR);
                }
                user.setNumberReviews(numberReviews + 1);
                request.getSession().setAttribute(AttributeName.REVIEW_MESSAGE_CREATED,true);
            } else {
                request.getSession().setAttribute(AttributeName.REVIEW_MESSAGE_ERROR,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while creating review",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        page = String.format(PagePath.SERVLET_CREATURE_ID,creatureId);
        return page;
    }
}
