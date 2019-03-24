package com.itqf.dtboot.job.task;

import com.itqf.dtboot.utils.InitDatabaseUtils;
import com.itqf.dtboot.utils.Lg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestBackUp {

    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.getRuntime();
            //mysqldump -uroot -padmin 数据库的名字 -r 备份后存放路径
            //根据properties文件构建一个Properties对象
            InputStream is =  BackUpDB.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(is);

            String command = InitDatabaseUtils.getExportCommand(properties);
            System.out.println(command);
            runtime.exec(command);
            Lg.log("备份成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
