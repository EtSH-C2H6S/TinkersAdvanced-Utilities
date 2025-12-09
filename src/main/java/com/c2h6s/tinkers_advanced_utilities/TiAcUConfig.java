package com.c2h6s.tinkers_advanced_utilities;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class TiAcUConfig {
    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }
    public static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;

    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }
    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TiAcUConfig.commonSpec);
    }
    public static class Common{
        public final ForgeConfigSpec.IntValue ROSE_GOLD_FAUCET_SPEED;
        public final ForgeConfigSpec.IntValue HEPATIZON_FAUCET_SPEED;
        public final ForgeConfigSpec.DoubleValue ROSE_GOLD_CASTING_INCREASE;
        public final ForgeConfigSpec.DoubleValue ROSE_GOLD_CASTING_DECREASE;
        public final ForgeConfigSpec.IntValue ROSE_GOLD_TABLE_SEPARATION;
        public final ForgeConfigSpec.IntValue ROSE_GOLD_BASIN_SEPARATION;
        public final ForgeConfigSpec.DoubleValue HEPATIZON_CASTING_SPEED;

        public final ForgeConfigSpec.IntValue ADVANCED_ALLOYER_CAPACITY;
        public final ForgeConfigSpec.IntValue ADVANCED_ALLOYER_WORK_CYCLE;

        public final ForgeConfigSpec.IntValue FUEL_ENGRAVER_CAPACITY;
        public final ForgeConfigSpec.IntValue FUEL_ENGRAVER_TEMP_EACH_MODIFIER;
        public final ForgeConfigSpec.IntValue FUEL_ENGRAVER_AMOUNT_EACH_LEVEL;
        public final ForgeConfigSpec.IntValue FUEL_ENGRAVER_AMOUNT_REMOVE_ENGRAVE;
        public final ForgeConfigSpec.BooleanValue FUEL_ENGRAVER_DISPLAY_FAIL_MESSAGE;

        public Common(ForgeConfigSpec.Builder builder){
            builder.comment("***注意！").comment("***Notice")
                    .comment("这些配置并不会同步修改语言文件中的描述，当你修改了某个工具/工具属性的性质后如果想要更改描述则需要手动覆盖语言文件！")
                    .comment("This configure won't affect language display. When a certain modifier or tool is configured, please manually replace the description of the relating language contents.");
            builder.comment("平衡性和游玩").push("Gameplay");
            this.ROSE_GOLD_FAUCET_SPEED = builder.comment("Transfer speed for Rose Gold Faucet, 1000mB/t by default.")
                    .comment("玫瑰金浇筑口的传输速率，默认1000mB/t。")
                    .defineInRange("rose_gold_faucet_speed",1000,1,200000000);
            this.HEPATIZON_FAUCET_SPEED = builder.comment("Transfer speed for Hepatizon Faucet, 2000000000mB/t by default.")
                    .comment("黑色科林斯青铜浇筑口的传输速率，默认2000000000mB/t。")
                    .defineInRange("hepatizon_faucet_speed",2000000000,1,2000000000);
            this.ROSE_GOLD_CASTING_INCREASE = builder.comment("Rose Gold Casting Speed multiplier with low temp recipes,5x by default.")
                    .comment("玫瑰金铸件台处理快速配方时的速度倍率，默认5x。")
                    .defineInRange("rose_gold_casting_increase",5f,0,Integer.MAX_VALUE);
            this.ROSE_GOLD_CASTING_DECREASE = builder.comment("Rose Gold Casting Speed multiplier with high temp recipes,0.5x by default.")
                    .comment("玫瑰金铸件台处理慢速配方时的速度倍率，默认0.5x。")
                    .defineInRange("rose_gold_casting_decrease",0.5f,0,Integer.MAX_VALUE);
            this.ROSE_GOLD_TABLE_SEPARATION = builder.comment("Recipe time separator for Rose Gold Table containers,100 ticks by default.")
                    .comment("玫瑰金铸件台的快慢速分界线，默认100刻(5秒)。")
                    .defineInRange("rose_gold_table_separation",100,1,Integer.MAX_VALUE);
            this.ROSE_GOLD_BASIN_SEPARATION = builder.comment("Recipe time separator for Rose Gold Basin containers,180 ticks by default.")
                    .comment("玫瑰金铸件台的快慢速分界线，默认180刻(9秒)。")
                    .defineInRange("rose_gold_basin_separation",180,1,Integer.MAX_VALUE);
            this.HEPATIZON_CASTING_SPEED = builder.comment("Hepatizon Casting speed modifier, 3x by default.")
                    .comment("黑色科林斯青铜铸件台的速度倍率，默认3x。")
                    .defineInRange("hepatizon_casting_speed",3f,0,Integer.MAX_VALUE);

            this.ADVANCED_ALLOYER_CAPACITY = builder.comment("Advanced Alloyer fluid capacity, 16000 mB by default.")
                    .comment("液金混熔炉的流体容量，默认16000mB。太低的容量会让部分配方无法进行！")
                    .defineInRange("AdvancedAlloyerTankCapacity",16000,1000,Integer.MAX_VALUE);
            this.ADVANCED_ALLOYER_WORK_CYCLE = builder.comment("Advanced Alloyer alloying cycle, 4 ticks by default.")
                    .comment("液金混熔炉的合金工作周期，默认3 ticks，越低合金越快（原版合金炉为4）。")
                    .defineInRange("AdvancedAlloyerWorkCycle",3,2,20);

            this.FUEL_ENGRAVER_CAPACITY = builder.comment("Fuel Engraver fluid capacity, 4000 by default.")
                    .comment("热射线蚀刻机的流体容量，默认4000mB。")
                    .defineInRange("FuelEngraverCapacity",4000,1,Integer.MAX_VALUE);
            this.FUEL_ENGRAVER_TEMP_EACH_MODIFIER = builder.comment("Fuel Engraver temperature requirement each modifier, 750 by default.")
                    .comment("热射线蚀刻机每蚀刻一个工具属性需要的温度，默认750℃。多个工具属性的温度相互叠加，可以用来限制最大蚀刻属性种类数。")
                    .defineInRange("FuelEngraverTempRequirement",750,0,Integer.MAX_VALUE);
            this.FUEL_ENGRAVER_AMOUNT_EACH_LEVEL = builder.comment("Fuel Engraver fuel consumption each level, 100 by default.")
                    .comment("热射线蚀刻机的流体消耗，默认每级工具属性消耗100mB。")
                    .defineInRange("FuelEngraverFuelConsumption",100,0,Integer.MAX_VALUE);
            this.FUEL_ENGRAVER_AMOUNT_REMOVE_ENGRAVE = builder.comment("Fuel Engraver venom consumption each level when removing engrave, 10 by default.")
                    .comment("热射线蚀刻机移除蚀刻时消耗的毒液量，默认10mB。")
                    .defineInRange("FuelEngraverVenomConsumption",10,0,Integer.MAX_VALUE);
            this.FUEL_ENGRAVER_DISPLAY_FAIL_MESSAGE = builder.comment("Fuel Engraver display fail message on use, true by default.")
                    .comment("热射线蚀刻机在蚀刻失败时显示失败原因，默认true。")
                    .define("FuelEngraverDisplayFailure",true);

            builder.pop();
        }
    }
    public static class Client{
        public Client(ForgeConfigSpec.Builder builder){

        }
    }
}
