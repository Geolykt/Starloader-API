package de.geolykt.starloader.apimixins;

import java.awt.Color;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.geolykt.starloader.api.NullUtils;
import de.geolykt.starloader.api.empire.ActiveEmpire;
import de.geolykt.starloader.api.empire.Alliance;
import de.geolykt.starloader.api.event.EventManager;
import de.geolykt.starloader.api.event.alliance.AllianceJoinEvent;
import de.geolykt.starloader.api.event.alliance.AllianceLeaveEvent;
import de.geolykt.starloader.impl.AWTColorAccesor;

import snoddasmannen.galimulator.GalColor;

@SuppressWarnings("unused")
@Mixin(value = snoddasmannen.galimulator.Alliance.class)
public class AllianceMixins implements Alliance {

    @SuppressWarnings("rawtypes")
    @Shadow
    private ArrayList members;

    @Shadow
    private String name; // name

    @Shadow
    private String nameIdentifier; // fullName

    @Shadow
    private int startDate;

    @Shadow
    public void a(snoddasmannen.galimulator.Empire var1) {
        var1.a((snoddasmannen.galimulator.Alliance) (Object) this);
    } // addMember

    @Override
    public void addMember(ActiveEmpire empire) {
        a((snoddasmannen.galimulator.Empire) empire);
    }

    @Inject(method = "a", at = @At("HEAD"))
    public void addMember(snoddasmannen.galimulator.Empire member, CallbackInfo info) {
        EventManager.handleEvent(new AllianceJoinEvent((Alliance) this, (ActiveEmpire) NullUtils.requireNotNull(member)));
    }

    @Shadow
    public void b(snoddasmannen.galimulator.Empire var1) {
    } // removeMember

    @Shadow
    public @NotNull GalColor c() { // getColor
        return new GalColor(0f, 0f, 0f);
    }

    @Shadow
    public boolean c(snoddasmannen.galimulator.Empire var1) { // hasMember
        return members.contains(var1);
    }

    @SuppressWarnings("null")
    @Override
    public @NotNull String getAbbreviation() {
        return name;
    }

    @Override
    public @NotNull Color getAWTColor() {
        return ((AWTColorAccesor) getColor()).asAWTColor();
    }

    @Override
    public @NotNull GalColor getColor() {
        return c();
    }

    @Override
    public int getFoundationYear() {
        return startDate;
    }

    @SuppressWarnings("null")
    @Override
    public @NotNull String getFullName() {
        return nameIdentifier;
    }

    @SuppressWarnings({ "unchecked", "null" })
    @Override
    @NotNull
    public ArrayList<ActiveEmpire> getMembers() {
        return members;
    }

    @Override
    public boolean hasEmpire(ActiveEmpire empire) {
        return c((snoddasmannen.galimulator.Empire) empire);
    }

    @Override
    public void removeMember(ActiveEmpire empire) {
        b((snoddasmannen.galimulator.Empire) empire);
    }

    @Inject(method = "b", at = @At("HEAD"))
    public void removeMember(snoddasmannen.galimulator.Empire member, CallbackInfo info) {
        EventManager.handleEvent(new AllianceLeaveEvent((Alliance) this, (ActiveEmpire) NullUtils.requireNotNull(member)));
    }
}
