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
import com.mycompany.thislike.database.SignData;
import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.kumaisulibraries.Utility;
import com.mycompany.thislike.config.Config;
import com.mycompany.thislike.database.Database;
import com.mycompany.thislike.control.DynmapControl;
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
     * 看板設置時に文章を記載した時に発生するイベント
     *
     * @param event
     */
    @EventHandler
    public void onSignChange( SignChangeEvent event ) {
        Tools.Prt( ChatColor.GOLD + "get Sign Change Event", Tools.consoleMode.max, programCode );

        Player player = event.getPlayer();
        Material material = event.getBlock().getType();
        String Title = event.getLine( 1 );
        Tools.Prt( ChatColor.YELLOW + "Material = " + material.name(), Tools.consoleMode.max, programCode );

        Tools.Prt( ChatColor.YELLOW + "Sign Line 1 : " + event.getLine( 0 ), Tools.consoleMode.max, programCode );
        Tools.Prt( ChatColor.YELLOW + "Sign Line 2 : " + Title, Tools.consoleMode.max, programCode );
        Tools.Prt( ChatColor.YELLOW + "Sign Line 3 : " + event.getLine( 2 ), Tools.consoleMode.max, programCode );
        Tools.Prt( ChatColor.YELLOW + "Sign Line 4 : " + event.getLine( 3 ), Tools.consoleMode.max, programCode );

        if ( event.getLine( 0 ).equals( Config.SignSetKey ) ) {
            event.setLine( 0, Utility.ReplaceString( Config.SignBase.get( 0 ) ) );
            event.setLine( 1, Utility.ReplaceString( Config.SignBase.get( 1 ) ) + Title );
            event.setLine( 2, Utility.ReplaceString( Config.SignBase.get( 2 ), player.getName() ) );
            event.setLine( 3, Utility.ReplaceString( Config.SignBase.get( 3 ) ) + ChatColor.BLUE + "0" );
            SignData.AddSQL( player, event.getBlock().getLocation() );
            if( Config.OnDynmap ) {
                SignData.GetSQL( event.getBlock().getLocation() );
                DynmapControl.SetDynmapMarker( Database.ID, Title, event.getBlock().getLocation() );
            }
        }
    }
}
