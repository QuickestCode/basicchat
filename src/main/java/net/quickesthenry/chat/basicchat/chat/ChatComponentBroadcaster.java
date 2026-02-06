package net.quickesthenry.chat.basicchat.chat;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public class ChatComponentBroadcaster {
    public static void broadcast(@NotNull Component component, @NotNull MinecraftServer server) {
        for (ServerPlayerEntity player: server.getPlayerManager().getPlayerList()) {
            ((Audience) player).sendMessage(component);
        }
        ((Audience) server).sendMessage(component);
    }
}
