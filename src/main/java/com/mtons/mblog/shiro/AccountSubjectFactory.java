package com.mtons.mblog.shiro;

import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * A {@code SubjectFactory} implementation that creates {@link WebDelegatingSubject} instances.
 */
@Slf4j
public class AccountSubjectFactory extends DefaultSubjectFactory {
    @Autowired
    private UserService userService;

    @Override
    public Subject createSubject(SubjectContext context) {
        if (!(context instanceof WebSubjectContext)) {
            return super.createSubject(context);
        } else {
            WebSubjectContext wsc = (WebSubjectContext)context;
            SecurityManager securityManager = wsc.resolveSecurityManager();
            Session session = wsc.resolveSession();
            boolean sessionEnabled = wsc.isSessionCreationEnabled();
            PrincipalCollection principals = wsc.resolvePrincipals();
            boolean authenticated = wsc.resolveAuthenticated();
            String host = wsc.resolveHost();
            ServletRequest request = wsc.resolveServletRequest();
            ServletResponse response = wsc.resolveServletResponse();

            Subject subject =  new WebDelegatingSubject(principals, authenticated, host, session, sessionEnabled, request, response, securityManager);
            handlerSession(subject);
            return subject;
        }
    }

    private void handlerSession(Subject subject) {
        Session session = subject.getSession(true);
        if ((subject.isAuthenticated() || subject.isRemembered()) && session.getAttribute("profile") == null) {
            AccountProfile profile = (AccountProfile) subject.getPrincipal();
            log.debug("reload session - " + profile.getUsername());
            session.setAttribute("profile", userService.findProfile(profile.getId()));
        }
    }

}
