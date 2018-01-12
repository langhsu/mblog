package mblog.shiro.tags;

import org.apache.shiro.subject.Subject;


/**
 * Displays body content if the current user has any of the roles specified.
 * <p>Equivalent to {@link org.apache.shiro.web.tags.HasAnyRolesTag}</p>
 *
 * @since 0.2
 */
public class HasAnyRolesTag extends RoleTag {
    // Delimeter that separates role names in tag attribute
    private static final String ROLE_NAMES_DELIMETER = ",";

    protected boolean showBody(String roleNames) {
        boolean hasAnyRole = false;
        Subject subject = getSubject();

        if (subject != null) {
            // Iterate through roles and check to see if the user has one of the roles
            for (String role : roleNames.split(ROLE_NAMES_DELIMETER)) {
                if (subject.hasRole(role.trim())) {
                    hasAnyRole = true;
                    break;
                }
            }
        }

        return hasAnyRole;
    }
}
