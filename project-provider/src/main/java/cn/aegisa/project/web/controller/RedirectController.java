package cn.aegisa.project.web.controller;

import cn.aegisa.project.model.ActivityInfo;
import cn.aegisa.project.model.CustomerInfo;
import cn.aegisa.project.model.JoinInfo;
import cn.aegisa.project.service.ActivityService;
import cn.aegisa.project.service.CustomerService;
import cn.aegisa.project.service.JoinService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/1/22 12:26
 */
@Slf4j
@Controller
@RequestMapping("/to")
public class RedirectController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private JoinService joinService;

    @RequestMapping("/main")
    public String toWelcomePage() {
        return "main/test";
    }

    @RequestMapping("/userAdd")
    public String toUserAdd(Model model) {
        model.addAttribute("category", "user");
        model.addAttribute("from", "userAdd");
        return "customer/add";
    }

    @RequestMapping("/userList")
    public String toUserList(Model model) {
        model.addAttribute("category", "user");
        model.addAttribute("from", "userList");
        return "customer/list";
    }

    @RequestMapping("/userEdit/{id}")
    public String toUserEdit(Model model, @PathVariable Integer id) {
        CustomerInfo customerInfo = customerService.getById(id);
        model.addAttribute("category", "user");
        model.addAttribute("from", "userEdit");
        model.addAttribute("noHead", "no");
        model.addAttribute("customer", customerInfo);
        log.info("传入要修改的人员：{}", JSON.toJSONString(customerInfo));
        return "customer/add";
    }

    @RequestMapping("/activityEdit/{id}")
    public String toActivityEdit(Model model, @PathVariable Integer id) {
        ActivityInfo activityInfo = activityService.getById(id);
        model.addAttribute("category", "activity");
        model.addAttribute("from", "activityEdit");
        model.addAttribute("noHead", "no");
        model.addAttribute("activity", activityInfo);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("formatter", formatter);
        log.info("传入要修改的活动：{}", JSON.toJSONString(activityInfo));
        return "activity/add";
    }

    @RequestMapping("/activityDetail/{id}")
    public String toActivityDetail(Model model, @PathVariable Integer id) {
        model.addAttribute("category", "activity");
        model.addAttribute("from", "activityDetail");
        ActivityInfo activityInfo = activityService.getById(id);
        Integer count = activityService.queryCustomerCount(id);
        model.addAttribute("count", count);
        model.addAttribute("activity", activityInfo);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("formatter", formatter);
        // 获取参加当前活动的人员信息
        String warn = activityService.getConflictSeat(id);
        model.addAttribute("warn", warn);
        return "activity/detail";
    }

    @RequestMapping("/activityAdd")
    public String toActivityAdd(Model model) {
        model.addAttribute("category", "activity");
        model.addAttribute("from", "activityAdd");
        return "activity/add";
    }

    @RequestMapping("/activityList")
    public String toActivityList(Model model) {
        model.addAttribute("category", "activity");
        model.addAttribute("from", "activityList");
        return "activity/list";
    }

    @RequestMapping("/join/customer/{id}")
    public String toCustomerJoin(@PathVariable Integer id, Model model) {
        CustomerInfo customerInfo = customerService.getById(id);
        List<ActivityInfo> activityInfoList = activityService.getListCustomerNotIn(id);
        model.addAttribute("customer", customerInfo);
        model.addAttribute("activities", activityInfoList);
        log.info(JSON.toJSONString(model));
        return "join/joinInfo";
    }

    @RequestMapping("/join/joinEdit/{id}")
    public String toJoinEdit(@PathVariable Integer id, Model model) {
        JoinInfo joinInfo = joinService.getById(id);
        Integer aid = joinInfo.getAid();
        Integer cid = joinInfo.getCid();
        ActivityInfo activityInfo = activityService.getById(aid);
        CustomerInfo customerInfo = customerService.getById(cid);
        model.addAttribute("customer", customerInfo);
        model.addAttribute("activity", activityInfo);
        model.addAttribute("join", joinInfo);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("formatter", formatter);
        return "join/joinInfo";
    }
}
