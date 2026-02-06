package net.quickesthenry.chat.basicchat;

import net.fabricmc.loader.api.FabricLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private static String messageConfiguration = "<PREFIX_REQUIRED> <USERNAME><SUFFIX_OPTIONAL> <b><dark_gray>>></dark_gray></b> <MESSAGE>";

    public static void loadConfiguration() {
        // Use Fabric's official config directory path
        Path configFile = FabricLoader.getInstance().getConfigDir().resolve("basicchat.conf");

        if (Files.exists(configFile)) {
            try {
                messageConfiguration = Files.readString(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                // Creates the directory if it doesn't exist and writes the default
                Files.createDirectories(configFile.getParent());
                Files.writeString(configFile, messageConfiguration);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getMessageConfiguration() {
        return messageConfiguration;
    }
}