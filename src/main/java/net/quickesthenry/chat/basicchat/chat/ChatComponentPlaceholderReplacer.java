package net.quickesthenry.chat.basicchat.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.quickesthenry.chat.basicchat.ConfigManager;

public class ChatComponentPlaceholderReplacer {
    private static MiniMessage mm = MiniMessage.miniMessage();

    public static Component makeComponent(Component prefixComponent, Component suffixComponent, Component playerNameComponent, Component messageComponent) {
        String messageConfiguration = ConfigManager.getMessageConfiguration();
        return mm.deserialize(messageConfiguration,
                Placeholder.component("PREFIX_REQUIRED", prefixComponent),
                Placeholder.component("PREFIX_OPTIONAL", prefixComponent),
                Placeholder.component("SUFFIX_REQUIRED", suffixComponent),
                Placeholder.component("SUFFIX_OPTIONAL", suffixComponent),
                Placeholder.component("USERNAME", playerNameComponent),
                Placeholder.component("MESSAGE", messageComponent)
        );
    }
}
