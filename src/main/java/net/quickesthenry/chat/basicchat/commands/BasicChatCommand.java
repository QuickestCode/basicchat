package net.quickesthenry.chat.basicchat.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.quickesthenry.chat.basicchat.ConfigManager;

public class BasicChatCommand {
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
                                    return 1;
                                })
                );
    }
}
