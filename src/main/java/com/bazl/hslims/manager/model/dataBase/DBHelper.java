package com.bazl.hslims.manager.model.dataBase;

import com.bazl.hslims.utils.PropertiesUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/9/8.
 */
public class DBHelper {

    public Connection conn = null;
    public PreparedStatement pst = null;

    public DBHelper(String sql) {

        PropertiesUtils.loadFile("configs.properties");

        String url = PropertiesUtils.getPropertyValue("jdbc.url");
        String username = PropertiesUtils.getPropertyValue("jdbc.username");
        String password = PropertiesUtils.getPropertyValue("jdbc.password");
        String driver = PropertiesUtils.getPropertyValue("jdbc.driver");

        try {
            Class.forName(driver);//指定连接类型
            conn = DriverManager.getConnection(url, username, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
