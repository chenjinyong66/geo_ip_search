package com.cjy;

import org.nutz.boot.NbApp;
import org.nutz.ioc.loader.annotation.*;

@IocBean(create="init", depose="depose")
public class MainLauncher {
    
//    @Inject
//    protected PropertiesProxy conf;
//    @Inject
//    protected Dao dao;
////    @At("/")
//    @Ok("->:/index.html")
    public void index() {}
    
    public void init() {
        // NB自身初始化完成后会调用这个方法

    }
    public void depose() {}

    public static void main(String[] args) throws Exception {
        new NbApp().setArgs(args).setPrintProcDoc(true).run();
    }

}
