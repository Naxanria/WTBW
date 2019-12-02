package com.wtbw.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

/*
  @author: Naxanria
*/
public class WTBWConfig {
    private static CommonConfig common;
    private static ForgeConfigSpec commonSpec;

    private static ClientConfig client;
    private static ForgeConfigSpec clientSpec;

    public static void initCommon() {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        commonSpec = specPair.getRight();
        common = specPair.getLeft();
    }

    public static void initClient() {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        clientSpec = specPair.getRight();
        client = specPair.getLeft();
    }

    public static void register(final ModLoadingContext context) {
        initCommon();
        initClient();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(WTBWConfig::reload);

        context.registerConfig(ModConfig.Type.COMMON, commonSpec);
        context.registerConfig(ModConfig.Type.CLIENT, clientSpec);
    }

    public static void reload(ModConfig.ModConfigEvent event) {
        ModConfig config = event.getConfig();

        if (config.getSpec() == commonSpec) {
            common.reload();
        } else if (config.getSpec() == clientSpec) {
            client.reload();
        }

    }
}
