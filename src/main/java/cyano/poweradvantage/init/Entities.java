package cyano.poweradvantage.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import cyano.poweradvantage.PowerAdvantage;
import cyano.poweradvantage.machines.*;

public abstract class Entities {


	private static boolean initDone = false;
	public static void init(){
		if(initDone) return;
		
		Blocks.init();

		GameRegistry.registerTileEntity(FluidDrainTileEntity.class,PowerAdvantage.MODID+".tileentity."+"fluid_drain");
		GameRegistry.registerTileEntity(FluidDischargeTileEntity.class,PowerAdvantage.MODID+".tileentity."+"fluid_discharge");
		
		// TODO: block GUIs
		
		initDone = true;
	}
}
