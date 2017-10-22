package io.github.zerthick.actionmessage.events.listeners;

import io.github.zerthick.actionmessage.ActionMessage;

public abstract class AbstractListener {

    protected ActionMessage plugin;

    public AbstractListener(ActionMessage plugin) {
        this.plugin = plugin;
    }
}
