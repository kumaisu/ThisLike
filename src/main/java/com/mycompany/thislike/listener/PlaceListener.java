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
import com.mycompany.thislike.config.Config;
import com.mycompany.thislike.database.Database;
import com.mycompany.thislike.database.SignData;
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
        for ( int i = 0; i < 4; i++ ) { Tools.Prt( ChatColor.YELLOW + "Old Sign " + i + " : " + event.getLine( i ), Tools.consoleMode.max, programCode ); }

        if ( event.getLine( 0 ).equals( Config.SignSetKey ) ) {
            SignData.AddSQL( player, event.getBlock().getLocation(), Title );
            for ( int i = 0; i < 4; i++ ) {
                String SignMsg = Config.ReplaceString( Config.SignBase.get( i ) );
                Tools.Prt( ChatColor.YELLOW + "New Sign " + i + " : " + SignMsg, Tools.consoleMode.max, programCode );
                event.setLine( i, SignMsg );
            }
            if( Config.OnDynmap ) {
                SignData.GetSQL( event.getBlock().getLocation() );
                DynmapControl.SetDynmapMarker( Database.ID, Title, event.getBlock().getLocation() );
            }
        }
    }
}
