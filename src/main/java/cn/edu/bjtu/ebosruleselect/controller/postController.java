package cn.edu.bjtu.ebosruleselect.controller;

import cn.edu.bjtu.ebosruleselect.entity.RestTemplateUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class postController {
    public static void sendPostRequest(String url, Map<String, Object> params){
        RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");
        String result = restTemplate.postForObject(url, params, String.class);
        System.out.println(result);
    }

}
