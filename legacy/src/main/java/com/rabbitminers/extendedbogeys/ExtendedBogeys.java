package com.rabbitminers.extendedbogeys;

import com.mojang.logging.LogUtils;
import com.rabbitminers.extendedbogeys.bogey.styles.BogeyStyles;
import com.rabbitminers.extendedbogeys.bogey.styles.content.DefaultStyle;
import com.rabbitminers.extendedbogeys.bogey.styles.content.FourWheelBogey;
import com.rabbitminers.extendedbogeys.bogey.styles.content.SingleAxisBogey;
import com.rabbitminers.extendedbogeys.bogey.styles.content.SixWheelBogey;
import com.rabbitminers.extendedbogeys.config.ExtendedBogeysConfig;
import com.rabbitminers.extendedbogeys.index.BogeyPartials;
import com.rabbitminers.extendedbogeys.index.ExtendedBogeysBlocks;
import com.rabbitminers.extendedbogeys.index.ExtendedBogeysTileEntities;
import com.rabbitminers.extendedbogeys.ponder.ExtendedBogeysPonderIndex;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ExtendedBogeys.MODID)
public class ExtendedBogeys {
    public static final String MODID = "extendedbogeys";
    private static final NonNullSupplier<CreateRegistrate> registrate = CreateRegistrate.lazy(ExtendedBogeys.MODID);
    public static final Logger LOGGER = LogUtils.getLogger();

    public ExtendedBogeys() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> BogeyPartials::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> ExtendedBogeysPonderIndex::register);
        ExtendedBogeysBlocks.register();
        ExtendedBogeysTileEntities.register();
        ExtendedBogeysConfig.register(modLoadingContext);
        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

    private void setup(final FMLCommonSetupEvent event) {
        BogeyStyles.addBogeyStyle(DefaultStyle.class, ExtendedBogeys.MODID);
        BogeyStyles.addBogeyStyle(SingleAxisBogey.class, ExtendedBogeys.MODID);
        BogeyStyles.addBogeyStyle(FourWheelBogey.class, ExtendedBogeys.MODID);
        BogeyStyles.addBogeyStyle(SixWheelBogey.class, ExtendedBogeys.MODID);
        LOGGER.info("Registered bogey types from: " + ExtendedBogeys.MODID);
    }

    public static CreateRegistrate registrate() {
        return registrate.get();
    }
}
