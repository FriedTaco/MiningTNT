package com.FriedTaco.taco.MiningTNT;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.*;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;



public class MiningTNTEntityListener extends EntityListener implements Cancellable
{
	int r = 2;
	

	private final MiningTNT plugin;

    public MiningTNTEntityListener(final MiningTNT plugin) {
        this.plugin = plugin;
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
    	event.setCancelled(true);
    	List<Block> blocks = event.blockList();
    	for(int i=0; i< blocks.size(); i++)
    	{
    		if(blocks.get(i) == null)
    		{
    		}
    		else if(com.FriedTaco.taco.MiningTNT.MiningTNT.destroy.contains(Integer.toString(blocks.get(i).getTypeId())) && blocks.get(i).getTypeId() != 0  && blocks.get(i).getY() < plugin.max)
    		{
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
    		}
    		
    	}
    }
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

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCancelled(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
}
