/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike;

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
    */
    public static void printLiker( Player player, int ID, String Title ) {
        Map< String, Date > liker = LikePlayerData.listSQL( ID );

        int slot = liker.size() + ( 9 - ( liker.size() % 9 ) ) + 9;
        if ( slot>54 ) { slot = 54; }

        Inventory TempInv;
        TempInv = Bukkit.createInventory( null, slot, "『" + Title + "』いいね" );

        ItemStack DelIcon = new ItemStack( Material.BARRIER, 1 );
        ItemMeta DelMeta = Bukkit.getItemFactory().getItemMeta(Material.BARRIER);
        DelMeta.setDisplayName( "Remove Sign" );
        DelIcon.setItemMeta( DelMeta );
        TempInv.setItem( slot - 1, DelIcon );
        
        liker.keySet().forEach( ( key ) -> {
            Tools.Prt( ChatColor.GREEN + key + " : " + ChatColor.WHITE + liker.get( key ), Tools.consoleMode.max, programCode );
            SimpleDateFormat ddf = new SimpleDateFormat( "yyyy/MM/dd" );
            SimpleDateFormat tdf = new SimpleDateFormat( "HH:mm:ss" );
            List< String > Lore = Arrays.asList( ddf.format( liker.get( key ) ),tdf.format( liker.get( key ) ) );
            TempInv.addItem( getPlayerHead( player, key, Lore ) );
        } );

        inv.put( player.getUniqueId(), TempInv );
        player.openInventory( TempInv );
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
        //  SkullMeta iMeta = ( SkullMeta ) Bukkit.getItemFactory().getItemMeta( Material.SKULL_ITEM );
        SkullMeta skull = ( SkullMeta ) Bukkit.getItemFactory().getItemMeta( Material.PLAYER_HEAD );
        skull.setOwner( target );
        skull.setDisplayName( target );
        skull.setLore( Lore );
        //  ItemStack i = new ItemStack( Material.SKULL_ITEM, 1, ( byte ) SkullType.PLAYER.ordinal() );
        ItemStack i = new ItemStack( Material.PLAYER_HEAD, 1 );
        i.setItemMeta( skull );
        return i;
    }

    public static void SendMenu( Player player ) {
        String ExecCommand;

        /*
        イイネ一覧  [表示]
        建物名      [変更]
        看板        [削除]
        */
        player.sendMessage( ChatColor.GREEN + "+-------------------------------------" );
        player.sendMessage( ChatColor.GREEN + "|" + ChatColor.AQUA + "イイネ[ThisLike] Owner コマンド" );
        
        ExecCommand = "tellraw " + player.getName() + " ["
                + "{\"text\":\"|イイネ一覧  \",\"color\":\"green\"},"
                + "{\"text\":\"[表示]\",\"color\":\"yellow\",\"underlined\":\"true\",\"clickEvent\":"
                + "{\"action\":\"run_command\",\"value\":\"/ThisLike list\"}}]";
        Bukkit.getServer().dispatchCommand( Bukkit.getConsoleSender(), ExecCommand );

        ExecCommand = "tellraw " + player.getName() + " ["
                + "{\"text\":\"|建物名      \",\"color\":\"green\"},"
                + "{\"text\":\"[変更]\",\"color\":\"yellow\",\"underlined\":\"true\",\"clickEvent\":"
                + "{\"action\":\"run_command\",\"value\":\"/ThisLike change\"}}]";
        Bukkit.getServer().dispatchCommand( Bukkit.getConsoleSender(), ExecCommand );

        ExecCommand = "tellraw " + player.getName() + " ["
                + "{\"text\":\"|看板        \",\"color\":\"green\"},"
                + "{\"text\":\"[削除]\",\"color\":\"yellow\",\"underlined\":\"true\",\"clickEvent\":"
                + "{\"action\":\"run_command\",\"value\":\"/ThisLike remove\"}}]";
        Bukkit.getServer().dispatchCommand( Bukkit.getConsoleSender(), ExecCommand );
        
        player.sendMessage( ChatColor.GREEN + "+-------------------------------------" );
    }
}
