package com.c2h6s.tinkers_advanced_utilities.utils;

import net.minecraft.util.Mth;

import java.util.Random;

public class CommonUUtils {
    public static int processConsumptionInt(int consumption,float efficiency){
        efficiency = Math.min(efficiency,1);
        float mul = 1-efficiency;
        float f = consumption*mul;
        int i = (int) f;
        f-=i;
        Random random = new Random();
        return i + (random.nextFloat()<f?1:0);
    }
}
