package mblog.web.controller.desk.auth;

import mblog.base.data.Data;
import mblog.base.lang.Consts;
import mblog.base.utils.MailHelper;
import mblog.core.data.AccountProfile;
import mblog.core.data.User;
import mblog.core.persist.service.UserService;
import mblog.core.persist.service.VerifyService;
import mblog.web.controller.BaseController;
import mblog.web.controller.desk.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author langhsu on 2015/8/14.
 */
@Controller
@RequestMapping("/email")
public class EmailController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private VerifyService verifyService;
    @Autowired
    private MailHelper mailHelper;

    @RequestMapping(value = "/retry/{userId}", method = RequestMethod.GET)
    public String retry(@PathVariable("userId") Long userId, Integer type, ModelMap model) {
        Assert.notNull(userId, "缺少必要的参数");
        Assert.notNull(type, "缺少必要的参数");

        String template = Consts.EMAIL_TEMPLATE_BIND;
        String subject = "邮箱绑定验证";

        if (type == Consts.VERIFY_FORGOT) {
            template = Consts.EMAIL_TEMPLATE_FORGOT;
            subject = "找回密码";
        }

        User user = userService.get(userId);

        String code = verifyService.generateCode(user.getId(), type, user.getEmail());
        Map<String, Object> context = new HashMap<>();
        context.put("userId", user.getId());
        context.put("code", code);

        mailHelper.sendEmail(template, user.getEmail(), subject, context);

        Data data = Data.success("邮件发送成功", Data.NOOP);
        model.put("data", data);

        data.addLink("email/retry/" + userId + "?type=" + type, "没收到? 再来一发");

        return view(Views.REGISTER_RESULT);
    }

    @RequestMapping(value = "/bind/{userId}", method = RequestMethod.GET)
    public String bind(@PathVariable("userId") Long userId, Integer type, String code, ModelMap model) {
        Assert.notNull(userId, "缺少必要的参数");
        Data data;
        try {
            verifyService.verify(userId, Consts.VERIFY_BIND, code);
            AccountProfile p = userService.updateActiveEmail(userId, Consts.ACTIVE_EMAIL);

            putProfile(p);

            data = Data.success("恭喜您! 邮箱绑定成功。", Data.NOOP);

            data.addLink("index", "返回首页");
        } catch (Exception e) {
            data = Data.failure(e.getMessage());

            // data.addLink("email/retry/" + userId + "?type=" +type, "重发邮件");
        }

        model.put("type", type);
        model.put("userId", userId);
        model.put("data", data);
        return view(Views.REGISTER_RESULT);
    }

    @RequestMapping(value = "/forgot/{userId}", method = RequestMethod.GET)
    public String forgot(@PathVariable("userId") Long userId, Integer type, String code, ModelMap model) {
        Assert.notNull(userId, "缺少必要的参数");

        model.put("userId", userId);
        Data data;
        try {
            String token = verifyService.verify(userId, Consts.VERIFY_FORGOT, code);
            model.put("token", token);

            return view(Views.FORGOT_RESET);
        } catch (Exception e) {
            data = Data.failure(e.getMessage());

            // data.addLink("email/retry/" + userId + "?type=" +type, "重发邮件");
        }

        model.put("type", type);
        model.put("data", data);
        return view(Views.REGISTER_RESULT);
    }

}
