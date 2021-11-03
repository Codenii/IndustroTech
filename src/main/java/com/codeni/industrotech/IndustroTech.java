package com.codeni.industrotech;

import com.codeni.industrotech.registry.ModBlocks;
import com.codeni.industrotech.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class IndustroTech implements ModInitializer {
    public static final String MOD_ID ="indutech";

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "general"),
            () -> new ItemStack(ModItems.RUBY)
    );

    public static final ItemGroup OTHER_GROUP = FabricItemGroupBuilder.create(
            new Identifier(MOD_ID, "other"))
            .icon(() -> new ItemStack(Blocks.ENCHANTING_TABLE))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(ModBlocks.RUBY_BLOCK));
                stacks.add(new ItemStack(Items.APPLE));
                stacks.add(new ItemStack(ModItems.RUBY));
                stacks.add(new ItemStack(Items.GLOWSTONE_DUST));
            })
            .build();

    private static final Identifier EMERALD_ORE_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/emerald_ore");
    private static final Identifier RUBY_BLOCK_LOOT_TABLE_ID = new Identifier(MOD_ID, "blocks/ruby_block");

    @Override
    public void onInitialize() {
        ModItems.registerItems();
        ModBlocks.registerBlocks();
        modifyLootTables();
    }

    private void modifyLootTables() {
        LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {
            if (EMERALD_ORE_LOOT_TABLE_ID.equals(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1f))
                        .with(ItemEntry.builder(Items.GOLD_NUGGET))
                        .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 4f)).build());
                supplier.withPool(poolBuilder.build());

                FabricLootPoolBuilder poolBuilder1 = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1f))
                        .with(LootTableEntry.builder(RUBY_BLOCK_LOOT_TABLE_ID));
                supplier.withPool(poolBuilder1.build());
            }
        }));
    }
}
