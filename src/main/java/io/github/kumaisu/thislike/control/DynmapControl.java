/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kumaisu.thislike.control;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import io.github.kumaisu.thislike.Lib.Tools;
import static io.github.kumaisu.thislike.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class DynmapControl {

    /**
     * Dynmap へ MarkerIcon を登録する
     *
     * @param ID
     * @param Title
     * @param Loc
     */
    public static void SetDynmapMarker( int ID, String Title, Location Loc ) {
        ///dmarker add id:14 icon:sign x:1 y:69 z:8 world:blaze "ブレイズＴＴ"
        String Command = "dmarker add"
            + " id:" + ID
            + " icon:sign x:" + Loc.getBlockX()
            + " y:" + Loc.getBlockY()
            + " z:" + Loc.getBlockZ()
            + " world:" + Loc.getWorld().getName()
            + " \"" + Title + "\"";
        Bukkit.getServer().dispatchCommand( Bukkit.getConsoleSender(), Command );
        Tools.Prt( "Dynmap set : " + Command, Tools.consoleMode.full, programCode );
    }

    /**
     * Dynmap から MarkerIcon を削除する
     *
     * @param Code 
     */
    public static void DelDynmapArea( int Code ) {
        String Command = "dmarker delete id:" + Code;
        Bukkit.getServer().dispatchCommand( Bukkit.getConsoleSender(), Command );
        Tools.Prt( "Dynmap del : " + Command, Tools.consoleMode.full, programCode );
    }
}
