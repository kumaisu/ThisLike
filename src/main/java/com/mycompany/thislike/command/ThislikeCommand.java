/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.mycompany.thislike.ThisLike;
import com.mycompany.thislike.config.Config;
import com.mycompany.thislike.config.ConfigManager;
import com.mycompany.thislike.database.Database;
import com.mycompany.thislike.database.SignData;
import com.mycompany.kumaisulibraries.Tools;

/**
 *
 * @author sugichan
 */
public class ThislikeCommand implements CommandExecutor {

     private final ThisLike instance;

     public ThislikeCommand( ThisLike instance ) {
         this.instance = instance;
     }

    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String commandLabel, String[] args ) {
        Player player = ( sender instanceof Player ? ( Player ) sender : null );

        int PrtLine = 9;
        String LikeName = "";
        String LikeDate = "";
        String LikeKey  = "";
        String CmdLine  = "help";

        if ( args.length > 0 ) {
            CmdLine = args[0];
            for ( String arg : args ) {
                String[] param = arg.split( ":" );
                switch ( param[0] ) {
                    case "u":
                        LikeName = param[1];
                        break;
                    case "d":
                        LikeDate = param[1];
                        break;
                    case "k":
                        LikeKey = param[1];
                        break;
                    case "l":
                        try {
                            PrtLine = Integer.valueOf( param[1] );
                        } catch( NumberFormatException e ) {}
                    default:
                }
            }
        }

        switch( CmdLine.toLowerCase() ) {
            case "top":
                SignData.LikeTop( player, PrtLine );
                return true;
            case "list":
                SignData.SignList( player, LikeName, LikeDate, LikeKey, PrtLine );
                return true;
            case "admin":
                SignData.SetAdmin( player );
                return true;
            case "set":
                SignData.SetOwner( player, LikeName );
                return true;
            case "info":
                if ( args.length > 1 ) {
                    if ( SignData.GetSignID( Integer.valueOf( args[1] ) ) ) {
                        Tools.Prt( player, ChatColor.GREEN + "ID       : " + ChatColor.WHITE + Database.ID, Config.programCode );
                        Tools.Prt( player, ChatColor.GREEN + "Title    : " + ChatColor.WHITE + Database.TITLE, Config.programCode );
                        Tools.Prt( player, ChatColor.GREEN + "Owner    : " + ChatColor.WHITE + Database.OwnerName, Config.programCode );
                        Tools.Prt( player, ChatColor.GREEN + "IP       : " + ChatColor.WHITE + Database.OwnerIP, Config.programCode );
                        Tools.Prt( player, ChatColor.GREEN + "Like     : " + ChatColor.WHITE + Database.LikeNum, Config.programCode );
                        Tools.Prt( player, ChatColor.GREEN + "Location : " + ChatColor.WHITE +
                            Database.LOC.getBlockX() + " , " +
                            Database.LOC.getBlockY() + " , " +
                            Database.LOC.getBlockZ() + " : " +
                            Database.LOC.getWorld().getName(), Config.programCode );
                        Tools.Prt( player, ChatColor.GREEN + "Date     : " + ChatColor.WHITE + Database.SignDate.toString(), Config.programCode );
                        return true;
                    } else {
                        Tools.Prt( player, ChatColor.RED + "Unknown ID Data", Config.programCode );
                    }
                }
                return false;
            case "title":
                if ( args.length > 2 ) {
                    SignData.chgTitle( Integer.valueOf( args[1] ), args[2] );
                    return true;
                } else {
                    Tools.Prt( player, ChatColor.GREEN + "Title Change Error, Check Paramerter", Config.programCode );
                    return false;
                }
            case "status":
                ConfigManager.Status( player );
                return true;
            case "message":
                ConfigManager.Message( player );
                return true;
            case "reload":
                ConfigManager.load();
                return true;
            case "console":
                if ( !Tools.setDebug( args[1], Config.programCode ) ) {
                    Tools.entryDebugFlag( Config.programCode, Tools.consoleMode.normal );
                    Tools.Prt( ChatColor.RED + "Config Debugモードの指定値が不正なので、normal設定にしました", Config.programCode );
                }
                Tools.Prt( player,
                    ChatColor.GREEN + "System Debug Mode is [ " +
                    ChatColor.RED + Tools.consoleFlag.get( Config.programCode ).toString() +
                    ChatColor.GREEN + " ]",
                    Config.programCode
                );
                return true;
            case "help":
                Tools.Prt( player, ChatColor.GREEN + "/ThisLike Command List", Config.programCode );
                Tools.Prt( player, ChatColor.WHITE + "Signs List         : " + ChatColor.YELLOW + "list [u:<player>] [d:<date>] [k:<Keyword>] [l:<line>]", Config.programCode );
                Tools.Prt( player, ChatColor.WHITE + "Set Admin Sign     : " + ChatColor.YELLOW + "admin", Config.programCode );
                Tools.Prt( player, ChatColor.WHITE + "Set Owner Sign     : " + ChatColor.YELLOW + "set [u:<player>]", Config.programCode );
                Tools.Prt( player, ChatColor.WHITE + "Change Signs Title : " + ChatColor.YELLOW + "title [id] [new title]", Config.programCode );
                Tools.Prt( player, ChatColor.WHITE + "ThisLike Top List  : " + ChatColor.YELLOW + "top [l:<line>]", Config.programCode );
                Tools.Prt( player, ChatColor.WHITE + "System Status      : " + ChatColor.YELLOW + "status", Config.programCode );
                Tools.Prt( player, ChatColor.WHITE + "System Message     : " + ChatColor.YELLOW + "message", Config.programCode );
                Tools.Prt( player, ChatColor.WHITE + "Console Mode       : " + ChatColor.YELLOW + "Console [max/full/normal/stop]", Config.programCode );
                Tools.Prt( player, ChatColor.WHITE + "Config Reload      : " + ChatColor.YELLOW + "reload", Config.programCode );
                return true;
            default:
                break;
        }
        Tools.Prt( player, ChatColor.RED + "[ThisLike] Unknown Command [" + CmdLine + "]", Tools.consoleMode.full, Config.programCode );
        return false;
    }
}
