package com.haineko0407.magicspellmod.item;

import com.haineko0407.magicspellmod.MagicSpellMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class magicSpellModItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MagicSpellMod.MODID);
    public static final RegistryObject<Item> WAND = ITEMS.register("wand", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPELL_BOOK = ITEMS.register("spell_book", () -> new spellBook(new Item.Properties()));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
