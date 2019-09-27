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
import com.mycompany.thislike.database.MySQLControl;

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
        MySQLControl.connect();
        MySQLControl.TableUpdate();
        new PlaceListener( this );
        new ClickListener( this );
        new BreakListener( this );
        getCommand( "thislike" ).setExecutor( new ThislikeCommand( this ) );
    }

    @Override
    public void onDisable() {
        super.onDisable(); //To change body of generated methods, choose Tools | Templates.
        HandlerList.unregisterAll( this );
        MySQLControl.disconnect();
    }

    @Override
    public void onLoad() {
        super.onLoad(); //To change body of generated methods, choose Tools | Templates.
    }
}
