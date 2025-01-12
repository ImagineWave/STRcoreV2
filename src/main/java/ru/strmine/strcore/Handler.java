package ru.strmine.strcore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.strmine.strcore.book.Book;

public class Handler implements Listener {
	
	private Main plugin;

	public Handler(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void spawnPlayer(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String name = p.getName();
		MessageManager.getManager().msg(p, MessageManager.MessageType.INFO, "Добро пожаловать на сервер, "+ name);
		newPlayerSequence(p);
	}

	@EventHandler
	public void mobProtect(CreatureSpawnEvent e) {
		Location loc = e.getEntity().getLocation();
		if(!loc.getWorld().getName().equalsIgnoreCase("world")) return;
		if(e.getEntityType() == EntityType.PLAYER) return;
		Location spawn = SpawnToLoc();
		int blockX = loc.getBlockX();
		int blockY = loc.getBlockY();
		int blockZ = loc.getBlockZ();
		int modX = Math.abs(blockX);
		int modY = Math.abs(blockY);
		int modZ = Math.abs(blockZ);
		int spawnModX =  Math.abs(spawn.getBlockX());
		int spawnModY =  Math.abs(spawn.getBlockY());
		int spawnModZ =  Math.abs(spawn.getBlockZ());
		if((modX-spawnModX<200)&&(modY-spawnModY<200)&&(modZ-spawnModZ<200)) {
			if(e.getEntityType() != EntityType.PLAYER)
			{
				e.setCancelled(true);
				return;
			}
		}
		return;
	}

	@EventHandler
		public void killCat(EntityDeathEvent e) {
			ArrayList<EntityType> animal = new ArrayList<EntityType>();
			animal.add(EntityType.CAT);
			animal.add(EntityType.OCELOT);
			if(!animal.contains(e.getEntity().getType())) return;
			if (!(e.getEntity() instanceof Damageable)) return;
			if (e.getEntity().getKiller()== null) return;
			Player p = e.getEntity().getKiller();
			Bukkit.broadcastMessage("§6"+p.getName()+" §4Убил кошку");
			p.setHealth(0);
	}
	@EventHandler
	public void playerSpawnsWitherInNether(PlayerInteractEvent e) {
			Player p = e.getPlayer();
			if(p.isOp()) return;
		if(p.getLocation().getWorld().getName().equalsIgnoreCase("world_nether"))return;
		if((p.getInventory().getItemInMainHand().getType().equals(Material.WITHER_SKELETON_SKULL)) || (p.getInventory().getItemInOffHand().getType().equals(Material.WITHER_SKELETON_SKULL))) {
			e.setCancelled(true);
			p.sendMessage("§4призывать визера разрешено только мире world_nether");
			return;
		}
		return;
	}
	@EventHandler
	public void playerSpawnsCrystals(PlayerInteractEvent e) {
			Player p = e.getPlayer();
			if(p.isOp()) return;
		if(!p.getLocation().getWorld().getName().equalsIgnoreCase("world_the_end")) {
			if((p.getInventory().getItemInMainHand().getType().equals(Material.END_CRYSTAL)) || (p.getInventory().getItemInOffHand().getType().equals(Material.END_CRYSTAL))) {
				e.setCancelled(true);
				p.sendMessage("§4Использовать эту вещь можно только в мире world_the_end");
				return;
			}
		}

		return;
	}

	public Location configToLoc (String name) {
		 File homes = new File(plugin.getDataFolder() + File.separator + "homes.yml");
		 FileConfiguration h = YamlConfiguration.loadConfiguration(homes);
		Location loc = new Location(Bukkit.getServer().getWorld(h.getString("locations." + name + ".world")),
				h.getDouble("locations." + name + ".x"),
				h.getDouble("locations." + name + ".y"),
				h.getDouble("locations." + name + ".z"));
		return loc;
	}
	public Location SpawnToLoc () {
		 File homes = new File(plugin.getDataFolder() + File.separator + "spawn.yml");
		 FileConfiguration h = YamlConfiguration.loadConfiguration(homes);
		Location spawn = new Location(Bukkit.getServer().getWorld(h.getString("locations.world")),
				h.getDouble("locations.x"),
				h.getDouble("locations.y"),
				h.getDouble("locations.z"));
		return spawn;
	}


	private void newPlayerSequence(Player p){
		File players = new File(plugin.getDataFolder() + File.separator + "players.yml");
		FileConfiguration users = YamlConfiguration.loadConfiguration(players);
		List<String> list = users.getStringList("users");//govno ebanoe
		if(list.contains(p.getName())){
			return;
		}
		setHomeNewPlayer(p);
		addEffects(p);
		createNewStrPlayer(p);
		giveRuleBook(p);
		addPlayerToList(p);
	}

	private void setHomeNewPlayer(Player p){
		Home home = new Home(plugin);
		Location spawn = SpawnToLoc();
		p.teleport(spawn);
		p.setBedSpawnLocation(spawn, true);
		home.locToConfig(p.getName(),spawn);
	}

	private void addEffects(Player p){
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 600, 4), true);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 20 * 600, 4), true);
	}

	private void createNewStrPlayer(Player p){
		try {
			StrPlayer spl = new StrPlayer(plugin);
			spl.setNickname(p.getName());
			spl.setPlayTime(System.currentTimeMillis());
			spl.setPlayerCfg(spl);
		} catch(NullPointerException e1) {
			e1.printStackTrace();
		}
	}

	private void addPlayerToList(Player p){
		File players = new File(plugin.getDataFolder() + File.separator + "players.yml");
		FileConfiguration users = YamlConfiguration.loadConfiguration(players);
		List<String> list = users.getStringList("users");//govno ebanoe
		list.add(p.getName());
		users.set("users", list);
		try {
			users.save(players);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Bukkit.broadcastMessage("Поприветствуем игрока " + p.getName()+"§a на §b§lSTR§amine!");
	}

	private void giveRuleBook(Player p){
		Book book = new Book();
		book.setTitle("§6Книга правил");
		book.setAuthor("§3BoB4uk76");
		book.bookMeta.addPage("Это §4§nНЕ гриферский§r сервер и §4§nНЕ анархия§r\n" +
				"\n"+
				"1. Не гриферить\n" +
				"2. Не читерить\n" +
				"3. Не убивать игроков\n" +
				"\n"+
				"Если вы §nзаранее§r договорились о PvP - §2можно§r\n");

		book.bookMeta.addPage("Если игрок §nОТКАЗЫВАЕТСЯ§r покинуть территорию вашего дома по вашему требованию, то вы §2можете§r его убить\n" +
				"(бить вас в ответ ему §4нельзя§r, это будет считаться §4грифом§r)");

		book.bookMeta.addPage("На этом в принципе все, остальные правила вы можете посмотреть на нашем сайте.\n"+
				"§nstrmine.ru§r \n" +
				"§2Удачной игры!§r\n" +
				"Ах да, эту книжку можно использовать в качестве начального оружия");




		book.addDefaultInfo();//Конечная
		if(p.isOp()){
			book.addSuperDamage();
		}
		book.giveBook(p);
	}
}
	