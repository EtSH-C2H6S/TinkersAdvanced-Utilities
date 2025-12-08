package com.c2h6s.tinkers_advanced_utilities.init;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;

public class TiAcUToolDefinitions {
    public static ToolDefinition name(String s){
        return ToolDefinition.create(TinkersAdvanced.getLocation(s));
    }
}
