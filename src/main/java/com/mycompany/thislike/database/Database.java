/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.database;

import java.util.UUID;
import java.util.Date;
import org.bukkit.Location;
import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * @author sugichan
 */
public class Database {
    public static HikariDataSource dataSource = null;

    //  いいね看板テーブル
    //      id : int                auto increment
    //      title : varchar(40)     Sign Title
    //      world : varchar(30)     world name
    //      x : int
    //      y : int
    //      z : int
    //      uuid : varchar(36)      owner player uuid
    //      name : varchar(20)      owner player name
    //      date : DATETIME
    //      like : int
    //  存在すれば、無視される
    public static int ID = 0;
    public static Location LOC = null;
    public static String TITLE = "";
    public static String OwnerName;
    public static UUID OwnerUUID;
    public static Date SignDate;
    public static int LikeNum = 0;

    //  いいねプレイヤーテーブル
    //      id : int
    //      uuid : varchar(36)      player uuid
    //      name : varchar(20)      player name
    //      date : DATETIME         update Date
    //  CREATE TABLE IF NOT EXISTS likes( id int, uuid varchar(36), name varchar(20), date DATETIME );
    public static UUID LikeUUID;
    public static String LikeName;
    public static Date StampDate;
}
