/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kumaisu.thislike.control;

import java.util.Date;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import io.github.kumaisu.thislike.Lib.Tools;
import io.github.kumaisu.thislike.Lib.Utility;
import io.github.kumaisu.thislike.config.Config;
import io.github.kumaisu.thislike.config.Reward;
import io.github.kumaisu.thislike.database.OwnerData;
import io.github.kumaisu.thislike.database.SignData;

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
                String cmd = Replace( player, CP, total );
                Tools.ExecOtherCommand( player, cmd, "" );
                Tools.Prt( ChatColor.AQUA + "Command Execute : " + ChatColor.WHITE + cmd, Tools.consoleMode.max, Config.programCode );
            } );

            if ( !"".equals( Reward.RewardMessage ) ) {
                Tools.Prt( player, Replace( player, Reward.RewardMessage, total ), Tools.consoleMode.full, Config.programCode );
            }
        } else {
            Tools.Prt( player, ChatColor.RED + "残念ながらイイネがありません", Tools.consoleMode.full, Config.programCode );
        }
    }

    public static String Replace( Player player, String msg, int total ) {
        String RetMsg = Utility.ReplaceString( msg );
        RetMsg = RetMsg.replace( "%owner%", player.getName() );
        RetMsg = RetMsg.replace( "%total%", String.valueOf( total * Reward.magnification ) );
        RetMsg = RetMsg.replace( "%count%", String.valueOf( total ) );
        return RetMsg;
    }

    public static void CheckRewards( Player player ) {
        //  Daily Rewards の判定
        Date RDate = OwnerData.GetDate( player.getUniqueId() );
        if ( RDate != null ) {
            int progress = Utility.dateDiff( RDate, new Date() );
            if ( progress >= Reward.DateCount ) {
                Tools.Prt( "ThisLike Rewards distribution : " + progress, Tools.consoleMode.full, Config.programCode );
                Reward( player );
            } else {
                Tools.Prt( "ThisLike Reward Progress : " + progress, Tools.consoleMode.full, Config.programCode );
                String NextMessage = ChatColor.YELLOW + "次のイイネ報酬まで ";
                if ( ( Reward.DateCount - progress ) > 1 ) {
                    NextMessage += ChatColor.AQUA + String.format( "%d", Reward.DateCount - progress ) + ChatColor.YELLOW + " 日です";
                } else {
                    long dateTimeTo = new Date().getTime();
                    long dateTimeFrom = RDate.getTime();
                    long dayDiff = dateTimeTo - dateTimeFrom;
                    Tools.Prt( "Current time      : " + dateTimeTo, Tools.consoleMode.max, Config.programCode );
                    Tools.Prt( "Last distribution : " + dateTimeFrom, Tools.consoleMode.max, Config.programCode );
                    Tools.Prt( "Differential time : " + dayDiff, Tools.consoleMode.max, Config.programCode );
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
