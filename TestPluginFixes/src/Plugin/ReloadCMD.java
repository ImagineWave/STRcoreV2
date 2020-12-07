package Plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Plugin.MessageManager.MessageType;

public class ReloadCMD implements CommandExecutor {

	public ReloadCMD(Main main) {
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("str.reload")) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "У вас нет прав для перезагрузки сервера");
			return true;
		}
		Bukkit.broadcastMessage("§r§a[§4§nОбъявление§a]: §bПерезагрузка сервера");
		Bukkit.broadcastMessage("§r§a[§4§nОбъявление§a]: §bНужно будет повторно ввести команду /login ПАРОЛЬ");
		Bukkit.reload();
		return true;
	}

}
