package edu.epam.swp.tag;

import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * AccessControlTag is used to check user's roles.
 * @author romab
 */
public class AccessControlTag extends TagSupport {

    private String role;

    /**
     * Sets access role.
     * @param role the role
     */
    public void setAccessRole(String role) {
        this.role = role;
    }

    /**
     * Checks user's role and skips tag's body if role is wrong.
     * @return 1 for EVAL_BODY_INCLUDE ro 0 for SKIP_BODY.
     */
    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute(AttributeName.USER);
        if (user != null) {
            AccountRole accountRole = user.getRole();
            if (AccountRole.valueOf(role) == accountRole) {
                return EVAL_BODY_INCLUDE;
            }
        }
        return SKIP_BODY;
    }

}
