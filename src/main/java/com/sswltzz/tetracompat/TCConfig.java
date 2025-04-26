package com.sswltzz.tetracompat;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = TetraCompat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.BooleanValue TETRACELIUM_KNIFE_COMPAT = BUILDER
            .comment("Enable the tetracelium knife compat.")
            .define("tetraceliumKnifeCompat", true);


    static final ForgeConfigSpec SPEC = BUILDER.build();
    public static boolean tetraceliumKnifeCompat;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        tetraceliumKnifeCompat = TETRACELIUM_KNIFE_COMPAT.get();
    }
}
