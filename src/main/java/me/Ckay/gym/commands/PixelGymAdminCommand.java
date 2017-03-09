package me.Ckay.gym.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import me.Ckay.gym.PixelGym;

public class PixelGymAdminCommand implements CommandExecutor
{
	PixelGym plugin;

	public PixelGymAdminCommand(PixelGym pixelGym)
	{
		this.plugin = pixelGym;
	}

	@Override
	public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException
	{
		Player p = (Player) sender;
		String arg = ctx.<String> getOne("args").orElse("");
		String[] args = arg.isEmpty() ? new String[] {} : arg.split(" ");

		if (args.length == 0)
		{
			p.sendMessage(Text.of(TextColors.GREEN, "/pixelgym reload", TextColors.DARK_GREEN, " - Reloads the plugin config."));
			p.sendMessage(Text.of(TextColors.GREEN, "/pixelgym checkconfig", TextColors.DARK_GREEN, " - Checks the currently set config settings."));
			p.sendMessage(Text.of(TextColors.GREEN, "/pixelgym addleader <Gym#/e4#> (Username)", TextColors.DARK_GREEN, " - Add a gym leader to a specific gym or elite 4 level. E.G: /pixelgym addleader Gym1 ABkayCkay"));
			p.sendMessage(Text.of(TextColors.GREEN, "/pixelgym delleader <Gym#/e4#> (Username)", TextColors.DARK_GREEN, " - Remove a gym leader from a specific gym or elite 4 level. E.G: /pixelgym delleader Gym1 ABkayCkay"));
			p.sendMessage(Text.of(TextColors.GREEN, "/pixelgym setlevel <gym#> (level)", TextColors.DARK_GREEN, " - Sets the level of the specified gym. (Modify's the config)"));
			p.sendMessage(Text.of(TextColors.GREEN, "/pixelgym setrule <gym#> <rule#> (rule)", TextColors.DARK_GREEN, " - Sets the rules of the specified gym. (Modify's the config) E.G: /pixelgym setrule gym1 1 No_Potions"));
			p.sendMessage(Text.of(TextColors.GREEN, "/gym closeall", TextColors.DARK_GREEN, " - Closes all Gym's."));
			p.sendMessage(Text.of(TextColors.GREEN, "/e4 closeall", TextColors.DARK_GREEN, " - Closes all Elite 4 level's."));
		}
		else if (args.length == 1)
		{
			if ((args[0].equals("reload")) && (p.hasPermission("pixelgym.admin")))
			{
				this.plugin.getConfig().load();
				sender.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextColors.GREEN, "Config Reloaded!"));
			}
			else if ((args[0].equals("checkconfig")) && (p.hasPermission("pixelgym.admin")))
			{
				p.sendMessage(Text.of(TextColors.GOLD, "======= Current PixelGym Config settings ======="));
				p.sendMessage(Text.of(" "));
				p.sendMessage(Text.of(TextColors.GREEN, "Player Join Messages: ", TextColors.GOLD, this.plugin.getConfig().getString("config.joinmessage")));
				p.sendMessage(Text.of(TextColors.GREEN, "Scoreboard Active: ", TextColors.GOLD, this.plugin.getConfig().getString("config.scoreboard")));
				p.sendMessage(Text.of(TextColors.GREEN, "Gym/E4 Leader healing: ", TextColors.GOLD, this.plugin.getConfig().getString("config.gymhealing")));
				p.sendMessage(Text.of(TextColors.GREEN, "Elite 4 Enabled: ", TextColors.GOLD, this.plugin.getConfig().getString("config.e4enabled")));
				p.sendMessage(Text.of(TextColors.GREEN, "Give Leaders Pokemon: ", TextColors.GOLD, this.plugin.getConfig().getString("config.giveleaderpokemon")));
			}
		}
		else if (args.length == 3)
		{
			if (args[0].equalsIgnoreCase("addleader"))
			{
				if (p.hasPermission("pixelgym.admin"))
				{
					if (Sponge.getServer().getPlayer(args[2]).orElse(null) != null)
					{
						for (int i = 1; i <= 32; i++)
						{
							if (args[1].equalsIgnoreCase("gym" + i))
							{
								Player playerTarget = Sponge.getServer().getPlayer(args[2]).orElse(null);
								String gym = args[1].replace("gym", "");

								if (this.plugin.getConfig().getString("config.enablegroup").equals("True"))
								{
									// TODO
								}
								else
								{
									// TODO
								}

								if (this.plugin.getConfig().getString("config.giveleaderpokemon").equals("True"))
								{
									Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("poke1").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("poke1lvl").toString()));
									Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("poke2").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("poke2lvl").toString()));
									Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("poke3").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("poke3lvl").toString()));
									Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("poke4").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("poke4lvl").toString()));
									Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("poke5").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("poke5lvl").toString()));
									Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("poke6").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("poke6lvl").toString()));
								}

								playerTarget.sendMessage(Text.of(TextColors.RED, "This feature does not currently add permissions, it just gives pixelmon from the config!"));
								p.sendMessage(Text.of(TextColors.RED, "This feature does not currently add permissions, it just gives pixelmon from the config! Permissions need to be added manually."));
								playerTarget.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ",TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("colour").toString())), "You have been set as a " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Leader!"));
								playerTarget.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("colour").toString())), "Try /gym or /e4 to see your new commands!"));
								p.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.")
									.append(args[1]).append("colour").toString())), playerTarget.getName().toString() + " has successfully been added as a " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " leader!"));
							}
						}
					}

					if ((args[1].equalsIgnoreCase("e41")) || (args[1].equalsIgnoreCase("e42")) || (args[1].equalsIgnoreCase("e43")) || (args[1].equalsIgnoreCase("e44")))
					{
						Player playerTarget = Sponge.getServer().getPlayer(args[2]).orElse(null);

						if (args[2].equals(playerTarget.getName()))
						{
							// TODO

							if (this.plugin.getConfig().getString("config.giveleaderpokemon").equals("True"))
							{
								Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke1").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke1lvl").toString()));
								Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke2").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke2lvl").toString()));
								Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke3").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke3lvl").toString()));
								Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke4").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke4lvl").toString()));
								Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke5").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke5lvl").toString()));
								Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke6").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke6lvl").toString()));
							}
							playerTarget.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title") + TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("colour").toString())) + "You have been set as a " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Leader!"));
							playerTarget.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title") + TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("colour").toString())) + "Try /gym or /e4 to see your new commands!"));
							p.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title") + TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("colour").toString())) + playerTarget.getName().toString() + " has successfully been added as a " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " leader!"));
						}
					}
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You do not have permission to use this command"));
				}
			}
			if (args[0].equalsIgnoreCase("delleader"))
			{
				if (p.hasPermission("pixelgym.admin"))
				{
					Player playerTarget = Sponge.getServer().getPlayer(args[2]).orElse(null);
					if (args[2].equals(playerTarget.getName()))
					{
						String gym = args[1].replace("gym", "");
						// TODO
						playerTarget.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig()
							.getString(new StringBuilder("config.").append(args[1])
								.append("colour").toString())), "You have been removed from being a " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Leader!"));
						p.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("colour").toString())), playerTarget.getName().toString() + " has successfully been removed as a " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " leader!"));
					}
					else if (((args[1].equalsIgnoreCase("e41")) || (args[1].equalsIgnoreCase("e42")) || (args[1].equalsIgnoreCase("e43")) || (args[1].equalsIgnoreCase("e44"))) && (args[2].equals(playerTarget.getName())))
					{
						// TODO
						playerTarget.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1])
							.append("colour").toString())), "You have been removed from being a " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Leader!"));
						p.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1])
							.append("colour").toString())), playerTarget.getName().toString() + " has successfully been removed as a " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " leader!"));
					}
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You do not have permission to use this command"));
				}
			}
			if (args[0].equalsIgnoreCase("setlevel"))
			{
				String gymArg = args[1].replace("gym", "");
				int gym = 0;

				try
				{
					gym = Integer.parseInt(gymArg);
				}
				catch (NumberFormatException nfe)
				{
					p.sendMessage(Text.of(TextColors.RED, args[1] + " is not a gym!"));
					return CommandResult.empty();
				}

				if ((gym < 1) || (gym > 32))
				{
					return CommandResult.empty();
				}
				if (args[2] != null)
				{
					this.plugin.getConfig().set("config.gym" + gym + "lvlcap", args[2]);
					this.plugin.getConfig().save();
				}
			}
		}
		else if ((args.length == 4) && (args[0].equalsIgnoreCase("setrule")))
		{
			String gymArg = args[1].replace("gym", "");
			int gym = 0;

			try
			{
				gym = Integer.parseInt(gymArg);
			}
			catch (NumberFormatException nfe)
			{
				p.sendMessage(Text.of(TextColors.RED, args[1] + " is not a gym!"));
				return CommandResult.empty();
			}

			if ((gym < 1) || (gym > 32))
			{
				return CommandResult.empty();
			}
			String ruleArg = args[2];
			int rule = 0;

			try
			{
				rule = Integer.parseInt(ruleArg);
			}
			catch (NumberFormatException nfe)
			{
				p.sendMessage(Text.of(TextColors.RED, args[2] + " is not an integer!"));
				return CommandResult.empty();
			}

			if ((rule <= 5) && (rule >= 1))
			{
				String message = args[3].replace("_", " ");

				this.plugin.getConfig().set("config.gym" + gym + "rule" + rule, message);
				this.plugin.getConfig().save();
				;
			}
		}

		return CommandResult.empty();
	}
}
