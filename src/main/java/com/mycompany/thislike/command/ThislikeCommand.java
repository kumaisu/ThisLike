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

        String commandString = "";
        String itemName = "";
        if ( args.length > 0 ) commandString = args[0];
        if ( args.length > 1 ) itemName = args[1];
        switch ( commandString ) {
            case "top":
                int lineSet;
                try {
                    lineSet = Integer.valueOf( itemName );
                } catch ( NumberFormatException e ) {
                    lineSet = 5;
                }
                SignData.LikeTop( player, lineSet );
                return true;
            case "list":
                SignData.SignList( player, ( ( args.length > 1 ) ? args[1] : "" ) );
                return true;
            case "status":
                instance.config.Status( player );
                return true;
            case "reload":
                instance.config.load();
                return true;
            case "Console":
                Tools.setDebug( itemName, programCode );
                Tools.Prt( player,
                    ChatColor.GREEN + "System Debug Mode is [ " +
                    ChatColor.RED + Tools.consoleFlag.get( programCode ).toString() +
                    ChatColor.GREEN + " ]",
                    programCode
                );
                return true;
            case "help":
                Tools.Prt( player, ChatColor.GREEN + "/ThisLike Command List", programCode );
                Tools.Prt( player, ChatColor.YELLOW + "top [num]      : " + ChatColor.WHITE + "いいねトップリスト", programCode );
                Tools.Prt( player, ChatColor.YELLOW + "list [player]  : " + ChatColor.WHITE + "いいね看板リスト", programCode );
                Tools.Prt( player, ChatColor.YELLOW + "status         : " + ChatColor.WHITE + "システム設定閲覧", programCode );
                Tools.Prt( player, ChatColor.YELLOW + "Console [Mode] : " + ChatColor.WHITE + "コンソールデバッグ設定 [max,full,normal,none]", programCode );
                Tools.Prt( player, ChatColor.YELLOW + "reload         : " + ChatColor.WHITE + "Configリロード", programCode );
                return true;
            default:
                break;
        }

        Tools.Prt( player, ChatColor.RED + "Not supported yet.", programCode );
        Tools.Prt( player, ChatColor.RED + "[ThisLike] Unknown Command [" + commandString + "]", Tools.consoleMode.full, programCode );
        return false;
    }
}
