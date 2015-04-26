package cyano.poweradvantage;

import java.util.Locale;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cyano.poweradvantage.api.example.ExamplePowerMod;
import cyano.poweradvantage.events.BucketHandler;
import cyano.poweradvantage.registry.FuelRegistry;
import cyano.poweradvantage.registry.MachineGUIRegistry;

// NOTE: other mods dependant on this one need to add the following to their @Mod annotation:
// dependencies = "required-after:poweradvantage" 



// TODO: follow the to-do list:
/* TODO list
 * drain block ✓
 * fluid pipes ✓
 * portable tank block
 * item chute ✓
 * fluid discharge block ✓
 * example crusher machine
 * -- SteamAdvantage mod --
 * Coal-Fired Steam Boiler
 * Boiler Tank
 * Steam Conduit
 * Steam Powered Rock Crusher
 * Steam Powered Blast Furnace (expanded furnace 2x2)
 * Steam Powered Drill
 * Steam Powered Lift (pushes up special lift blocks, like an extendable piston)
 * Steam Powered Machine Shop (automatable crafter)
 * Musket (slow-loading ranged weapon)
 * Steam Powered Defense Cannon (manual aiming and requires redstone trigger)
 * Oil-Burning Steam Boiler
 * Bioreactor (slowly makes liquid fuel from organic matter)
 * -- ElectricalAdvantage --
 * Electricity Conduit
 * Steam Powered Generator
 * Combustion Generator (fuel burning)
 * Solar Generator
 * Water Turbine Generator
 * Wind Turbine Generator 
 * Electric Boiler (makes steam power from electricity)
 * Electric Battery
 * Electric Furnace
 * Electric Arc Furnace (expanded furnace 3x3)
 * Electric Rock Crusher
 * Electric Drill
 * Electric Assembler
 * Revolver Pistol (six-shots between slow reloads)
 * Electric Defense Cannon (auto-aiming and automatically attacks hostile mobs)
 * Electric Lift (electric version of steam lift)
 * Electric Tools
 * Electric Charging Station
 * Electric Robot Station
 * Robots:
 * - Farm-bot (farms)
 * - Lumber-bot (cuts and plants trees)
 * - Kill-bot (kills mobs with arrows or bullets)
 * - Drill-bot (digs)
 * - Construction-bot (builds villager homes)
 * - Chef-bot (cooks food)
 * - Fish-bot (fishes)
 * 
 * -- MagicAdvantage -- (power from colored mana)
 * Mana:
 * - Red (fire)
 * - Blue (water)
 * - White (air)
 * - Brown (earth)
 * - Purple (darkness, not found naturally in overworld)
 * - Yellow (light, not found naturally in overworld)
 * mana crystal generates in the world and slowly grow over time 
 * fully grown mana crystals are hazardous to be near and sprout new crystals in neighboring air blocks
 * mining a mana crystal drops mana
 * magical machines are powered by mana and have extra effects depending on mane used for power
 * Mana Pool (stores mana and powers near-by magic machines)
 * Mana Focus (giant crystal that extends the range of the mana pool)
 * Red Mana Condenser (turns fuel into red mana)
 * Blue Mana Condenser (generates blue mana if submerged in water)
 * Brown Mana Condenser (turns plants into brown mana)
 * White Mana Condenser (generates white mana while high in the sky)
 * Yellow Mana Condenser (generates yellow mana while sunlight shines on it)
 * Purple Mana Condenser (hurts nearby entities and generates purple mana)
 * Magical Excavator (mines the area)
 * - red mana: smelt items as they're mined
 * - blue mana: mine faster
 * - brown mana: fill mined area with dirt
 * - white mana: mine larger area
 * - yellow mana: occasionally mines a duplicate block (+10% yield)
 * - purple mana: destroy worthless blocks instead of mining them
 * Magical Conjurer (summons entities/items)
 * - red mana: random treasure chest loot item
 * - blue mana: random fishing result
 * - brown mana: random ore or crop
 * - white mana: random animal
 * - yellow mana: random mana shard
 * - purple mana: random hostile mob, occasionally from Nether
 * Magical Spirit Shrine
 * - red mana: attacks hostile mobs and smelts near-by blocks (turns trees into charcoal and cobblestone into stone)
 * - blue mana: plants crops and trees using items in nearby chests
 * - brown mana: adds mining haste effect to nearby players
 * - white mana: harvests crops and leaves and picks-up nearby items and puts them in a chest
 * - yellow mana: lights up the area (places fae lights)
 * - purple mana: hurts all mobs and steals from player inventories
 * Magical Furnace (expanded furnace 3x3)
 * - red mana: smelts more items per mana shard
 * - blue mana: unsmelts items
 * - brown mana: has small change of spitting out a random mana shard after each smelt
 * - white mana: smelts faster (but does not smelt more items per mana shard)
 * - yellow mana: has chance of not consuming input items
 * - purple mana: smelts super fast, with 12.5% loss
 * Magical Mystery Box (does stuff to items placed inside)
 * - red mana: smelts items
 * - blue mana: turns water buckets into ice blocks, lava buckets into obsidian
 * - brown mana: grows/duplicates plant items
 * - white mana: crafts blocks into fancier forms (e.g. stone into stone bricks)
 * - yellow mana: repairs items
 * - purple mana: destroys items, creating XP orbs
 * 
 * -- QuantumAdvantage -- (wireless power)
 * Fusion Generator (burns water for electricity)
 * Quantum Entanglement Generator (powered by electricity, wirelessly sends power to quantum machines and also recharges quantum batteries in player inventories)
 * Quantum Relay (range extender for Quantum Entanglement Generator)
 * Quantum Furnace (super-fast 3x3 expanded furnace)
 * Quantum Teleporter
 * Quantum Disassembler (like crusher, but also yields 1 stone block per ore)
 * Quantum Defense Cannon (automatically hurts all enemy mobs within range)
 * Quantum Quarry (like BuildCraft Quarry)
 * Quantum Farm (manages farming in nearby area)
 * Quantum Tools (powered by a quantum battery in player inventory)
 * Ray Gun (gun with no reload time, powered by a quantum battery in player inventory)
 * Private Quantum Storage Chest (like  ender chest with bigger inventory)
 * Public Quantum Storage Chest (like ender chest, but all players on same team have access)
 * Quantum Shield (worn as chestplate, provides full armor protection and repairs with quantum energy)
 * Quantum Goggles (worn as helmet, lets user see ores and entity through walls and see in the dark)
 * Quantum Leg Enhancements (worn as leggings, bestows fast speed and player can walk up 1-block height change without jumping)
 * Quantum Shoes (slow fall and air jumping, allows for primitive flight)
 * Instant *** (place on ground to make building instantly appear)
 * Instant Camp
 * Instant Farm
 * Instant House
 * Instant Castle
 */

/**
 * <p>This is the main mod class for Power Advantage. Forge/FML will create an 
 * instance of this class and call its initializer functions. There are some 
 * utility functions in this class that makers of add-on mods may wish to use, 
 * such as PowerAdvantage.getInstance(). </p>
 * <p>
 * Note that to make a mod that uses the PowerAdvantage API, you will need to make a few 
 * adjustments to add the appropriate dependancies.
 * </p><p>
 * First, you need to add the required libraries to your project. Create a folder in your project 
 * called <b>lib</b>. Then grab the PowerAdvantage and BaseMetals API .jar files and put them in 
 * the <b>lib</b> folder. It will also be helpful to put the javadoc .zip files in this folder as 
 * well.
 * </p><p>
 * Next, you need to specify the dependencies in the &#64;mod annotation. For example:<br>
 * <code>@Mod(modid = MyMod.MODID, version = MyMod.VERSION, name=MyMod.NAME, dependencies = "required-after:poweradvantage;required-after:basemetals")</code> 
 * </p><p>
 * After that, you need to update your gradle build script to include the dependencies. Add the 
 * following to <b>build.gradle</b> (adjusting the version numbers on the .jar filenames as 
 * neessary):<br><code>

allprojects {
    apply plugin: 'java'
    sourceCompatibility = 1.7
    targetCompatibility = 1.7
}

dependencies {
    compile files(
        'lib/basemetals-1.2.3-dev.jar'
        'lib/PowerAdvantage-API-1.0.0.jar'
    )

}
</code><br>This is necessary to include the API in your compile build path and also specify that you 
 * are compiling with Java 7 (Java 6 will not work). 
 * </p><p>
 * Finally, you need to add the API .jar files to your IDE project configuration. In Eclipse, 
 * right-click on your project and go to <i>Properties</i>, then click on <i>Java Build Path</i> and 
 * click on the <i>Libraries</i> tab. Then click <i>Add Jars...</i> and select the BaseMetals and 
 * PowerAdvantage API jars in your <b>lib</b> folder and close the window by clicking OK.
 * </p>
 * @author DrCyano
 *
 */
@Mod(modid = PowerAdvantage.MODID, version = PowerAdvantage.VERSION, name=PowerAdvantage.NAME, dependencies = "required-after:basemetals")
public class PowerAdvantage
{
	/** The identifier for this mod */
    public static final String MODID = "poweradvantage";
    /** The display name for this mod */
    public static final String NAME = "Power Advantage";
    /** The version of this mod, in the format major.minor.update */
    public static final String VERSION = "0.0.8";

    /** if true, example machines will be added to the game */
    @Deprecated // TODO: remove example machines
    public static boolean DEMO_MODE = false;
    /** singleton instance */
    private static PowerAdvantage instance;
    /** Demo mod content */
    @Deprecated // TODO: remove example machines
    private ExamplePowerMod exampleMod = null;
    /**
     * This is the recipe mode set for this mod. Add-on mods may want to adjust their recipes 
     * accordingly.
     */
    public static Enum recipeMode = RecipeMode.NORMAL;
    
    /**
     * Pre-initialization step. Used for initializing objects and reading the 
     * config file
     * @param event FML event object
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	instance = this;
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	String mode = config.getString("recipe_mode", "options", "NORMAL", "NORMAL, APOCALYPTIC, or TECH_PROGRESSION. \n"
    			+ "Sets the style of recipes used in your game. \n"
    			+ "In NORMAL mode, everything needed is craftable from vanilla items and the machines are \n"
    			+ "available pretty much as soon as the player returns from their first mining expedition. \n"
    			+ "In APOCALYPTIC mode, some important items are not craftable, but can be found in \n"
    			+ "treasure chests, requiring the players to pillage for their machines. \n"
    			+ "In TECH_PROGRESSION mode, important items are very complicated to craft using vanilla \n"
    			+ "items, but are easy to duplicate once they are made. This gives the players a sense of \n"
    			+ "invention and rising throught the ages from stone-age to space-age.").toUpperCase(Locale.US);
    	switch (mode){
    	case "NORMAL":
    		recipeMode = RecipeMode.NORMAL;
    		break;

    	case "APOCALYPTIC":
    		recipeMode = RecipeMode.APOCALYPTIC;
    		break;

    	case "TECH_PROGRESSION":
    		recipeMode = RecipeMode.TECH_PROGRESSION;
    		break;

    	default:
    		throw new IllegalArgumentException("'"+mode+"' is not valid for config option 'recipe_mode'. Valid options are: NORMAL, APOCALYPTIC, or TECH_PROGRESSION");
    	}
    	// demonstration code and examples
    	// TODO: default to false
    	//DEMO_MODE = config.getBoolean("demo", "options", false,
    	DEMO_MODE = config.getBoolean("demo", "options", true,
 "If true, then example machines will be loaded. For testing/example use only!");
    	
    	if(DEMO_MODE){
    		exampleMod = new ExamplePowerMod();
        	exampleMod.preInit(event, config);
    	}
    	
    	config.save();
		// TODO: have a "post-apocalypse" mode where certain key technologies are not craftable but can be found in treasure chests
		// TODO: have a "invention" mode where certain key technologies are dependant on other technologies to be crafted, forcing players to advance up a tech tree

		cyano.poweradvantage.init.Fluids.init(); 
    	cyano.poweradvantage.init.Blocks.init();
    	cyano.poweradvantage.init.Items.init();
    	
    	// keep this comment, it is useful for finding Vanilla recipes
    	//OreDictionary.initVanillaEntries();
    	if(event.getSide() == Side.CLIENT){
			clientPreInit(event);
		}
		if(event.getSide() == Side.SERVER){
			serverPreInit(event);
		}
	}
	
	@SideOnly(Side.CLIENT)
	private void clientPreInit(FMLPreInitializationEvent event){
		// client-only code
	}
	@SideOnly(Side.SERVER)
	private void serverPreInit(FMLPreInitializationEvent event){
		// client-only code
	}

    /**
     * Initialization step. Used for adding renderers and most content to the 
     * game
     * @param event FML event object
     */
    @EventHandler
    public void init(FMLInitializationEvent event)
    {

		NetworkRegistry.INSTANCE.registerGuiHandler(PowerAdvantage.getInstance(), MachineGUIRegistry.getInstance());
		GameRegistry.registerFuelHandler(FuelRegistry.getInstance());
		
		cyano.poweradvantage.init.Fuels.init();
		cyano.poweradvantage.init.Entities.init();
		cyano.poweradvantage.init.Recipes.init();
		cyano.poweradvantage.init.Villages.init(); 
		cyano.poweradvantage.init.GUI.init();
		cyano.poweradvantage.init.TreasureChests.init();
		
		MinecraftForge.EVENT_BUS.register(BucketHandler.getInstance());
		
		
    	if(DEMO_MODE)exampleMod.init(event);
    	
    	
    	if(event.getSide() == Side.CLIENT){
			clientInit(event);
		}
		if(event.getSide() == Side.SERVER){
			serverInit(event);
		}
	}
	

	@SideOnly(Side.CLIENT)
	private void clientInit(FMLInitializationEvent event){
		// client-only code
		cyano.poweradvantage.init.Items.registerItemRenders(event);
		cyano.poweradvantage.init.Blocks.registerItemRenders(event);
	}
	@SideOnly(Side.SERVER)
	private void serverInit(FMLInitializationEvent event){
		// client-only code
	}

    /**
     * Post-initialization step. Used for cross-mod options
     * @param event FML event object
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	if(DEMO_MODE)exampleMod.postInit(event);
    	
    }
	

	@SideOnly(Side.CLIENT)
	private void clientPostInit(FMLPostInitializationEvent event){
		// client-only code
	}
	@SideOnly(Side.SERVER)
	private void serverPostInit(FMLPostInitializationEvent event){
		// client-only code
	}
/**
 * Gets a singleton instance of this mod. Is null until the completion of the 
 * pre-initialization step
 * @return The global instance of this mod
 */
	public static PowerAdvantage getInstance() {
		return instance;
	}
    
}
