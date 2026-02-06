package net.quickesthenry.chat.basicchat.chat;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.quickesthenry.chat.basicchat.ConfigManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChatComponentMaker {
    private static final MiniMessage STANDARD_MINIMESSAGE_DESERIALIZER = MiniMessage.builder()
            .tags(TagResolver.resolver(
                    TagResolver.resolver("click", (queue, ctx) -> {
                        // Verpackt die leere Komponente in ein Tag-Objekt
                        return net.kyori.adventure.text.minimessage.tag.Tag.inserting(Component.empty());
                    }),
                    net.kyori.adventure.text.minimessage.tag.standard.StandardTags.defaults()
            ))
            .build();

    private static final MiniMessage ADMIN_MINIMESSSAGE_DESERIALIZER = MiniMessage.miniMessage();

    private static final PlainTextComponentSerializer plainTextComponentSerializer = PlainTextComponentSerializer.plainText();
    /**
     *
     * @param prefix The prefix for formatting
     * @param suffix The suffix for formatting
     * @param playerName the player playerName
     * @param player The player, used for formatting permissions
     * @param playerMessage The message the player sent
     * @return Component requested
     * @throws RequiredMetaDataNulled if Prefix or Suffix is provided as null while required according to the config.
     */
    public static Component makeComponent(@Nullable String prefix, @Nullable String suffix, @NotNull String playerName, @NotNull ServerPlayerEntity player, @NotNull String playerMessage) throws RequiredMetaDataNulled {
        String messageConfiguration = ConfigManager.getMessageConfiguration();
        if (messageConfiguration.contains("<prefix_required>") && prefix == null) {
            throw new RequiredMetaDataNulled("Prefix");
        }
        if (messageConfiguration.contains("<suffix_required>") && suffix == null) {
            throw new RequiredMetaDataNulled("Suffix");
        }

        // Define different Component types
        Component messageComponent;
        Component prefixComponent;
        Component suffixComponent;
        Component playerNameComponent = plainTextComponentSerializer.deserialize(playerName);

        // Make prefix and suffix components
        if (prefix == null) {
            prefixComponent = Component.empty();
        } else {
            prefixComponent = ADMIN_MINIMESSSAGE_DESERIALIZER.deserialize(prefix);
        }
        if (suffix == null) {
            suffixComponent = Component.empty();
        } else {
            suffixComponent = ADMIN_MINIMESSSAGE_DESERIALIZER.deserialize(suffix);
        }

        if (Permissions.check(player, "basicchat.format.all", 4)) {
            messageComponent = ADMIN_MINIMESSSAGE_DESERIALIZER.deserialize(playerMessage);
        } else if (Permissions.check(player, "basicchat.format.standard", 1)) {
            messageComponent = STANDARD_MINIMESSAGE_DESERIALIZER.deserialize(playerMessage);
        } else {
            messageComponent = plainTextComponentSerializer.deserialize(playerMessage);
        }
        return ChatComponentPlaceholderReplacer.makeComponent(prefixComponent, suffixComponent, playerNameComponent, messageComponent);
    }
}
