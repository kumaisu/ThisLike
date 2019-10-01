/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.database;

import org.bukkit.Location;
import com.mycompany.kumaisulibraries.Tools;
import static com.mycompany.thislike.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class DatabaseUtil {

    public static String makeID( Location loc ) {
        String X = String.format( "%08x", loc.getBlockX() );
        String Y = String.format( "%04x", loc.getBlockY() );
        String Z = String.format( "%08x", loc.getBlockZ() );
        Tools.Prt( "Location Convert = " +
            loc.getBlockX() + ":" + X + "," +
            loc.getBlockY() + ":" + Y + "," +
            loc.getBlockZ() + ":" + Z,
            Tools.consoleMode.max, programCode );
        String RetStr = X + Y + Z;
        return RetStr;
    }

    public static Location StoreID( String ID ) {
        Location Loc = null;
        Integer X = Integer.decode( "0x" + ID.substring( 0, 8 ) );
        Integer Y = Integer.decode( "0x" + ID.substring( 8, 4 ) );
        Integer Z = Integer.decode( "0x" + ID.substring( 12, 8 ) );
        Tools.Prt( "Location Resotre [" + ID + "] " + X + "," + Y + "," + Z, Tools.consoleMode.max, programCode );
        Loc.setX( X );
        Loc.setY( Y );
        Loc.setZ( Z );
        return Loc;
    }
}
