package mblog.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import mtons.commons.utils.Exceptions;

/**
 * 系统初始化
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Logger log = Logger.getLogger(getClass());
    
    public InitServlet() {
        super();
    }
    
    /**
     * Init
     */
    @Override
    public void init() throws ServletException {
    	Properties properties = new Properties();
    	InputStream inStream = null;
    	 // 初始化配置文件
        inStream = InitServlet.class.getResourceAsStream("/start.properties");
        try {
			properties.load(inStream);
		} catch (IOException e) {
			log.error("System initialize failure:" + Exceptions.getStackTraceAsString(e));
            System.exit(0);
		}
    }
}
