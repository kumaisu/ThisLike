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
        Map< String, Date > liker = LikePlayerData.listSQL( ID );

        boolean hasOwner = Database.OwnerName.equals( player.getName() );
        boolean hasPerm = ( player.isOp() || player.hasPermission( "ThisLike.admin" ) );
        
        int slot = liker.size() + ( ( ( liker.size() % 9 ) == 0 ) ? 0 : ( 9 - ( liker.size() % 9 ) ) ) + 9;
        if ( slot>54 ) { slot = 54; }

        Inventory TempInv;
        TempInv = Bukkit.createInventory( null, slot, Config.ReplaceString( Config.InventoryTitle ) );

        if ( hasOwner || hasPerm ) {
            //  Remove Icon 作成
            ItemStack DelIcon = new ItemStack( Material.BARRIER, 1 );
            ItemMeta DelMeta = Bukkit.getItemFactory().getItemMeta( Material.BARRIER );
            DelMeta.setDisplayName( ChatColor.WHITE + "Remove" );
            //  List<String> DelLore = Arrays.asList( ChatColor.RED + "いいね看板を", ChatColor.RED + "削除します" );
            DelMeta.setLore( Config.RemoveSignLore );
            DelIcon.setItemMeta( DelMeta );
            TempInv.setItem( slot - 1, DelIcon );
        }

        if ( hasPerm ) {
            //  Remove Icon 作成
            ItemStack UpIcon = new ItemStack( Material.END_CRYSTAL, 1 );
            ItemMeta UpMeta = Bukkit.getItemFactory().getItemMeta( Material.END_CRYSTAL );
            UpMeta.setDisplayName( ChatColor.WHITE + "Update" );
            List<String> UpLore = Arrays.asList( ChatColor.AQUA + "Update Sign", ChatColor.AQUA + "Rewrite Infomation" );
            UpMeta.setLore( UpLore );
            UpIcon.setItemMeta( UpMeta );
            TempInv.setItem( slot - 2, UpIcon );
        }

        //  イイネ解除
        TempInv.setItem( slot - 8, Absorption.Unlike() );

        //  イイネ解除
        TempInv.setItem( slot - 9, Absorption.Like() );

       // List 生成 (ソート用)
        List< Map.Entry< String, Date > > entries = new ArrayList<>( liker.entrySet() );
        Collections.sort( entries, ( Entry< String, Date > entry1, Entry< String, Date > entry2 ) -> ( ( Date ) entry2.getValue() ).compareTo( ( Date ) entry1.getValue() ) );
         
        // 内容を表示
        for ( Entry< String, Date > s : entries ) {
            Tools.Prt( ChatColor.GREEN + s.getKey() + " : " + ChatColor.WHITE + s.getValue(), Tools.consoleMode.max, programCode );
            SimpleDateFormat ddf = new SimpleDateFormat( "yyyy/MM/dd" );
            SimpleDateFormat tdf = new SimpleDateFormat( "HH:mm:ss" );
            List< String > Lore = Arrays.asList( ddf.format( s.getValue() ),tdf.format( s.getValue() ) );
            TempInv.addItem( Absorption.getPlayerHead( player, s.getKey(), Lore ) );
        }

        player.openInventory( TempInv );
        inv.put( player.getUniqueId(), TempInv );
    }
}
