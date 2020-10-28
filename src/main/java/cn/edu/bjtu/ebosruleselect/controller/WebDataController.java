package cn.edu.bjtu.ebosruleselect.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController

public class WebDataController {
    public static String[] parameterName = new String[10];
    public static int [] threshold= new int [10];
    public static String[] symbol= new String[10];
    public static String[] operation= new String[10];
    public static String[] service= new String[10];
    public static String[] ruleName= new String[10];
    public static String[] device= new String[10];
    public static String[] scenario= new String[10];

    public static String[][] otherLogic;
    public static int [][] otherThreshold;
    public static String[][] otherSymbol;
    public static String[][] otherDevice;
    public static String[][] otherParameterName;

    @CrossOrigin
    @PostMapping("/webdata")
    public String Webdata(@RequestBody JSONObject info){
        int threshold = info.getIntValue("ruleParaThreshold");
        String name = info.getString("rulePara");
        String symbol = info.getString("ruleJudge");
        String operation = info.getString("ruleExecute");
        String service = info.getString("service");
        String ruleName = info.getString("ruleName");
        String device = info.getString("device");
        String scenario = info.getString("scenario");
        JSONArray otherRules = info.getJSONArray("otherRules");
        int otherRulesLen = otherRules.size();
        this.otherDevice = new String[10][otherRulesLen];
        this.otherSymbol = new String[10][otherRulesLen];
        this.otherThreshold = new int [10][otherRulesLen];
        this.otherLogic = new String[10][otherRulesLen];
        this.otherParameterName = new String[10][otherRulesLen];
        for (int i = 0; i<10; i++)
            if (this.parameterName[i] == null)
            {
                this.parameterName[i] = name;
                this.threshold[i] = threshold;
                this.symbol[i] = symbol;
                this.operation[i] = operation;
                this.service[i] = service;
                this.ruleName[i] = ruleName;
                this.device[i] = device;
                this.scenario[i] = scenario;
                for(int j=0; j<otherRulesLen; j++){
                    this.otherDevice [i][j] = otherRules.getJSONObject(j).getString("device");
                    this.otherSymbol [i][j] = otherRules.getJSONObject(j).getString("ruleJudge");
                    this.otherThreshold [i][j] = otherRules.getJSONObject(j).getIntValue("ruleParaThreshold");
                    this.otherLogic [i][j] = otherRules.getJSONObject(j).getString("logic");
                    this.otherParameterName [i][j] = otherRules.getJSONObject(j).getString("parameter");
                }
                break;
            }
        return "收到前端表单数据";
    }
}