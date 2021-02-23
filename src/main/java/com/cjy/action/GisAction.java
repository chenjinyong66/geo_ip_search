package com.cjy.action;

import com.cjy.service.GisService;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

/**
 * @author chenjinyong
 * @create 2021-02-22
 */
@At("/gis")
@IocBean
public class GisAction {
    @Inject
    private GisService gisService;

    @At("/geoGet")
    @Ok("json")
//    @Ok("->:/index.html")
    public Object geoGet( @Param("lon") double lon, @Param("lat") double lat) {
        return gisService.geoGet(lon, lat);
    }

    @At("/ipGet")
    @Ok("json")
//    @Ok("->:/index.html")
    public Object ipGet(@Param("ip") String ip) {
        return gisService.ipGet(ip);
    }


}
