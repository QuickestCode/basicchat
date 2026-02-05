package net.quickesthenry.chat.basicchat;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class Basicchat implements ModInitializer {
    public static MiniMessage miniMessage = MiniMessage.builder()
            .tags(TagResolver.resolver(
                    TagResolver.resolver("click", (queue, ctx) -> {
                        // Verpackt die leere Komponente in ein Tag-Objekt
                        return net.kyori.adventure.text.minimessage.tag.Tag.inserting(Component.empty());
                    }),
                    net.kyori.adventure.text.minimessage.tag.standard.StandardTags.defaults()
            ))
            .build();
    public static MiniMessage MINIMESSAGE_ADMIN = MiniMessage.miniMessage();
    public static PlainTextComponentSerializer plainTextComponentSerializer = PlainTextComponentSerializer.plainText();
    private static boolean allowChatMessage(SignedMessage signedMessage, ServerPlayerEntity serverPlayerEntity, MessageType.Parameters parameters) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        String rawText = signedMessage.getSignedContent();
        User user = luckPerms.getUserManager().getUser(serverPlayerEntity.getUuid());
        if (user == null) return true;
        CachedMetaData metaData = user.getCachedData().getMetaData();
        if (metaData.getPrefix() == null) return true;
        String suffix = metaData.getSuffix();
        if (metaData.getSuffix() == null) suffix = "";
        Component prefix = miniMessage.deserialize(metaData.getPrefix() + " " + serverPlayerEntity.getGameProfile().name() + suffix + " <b><dark_gray>>></dark_gray></b> ");
        Component message;
        if (Permissions.check(serverPlayerEntity, "basicchat.format.all", 4)) {
            message = MINIMESSAGE_ADMIN.deserialize(rawText);
        }
        else if (Permissions.check(serverPlayerEntity, "basicchat.format.standard", 1)) {
            message = miniMessage.deserialize(rawText);
        } else {
            message = plainTextComponentSerializer.deserialize(rawText);
        }
        Component BroadcastMessage = prefix.append(message);
        MinecraftServer server = serverPlayerEntity.getEntityWorld().getServer();
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            // Each player implements Adventure's Audience interface
            ((Audience) player).sendMessage(BroadcastMessage);
        }
        ((Audience) server).sendMessage(BroadcastMessage);
        return false;
    }

    @Override
    public void onInitialize() {
        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register(Basicchat::allowChatMessage);
    }
}
