/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kumaisu.thislike.config;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.configuration.file.FileConfiguration;
import io.github.kumaisu.thislike.Lib.Tools;
import static io.github.kumaisu.thislike.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class ConfigManager {

    private static Plugin plugin;
    private static FileConfiguration config = null;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        Tools.entryDebugFlag( programCode, Tools.consoleMode.print );
        Tools.Prt( "Config Loading now...", programCode );
        load();
    }

    /*
     * 設定をロードします
     */
    public static void load() {
        // 設定ファイルを保存
        plugin.saveDefaultConfig();
        if ( config != null ) { // configが非null == リロードで呼び出された
            Tools.Prt( "Config Reloading now...", programCode );
            plugin.reloadConfig();
        }
        config = plugin.getConfig();

        Config.host     = config.getString( "mysql.host", "local" );
        Config.port     = config.getString( "mysql.port", "3306" );
        Config.database = config.getString( "mysql.database", "Citizenship" );
        Config.username = config.getString( "mysql.username", "root" );
        Config.password = config.getString( "mysql.password", "" );
        Config.OnDynmap = config.getBoolean( "OnDynmap", false );

        Config.LikeBroadcast    = config.getBoolean( "LikeBroadcast", true );
        Config.UnlikeBroadcast  = config.getBoolean( "UnlikeBroadcast", false );
        Config.like             = config.getString( "like", "like" );
        Config.unlike           = config.getString( "unlike", "unlike" );
        Config.SignSetKey       = config.getString( "SignSetKey", "SignSetKey" );
        Config.AdminName        = config.getString( "Admin", "Admin" );
        Config.SetLike          = config.getString( "SetLike", "SetLike" );
        Config.InfoLike         = config.getString( "InfoLike", "InfoLike" );
        Config.SetUnlike        = config.getString( "SetUnlike", "SetUnlike" );
        Config.InfoUnlike       = config.getString( "InfoUnlike", "InfoUnlike" );
        Config.InventoryTitle   = config.getString( "InventoryTitle", "InventoryTitle" );
        Config.YourSign         = config.getString( "YourSign", "YourSign" );
        Config.Remove           = config.getString( "Remove", "Remove" );
        Config.RemoveSignLore   = config.getStringList( "RemoveSignLore" );
        Config.SignBase         = config.getStringList( "SignBase" );
        Config.MakeHead         = config.getBoolean( "MakeHead", false );

        Reward.Flag             = config.getBoolean( "Reward.enable", false );
        Reward.DateCount        = config.getInt( "Reward.datecount", 1 );
        Reward.magnification    = config.getInt( "Reward.magnification", 1 );
        Reward.RewardMessage    = config.getString( "Reward.message", "" );
        Reward.Commands         = config.getStringList( "Reward.commands" );
        Reward.sound_play       = config.getBoolean( "Reward.sound.enabled", false );
        Reward.sound_type       = config.getString( "Reward.sound.type", "" );
        Reward.sound_volume     = config.getInt( "Reward.sound.volume", 1 );
        Reward.sound_pitch      = config.getInt( "Reward.sound.pitch", 1 );

        if ( !Tools.setDebug( config.getString( "Debug" ), programCode ) ) {
            Tools.entryDebugFlag( programCode, Tools.consoleMode.normal );
            Tools.Prt( ChatColor.RED + "Config Debugモードの指定値が不正なので、normal設定にしました", programCode );
        }
    }

    public static void Status( Player p ) {
        Tools.Prt( p, ChatColor.GREEN + "=== ThisLike Status ===", programCode );
        Tools.Prt( p, ChatColor.WHITE + "Degub Mode  : " + ChatColor.YELLOW + Tools.consoleFlag.get( programCode ).toString(), programCode );
        Tools.Prt( p, ChatColor.WHITE + "Mysql       : " + ChatColor.YELLOW + Config.host + ":" + Config.port + "/" + Config.database, programCode );
        if ( p == null ) {
            Tools.Prt( p, ChatColor.WHITE + "DB UserName : " + ChatColor.YELLOW + Config.username + " [" + Config.password + "]", programCode );
        }
        Tools.Prt( p, ChatColor.WHITE + "Dynmap Icon : " + ChatColor.YELLOW + ( Config.OnDynmap ? "True":"False" ), programCode );
        Tools.Prt( p, ChatColor.WHITE + "LIKE : " + ChatColor.YELLOW + Config.like + 
            ChatColor.WHITE + " - UNLIKE : " + ChatColor.YELLOW + Config.unlike, programCode );
        Tools.Prt( p, ChatColor.WHITE + "SignSetKey  : " + ChatColor.YELLOW + Config.SignSetKey, programCode );
        Tools.Prt( p, ChatColor.WHITE + "Admin Name  : " + ChatColor.YELLOW + Config.AdminName, programCode );
        Tools.Prt( p, ChatColor.WHITE + "Inventory   : " + ChatColor.YELLOW + Config.InventoryTitle, programCode );
        Tools.Prt( p, ChatColor.WHITE + "Player Head : " + ChatColor.YELLOW + ( Config.MakeHead ? "Make" : "Plain" ) + " Icon", programCode );
        
        Tools.Prt( p, ChatColor.WHITE + "Broadcast : Link(" +
            ChatColor.YELLOW + ( Config.LikeBroadcast ? "Yes" : "No" ) +
            ChatColor.WHITE + ") Unlike(" +
            ChatColor.YELLOW + ( Config.UnlikeBroadcast ? "Yes" : "No" ) +
            ChatColor.WHITE + ")", programCode
        );

        if ( Reward.Flag ) {
            Tools.Prt( p, ChatColor.GREEN + "ThisLike Rewards", programCode );
            if ( Reward.sound_play ) {
                Tools.Prt( p, ChatColor.WHITE + "Sound Type : " + ChatColor.YELLOW + Reward.sound_type +
                    ChatColor.WHITE + " V:" + ChatColor.YELLOW + Reward.sound_volume +
                    ChatColor.WHITE + " P:" + ChatColor.YELLOW + Reward.sound_pitch, programCode );
            }
            Tools.Prt( p,
                ChatColor.WHITE + "Date Count : " +
                ChatColor.YELLOW + Reward.DateCount + " Days " +
                ChatColor.AQUA + "(×" + Reward.magnification + ")",
                programCode
            );
            Tools.Prt( p, ChatColor.WHITE + "Reward Command", programCode );
            Reward.Commands.stream().forEach( CP -> { Tools.Prt( p, ChatColor.WHITE + " - " + ChatColor.YELLOW + CP, programCode ); } );
        }
        
        Tools.Prt( p, ChatColor.GREEN + "==========================", programCode );
    }
    
    public static void Message( Player p ) {
        Tools.Prt( p, ChatColor.WHITE + "Remove Sign Switch Lore", programCode );
        Config.RemoveSignLore.stream().forEach( CP -> { Tools.Prt( p, ChatColor.WHITE + " - " + ChatColor.YELLOW + CP, programCode ); } );

        Tools.Prt( p, ChatColor.WHITE + "This Like Sign Format", programCode );
        Config.SignBase.stream().forEach( CP -> { Tools.Prt( p, ChatColor.WHITE + " - " + ChatColor.YELLOW + CP, programCode ); } );

        Tools.Prt( p, ChatColor.GREEN + "=== ThisLike Message ===", programCode );
        Tools.Prt( p, ChatColor.WHITE + "YourSignMessage     : " + ChatColor.YELLOW + Config.YourSign, programCode );
        Tools.Prt( p, ChatColor.WHITE + "PlayerMessageLike   : " + ChatColor.YELLOW + Config.SetLike, programCode );
        Tools.Prt( p, ChatColor.WHITE + "OwnerMessageLike    : " + ChatColor.YELLOW + Config.InfoLike, programCode );
        Tools.Prt( p, ChatColor.WHITE + "PlayerMessageUnlike : " + ChatColor.YELLOW + Config.SetUnlike, programCode );
        Tools.Prt( p, ChatColor.WHITE + "OwnerMessageUnlike  : " + ChatColor.YELLOW + Config.InfoUnlike, programCode );
        Tools.Prt( p, ChatColor.WHITE + "RemoveMessage       : " + ChatColor.YELLOW + Config.Remove, programCode );
        Tools.Prt( p, ChatColor.GREEN + "==========================", programCode );
    }
}
