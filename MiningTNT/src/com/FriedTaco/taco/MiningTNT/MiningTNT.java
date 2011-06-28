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
	import org.bukkit.event.Event.Priority;
	import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
	import org.bukkit.plugin.PluginDescriptionFile;
	import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;


	public class MiningTNT extends JavaPlugin { 
	    private final MiningTNTEntityListener entityListener = new MiningTNTEntityListener(this);
	    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	    private Logger log;
	    public static List<String> destroy = new ArrayList<String>();
	    double max;
	    public static boolean isConflict=false;
	    boolean creeper,miningCreeper;
	    
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
	        pm.registerEvent(Event.Type.ENTITY_EXPLODE, entityListener, Priority.High, this);
	        PluginDescriptionFile pdfFile = this.getDescription();
	        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	        loadSettings();
	        checkForConflict();
	    }
	    public void loadSettings() {
	        String folderDir = "";
	        if (!new File("MiningTNT.properties").exists()) {
	            FileWriter writer = null;
	            try {
	                writer = new FileWriter("MiningTNT.properties");
	                writer.write("Mining TNT v 0.1 configuration\r\n");
	                writer.write("Add item ID's sparated by spaces for them to be affected by TNT.\r\n");
	                writer.write("Example:\r\n");
	                writer.write("//DestroyTheseBlocks=1 2 3 4 17\r\n");
	                writer.write("DestroyTheseBlocks=\r\n");
	                writer.write("MaxAltitude=70\r\n");
	                writer.write("CreeperNerf=true\r\n");
	                writer.write("CreepersActLikeTNT=false\r\n");
	                
	                
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
	        
	        PropertiesFile properties = new PropertiesFile(folderDir
	                + "MiningTNT.properties");
	        try {
	          destroy = Arrays.asList(properties.getString("DestroyTheseBlocks", "").split(" "));
	          max = properties.getDouble("MaxAltitude", 70);
	          creeper = properties.getBoolean("CreeperNerf", true);
	          miningCreeper = properties.getBoolean("CreepersActLikeTNT", false);
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




