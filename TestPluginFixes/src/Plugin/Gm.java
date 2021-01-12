package Plugin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Plugin.MessageManager.MessageType;

public class Gm implements CommandExecutor {

	public Gm(Main plugin) {}
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only for player usage!");
            return true;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("str.gamemode")) {
        	MessageManager.getManager().msg(p, MessageType.BAD, "У вас нет прав для изменения режима игры");
            return true;
        }
                if (args.length == 0) {
                	MessageManager.getManager().msg(p, MessageType.INFO, "Использование /gm <режим> <игрок>");
                    return false;
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s")
                            || args[0].equalsIgnoreCase("0")) {
                        p.setGameMode(org.bukkit.GameMode.SURVIVAL);
                        MessageManager.getManager().msg(p, MessageType.GOOD, "Ваш игровой режим: §bsurvival");
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" сменил режим игры на survival");
            				}
            			}
                        return true;
                    } else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c")
                            || args[0].equalsIgnoreCase("1")) {
                    	 if (!sender.hasPermission("str.gamemode.creative")) {
                         	MessageManager.getManager().msg(p, MessageType.BAD, "У вас нет прав для изменения режима игры");
                             return true;
                         }
                        p.setGameMode(org.bukkit.GameMode.CREATIVE);
                        MessageManager.getManager().msg(p, MessageType.GOOD, "Ваш игровой режим: §bcreative");
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" сменил режим игры на creative");
            				}
            			}
                        return true;
                    } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a")
                            || args[0].equalsIgnoreCase("2")) {
                        p.setGameMode(org.bukkit.GameMode.ADVENTURE);
                        MessageManager.getManager().msg(p, MessageType.BAD, "Ваш игровой режим: §4adventure");
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" сменил режим игры на adventure");
            				}
            			}
                        return true;
                    } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
                        p.setGameMode(GameMode.SPECTATOR);
                        MessageManager.getManager().msg(p, MessageType.GOOD, "Ваш игровой режим: §bspectator");
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
                     	MessageManager.getManager().msg(p, MessageType.BAD, "У вас нет прав для изменения режима игры другим игрокам");
                         return true;
                	 }
                	Player t = (Bukkit.getPlayerExact(args[1]));
                	if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s")
                            || args[0].equalsIgnoreCase("0")) {
                        t.setGameMode(org.bukkit.GameMode.SURVIVAL);
                        MessageManager.getManager().msg(t, MessageType.GOOD, "Ваш игровой режим: §bsurvival");
                        MessageManager.getManager().msg(p, MessageType.GOOD, "Вы установили режим игры §bsurvival§2 игроку §6" + t.getName());
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" сменил режим игры на survival игроку "+t.getName());
            				}
            			}
                        return true;
                    } else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c")
                            || args[0].equalsIgnoreCase("1")) {
                        t.setGameMode(org.bukkit.GameMode.CREATIVE);
                        MessageManager.getManager().msg(t, MessageType.GOOD, "Ваш игровой режим: §bcreative");
                        MessageManager.getManager().msg(p, MessageType.GOOD, "Вы установили режим игры §bcreative§2 игроку §6" + t.getName());
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" сменил режим игры на creative игроку "+t.getName());
            				}
            			}
                        return true;
                    } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a")
                            || args[0].equalsIgnoreCase("2")) {
                        t.setGameMode(org.bukkit.GameMode.ADVENTURE);
                        MessageManager.getManager().msg(t, MessageType.BAD, "Ваш игровой режим: §4adventure");
                        MessageManager.getManager().msg(p, MessageType.GOOD, "Вы установили режим игры §4adventure§2 игроку §6" + t.getName());
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" сменил режим игры на adventure игроку "+t.getName());
            				}
            			}
                        return true;
                    } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
                        t.setGameMode(GameMode.SPECTATOR);
                        MessageManager.getManager().msg(t, MessageType.GOOD, "Ваш игровой режим: §bspectator");
                        MessageManager.getManager().msg(p, MessageType.GOOD, "Вы установили режим игры §bspectator§2 игроку §6" + t.getName());
                        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            				if (pls.hasPermission("str.spy.admin")) {
            					pls.sendMessage("§7[SPY]: "+p.getName()+" сменил режим игры на spectator игроку "+t.getName());
            				}
            			}
                        return true;
                }
                
                }
        
        return true;

    }

}