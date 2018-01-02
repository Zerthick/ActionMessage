/*
 * Copyright (C) 2018  Zerthick
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

package io.github.zerthick.actionmessage.events.listeners.block.changeBlockEvent;

import io.github.zerthick.actionmessage.ActionMessage;
import io.github.zerthick.actionmessage.events.listeners.AbstractListener;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Map;

public class BreakListener extends AbstractListener {

    private Map<String, Text> messages;

    public BreakListener(ActionMessage plugin) {
        super(plugin);
        messages = plugin.getBlockBreakMessages();
    }

    @Listener
    public void onBlockBreak(ChangeBlockEvent.Break event, @Root Player player) {
        List<Transaction<BlockSnapshot>> transactions = event.getTransactions();
        for (Transaction<BlockSnapshot> transaction : transactions) {
            BlockSnapshot snapshot = transaction.getOriginal();
            String blockTypeId = snapshot.getState().getType().getId();
            String blockStateId = snapshot.getState().getId();
            if (messages.containsKey(blockStateId)) {
                player.sendMessage(messages.get(blockStateId));
            } else if(messages.containsKey(blockTypeId)) {
                player.sendMessage(messages.get(blockTypeId));
            }
        }
    }
}
