package com.cjy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

/**
 * @author chenjinyong
 * @create 2021-02-22
 */
@IocBean
public class GisService {
    @Inject
    private Dao dao;

    public Object geoGet(double lon, double lat) {

//        String sql = "SELECT json_object_agg(level,name)as data FROM adcode_fences WHERE ST_Contains(fence, ST_Point(" + lon + ", " + lat + "))";
        String sql = "SELECT json_object_agg ( LEVEL, NAME ) AS data, max( yyn_id ) AS yyn_id,max( adcode ) AS adcode FROM ( SELECT LEVEL, NAME, adcode, yyn_id, rank FROM adcode_fences WHERE ST_Contains ( fence, ST_Point ( " + lon + "," + lat + ") ) ) a";

        System.err.println(sql);

        Sql geoSql = Sqls.create(sql);

        Record record = new Record();
        try {

            geoSql.setCallback(Sqls.callback.records());
            dao.execute(geoSql);
            // 根据回调函数 获得总记录数

            List<Record> result = (List<Record>) geoSql.getResult();

            if (result.size() > 0) {

                Record rec = new Record();
                for (Record re : result) {

                    String data = re.getString("data");
                    String yyn_id = re.getString("yyn_id");
                    int adcode = re.getInt("adcode");

                    JSONObject jsonObject = JSON.parseObject(data);

                    for (String k : jsonObject.keySet()) {
                        rec.set(k, jsonObject.get(k));
                    }
                    rec.set("yyn_id", yyn_id);
                    rec.set("adcode", adcode);


                }

                record.set("data", rec);
                String data = record.getString("data");

                if (data == null) {
                    record.set("code", 2);
                    record.set("msg", "ok");
                } else {
                    record.set("data", data);
                    record.set("code", 0);
                    record.set("msg", "ok");
                }
            }


            System.err.println(record);
        } catch (Exception e) {
            e.printStackTrace();
            return record.set("code", 1).set("msg", "INVALID_KEY");
        }

        return record;

    }


    public Object ipGet(String ip) {

//        String sql = "select concat(country,'|',province,'|',city,'|',operator) as data from ipshigh where ips @>inet '" + ip + "'";
        String sql = "select country,province,city,carriers,adcode,yyn_id  from ipshigh where ips @>inet '" + ip + "'";

        System.err.println(sql);

        Sql geoSql = Sqls.create(sql);


        Record record = new Record();
        try {
            geoSql.setCallback(Sqls.callback.records());
            dao.execute(geoSql);
            List<Record> result = (List<Record>) geoSql.getResult();

            if (result.size() > 0) {
                record.set("data", result.get(0));
            }

            record.set("code", 0);
            record.set("msg", "ok");

            System.err.println(record);

        } catch (Exception e) {
            e.printStackTrace();

            return record.set("code", 1).set("msg", "INVALID__KEY");
        }
        return record;

    }

}
