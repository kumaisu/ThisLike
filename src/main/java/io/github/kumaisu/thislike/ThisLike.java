/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kumaisu.thislike;

import java.sql.SQLException;
import java.util.HashMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.HandlerList;
import io.github.kumaisu.thislike.config.ConfigManager;
import io.github.kumaisu.thislike.listener.LoginListener;
import io.github.kumaisu.thislike.listener.BreakListener;
import io.github.kumaisu.thislike.listener.ClickListener;
import io.github.kumaisu.thislike.listener.PlaceListener;
import io.github.kumaisu.thislike.listener.InventoryListener;
import io.github.kumaisu.thislike.command.ThislikeCommand;
import io.github.kumaisu.thislike.TabComplete.ThisLikeTabComp;
import io.github.kumaisu.thislike.control.OwnerControl;
import io.github.kumaisu.thislike.database.MySQLControl;

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
        try {
            MySQLControl.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        MySQLControl.TableUpdate();
        new LoginListener( this );
        new PlaceListener( this );
        new ClickListener( this );
        new BreakListener( this );
        new InventoryListener( this );
        getCommand( "thislike" ).setExecutor( new ThislikeCommand( this ) );
        getCommand( "thislike" ).setTabCompleter( new ThisLikeTabComp() );
        OwnerControl.inv = new HashMap<>();
        OwnerControl.loc = new HashMap<>();
    }

    @Override
    public void onDisable() {
        super.onDisable(); //To change body of generated methods, choose Tools | Templates.
        HandlerList.unregisterAll( this );
        //  MySQLControl.disconnect();
    }

    @Override
    public void onLoad() {
        super.onLoad(); //To change body of generated methods, choose Tools | Templates.
    }
}
