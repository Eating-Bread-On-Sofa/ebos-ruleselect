package cn.edu.bjtu.ebosruleselect.controller;

import cn.edu.bjtu.ebosruleselect.dao.RuleRepository;
import cn.edu.bjtu.ebosruleselect.entity.RuleSelect;
import cn.edu.bjtu.ebosruleselect.service.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.client.RestTemplate;

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
    @Autowired
    RestTemplate restTemplate;

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
        postController.sendPostRequest("http://" + ip +":8083/api/rule", info);
        postController.sendPostRequest("http://" + ip +":8083/api/ruleDelete", info);
        restTemplate.getForObject("http://" + ip +":8083/api/ruleAlert",JSONObject.class);
        logService.info("create","用户添加规则");
        return "成功收到前端添加规则";
    }

    @CrossOrigin
    @PostMapping("/ruleSave")
    public Boolean addRule(@RequestBody RuleSelect rule) {
        System.out.println(rule);
        if (rule != null) {
            for (int i = 0; i < ruleService.findAllRule().size(); i++) {
                if (rule.getRuleName() == ruleService.findAllRule().get(i).getRuleName()) {
                    logService.error("create","规则名称重复");
                    return false;
                }
            }
            if (ruleService.addRule(rule)) {
                logService.info("create","保存新规则"+rule.getRulePara()+rule.getRuleJudge()+rule.getRuleParaThreshold());
                return true;
            }
        }
        logService.error("create","保存新规则失败");
        return false;
    }

    @CrossOrigin
    @PostMapping("/ruleDelete")
    //清空内存中的数据
    public void ruleDelete(@RequestBody JSONObject info)
    {
        String ruleName = info.getString("ruleName");
        for (int i = 0; i<10; i++) {
            if((WebDataController.ruleName[i])!=null) {
                if ((WebDataController.ruleName[i]).equals(ruleName)) {
                    WebDataController.parameterName[i] = null;
                    WebDataController.threshold[i] = 0;
                    WebDataController.symbol[i] = null;
                    WebDataController.operation[i] = null;
                    WebDataController.service[i] = null;
                    break;
                }
            }
            else
                continue;
        }
        logService.info("delete","成功删除规则:"+ruleName);
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
        this.loadRule();
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

    public void loadRule(){
        for (int i = 0; i < ruleService.findAllRule().size(); i++){
            String ip = ruleService.findAllRule().get(i).getGateway();
            JSONObject j = new JSONObject();
            try {
                postController.sendPostRequest("http://" + ip +":8083/api/ruleLoad",j);
            } catch (Exception e){
                System.out.println("---------------------异常："+e);
            }

        }
    }
}
