/**
 * 
 */
package mblog.web.exceptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mtons.modules.pojos.Data;

import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

/**
 * 异常处理
 * @author langhsu
 *
 */
public class DefaultExceptionHandler implements HandlerExceptionResolver {
	private Logger log = Logger.getLogger(getClass());
	
	private String errorView = "/error";
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		if (log.isDebugEnabled()) {
			log.error("Catch Exception: ", ex);
		} else {
			log.error(ex.getMessage());
		}
		
		ModelAndView view = null;
		String ret = ex.getMessage();
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), ResponseBody.class);  
		if (responseBodyAnn != null) {
			try {
				response.setContentType("application/json;charset=UTF-8");
				Gson gson = new Gson();
				response.getWriter().print(gson.toJson(Data.failure(ret)));
			} catch (IOException e) {
			}
			
			view = new ModelAndView();
		} else {
			Map<String, Object> map = new HashMap<String, Object>();  
			map.put("error", ret);
	        map.put("base", request.getContextPath());
			view = new ModelAndView(errorView, map);
		}
		return view;
	}
	
}
