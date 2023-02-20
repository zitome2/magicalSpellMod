package com.haineko0407.magicspellmod;

import com.haineko0407.magicspellmod.item.magicSpellModItems;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

public class EventHandler {
    private ArrayList<Character> currentSpellKey = new ArrayList<>();
    @SubscribeEvent
    public void inputEvent(InputEvent e) {
        Player player = Minecraft.getInstance().player;
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();
        MouseHandler mouseHandler = Minecraft.getInstance().mouseHandler;
        player.displayClientMessage(Component.literal("s"),true);
        if(mainHandItem.getItem() == magicSpellModItems.WAND.get()) {
            if(mouseHandler.isLeftPressed()){
                currentSpellKey.add('L');
                useWand(player);
            }
            if(mouseHandler.isMiddlePressed()){
                currentSpellKey.add('M');
                useWand(player);
            }
            if(mouseHandler.isRightPressed()){
                currentSpellKey.add('R');
                useWand(player);
            }
            if(currentSpellKey.get(2)!=null){
                if(offHandItem.hasTag()) {
                    String[] spellKey = offHandItem.getTag().getString("spellKey").split("-");
                    if(currentSpellKey.get(0)==spellKey[0].toCharArray()[0]&&currentSpellKey.get(1)==spellKey[1].toCharArray()[0]&&currentSpellKey.get(2)==spellKey[2].toCharArray()[0]) {
                        player.sendSystemMessage(Component.literal("魔法発動"));
                    }
                }
                currentSpellKey = null;
            }
        }
    }

    private void useWand(Player player) {
        player.playSound(SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON,1.0F,1.0F);
        String text = "";
        if(currentSpellKey.get(0)!=null) text += currentSpellKey.get(0);
        if(currentSpellKey.get(1)!=null) text += "-" + currentSpellKey.get(1);
        if(currentSpellKey.get(2)!=null) text += "-" + currentSpellKey.get(2);
        player.displayClientMessage(Component.literal(text),true);
    }
}
