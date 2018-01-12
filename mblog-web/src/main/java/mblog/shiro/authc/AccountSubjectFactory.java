/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.shiro.authc;

import mblog.core.persist.service.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.subject.WebSubjectContext;

import mblog.base.lang.Consts;
import mblog.core.data.AccountProfile;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountSubjectFactory implements SubjectFactory {

    @Autowired
    private UserService userService;

    @Override
    public Subject createSubject(SubjectContext context) {
        WebSubjectContext wsc = (WebSubjectContext) context;
        AuthenticationInfo info = wsc.getAuthenticationInfo();

        AccountProfile profile = null;
        AccountSubject subject = null;
        if (info instanceof AccountAuthenticationInfo) {
        	profile = ((AccountAuthenticationInfo) info).getProfile();
            subject = doCreate(wsc, profile);
            subject.getSession(true).setAttribute("profile", profile);
        }else{
            Session session = wsc.getSession();
            if(session != null){
            	profile = (AccountProfile)session.getAttribute("profile");
            }
            subject = doCreate(wsc, profile);
            boolean isRemembered = subject.isRemembered();
            if (session == null) {
                wsc.setSessionCreationEnabled(true);
                subject.getSession(true);
            }
            if (isRemembered && profile == null) {
                Object username = subject.getPrincipal();
                profile = userService.getProfileByName((String) username);
                
                subject.getSession(true).setTimeout(30 * 60 * Consts.TIME_MIN);
                subject.getSession(true).setAttribute("profile", profile);
            }
        }
        

        return doCreate(wsc, profile);
    }

    private AccountSubject doCreate(WebSubjectContext wsc, AccountProfile profile) {
        return new AccountSubject(wsc.resolvePrincipals(), wsc.resolveAuthenticated(), wsc.resolveHost(),
                wsc.resolveSession(), wsc.isSessionCreationEnabled(), wsc.resolveServletRequest(),
                wsc.resolveServletResponse(), wsc.resolveSecurityManager(), profile);
    }
}
