package com.mtons.mblog.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author landy
 */
@Slf4j
public class RequestCostFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		chain.doFilter(request, response);
		stopWatch.stop();

		log.debug("{} -> request code - {}",
                httpRequest.getRequestURI(),
                stopWatch.getTotalTimeMillis());
    }

    @Override
    public void destroy() {

    }
}
