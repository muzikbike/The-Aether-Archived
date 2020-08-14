package com.legacy.aether.containers.slots;

import com.legacy.aether.registry.AetherLore;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLore extends Slot
{
    public SlotLore(IInventory inv, int slot, int x, int y)
    {
        super(inv, slot, x, y);
    }

    public boolean isItemValid(ItemStack stack)
    {
        return I18n.hasKey(AetherLore.getLoreEntryKey(stack));
    }
}