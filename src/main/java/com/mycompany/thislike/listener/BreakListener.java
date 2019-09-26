/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author sugichan
 */
public class BreakListener implements Listener {

    public BreakListener( Plugin plugin ) {
        plugin.getServer().getPluginManager().registerEvents( this, plugin );
    }

}
