/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.listener;

import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.thislike.database.Database;
import com.mycompany.thislike.database.SignData;
import com.mycompany.thislike.database.DatabaseUtil;
import static com.mycompany.thislike.config.Config.programCode;
import org.bukkit.Location;
import com.mycompany.thislike.database.LikePlayerData;

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
        Tools.Prt( "Material = " + material.name(), Tools.consoleMode.max, programCode );

        if ( !material.name().contains( "SIGN" ) ) { return; }
        
        //  DBからデータ取得
        String SignLOC = DatabaseUtil.makeID( block.getLocation() );
        String SignWorld = block.getLocation().getWorld().getName();
        Tools.Prt( ChatColor.YELLOW + "Sign LOC = " + SignLOC + " : " + SignWorld, Tools.consoleMode.max, programCode );
        if ( SignData.GetSQL( SignLOC, SignWorld ) ) {
            Tools.Prt( ChatColor.GOLD + "get [" + ChatColor.AQUA + "ThisLike"+ ChatColor.GOLD + "] Sign", Tools.consoleMode.max, programCode );

            Tools.Prt( player.getUniqueId().toString() + " : " + Database.OwnerUUID.toString(), Tools.consoleMode.max, programCode );

            //  設定された看板であれば何人も壊せない仕様
            //  将来的には、OPまたはADMINは壊せるように変更
            Tools.Prt( "Break Cancelled", Tools.consoleMode.max, programCode );
            event.setCancelled( true );

            /*
            return;
                
            if ( player.getUniqueId().equals( Database.OwnerUUID ) || player.hasPermission( "ThisLike.admin" ) ) {
                Location loc = block.getLocation();
                Block checkBlock = loc.getBlock();
                Tools.Prt( ChatColor.GREEN + "Check Block Type : " + checkBlock.getType().name(), Tools.consoleMode.max, programCode );

                Tools.Prt( ChatColor.GOLD + "Owner or Admin Delete process", Tools.consoleMode.max, programCode );

                //  Owner か Admin なら DBから看板削除 & イイネDBをクリアー
                SignData.DelSQL( Database.ID );
                LikePlayerData.DelSQL( Database.ID );
            }
            */
        }
    }
}
