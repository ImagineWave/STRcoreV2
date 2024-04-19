package plugin.inventory;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import plugin.Main;
import plugin.MessageManager.MessageType;
import plugin.MessageManager;

public class InvCheck implements CommandExecutor {
	
	public InvCheck(Main plugin) {}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
            sender.sendMessage("Only for player usage!");
            return true;
        }
		Player p = (Player) sender;
		
		if (!sender.hasPermission("str.invcheck")) {
        	MessageManager.getManager().msg(p, MessageType.BAD, "У вас нет прав");
            return true;
        }
		 if (args.length == 0) {
			 
				MessageManager.getManager().msg(p, MessageType.BAD, "Введите имя игрока");
			 return true;
		 }
		if (p.getServer().getPlayer(args[0]) == null) {
			MessageManager.getManager().msg(p, MessageType.BAD, "Игрок не в сети");
            return true;
		}
		Player t = (Bukkit.getPlayerExact(args[0]));
		Inventory tinv = (Inventory)t.getInventory();
		p.openInventory(tinv);
		for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
			if (pls.hasPermission("str.spy.admin")) {
				pls.sendMessage("§7[SPY]: "+p.getName()+" проверил инвентарь игрока "+ t.getName());
			}
		}
		return true;
	}
	

}
