/*
 * Copyright (C) 2015  Zerthick
 *
 * This file is part of ProtectionPerms.
 *
 * ProtectionPerms is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * ProtectionPerms is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ProtectionPerms.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zerthick.actionmessage.events.listeners.block;


import io.github.zerthick.actionmessage.ActionMessage;
import io.github.zerthick.actionmessage.events.listeners.ListenerManager;
import io.github.zerthick.actionmessage.events.listeners.block.changeBlockEvent.BreakListener;
import io.github.zerthick.actionmessage.events.listeners.block.changeBlockEvent.PlaceListener;
import io.github.zerthick.actionmessage.events.listeners.block.interactBlockEvent.PrimaryListener;
import io.github.zerthick.actionmessage.events.listeners.block.interactBlockEvent.SecondaryListener;

import java.util.LinkedList;
import java.util.List;

public class BlockListenerManager implements ListenerManager {

    private List<Object> listeners;

    public BlockListenerManager(ActionMessage plugin) {
        listeners = new LinkedList<>();
        listeners.add(new BreakListener(plugin));
        listeners.add(new PlaceListener(plugin));
        listeners.add(new PrimaryListener(plugin));
        listeners.add(new SecondaryListener(plugin));
    }

    @Override
    public List<Object> getListeners() {
        return listeners;
    }
}
