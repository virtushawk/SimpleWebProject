package edu.epam.swp.tag;

import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;

public class AccessControlTag extends TagSupport {

    private String role;

    public void setAccessRole(String role) {
        this.role = role;
    }

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
