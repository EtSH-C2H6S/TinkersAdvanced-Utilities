package com.c2h6s.tinkers_advanced_utilities.content.tool.modifiers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierTraitHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import java.util.List;

public class FuelEngraved extends Modifier implements ModifierTraitHook {
    public static final ResourceLocation KEY_EXTRA_MODIFIERS = TinkersAdvanced.getLocation("extra_modifiers");
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MODIFIER_TRAITS);
    }

    @Override
    public void addTraits(IToolContext context, ModifierEntry modifier, TraitBuilder builder, boolean firstEncounter) {
        if (!firstEncounter) return;
        var extraMods = ModifierNBT.readFromNBT(context.getPersistentData().get(KEY_EXTRA_MODIFIERS));
        extraMods.forEach(builder::add);
    }
}
