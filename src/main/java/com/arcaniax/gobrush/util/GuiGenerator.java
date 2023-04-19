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
package com.arcaniax.gobrush.util;

import com.arcaniax.gobrush.object.BrushPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains a utility used for generating the goBrush main menu
 * inventory.
 *
 * @author Arcaniax, McJeffr
 */
public class GuiGenerator {

    private static final String MAIN_MENU_INVENTORY_TITLE = "&1goBrush 選單";
    private static final ItemStack GRAY_GLASS_PANE = createItem(
            XMaterial.GRAY_STAINED_GLASS_PANE.parseMaterial(),
            (short) XMaterial.GRAY_STAINED_GLASS_PANE.data,
            "&6",
            ""
    );
    private static final ItemStack GREEN_GLASS_PANE = createItem(
            XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial(),
            (short) XMaterial.GREEN_STAINED_GLASS_PANE.data,
            "&6",
            ""
    );
    private static final ItemStack ORANGE_GLASS_PANE = createItem(
            XMaterial.ORANGE_STAINED_GLASS_PANE.parseMaterial(),
            (short) XMaterial.ORANGE_STAINED_GLASS_PANE.data,
            "&6",
            ""
    );
    private static final ItemStack RED_GLASS_PANE = createItem(
            XMaterial.RED_STAINED_GLASS_PANE.parseMaterial(),
            (short) XMaterial.RED_STAINED_GLASS_PANE.data,
            "&6",
            ""
    );
    private static final ItemStack WHITE_GLASS_PANE = createItem(
            XMaterial.WHITE_STAINED_GLASS_PANE.parseMaterial(),
            (short) XMaterial.WHITE_STAINED_GLASS_PANE.data,
            "&6",
            ""
    );

    /**
     * This method generates the goBrush main menu based on the BrushPlayer's
     * settings.
     *
     * @param brushPlayer The BrushPlayer that this main menu belongs to.
     * @return The generated goBrush main menu inventory.
     */
    public static Inventory generateMainMenu(BrushPlayer brushPlayer) {
        Inventory mainMenu = Bukkit.createInventory(
                null,
                27,
                ChatColor.translateAlternateColorCodes('&', MAIN_MENU_INVENTORY_TITLE)
        );
        for (int i = 0; i < 27; i++) {
            mainMenu.setItem(i, GRAY_GLASS_PANE);
        }

        mainMenu.setItem(
                11,
                createItem(XMaterial.BROWN_MUSHROOM.parseMaterial(),
                        (short) 0,
                        "&6大小: &e" + brushPlayer.getBrushSize(),
                        "&63D 大小: &e" + (double) brushPlayer.getBrushSize() / 4.0 +
                                "___&3___&7左鍵點擊增加&3___&7右鍵點擊減少___&7Shift " + "點擊更改 10"
                )
        );
        mainMenu.setItem(
                12,
                createItem(XMaterial.BLAZE_POWDER.parseMaterial(),
                        (short) 0,
                        "&6強度: &e" + brushPlayer.getBrushIntensity(),
                        "&3___&7左鍵點擊增加&3___&7右鍵點擊減少"
                )
        );
        if (brushPlayer.getBrushSize() > brushPlayer.getMaxBrushSize()) {
            mainMenu.setItem(2, ORANGE_GLASS_PANE);
            mainMenu.setItem(20, ORANGE_GLASS_PANE);
        } else {
            mainMenu.setItem(2, WHITE_GLASS_PANE);
            mainMenu.setItem(20, WHITE_GLASS_PANE);
        }
        if (brushPlayer.getBrushIntensity() > brushPlayer.getMaxBrushIntensity()) {
            mainMenu.setItem(3, ORANGE_GLASS_PANE);
            mainMenu.setItem(21, ORANGE_GLASS_PANE);
        } else {
            mainMenu.setItem(3, WHITE_GLASS_PANE);
            mainMenu.setItem(21, WHITE_GLASS_PANE);
        }
        if (brushPlayer.isBrushEnabled()) {
            mainMenu.setItem(
                    10,
                    createItem(XMaterial.WRITABLE_BOOK.parseMaterial(),
                            (short) 0,
                            "&6選定筆刷: &e" + brushPlayer.getBrush().getName(),
                            "&a&l啟用___&7___&7左鍵點擊以更改畫筆___&7右鍵點擊切換"
                    )
            );
            mainMenu.setItem(1, GREEN_GLASS_PANE);
            mainMenu.setItem(19, GREEN_GLASS_PANE);
        } else {
            mainMenu.setItem(
                    10,
                    createItem(XMaterial.WRITABLE_BOOK.parseMaterial(),
                            (short) 0,
                            "&6選定筆刷: &e" + brushPlayer.getBrush().getName(),
                            "&c&l停用___&7___&7左鍵點擊以更改畫筆___&7右鍵點擊切換"
                    )
            );
            mainMenu.setItem(1, RED_GLASS_PANE);
            mainMenu.setItem(19, RED_GLASS_PANE);
        }
        if (brushPlayer.isDirectionMode()) {
            mainMenu.setItem(13, HeadURL.create(HeadURL.upB64, "&6拉模式", "&7點擊更改"));
            mainMenu.setItem(4, ORANGE_GLASS_PANE);
            mainMenu.setItem(22, ORANGE_GLASS_PANE);
        } else {
            mainMenu.setItem(13, HeadURL.create(HeadURL.downB64, "&6推模式", "&7點擊更改"));
            mainMenu.setItem(4, ORANGE_GLASS_PANE);
            mainMenu.setItem(22, ORANGE_GLASS_PANE);
        }
        if (brushPlayer.is3DMode()) {
            mainMenu.setItem(14, HeadURL.create(HeadURL._3DB64, "&63D 模式", "&a&l啟用___&7___&7點擊切換"));
            mainMenu.setItem(5, GREEN_GLASS_PANE);
            mainMenu.setItem(23, GREEN_GLASS_PANE);
        } else {
            mainMenu.setItem(14, HeadURL.create(HeadURL._3DB64, "&63D 模式", "&c&l停用___&7___&7點擊切換"));
            mainMenu.setItem(5, RED_GLASS_PANE);
            mainMenu.setItem(23, RED_GLASS_PANE);
        }
        if (brushPlayer.isFlatMode()) {
            mainMenu.setItem(
                    15,
                    createItem(XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE.parseMaterial(),
                            (short) 0,
                            "&6平面模式",
                            "&a&l啟用___&7___&7點擊切換"
                    )
            );
            mainMenu.setItem(6, GREEN_GLASS_PANE);
            mainMenu.setItem(24, GREEN_GLASS_PANE);
        } else {
            mainMenu.setItem(
                    15,
                    createItem(XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE.parseMaterial(),
                            (short) 0,
                            "&6平面模式",
                            "&c&l停用___&7___&7點擊切換"
                    )
            );
            mainMenu.setItem(6, RED_GLASS_PANE);
            mainMenu.setItem(24, RED_GLASS_PANE);
        }

        if (brushPlayer.isAutoRotation()) {
            mainMenu.setItem(
                    16,
                    createItem(XMaterial.COMPASS.parseMaterial(),
                            (short) 0,
                            "&6自動旋轉",
                            "&a&l啟用___&7___&7點擊切換"
                    )
            );
            mainMenu.setItem(7, GREEN_GLASS_PANE);
            mainMenu.setItem(25, GREEN_GLASS_PANE);
        } else {
            mainMenu.setItem(
                    16,
                    createItem(XMaterial.COMPASS.parseMaterial(),
                            (short) 0,
                            "&6自動旋轉",
                            "&c&l停用___&7___&7點擊切換"
                    )
            );
            mainMenu.setItem(7, RED_GLASS_PANE);
            mainMenu.setItem(25, RED_GLASS_PANE);
        }
        return mainMenu;
    }

    private static ItemStack createItem(Material material, short data, String name, String lore) {
        ItemStack is = new ItemStack(material);
        ItemMeta meta = is.getItemMeta();
        if (!lore.equals("")) {
            String[] loreListArray = lore.split("___");
            List<String> loreList = new ArrayList<String>();
            String[] arrayOfString1;
            int j = (arrayOfString1 = loreListArray).length;
            for (int i = 0; i < j; i++) {
                String s = arrayOfString1[i];
                loreList.add(ChatColor.translateAlternateColorCodes('&', s));
            }
            meta.setLore(loreList);
        }
        if (!name.equals("")) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        is.setItemMeta(meta);
        is.setDurability(data);
        return is;
    }

}
