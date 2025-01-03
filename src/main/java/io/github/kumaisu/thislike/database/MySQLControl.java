/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kumaisu.thislike.database;

import java.sql.*;
import org.bukkit.ChatColor;
import io.github.kumaisu.thislike.Lib.Tools;
import io.github.kumaisu.thislike.config.Config;
import static io.github.kumaisu.thislike.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class MySQLControl {
    /**
     * Database Open(接続) 処理
     */
    public static void connect() throws SQLException {
        if ( Database.dataSource != null ) {
            if ( Database.dataSource.isClosed() ) {
                Tools.Prt( ChatColor.RED + "database closed.", programCode );
                disconnect();
            } else {
                Tools.Prt( ChatColor.AQUA + "dataSource is not null", programCode );
                return;
            }

            Database.DB_URL = "jdbc:mysql://" + Config.host + ":" + Config.port + "/" + Config.database;
        }
    }

    /**
     * Database Close 処理
     */
    public static void disconnect() {
        if ( Database.dataSource != null ) {
            try {
                Database.dataSource.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Database Table Initialize
     */
    public static void TableUpdate() {
        try ( Connection con = DriverManager.getConnection( Database.DB_URL, Config.username, Config.password ) ) {
            //  いいね看板テーブル
            //      id : int                auto increment
            //      title : varchar(40)     Sign Title
            //      world : varchar(30)     world name
            //      x : int
            //      y : int
            //      z : int
            //      uuid : varchar(36)      owner player uuid
            //      name : varchar(20)      owner player name
            //      ip : INTEGER UNSIGNED   IP Address
            //      date : DATETIME
            //      like : int
            //  存在すれば、無視される
            String sql = "CREATE TABLE IF NOT EXISTS sign( "
                    + "id int auto_increment, "
                    + "title varchar(40), "
                    + "world varchar(30), "
                    + "x int, "
                    + "y int, "
                    + "z int, "
                    + "uuid varchar(36), "
                    + "name varchar(20), "
                    + "ip INTEGER UNSIGNED, "
                    + "date DATETIME, "
                    + "likenum int, "
                    + "index( id ) );";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max, programCode );
            PreparedStatement preparedStatement = con.prepareStatement( sql );
            preparedStatement.executeUpdate();

            //  いいねプレイヤーテーブル
            //      id : int
            //      uuid : varchar(36)      player uuid
            //      name : varchar(20)      player name
            //      date : DATETIME         update Date
            //  存在すれば、無視される
            sql = "CREATE TABLE IF NOT EXISTS likes( id int, uuid varchar(36), name varchar(20), date DATETIME );";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max, programCode );
            preparedStatement = con.prepareStatement( sql );
            preparedStatement.executeUpdate();

            //  オーナープレイヤーテーブル
            //      uuid : varchar(36)      player uuid
            //      name : varchar(20)      player name
            //      date : DATETIME         Rewards Date
            //  存在すれば、無視される
            sql = "CREATE TABLE IF NOT EXISTS owner( uuid varchar(36), name varchar(20), date DATETIME );";
            Tools.Prt( "SQL : " + sql, Tools.consoleMode.max, programCode );
            preparedStatement = con.prepareStatement( sql );
            preparedStatement.executeUpdate();

            Tools.Prt( ChatColor.AQUA + "dataSource Open Success.", programCode );
            con.close();
        } catch( SQLException e ) {
            Tools.Prt( ChatColor.RED + "Connection Error : " + e.getMessage(), programCode);
        }
    }
}
