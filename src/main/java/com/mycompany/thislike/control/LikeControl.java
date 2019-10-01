/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.control;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.mycompany.thislike.database.Database;
import com.mycompany.thislike.database.SignData;
import com.mycompany.thislike.database.LikePlayerData;
import com.mycompany.kumaisulibraries.Tools;
import static com.mycompany.thislike.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class LikeControl {

    /**
     * いいね設定
     *
     * @param ID
     * @param player
     * @param Owner
     * @param Title
     * @return 
     */
    public static boolean SetLike( int ID, Player player, String Owner, String Title ) {
        //  イイネ処理
        if ( !LikePlayerData.hasSQL( Database.ID, player ) ) {
            SignData.incLike( Database.ID );
            LikePlayerData.AddSQL( player, Database.ID );
            Tools.Prt( player,
                Owner + "さんの" +
                ChatColor.YELLOW + "『" + Title + "』" +
                ChatColor.GREEN + "に" +
                ChatColor.AQUA + "イイネ" +
                ChatColor.GREEN + "しました",
                Tools.consoleMode.full, programCode
            );
            return true;
        } else return false;
    }

    /**
     * いいね解除
     *
     * @param ID
     * @param player
     * @param Owner
     * @param Title
     * @return 
     */
    public static boolean SetUnlike( int ID, Player player, String Owner, String Title  ) {
        //  イイネ解除処理
        if ( LikePlayerData.hasSQL( Database.ID, player ) ) {
            SignData.subLike( Database.ID );
            LikePlayerData.DelPlayerSQL( Database.ID, player );
            Tools.Prt( player,
                Owner +
                ChatColor.LIGHT_PURPLE + "さんの" +
                ChatColor.YELLOW + "『" + Title + "』" +
                ChatColor.LIGHT_PURPLE + "から" +
                ChatColor.AQUA + "イイネ" +
                ChatColor.LIGHT_PURPLE + "をやめました",
                Tools.consoleMode.full, programCode
            );
            return true;
        } else return false;
    }
}
