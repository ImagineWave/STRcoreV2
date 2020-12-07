package Plugin.Inventory;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import Plugin.Main;
import Plugin.MessageManager;
import Plugin.MessageManager.MessageType;

public class EndInvCheck implements CommandExecutor {

	public EndInvCheck(Main main) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
            sender.sendMessage("Only for player usage!");
            return true;
        }
		Player p = (Player) sender;
		
		if (!sender.hasPermission("str.invcheck")) {
        	MessageManager.getManager().msg(p, MessageType.BAD, "У вас нет прав для проверки инвентарей");
            return true;
        }
		 if (args.length == 0) {
			 
			 Inventory inv = (Inventory)p.getEnderChest();
			 p.openInventory(inv);
			 
			 return true;
		 }
		if (p.getServer().getPlayer(args[0]) == null) {
			MessageManager.getManager().msg(p, MessageType.BAD, "Игрок не найден");
            return true;
		}
		Player t = (Bukkit.getPlayerExact(args[0]));
		Inventory tinv = (Inventory)t.getEnderChest();
		p.openInventory(tinv);
		for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
			if (pls.hasPermission("str.spy.admin")) {
				pls.sendMessage("§7[SPY]: "+p.getName()+" Проверил эндерсундук игрока "+ t.getName());
			}
		}
		return true;
	}
	

}
