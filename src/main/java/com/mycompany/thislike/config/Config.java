/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.config;

import java.util.List;
import com.mycompany.thislike.database.Database;

/**
 *
 * @author sugichan
 */
public class Config {

    public static String programCode = "TL";

    public static String host;
    public static String port;
    public static String database;
    public static String username;
    public static String password;

    public static boolean OnDynmap;

    public static String like;
    public static String unlike;
    public static String SignSetKey;
    public static String SetLike;
    public static String SetUnlike;
    public static String InventoryTitle;
    public static String YourSign;
    public static String Remove;
    public static List<String> RemoveSignLore;
    public static List<String> SignBase;

    /**
     * 文字列置換
     * @param data  書き換え元の文章
     * @return      書き換え後の文章
     */
    public static String ReplaceString( String data ) {
        data = data.replace( "%player%", Database.OwnerName );
        data = data.replace( "%title%", Database.TITLE );
        data = data.replace( "%num%", String.valueOf( Database.LikeNum ) );
        return data.replace( "%$", "§" );
    }   
}
