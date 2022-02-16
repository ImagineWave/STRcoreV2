package Plugin.meta;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Plugin.Main;
import Plugin.MessageManager;
import Plugin.MessageManager.MessageType;

public class ClanHelp implements CommandExecutor{
	private Main plugin;
	public ClanHelp (Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		MessageManager.getManager().msg(sender, MessageType.INFO, "/clancreate <имя клана> <клан-тег> - создать клан");
		MessageManager.getManager().msg(sender, MessageType.INFO, "/claninvite <игрок> - пригласить игрока в клан");
		MessageManager.getManager().msg(sender, MessageType.INFO, "/clanleave - выйти из клана");
		MessageManager.getManager().msg(sender, MessageType.INFO, "/clanmodify - редактировать клан");
		MessageManager.getManager().msg(sender, MessageType.INFO, "/clanchat <сообщение> или /сс <сообщение> - чат клана");
		return true;
	}

}
