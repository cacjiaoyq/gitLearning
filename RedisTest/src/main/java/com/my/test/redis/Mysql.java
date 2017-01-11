package com.my.test.redis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {

    public Connection conn;

    {

            try {

                     Class.forName("com.mysql.jdbc.Driver");

                     conn=DriverManager.getConnection("jdbc:mysql://172.28.176.188:3306/wxshop","wx","wx");

            } catch (ClassNotFoundException e) {

                     e.printStackTrace();

            } catch (SQLException e) {

                     e.printStackTrace();

            }

    }



}

