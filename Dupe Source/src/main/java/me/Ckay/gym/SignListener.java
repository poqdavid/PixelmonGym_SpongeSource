package me.Ckay.gym;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.Ckay.gym.config.PluginConfiguration;

public class SignListener
{
	private PixelGym plugin;

	public SignListener(PixelGym plugin)
	{
		this.plugin = plugin;
	}

	public boolean setStatusSign(Location<World> l, String gym)
	{
		PluginConfiguration config = this.plugin.settings.getSigns();
		List<String> list = config.getStringList("status");

		if (list == null)
		{
			list = new ArrayList<>();
		}

		list.add(l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ() + ", " + l.getExtent().getName() + ", " + gym);
		config.set("status", list);
		config.save();

		PixelGym.getInstance().updateSigns();
		return true;
	}

	public boolean setLeaderSign(Location<World> l, String gym, int number)
	{
		PluginConfiguration config = this.plugin.settings.getSigns();
		List<String> list = config.getStringList("leader");

		if (list == null)
		{
			list = new ArrayList<>();
		}

		list.add(l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ() + ", " + l.getExtent().getName() + ", " + gym + ", " + number);

		config.set("leader", list);
		config.save();

		PixelGym.getInstance().updateSigns();
		return true;
	}

	@Listener
	public void onSignChange(ChangeSignEvent e, @Root Player p)
	{
		List<String> line = e.getText().lines().get()
			.stream()
			.map(t -> t.toPlain())
			.collect(Collectors.toList());

		if ((line.get(0).equalsIgnoreCase("[GymStatus]")) && (p.hasPermission("pixelgym.admin")))
		{
			if (line.get(1).startsWith("gym"))
			{
				String gym = line.get(1).replace("gym", "");
				int gymNumber = 0;
				try
				{
					gymNumber = Integer.parseInt(gym);
				}
				catch (NumberFormatException ex)
				{
					p.sendMessage(Text.of("Use \"gym + number\" on line 2!"));
				}

				setStatusSign(e.getTargetTile().getLocation(), Integer.toString(gymNumber));
			}
		}
		else if ((line.get(0).equalsIgnoreCase("[GymLeaders]")) && (p.hasPermission("pixelgym.admin")))
		{
			setLeaderSign(e.getTargetTile().getLocation(), line.get(1), 1);
		}
		else if ((line.get(0).startsWith("[GymLeaders")) && (line.get(0).endsWith("]")) && (p.hasPermission("pixelgym.admin")))
		{
			try
			{
				setLeaderSign(e.getTargetTile().getLocation(), line.get(1), Integer.parseInt(line.get(0).replace("[GymLeaders", "").replace("]", "")));
			}
			catch (NumberFormatException ex)
			{
				p.sendMessage(Text.of(TextColors.RED, "Use [GymLeaders + number] on line 1!")); 
			}
		}
	}
}
