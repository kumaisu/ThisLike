/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.block.Sign;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import com.mycompany.thislike.database.Database;
import com.mycompany.thislike.database.SignData;
import com.mycompany.thislike.database.LikePlayerData;
import com.mycompany.thislike.control.OwnerControl;
import com.mycompany.thislike.control.LikeControl;
import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.kumaisulibraries.Utility;
import com.mycompany.thislike.config.Config;
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

        Sign sign = (Sign) OwnerControl.loc.get( player.getUniqueId() ).getBlock().getState();

        switch( event.getCurrentItem().getType().name() ) {
            case "BARRIER":
                if ( event.getCurrentItem().getItemMeta().getDisplayName().contains( "Remove" ) ) {
                    event.getWhoClicked().closeInventory();
                    Tools.Prt( ChatColor.YELLOW + player.getName() + " Signloc = " + OwnerControl.loc.get( player.getUniqueId() ).toString(), Tools.consoleMode.full, programCode );
                    if ( SignData.GetSQL( OwnerControl.loc.get( player.getUniqueId() ) ) ) {
                        //  Owner か Admin なら DBから看板削除 & イイネDBをクリアー
                        SignData.DelSQL( Database.ID );
                        LikePlayerData.DelSQL( Database.ID );
                        //  対象看板を破壊する操作※追加予定　暫定的に1行目を赤にして破壊可能にする
                        sign.setLine( 0, ChatColor.RED + "#[ThisLike]#" );
                        sign.update();
                        Tools.Prt( player, Utility.ReplaceString( Config.Remove ), Tools.consoleMode.full, programCode );
                    }
                }
                break;
            case "BLUE_WOOL":
                event.getWhoClicked().closeInventory();
                if ( event.getCurrentItem().getItemMeta().getDisplayName().contains( Config.like ) ) {
                    LikeControl.SetLike( Database.ID, player, sign.getLine( 2 ), sign.getLine( 1 ) );
                }
                break;
            case "RED_WOOL":
                event.getWhoClicked().closeInventory();
                if ( event.getCurrentItem().getItemMeta().getDisplayName().contains( Config.unlike ) ) {
                    LikeControl.SetUnlike( Database.ID, player, sign.getLine( 2 ), sign.getLine( 1 ) );
                }
                break;
            case "WOOL":    // 1.12.2 対応
                Tools.Prt( "WOOL", Tools.consoleMode.max, programCode );
                event.getWhoClicked().closeInventory();
                if ( event.getCurrentItem().getItemMeta().getDisplayName().equals( Config.like ) ) {
                    LikeControl.SetLike( Database.ID, player, sign.getLine( 2 ), sign.getLine( 1 ) );
                }
                if ( event.getCurrentItem().getItemMeta().getDisplayName().equals( Config.unlike ) ) {
                    LikeControl.SetUnlike( Database.ID, player, sign.getLine( 2 ), sign.getLine( 1 ) );
                }
                break;
            default:
                Tools.Prt( event.getCurrentItem().getType().name(), Tools.consoleMode.max, programCode );
        }

        //  看板内容更新
        SignData.GetSQL( OwnerControl.loc.get( player.getUniqueId() ) );
        sign.setLine( 0, Utility.ReplaceString( Config.SignBase.get( 0 ) ) );
        sign.setLine( 3, Utility.ReplaceString( Config.SignBase.get( 3 ) ) + ChatColor.BLUE + Database.LikeNum );
        sign.update();
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
