package Plugin.Fixes;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Plugin.Main;
import net.minecraft.server.v1_16_R3.NBTTagCompound;

public class Checkers implements Listener {
	private Main plugin;
	public Checkers(Main main) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void onInvClick(InventoryClickEvent event) {
        if (event.getWhoClicked().getType() != EntityType.PLAYER) return;
        final Player p = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null) return;
        if (checkItem(event.getCurrentItem(), p)) {
            event.setCancelled(true);
            
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void onDrop(PlayerDropItemEvent event) {
        final Player p = event.getPlayer();
        if (event.getItemDrop() == null) return;
        if (checkItem(event.getItemDrop().getItemStack(), p)) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false) 
    public void onSlotChange(PlayerItemHeldEvent event) {
        Player p = event.getPlayer();
        ItemStack stack = p.getInventory().getItem(event.getNewSlot());
        if (checkItem(stack, p)) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        for (ItemStack stack : event.getPlayer().getInventory().getContents()) {
            checkItem(stack, event.getPlayer());
        }
    }



    public boolean checkItem(ItemStack stack, Player p) {
    boolean cheat = false;
    if(stack == null) return cheat;
    	if(isEnchantsOp(stack, p)) {
    		cheat = true;
    	}
	return cheat;
    }


    
    private boolean isEnchantsOp (ItemStack item, Player p) {
    	boolean overpower = false;
    	if (p.hasPermission("str.bypass.enchant") || (p.hasPermission("str.bypass.*"))) return false;
    	if (p.isOp()) return false;
    	if (item.getType() == Material.AIR) return false;
    	if ( ( !item.hasItemMeta() ) )return false;
    		if(item.getItemMeta().hasEnchants()){
    		ItemMeta meta = item.getItemMeta();
    		Map<Enchantment, Integer> enchantments = (item.getItemMeta().getEnchants());
    		for (Map.Entry<Enchantment, Integer> entry: enchantments.entrySet()) {
    			Enchantment currentEnchant =entry.getKey();
    			Integer value = entry.getValue();
    			if(value> currentEnchant.getMaxLevel()) {
    				meta.removeEnchant(currentEnchant);
        			meta.addEnchant(currentEnchant, currentEnchant.getMaxLevel(), true);
        			item.setItemMeta(meta);
    			}
    		}
    	}
    
    
    return overpower;
    }
    
   /* private boolean checkLGBT(net.minecraft.server.v1_16_R3.ItemStack item2, Player p) {
    	net.minecraft.server.v1_16_R3.ItemStack item = p.getInventory().getItemInMainHand();
    	boolean cheat = false;
    	if (p.hasPermission("str.bypass.nbt") || (p.hasPermission("str.bypass.*"))) return false;
    	if (p.isOp()) return false;
    	if(!item.hasTag()) return cheat;
    	NBTTagCompound tag =item.getTag();
    	Bukkit.broadcastMessage("tag= "+tag.toString()); //”¡–¿“‹ œŒ«∆≈
    	
    	return cheat;
    } */
    
private boolean checkEnchants1(ItemStack stack, Player p) {
    boolean cheat = false;
    if (!p.hasPermission("str.bypass.enchant") && stack.hasItemMeta() && stack.getItemMeta().hasEnchants()) {
        final ItemMeta meta = stack.getItemMeta();
        Map<Enchantment, Integer> enchantments = null;
        Bukkit.broadcastMessage("checkEnchants checked "+ cheat);
        try {
            enchantments = meta.getEnchants();
        } catch (Exception e) {
            p.updateInventory();
            //p.getInventory().clear();
            return true;
        }
        for (Map.Entry<Enchantment, Integer> ench : enchantments.entrySet()) {
            Enchantment enchant = ench.getKey();
            String perm = "itemfixer.allow."+stack.getType().toString()+"."+enchant.getName()+"."+ench.getValue();
                meta.removeEnchant(enchant);
                cheat = true;
            
            if ((ench.getValue() > enchant.getMaxLevel() || ench.getValue() < 0) && !p.hasPermission(perm)) {
                meta.removeEnchant(enchant);
                cheat = true;
            }
        }
        if (cheat) stack.setItemMeta(meta);
    }
    Bukkit.broadcastMessage("checkEnchants HAVEN't checked "+ cheat);
    return cheat;
}


}
