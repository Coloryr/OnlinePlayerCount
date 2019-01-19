package Color_yr.OnlinePlayerCount;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public class message extends Thread
{
	public void save_once()
	{
		ProxyServer proxyserver = (ProxyServer) ProxyServer.getInstance();
		Map<String, ServerInfo> Server = proxyserver.getServers();
		Collection<ServerInfo> values = Server.values();
		Iterator<ServerInfo> iterator2 = values.iterator();
		String One_Server_message = "";
		String All_Server_message = "";
		String Send_message = OnlinePlayerCount.SaveMessage;
		int one_player_number = 0;
		int all_player_number = proxyserver.getOnlineCount();
		while (iterator2.hasNext())
		{
			ServerInfo serverinfo = iterator2.next();
			String player_onserver = serverinfo.getPlayers().toString();
			if(player_onserver == "[]")
			{
				String Server_name = OnlinePlayerCount.config.getString("Servers." + serverinfo.getName());
				if(Server_name == "" || Server_name == null)
				{
					Server_name = serverinfo.getName();
					Server_name = Server_name.replace("null", "");
				}		
				One_Server_message = OnlinePlayerCount.SaveServer
						.replaceAll("%ServerName%", Server_name)
						.replaceAll("%OnlinePlayer%", "0")
						.replaceAll("%PlayerList%", "无");
				All_Server_message = All_Server_message + One_Server_message;
			}
			else
			{
				one_player_number = 1;
				for(int i = 0; i < player_onserver.length(); i++)
				{
					if(player_onserver.charAt(i) == ',')
						one_player_number ++;
				}
				String Server_name = OnlinePlayerCount.config.getString("Servers." + serverinfo.getName());
				if(Server_name == "" || Server_name == null)
				{
					Server_name = serverinfo.getName();
					Server_name = Server_name.replace("null", "");
				}
				One_Server_message = OnlinePlayerCount.SaveServer
						.replaceAll("%ServerName%", Server_name)
						.replaceAll("%OnlinePlayer%", "" + one_player_number)
						.replaceAll("%PlayerList%", player_onserver.replace("[", "").replace("]", ""));
				All_Server_message = All_Server_message + One_Server_message;
			}
			Send_message = Send_message.replaceAll("%Player%", "" + all_player_number)
					.replaceAll("%Server%", All_Server_message);
			logs logs = new logs();
			logs.log_write(Send_message);
		}
	}
	@Override
	public void run()
	{
		while(true)
		{
			try 
			{
				save_once();
				OnlinePlayerCount.log.info("[OnlinePlayerCount]已保存在线人数");
				Thread.sleep(OnlinePlayerCount.SaveTime*1000*60);
			}
			catch(InterruptedException e)
			{
				OnlinePlayerCount.log.warning("[OnlinePlayerCount]保存人数线程发生错误");
			}
		}
	}
}
