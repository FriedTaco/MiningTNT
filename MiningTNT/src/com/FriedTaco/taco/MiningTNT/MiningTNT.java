package com.FriedTaco.taco.MiningTNT;

	import java.util.List;
	import java.util.logging.Level;
	import java.util.logging.Logger;
	import java.io.File;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.HashMap;
	import org.bukkit.entity.Player;
	import org.bukkit.plugin.Plugin;
	import org.bukkit.plugin.PluginDescriptionFile;
	import org.bukkit.plugin.java.JavaPlugin;
	import org.bukkit.plugin.PluginManager;




	public class MiningTNT extends JavaPlugin { 
	    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	    private Logger log = Logger.getLogger("Minecraft");
	    public List<String> destroy = new ArrayList<String>();
	    double max, yield;
	    public static boolean isConflict=false;
	    boolean creeper,miningCreeper,chain;
	    
	    private void checkForConflict()
	    {
	    Plugin conflict = this.getServer().getPluginManager().getPlugin("HigherExplosives");
	    if(conflict!=null)
	        {
	             isConflict = true;
	        }
	    }

	   

	    public void onDisable() {
	    }

	    public void onEnable() {
	        PluginManager pm = getServer().getPluginManager();
	        pm.registerEvents(new MiningTNTEntityListener(this), this);
	        PluginDescriptionFile pdfFile = this.getDescription();
	        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	        try {
				loadSettings();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        checkForConflict();
	    }
	    public void loadSettings() throws Exception
		 {
			 if (!this.getDataFolder().exists())
			 {
				 	this.getDataFolder().mkdirs();
			 }
			 final String dir = "plugins/MiningTNT";
		        if (!new File(dir + File.separator + "MiningTNT.properties").exists()) {
		            FileWriter writer = null;
		            try {
		            	writer = new FileWriter(dir + File.separator + "MiningTNT.properties");
		                writer.write("#MiningTNT configuration\r\n");
		                writer.write("#Add item ID's sparated by spaces for them to be affected by TNT.\r\n");
		                writer.write("#Example:\r\n");
		                writer.write("#DestroyTheseBlocks=1 2 3 4 17\r\n");
		                writer.write("DestroyTheseBlocks=\r\n");
		                writer.write("MaxAltitude=70\r\n");
		                writer.write("#CreeperNerf MUST be enabled if CreepersActLikeTNT is enabled.");
		                writer.write("CreeperNerf=true\r\n");
		                writer.write("CreepersActLikeTNT=false\r\n");
		                writer.write("#Allow TNT to activate other TNT within the blast radius, otherwise the TNT will be destroyed.\r\n");
		                writer.write("TNT (ID 46) must be added to list of blocks to be destroyed for TNT to chain.\r\n");
		                writer.write("AllowTNTChaining=true\r\n");
		                writer.write("#Yield is the percentage that the block is dropped rather than destroyed when in blast radius. Anything above 1 will be assumed to be 1.\r\n");
		                writer.write("Yield=0.4\r\n");
		                
		                } catch (Exception e) {
		                log.log(Level.SEVERE,
		                        "Exception while creating MiningTNT.properties", e);
		                try {
		                    if (writer != null)
		                        writer.close();
		                } catch (IOException ex) {
		                    log
		                            .log(
		                                    Level.SEVERE,
		                                    "Exception while closing writer for MiningTNT.properties",
		                                    ex);
		                }
		            } finally {
		                try {
		                    if (writer != null)
		                        writer.close();
		                } catch (IOException e) {
		                    log
		                            .log(
		                                    Level.SEVERE,
		                                    "Exception while closing writer for MiningTNT.properties",
		                                    e);
		                }
		            }
		        }
		        PropertiesFile properties = new PropertiesFile(dir + File.separator + "MiningTNT.properties");
		        try {
			          destroy = Arrays.asList(properties.getString("DestroyTheseBlocks", "").split(" "));
			          max = properties.getDouble("MaxAltitude", 70);
			          creeper = properties.getBoolean("CreeperNerf", true);
			          miningCreeper = properties.getBoolean("CreepersActLikeTNT", false);
			          chain = properties.getBoolean("AllowTNTChaining", true);
			          yield = properties.getDouble("Yield", 0.4);
		        } catch (Exception e) {
		            log.log(Level.SEVERE,
		                    "Exception while reading from MiningTNT.properties", e);
		        }

		 }

	    public boolean isDebugging(final Player player) {
	        if (debugees.containsKey(player)) {
	            return debugees.get(player);
	        } else {
	            return false;
	        }
	    }

	    public void setDebugging(final Player player, final boolean value) {
	        debugees.put(player, value);
	    }

	}




