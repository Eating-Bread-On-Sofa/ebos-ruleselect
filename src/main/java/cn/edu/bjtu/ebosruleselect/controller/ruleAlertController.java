package cn.edu.bjtu.ebosruleselect.controller;

import cn.edu.bjtu.ebosruleselect.service.MqConsumer;
import cn.edu.bjtu.ebosruleselect.service.MqFactory;
import cn.edu.bjtu.ebosruleselect.service.MqGetIp;
import cn.edu.bjtu.ebosruleselect.service.MqProducer;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/api")
@RestController
public class ruleAlertController {
    @Autowired
    MqFactory mqFactory;

    public static ArrayList<String> al = new ArrayList<String>();

//    public void getMsg() {
//        System.out.println("开始订阅");
//        try {
//            MqConsumer mqc = mqFactory.createConsumer("notice");
//            String msg = mqc.subscribe();
//            al.add(msg);
//            System.out.println(msg);
//        } catch (Exception e){
//            System.out.println("异常+++++++++++++++++"+e);
//        }
//
//    }

    @CrossOrigin
    @GetMapping("/ruleAlert")
    public JSONArray getRuleAlert(){
        JSONArray ja=new JSONArray();
        System.out.println("al列表是+++++++++++++++"+al);
        for(int i=0; i<al.size(); i++){
            JSONObject jj = new JSONObject();
            jj.put("message", al.get(i));
            jj.put("url", "/image/1.png");
            ja.add(jj);
        }
        System.out.println("ruleAlert拉取的告警信息—++++++++++++++++++++++++++" + ja);
        al.clear();
        return ja;
    }

    @CrossOrigin
    @PostMapping("/getMessage")
    public JSONObject getMessage(@RequestBody JSONObject info){
        System.out.println("getMessage+++++++++++++++====");
        MqProducer mqProducer = mqFactory.createProducer();
        System.out.println("------------------------从mq收到告警信息" + info);
        String msg = info.getString("message");
        System.out.println("收到的msg+++++++++"+msg);
        al.add(msg);
        mqProducer.publish("notice",info.toString());
        System.out.println("al是__________________________________"+al);
        return info;
    }
    @CrossOrigin
    @PostMapping("/getCommand")
    public JSONObject getCommand(@RequestBody JSONObject info){
        System.out.println("getCommand+++++++++++++++====");
        MqProducer mqProducer = mqFactory.createProducer();
        System.out.println("------------------------从mq收到操作设备命令" + info);
        mqProducer.publish("run.command",info.toString());
        return info;
    }
}