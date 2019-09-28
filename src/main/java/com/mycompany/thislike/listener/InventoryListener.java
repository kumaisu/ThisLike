/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.listener;

import com.mycompany.kumaisulibraries.Tools;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import com.mycompany.thislike.OwnerControl;
import static com.mycompany.thislike.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class InventoryListener implements Listener {

    /**
     *
     * @param plugin
     */
    public InventoryListener( Plugin plugin ) {
        plugin.getServer().getPluginManager().registerEvents( this, plugin );
    }
    
    /**
     * クリックされた時の処理
     *
     * @param event 
     */
    @EventHandler
    public void onInventoryClick( InventoryClickEvent event ) {
        Tools.Prt( ChatColor.GOLD + "get Inventory Click Event", Tools.consoleMode.max, programCode );
        Player player = ( Player ) event.getWhoClicked();
        if ( !event.getInventory().equals( OwnerControl.inv.get( player.getUniqueId() ) ) ) return;
        event.setCancelled( true );
    }

    /**
     * インベントリを閉じた時の処理
     *
     * @param event
     */
    @EventHandler
    public void onInventoryClose( InventoryCloseEvent event ) {
        Tools.Prt( ChatColor.GOLD + "get Inventory Close Event", Tools.consoleMode.max, programCode );
        Player player = ( Player ) event.getPlayer();
        if ( event.getInventory().equals( OwnerControl.inv.get( player.getUniqueId() ) ) ) {
            OwnerControl.inv.remove( player.getUniqueId() );
        }
    }
}
