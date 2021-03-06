package de.geolykt.starloader;

import org.slf4j.Logger;

import net.minestom.server.extras.selfmodification.MinestomRootClassLoader;

import de.geolykt.starloader.api.Galimulator;
import de.geolykt.starloader.api.event.EventManager;
import de.geolykt.starloader.api.event.lifecycle.SignalExtensionTerminationEvent;
import de.geolykt.starloader.api.gui.Drawing;
import de.geolykt.starloader.api.gui.SidebarInjector;
import de.geolykt.starloader.api.gui.modconf.ModConf;
import de.geolykt.starloader.impl.DrawingManager;
import de.geolykt.starloader.impl.GalimulatorConfiguration;
import de.geolykt.starloader.impl.GalimulatorImplementation;
import de.geolykt.starloader.impl.SLSidebarInjector;
import de.geolykt.starloader.impl.asm.UIASMTransformer;
import de.geolykt.starloader.mod.Extension;

/**
 * Entrypoint for the starloader API as an extension.
 * Absolutely not official API.
 */
@SuppressWarnings("resource")
public class StarloaderAPIExtension extends Extension {

    public static Logger lggr;

    @Override
    public void preInitialize() {
        lggr = this.getLogger();
        // We had to move this to preinit as some AWs are bork in SLL 2.0.0 and below, however
        // some of these versions are still supported by the current SLAPI version
        ModConf.setImplementation(new de.geolykt.starloader.impl.ModConf());
    }

    @Override
    public void unload() {
        EventManager.handleEvent(new SignalExtensionTerminationEvent(this));
        getLogger().info("SLAPI is going to bed. Let's conquer the stars tomorrow!");
    }

    static {
        Galimulator.setImplementation(new GalimulatorImplementation());
        Galimulator.setConfiguration(new GalimulatorConfiguration());
        Drawing.setImplementation(new DrawingManager());
        SidebarInjector.setImplementation(new SLSidebarInjector());
        MinestomRootClassLoader.getInstance().addCodeModifier(new UIASMTransformer());
    }
}
