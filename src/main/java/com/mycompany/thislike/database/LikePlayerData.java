/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.database;

import java.util.Date;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.mycompany.kumaisulibraries.Tools;
import static com.mycompany.thislike.config.Config.programCode;

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
    public static void AddSQL( Player player, String ID ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "INSERT INTO likes (id, uuid, name, date) VALUES (?, ?, ?, ?);";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.setString( 1, ID );
            preparedStatement.setString( 2, player.getUniqueId().toString() );
            preparedStatement.setString( 3, player.getName() );
            preparedStatement.setString( 4, sdf.format( new Date() ) );

            preparedStatement.executeUpdate();
            con.close();

            Database.LikeUUID   = player.getUniqueId();
            Database.LikeName   = player.getName();
            Database.StampDate  = new Date();

            Tools.Prt( "Add Data to SQL Success.", Tools.consoleMode.full , programCode );
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
    public static boolean DelSQL( String ID, Player player ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "DELETE FROM likes WHERE id = '" + ID + "' AND uuid = '" + player.getUniqueId().toString() + "';";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.executeUpdate();
            Tools.Prt( "Delete Data from SQL Success.", Tools.consoleMode.full , programCode );
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
     * @param player
     * @return
     */
    public static boolean hasSQL( String ID, Player player ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            boolean retStat = false;
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM likes WHERE id = '" + ID + "' AND uuid = '" + player.getUniqueId().toString() + "';";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            ResultSet rs = stmt.executeQuery( sql );
            if ( rs.next() ) {
                Tools.Prt( "Get Data from SQL Success.", Tools.consoleMode.full , programCode );
                retStat = true;
            }
            con.close();
            return retStat;
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error GetPlayer" + e.getMessage(), programCode );
            return false;
        }
    }
}
