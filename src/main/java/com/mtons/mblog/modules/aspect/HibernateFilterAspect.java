package com.mtons.mblog.modules.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Mark Han
 */
@Aspect
@Component
public class HibernateFilterAspect {

    private static final String FILTER_NAME = "POST_STATUS_FILTER";

    @PersistenceContext
    protected EntityManager em;

    @Pointcut("@annotation(com.mtons.mblog.modules.aspect.PostStatusFilter)")
    public void filter() {
    }

    @Before("filter()")
    public void doBefore(JoinPoint joinPoint) {
        Session mfSession = (Session) em.getDelegate();
        if (mfSession.isOpen()) {
            mfSession.enableFilter(FILTER_NAME).validate();
        }
    }
}
