package com.codeni.industrotech;

import com.codeni.industrotech.registry.ModBlocks;
import com.codeni.industrotech.registry.ModItems;
import net.fabricmc.api.ModInitializer;

public class IndustroTech implements ModInitializer {
    public static final String MOD_ID ="indutech";
    @Override
    public void onInitialize() {
        ModItems.registerItems();
        ModBlocks.registerBlocks();
    }
}
