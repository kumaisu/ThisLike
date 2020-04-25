/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.TabComplete;

import java.util.List;
import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 *  Copyright (c) 2019 sugichan. All rights reserved.
 *
 * @author sugichan
 */
public class ThisLikeTabComp implements TabCompleter {

    /**
     * ThisLike Command TAB Complete list
     *
     * @param sender
     * @param command
     * @param alias
     * @param args
     * @return 
     */
    @Override
    public List<String> onTabComplete( CommandSender sender, Command command, String alias, String[] args ) {
        List< String > list = new ArrayList<>();
        switch ( args.length ) {
            case 1:
                list.add( "top" );
                list.add( "list" );
                list.add( "admin" );
                list.add( "set" );
                list.add( "info" );
                list.add( "title" );
                list.add( "status" );
                list.add( "message" );
                list.add( "reload" );
                list.add( "console" );
                list.add( "help" );
                break;
            case 2:
                switch ( args[0] ) {
                    case "console":
                        list.add( "full" );
                        list.add( "max" );
                        list.add( "normal" );
                        list.add( "stop" );
                        break;
                    case "title":
                        list.add( "[Sign ID]" );
                        break;
                    case "list":
                        list.add( "u:player" );
                        list.add( "d:yyyy-mm-dd" );
                        list.add( "k:Keyword" );
                    case "top":
                        list.add( "l:lines" );
                        break;
                }
                break;
            case 3:
                if ( args[0].equals( "title") ) {
                    list.add( "new title" );
                    break;
                }
        }
        return list;
    }
}

