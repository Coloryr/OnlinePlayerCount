package Color_yr.OnlinePlayerCount;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class OnlinePlayerCount extends Plugin 
{
	public static String Version = "1.0.0";
	public static String SaveMessage;
	public static String SaveServer;
	public static int SaveTime;
	
	public static Thread save;
	
	public static Configuration config;
	private static File FileName;
	
	public static Logger log = ProxyServer.getInstance().getLogger();

	public static void loadconfig() 
	{
		log.info("[OnlinePlayerCount]你的配置文件版本是：" + config.getString("Version"));
			
		SaveMessage = config.getString("Save.Message", "总数：%Player%，%Server%");
		SaveServer = config.getString("Save.Server", "子服：[(%ServerName%)|%OnlinePlayer%|%PlayerList%]");
		SaveTime = config.getInt("Save.Time", 30);
	}

	public static void reloadConfig() 
	{
		try 
		{
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(FileName);
			loadconfig();
		}
		catch (Exception arg0) 
		{
			log.warning("[OnlinePlayerCount]配置文件读取错误：" + arg0.getMessage());
		}
	}

	public static Configuration getConfig() 
	{
		return config;
	}

	public void setConfig() 
	{
		FileName = new File(getDataFolder(), "config.yml");
		logs.file = new File(getDataFolder(), "logs.log");
		if (!getDataFolder().exists())
			getDataFolder().mkdir();
		if (!FileName.exists()) 
		{
			try (InputStream in = getResourceAsStream("config.yml"))
			{
				Files.copy(in, FileName.toPath());
			} 
			catch (IOException e) 
			{
				log.warning("[OnlinePlayerCount]配置文件创建错误：" + e.getMessage());
			}
		}
		try 
		{
			if (!logs.file.exists()) 
			{
				logs.file.createNewFile();
			}
		} 
		catch (IOException e) 
		{
			log.warning("[OnlinePlayerCount]配置文件读取错误：" + e.getMessage());
		}
	}
	
	@Override
	public void onEnable() 
	{		
		log.info("[OnlinePlayerCount]正在启动，插件交流群：571239090欢迎加入");	
		setConfig();
		reloadConfig();
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new command());
		save = new message();
		save.start();
		log.info("[OnlinePlayerCount]已启动，你运行的版本是：" + Version);	
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() 
	{
		save.stop();
		log.info("[OnlinePlayerCount]已停止运行，欢迎再次使用");
	}
}
