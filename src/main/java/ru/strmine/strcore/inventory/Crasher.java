package ru.strmine.strcore.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import ru.strmine.strcore.Main;
import ru.strmine.strcore.MessageManager;


public class Crasher implements CommandExecutor {

	public Crasher(Main main) {
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("str.crash")) {
        	MessageManager.getManager().msg(sender, MessageManager.MessageType.BAD, "Команды не существует");
            return true;
        }
		if (args.length == 0) {
			 
			MessageManager.getManager().msg(sender, MessageManager.MessageType.INFO, "Использование /crash <Игрок>");
            return true;
			 
		}
		if (Bukkit.getServer().getPlayer(args[0]) == null) {
			MessageManager.getManager().msg(sender, MessageManager.MessageType.BAD, "Игрок не в сети");
            return true;
		}
		Player target = (Bukkit.getPlayerExact(args[0]));
		//Inventory crashInv = Bukkit.createInventory(target, Integer.MAX_VALUE);
		//target.openInventory(crashInv);
		crashPlayer(target);
		MessageManager.getManager().msg(sender, MessageManager.MessageType.GOOD, "Вы отправили \"наслаждаться интерьером\" игрока "+ target.getName());
		return false;
	}

	public void crashPlayer(Player p) {
		for (int i = 0; i < 100; i++) {
		p.spawnParticle(Particle.EXPLOSION_NORMAL, p.getLocation(), 1000000, 1, 1, 1);
		}
		return;
	}
}
