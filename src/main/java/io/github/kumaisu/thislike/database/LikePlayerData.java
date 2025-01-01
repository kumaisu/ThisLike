/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kumaisu.thislike.database;

import java.sql.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import static java.util.UUID.fromString;
import java.text.SimpleDateFormat;

import io.github.kumaisu.thislike.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import io.github.kumaisu.thislike.Lib.Tools;
import static io.github.kumaisu.thislike.config.Config.programCode;

/**
 *
 * @author sugichan
 *
 * CREATE TABLE IF NOT EXISTS likes( id varchar(12), uuid varchar(36), name varchar(20), date DATETIME );
 *
 * public static UUID LikeUUID;
 * public static String LikeName;
 * public static Date StampDate;
 */
public class LikePlayerData {

    private static final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

    /**
     * プレイヤー情報を新規追加する
     *
     * @param player
     * @param ID
     */
    public static void AddSQL( Player player, int ID ) {
        try ( Connection con = DriverManager.getConnection( Database.DB_URL, Config.username, Config.password ) ) {
            String sql = "INSERT INTO likes (id, uuid, name, date) VALUES (?, ?, ?, ?);";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.setInt( 1, ID );
            preparedStatement.setString( 2, player.getUniqueId().toString() );
            preparedStatement.setString( 3, player.getName() );
            preparedStatement.setString( 4, sdf.format( new Date() ) );

            preparedStatement.executeUpdate();
            con.close();

            Database.LikeUUID   = player.getUniqueId();
            Database.LikeName   = player.getName();
            Database.StampDate  = new Date();

            Tools.Prt( "Like Add Data to SQL Success.", Tools.consoleMode.full , programCode );
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error AddToSQL" + e.getMessage(), programCode );
        }
    }

    /**
     * プレイヤー情報を削除する
     *
     * @param ID
     * @param player
     * @return
     */
    public static boolean DelPlayerSQL( int ID, Player player ) {
        try ( Connection con = DriverManager.getConnection( Database.DB_URL, Config.username, Config.password ) ) {
            String sql = "DELETE FROM likes WHERE id = " + ID + " AND uuid = '" + player.getUniqueId().toString() + "';";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.executeUpdate();
            Tools.Prt( "Like Delete Data from SQL Success.", Tools.consoleMode.full , programCode );
            con.close();
            return true;
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error DelFromSQL" + e.getMessage(), programCode );
            return false;
        }
    }

    /**
     * プレイヤー情報を削除する
     *
     * @param ID
     * @return
     */
    public static boolean DelSQL( int ID ) {
        try ( Connection con = DriverManager.getConnection( Database.DB_URL, Config.username, Config.password ) ) {
            String sql = "DELETE FROM likes WHERE id = " + ID + ";";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.executeUpdate();
            Tools.Prt( "Like Delete Data from SQL Success.", Tools.consoleMode.full, programCode );
            con.close();
            return true;
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error DelFromSQL" + e.getMessage(), programCode );
            return false;
        }
    }

    /**
     * UUIDからプレイヤー情報を取得する
     *
     * @param ID
     * @return
     */
    public static boolean GetSQL( int ID ) {
        try ( Connection con = DriverManager.getConnection( Database.DB_URL, Config.username, Config.password ) ) {
            boolean retStat = false;
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM likes WHERE id = " + ID + ";";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            ResultSet rs = stmt.executeQuery( sql );
            if ( rs.next() ) {
                Database.LikeUUID   = fromString( rs.getString( "uuid" ) );
                Database.LikeName   = rs.getString( "name" );
                Database.StampDate  = rs.getTimestamp( "date" );
                Tools.Prt( "Like Get Data from SQL Success.", Tools.consoleMode.full, programCode );
                retStat = true;
            }
            con.close();
            return retStat;
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error GetPlayer" + e.getMessage(), programCode );
            return false;
        }
    }

    /**
     * UUIDからプレイヤー情報を取得する
     *
     * @param ID
     * @param player
     * @return
     */
    public static boolean hasSQL( int ID, Player player ) {
        try ( Connection con = DriverManager.getConnection( Database.DB_URL, Config.username, Config.password ) ) {
            boolean retStat = false;
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM likes WHERE id = " + ID + " AND uuid = '" + player.getUniqueId().toString() + "';";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            ResultSet rs = stmt.executeQuery( sql );
            if ( rs.next() ) {
                Database.LikeUUID   = fromString( rs.getString( "uuid" ) );
                Database.LikeName   = rs.getString( "name" );
                Database.StampDate  = rs.getTimestamp( "date" );
                Tools.Prt( "Get Data from SQL Success.", Tools.consoleMode.full, programCode );
                retStat = true;
            }
            con.close();
            return retStat;
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error GetPlayer" + e.getMessage(), programCode );
            return false;
        }
    }

    /**
     * IDからプレイヤー一覧を取得
     *
     * @param ID
     * @return 
     */
    public static Map< String, Date > listSQL( int ID ) {
        Map< String, Date > likeP = new HashMap<>();

        try ( Connection con = DriverManager.getConnection( Database.DB_URL, Config.username, Config.password ) ) {
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM likes WHERE id = " + ID; // + " ORDER BY date DESC;";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            ResultSet rs = stmt.executeQuery( sql );
            int i = 0;
            while ( rs.next() ) {
                String liker = rs.getString( "name" );
                Date likeD = rs.getTimestamp( "date" );
                Tools.Prt( "get Liker : " + liker, Tools.consoleMode.max, programCode );
                likeP.put( liker, likeD );
            }
            Tools.Prt( "get Like Player List from SQL Success.", Tools.consoleMode.full, programCode );
            con.close();
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error listSQL" + e.getMessage(), programCode );
        }
        return likeP;
    }
}
