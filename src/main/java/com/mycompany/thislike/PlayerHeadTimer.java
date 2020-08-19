/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.thislike.config.Config;
import com.mycompany.thislike.control.OwnerControl;
import static com.mycompany.thislike.ThisLike.TaskFlag;
import static com.mycompany.thislike.config.Config.programCode;

/**
 *
 * 非同期タイマークラス
 * @author Kumaisu
 */
public class PlayerHeadTimer extends BukkitRunnable {
    private final Plugin plg;
    private final Player pl;

    /**
     * コンストラクタ
     * @param plg_ プラグインメインクラスのインスタンス
     * @param pl_ 実行者
     */
    public PlayerHeadTimer( Plugin plg_, Player pl_ )
    {
        plg = plg_;
        pl = pl_;
    }

    /**
     * 非同期処理実行メソッド
     */
    public void run()
    {
        if ( TaskFlag ) {
            if ( !Config.MakeHead || !TaskFlag ) { return; }
            // 内容を更新
            Inventory TempInv = OwnerControl.inv.get( pl.getUniqueId() );
            int i = 0;
            for ( ItemStack inv : TempInv ) {
                if ( inv == null ) {
                    Tools.Prt( ChatColor.GREEN + "Item Name : null", Tools.consoleMode.max, programCode );
                } else {
                    Tools.Prt(
                        ChatColor.AQUA + "Item Name : " +
                        inv.getType().toString() + " = " +
                        inv.getItemMeta().getDisplayName(),
                        Tools.consoleMode.max,
                        programCode
                    );
                    if ( inv.getType().equals( Material.PLAYER_HEAD ) ) {
                        String target = inv.getItemMeta().getDisplayName();
                        SkullMeta skull = ( SkullMeta ) inv.getItemMeta();
                        Tools.Prt( "Get Online : " + target, Tools.consoleMode.max, programCode );
                        skull.setOwner( target );
                        inv.setItemMeta( skull );
                        pl.getOpenInventory().setItem( i, inv );
                        pl.updateInventory();
                    }
                }
                i++;
            }
        }
    }   
}
