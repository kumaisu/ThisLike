/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.config;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.configuration.file.FileConfiguration;
import com.mycompany.kumaisulibraries.Tools;
import static com.mycompany.thislike.config.Config.programCode;

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

        Config.like             = config.getString( "like", "Error" );
        Config.unlike           = config.getString( "unlike", "Error" );
        Config.SignSetKey       = config.getString( "SignSetKey", "Error" );
        Config.SetLike          = config.getString( "SetLike", "Error" );
        Config.SetUnlike        = config.getString( "SetUnlike", "Error" );
        Config.InventoryTitle   = config.getString( "InventoryTitle", "Error" );
        Config.YourSign         = config.getString( "YourSign", "Error" );
        Config.Remove           = config.getString( "Remove", "Error" );
        Config.RemoveSignLore   = config.getStringList( "RemoveSignLore" );
        Config.SignBase         = config.getStringList( "SignBase" );

        Tools.consoleMode DebugFlag;
        try {
            DebugFlag = Tools.consoleMode.valueOf( config.getString( "Debug" ) );
        } catch( IllegalArgumentException e ) {
            Tools.Prt( ChatColor.RED + "Config Debugモードの指定値が不正なので、normal設定にしました", programCode );
            DebugFlag = Tools.consoleMode.normal;
        }
        Tools.entryDebugFlag( programCode, DebugFlag );
    }

    public static void Status( Player p ) {
        Tools.Prt( p, ChatColor.GREEN + "=== ThisLike Status ===", programCode );
        Tools.Prt( p, ChatColor.WHITE + "Degub Mode  : " + ChatColor.YELLOW + Tools.consoleFlag.get( programCode ).toString(), programCode );
        Tools.Prt( p, ChatColor.WHITE + "Mysql       : " + ChatColor.YELLOW + Config.host + ":" + Config.port, programCode );
        Tools.Prt( p, ChatColor.WHITE + "DB Name     : " + ChatColor.YELLOW + Config.database, programCode );
        if ( p == null ) {
            Tools.Prt( p, ChatColor.WHITE + "DB UserName : " + ChatColor.YELLOW + Config.username, programCode );
            Tools.Prt( p, ChatColor.WHITE + "DB Password : " + ChatColor.YELLOW + Config.password, programCode );
        }
        Tools.Prt( p, ChatColor.WHITE + "Dynmap Icon : " + ChatColor.YELLOW + ( Config.OnDynmap ? "True":"False" ), programCode );
        Tools.Prt( p, ChatColor.WHITE + "LIKE        : " + ChatColor.YELLOW + Config.like, programCode );
        Tools.Prt( p, ChatColor.WHITE + "UNLIKE      : " + ChatColor.YELLOW + Config.unlike, programCode );
        Tools.Prt( p, ChatColor.WHITE + "SignSetKey  : " + ChatColor.YELLOW + Config.SignSetKey, programCode );
        Tools.Prt( p, ChatColor.WHITE + "Inventory   : " + ChatColor.YELLOW + Config.InventoryTitle, programCode );
        
        Tools.Prt( p, ChatColor.WHITE + "YourSignMessage     : " + ChatColor.YELLOW + Config.YourSign, programCode );
        Tools.Prt( p, ChatColor.WHITE + "PlayerMessageLike   : " + ChatColor.YELLOW + Config.SetLike, programCode );
        Tools.Prt( p, ChatColor.WHITE + "PlayerMessageUnlike : " + ChatColor.YELLOW + Config.SetUnlike, programCode );
        Tools.Prt( p, ChatColor.WHITE + "RemoveMessage       : " + ChatColor.YELLOW + Config.Remove, programCode );

        Tools.Prt( p, ChatColor.WHITE + "Remove Sign Switch Lore", programCode );
        Config.RemoveSignLore.stream().forEach( CP -> { Tools.Prt( p, ChatColor.WHITE + " - " + ChatColor.YELLOW + CP, programCode ); } );

        Tools.Prt( p, ChatColor.WHITE + "This Like Sign Format", programCode );
        Config.SignBase.stream().forEach( CP -> { Tools.Prt( p, ChatColor.WHITE + " - " + ChatColor.YELLOW + CP, programCode ); } );

        Tools.Prt( p, ChatColor.GREEN + "==========================", programCode );
    }
}
