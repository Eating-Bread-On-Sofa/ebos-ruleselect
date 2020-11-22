package cn.edu.bjtu.ebosruleselect.controller;

import cn.edu.bjtu.ebosruleselect.dao.RuleRepository;
import cn.edu.bjtu.ebosruleselect.service.*;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/api")
@RestController
public class RuleSelectController {
    @Autowired
    RuleService ruleService;
    @Autowired
    RuleRepository ruleRepository;
    @Autowired
    LogService logService;

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

    @ApiOperation(value = "微服务健康检查")
    @CrossOrigin
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
