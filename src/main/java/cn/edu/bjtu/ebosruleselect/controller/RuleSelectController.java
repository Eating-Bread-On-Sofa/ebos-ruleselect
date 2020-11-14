package cn.edu.bjtu.ebosruleselect.controller;

import cn.edu.bjtu.ebosruleselect.dao.RuleRepository;
import cn.edu.bjtu.ebosruleselect.service.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;

@RequestMapping("/api")
@RestController
public class RuleSelectController {
    @Autowired
    RuleService ruleService;
    @Autowired
    RuleRepository ruleRepository;
    @Autowired
    LogService logService;

    public static JSONObject alertJson=new JSONObject();
    public static ArrayList al = new ArrayList();

    @CrossOrigin
    @PostMapping("/ruleReceive")
    public String ruleReceive(@RequestBody JSONObject info) {
        System.out.println(info);
        String ip = info.getString("gateway");
        System.out.println(ip);
        postController.sendPostRequest("http://" + ip +":8083/api/ruleReceive", info);
        postController.sendPostRequest("http://" + ip +":8083/api/ruleCreate", info);
        logService.info("create","用户添加规则");
        return "成功收到前端添加规则";
    }

    @CrossOrigin
    @GetMapping("/ruleAlert")
    public JSONObject getRuleAlert(){
        JSONObject j=new JSONObject();
        j.put("alertList", al);
        System.out.println("ruleAlert拉取的告警信息—++++++++++++++++++++++++++" + j);
        return j;
    }

    @CrossOrigin
    @PostMapping("/xinjiekou")
    public void getRuleAlert(JSONObject j){
        for (int i=0; i<j.size(); i++){
            JSONArray jjj;
            jjj = j.getJSONArray("alertList");
            for(int index=0; index<jjj.size(); index++){
                al.add(jjj);
            }
        }
    }

    @ApiOperation(value = "微服务健康检查")
    @CrossOrigin
    @GetMapping("/ping")
    public String ping(){
//        logService.info("retrieve","对网关管理进行了一次健康检测");
        return "pong";
    }
}
