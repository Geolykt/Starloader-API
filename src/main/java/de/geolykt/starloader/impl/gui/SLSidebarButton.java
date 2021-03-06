package de.geolykt.starloader.impl.gui;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import de.geolykt.starloader.api.NullUtils;

import snoddasmannen.galimulator.ui.fl;

public class SLSidebarButton extends fl {

    protected final @NotNull Runnable action;

    public SLSidebarButton(@NotNull String textureName, int w, int h, @NotNull Runnable action) {
        super(Objects.requireNonNull(textureName), w, h);
        this.action = NullUtils.requireNotNull(action);
    }

    @Override
    protected void a(float float1, float float2) {
        action.run();
    }
}
