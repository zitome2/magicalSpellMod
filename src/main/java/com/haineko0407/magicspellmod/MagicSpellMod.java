package com.haineko0407.magicspellmod;

import com.haineko0407.magicspellmod.item.magicSpellModItems;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.ArrayList;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MagicSpellMod.MODID)
public class MagicSpellMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "magicspellmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public MagicSpellMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        magicSpellModItems.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab().equals(CreativeModeTabs.COMBAT)) {
            event.accept(magicSpellModItems.WAND);
            event.accept(magicSpellModItems.SPELL_BOOK);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

    private ArrayList<Character> currentSpellKey = new ArrayList<>();
    private int i = 0;
    @SubscribeEvent
    public void inputEvent(InputEvent.MouseButton e) {
        try {
            Player player = Minecraft.getInstance().player;
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();
                if (mainHandItem.getItem() == magicSpellModItems.WAND.get()) {
                    if (e.getAction()==1&&(e.getButton()==0||e.getButton()==1||e.getButton()==2)) {
                        i++;
                        if(i>=2) {
                            currentSpellKey.add(e.getButton()==0?'L':(e.getButton()==1?'R':(e.getButton()==2?'M':null)));
                            useWand(player);
                            i = 0;
                        }
                    }
                    if (currentSpellKey.size()>=3) {
                        if (offHandItem.hasTag()) {
                            String[] spellKey = offHandItem.getTag().getString("spellKey").split("-");
                            if (currentSpellKey.get(0) == spellKey[0].toCharArray()[0] && currentSpellKey.get(1) == spellKey[1].toCharArray()[0] && currentSpellKey.get(2) == spellKey[2].toCharArray()[0]) {
                                player.displayClientMessage(Component.literal("魔法発動"),false);
                            }
                        }
                        currentSpellKey.clear();
                    }
                }
        }catch (Exception er){
        }
    }

    private void useWand(Player player) {
        player.playSound(SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON,1.0F,1.0F);
        String text = "";
        if(currentSpellKey.size()>=1) text += currentSpellKey.get(0);
        if(currentSpellKey.size()>=2) text += "-" + currentSpellKey.get(1);
        if(currentSpellKey.size()>=3) text += "-" + currentSpellKey.get(2);
        player.displayClientMessage(Component.literal(text),true);
    }
}
