package ru.strmine.strcore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class BedTp implements CommandExecutor{

	private static final int BEDS_PER_PAGE = 10;

	private Main plugin;

	public BedTp(Main plugin) {
		this.plugin = plugin;
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (checkSender(sender)){
		return true;
		}
		Player p = (Player) sender;
		if(hasNoPerm(p, "str.bedtp")) return true;

		if(args[0].equalsIgnoreCase("list")){
			int page = 1;
			if(args.length == 2){
				try {
					int newPage = Integer.parseInt(args[1]);
					page = newPage;
				}
				catch (IllegalArgumentException e){
					MessageManager.getManager().msg(p, MessageManager.MessageType.BAD, "Введите корректный номер страницы");
					return true;
				}
			}
			displayLocationNames(p, page);
			return true;
		}
		teleportPlayerToBedName(p, args[0]);
		return true;
	}

	private void teleportPlayerToBedName(Player p, String bedName){
		Location bedLocation;
		try {
			bedLocation = configToLoc(bedName);
		} catch (IllegalArgumentException e) {
			MessageManager.getManager().msg(p, MessageManager.MessageType.BAD, "Игрока не существует / не имеет кровати");
			return;
		}
		p.teleport(bedLocation);
	}

	private void displayLocationNames(Player p, int page){
		List<String> bedNames = new ArrayList<>(getAllLocationNames());
		int prevIterator = BEDS_PER_PAGE*(page-1);
		int nextIterator = bedNames.size() > BEDS_PER_PAGE * page ? BEDS_PER_PAGE * page : bedNames.size();
		for(int i = prevIterator; i<nextIterator; i++){
			String bed = bedNames.get(i);
			TextComponent text = new TextComponent();
			text.setText("§6["+bed+"]");
			text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bedtp "+bed));
			p.spigot().sendMessage(text);
		}

		if(bedNames.size()>BEDS_PER_PAGE*(page)){
			TextComponent next = new TextComponent();
			next.setText("§3[>>>>]");
			int nextPage = page+1;
			next.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bedtp list "+nextPage));
			p.spigot().sendMessage(next);
		}
		if(page>1){
			TextComponent prev = new TextComponent();
			prev.setText("§3[<<<<]");
			int prevPage = page-1;
			prev.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bedtp list "+prevPage));
			p.spigot().sendMessage(prev);
		}

	}

	private boolean checkSender(CommandSender sender){
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only for player usage!");
			return true;
		}
		return false;
	}

	private boolean hasNoPerm(Player p, String perm){
		if(!p.hasPermission(perm)){
			MessageManager.getManager().msg(p, MessageManager.MessageType.BAD, "У вас нет прав!");
			return true;
		}
		return false;
	}

	private Location configToLoc (String name) throws IllegalArgumentException {
		 File beds = new File(plugin.getDataFolder() + File.separator + "beds.yml");
		 FileConfiguration b = YamlConfiguration.loadConfiguration(beds);
		 Location loc = new Location(Bukkit.getServer().getWorld(b.getString("locations." + name + ".world")),
				b.getDouble("locations." + name + ".x"),
				b.getDouble("locations." + name + ".y"),
				b.getDouble("locations." + name + ".z"));
		return loc;
	}

	/*
	t.sendMessage("§aВы были приглашены в клан §6"+name+" §aигроком §6"+p.getName());
		p.sendMessage("§aВы приглаcили в клан §6"+name+" §aигрока §6"+t.getName());
		tc.setText("§2§l[ПРИНЯТЬ]");
		tc.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/clanaccept"));
		t.spigot().sendMessage(tc);
		TextComponent tc2 = new TextComponent();
		tc2.setText("§4§l[ОТКЛОНИТЬ]");
		tc2.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/clandecline"));
		t.spigot().sendMessage(tc2);
	 */




	private Set<String> getAllLocationNames() {
		File beds = new File(plugin.getDataFolder() + File.separator + "beds.yml");
		FileConfiguration b = YamlConfiguration.loadConfiguration(beds);
		Set<String> keys = b.getConfigurationSection("locations").getKeys(false);
		return keys;
	}
}
