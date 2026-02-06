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
                Placeholder.component("prefix_required", prefixComponent),
                Placeholder.component("prefix_optional", prefixComponent),
                Placeholder.component("suffix_required", suffixComponent),
                Placeholder.component("suffix_optional", suffixComponent),
                Placeholder.component("username", playerNameComponent),
                Placeholder.component("message", messageComponent)
        );
    }
}
