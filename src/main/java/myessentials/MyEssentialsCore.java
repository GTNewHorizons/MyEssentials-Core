package myessentials;

import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import myessentials.entities.api.sign.SignManager;
import myessentials.entities.api.tool.ToolManager;
import myessentials.localization.api.Local;
import myessentials.localization.api.LocalManager;

@Mod(
        modid = "MyEssentials-Core",
        name = "MyEssentials-Core",
        version = "GRADLETOKEN_VERSION",
        dependencies = "required-after:Forge",
        acceptableRemoteVersions = "*")
public class MyEssentialsCore {

    @Instance("MyEssentials-Core")
    public static MyEssentialsCore instance;

    public Local LOCAL;
    public Logger LOG;

    @EventHandler
    public void preInit(FMLPreInitializationEvent ev) {

        LOG = ev.getModLog();
        Constants.CONFIG_FOLDER = ev.getModConfigurationDirectory().getPath() + "/MyEssentials-Core/";
        Constants.DATABASE_FOLDER = ev.getModConfigurationDirectory().getParent() + "/databases/";
        // Load Configs
        Config.instance.init(Constants.CONFIG_FOLDER + "/Core.cfg", "MyEssentials-Core");
        // REF: The localization can simply take the whole config instance to get the localization needed.
        LOCAL = new Local(
                Constants.CONFIG_FOLDER + "/localization/",
                Config.instance.localization.get(),
                "/myessentials/localization/",
                MyEssentialsCore.class);
        LocalManager.register(LOCAL, "myessentials");

        // Register handlers/trackers
        FMLCommonHandler.instance().bus().register(PlayerTracker.instance);
        MinecraftForge.EVENT_BUS.register(PlayerTracker.instance);

        FMLCommonHandler.instance().bus().register(ToolManager.instance);
        MinecraftForge.EVENT_BUS.register(ToolManager.instance);

        FMLCommonHandler.instance().bus().register(SignManager.instance);
        MinecraftForge.EVENT_BUS.register(SignManager.instance);
    }
}
