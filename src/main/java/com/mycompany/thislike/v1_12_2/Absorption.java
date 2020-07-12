/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.v1_12_2;

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
     * @param makeIcon
     * @return 
     */
    public static ItemStack getPlayerHead( String target, List<String> Lore, boolean makeIcon ) {
        SkullMeta skull;
        try {
            //  1.14.4
            skull = ( SkullMeta ) Bukkit.getItemFactory().getItemMeta( Material.valueOf( "PLAYER_HEAD" ) );
        } catch( Exception e ) {
            //  1.12.2
            skull = ( SkullMeta ) Bukkit.getItemFactory().getItemMeta( Material.valueOf( "SKULL_ITEM" ) );
        }
        
        if ( makeIcon ) {
            skull.setOwningPlayer( Bukkit.getOfflinePlayer( target ) );
            //skull.setOwner( target );
        }
        skull.setDisplayName( target );
        skull.setLore( Lore );
        ItemStack RetItem;
        try {
            //  1.14.4
            RetItem = new ItemStack( Material.valueOf( "PLAYER_HEAD" ), 1 );
        } catch( Exception e ) {
            //  1.12.2
            if ( makeIcon ) {
                RetItem = new ItemStack( Material.valueOf( "SKULL_ITEM" ), 1, ( byte ) SkullType.PLAYER.ordinal() );
            } else {
                RetItem = new ItemStack( Material.valueOf( "SKULL_ITEM" ), 1 );
            }
        }
        RetItem.setItemMeta( skull );
        return RetItem;
    }

    public static ItemStack test() {
        SkullMeta skull = ( SkullMeta ) Bukkit.getItemFactory().getItemMeta( Material.PLAYER_HEAD );
        skull.setOwningPlayer( Bukkit.getOfflinePlayer( "Kumaisu" ) );
        skull.setDisplayName( "Kumaisu" );
        ItemStack RetItem = new ItemStack( Material.PLAYER_HEAD, 1 );
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
/*
    public ItemStack applySkullTexture() {
        SkullMeta skull = ( SkullMeta ) Bukkit.getItemFactory().getItemMeta( Material.valueOf( "PLAYER_HEAD" ) );

        skull.setOwningPlayer( target );
        skull.setOwner( target );
        skull.setDisplayName( target );
        skull.setLore( Lore );
        ItemStack RetItem;
        try {
            //  1.14.4
            RetItem = new ItemStack( Material.valueOf( "PLAYER_HEAD" ), 1 );
        } catch( Exception e ) {
            //  1.12.2
            if ( makeIcon ) {
                RetItem = new ItemStack( Material.valueOf( "SKULL_ITEM" ), 1, ( byte ) SkullType.PLAYER.ordinal() );
            } else {
                RetItem = new ItemStack( Material.valueOf( "SKULL_ITEM" ), 1 );
            }
        }





        ItemStack item = null;
        // = CraftItemStack.asNMSCopy(new ItemStack(Material.PLAYER_HEAD, 1));
        NBTTagCompound tag;
        if ( item.hasTag() ) {
            tag = item.getTag();
        } else {
            tag = new NBTTagCompound();
        }
        NBTTagCompound skullOwner = new NBTTagCompound();
        NBTTagCompound properties = new NBTTagCompound();
        NBTTagList textures = new NBTTagList();
        NBTTagCompound texture = new NBTTagCompound();

        texture.setString("Value", "ewogICJ0aW1lc3RhbXAiIDogMTU4OTc4MjY3NzIzMiwKICAicHJvZmlsZUlkIiA6ICI4OTJkMDE2ZGQ2MTc0MjgzYjRjOGJlZTRlZTkwMjNlMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJLdW1haXN1IiwKICAidGV4dHVyZXMiIDogewogICA=" );
        textures.add(texture);
        properties.set("textures", textures);
        skullOwner.set("Properties", properties);
        tag.set("SkullOwner", skullOwner);

        item.setTag( tag );
        return CraftItemStack.asBukkitCopy( item );
    }

}
*/
