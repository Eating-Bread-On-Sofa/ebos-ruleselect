//package cn.edu.bjtu.ebosruleselect.service;
//
//import cn.edu.bjtu.ebosruleselect.entity.Gateway;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.concurrent.SynchronousQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class InitListen implements ApplicationRunner {
//
//    @Autowired
//    GatewayService gatewayService;
//    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 50,3, TimeUnit.SECONDS,new SynchronousQueue<>());
//
////    @Override
////    public void run(ApplicationArguments args) throws Exception {
////        List<Gateway> gateways = gatewayService.findAllGateway();
////        for
////    }
//}
