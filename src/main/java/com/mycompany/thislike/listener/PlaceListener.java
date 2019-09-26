/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.listener;

import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import com.mycompany.kumaisulibraries.Tools;
import static com.mycompany.thislike.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class PlaceListener implements Listener {

    /**
     *
     * @param plugin
     */
    public PlaceListener( Plugin plugin ) {
        plugin.getServer().getPluginManager().registerEvents( this, plugin );
    }
    
    /**
     * ブロックを設置した時の処理
     * ポイントブロックを置いた場合は、スコアからポイントをマイナスする
     * ※自然生成か設置かを判断するための試作をしているが機能していない
     *
     * @param event
     */
    @EventHandler
    public void onSignChange( SignChangeEvent event ) {
        Tools.Prt( ChatColor.GOLD + "get Sign Change Event", Tools.consoleMode.max, programCode );

        Player player = event.getPlayer();
        Material material = event.getBlock().getType();
        Tools.Prt( ChatColor.YELLOW + "Material = " + material.name(), Tools.consoleMode.max, programCode );

        //  try {
            Tools.Prt( ChatColor.YELLOW + "Sign Line 1 : " + event.getLine( 0 ), Tools.consoleMode.max, programCode );
            Tools.Prt( ChatColor.YELLOW + "Sign Line 2 : " + event.getLine( 1 ), Tools.consoleMode.max, programCode );
            Tools.Prt( ChatColor.YELLOW + "Sign Line 3 : " + event.getLine( 2 ), Tools.consoleMode.max, programCode );
            Tools.Prt( ChatColor.YELLOW + "Sign Line 4 : " + event.getLine( 3 ), Tools.consoleMode.max, programCode );
            if ( event.getLine( 0 ).equals( "ThisLike" ) ) {
                event.setLine( 0, ChatColor.AQUA + "[ThisLike]" );
                event.setLine( 2, ChatColor.GREEN + player.getName() );
                event.setLine( 3, ChatColor.YELLOW + "イイネ : " + ChatColor.BLUE + "0" );
            }
        //  } catch ( ClassCastException e ) {}
    }

}
