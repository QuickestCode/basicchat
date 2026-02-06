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
import net.quickesthenry.chat.basicchat.chat.ChatFormatter;
import net.quickesthenry.chat.basicchat.commands.ModCommands;

public class Basicchat implements ModInitializer {
    @Override
    public void onInitialize() {
        ModCommands.registerCommands();
        ChatFormatter.registerChatFormatting();
    }
}
