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

package io.github.zerthick.actionmessage.events;

import io.github.zerthick.actionmessage.ActionMessage;
import io.github.zerthick.actionmessage.events.listeners.ListenerManager;
import io.github.zerthick.actionmessage.events.listeners.block.BlockListenerManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventManager;

import java.util.HashSet;
import java.util.Set;

public class ListenerRegister {

    private static Set<ListenerManager> listenerManagerSet;

    public static void registerListeners(ActionMessage plugin) {

        listenerManagerSet = new HashSet<>();
        listenerManagerSet.add(new BlockListenerManager(plugin));

        EventManager eventManager = Sponge.getGame().getEventManager();

        for(ListenerManager listenerManager : listenerManagerSet) {
            for(Object listener : listenerManager.getListeners()) {
                eventManager.registerListeners(plugin, listener);
            }
        }
    }

    public static void unregisterListeners() {
        EventManager eventManager = Sponge.getGame().getEventManager();

        for(ListenerManager listenerManager : listenerManagerSet) {
            for(Object listener : listenerManager.getListeners()) {
                eventManager.unregisterListeners(listener);
            }
        }
    }
}
