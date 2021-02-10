package Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Plugin.MessageManager.MessageType;

public class Vanish implements CommandExecutor, Listener{

	private Main plugin;
	public Vanish(Main plugin) {
		this.plugin = plugin;
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		File vanished = new File(plugin.getDataFolder() + File.separator + "vanished.yml");
		FileConfiguration users = YamlConfiguration.loadConfiguration(vanished);
		List<String> vanishedlist = users.getStringList("users");
		Player p = (Player) sender;
		if(!(sender instanceof Player)) {
			sender.sendMessage("§cOnly for player usage");
			return true;
		}
		if(!p.hasPermission("str.vanish")) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "У вас нет прав для использования невидимости!");
			return true;
		}
		if(!vanishedlist.contains(p.getName())) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 9999, 2), true);
			for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
				if(!pls.hasPermission("str.vanish.see")) {
					pls.hidePlayer(p);
				}
			}
			vanishedlist.add(p.getName());
			users.set("users", vanishedlist);
			try {
				users.save(vanished);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			MessageManager.getManager().msg(sender, MessageType.GOOD, "Вы вошли в невидимость!");
			return true;
		}
		else {
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
			for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
				pls.showPlayer(p);
			}
			vanishedlist.remove(p.getName());
			users.set("users", vanished);
			try {
				users.save(vanished);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			MessageManager.getManager().msg(sender, MessageType.GOOD, "Вы вышли из невидимости!");
			return true;
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
		public void onPlayerjoin(PlayerJoinEvent e){
		File vanished = new File(plugin.getDataFolder() + File.separator + "vanished.yml");
		FileConfiguration users = YamlConfiguration.loadConfiguration(vanished);
		List<String> vanishedlist = users.getStringList("users");
		Player p = e.getPlayer();
				for (String v : vanishedlist) {
					if(!p.hasPermission("str.vanish.see")) {
						p.hidePlayer(Bukkit.getPlayerExact(v));
					}
					
				}
	}
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		File vanished = new File(plugin.getDataFolder() + File.separator + "vanished.yml");
		FileConfiguration users = YamlConfiguration.loadConfiguration(vanished);
		List<String> vanishedlist = users.getStringList("users");
		vanishedlist.remove(e.getPlayer().getName());
		users.set("users", vanishedlist);
		try {
			users.save(vanished);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
