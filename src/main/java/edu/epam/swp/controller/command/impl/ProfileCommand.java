package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
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
import java.util.Optional;

/**
 * Command to open profile page.
 * @author romab
 */
public class ProfileCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ProfileCommand.class);
    private static final CreatureService creatureService = CreatureServiceImpl.getInstance();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final ReviewService reviewService = ReviewServiceImpl.getInstance();
    private static final CorrectionService correctionService = CorrectionServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        Optional<User> user;
        String page;
        try {
            user = userService.findUser(id);
            if (user.isPresent()) {
                request.setAttribute(AttributeName.USER,user.get());
                List<Review> reviews = reviewService.findUserReviews(id);
                List<Creature> creatures = creatureService.findUserCreatures(id);
                request.setAttribute(AttributeName.REVIEWS,reviews);
                request.setAttribute(AttributeName.CREATURES,creatures);
                User sessionUser = (User) request.getSession().getAttribute(AttributeName.USER);
                long accountId = sessionUser.getAccountId();
                if (id == accountId) {
                    List<Creature> suggestedCreatures = creatureService.findUserSuggestedCreatures(id);
                    List<Correction> corrections = correctionService.findUserCorrections(id);
                    request.setAttribute(AttributeName.UNCHECKED_CREATURES,suggestedCreatures);
                    request.setAttribute(AttributeName.CORRECTIONS,corrections);
                }
                page = PagePath.PROFILE;
            } else {
                page = PagePath.ERROR;
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while requesting database",e);
            request.setAttribute(AttributeName.DATABASE_ERROR_MESSAGE, true);
            page = PagePath.HOME;
        }
        return page;
    }
}
