package com.FriedTaco.taco.MiningTNT;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.*;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;



public class MiningTNTEntityListener implements Listener
{
	int r = 2;
	

	private final MiningTNT plugin;

    public MiningTNTEntityListener(final MiningTNT instance) {
        plugin = instance;
    }
    public int getAmount(Block block)
    {
    	if(block.getTypeId() == 73 || block.getTypeId() == 74 || block.getTypeId() == 21)
    	{
    	    return (int)((Math.random()*10)%5);
    	}
    	else
    	{
    		return 1;
    	}
    		
    }
    public int getDrop(Block block)
    {
    	if(block.getTypeId() == 1 || block.getTypeId() == 4)
    	{
    	    return 4;
    	}
    	else if(block.getTypeId() == 2 || block.getTypeId() == 3 || block.getTypeId() == 60)
    	{
    		return 3;
    	}
    	if(block.getTypeId() == 16)
    	{
    	    return 263;
    	}
    	if(block.getTypeId() == 21)
    	{
    	    return 351;
    	}
    	if(block.getTypeId() == 73 || block.getTypeId() == 74)
    	{
    	    return 331;
    	}
    	else if(block.getTypeId() == 18)
    	{
    		if(Math.random() < .1)
    		{
    			return 6;
    		}
    		else
    		{
    			return 0;
    		}
    	}
    	else if(block.getTypeId() == 20)
    	{
    		return -1;
    	}
    	else if(block.getTypeId() == 7 || block.getTypeId() == 49)
    	{
    		return 0;
    	}
    	else
    	{
    		return block.getTypeId();
    	}
    }
    public void mine(EntityExplodeEvent event)
    {
    	if(event.isCancelled())
    	{
    		return;
    	}
    	else if(event.getEntity()==null)
    	{
    		return;
    	}
    	event.getLocation().getWorld().createExplosion(event.getLocation(), 0.0F);
    	event.setCancelled(true);
    	List<Block> blocks = event.blockList();
    	for(int i=0; i< blocks.size(); i++)
    	{
    		Block block = blocks.get(i);
    		if(block!=null)
    		{
	    		if(plugin.destroy.contains(Integer.toString(block.getTypeId())) && block.getTypeId() != 0  && block.getY() < plugin.max)
	    		{    			
	    			
	    			Location pos = block.getLocation();
	    			if(block.getTypeId()==46)
	    			{
	    				block.getWorld().spawn(pos,TNTPrimed.class);
	    				block.breakNaturally();
	    			}
	    			if((Math.random() < plugin.yield))
	    				block.breakNaturally();
	    			/* This is no longer needed.
	    			Block block = blocks.get(i);
	    			Location pos = block.getLocation();
	    			int Id = getDrop(block);
	    			int amount = getAmount(block);
	    			ItemStack item = new ItemStack(Id, amount);
	    			
	    			if(Id != 0)
	    			{
	    				if(Id != -1)
	    				{
	    					if(block.getTypeId() == 21)
	    		    			item.setDurability((short) 4);
	    					if(block.getTypeId() == 46 && plugin.chain)
	    						block.getWorld().spawn(pos,TNTPrimed.class);
	    					else if(!MiningTNT.isConflict && (Math.random() < plugin.yield))
	    						block.getWorld().dropItemNaturally(pos, item);
	    				}
	    				block.setTypeId(0);
	    			}
	    			*/
	    		}
    		}
    	}
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityExplode(EntityExplodeEvent event)
    {
    	if(event.getEntity() instanceof Creeper)
    	{
    		if(plugin.creeper)
    		{
    			event.setCancelled(true);
    			if(plugin.miningCreeper)
        		{
        			mine(event);
        		}
    		}
    	}
    	else
    	{
    		mine(event);
    	}
    }

	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setCancelled(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
}
