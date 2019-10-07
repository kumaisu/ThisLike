/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.control;

import java.util.UUID;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import com.mycompany.thislike.database.LikePlayerData;
import com.mycompany.kumaisulibraries.Tools;
import static com.mycompany.thislike.config.Config.programCode;

import org.bukkit.SkullType;
import org.bukkit.material.Wool;
import org.bukkit.DyeColor;

/**
 *
 * @author sugichan
 */
public class OwnerControl {

    public static Map< UUID, Inventory > inv;
    public static Map< UUID, Location > loc;

    /**
     * イイネのプレイヤー一覧表示 
     *
     * @param player
     * @param ID
     * @param Title
     * @param hasOwner
    */
    public static void printLiker( Player player, int ID, String Title, boolean hasOwner ) {
        Map< String, Date > liker = LikePlayerData.listSQL( ID );

        int slot = liker.size() + ( 9 - ( liker.size() % 9 ) ) + 9;
        if ( slot>54 ) { slot = 54; }

        Inventory TempInv;
        TempInv = Bukkit.createInventory( null, slot, "『" + Title + "』いいね" );

        if ( hasOwner ) {
            //  Remove Icon 作成
            ItemStack DelIcon = new ItemStack( Material.BARRIER, 1 );
            ItemMeta DelMeta = Bukkit.getItemFactory().getItemMeta(Material.BARRIER);
            DelMeta.setDisplayName( "Remove" );
            List<String> DelLore = Arrays.asList( ChatColor.RED + "いいね看板を", ChatColor.RED + "削除します" );
            DelMeta.setLore( DelLore );
            DelIcon.setItemMeta( DelMeta );
            TempInv.setItem( slot - 1, DelIcon );
        }

        //  イイネ解除
        ItemStack Unlike = new Wool( DyeColor.RED ).toItemStack( 1 );
        ItemMeta um = Unlike.getItemMeta();
        um.setDisplayName( "解除" );
        Unlike.setItemMeta( um );
        TempInv.setItem( slot - 8, Unlike );
        
        //  イイネ解除
        ItemStack Like = new Wool( DyeColor.BLUE ).toItemStack( 1 );
        ItemMeta lm = Like.getItemMeta();
        lm.setDisplayName( "いいね" );
        Like.setItemMeta( lm );
        TempInv.setItem( slot - 9, Like );
        
        liker.keySet().forEach( ( key ) -> {
            Tools.Prt( ChatColor.GREEN + key + " : " + ChatColor.WHITE + liker.get( key ), Tools.consoleMode.max, programCode );
            SimpleDateFormat ddf = new SimpleDateFormat( "yyyy/MM/dd" );
            SimpleDateFormat tdf = new SimpleDateFormat( "HH:mm:ss" );
            List< String > Lore = Arrays.asList( ddf.format( liker.get( key ) ),tdf.format( liker.get( key ) ) );
            TempInv.addItem( getPlayerHead( player, key, Lore ) );
        } );

        player.openInventory( TempInv );
        inv.put( player.getUniqueId(), TempInv );
    }

    /**
     * プレイヤーHead取得
     * 
     * @param player
     * @param target
     * @param Lore
     * @return 
     */
    public static ItemStack getPlayerHead( Player player, String target, List<String> Lore ) {
        SkullMeta skull = ( SkullMeta ) Bukkit.getItemFactory().getItemMeta( Material.SKULL_ITEM );         //  1.12.2
        //  SkullMeta skull = ( SkullMeta ) Bukkit.getItemFactory().getItemMeta( Material.PLAYER_HEAD );
        skull.setOwner( target );
        skull.setDisplayName( target );
        skull.setLore( Lore );
        ItemStack i = new ItemStack( Material.SKULL_ITEM, 1, ( byte ) SkullType.PLAYER.ordinal() );         //  1.12.2
        //  ItemStack i = new ItemStack( Material.PLAYER_HEAD, 1 );
        i.setItemMeta( skull );
        return i;
    }
}
