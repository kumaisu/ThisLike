/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;
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
import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.thislike.Absorption;
import com.mycompany.thislike.config.Config;
import com.mycompany.thislike.database.Database;
import com.mycompany.thislike.database.LikePlayerData;
import static com.mycompany.thislike.config.Config.programCode;

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
    */
    public static void printLiker( Player player, int ID ) {
        Tools.Prt( ChatColor.RED + "Inventory Making", Tools.consoleMode.max, programCode );

        Map< String, Date > liker = LikePlayerData.listSQL( ID );
        //  boolean hasOwner = Database.OwnerName.equals( player.getName() );
        boolean hasOwner = Database.OwnerUUID.equals( player.getUniqueId() );

        int slot = liker.size() + ( ( ( liker.size() % 9 ) == 0 ) ? 0 : ( 9 - ( liker.size() % 9 ) ) ) + 9;
        if ( slot>54 ) { slot = 54; }

        Inventory TempInv;
        TempInv = Bukkit.createInventory( null, slot, Config.ReplaceString( Config.InventoryTitle + " (" + liker.size() + ")" ) );

        if ( hasOwner || player.hasPermission( "ThisLike.admin" ) ) {
            //  Remove Icon 作成
            ItemStack DelIcon = new ItemStack( Material.BARRIER, 1 );
            ItemMeta DelMeta = Bukkit.getItemFactory().getItemMeta( Material.BARRIER );
            DelMeta.setDisplayName( ChatColor.WHITE + "Remove" );
            //  List<String> DelLore = Arrays.asList( ChatColor.RED + "いいね看板を", ChatColor.RED + "削除します" );
            DelMeta.setLore( Config.RemoveSignLore );
            DelIcon.setItemMeta( DelMeta );
            TempInv.setItem( slot - 1, DelIcon );
        }

        if ( player.hasPermission( "ThisLike.admin" ) ) {
            //  Update Icon 作成
            ItemStack UpIcon = new ItemStack( Material.END_CRYSTAL, 1 );
            ItemMeta UpMeta = Bukkit.getItemFactory().getItemMeta( Material.END_CRYSTAL );
            UpMeta.setDisplayName( ChatColor.WHITE + "Update" );
            List<String> UpLore = Arrays.asList( ChatColor.AQUA + "Update Sign", ChatColor.AQUA + "Rewrite Infomation" );
            UpMeta.setLore( UpLore );
            UpIcon.setItemMeta( UpMeta );
            TempInv.setItem( slot - 2, UpIcon );
        }

        if (
            ( Database.OwnerName.equals( Config.AdminName ) )
            ||
            ( player.hasPermission( "ThisLike.admin" ) )
            ||
            (
                ( !hasOwner )
                &&
                ( !Database.OwnerIP.equals( player.getAddress().getHostString() ) ) 
            )
        ) {
            //  イイネ設定
            TempInv.setItem( slot - 9, Absorption.Like() );
            //  イイネ解除
            TempInv.setItem( slot - 8, Absorption.Unlike() );
        }

        player.openInventory( TempInv );
        Tools.Prt( ChatColor.GREEN + "Inventory Title", Tools.consoleMode.max, programCode );

        // List 生成 (ソート用)
        List< Map.Entry< String, Date > > entries = new ArrayList<>( liker.entrySet() );
        Collections.sort( entries, ( Entry< String, Date > entry1, Entry< String, Date > entry2 ) -> ( ( Date ) entry2.getValue() ).compareTo( ( Date ) entry1.getValue() ) );
         
        int i = 0;
        SimpleDateFormat ddf = new SimpleDateFormat( "yyyy/MM/dd" );
        SimpleDateFormat tdf = new SimpleDateFormat( "HH:mm:ss" );
        // 内容を表示
        for ( Entry< String, Date > s : entries ) {
            Tools.Prt( ChatColor.AQUA + s.getKey() + " : " + s.getValue(), Tools.consoleMode.full, programCode );
            if ( i<46 ) {
                List< String > Lore = Arrays.asList( ddf.format( s.getValue() ),tdf.format( s.getValue() ) );
                TempInv.setItem( i, Absorption.getPlayerHead( s.getKey(), Lore, Config.MakeHead ) );
                player.updateInventory();
                Tools.Prt( ChatColor.GREEN + "Player Head Inventory Menu " + i + " Done", Tools.consoleMode.max, programCode );
            }
            i++;
        }
        inv.put( player.getUniqueId(), TempInv );
        Tools.Prt( ChatColor.RED + "Inventory Maked finished", Tools.consoleMode.max, programCode );
    }
}
