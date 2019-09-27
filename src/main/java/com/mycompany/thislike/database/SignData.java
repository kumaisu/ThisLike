/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.database;

import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.mycompany.kumaisulibraries.Tools;
import static com.mycompany.thislike.config.Config.programCode;

/**
 * @author sugichan
 *
 * CREATE TABLE IF NOT EXISTS sign( id varchar(20), uuid varchar(36), name varchar(20), date DATETIME, likenum int );
 */
public class SignData {
    private static final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

    /**
     * プレイヤー情報を新規追加する
     *
     * @param player
     * @param ID
     */
    public static void AddSQL( Player player, String ID ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "INSERT INTO sign (id, uuid, name, date, likenum) VALUES (?, ?, ?, ?, ?);";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.setString( 1, ID );
            preparedStatement.setString( 2, player.getUniqueId().toString() );
            preparedStatement.setString( 3, player.getName() );
            preparedStatement.setString( 4, sdf.format( new Date() ) );
            preparedStatement.setInt( 5, 0 );

            preparedStatement.executeUpdate();
            con.close();

            Database.ID = ID;
            Database.OwnerName = player.getName();
            Database.SignDate = new Date();
            Database.LikeNum = 0;

            Tools.Prt( "Add Data to SQL Success.", Tools.consoleMode.full , programCode );
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error AddToSQL" + e.getMessage(), programCode );
        }
    }

    /**
     * プレイヤー情報を削除する
     *
     * @param ID
     * @return
     */
    public static boolean DelSQL( String ID ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "DELETE FROM sign WHERE id = '" + ID + "';";
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
     * @return
     */
    public static boolean GetSQL( String ID ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            boolean retStat = false;
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM sign WHERE id = '" + ID + "';";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            ResultSet rs = stmt.executeQuery( sql );
            if ( rs.next() ) {
                Database.ID             = rs.getString( "id" );
                Database.OwnerName      = rs.getString( "name" );
                Database.SignDate       = rs.getTimestamp( "date" );
                Database.LikeNum        = rs.getInt( "likenum" );
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

    /**
     * Like imprsioment イイネ回数カウントアップ
     *
     * @param ID
     */
    public static void incLike( String ID ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "UPDATE sign SET likenum = likenum + 1 WHERE id = '" + ID + "'";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.executeUpdate();
            con.close();
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error Add LikeNum : " + e.getMessage(), programCode );
        }
    }

    /**
     * Like imprsioment イイネ回数カウントダウン
     *
     * @param ID
     */
    public static void subLike( String ID ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "UPDATE sign SET likenum = likenum - 1 WHERE id = '" + ID + "'";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.executeUpdate();
            con.close();
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error sub LikeNum : " + e.getMessage(), programCode );
        }
    }
}
