/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import com.mycompany.thislike.database.Database;
import com.mycompany.thislike.database.DatabaseUtil;
import com.mycompany.thislike.database.SignData;
import com.mycompany.thislike.database.LikePlayerData;
import com.mycompany.thislike.OwnerControl;
import com.mycompany.kumaisulibraries.Tools;
import static com.mycompany.thislike.config.Config.programCode;
import org.bukkit.block.Sign;

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
        if ( event.getCurrentItem().getItemMeta() == null ) return;
        if ( event.getCurrentItem().getItemMeta().getDisplayName().contains("Remove Sign")) {
            event.getWhoClicked().closeInventory();

            String SignLOC = DatabaseUtil.makeID( OwnerControl.loc.get( player.getUniqueId() ) );
            String SignWorld = OwnerControl.loc.get( player.getUniqueId() ).getWorld().getName();
            Tools.Prt( ChatColor.YELLOW + "Sign LOC = " + SignLOC + " : " + SignWorld, Tools.consoleMode.full, programCode );
            if ( SignData.GetSQL( SignLOC, SignWorld ) ) {
                //  Owner か Admin なら DBから看板削除 & イイネDBをクリアー
                SignData.DelSQL( Database.ID );
                LikePlayerData.DelSQL( Database.ID );
                //  対象看板を破壊する操作※追加予定　暫定的に1行目を赤にして破壊可能にする
                Sign sign = (Sign) OwnerControl.loc.get( player.getUniqueId() ).getBlock().getState();
                sign.setLine( 0, ChatColor.RED + "#[ThisLike]#" );
                sign.update();
                Tools.Prt( player,
                    ChatColor.LIGHT_PURPLE + "イイネ看板を無効にしました。" +
                    ChatColor.GREEN + "破壊可能です",
                    Tools.consoleMode.max, programCode
                );
            }
        }
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
