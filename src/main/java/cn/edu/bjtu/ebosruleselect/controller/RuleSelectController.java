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
    @GetMapping("/getRuleLists")
    public JSONArray getRule(){
        JSONArray ja = new JSONArray();
        for (int i = 0; i < ruleService.findAllRule().size(); i++) {
            JSONObject j=new JSONObject();
            j.put("ruleName",ruleService.findAllRule().get(i).getRuleName());
            j.put("parameter",ruleService.findAllRule().get(i).getRulePara());
            j.put("ruleJudge",ruleService.findAllRule().get(i).getRuleJudge());
            j.put("threshold",ruleService.findAllRule().get(i).getRuleParaThreshold());
            j.put("ruleExecute",ruleService.findAllRule().get(i).getRuleExecute());
            j.put("ruleId",ruleService.findAllRule().get(i).getRuleId());
            j.put("service",ruleService.findAllRule().get(i).getService());
            j.put("device",ruleService.findAllRule().get(i).getDevice());
            j.put("scenario",ruleService.findAllRule().get(i).getScenario());
            j.put("gateway",ruleService.findAllRule().get(i).getGateway());
            j.put("otherRules",ruleService.findAllRule().get(i).getOtherRules());
            ja.add(j);
        }
//        this.ja=ja;
//        this.loadRule();
//        logService.info("retrieve","用户接收规则列表");
        return ja;
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
