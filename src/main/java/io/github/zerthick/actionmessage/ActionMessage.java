/*
 * Copyright (C) 2017  Zerthick
 *
 * This file is part of ActionMessage.
 *
 * ActionMessage is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * ActionMessage is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ActionMessage.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zerthick.actionmessage;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import io.github.zerthick.actionmessage.events.ListenerRegister;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

@Plugin(
        id = "actionmessage",
        name = "ActionMessage",
        description = "A simple plugin to send messages to players on completing actions",
        version = "1.1.0",
        authors = {
                "Zerthick"
        }
)
public class ActionMessage {

    @Inject
    private Logger logger;
    @Inject
    private PluginContainer instance;
    @Inject
    @DefaultConfig(sharedRoot = true)
    private Path defaultConfig;

    private Map<String, Text> blockBreakMessages;
    private Map<String, Text> blockPlaceMessages;
    private Map<String, Text> blockInteractPrimaryMessages;
    private Map<String, Text> blockInteractSecondaryMessages;

    public Logger getLogger() {
        return logger;
    }

    public PluginContainer getInstance() {
        return instance;
    }

    @Listener
    public void onServerInit(GameInitializationEvent event) {
        ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(defaultConfig).build();

        // Generate default config if it doesn't exist
        if (!defaultConfig.toFile().exists()) {
            Asset defaultConfigAsset = getInstance().getAsset("ActionMessageDefaultConfig.conf").get();
            try {
                defaultConfigAsset.copyToFile(defaultConfig);
                configLoader.save(configLoader.load());
            } catch (IOException e) {
                logger.warn("Error loading default config! Error: " + e.getMessage());
            }
        }

        // Load Messages
        try {
            CommentedConfigurationNode messagesNode = configLoader.load().getNode("messages");

            // Load Block Messages
            CommentedConfigurationNode blockMessagesNode = messagesNode.getNode("block");

            Map<String, String> breakMessages = blockMessagesNode.getNode("break").getValue(new TypeToken<Map<String, String>>() {});
            blockBreakMessages = breakMessages.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> TextSerializers.FORMATTING_CODE.deserialize(e.getValue())));

            Map<String, String> placeMessages = blockMessagesNode.getNode("place").getValue(new TypeToken<Map<String, String>>() {});
            blockPlaceMessages = placeMessages.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> TextSerializers.FORMATTING_CODE.deserialize(e.getValue())));

            Map<String, String> interactPrimaryMessages = blockMessagesNode.getNode("interact", "primary").getValue(new TypeToken<Map<String, String>>() {});
            blockInteractPrimaryMessages = interactPrimaryMessages.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> TextSerializers.FORMATTING_CODE.deserialize(e.getValue())));

            Map<String, String> interactSecondaryMessages = blockMessagesNode.getNode("interact", "secondary").getValue(new TypeToken<Map<String, String>>() {});
            blockInteractSecondaryMessages = interactSecondaryMessages.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> TextSerializers.FORMATTING_CODE.deserialize(e.getValue())));

        } catch (ObjectMappingException | IOException e) {
            logger.warn("Error loading config! Error: " + e.getMessage());
        }

        // Register Event Listeners
        ListenerRegister.registerListeners(this);
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        getLogger().info(
                getInstance().getName() + " version " + getInstance().getVersion().orElse("")
                        + " enabled!");
    }

    @Listener
    public void onServerReload(GameReloadEvent event) {
        ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(defaultConfig).build();

        // Load New Messages
        try {
            CommentedConfigurationNode messagesNode = configLoader.load().getNode("messages");

            // Load Block Messages
            CommentedConfigurationNode blockMessagesNode = messagesNode.getNode("block");

            Map<String, String> breakMessages = blockMessagesNode.getNode("break").getValue(new TypeToken<Map<String, String>>() {});
            blockBreakMessages = breakMessages.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> TextSerializers.FORMATTING_CODE.deserialize(e.getValue())));

            Map<String, String> placeMessages = blockMessagesNode.getNode("place").getValue(new TypeToken<Map<String, String>>() {});
            blockPlaceMessages = placeMessages.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> TextSerializers.FORMATTING_CODE.deserialize(e.getValue())));

            Map<String, String> interactPrimaryMessages = blockMessagesNode.getNode("interact", "primary").getValue(new TypeToken<Map<String, String>>() {});
            blockInteractPrimaryMessages = interactPrimaryMessages.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> TextSerializers.FORMATTING_CODE.deserialize(e.getValue())));

            Map<String, String> interactSecondaryMessages = blockMessagesNode.getNode("interact", "secondary").getValue(new TypeToken<Map<String, String>>() {});
            blockInteractSecondaryMessages = interactSecondaryMessages.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> TextSerializers.FORMATTING_CODE.deserialize(e.getValue())));

        } catch (ObjectMappingException | IOException e) {
            logger.warn("Error loading config! Error: " + e.getMessage());
        }

        // Unregister Old Listeners
        ListenerRegister.unregisterListeners();

        // Register New Listeners
        ListenerRegister.registerListeners(this);
    }

    public Map<String, Text> getBlockBreakMessages() {
        return blockBreakMessages;
    }

    public Map<String, Text> getBlockPlaceMessages() {
        return blockPlaceMessages;
    }

    public Map<String, Text> getBlockInteractPrimaryMessages() {
        return blockInteractPrimaryMessages;
    }

    public Map<String, Text> getBlockInteractSecondaryMessages() {
        return blockInteractSecondaryMessages;
    }
}
