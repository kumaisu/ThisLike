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
 * CREATE TABLE IF NOT EXISTS owner( uuid varchar(36), name varchar(20), date DATETIME );
 *
 * オーナープレイヤーテーブル
 * uuid : varchar(36)      player uuid
 * name : varchar(20)      player name
 * date : DATETIME         Rewards Date
 */
public class OwnerData {

    private static final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

    /**
     * OWNER情報を新規追加する
     *
     * @param player
     */
    public static void AddSQL( Player player ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "INSERT INTO owner (uuid, name, date) VALUES (?, ?, ?);";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.setString( 1, player.getUniqueId().toString() );
            preparedStatement.setString( 2, player.getName() );
            preparedStatement.setString( 3, sdf.format( new Date() ) );

            preparedStatement.executeUpdate();
            con.close();

            Tools.Prt( "Owner Add Data to SQL Success.", Tools.consoleMode.full , programCode );
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error Owner.AddSQL:" + e.getMessage(), programCode );
        }
    }

    /**
     * OWNER情報を削除する
     *
     * @param uuid
     * @return
     */
    public static boolean DelSQL( UUID uuid ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "DELETE FROM owner WHERE uuid = '" + uuid.toString() + "';";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.executeUpdate();
            Tools.Prt( "Owner Delete Data from SQL Success.", Tools.consoleMode.full , programCode );
            con.close();
            return true;
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error Owner.DelSQL:" + e.getMessage(), programCode );
            return false;
        }
    }

    /**
     * UUIDからReward更新日を取得する
     *
     * @param uuid
     * @return
     */
    public static Date GetDate( UUID uuid ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            Date Ret = null;
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM owner WHERE uuid = '" + uuid.toString() + "';";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            ResultSet rs = stmt.executeQuery( sql );
            if ( rs.next() ) {
                Ret = rs.getTimestamp( "date" );
                Tools.Prt( "Owner Get Date from SQL Success.", Tools.consoleMode.full, programCode );
            }
            con.close();
            return Ret;
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error Owner.GetDate:" + e.getMessage(), programCode );
            return null;
        }
    }

    /**
     * UUID から Reward日を更新する
     *
     * @param uuid
     */
    public static void Update( UUID uuid ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "UPDATE owner SET date = '" + sdf.format( new Date() ) + "' WHERE uuid = '" + uuid.toString() + "';";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.executeUpdate();
            Tools.Prt( "Update Owner Rewards Date Success.", Tools.consoleMode.full, programCode );
            con.close();
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error Owner.Update:" + e.getMessage(), programCode );
        }
    }
}
