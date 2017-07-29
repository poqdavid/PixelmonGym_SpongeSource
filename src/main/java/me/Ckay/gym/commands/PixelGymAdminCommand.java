package me.Ckay.gym.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.util.Tristate;

import com.google.common.collect.Sets;

import me.Ckay.gym.PixelGym;
import me.Ckay.gym.scoreboard.ScoreboardManager;

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
			p.sendMessage(Text.of(TextColors.GREEN, "/pixelgym addleader <GymName/e4#> (Username)", TextColors.DARK_GREEN, " - Add a gym leader to a specific gym or elite 4 level. E.G: /pixelgym addleader Gym1 ABkayCkay"));
			p.sendMessage(Text.of(TextColors.GREEN, "/pixelgym delleader <GymName/e4#> (Username)", TextColors.DARK_GREEN, " - Remove a gym leader from a specific gym or elite 4 level. E.G: /pixelgym delleader Gym1 ABkayCkay"));
			p.sendMessage(Text.of(TextColors.GREEN, "/pixelgym setlevel <GymName> (level)", TextColors.DARK_GREEN, " - Sets the level of the specified gym. (Modify's the config)"));
			p.sendMessage(Text.of(TextColors.GREEN, "/pixelgym setrule <GymName> <rule#> (rule)", TextColors.DARK_GREEN, " - Sets the rules of the specified gym. (Modify's the config) E.G: /pixelgym setrule gym1 1 No_Potions"));
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
				
				boolean gymName = false;
				int gym = 0;
					
					outerloop:
					
					for (int g = 1; g <= 32; g++)
					{
						if (this.plugin.getConfig().getString("config.gym"+g).equalsIgnoreCase(args[1]))
						{
							gymName = true;
							
							gym = g;
							
							break outerloop;
						}
						
					}
						
				if (args[1].matches("(gym|GYM)[1-9]?[0-9]") || gymName == true) {
      				
      				
      				//int i = gym - 1;
      				
      				if (gymName == true) {
      					//do nothing all good.
      				}
      				else {
      					String gymArg = args[1].replace("gym", "");
      					
      					try
      					{
      						gym = Integer.parseInt(gymArg);
      					}
      					catch (NumberFormatException nfe)
      					{
      						p.sendMessage(Text.of(TextColors.RED, args[1] + " is not a gym!"));
      						return CommandResult.empty();
      					}
      				}
				
				if (p.hasPermission("pixelgym.admin"))
				{
					if (Sponge.getServer().getPlayer(args[2]).orElse(null) != null)
					{
						
								Player playerTarget = Sponge.getServer().getPlayer(args[2]).orElse(null);
								Subject subject = playerTarget.getContainingCollection().get(playerTarget.getIdentifier());
								PermissionService permService = Sponge.getServiceManager().provide(PermissionService.class).get();

								//String gym = args[1].replace("gym", "");

								if (this.plugin.getConfig().getString("config.enablegroup").equals("True"))
								{
									if (subject.getParents().stream().filter(t -> t.getIdentifier().equals(this.plugin.getConfig().getString("config.globalgroupname")))
										.findAny().isPresent())
									{
										playerTarget.sendMessage(Text.of(TextColors.RED, "Player is already in this group, giving them the gym permission node. (pixelgym.gym" + gym + ")"));
										subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.gym" + gym, Tristate.TRUE);
									}
								else
								{
									String gymgroup = this.plugin.getConfig().getString("config.globalgroupname");
									subject.getSubjectData().addParent(Sets.newHashSet(), permService.getGroupSubjects().get(gymgroup));
									subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.gym" + gym, Tristate.TRUE);
								}
							}
								else {
									subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.gym" + gym, Tristate.TRUE);
									subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.leader", Tristate.TRUE);
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

								playerTarget.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("colour").toString())).getChildren().get(0).getColor(), "You have been set as a " + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).toString()) + " Leader!"));
								playerTarget.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("colour").toString())).getChildren().get(0).getColor(), "Try /gym or /e4 to see your new commands!"));
								p.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.gym")
									.append(gym).append("colour").toString())).getChildren().get(0).getColor(), playerTarget.getName().toString() + " has successfully been added as a " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " leader!"));
							
						
					}

					
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You do not have permission to use this command"));
				}
				}
				else if ((args[1].equalsIgnoreCase("e41")) || (args[1].equalsIgnoreCase("e42")) || (args[1].equalsIgnoreCase("e43")) || (args[1].equalsIgnoreCase("e44")))
				{
					Player playerTarget = Sponge.getServer().getPlayer(args[2]).orElse(null);
					Subject subject = playerTarget.getContainingCollection().get(playerTarget.getIdentifier());
					PermissionService permService = Sponge.getServiceManager().provide(PermissionService.class).get();

					if (args[2].equals(playerTarget.getName()))
					{
						if (this.plugin.getConfig().getString("config.enablegroup").equals("True"))
						{
							if (subject.getSubjectData().getParents(Sets.newHashSet()).stream().filter(t -> t.getIdentifier().equals(this.plugin.getConfig().getString("config.globale4groupname"))).findAny().isPresent())
							{
								p.sendMessage(Text.of(TextColors.RED, "Player is already in this group, giving them the gym permission node. (pixelgym." + args[1] + ")"));
								subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym." + args[1], Tristate.TRUE);
							}
							else
							{
								String e4group = this.plugin.getConfig().getString("config.globale4groupname");
								subject.getSubjectData().addParent(Sets.newHashSet(), permService.getGroupSubjects().get(e4group));
								subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym." + args[1], Tristate.TRUE);
							}
						}
						else
						{
							subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym." + args[1], Tristate.TRUE);
							subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.e4leader", Tristate.TRUE);
						}

						if (this.plugin.getConfig().getString("config.giveleaderpokemon").equals("True"))
						{
							Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke1").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke1lvl").toString()));
							Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke2").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke2lvl").toString()));
							Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke3").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke3lvl").toString()));
							Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke4").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke4lvl").toString()));
							Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke5").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke5lvl").toString()));
							Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + playerTarget.getName().toString() + " " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke6").toString()) + " lvl" + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("poke6lvl").toString()));
						}
						
					
						playerTarget.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("colour").toString())).getChildren().get(0).getColor() + "You have been set as a " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Leader!"));
						playerTarget.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ",TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("colour").toString())).getChildren().get(0).getColor() + "Try /gym or /e4 to see your new commands!"));
						p.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title") + TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("colour").toString())) + playerTarget.getName().toString() + " has successfully been added as a " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " leader!"));
					}
				}
				else {
					p.sendMessage(Text.of(TextColors.RED, "Gym " + args[1] + " is not a valid gym."));
				}
				gymName = false;
			}
			if (args[0].equalsIgnoreCase("delleader"))
			{
				
				Player playerTarget = Sponge.getServer().getPlayer(args[2]).orElse(null);
				Subject subject = playerTarget.getContainingCollection().get(playerTarget.getIdentifier());
				PermissionService permService = Sponge.getServiceManager().provide(PermissionService.class).get();
				
				boolean gymName = false;
				int gym = 0;
				boolean all = false;
					
					outerloop:
					
					for (int g = 1; g <= 32; g++)
					{
						if (this.plugin.getConfig().getString("config.gym"+g).equalsIgnoreCase(args[1]))
						{
							gymName = true;
							
							gym = g;
							
							break outerloop;
						}
						
					}
						
				if (args[1].matches("(gym|GYM)[1-9]?[0-9]") || args[1].equalsIgnoreCase("all") || gymName == true) {
      				
      				
      				//int i = gym - 1;
      				
      				if (gymName == true) {
      					//do nothing all good.
      				}
      				else if (args[1].equalsIgnoreCase("all")) {
      					//do nothing all good
      				}
      				else {
      					String gymArg = args[1].replace("gym", "");
      					
      					try
      					{
      						gym = Integer.parseInt(gymArg);
      					}
      					catch (NumberFormatException nfe)
      					{
      						p.sendMessage(Text.of(TextColors.RED, args[1] + " is not a gym!"));
      						return CommandResult.empty();
      					}
      				}
      				
				if (p.hasPermission("pixelgym.admin"))
				{
					if (args[2].equals(playerTarget.getName()))
					{
						
						if (this.plugin.getConfig().getString("config.enablegroup").equals("True"))
						{
							if (subject.getSubjectData().getParents(Sets.newHashSet()).stream().filter(t -> t.getIdentifier().equals(this.plugin.getConfig().getString("config.globalgroupname"))).findAny().isPresent())
							{
								if (!args[1].equalsIgnoreCase("all")) {
									subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.gym" + gym, Tristate.FALSE);
								}
								
								outerloop:
								
								for (int i = 1; i <= 32; i++)
								{
									if (playerTarget.hasPermission("pixelgym.gym" + i))
									{
										if (args[1].equalsIgnoreCase("all")) {
											subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.gym" + i, Tristate.FALSE);
										}
										else {
											break outerloop;
										}
									}
									else {
										subject.getSubjectData().removeParent(Sets.newHashSet(), permService.getGroupSubjects().get(this.plugin.getConfig().getString("config.globalgroupname")));
										
									}
								}
							}
							else
							{
								p.sendMessage(Text.of(TextColors.RED, playerTarget.getName() + " is not in the gym leader group! Removing there permission nodes."));
								subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.gym" + gym, Tristate.FALSE);

								outerloop:
								
								for (int i = 1; i <= 32; i++)
								{
									if (playerTarget.hasPermission("pixelgym.gym" + i))
									{
										if (args[1].equalsIgnoreCase("all")) {
											subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.gym" + i, Tristate.FALSE);
										}
										else {
											break outerloop;
										}
									}
									else {
										playerTarget.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.leader", Tristate.FALSE);
									}
								}
							}
						}
						else
						{
							subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.gym" + gym, Tristate.FALSE);

							outerloop:
							
							for (int i = 1; i <= 32; i++)
							{
								if (playerTarget.hasPermission("pixelgym.gym" + i))
								{
									if (args[1].equalsIgnoreCase("all")) {
										subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.gym" + i, Tristate.FALSE);
									}
									else {
										break outerloop;
									}
								}
								else {
									subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.leader", Tristate.FALSE);
								}
							}
						}
						
						if (args[1].equalsIgnoreCase("all")) {
							playerTarget.sendMessage(Text.of(TextColors.RED, "You have been removed from all gyms!"));
							p.sendMessage(Text.of(TextColors.GREEN, "Removed all of " +playerTarget.getName() + "'s gym permissions!"));
						}
						else {
						playerTarget.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ",  TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig()
							.getString(new StringBuilder("config.gym").append(gym)
								.append("colour").toString())).getChildren().get(0).getColor(), "You have been removed from being a " + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).toString()) + " Leader!"));
						p.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("colour").toString())).getChildren().get(0).getColor(), playerTarget.getName().toString() + " has successfully been removed as a " + this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).toString()) + " leader!"));
					
						}
					}
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You do not have permission to use this command"));
				}
				}
				else if (((args[1].equalsIgnoreCase("e41")) || (args[1].equalsIgnoreCase("all") || (args[1].equalsIgnoreCase("e42")) || (args[1].equalsIgnoreCase("e43")) || (args[1].equalsIgnoreCase("e44"))) && (args[2].equals(playerTarget.getName()))))
				{
					if (this.plugin.getConfig().getString("config.enablegroup").equals("True"))
					{
						if (subject.getSubjectData().getParents(Sets.newHashSet()).stream().filter(t -> t.getIdentifier().equals(this.plugin.getConfig().getString("config.globale4groupname"))).findAny().isPresent())
						{
							subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym." + args[1], Tristate.FALSE);
							//subject.getSubjectData().removeParent(Sets.newHashSet(), permService.getGroupSubjects().get(this.plugin.getConfig().getString("config.globale4groupname")));
						
							outerloop:
								
								for (int i = 1; i <= 4; i++)
								{
									if (playerTarget.hasPermission("pixelgym.e4" + i))
									{
										if (args[1].equalsIgnoreCase("all")) {
											subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.e4" + i, Tristate.FALSE);
										}
										else {
											break outerloop;
										}
									}
									else {
										subject.getSubjectData().removeParent(Sets.newHashSet(), permService.getGroupSubjects().get(this.plugin.getConfig().getString("config.globale4groupname")));
										
									}
								}
						}
						else
						{
							p.sendMessage(Text.of(TextColors.RED, playerTarget.getName() + " is not in the e4 leader group! Removing there permission nodes."));
							subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym." + args[1], Tristate.FALSE);

							outerloop:
							
							for (int i = 1; i <= 4; i++)
							{
								if (playerTarget.hasPermission("pixelgym.e4" + i))
								{
									if (args[1].equalsIgnoreCase("all")) {
										subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.gym" + i, Tristate.FALSE);
									}
									else {
										break outerloop;
									}
								}
								else {
									playerTarget.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.e4leader", Tristate.FALSE);
								}
							}
							
						}
					}
					else
					{
						subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym." + args[1], Tristate.FALSE);

						outerloop:
						
						for (int i = 1; i <= 4; i++)
						{
							if (playerTarget.hasPermission("pixelgym.e4" + i))
							{
								if (args[1].equalsIgnoreCase("all")) {
									subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.gym" + i, Tristate.FALSE);
								}
								else {
									break outerloop;
								}
							}
							else {
								subject.getSubjectData().setPermission(Sets.newHashSet(), "pixelgym.e4leader", Tristate.FALSE);
							}
						}
					}
					
					if (args[1].equalsIgnoreCase("all")) {
						playerTarget.sendMessage(Text.of(TextColors.RED, "You have been removed from all e4's!"));
						p.sendMessage(Text.of(TextColors.GREEN, "Removed all of " +playerTarget.getName() + "'s e4 permissions!"));
					}
					else {

					playerTarget.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1])
						.append("colour").toString())).getChildren().get(0).getColor(), "You have been removed from being a " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Leader!"));
					p.sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, this.plugin.getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1])
						.append("colour").toString())).getChildren().get(0).getColor(), playerTarget.getName().toString() + " has successfully been removed as a " + this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " leader!"));
					}
				}
				else {
					p.sendMessage(Text.of(TextColors.RED, "Gym " + args[1] + " is not a valid gym."));
				}
				gymName = false;
			}
			if (args[0].equalsIgnoreCase("setlevel"))
			{
				
				boolean gymName = false;
				int gym = 0;
					
					outerloop:
					
					for (int g = 1; g <= 32; g++)
					{
						if (this.plugin.getConfig().getString("config.gym"+g).equalsIgnoreCase(args[1]))
						{
							gymName = true;
							
							gym = g;
							
							break outerloop;
						}
						
					}
						
				if (args[1].matches("(gym|GYM)[1-9]?[0-9]") || gymName == true) {
      				
      				
      				//int i = gym - 1;
      				
      				if (gymName == true) {
      					//do nothing all good.
      				}
      				else {
      					String gymArg = args[1].replace("gym", "");
      					
      					try
      					{
      						gym = Integer.parseInt(gymArg);
      					}
      					catch (NumberFormatException nfe)
      					{
      						p.sendMessage(Text.of(TextColors.RED, args[1] + " is not a gym!"));
      						return CommandResult.empty();
      					}
      				}
				
				if (args[2] != null)
				{
					this.plugin.getConfig().set("config.gym" + gym + "lvlcap", args[2]);
					this.plugin.getConfig().save();
					p.sendMessage(Text.of(TextColors.GREEN + "Level cap of gym"+gym+ " set!"));
				}
				}
				else {
					p.sendMessage(Text.of(TextColors.RED, "Gym " + args[1] + " is not a valid gym."));
				}
				gymName = false;
			}
		
			
		}
		else if ((args.length == 4) && (args[0].equalsIgnoreCase("setrule")))
		{
			
			boolean gymName = false;
			int gym = 0;
				
				outerloop:
				
				for (int g = 1; g <= 32; g++)
				{
					if (this.plugin.getConfig().getString("config.gym"+g).equalsIgnoreCase(args[1]))
					{
						gymName = true;
						
						gym = g;
						
						break outerloop;
					}
					
				}
					
			if (args[1].matches("(gym|GYM)[1-9]?[0-9]") || gymName == true) {
  				
  				
  				//int i = gym - 1;
  				
  				if (gymName == true) {
  					//do nothing all good.
  				}
  				else {
  					String gymArg = args[1].replace("gym", "");
  					
  					try
  					{
  						gym = Integer.parseInt(gymArg);
  					}
  					catch (NumberFormatException nfe)
  					{
  						p.sendMessage(Text.of(TextColors.RED, args[1] + " is not a gym!"));
  						return CommandResult.empty();
  					}
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
			else {
				p.sendMessage(Text.of(TextColors.RED, "Gym " + args[1] + " is not a valid gym."));
			}
			gymName = false;
			
		}

		return CommandResult.empty();
	}
}
