/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.database;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
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
 *      title : varchar(40)     Sign Title
 *      world : varchar(30)     world name
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
     * @param Title
     */
    public static void AddSQL( Player player, Location LOC, String Title ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "INSERT INTO sign (title, world, x, y, z, uuid, name, date, likenum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.setString( 1, Title );
            preparedStatement.setString( 2, LOC.getWorld().getName() );
            preparedStatement.setInt( 3, LOC.getBlockX() );
            preparedStatement.setInt( 4, LOC.getBlockY() );
            preparedStatement.setInt( 5, LOC.getBlockZ() );
            preparedStatement.setString( 6, player.getUniqueId().toString() );
            preparedStatement.setString( 7, player.getName() );
            preparedStatement.setString( 8, sdf.format( new Date() ) );
            preparedStatement.setInt( 9, 0 );

            preparedStatement.executeUpdate();
            con.close();

            Database.LOC = LOC;
            Database.TITLE = Title;
            Database.OwnerName = player.getName();
            Database.SignDate = new Date();
            Database.LikeNum = 0;

            Tools.Prt( "Sign Add Data to SQL Success.", Tools.consoleMode.full, programCode );
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
            Tools.Prt( "Sign Delete Data from SQL Success.", Tools.consoleMode.full, programCode );
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
     * @param sqlCmd
     * @return
     */
    public static boolean GetSQL( String sqlCmd ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            boolean retStat = false;
            Statement stmt = con.createStatement();
            Tools.Prt( "SQL : " + sqlCmd, Tools.consoleMode.max , programCode );
            ResultSet rs = stmt.executeQuery( sqlCmd );
            if ( rs.next() ) {
                Database.ID             = rs.getInt( "id" );
                Database.LOC = new Location(
                        Bukkit.getWorld( rs.getString( "world" ) ),
                        rs.getInt( "x" ),
                        rs.getInt( "y" ),
                        rs.getInt( "z" ) );
                Database.TITLE          = rs.getString( "title" );
                Database.OwnerUUID      = fromString( rs.getString( "uuid" ) );
                Database.OwnerName      = rs.getString( "name" );
                Database.SignDate       = rs.getTimestamp( "date" );
                Database.LikeNum        = rs.getInt( "likenum" );
                Tools.Prt( "Sign Get Data from SQL Success.", Tools.consoleMode.full, programCode );
                retStat = true;
            }
            con.close();
            return retStat;
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error GetSQL" + e.getMessage(), programCode );
            return false;
        }
    }

    /**
     * 看板のロケーションからデータを取得
     *
     * @param LOC
     * @return 
     */
    public static boolean GetSignLoc( Location LOC ) {
        String sql = "SELECT * FROM sign WHERE x = " + LOC.getBlockX() +
            " AND y = " + LOC.getBlockY() +
            " AND z = " + LOC.getBlockZ() +
            " AND world = '" + LOC.getWorld().getName() + "';";
        return GetSQL( sql );
    }

    /**
     * 看板のIDからデータを取得
     *
     * @param ID
     * @return 
     */
    public static boolean GetSignID( int ID ) {
        String sql = "SELECT * FROM sign WHERE ID = " + ID;
        return GetSQL( sql );
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
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.executeUpdate();
            Tools.Prt( "Sign Like Inc Success.", Tools.consoleMode.full, programCode );
            con.close();
            Database.LikeNum++;
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
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.executeUpdate();
            Tools.Prt( "Sign Like Sub Success.", Tools.consoleMode.full, programCode );
            con.close();
            Database.LikeNum--;
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error sub LikeNum : " + e.getMessage(), programCode );
        }
    }

    /**
     * Sifn Title 再設定
     *
     * @param ID
     * @param Title
     */
    public static void chgTitle( int ID, String Title ) {
        try ( Connection con = Database.dataSource.getConnection() ) {
            String sql = "UPDATE sign SET title = '" + Title + "' WHERE id = " + ID + ";";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max , programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.executeUpdate();
            Tools.Prt( "Sign Title Update Success.", Tools.consoleMode.full, programCode );
            con.close();
            Database.TITLE = Title;
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error Sign Title Update : " + e.getMessage(), programCode );
        }
    }

    /**
     * DBからリストを取得する
     *
     * @param player
     * @param sqlCmd
     * @param Title
     * @param line
     * @param IDPRT
     */
    public static void GetList( Player player, String sqlCmd, String Title, int line, boolean IDPRT ) {
        List< String > StringData = new ArrayList<>();
        Tools.Prt( "SQL : " + sqlCmd, Tools.consoleMode.max, programCode );

        try ( Connection con = Database.dataSource.getConnection() ) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( sqlCmd );

            int loopCount = 0;
            while( rs.next() && ( loopCount<line ) ) {
                loopCount++;
                String Lines;
                if ( IDPRT ) {
                    Lines = ChatColor.WHITE + String.format( "%4d", rs.getInt( "id" ) );
                } else {
                    Lines = ChatColor.WHITE + String.format( "%2d", loopCount ) +
                    ChatColor.GOLD + "(" +
                    ChatColor.BLUE + String.format( "%2d", rs.getInt( "likenum" ) ) +
                    ChatColor.GOLD + ")";
                }
                Lines += 
                    ChatColor.WHITE + ": " +
                    ChatColor.GREEN + rs.getString( "title" ) +
                    ChatColor.AQUA +  " By." + rs.getString( "name" );
                StringData.add( Lines );
            }

            con.close();
            Tools.Prt( "Get List Success.", Tools.consoleMode.full, programCode );
        } catch ( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Error GetList : " + e.getMessage(), programCode );
            return;
        }

        if ( StringData.size() > 0 ) {
            Tools.Prt( player, Title, programCode );
            StringData.forEach( ( s ) -> { Tools.Prt( player, s, programCode ); } );
        }
    }

    /**
     * イイネ看板リスト
     *
     * @param player 
     * @param name 
     * @param date 
     * @param keyword 
     * @param line 
     */
    public static void SignList( Player player, String name, String date, String keyword, int line ) {
        String TitleString = ChatColor.WHITE + "== Sign List == ";
        String sqlCmd = "SELECT * FROM sign";
        boolean sqlAdd = false;

        if ( !"".equals( name ) ) {
            TitleString += "[Name:" + name + "] ";
            sqlCmd += " WHERE name LIKE '%" + name +"%'";
            sqlAdd = true;
        }

        if ( !"".equals( date ) ) {
            if ( sqlAdd ) { sqlCmd += " ADD "; } else { sqlCmd += " WHERE "; }
            TitleString += "[Date:" + date + "]";
            sqlCmd += "date BETWEEN '" + date + " 00:00:00' AND '" + date + " 23:59:59'";
            sqlAdd = true;
        }

        if ( !"".equals( keyword ) ) {
            if ( sqlAdd ) { sqlCmd += " ADD "; } else { sqlCmd += " WHERE "; }
            TitleString += "[Keyword:" + keyword + "]";
            sqlCmd += "title LIKE '%" + keyword + "%'";
        }

        sqlCmd +=  " ORDER BY date DESC;";

        GetList( player, sqlCmd, TitleString, line, true );
    }

    /**
     * イイネ看板トップリスト
     *
     * @param player 
     * @param LineSet 
     */
    public static void LikeTop( Player player, int LineSet ) {
        GetList( player, "SELECT * FROM sign ORDER BY likenum DESC;", ChatColor.GREEN + "Like Top List ...", LineSet, false );
    }
}
