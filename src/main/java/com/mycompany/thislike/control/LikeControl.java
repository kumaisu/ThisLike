/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.control;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.thislike.config.Config;
import com.mycompany.thislike.database.Database;
import com.mycompany.thislike.database.SignData;
import com.mycompany.thislike.database.LikePlayerData;
import static com.mycompany.thislike.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class LikeControl {

    /**
     * 共通プレイヤー向けメッセージ
     *
     * @param player 
     */
    private static void SendMessage( Player player, String Msg ) {
        Tools.Prt( player, Msg, Tools.consoleMode.full, programCode );
        Player OwnerPlayer = Bukkit.getServer().getPlayer( Database.OwnerName );
        if ( OwnerPlayer != null ) Tools.Prt( OwnerPlayer, Msg, Tools.consoleMode.max, programCode );
    }

    /**
     * いいね設定
     *
     * @param ID
     * @param player
     * @return 
     */
    public static boolean SetLike( int ID, Player player ) {
        //  イイネ処理
        if ( Database.ID != ID ) {
            Tools.Prt( ChatColor.RED + "Error Set Like: " + ID + " Database:" + Database.ID, Tools.consoleMode.max, programCode );
            return false;
        }
        if ( !LikePlayerData.hasSQL( Database.ID, player ) ) {
            SignData.incLike( Database.ID );
            LikePlayerData.AddSQL( player, Database.ID );
            Tools.Prt( player, Config.ReplaceString( Config.SetLike ), Tools.consoleMode.max, programCode );
            if ( ( Bukkit.getPlayer( Database.OwnerUUID ) != null ) && ( !Config.InfoLike.equals( "none" ) ) ) {
                Tools.Prt( Bukkit.getPlayer( Database.OwnerUUID), Config.ReplaceString( Config.InfoLike ), Tools.consoleMode.max, programCode );
            }

            if ( Config.LikeBroadcast ) {
                Tools.Prt( "Owner UUID : " + Database.OwnerUUID.toString(), Tools.consoleMode.max, programCode );
                Tools.Prt( "Liker UUID : " + Database.LikeUUID.toString(), Tools.consoleMode.max, programCode );
                Bukkit.getOnlinePlayers().stream().forEachOrdered( ( p ) -> {
                    String getUUID = p.getUniqueId().toString();
                    Tools.Prt( "Get UUID : " + getUUID, Tools.consoleMode.max, programCode );
                    if ( !Database.OwnerUUID.toString().equals( getUUID ) && !Database.LikeUUID.toString().equals( getUUID ) ) {
                        Tools.Prt( "send to Message : " + p.getDisplayName(), Tools.consoleMode.max, programCode );
                        Tools.Prt( p, Config.ReplaceString( Config.SetLike ), Tools.consoleMode.max, programCode );
                    }
                } );
            }
            return true;
        } else return false;
    }

    /**
     * いいね解除
     *
     * @param ID
     * @param player
     * @return 
     */
    public static boolean SetUnlike( int ID, Player player ) {
        //  イイネ解除処理
        if ( Database.ID != ID ) {
            Tools.Prt( ChatColor.RED + "Error Set UnLike: " + ID + " Database:" + Database.ID, Tools.consoleMode.max, programCode );
            return false;
        }
        if ( LikePlayerData.hasSQL( Database.ID, player ) ) {
            SignData.subLike( Database.ID );
            LikePlayerData.DelPlayerSQL( Database.ID, player );
            Tools.Prt( player, Config.ReplaceString( Config.SetUnlike ), Tools.consoleMode.max, programCode );
            if ( ( Bukkit.getPlayer( Database.OwnerUUID ) != null ) && ( !Config.InfoUnlike.equals( "none" ) ) ) {
                Tools.Prt( Bukkit.getPlayer( Database.OwnerUUID), Config.ReplaceString( Config.InfoUnlike ), Tools.consoleMode.max, programCode );
            }

            if ( Config.UnlikeBroadcast ) {
                Tools.Prt( "Owner UUID : " + Database.OwnerUUID.toString(), Tools.consoleMode.max, programCode );
                Tools.Prt( "Liker UUID : " + Database.LikeUUID.toString(), Tools.consoleMode.max, programCode );
                Bukkit.getOnlinePlayers().stream().forEachOrdered( ( p ) -> {
                    String getUUID = p.getUniqueId().toString();
                    Tools.Prt( "Get UUID : " + getUUID, Tools.consoleMode.max, programCode );
                    if ( !Database.OwnerUUID.toString().equals( getUUID ) && !Database.LikeUUID.toString().equals( getUUID ) ) {
                        Tools.Prt( "send to Message : " + p.getDisplayName(), Tools.consoleMode.max, programCode );
                        Tools.Prt( p, Config.ReplaceString( Config.SetUnlike ), Tools.consoleMode.max, programCode );
                    }
                } );
            }
            return true;
        } else return false;
    }
}
