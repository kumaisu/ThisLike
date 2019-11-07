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
import com.mycompany.thislike.database.SignData;
import com.mycompany.kumaisulibraries.Tools;
import static com.mycompany.thislike.config.Config.programCode;
import com.mycompany.thislike.database.Database;

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
            case "info":
                if ( args.length > 1 ) {
                    if ( SignData.GetSignID( Integer.valueOf( args[1] ) ) ) {
                        Tools.Prt( player, ChatColor.GREEN + "ID       : " + ChatColor.WHITE + Database.ID, programCode );
                        Tools.Prt( player, ChatColor.GREEN + "Title    : " + ChatColor.WHITE + Database.TITLE, programCode );
                        Tools.Prt( player, ChatColor.GREEN + "Owner    : " + ChatColor.WHITE + Database.OwnerName, programCode );
                        Tools.Prt( player, ChatColor.GREEN + "Like     : " + ChatColor.WHITE + Database.LikeNum, programCode );
                        Tools.Prt( player, ChatColor.GREEN + "Location : " + ChatColor.WHITE +
                            Database.LOC.getBlockX() + " , " +
                            Database.LOC.getBlockY() + " , " +
                            Database.LOC.getBlockZ() + " : " +
                            Database.LOC.getWorld().getName(), programCode );
                        Tools.Prt( player, ChatColor.GREEN + "Date     : " + ChatColor.WHITE + Database.SignDate.toString(), programCode );
                        return true;
                    } else {
                        Tools.Prt( player, ChatColor.RED + "Unknown ID Data", programCode );
                    }
                }
                return false;
            case "title":
                if ( args.length > 2 ) {
                    SignData.chgTitle( Integer.valueOf( args[1] ), args[2] );
                    return true;
                } else {
                    Tools.Prt( player, ChatColor.GREEN + "Title Change Error, Check Paramerter", programCode );
                    return false;
                }
            case "status":
                instance.config.Status( player );
                return true;
            case "reload":
                instance.config.load();
                return true;
            case "Console":
                if ( !Tools.setDebug( args[1], programCode ) ) {
                    Tools.entryDebugFlag( programCode, Tools.consoleMode.normal );
                    Tools.Prt( ChatColor.RED + "Config Debugモードの指定値が不正なので、normal設定にしました", programCode );
                }
                Tools.Prt( player,
                    ChatColor.GREEN + "System Debug Mode is [ " +
                    ChatColor.RED + Tools.consoleFlag.get( programCode ).toString() +
                    ChatColor.GREEN + " ]",
                    programCode
                );
                return true;
            case "help":
                Tools.Prt( player, ChatColor.GREEN + "/ThisLike Command List", programCode );
                Tools.Prt( player, ChatColor.WHITE + "Signs List         : " + ChatColor.YELLOW + "list [u:<player>] [d:<date>] [k:<Keyword>] [l:<line>]", programCode );
                Tools.Prt( player, ChatColor.WHITE + "Change Signs Title : " + ChatColor.YELLOW + "title [id] [new title]", programCode );
                Tools.Prt( player, ChatColor.WHITE + "ThisLike Top List  : " + ChatColor.YELLOW + "top [l:<line>]", programCode );
                Tools.Prt( player, ChatColor.WHITE + "System status      : " + ChatColor.YELLOW + "status: ", programCode );
                Tools.Prt( player, ChatColor.WHITE + "Console Mode       : " + ChatColor.YELLOW + "Console [max/full/normal/stop]", programCode );
                Tools.Prt( player, ChatColor.WHITE + "Config Reload      : " + ChatColor.YELLOW + "reload", programCode );
                return true;
            default:
                break;
        }
        Tools.Prt( player, ChatColor.RED + "[ThisLike] Unknown Command [" + CmdLine + "]", Tools.consoleMode.full, programCode );
        return false;
    }
}
