/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike;

import com.mycompany.kumaisulibraries.Tools;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.ItemMeta;
import com.mycompany.thislike.config.Config;
import static com.mycompany.thislike.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class Absorption {
    
    public static int getPlayTime( Player player ) {
        return player.getStatistic( Statistic.PLAY_ONE_MINUTE );
    }

    /**
     * プレイヤーHead取得
     *
     * @param target
     * @param Lore
     * @param makeIcon
     * @return 
     */
    public static ItemStack getPlayerHead( String target, List<String> Lore, boolean makeIcon ) {
        ItemStack RetItem = new ItemStack( Material.PLAYER_HEAD, 1 );
        SkullMeta skull = ( SkullMeta ) RetItem.getItemMeta();

        if ( makeIcon ) {
            Tools.Prt( "Get Online : " + target, Tools.consoleMode.max, programCode );
            skull.setOwner( target );
        }

        skull.setDisplayName( target );
        skull.setLore( Lore );

        RetItem.setItemMeta( skull );
        return RetItem;
    }

    public static ItemStack Like() {
        //  イイネ解除
        ItemStack Like = new ItemStack( Material.BLUE_WOOL, 1 );
        ItemMeta lm = Like.getItemMeta();
        lm.setDisplayName( Config.like );
        Like.setItemMeta( lm );
        return Like;
    }

    public static ItemStack Unlike() {
        //  イイネ解除
        ItemStack Unlike = new ItemStack( Material.RED_WOOL, 1 );
        ItemMeta um = Unlike.getItemMeta();
        um.setDisplayName( Config.unlike );
        Unlike.setItemMeta( um );
        return Unlike;
    }
}
