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

import com.arcaniax.gobrush.GoBrushPlugin;
import com.arcaniax.gobrush.Session;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

public class BrushZipManager {

    private static int amountOfValidBrushes;

    public static void setupBrushes() {
        try {
            amountOfValidBrushes = Session.initializeValidBrushes();
            if (amountOfValidBrushes == 0) {
                GoBrushPlugin.getPlugin().getLogger().log(Level.INFO, "正在從GitHub下載筆刷，請稍候...");
                try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(
                        "https://github.com/Arcaniax-Development/goBrush-Assets/blob/main/brushes.zip?raw=true").openStream());
                     FileOutputStream fileOutputStream = new FileOutputStream(GoBrushPlugin
                             .getPlugin()
                             .getDataFolder() + "/brushes/brushes.zip")) {
                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = bufferedInputStream.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                    GoBrushPlugin.getPlugin().getLogger().log(Level.INFO, "筆刷下載成功。");
                    try {
                        GoBrushPlugin.getPlugin().getLogger().log(Level.INFO, "提取筆刷...");
                        ZipFile zipFile = new ZipFile(GoBrushPlugin.getPlugin().getDataFolder() + "/brushes/brushes.zip");
                        zipFile.extractAll(GoBrushPlugin.getPlugin().getDataFolder() + "/brushes/");
                        GoBrushPlugin.getPlugin().reloadConfig();
                        int amountOfValidBrushes = Session.initializeValidBrushes();
                        Session.getConfig().reload(GoBrushPlugin.getPlugin().getConfig());
                        Session.initializeBrushMenu();
                        Session.initializeBrushPlayers();
                        GoBrushPlugin.getPlugin().getLogger().log(Level.INFO, "清理中...");
                        String brushesZip = GoBrushPlugin.getPlugin().getDataFolder() + "/brushes/brushes.zip";
                        try {
                            Files.delete(Paths.get(brushesZip));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        GoBrushPlugin.getPlugin().getLogger().log(
                                Level.INFO,
                                "已註冊 {0} 個筆刷。準備好了!",
                                amountOfValidBrushes
                        );
                    } catch (ZipException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    GoBrushPlugin.getPlugin().getLogger().log(
                            Level.SEVERE,
                            "無法下載筆刷。請手動下載並放入 /plugins/goBrush/brushes"
                    );
                    GoBrushPlugin.getPlugin().getLogger().log(
                            Level.SEVERE,
                            "https://github.com/Arcaniax-Development/goBrush-Assets/blob/main/brushes.zip?raw=true"
                    );
                }
            }
        } catch (Exception ex) {
            GoBrushPlugin.getPlugin().getLogger().log(
                    Level.SEVERE,
                    "Could not download brushes. Please download them manually here and put them into /plugins/goBrush/brushes"
            );
            GoBrushPlugin.getPlugin().getLogger().log(
                    Level.SEVERE,
                    "https://github.com/Arcaniax-Development/goBrush-Assets/blob/main/brushes.zip?raw=true"
            );
        }
    }

}
