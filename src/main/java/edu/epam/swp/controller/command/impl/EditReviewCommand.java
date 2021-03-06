package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.Review;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.service.ReviewService;
import edu.epam.swp.model.service.impl.ReviewServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * Command to edit review
 */
public class EditReviewCommand implements Command {

    private static final Logger logger = LogManager.getLogger(EditReviewCommand.class);
    private static final ReviewService service = ReviewServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String text = request.getParameter(ParameterName.REVIEW);
        int rating = Integer.parseInt(request.getParameter(ParameterName.RATING));
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        Review review = new Review.ReviewBuilder().withReviewId(id).
                withText(text).withDate(date).withRating(rating).build();
        boolean flag;
        String page;
        if (user.getRole().equals(AccountRole.ADMIN)) {
            try {
                flag = service.editReview(review);
                if (flag) {
                    request.getSession().setAttribute(AttributeName.REVIEW_CHANGE_VALID,true);
                } else {
                    request.getSession().setAttribute(AttributeName.REVIEW_CHANGE_ERROR,true);
                }
            } catch (ServiceException e) {
                logger.error("Error occurred while accessing database",e);
                request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
            }
            page = PagePath.SERVLET_ADMIN_PAGE;
        } else {
            try {
                long accountId = user.getAccountId();
                flag = service.editReview(accountId,review);
                if (flag) {
                    request.getSession().setAttribute(AttributeName.REVIEW_CHANGE_VALID,true);
                } else {
                    request.getSession().setAttribute(AttributeName.REVIEW_CHANGE_ERROR,true);
                }
            } catch (ServiceException e) {
                logger.error("Error occurred while accessing database",e);
                request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
            }
            long creatureId = Long.parseLong(request.getParameter(ParameterName.CREATURE));
            page = String.format(PagePath.SERVLET_CREATURE_ID,creatureId);
        }
        return page;
    }
}
