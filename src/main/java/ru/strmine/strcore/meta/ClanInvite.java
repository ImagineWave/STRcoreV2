package ru.strmine.strcore.meta;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ru.strmine.strcore.Main;
import ru.strmine.strcore.MessageManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class ClanInvite implements CommandExecutor{
	private Main plugin;
	public ClanInvite(Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("str.clanmember")) {
			MessageManager.getManager().msg(sender, MessageManager.MessageType.BAD, "Вы не состоите в клане");
			return true;
		}
		Player p = (Player) sender;
		if(!(checkOwner(p)||checkOfficer(p))) {
			MessageManager.getManager().msg(sender, MessageManager.MessageType.BAD, "У вас нет прав");
			return true;
		}
		if(args.length!=1) {
			MessageManager.getManager().msg(sender, MessageManager.MessageType.BAD, "Использование /claninvite <Ник>");
			return true;
		}
		if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayerExact(args[0]))) {
			MessageManager.getManager().msg(sender, MessageManager.MessageType.BAD, "Игрока §6"+args[0]+"§c нет в сети");
			return true;
		}
		Player t = Bukkit.getPlayerExact(args[0]);
		if(p==t) {
			MessageManager.getManager().msg(sender, MessageManager.MessageType.BAD, "Вы не можете пригласить сами себя");
			return true;
		}
		PlayerInvitesTarget(p, t);
		return true;
	}
	private void PlayerInvitesTarget(Player p, Player t) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String name = getClanName(p.getName());
		c.set("Players."+t.getName()+".invite",name);
		try {
			c.save(clans);
		} catch (IOException e) {
			e.printStackTrace();
		}
		TextComponent tc = new TextComponent();
		t.sendMessage("§aВы были приглашены в клан §6"+name+" §aигроком §6"+p.getName());
		p.sendMessage("§aВы приглаcили в клан §6"+name+" §aигрока §6"+t.getName());
		tc.setText("§2§l[ПРИНЯТЬ]");
		tc.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/clanaccept")); 
		t.spigot().sendMessage(tc);
		TextComponent tc2 = new TextComponent();
		tc2.setText("§4§l[ОТКЛОНИТЬ]");
		tc2.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/clandecline"));
		t.spigot().sendMessage(tc2);
	}
	private String getClanName(String p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String output = c.getString("Players."+p+".clan");
		return output;
	}
	private boolean checkOwner(Player p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String clanName = getClanName(p.getName());
		String ownerName = c.getString("Clans."+clanName+".owner");
		return p.getName().equals(ownerName);
	}
	private boolean checkOfficer(Player p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String clanName = getClanName(p.getName());
		List<String> officers = c.getStringList("Clans."+clanName+".officers");
		return officers.contains(p.getName());
	}
	
}
