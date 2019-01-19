package Color_yr.OnlinePlayerCount;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class command extends Command 
{

	public command() 
	{
		super("opc");
	}

	public void execute(CommandSender sender, String[] args) 
	{
		if(args[0].equalsIgnoreCase("reload"))
		{
			if(sender.hasPermission("opc.admin"))
			{
				OnlinePlayerCount.reloadConfig();
				sender.sendMessage(new TextComponent("[OnlinePlayerCount]日志内容： " + OnlinePlayerCount.SaveMessage));
				sender.sendMessage(new TextComponent("[OnlinePlayerCount]日志内容： " + OnlinePlayerCount.SaveServer));
				sender.sendMessage(new TextComponent("[OnlinePlayerCount]记录间隔： " + OnlinePlayerCount.SaveTime));
				return;
			} 
			else 
			{
				sender.sendMessage(new TextComponent("[OnlinePlayerCount]你没有权限！"));
				return;
			}
		}
		if(args[0].equalsIgnoreCase("help"))
		{
			sender.sendMessage(new TextComponent("[OnlinePlayerCount]使用/opc reload重读配置文件"));
			sender.sendMessage(new TextComponent("[OnlinePlayerCount]使用/opc save保存一次在线玩家人数"));
			return;
		}
		if(args[0].equalsIgnoreCase("save"))
		{
			if(sender.hasPermission("opc.admin"))
			{
				message message = new message();
				message.save_once();
				sender.sendMessage(new TextComponent("[OnlinePlayerCount]已保存在线人数"));
			} 
			else 
				sender.sendMessage(new TextComponent("[OnlinePlayerCount]你没有权限！"));
			return;
		}
		else
		{
			sender.sendMessage(new TextComponent("[OnlinePlayerCount]请输入/opc help获取帮助"));
			return;
		}
	}
}
