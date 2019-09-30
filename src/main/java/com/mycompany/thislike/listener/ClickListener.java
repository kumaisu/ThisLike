/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.block.Action;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.thislike.database.Database;
import com.mycompany.thislike.database.DatabaseUtil;
import com.mycompany.thislike.database.SignData;
import com.mycompany.thislike.database.LikePlayerData;
import com.mycompany.thislike.OwnerControl;
import static com.mycompany.thislike.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class ClickListener implements Listener {

    public ClickListener( Plugin plugin ) {
        plugin.getServer().getPluginManager().registerEvents( this, plugin );
    }

    /**
     * 看板設置時に文章を記載した時に発生するイベント
     *
     * @param event
     */
    @EventHandler
    public void onClick( PlayerInteractEvent event ) {
        Tools.Prt( ChatColor.GOLD + "get Click Event", Tools.consoleMode.max, programCode );

        if ( event.getAction() != Action.RIGHT_CLICK_BLOCK ) return;

        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        Material material = clickedBlock.getType();
        Tools.Prt( "Material = " + material.name(), Tools.consoleMode.max, programCode);

        if ( !material.name().contains( "SIGN" ) ) { return; }

        String SignLOC = DatabaseUtil.makeID( clickedBlock.getLocation() );
        String SignWorld = clickedBlock.getLocation().getWorld().getName();
        Tools.Prt( ChatColor.YELLOW + "Sign LOC = " + SignLOC + " : " + SignWorld, Tools.consoleMode.full, programCode );
        if ( SignData.GetSQL( SignLOC, SignWorld ) ) {
            Sign sign = (Sign) clickedBlock.getState();

            if ( Database.OwnerName.equals( player.getName() ) ) {
                OwnerControl.loc.put( player.getUniqueId(), clickedBlock.getLocation() );
                OwnerControl.printLiker( player, Database.ID, sign.getLine( 1 ) );
                return;
            }

            Tools.Prt( ChatColor.YELLOW + "Sign Line 1 : " + sign.getLine( 0 ), Tools.consoleMode.max, programCode );
            Tools.Prt( ChatColor.YELLOW + "Sign Line 2 : " + sign.getLine( 1 ), Tools.consoleMode.max, programCode );
            Tools.Prt( ChatColor.YELLOW + "Sign Line 3 : " + sign.getLine( 2 ), Tools.consoleMode.max, programCode );
            Tools.Prt( ChatColor.YELLOW + "Sign Line 4 : " + sign.getLine( 3 ), Tools.consoleMode.max, programCode );

            if ( LikePlayerData.hasSQL( Database.ID, player ) ) {
                //  既にイイネしてるので解除処理
                SignData.subLike( Database.ID );
                LikePlayerData.DelPlayerSQL( Database.ID, player );
                Tools.Prt( player,
                    sign.getLine( 2 ) +
                    ChatColor.LIGHT_PURPLE + "さんの" +
                    ChatColor.YELLOW + "『" + sign.getLine( 1 ) + "』" +
                    ChatColor.LIGHT_PURPLE + "から" +
                    ChatColor.AQUA + "イイネ" +
                    ChatColor.LIGHT_PURPLE + "をやめました",
                    Tools.consoleMode.full, programCode
                );
            } else {
                //  イイネ処理
                SignData.incLike( Database.ID );
                LikePlayerData.AddSQL( player, Database.ID );
                Tools.Prt( player,
                    sign.getLine( 2 ) + "さんの" +
                    ChatColor.YELLOW + "『" + sign.getLine( 1 ) + "』" +
                    ChatColor.GREEN + "に" +
                    ChatColor.AQUA + "イイネ" +
                    ChatColor.GREEN + "しました",
                    Tools.consoleMode.full, programCode
                );
            }

            //  看板内容更新
            SignData.GetSQL( SignLOC, SignWorld );
            sign.setLine( 3, ChatColor.YELLOW + "イイネ : " + ChatColor.BLUE + Database.LikeNum );
            sign.update();
        }
    }
}
