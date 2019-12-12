/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.DyeColor;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import com.mycompany.thislike.config.Config;

/**
 *
 * @author sugichan
 */
public class Absorption {
    
    public static int getPlayTime( Player player ) {
        try {
            return player.getStatistic( Statistic.valueOf( "PLAY_ONE_MINUTE" ) );
        } catch( IllegalArgumentException e ) {
            return player.getStatistic( Statistic.valueOf( "PLAY_ONE_TICK" ) );
        }
    }

    /**
     * プレイヤーHead取得
     *
     * @param target
     * @param Lore
     * @return 
     */
    public static ItemStack getPlayerHead( String target, List<String> Lore ) {
        SkullMeta skull;
        try {
            //  1.14.4
            skull = ( SkullMeta ) Bukkit.getItemFactory().getItemMeta( Material.valueOf( "PLAYER_HEAD" ) );
        } catch( Exception e ) {
            //  1.12.2
            skull = ( SkullMeta ) Bukkit.getItemFactory().getItemMeta( Material.valueOf( "SKULL_ITEM" ) );
        }
        skull.setOwner( target );
        skull.setDisplayName( target );
        skull.setLore( Lore );
        ItemStack RetItem;
        try {
            //  1.14.4
            RetItem = new ItemStack( Material.valueOf( "PLAYER_HEAD" ), 1 );
        } catch( Exception e ) {
            //  1.12.2
            RetItem = new ItemStack( Material.valueOf( "SKULL_ITEM" ), 1, ( byte ) SkullType.PLAYER.ordinal() );
        }
        RetItem.setItemMeta( skull );
        return RetItem;
    }

    public static ItemStack Like() {
        //  イイネ解除
        ItemStack Like;
        try {
            //  1.14.4
            Like = new ItemStack( Material.valueOf( "BLUE_WOOL" ), 1 );
        } catch( Exception e ) {
            //  1.12.2
            Like = new Wool( DyeColor.BLUE ).toItemStack( 1 );
        }
        ItemMeta lm = Like.getItemMeta();
        lm.setDisplayName( Config.like );
        Like.setItemMeta( lm );
        return Like;
    }

    public static ItemStack Unlike() {
        //  イイネ解除
        ItemStack Unlike;
        try {
            //  1.14.4
            Unlike = new ItemStack( Material.valueOf( "RED_WOOL" ), 1 );
        } catch( Exception e ) {
            //  1.12.2
            Unlike = new Wool( DyeColor.RED ).toItemStack( 1 );
        }
        ItemMeta um = Unlike.getItemMeta();
        um.setDisplayName( Config.unlike );
        Unlike.setItemMeta( um );
        return Unlike;
    }
}
