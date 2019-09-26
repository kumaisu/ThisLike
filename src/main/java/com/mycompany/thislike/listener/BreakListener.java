/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.listener;

import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import com.mycompany.kumaisulibraries.Tools;
import static com.mycompany.thislike.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class BreakListener implements Listener {

    public BreakListener( Plugin plugin ) {
        plugin.getServer().getPluginManager().registerEvents( this, plugin );
    }

    /**
     * ブロックが破壊された時の処理
     *
     * @param event
     */
    @EventHandler
    public void onBlockBreak( BlockBreakEvent event ) {
        Tools.Prt( ChatColor.GOLD + "get BlockBreak Event", Tools.consoleMode.max, programCode );

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material material = block.getType();
        Tools.Prt( "Material = " + material.name(), Tools.consoleMode.max, programCode);

        try {
            Sign sign = (Sign) block.getState();

            if ( !sign.getLine( 0 ).equals( ChatColor.AQUA + "[ThisLike]" ) ) { return; }

            Tools.Prt( ChatColor.GOLD + "get [ThisLike] Sign", Tools.consoleMode.max, programCode );

            //  DBからデータ取得

            //  Owner 以外ならば event.setCancelled( true );

            Tools.Prt( ChatColor.GOLD + "Owner or Admin Delete process", Tools.consoleMode.max, programCode );

            //  Owner か Admin なら DBから看板削除 & イイネDBをクリアー

        } catch ( ClassCastException e ) {}
    }
}
