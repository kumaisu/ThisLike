/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.control;

import java.util.Date;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.kumaisulibraries.Utility;
import com.mycompany.thislike.config.Config;
import com.mycompany.thislike.config.Reward;
import com.mycompany.thislike.database.OwnerData;
import com.mycompany.thislike.database.SignData;

/**
 *
 * @author sugichan
 */
public class Rewards {

    public static void Reward( Player player ) {
        Tools.Prt( ChatColor.YELLOW + "ThisLike Rewards !!", Tools.consoleMode.full, Config.programCode );
        int total = SignData.GetTotalLikes( player.getUniqueId() );
        Tools.Prt( "Get Like Total : " + total, Tools.consoleMode.full, Config.programCode );

        if ( total > 0 ) {
            OwnerData.Update( player.getUniqueId() );

            if ( Reward.sound_play ) {
                Tools.Prt( "Sound Play !!", Tools.consoleMode.full, Config.programCode );
                ( player.getWorld() ).playSound(
                    player.getLocation(),                   // 鳴らす場所
                    Sound.valueOf( Reward.sound_type ),     // 鳴らす音
                    Reward.sound_volume,                    // 音量
                    Reward.sound_pitch                      // 音程
                );
            }

            Reward.Commands.stream().forEach( CP -> {
                String cmd = CP.replace( "%owner%", player.getName() );
                cmd = cmd.replace( "%total%", String.valueOf( total * Reward.magnification ) );
                cmd = cmd.replace( "%count%", String.valueOf( total ) );
                Tools.ExecOtherCommand( player, cmd, "" );
                Tools.Prt( ChatColor.AQUA + "Command Execute : " + ChatColor.WHITE + cmd, Tools.consoleMode.max, Config.programCode );
            } );
        } else {
            Tools.Prt( player, ChatColor.RED + "残念ながらイイネがありません", Tools.consoleMode.full, Config.programCode );
        }
    }

    public static void CheckRewards( Player player ) {
        //  Daily Rewards の判定
        Date RDate = OwnerData.GetDate( player.getUniqueId() );
        if ( RDate != null ) {
            int progress = Utility.dateDiff( RDate, new Date() );
            if ( progress >= Reward.DateCount ) {
                Tools.Prt( "ThisLike Rewards distribution : " + progress, Config.programCode );
                Reward( player );
            } else {
                Tools.Prt( "ThisLike Player Progress : " + progress, Config.programCode );
                String NextMessage = ChatColor.YELLOW + "次のイイネ報酬まで ";
                if ( ( Reward.DateCount - progress ) > 1 ) {
                    NextMessage += ChatColor.AQUA + String.format( "%d", Reward.DateCount - progress ) + ChatColor.YELLOW + " 日です";
                } else {
                    long dateTimeTo = new Date().getTime();
                    long dateTimeFrom = RDate.getTime();
                    long dayDiff = dateTimeTo - dateTimeFrom;
                    Tools.Prt( "Current time      : " + dateTimeTo, Tools.consoleMode.full, Config.programCode );
                    Tools.Prt( "Last distribution : " + dateTimeFrom, Tools.consoleMode.full, Config.programCode );
                    Tools.Prt( "Differential time : " + dayDiff, Tools.consoleMode.full, Config.programCode );
                    //  純粋に時間なのでms秒での数値を時間に修正
                    int NextTime = ( int ) Math.round( dayDiff / 1000 / 60 );
                    NextMessage += ChatColor.AQUA + String.format( "%d", 1440 - NextTime ) + ChatColor.YELLOW + " 分です";
                }
                Tools.Prt( player, NextMessage, Tools.consoleMode.max, Config.programCode );
            }
        } else {
            Tools.Prt( ChatColor.RED + "ThisLike Rewards Date is NULL", Config.programCode );
        }
    }
}
