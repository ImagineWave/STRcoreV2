package Plugin.Inventory;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;


import Plugin.Main;
import Plugin.MessageManager;
import Plugin.MessageManager.MessageType;


public class Crasher implements CommandExecutor {

	public Crasher(Main main) {
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
            sender.sendMessage("Only for player usage!");
            return true;
        }
		Player p = (Player) sender;
		if (!sender.hasPermission("str.crash")) {
        	MessageManager.getManager().msg(p, MessageType.BAD, "У вас нет прав для такой штуки");
            return true;
        }
		if (args.length == 0) {
			 
			MessageManager.getManager().msg(p, MessageType.INFO, "Использование /crash игрок");
            return true;
			 
		}
		if (p.getServer().getPlayer(args[0]) == null) {
			MessageManager.getManager().msg(p, MessageType.BAD, "Игрок не найден");
            return true;
		}
		Player target = (Bukkit.getPlayerExact(args[0]));
		//Inventory crashInv = Bukkit.createInventory(target, Integer.MAX_VALUE);
		//target.openInventory(crashInv);
		crashPlayer(target);
		MessageManager.getManager().msg(p, MessageType.GOOD, "Вы крашнули игрока "+ target.getName());
		return false;
	}

	public void crashPlayer(Player p) {
		for (int i = 0; i < 100; i++) {
		p.spawnParticle(Particle.EXPLOSION_NORMAL, p.getLocation(), 1000000, 1, 1, 1);
		}
		return;
	}
}
