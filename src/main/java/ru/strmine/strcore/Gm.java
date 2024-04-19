package ru.strmine.strcore;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Gm implements CommandExecutor {

	public Gm(Main plugin) {}
	private Main plugin;
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only for player usage!");
            return true;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("str.gamemode")) {
        	MessageManager.getManager().msg(p, MessageManager.MessageType.BAD, "У вас нет прав");
            return true;
        }
                if (args.length == 0) {
                	MessageManager.getManager().msg(p, MessageManager.MessageType.INFO, "Использование /gm <режим> <игрок>");
                    return false;
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s")
                            || args[0].equalsIgnoreCase("0")) {
                        p.setGameMode(org.bukkit.GameMode.SURVIVAL);
                        MessageManager.getManager().msg(p, MessageManager.MessageType.GOOD, "Ваш игровой режим: §bsurvival");
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" сменил режим игры на survival");
            				}
            			}
                        return true;
                    } else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c")
                            || args[0].equalsIgnoreCase("1")) {
                    	 if (!sender.hasPermission("str.gamemode.creative")) {
                         	MessageManager.getManager().msg(p, MessageManager.MessageType.BAD, "Хитрец)");
                             return true;
                        }
                    	
                        p.setGameMode(org.bukkit.GameMode.CREATIVE);
                        MessageManager.getManager().msg(p, MessageManager.MessageType.GOOD, "Ваш игровой режим: §bcreative");
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" сменил режим игры на creative");
            				}
            			}
                        return true;
                    } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a")
                            || args[0].equalsIgnoreCase("2")) {
                        p.setGameMode(org.bukkit.GameMode.ADVENTURE);
                        MessageManager.getManager().msg(p, MessageManager.MessageType.BAD, "Ваш игровой режим: §4adventure");
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" сменил режим игры на adventure");
            				}
            			}
                        return true;
                    } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
                        p.setGameMode(GameMode.SPECTATOR);
                        MessageManager.getManager().msg(p, MessageManager.MessageType.GOOD, "Ваш игровой режим: §bspectator");
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" сменил режим игры на spectator");
            				}
            			}
                        return true;
                    } else {
                        p.sendMessage(
                                "§cYou must enter either /gamemode <survival | creative | adventure | spectator>");
                    }
                }
                if (args.length == 2) {
                	 if (!sender.hasPermission("str.gamemode.others")) {
                     	MessageManager.getManager().msg(p, MessageManager.MessageType.BAD, "У вас нет прав");
                         return true;
                	 }
                	Player t = (Bukkit.getPlayerExact(args[1]));
                	if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s")
                            || args[0].equalsIgnoreCase("0")) {
                        t.setGameMode(org.bukkit.GameMode.SURVIVAL);
                        MessageManager.getManager().msg(t, MessageManager.MessageType.GOOD, "Ваш игровой режим: §bsurvival");
                        MessageManager.getManager().msg(p, MessageManager.MessageType.GOOD, "Вы установили режим §bsurvival§a игроку §6" + t.getName());
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" установил режим игры survival игроку "+t.getName());
            				}
            			}
                        return true;
                    } else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c")
                            || args[0].equalsIgnoreCase("1")) {
                        t.setGameMode(org.bukkit.GameMode.CREATIVE);
                        MessageManager.getManager().msg(t, MessageManager.MessageType.GOOD, "Ваш игровой режим: §bcreative");
                        MessageManager.getManager().msg(p, MessageManager.MessageType.GOOD, "Вы установили режим §bcreative§a игроку §6" + t.getName());
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" установил режим игры creative игроку "+t.getName());
            				}
            			}
                        return true;
                    } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a")
                            || args[0].equalsIgnoreCase("2")) {
                        t.setGameMode(org.bukkit.GameMode.ADVENTURE);
                        MessageManager.getManager().msg(t, MessageManager.MessageType.BAD, "Ваш игровой режим: §4adventure");
                        MessageManager.getManager().msg(p, MessageManager.MessageType.GOOD, "Вы установили режим §4adventure§a игроку §6" + t.getName());
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" установил режим игры adventure игроку "+t.getName());
            				}
            			}
                        return true;
                    } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
                        t.setGameMode(GameMode.SPECTATOR);
                        MessageManager.getManager().msg(t, MessageManager.MessageType.GOOD, "Ваш игровой режим: §bspectator");
                        MessageManager.getManager().msg(p, MessageManager.MessageType.GOOD, "Вы установили режим §bspectator§a игроку §6" + t.getName());
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" установил режим игры spectator игроку "+t.getName());
            				}
            			}
                        return true;
                }
                
                }
        
        return true;

    }
    private void addInvToFile(Player p, ItemStack[] items) {
    	File inventory = new File(plugin.getDataFolder() + File.separator + "Inventory.yml");
    	FileConfiguration h = YamlConfiguration.loadConfiguration(inventory);
    	h.set("inventory."+p.getName()+".creative", items);
    	try {
			h.save(inventory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//h.getItemStack("inventory."+p.getName()+".creative");
    	Long lastUsage = h.getLong("inventory."+p.getName()+".creative");
    }

}