package me.Ckay.gym.config;

import me.Ckay.gym.PixelGym;
import me.Ckay.gym.utils.Utils;

public class SettingsManager
{
	static SettingsManager instance = new SettingsManager();
	private PluginConfiguration data;
	private PluginConfiguration badges;
	private PluginConfiguration logs;
	private PluginConfiguration extras;
	private PluginConfiguration signs;

	public static SettingsManager getInstance()
	{
		return instance;
	}

	// Badges
	public void setupBadges(PixelGym p)
	{
		Utils.saveResource(p, "badges.yml", false);
		this.badges = new PluginConfiguration(p, "badges.yml");
	}

	public PluginConfiguration getBadge()
	{
		return badges;
	}

	public void saveBadges()
	{
		this.badges.save();
	}

	public void reloadBadges()
	{
		this.badges.load();
	}

	// Data
	public void setup(PixelGym p)
	{
		Utils.saveResource(p, "data.yml", false);
		this.data = new PluginConfiguration(p, "data.yml");
	}

	public PluginConfiguration getData()
	{
		return this.data;
	}

	public void saveData()
	{
		this.data.save();
	}

	public void reloadData()
	{
		this.data.load();
	}

	// Log
	public void setupLog(PixelGym p)
	{
		Utils.saveResource(p, "logs.yml", false);
		this.logs = new PluginConfiguration(p, "logs.yml");
	}

	public PluginConfiguration getLogs()
	{
		return this.logs;
	}

	public void saveLogs()
	{
		this.logs.save();
	}

	public void reloadLogs()
	{
		this.logs.load();
	}

	// Extras
	public void setupExtra(PixelGym p)
	{
		Utils.saveResource(p, "extras.yml", false);
		this.extras = new PluginConfiguration(p, "extras.yml");
	}

	public PluginConfiguration getExtras()
	{
		return this.extras;
	}

	public void saveExtras()
	{
		this.extras.save();
	}

	public void reloadExtras()
	{
		this.extras.load();
	}

	// Signs
	public void setupSigns(PixelGym p)
	{
		this.signs = new PluginConfiguration(p, "signs.yml");
	}

	public PluginConfiguration getSigns()
	{
		return this.signs;
	}

	public void saveSigns()
	{
		this.signs.save();
	}

	public void reloadSigns()
	{
		this.signs.load();
	}
}
