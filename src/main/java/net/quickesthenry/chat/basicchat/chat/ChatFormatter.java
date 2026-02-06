package net.quickesthenry.chat.basicchat.chat;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ChatFormatter {
    public static void registerChatFormatting() {
        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register(ChatFormatter::allowChatMessage);
    }

    private static boolean allowChatMessage(SignedMessage signedMessage, ServerPlayerEntity serverPlayerEntity, MessageType.Parameters parameters) {
        String prefix;
        String suffix;
        String message = signedMessage.getSignedContent();
        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getUserManager().getUser(serverPlayerEntity.getUuid());
        if (user == null) {
            prefix = null;
            suffix = null;
        } else {
            CachedMetaData metaData = user.getCachedData().getMetaData();
            prefix = metaData.getPrefix();
            suffix = metaData.getSuffix();
        }
        Component chatComponent;
        try {
            chatComponent = ChatComponentMaker.makeComponent(prefix, suffix, serverPlayerEntity.getGameProfile().name(), serverPlayerEntity, message);
        } catch (RequiredMetaDataNulled e) {
            e.printStackTrace();
            return true;
        }
        MinecraftServer server = serverPlayerEntity.getEntityWorld().getServer();
        if (server == null) return true;
        ChatComponentBroadcaster.broadcast(chatComponent, serverPlayerEntity.getEntityWorld().getServer());
        return false;
    }
}
