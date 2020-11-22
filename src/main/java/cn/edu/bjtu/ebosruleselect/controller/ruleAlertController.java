package cn.edu.bjtu.ebosruleselect.controller;

import cn.edu.bjtu.ebosruleselect.service.MqFactory;
import cn.edu.bjtu.ebosruleselect.service.MqProducer;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/api")
@RestController
public class ruleAlertController {
    @Autowired
    MqFactory mqFactory;

    public static String[] arrMsg = {"","","","","","","","","",""};
    public static Boolean[] arrFlag = {false,false,false,false,false,false,false,false,false,false};
    public static ArrayList<String> al = new ArrayList<String>();

    @CrossOrigin
    @GetMapping("/ruleAlert1")
    public JSONObject getRuleAlert(){
        JSONObject j=new JSONObject();
//        ArrayList<String> al = new ArrayList<String>();
//        for(int i=0; i<10; i++){
//            if(arrFlag[i]){
//                al.add(arrMsg[i]);
//            }
//        }
        j.put("alertList", al);
        System.out.println("ruleAlert拉取的告警信息—++++++++++++++++++++++++++" + j);
        al.clear();
        return j;
    }

    @CrossOrigin
    @PostMapping("/getMessage")
    public JSONObject getMessage(@RequestBody JSONObject info){
        MqProducer mqProducer = mqFactory.createProducer();
        System.out.println("------------------------从mq收到告警信息" + info);
        al.add(info.getString("message"));
        mqProducer.publish("notice",info.toString());
        System.out.println(al);
        return info;
    }
    @CrossOrigin
    @PostMapping("/getCommand")
    public JSONObject getCommand(@RequestBody JSONObject info){
        MqProducer mqProducer = mqFactory.createProducer();
        System.out.println("------------------------从mq收到操作设备命令" + info);
        mqProducer.publish("run.command",info.toString());
        return info;
    }
}