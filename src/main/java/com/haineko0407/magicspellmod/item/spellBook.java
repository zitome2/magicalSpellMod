package com.haineko0407.magicspellmod.item;

import com.haineko0407.magicspellmod.MagicSpellMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.internal.TextComponentMessageFormatHandler;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;

public class spellBook extends Item {

    public spellBook(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> toolTipComponents, TooltipFlag isAdvanced) {
        if(itemStack.hasTag()){
            String spellKey = itemStack.getTag().getString("spellKey");
            if(spellKey!=null){
                toolTipComponents.add(Component.literal("[spell]"));
                toolTipComponents.add(Component.literal(spellKey));
            }
        }

        super.appendHoverText(itemStack,level,toolTipComponents,isAdvanced);
    }
}
