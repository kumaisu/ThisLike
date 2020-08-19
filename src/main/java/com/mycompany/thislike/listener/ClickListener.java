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
import com.mycompany.thislike.PlayerHeadTimer;
import static com.mycompany.thislike.ThisLike.TaskFlag;
import com.mycompany.thislike.config.Config;
import com.mycompany.thislike.database.Database;
import com.mycompany.thislike.database.SignData;
import com.mycompany.thislike.database.LikePlayerData;
import com.mycompany.thislike.control.OwnerControl;
import com.mycompany.thislike.control.LikeControl;
import static com.mycompany.thislike.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class ClickListener implements Listener {
    
    private final Plugin plg;

    public ClickListener( Plugin plugin ) {
        plugin.getServer().getPluginManager().registerEvents( this, plugin );
        plg = plugin;
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

        Sign sign = ( Sign ) clickedBlock.getState();

        Tools.Prt( ChatColor.YELLOW + player.getName() + " SignLOC = " + clickedBlock.getLocation().toString(), Tools.consoleMode.max, programCode );
        String CheckSign = Config.ReplaceString( Config.SignBase.get( 0 ) );
        Tools.Prt( 
            ChatColor.WHITE + "[" +
            sign.getLine( 0 ) +
            ChatColor.WHITE + "] : [" +
            CheckSign +
            ChatColor.WHITE + "]",
            Tools.consoleMode.full, Config.programCode
        );
        if ( !sign.getLine( 0 ).equals( CheckSign ) ) { return; }

        if ( SignData.GetSignLoc( clickedBlock.getLocation() ) ) {
            for ( int i = 0; i < 4; i++ ) {
                Tools.Prt( ChatColor.YELLOW + "Sign Line " + i + " : " + sign.getLine( i ), Tools.consoleMode.max, programCode );
            }
            event.setCancelled( true );

            if ( ( !Database.OwnerName.equals( player.getName() ) ) && ( !LikePlayerData.hasSQL( Database.ID, player ) ) ) {
                boolean Like = true;
                if ( !Database.OwnerName.equals( Config.AdminName ) ) {
                    if ( Database.OwnerIP.equals( player.getAddress().getHostString() ) ) {
                        Tools.Prt( player, ChatColor.RED + "同一IPからのイイネは出来ません", Tools.consoleMode.full, programCode );
                        Like = false;
                    }
                    if ( ( Like ) && ( player.getUniqueId().equals( Database.OwnerUUID ) ) ) {
                        Tools.Prt( player, ChatColor.RED + "同一UUIDからのイイネは出来ません", Tools.consoleMode.full, programCode );
                        Like = false;
                    }
                }
                if ( Like ) { LikeControl.SetLike( Database.ID, player ); }
            } else {
                //  LikeControl.SetUnlike( Database.ID, player );
                OwnerControl.loc.put( player.getUniqueId(), clickedBlock.getLocation() );
                OwnerControl.printLiker( player, Database.ID );
                TaskFlag = true;
                new PlayerHeadTimer( plg, player ).runTaskLater( plg, 0 );
                //new PlayerHeadTimer( plg. player ).runTaskAsynchronously( plg, 0 );
            }

            //  看板内容更新
            SignData.GetSignLoc( clickedBlock.getLocation() );

            for ( int i = 0; i < 4; i++ ) {
                String SignMsg = Config.ReplaceString( Config.SignBase.get( i ) );
                Tools.Prt( ChatColor.YELLOW + "Rewrite Sign " + i + " : " + SignMsg, Tools.consoleMode.max, programCode );
                sign.setLine( i, SignMsg );
            }
            sign.update();
        }
    }
}
