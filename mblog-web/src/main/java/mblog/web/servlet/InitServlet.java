package mblog.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import mtons.modules.utils.Exceptions;
import mtons.modules.utils.GMagickUtils;

import org.apache.log4j.Logger;

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
    	System.out.println(" --- 系统参数初始化... --- ");
    	Properties properties = new Properties();
    	InputStream inStream = null;
    	 // 初始化配置文件
        inStream = InitServlet.class.getResourceAsStream("/mtons.properties");
        try {
			properties.load(inStream);
			
			String gmHome = (String) properties.get(GMagickUtils.GMAGICK_HOME);
			System.setProperty(GMagickUtils.GMAGICK_HOME, gmHome);
		} catch (IOException e) {
			log.error("System initialize failure:" + Exceptions.getStackTraceAsString(e));
            System.exit(0);
		}
        System.out.println(" --- 系统参数初始化结束 --- ");
    }
}
