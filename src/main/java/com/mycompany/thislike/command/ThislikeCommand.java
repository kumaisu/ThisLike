/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thislike.command;

import com.mycompany.thislike.ThisLike;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author sugichan
 */
public class ThislikeCommand implements CommandExecutor {

     private final ThisLike instance;

     public ThislikeCommand( ThisLike instance ) {
         this.instance = instance;
     }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
