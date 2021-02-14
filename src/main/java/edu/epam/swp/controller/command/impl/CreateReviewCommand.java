package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.Review;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.ReviewService;
import edu.epam.swp.model.service.impl.ReviewServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class CreateReviewCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateReviewCommand.class);
    private static final ReviewService service = ReviewServiceImpl.getInstance();

    //todo : fix and clean
    @Override
    public String execute(HttpServletRequest request) {
        String text = request.getParameter(ParameterName.REVIEW);
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        String ratingString = request.getParameter(ParameterName.RATING);
        int rating = Integer.parseInt(ratingString);
        long accountId = user.getId();
        String id = request.getParameter(ParameterName.ID);
        long creatureId = Long.parseLong(id);
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        Review review = new Review(accountId,creatureId,text,date,rating);
        boolean flag;
        try {
            flag = service.createReview(review);
        } catch (ServiceException e) {
            logger.error("Error occurred while creating review",e);
            request.getSession().setAttribute(AttributeName.GENERAL_ERROR_MESSAGE,true);
        }
        return PagePath.SERVLET_HOME;
    }
}
