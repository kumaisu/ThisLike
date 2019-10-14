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
import com.mycompany.thislike.config.Config;
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
     * @return 
     */
    public static boolean SetLike( int ID, Player player ) {
        //  イイネ処理
        if ( Database.ID != ID ) {
            Tools.Prt( ChatColor.RED + "Error Set Like: " + ID + " Database:" + Database.ID, Tools.consoleMode.full, programCode );
            return false;
        }
        if ( !LikePlayerData.hasSQL( Database.ID, player ) ) {
            SignData.incLike( Database.ID );
            LikePlayerData.AddSQL( player, Database.ID );
            Tools.Prt( player, Config.ReplaceString( Config.SetLike ), Tools.consoleMode.full, programCode );
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
            Tools.Prt( ChatColor.RED + "Error Set UnLike: " + ID + " Database:" + Database.ID, Tools.consoleMode.full, programCode );
            return false;
        }
        if ( LikePlayerData.hasSQL( Database.ID, player ) ) {
            SignData.subLike( Database.ID );
            LikePlayerData.DelPlayerSQL( Database.ID, player );
            Tools.Prt( player, Config.ReplaceString( Config.SetUnlike ), Tools.consoleMode.full, programCode );
            return true;
        } else return false;
    }
}
