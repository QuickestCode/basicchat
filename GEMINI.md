# Project Overview

This is a Fabric mod for Minecraft that provides chat formatting capabilities using the LuckPerms and Adventure APIs. The mod intercepts chat messages and formats them according to the player's LuckPerms prefix and suffix. The chat messages can be formatted using MiniMessage tags.

## Main Technologies

*   **Java 21**
*   **Gradle**
*   **Fabric**
*   **LuckPerms API**
*   **Adventure API**

## Architecture

The main entry point of the mod is the `Basicchat` class, which implements `ModInitializer`. The `onInitialize` method registers the chat formatter and commands.

The `ChatFormatter` class intercepts chat messages using the `ServerMessageEvents.ALLOW_CHAT_MESSAGE` event. It retrieves the player's prefix and suffix from the LuckPerms API and then uses the `ChatComponentMaker` to create the final chat component.

The `ChatComponentMaker` class creates the chat component using the Adventure API. It replaces placeholders in the configured message format with the player's prefix, suffix, username, and message.

The `ConfigManager` class manages the mod's configuration. The configuration is stored in the `basicchat.conf` file in the Fabric config directory.

# Building and Running

## Building

To build the project, run the following command:

```bash
./gradlew build
```

The compiled mod will be located in the `build/libs` directory.

## Running

To run the project, you will need to set up a Fabric server and place the compiled mod in the `mods` folder.

# Development Conventions

## Code Style

The project follows standard Java conventions.

## Testing

There are currently no tests in the project.

## Contribution Guidelines

There are currently no contribution guidelines for the project.
