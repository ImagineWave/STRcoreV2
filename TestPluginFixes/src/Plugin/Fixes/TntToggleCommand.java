package Plugin.Fixes;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import Plugin.Main;
import Plugin.MessageManager;
import Plugin.MessageManager.MessageType;
import net.md_5.bungee.api.ChatColor;

public class TntToggleCommand implements CommandExecutor, Listener{
	
	private Main plugin;
	public TntToggleCommand(Main plugin) {
		this.plugin = plugin;
	}
	
	private static boolean tntToggle = false;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isOp()) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "У вас нет прав");
			return true;
		}
		if (TntToggleCommand.tntToggle) {
			TntToggleCommand.tntToggle = false;
			MessageManager.getManager().msg(sender, MessageType.INFO, "Вы ВКЛЮЧИЛИ взрывы");
			Bukkit.broadcastMessage(ChatColor.GREEN + "Взрывы ВКЛЮЧЕНЫ");
			return true;
		}
		if (!TntToggleCommand.tntToggle) {
			TntToggleCommand.tntToggle = true;
			MessageManager.getManager().msg(sender, MessageType.INFO, "Вы ВЫКЛЮЧИЛИ взрывы");
			Bukkit.broadcastMessage(ChatColor.DARK_RED + "Взрывы ВЫКЛЮЧЕНЫ");
			return true;
		}
		return true;
	}

	@EventHandler
	public void onTnt(ExplosionPrimeEvent e) {
		if (TntToggleCommand.tntToggle) {
			e.setCancelled(true);
		}
	}
}
