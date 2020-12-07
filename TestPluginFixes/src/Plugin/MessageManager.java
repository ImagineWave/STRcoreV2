package Plugin;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class MessageManager {
	
	private MessageManager() { }
		
	private static MessageManager manager = new MessageManager();
	
	public static MessageManager getManager() {
		return manager;
	}
	
	public enum MessageType {
		INFO(ChatColor.AQUA),
		GOOD(ChatColor.GREEN),
		BAD(ChatColor.RED);
		
		private ChatColor color;
		
		MessageType (ChatColor color) {
			this.color = color;
		}
		
		public ChatColor getColor() {
			return color;
		}
	}
	
	private String prefix = ChatColor.GRAY + "[" + ChatColor.AQUA + "STR" + ChatColor.GREEN + "mine" + ChatColor.GRAY + "]: " + ChatColor.RESET;
	//MessageManager.getManager().msg(sender, MessageType.BAD, "TEXT");
	public void msg (CommandSender sender, MessageType type, String... msgs) {
		for (String msg : msgs) {
			sender.sendMessage(prefix + type.getColor() + msg);
		}
	}
}
