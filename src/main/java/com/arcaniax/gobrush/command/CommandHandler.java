/*
 * goBrush is designed to streamline and simplify your mountain building experience.
 * Copyright (C) Arcaniax-Development
 * Copyright (C) Arcaniax team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.arcaniax.gobrush.command;

import com.arcaniax.gobrush.GoBrushPlugin;
import com.arcaniax.gobrush.Session;
import com.arcaniax.gobrush.object.Brush;
import com.arcaniax.gobrush.object.BrushPlayer;
import com.arcaniax.gobrush.object.HeightMapExporter;
import com.sk89q.worldedit.IncompleteRegionException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CommandHandler implements CommandExecutor {

    private static final String prefix = "&bgoBrush> ";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gobrush") || cmd.getName().equalsIgnoreCase("gb")) {
            if (!(sender instanceof Player)) {
                return false;
            }
            final Player p = (Player) sender;
            BrushPlayer bp = Session.getBrushPlayer(p.getUniqueId());
            if (!p.hasPermission("gobrush.use")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&',
                        prefix + "&c您缺少權限 gobrush.use"
                ));
                return true;
            }
            if (args.length == 0) {
                if (p.hasPermission("gobrush.admin")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes(
                            '&',
                            prefix + "&c/gb size&7|&cintensity&7|&cbrush&7|&ctoggle&7|&creload&7|&cexport&7|&cinfo "
                    ));
                    return true;
                } else if (p.hasPermission("gobrush.export")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes(
                            '&',
                            prefix + "&c/gb size&7|&cintensity&7|&cbrush&7|&ctoggle&7|&cexport&7|&cinfo "
                    ));
                    return true;
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&',
                        prefix + "&c/gb size&7|&cintensity&7|&cbrush&7|&ctoggle&7|&cinfo "
                ));
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("size") || args[0].equalsIgnoreCase("s")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&c/gb size [數字]"));
                    return true;
                } else if (args[0].equalsIgnoreCase("intensity") || args[0].equalsIgnoreCase("i")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&c/gb intensity [數字]"));
                    return true;
                } else if (args[0].equalsIgnoreCase("brush") || args[0].equalsIgnoreCase("b")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&c/gb brush [檔案名稱]"));
                    return true;
                } else if ((args[0].equalsIgnoreCase("export") || args[0].equalsIgnoreCase("e")) && p.hasPermission(
                        "gobrush.export")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&c/gb export [檔案名稱]"));
                    return true;
                } else if (args[0].equalsIgnoreCase("toggle") || args[0].equalsIgnoreCase("t")) {

                    if (bp.isBrushEnabled()) {
                        bp.toggleBrushEnabled();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&c停用筆刷"));
                    } else {
                        bp.toggleBrushEnabled();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&a啟用筆刷"));
                    }
                    return true;
                } else if ((args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) && p.hasPermission(
                        "gobrush.admin")) {
                    GoBrushPlugin.getPlugin().reloadConfig();
                    Session.getConfig().reload(GoBrushPlugin.getPlugin().getConfig());
                    int amountOfValidBrushes = Session.initializeValidBrushes();
                    GoBrushPlugin.getPlugin().getLogger().log(Level.INFO, "已註冊 {0} 個筆刷。", amountOfValidBrushes);
                    Session.initializeBrushMenu();
                    Session.initializeBrushPlayers();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&a重新加載成功"));
                    return true;
                } else if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("i")) {

                    p.spigot().sendMessage(new ComponentBuilder("goBrush> ").color(ChatColor.AQUA)
                            .append("Created by: ").color(ChatColor.GOLD)
                            .append("Arcaniax").color(ChatColor.YELLOW)
                            .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://twitter.com/Arcaniax")).create());

                    p.spigot().sendMessage(new ComponentBuilder("goBrush> ").color(ChatColor.AQUA)
                            .append("Sponsored by: ").color(ChatColor.GOLD)
                            .append("@goCreativeMC").color(ChatColor.YELLOW)
                            .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://twitter.com/goCreativeMC")).create());

                    p.sendMessage(ChatColor.translateAlternateColorCodes(
                            '&',
                            prefix + "&6Plugin download: &ehttps://www.spigotmc.org/resources/23118/"
                    ));

                    p.spigot().sendMessage(new ComponentBuilder("goBrush> ").color(ChatColor.AQUA)
                            .append("More brushes: ").color(ChatColor.GOLD)
                            .append("Click here").color(ChatColor.YELLOW)
                            .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://gumroad.com/aerios#JAtxa")).create());

                    return true;
                }
                if (p.hasPermission("gobrush.admin")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes(
                            '&',
                            prefix + "&c/gb size&7|&cintensity&7|&cbrush&7|&ctoggle&7|&creload&7|&cexport&7|&cinfo "
                    ));
                    return true;
                } else if (p.hasPermission("gobrush.export")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes(
                            '&',
                            prefix + "&c/gb size&7|&cintensity&7|&cbrush&7|&ctoggle&7|&cexport&7|&cinfo "
                    ));
                    return true;
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&',
                        prefix + "&c/gb size&7|&cintensity&7|&cbrush&7|&ctoggle&7|&cinfo "
                ));
                return true;
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("size") || args[0].equalsIgnoreCase("s")) {
                    try {
                        int sizeAmount = Integer.parseInt(args[1]);
                        if (sizeAmount > bp.getMaxBrushSize() && !p.hasPermission("gobrush.bypass.maxsize")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes(
                                    '&',
                                    prefix + "&6最大尺寸為 &e" + bp.getMaxBrushSize()
                            ));
                            sizeAmount = bp.getMaxBrushSize();
                        } else if (sizeAmount < 5) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&6最小尺寸是 &e5"));
                            sizeAmount = 5;
                        } else if (sizeAmount % 2 == 0) {
                            sizeAmount++;
                        }
                        bp.setBrushSize(sizeAmount);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&6大小設置為: &e" + sizeAmount));
                        bp.getBrush().resize(sizeAmount);

                        return true;
                    } catch (Exception e) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&c/gb size [數字]"));
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("intensity") || args[0].equalsIgnoreCase("i")) {
                    try {
                        int intensityAmount = Integer.parseInt(args[1]);
                        if (intensityAmount > bp.getMaxBrushIntensity() && !p.hasPermission("gobrush.bypass.maxintensity")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes(
                                    '&',
                                    prefix + "&6最大強度為 &e" + bp.getBrushIntensity()
                            ));
                            intensityAmount = bp.getMaxBrushIntensity();
                        } else if (intensityAmount < 1) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&6最小強度是 &e1"));
                            intensityAmount = 1;
                        }
                        bp.setBrushIntensity(intensityAmount);
                        p.sendMessage(ChatColor.translateAlternateColorCodes(
                                '&',
                                prefix + "&6強度設置為: &e" + intensityAmount
                        ));
                        return true;
                    } catch (Exception e) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&c/gb intensity [數字]"));
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("brush") || args[0].equalsIgnoreCase("b")) {
                    String name = args[1].replace("_", " ");
                    if (Session.containsBrush(name)) {
                        int size = bp.getBrushSize();
                        Brush brush = Session.getBrush(name);
                        bp.setBrush(brush);
                        bp.getBrush().resize(size);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&6筆刷設置為: &e" + name));
                        return true;
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes(
                                '&',
                                prefix + "&c無法載入筆刷 \"" + name + "\""
                        ));
                        return true;
                    }
                } else if ((args[0].equalsIgnoreCase("export") || args[0].equalsIgnoreCase("e")) && p.hasPermission(
                        "gobrush.export")) {
                    final String name = args[1];
                    Bukkit.getScheduler().runTaskAsynchronously(GoBrushPlugin.plugin, new Runnable() {
                        @Override
                        public void run() {
                            HeightMapExporter hm;
                            try {
                                hm = new HeightMapExporter(p);
                            } catch (IncompleteRegionException e) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes(
                                        '&',
                                        prefix + "&c請創建 WorldEdit 選區 &6(//wand)"
                                ));
                                return;
                            }
                            if (!hm.hasWorldEditSelection()) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes(
                                        '&',
                                        prefix + "&c請創建 WorldEdit 選區 &8(//wand)"
                                ));
                                return;
                            }
                            hm.exportImage(500, name);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&6已輸出為 &e" + name + ".png"));
                            Session.initializeValidBrushes();
                            Session.initializeBrushMenu();

                        }
                    });
                    return true;
                }
                if (p.hasPermission("gobrush.admin")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes(
                            '&',
                            prefix + "&c/gb size&7|&cintensity&7|&cbrush&7|&ctoggle&7|&creload&7|&cexport&7|&cinfo "
                    ));
                    return true;
                } else if (p.hasPermission("gobrush.export")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes(
                            '&',
                            prefix + "&c/gb size&7|&cintensity&7|&cbrush&7|&ctoggle&7|&cexport&7|&cinfo "
                    ));
                    return true;
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&',
                        prefix + "&c/gb size&7|&cintensity&7|&cbrush&7|&ctoggle&7|&cinfo "
                ));
                return true;
            }
        }
        return false;
    }

}
