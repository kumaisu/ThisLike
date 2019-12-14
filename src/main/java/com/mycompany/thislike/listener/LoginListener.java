/*
 *  Copyright (c) 2019 sugichan. All rights reserved.
 */
package com.mycompany.thislike.listener;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;
import java.util.Date;
import java.net.UnknownHostException;
import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.thislike.config.Config;
import com.mycompany.thislike.control.OwnerControl;

/**
 *
 * @author sugichan
 */
public class LoginListener implements Listener {

    private Date date;
    private final Plugin plugin;

    /**
     *
     * @param plugin
     */
    public LoginListener( Plugin plugin ) {
        plugin.getServer().getPluginManager().registerEvents( this, plugin );
        this.plugin = plugin;
    }

    /**
     * プレイヤーがログインを成功すると発生するイベント
     * ここでプレイヤーに対して、様々な処理を実行する
     *
     * @param event
     * @throws UnknownHostException
     */
    @EventHandler( priority = EventPriority.HIGH )
    public void onPlayerLogin( PlayerJoinEvent event ) throws UnknownHostException {
        Tools.Prt( "onPlayerLogin process", Tools.consoleMode.max, Config.programCode );
        Player player = event.getPlayer();
        OwnerControl.inv.put( player.getUniqueId(), null );
        OwnerControl.loc.put( player.getUniqueId(), null );
    }

    /**
     * プレイヤーがログアウトした時に発生するイベント
     *
     * @param event
     */
    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event ) {
        Tools.Prt( "onPlayerQuit process", Tools.consoleMode.max, Config.programCode );
        Player player = event.getPlayer();
        OwnerControl.inv.remove( player.getUniqueId() );
        OwnerControl.loc.remove( player.getUniqueId() );
    }
}