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
import static java.util.UUID.fromString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.mycompany.kumaisulibraries.Tools;
import static com.mycompany.thislike.config.Config.programCode;

/**
 * @author sugichan
 *
 * いいね看板テーブル
 *      id : int                auto increment
 *      world : varchar(20)     world name
 *      x : int
 *      y : int
 *      z : int
 *      uuid : varchar(36)      owner player uuid
 *      name : varchar(20)      owner player name
 *      date : DATETIME
 *      like : int
 */
public class SignData {
    private static final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

    /**
     * プレイヤー情報を新規追加する
     *
     * @param player
     * @param LOC
     */
    public static void AddSQL( Player player, Location LOC ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "INSERT INTO sign (world, x, y, z, uuid, name, date, likenum) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.setString( 1, LOC.getWorld().getName() );
            preparedStatement.setInt( 2, LOC.getBlockX() );
            preparedStatement.setInt( 3, LOC.getBlockY() );
            preparedStatement.setInt( 4, LOC.getBlockZ() );
            preparedStatement.setString( 5, player.getUniqueId().toString() );
            preparedStatement.setString( 6, player.getName() );
            preparedStatement.setString( 7, sdf.format( new Date() ) );
            preparedStatement.setInt( 8, 0 );

            preparedStatement.executeUpdate();
            con.close();

            Database.LOC = LOC;
            Database.OwnerName = player.getName();
            Database.SignDate = new Date();
            Database.LikeNum = 0;

            Tools.Prt( "Sign Add Data to SQL Success.", Tools.consoleMode.max, programCode );
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
    public static boolean DelSQL( int ID ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "DELETE FROM sign WHERE id = " + ID + ";";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.executeUpdate();
            Tools.Prt( "Sign Delete Data from SQL Success.", Tools.consoleMode.max, programCode );
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
     * @param LOC
     * @return
     */
    public static boolean GetSQL( Location LOC ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            boolean retStat = false;
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM sign WHERE x = " + LOC.getBlockX() +
                    " AND y = " + LOC.getBlockY() +
                    " AND z = " + LOC.getBlockZ() +
                    " AND world = '" + LOC.getWorld().getName() + "';";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            ResultSet rs = stmt.executeQuery( sql );
            if ( rs.next() ) {
                Database.ID             = rs.getInt( "id" );
                Database.LOC    = new Location(
                        Bukkit.getWorld( rs.getString( "world" ) ),
                        rs.getInt( "x" ),
                        rs.getInt( "y" ),
                        rs.getInt( "z" ) );
                Database.OwnerUUID      = fromString( rs.getString( "uuid" ) );
                Database.OwnerName      = rs.getString( "name" );
                Database.SignDate       = rs.getTimestamp( "date" );
                Database.LikeNum        = rs.getInt( "likenum" );
                Tools.Prt( "Sign Get Data from SQL Success.", Tools.consoleMode.max , programCode );
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
    public static void incLike( int ID ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "UPDATE sign SET likenum = likenum + 1 WHERE id = " + ID + ";";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.executeUpdate();
            Tools.Prt( "Sign Like Inc Success.", Tools.consoleMode.max , programCode );
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
    public static void subLike( int ID ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "UPDATE sign SET likenum = likenum - 1 WHERE id = " + ID + ";";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.executeUpdate();
            Tools.Prt( "Sign Like Sub Success.", Tools.consoleMode.max , programCode );
            con.close();
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error sub LikeNum : " + e.getMessage(), programCode );
        }
    }

    /**
     * イイネ看板リスト
     *
     * @param player 
     */
    public static void SignList( Player player ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            Tools.Prt( player, ChatColor.GREEN + "List for Signs ...", programCode );
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM sign ORDER BY world ASC;";
            ResultSet rs = stmt.executeQuery( sql );
            while( rs.next() ) {
                Tools.Prt( player, 
                    ChatColor.WHITE + String.format( "%4d", rs.getInt( "id" ) ) + ": " +
                    ChatColor.AQUA + rs.getString( "name" ) + " (" +
                    ChatColor.BLUE + String.format( "%2d", rs.getInt( "likenum" ) ) +
                    ChatColor.AQUA + ") " +
                    ChatColor.YELLOW + "[" +
                    rs.getString( "world" ) + " " +
                    rs.getInt( "x" ) + "," + 
                    rs.getInt( "y" ) + "," +
                    rs.getInt( "z" ) + "] " +
                    ChatColor.GREEN + rs.getString( "date" ),
                    programCode
                );
            }
            con.close();
            Tools.Prt( player, ChatColor.GREEN + "List [EOF]", programCode );
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error SignList : " + e.getMessage(), programCode );
        }
    }

    /**
     * イイネ看板リスト
     *
     * @param player 
     * @param LineSet 
     */
    public static void LikeTop( Player player, int LineSet ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            Tools.Prt( player, ChatColor.GREEN + "Like Top List ...", programCode );
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM sign ORDER BY likenum DESC;";
            ResultSet rs = stmt.executeQuery( sql );

            int Rank = 0;
            while( rs.next() && ( Rank<LineSet ) ) {
                Rank++;
                Tools.Prt( player, 
                    ChatColor.WHITE + String.format( "%3d", Rank ) + ": " +
                    ChatColor.AQUA + rs.getString( "name" ) + " (" +
                    ChatColor.BLUE + String.format( "%2d", rs.getInt( "likenum" ) ) +
                    ChatColor.AQUA + ") " +
                    ChatColor.YELLOW + "[" +
                    rs.getString( "world" ) + " " +
                    String.format( "%6d", rs.getInt( "x" ) ) + "," + 
                    String.format( "%3d", rs.getInt( "y" ) ) + "," +
                    String.format( "%6d", rs.getInt( "z" ) ) + "] ",
                    programCode
                );
            }
            con.close();
            if ( Rank == 0 ) Tools.Prt( player, ChatColor.GREEN + "Top List [EOF]", programCode );
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error LikeTop : " + e.getMessage(), programCode );
        }
    }
}
