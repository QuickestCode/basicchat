package net.quickesthenry.chat.basicchat.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.quickesthenry.chat.basicchat.ConfigManager;

public class BasicChatCommand {
    private static final Component RELOAD_SUCCESS_MESSAGE = MiniMessage.miniMessage().deserialize("<white>[<green>Basic Chat</green>]</white> <gray>Successfully reloaded config.");
    public static LiteralArgumentBuilder<ServerCommandSource> getCommand() {
        return CommandManager.literal("basicchat")
                .requires(
                        predicate -> Permissions.check(predicate, "basicchat.commands.basicchat", 4)
                )
                .then(
                        CommandManager.literal("reloadconfig")
                                .requires(predicate -> Permissions.check(predicate, "basicchat.commands.reloadconfig", 4))
                                .executes(commandContext -> {
                                    ConfigManager.loadConfiguration();
                                    commandContext.getSource().sendMessage(RELOAD_SUCCESS_MESSAGE);
                                    return 1;
                                })
                );
    }
}
