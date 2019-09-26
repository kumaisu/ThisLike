/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.HandlerList;
import com.mycompany.thislike.config.ConfigManager;
import com.mycompany.thislike.listener.BreakListener;
import com.mycompany.thislike.listener.ClickListener;
import com.mycompany.thislike.listener.PlaceListener;
import com.mycompany.thislike.command.ThislikeCommand;

/**
 *
 * @author sugichan
 */
public class ThisLike extends JavaPlugin {

    private ThisLike instance;

    public static ConfigManager config;
    
    @Override
    @SuppressWarnings( "ResultOfObjectAllocationIgnored" )
    public void onEnable() {
        config = new ConfigManager( this );
        new PlaceListener( this );
        new ClickListener( this );
        new BreakListener( this );
        getCommand( "thislike" ).setExecutor( new ThislikeCommand( this ) );
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll( this );
    }

    @Override
    public void onLoad() {
    }
}
