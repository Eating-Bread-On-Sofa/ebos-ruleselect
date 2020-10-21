package cn.edu.bjtu.ebosruleselect.controller;

import cn.edu.bjtu.ebosruleselect.dao.RuleRepository;
import cn.edu.bjtu.ebosruleselect.service.RuleService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class RuleSelectController {
    @Autowired
    RuleService ruleService;
    @Autowired
    RuleRepository ruleRepository;

    @CrossOrigin
    @PostMapping("/ruleReceive")
    public String ruleReceive(@RequestBody JSONObject info) {
        System.out.println(info);
        String ip = info.getString("gateway");
        System.out.println(ip);
        postController.sendPostRequest("http://" + ip +"/api/ruleReceive", info);
        postController.sendPostRequest("http://" + ip +"/api/ruleCreate", info);
        return "成功收到前端添加规则";
    }
}
