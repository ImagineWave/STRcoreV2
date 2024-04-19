package ru.strmine.strcore.fixes;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import ru.strmine.strcore.Main;

import ru.strmine.strcore.MessageManager;

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
			MessageManager.getManager().msg(sender, MessageManager.MessageType.BAD, "У вас нет прав");
			return true;
		}
		if (TntToggleCommand.tntToggle) {
			TntToggleCommand.tntToggle = false;
			MessageManager.getManager().msg(sender, MessageManager.MessageType.INFO, "Вы ВКЛЮЧИЛИ взрывы");
			Bukkit.broadcastMessage(ChatColor.GREEN + "Взрывы ВКЛЮЧЕНЫ");
			return true;
		}
		if (!TntToggleCommand.tntToggle) {
			TntToggleCommand.tntToggle = true;
			MessageManager.getManager().msg(sender, MessageManager.MessageType.INFO, "Вы ВЫКЛЮЧИЛИ взрывы");
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
