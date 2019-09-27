/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.database;

import java.util.UUID;
import java.util.Date;
import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * @author sugichan
 */
public class Database {
    public static HikariDataSource dataSource = null;

    //  いいね看板テーブル
    //      id : varchar(12)        x y z x16 { max ffffffffffff }
    //      uuid : varchar(36)      owner player uuid
    //      name : varchar(20)      owner player name
    //      date : DATETIME
    //      like : int
    //  CREATE TABLE IF NOT EXISTS sign( id varchar(20), uuid varchar(36), name varchar(20), date DATETIME, like int );
    public static String ID = "000000000000";
    public static String OwnerName;
    public static Date SignDate;
    public static int LikeNum = 0;

    //  いいねプレイヤーテーブル
    //      id : varchar(12)
    //      uuid : varchar(36)      player uuid
    //      name : varchar(20)      player name
    //      date : DATETIME         update Date
    //  CREATE TABLE IF NOT EXISTS likes( id varchar(12), uuid varchar(36), name varchar(20), date DATETIME );
    public static UUID LikeUUID;
    public static String LikeName;
    public static Date StampDate;
}
