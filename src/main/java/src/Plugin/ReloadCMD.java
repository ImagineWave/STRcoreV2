package main.java.src.Plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import main.java.src.Plugin.MessageManager.MessageType;

public class ReloadCMD implements CommandExecutor {

	public ReloadCMD(Main main) {
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("str.reload")) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "У вас нет прав");
			return true;
		}
		Bukkit.broadcastMessage("§a[§4§nОбъявление§a]: §bПерезагрузка сервера. Введите команду §a/login <пароль>");
		Bukkit.reload();
		return true;
	}

}
