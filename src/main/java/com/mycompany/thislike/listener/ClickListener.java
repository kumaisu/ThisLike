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

        try {
            Sign sign = (Sign) clickedBlock.getState();
            if ( sign.getLine( 0 ).equals( ChatColor.AQUA + "[ThisLike]" ) ) {

                //  Owner ならば、コマンド処理　＞　OwnerControl.java

                //  DB参照　無ければ以下処理　有れば return;

                Tools.Prt( ChatColor.YELLOW + "Sign Line 1 : " + sign.getLine( 0 ), Tools.consoleMode.max, programCode );
                Tools.Prt( ChatColor.YELLOW + "Sign Line 2 : " + sign.getLine( 1 ), Tools.consoleMode.max, programCode );
                Tools.Prt( ChatColor.YELLOW + "Sign Line 3 : " + sign.getLine( 2 ), Tools.consoleMode.max, programCode );
                Tools.Prt( ChatColor.YELLOW + "Sign Line 4 : " + sign.getLine( 3 ), Tools.consoleMode.max, programCode );

                //  DB追加処理　イイネ処理
                //  該当なしならイイネ処理
                //  該当ありならイイネ削除処理

                Tools.Prt( player,
                    sign.getLine( 2 ) + "さんの" +
                    ChatColor.YELLOW + "『" + sign.getLine( 1 ) + "』" +
                    ChatColor.GREEN + "に" +
                    ChatColor.AQUA + "イイネ" +
                    ChatColor.GREEN + "しました",
                    Tools.consoleMode.full, programCode
                );

                //  看板内容更新
                sign.setLine( 3, ChatColor.YELLOW + "イイネ : " + ChatColor.BLUE + "1" );
                sign.update();
            }
        } catch ( ClassCastException e ) {}
    }
}
