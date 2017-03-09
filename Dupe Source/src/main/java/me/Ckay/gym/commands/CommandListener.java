package me.Ckay.gym.commands;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.sound.SoundCategories;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.collect.Lists;

import me.Ckay.gym.PixelGym;
import me.Ckay.gym.config.PluginConfiguration;
import me.Ckay.gym.scoreboard.ScoreboardManager;

public class CommandListener
{
	private PixelGym plugin;

	public CommandListener(PixelGym plugin)
	{
		this.plugin = plugin;
	}

	@Listener
	public void onCommand(SendCommandEvent event, @Root Player p)
	{
		String commandLabel = event.getCommand();
		String[] args = event.getArguments().isEmpty() ? new String[] {} : event.getArguments().split(" ");

		if (commandLabel.equalsIgnoreCase("gym"))
		{
			event.setCancelled(true);
			handleGym(p, args);
		}
		else if ((commandLabel.equalsIgnoreCase("e4")) && (this.plugin.enablee4.equalsIgnoreCase("true")))
		{
			event.setCancelled(true);
			handleE4(p, args);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void handleGym(Player p, String[] args)
	{
		if (args.length == 0)
		{
			int time = plugin.getConfig().getConfig().getNode("config.cooldowntime").getInt();

			if (!p.hasPermission("pixelgym.leader"))
			{
				p.sendMessage(Text.of(TextColors.GOLD, "----- PixelmonGyms -----"));
				p.sendMessage(Text.of(""));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym list", TextColors.DARK_GREEN, " - Get a list of the gyms and there status."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym leaders", TextColors.DARK_GREEN, " - Get a list of the online gym leaders."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym rules <gym#>", TextColors.DARK_GREEN, " - Shows all the rules for the specified Gym."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym join <gym#>", TextColors.DARK_GREEN, " - Join the queue for the gym you want. Example: /gym join gym1"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym <leave | quit> <gym#>", TextColors.DARK_GREEN, " - Quits the gym queue of the specified gym. Example: /gym leave gym1."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym <check | position> <gym#>", TextColors.DARK_GREEN, " - Check your position in a queue. Example: /gym check gym1"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym see [Player]", TextColors.DARK_GREEN, " - Shows the gym badge case of a specific player."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym scoreboard", TextColors.DARK_GREEN, " - Toggle ScoreBoard."));
				p.sendMessage(Text.of(""));
				p.sendMessage(Text.of(TextColors.RED, "Plugin Made By ", TextColors.GOLD, "ABkayCkay"));
			}
			else if (p.hasPermission("pixelgym.leader"))
			{
				p.sendMessage(Text.of(TextColors.GOLD, "----- PixelmonGyms -----"));
				p.sendMessage(Text.of(""));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym list", TextColors.DARK_GREEN, " - Get a list of the gyms and there status."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym leaders", TextColors.DARK_GREEN, " - Get a list of the online gym leaders."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym scoreboard", TextColors.DARK_GREEN, " - Toggle ScoreBoard."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym rules <gym#>", TextColors.DARK_GREEN, " - Shows all the rules for the specified Gym."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym <check | position> <gym#>", TextColors.DARK_GREEN, " - Check your position in a queue. Example: /gym check gym1"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym join <gym#>", TextColors.DARK_GREEN, " - Join the queue for the gym you want. Example: /gym join gym1"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym <see | check> [Player]", TextColors.DARK_GREEN, " - Shows the gym badge case of a specific player. | = or, you can type see or check."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym next <gym#>", TextColors.DARK_GREEN, " - Grabs the first person of the specified gym queue and teleports them to the gym. (It also displays the gym rules for them in chat, so you don't need to)"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym remove <gym#>", TextColors.DARK_GREEN, " - Remove's the first person of the specified gym queue (If someone has disconnected and does not relog after a while)"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym giveTM <gym#> [Player]", TextColors.DARK_GREEN, " - Give's the TM to the player if they had there inventory full when winning the gym!"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym <sq | skip | skipq> <gym#> [Player]", TextColors.DARK_GREEN, " - Skips the players cooldown of the specified gym, most commonly used if they disconnect mid battle and the automatic cooldown apply's"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym <winner | win | w> <gym#> [Player]", TextColors.DARK_GREEN, " - Sets the gym challeger to a winner, giving them the badge for the next gym!"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym <lost | lose | l> <gym#> [Player]", TextColors.DARK_GREEN, " - Sets the gym challeger to a loser, teleporting them out of the gym and giving them a " + time + " minute cooldown!"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym <leave | quit> <gym#>", TextColors.DARK_GREEN, " - Quits the gym queue of the specified gym. Example: /gym leave gym1."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym sendrules <gym#> (Username)", TextColors.DARK_GREEN, " - Force shows the specifed gym's rules to the player specifed."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym open <gym#>", TextColors.DARK_GREEN, " - Open a particular gym."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym close <gym#>", TextColors.DARK_GREEN, " - Close a particular gym."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym heal", TextColors.DARK_GREEN, " - Heals your pokemon."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym quit", TextColors.DARK_GREEN, " - Force quits the gym battle."));
				p.sendMessage(Text.of(""));
				p.sendMessage(Text.of(TextColors.RED, "Plugin Made By ", TextColors.GOLD, "ABkayCkay"));
			}
			else if (p.hasPermission("pixelgym.admin"))
			{
				p.sendMessage(Text.of(TextColors.GOLD, "----- PixelmonGyms -----"));
				p.sendMessage(Text.of(""));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym list", TextColors.DARK_GREEN, " - Get a list of the gyms and there status."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym leaders", TextColors.DARK_GREEN, " - Get a list of the online gym leaders."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym scoreboard", TextColors.DARK_GREEN, " - Toggle ScoreBoard."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym rules <gym#>", TextColors.DARK_GREEN, " - Shows all the rules for the specified Gym."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym <check | position> <gym#>", TextColors.DARK_GREEN, " - Check your position in a queue. Example: /gym check gym1"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym join <gym#>", TextColors.DARK_GREEN, " - Join the queue for the gym you want. Example: /gym join gym1"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym <see | check> [Player]", TextColors.DARK_GREEN, " - Shows the gym badge case of a specific player. | = or, you can type see or check."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym next <gym#>", TextColors.DARK_GREEN, " - Grabs the first person of the specified gym queue and teleports them to the gym. (It also displays the gym rules for them in chat, so you don't need to)"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym remove <gym#>", TextColors.DARK_GREEN, " - Remove's the first person of the specified gym queue (If someone has disconnected and does not relog after a while)"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym giveTM <gym#> [Player]", TextColors.DARK_GREEN, " - Give's the TM to the player if they had there inventory full when winning the gym!"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym <winner | win | w> <gym#> [Player]", TextColors.DARK_GREEN, " - Sets the gym challeger to a winner, giving them the badge for the next gym!"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym <lost | lose | l> <gym#> [Player]", TextColors.DARK_GREEN, " - Sets the gym challeger to a loser, teleporting them out of the gym and giving them a " + time + " minute cooldown!"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym givebadge <gym#> [player]", TextColors.DARK_GREEN, " - Need to give badges quickly? Then use this command to give player's there badge's, avoiding the cooldown and having to be in a queue."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym delbadge <gym#> [player]", TextColors.DARK_GREEN, " - Need to delete badges quickly? Then use this command to remove player's badge's, avoiding the cooldown and having to be in a queue."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym addTM <gym#>", TextColors.DARK_GREEN, " - Adds the item in hand as a TM to the specified gym for the winners to randomly win!"));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym <leave | quit> <gym#>", TextColors.DARK_GREEN, " - Quits the gym queue of the specified gym. Example: /gym leave gym1."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym setwarp <gym#>", TextColors.DARK_GREEN, " - Used for the queue system, set a warp that is only a number. E.G: /gym setwarp 1 in the gym1 challanger spot."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym delwarp <gym#>", TextColors.DARK_GREEN, " - Used for the queue system, delete a warp that you no longer need. E.G: /gym delwarp 1 to remove the gym1 teleport."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym closeall", TextColors.DARK_GREEN, " - Closes all Gym's."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym warp [warp name]", TextColors.DARK_GREEN, " - Warp to a gym warp! (For testing teleport locations of the queue system)."));
				p.sendMessage(Text.of(TextColors.GREEN, "/pixelgym", TextColors.DARK_GREEN, " - More admin commands"));
			}

			int req = this.plugin.getConfig().getInt("config.prestige_req");

			if ((this.plugin.settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym" + req) != null) && (this.plugin.settings.getBadge().getString("Players." + p.getUniqueId() + ".Badges.gym" + req).equals("Won")))
			{
				p.sendMessage(Text.of(TextColors.GOLD, "----- Prestige -----"));
				p.sendMessage(Text.of(TextColors.GOLD, "/gym prestige - Prestige to reset your gym badges and receive rewards!"));
			}
		}
		else if (args.length == 1)
		{
			if ((args[0].equalsIgnoreCase("scoreboard")) && (this.plugin.getConfig().getString("config.scoreboard").equals("True")))
			{
				if (this.plugin.hashmap.containsKey(p))
				{
					p.sendMessage(Text.of(TextColors.GRAY, "Scoreboard - ", TextColors.RED, "Disabled"));
					p.setScoreboard(ScoreboardManager.clear);
					this.plugin.hashmap.remove(p);
				}
				else
				{
					p.setScoreboard(ScoreboardManager.board);
					this.plugin.hashmap.put(p, null);

					p.sendMessage(Text.of(TextColors.GRAY, "Scoreboard - ", TextColors.GREEN, "Enabled"));
					ScoreboardManager.updateScoreboard();
				}
			}
			else if (args[0].equalsIgnoreCase("leaders"))
			{
				p.sendMessage(Text.of(TextColors.GOLD, "----- Online Gym Leaders -----"));
				p.sendMessage(Text.of(""));
				for (Player staff : Sponge.getServer().getOnlinePlayers())
				{
					if (p.canSee(staff))
					{
						if (!staff.getName().equalsIgnoreCase("ABkayCkay"))
						{
							for (int i = 1; i <= 32; i++)
							{
								if ((staff.hasPermission("pixelgym.gym" + i)) && (this.plugin.getConfig().getString("config.gym" + i + "enabled").equalsIgnoreCase("true")))
								{
									p.sendMessage(Text.of(TextColors.GREEN, staff.getName(), TextColors.BLACK, " - ", TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString(new StringBuilder("config.gym").append(i).append("colour").toString())), TextStyles.BOLD, getConfig().getString(new StringBuilder("config.gym").append(i).toString()) + " Gym"));
								}
							}
						}
					}
				}
			}
			else if (args[0].equalsIgnoreCase("prestige"))
			{
				int req = getConfig().getInt("config.prestige_req");

				if (!this.plugin.inPrest.contains(p.getUniqueId()))
				{
					if (this.plugin.settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym" + req) != null)
					{
						if (this.plugin.settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym" + req).equals("Won"))
						{
							this.plugin.inPrest.add(p.getUniqueId());

							p.sendMessage(Text.of(TextColors.RED, "You are about to reset your gym badges! This will delete all previous gym badges and give you money. Also so you can re-do the gym's."));
							p.sendMessage(Text.of(TextColors.GREEN, "Are you sure you want to prestige? There is no going back. Type '/gym confirm' if you are sure. And '/gym deny' if you no longer want to prestige!"));
						}
						else
						{
							p.sendMessage(Text.of(TextColors.RED, "You have not won Gym" + req + " Badge!"));
						}
					}
					else
					{
						p.sendMessage(Text.of(TextColors.RED, "You have never challenged Gym" + req + "!"));
					}

				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You are already in the list to confirm or deny prestiging. Type '/gym confirm' if you are sure. And '/gym deny' if you no longer want to prestige!"));
				}

			}
			else if (args[0].equalsIgnoreCase("confirm"))
			{
				int req = getConfig().getInt("config.prestige_req");

				if (this.plugin.inPrest.contains(p.getUniqueId()))
				{
					if ((this.plugin.settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym" + req) != null) && (this.plugin.settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym" + req).equals("Won")))
					{
						for (int i = 1; i <= 32; i++)
						{
							this.plugin.settings.getBadge().set("Players." + p.getUniqueId() + ".Badges.gym" + i, null);
						}

						int fee = getConfig().getInt("config.prestige_pay");

						this.plugin.getEconomy().getOrCreateAccount(p.getUniqueId()).get()
							.deposit(this.plugin.getEconomy().getDefaultCurrency(), new BigDecimal(fee), Cause.of(NamedCause.source(this.plugin)));

						p.sendMessage(Text.of(TextColors.GOLD, "You have been payed $" + fee + "! You may now re-do all the gym's!"));
					}

					p.sendMessage(Text.of(TextColors.GREEN, "Deleted all badges you have!"));
					this.plugin.settings.saveBadges();
					this.plugin.inPrest.remove(p.getUniqueId());
				}

			}
			else if (args[0].equalsIgnoreCase("deny"))
			{
				if (this.plugin.inPrest.contains(p.getUniqueId()))
				{
					this.plugin.inPrest.remove(p.getUniqueId());
					p.sendMessage(Text.of(TextColors.GREEN, "You have succefully denied prestiging. Your Badges will not be reset."));
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You have not done the prestige command! Type '/gym prestige' to get started."));
				}

			}
			else if (args[0].equals("list"))
			{
				p.sendMessage(Text.of(TextColors.GOLD, "----- PixelmonGyms -----"));
				p.sendMessage(Text.of(""));
				for (int i = 1; i <= 32; i++)
				{
					if (this.plugin.getConfig().getString("config.gym" + i + "enabled").equalsIgnoreCase("true"))
					{
						p.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString(new StringBuilder("config.gym").append(i).append("colour").toString())), TextStyles.BOLD, getConfig().getString(new StringBuilder("config.gym").append(i).toString()) + " Gym is: ", TextColors.DARK_GREEN, getConfig().getString(new StringBuilder("config.gym").append(i).append("stat").toString()), TextColors.BLUE, " - " + "Level Cap = " + getConfig().getString(new StringBuilder("config.gym").append(i).append("lvlcap").toString())));
					}

				}
			}
			else if ((args[0].equals("open")) && (p.hasPermission("pixelgym.leader")))
			{
				p.sendMessage(Text.of(TextColors.DARK_RED, "You need to specify a gym to open!"));
				p.sendMessage(Text.of(TextColors.DARK_RED, "Proper Usage: ", TextColors.RED, "/gym open <gym#>"));
			}
			else if ((args[0].equals("sendrules")) && (p.hasPermission("pixelgym.leader")))
			{
				p.sendMessage(Text.of(TextColors.DARK_RED, "You need to specify a gym to send rules about, as well as a player to send the rules too."));
				p.sendMessage(Text.of(TextColors.DARK_RED, "Proper Usage: ", TextColors.RED, "/gym sendrules <gym#> (username)"));
			}
			else if ((args[0].equals("close")) && (p.hasPermission("pixelgym.leader")))
			{
				p.sendMessage(Text.of(TextColors.DARK_RED, "You need to specify a gym to close!"));
				p.sendMessage(Text.of(TextColors.DARK_RED, "Proper Usage: ", TextColors.RED, "/gym close <gym#>"));
			}
			else if (args[0].equals("rules"))
			{
				p.sendMessage(Text.of(TextColors.DARK_RED, "You need to specify a gym that you want to read the rules of!"));
				p.sendMessage(Text.of(TextColors.DARK_RED, "Proper Usage: ", TextColors.RED, "/gym rules <gym#>"));
			}
			else if (args[0].equals("join"))
			{
				p.sendMessage(Text.of(TextColors.DARK_RED, "You need to specify a gym queue that you want to join!"));
				p.sendMessage(Text.of(TextColors.DARK_RED, "Proper Usage: ", TextColors.RED, "/gym join <gym#>"));
			}
			else if (args[0].equals("leave"))
			{
				p.sendMessage(Text.of(TextColors.DARK_RED, "You need to specify a gym queue that you want to leave!"));
				p.sendMessage(Text.of(TextColors.DARK_RED, "Proper Usage: ", TextColors.RED, "/gym leave <gym#>"));
			}
			else if ((args[0].equalsIgnoreCase("check")) || (args[0].equalsIgnoreCase("position")))
			{
				p.sendMessage(Text.of(TextColors.DARK_RED, "You need to specify a gym queue position that you want to check!"));
				p.sendMessage(Text.of(TextColors.DARK_RED, "Proper Usage: ", TextColors.RED, "/gym position <gym#>"));
			}
			else if (args[0].equals("next"))
			{
				p.sendMessage(Text.of(TextColors.DARK_RED, "You need to specify a gym to grab the first player of a queue for!"));
				p.sendMessage(Text.of(TextColors.DARK_RED, "Proper Usage: ", TextColors.RED, "/gym next <gym#>"));
			}
			else if ((args[0].equals("heal")) && (p.hasPermission("pixelgym.leader")) && (this.plugin.enableGymHeal.equalsIgnoreCase("true")))
			{
				Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokeheal " + p.getName().toString());
				p.sendMessage(Text.of("Your pixelmon have been healed!"));
			}
			else if ((args[0].equals("heal")) && (p.hasPermission("pixelgym.leader")) && (!this.plugin.enableGymHeal.equalsIgnoreCase("true")))
			{
				p.sendMessage(Text.of(TextColors.RED, "Gym/E4 Leader healing disabled in the plugin config"));
			}
			else if ((args[0].equals("quit")) && (p.hasPermission("pixelgym.leader")))
			{
				Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "endbattle");
				p.sendMessage(Text.of("You have successfully quit the battle!"));
			}
			else if ((args[0].equalsIgnoreCase("closeall")) && (p.hasPermission("pixelgym.admin")))
			{
				p.sendMessage(Text.of(TextColors.AQUA, "All gyms are now closed."));
				MessageChannel.TO_ALL.send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextColors.YELLOW, "All gyms are now closed."));

				getConfig().set("config.gym1stat", "Closed");
				getConfig().set("config.gym2stat", "Closed");
				getConfig().set("config.gym3stat", "Closed");
				getConfig().set("config.gym4stat", "Closed");
				getConfig().set("config.gym5stat", "Closed");
				getConfig().set("config.gym6stat", "Closed");
				getConfig().set("config.gym7stat", "Closed");
				getConfig().set("config.gym8stat", "Closed");
				getConfig().set("config.gym9stat", "Closed");
				getConfig().set("config.gym10stat", "Closed");
				getConfig().set("config.gym11stat", "Closed");
				getConfig().set("config.gym12stat", "Closed");
				getConfig().set("config.gym13stat", "Closed");
				getConfig().set("config.gym14stat", "Closed");
				getConfig().set("config.gym15stat", "Closed");
				getConfig().set("config.gym16stat", "Closed");
				getConfig().set("config.gym17stat", "Closed");
				getConfig().set("config.gym18stat", "Closed");
				getConfig().set("config.gym19stat", "Closed");
				getConfig().set("config.gym20stat", "Closed");
				getConfig().set("config.gym21stat", "Closed");
				getConfig().set("config.gym22stat", "Closed");
				getConfig().set("config.gym23stat", "Closed");
				getConfig().set("config.gym24stat", "Closed");
				getConfig().set("config.gym25stat", "Closed");
				getConfig().set("config.gym26stat", "Closed");
				getConfig().set("config.gym27stat", "Closed");
				getConfig().set("config.gym28stat", "Closed");
				getConfig().set("config.gym29stat", "Closed");
				getConfig().set("config.gym30stat", "Closed");
				getConfig().set("config.gym31stat", "Closed");
				getConfig().set("config.gym32stat", "Closed");

				ScoreboardManager.updateScoreboard();
			}
		}
		else if (args.length == 2)
		{
			if ((args[0].equalsIgnoreCase("admincheck")) && (Sponge.getServer().getPlayer(args[1]).orElse(null) != null))
			{
				UUID uuid = Sponge.getServer().getPlayer(args[1]).get().getUniqueId();
				Player playerTarget = Sponge.getServer().getPlayer(uuid).get();

				for (int i = 1; i <= 32; i++)
				{
					if (((List) this.plugin.queues.get(Integer.valueOf(i))).contains(uuid))
					{
						p.sendMessage(Text.of(TextColors.GREEN, playerTarget.getName() + " is in gym" + i + " queue."));
					}
					if (((List) this.plugin.inArena.get(Integer.valueOf(i))).contains(uuid))
					{
						p.sendMessage(Text.of(TextColors.GREEN, playerTarget.getName() + " is in gym" + i + " inArena."));
					}
					if (((List) this.plugin.cooldownGym.get(Integer.valueOf(i))).contains(uuid))
					{
						p.sendMessage(Text.of(TextColors.GREEN, playerTarget.getName() + " is in gym" + i + " Cooldown."));
					}
				}

				p.sendMessage(Text.of(TextColors.GOLD, " " + uuid));
			}

			if (args[0].equalsIgnoreCase("join"))
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
					return;
				}

				if ((gym < 1) || (gym > 32))
				{
					return;
				}

				int i = gym - 1;

				UUID o = p.getUniqueId();

				if (!((Map) this.plugin.cooldownTime.get(Integer.valueOf(gym))).containsKey(o))
				{
					if (this.plugin.settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym" + gym) != null)
					{
						if (this.plugin.settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym" + gym).equals("Won"))
						{
							p.sendMessage(Text.of(TextColors.RED, "You have already completed this gym! You may not do it again!"));
						}

					}
					else if (gym == 1)
					{
						if (((List) this.plugin.queues.get(Integer.valueOf(gym))).contains(p.getUniqueId()))
						{
							p.sendMessage(Text.of(TextColors.RED, "You are already in this queue, please wait to be teleported."));
						}
						else
						{
							((List) this.plugin.queues.get(Integer.valueOf(gym))).add(p.getUniqueId());

							//String.valueOf(this.plugin.getConfig().getString("config.gym1colour"))
							
							p.sendMessage(Text.of(TextColors.GREEN, "Added to queue: ", TextColors.YELLOW, TextStyles.BOLD, gym, TextColors.BLACK, " (", TextSerializers.FORMATTING_CODE.deserialize(String.valueOf(this.plugin.getConfig().getString("config.gym"+gym+"colour"))), TextStyles.BOLD, this.plugin.getConfig().getString("config.gym1"), TextColors.BLACK, ")"));
							p.sendMessage(Text.of(TextColors.GOLD, "You are in position " + ((List) this.plugin.queues.get(Integer.valueOf(gym))).size() + " for the " + getConfig().getString("config.gym1") + " Gym"));
							p.sendMessage(Text.of(TextColors.GREEN, "Notified gym leaders of gym1", TextColors.BLACK, " (", TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString("config.gym1colour")), TextStyles.BOLD, this.plugin.getConfig().getString("config.gym1"), TextColors.BLACK, ")", TextColors.GREEN, " that you are waiting to be battled!"));

							for (Player leader : Sponge.getServer().getOnlinePlayers())
							{
								if (leader.hasPermission("pixelgym." + args[1]))
								{
									leader.sendMessage(Text.of(TextColors.BLUE, "A challenger has joined queue ", TextColors.YELLOW, TextStyles.BOLD, gym, TextColors.BLACK, " (", TextSerializers.FORMATTING_CODE.deserialize(new StringBuilder(this.plugin.getConfig().getString(new StringBuilder("config.")
										.append(args[1]).append("colour").toString())).toString()), TextStyles.BOLD, this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()), TextColors.BLACK, ")", TextColors.GOLD, " (" + p.getName() + ")"));

									leader.sendMessage(Text.of(TextColors.BLUE, "Type /gym next " + args[1] + " to teleport them to your gym."));
								}

							}

						}

					}
					else if (this.plugin.getConfig().getString("config.gymrequire").equalsIgnoreCase("False") || this.plugin.settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym" + i) != null)
					{
						if (this.plugin.getConfig().getString("config.gymrequire").equalsIgnoreCase("False") || this.plugin.settings.getBadge().getString("Players." + p.getUniqueId() + ".Badges.gym" + i).equals("Won"))
						{
							if (((List) this.plugin.queues.get(Integer.valueOf(gym))).contains(p.getUniqueId()))
							{
								p.sendMessage(Text.of(TextColors.RED, "You are already in this queue, please wait to be teleported."));
								return;
							}

							this.plugin.queues.get(Integer.valueOf(gym)).add(p.getUniqueId());

							p.sendMessage(Text.of(TextColors.GREEN, "Added to queue: ", TextColors.YELLOW, TextStyles.BOLD, gym, TextColors.BLACK, " (", TextSerializers.FORMATTING_CODE.deserialize(new StringBuilder(String.valueOf(this.plugin.getConfig().getString(new StringBuilder("config.")
								.append(args[1]).append("colour").toString())))
									.toString()), TextStyles.BOLD, this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()), TextColors.BLACK, ")"));
							p.sendMessage(Text.of(TextColors.GOLD, "You are in position " + ((List) this.plugin.queues.get(Integer.valueOf(gym))).size() + " for the " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Gym"));
							p.sendMessage(Text.of(TextColors.GREEN, "Notified gym leaders of gym" + gym, TextColors.BLACK, " (", TextSerializers.FORMATTING_CODE.deserialize(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("colour").toString())), TextStyles.BOLD, this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()), TextColors.BLACK, ")", TextColors.GREEN, " that you are waiting to be battled!"));

							for (Player leader : Sponge.getServer().getOnlinePlayers())
							{
								if (leader.hasPermission("pixelgym." + args[1]))
								{
									leader.sendMessage(Text.of(TextColors.BLUE, "A challenger has joined queue ", TextColors.YELLOW, TextStyles.BOLD, gym, TextColors.BLACK, " (", TextSerializers.FORMATTING_CODE.deserialize(new StringBuilder(String.valueOf(this.plugin.getConfig()
										.getString(new StringBuilder("config.")
											.append(args[1]).append("colour").toString()))).toString()), TextStyles.BOLD, this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()), TextColors.BLACK, ")", TextColors.GOLD, " (", p.getName(), ")"));

									leader.sendMessage(Text.of(TextColors.BLUE, "Type /gym next " + args[1] + " to teleport them to your gym."));
								}
							}
						}
						else
						{
							p.sendMessage(Text.of(TextColors.RED, "You cannot join this queue as you have not won the previous badge!"));
						}
					}
					else
					{
						p.sendMessage(Text.of(TextColors.RED, "You cannot join the gym queue for gym" + gym + " because you do not have the previous badge. (gym" + i + ")"));
					}
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You have to wait " + ((Map) this.plugin.cooldownTime.get(Integer.valueOf(gym))).get(o) + " minutes before you can try gym" + gym + " again."));
				}
			}
			else if ((args[0].equalsIgnoreCase("check")) || (args[0].equalsIgnoreCase("position")))
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
					return;
				}

				if ((gym < 1) || (gym > 32))
				{
					return;
				}

				Player playerFirst = null;

				if (((List) this.plugin.queues.get(Integer.valueOf(gym))).size() > 0)
				{
					UUID uuid = (UUID) ((List) this.plugin.queues.get(Integer.valueOf(gym))).get(0);
					playerFirst = Sponge.getServer().getPlayer(uuid).orElse(null);

					int iu = ((List) this.plugin.queues.get(Integer.valueOf(gym))).indexOf(p.getUniqueId()) + 1;

					if (((List) this.plugin.queues.get(Integer.valueOf(gym))).contains(p.getUniqueId()))
					{
						p.sendMessage(Text.of(TextColors.GOLD, "You are currently in position " + iu + " for the " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Gym"));
						p.sendMessage(Text.of(TextColors.GOLD, "The queue size for the " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Gym is " + ((List) this.plugin.queues.get(Integer.valueOf(gym))).size()));
					}
					else if ((p.hasPermission("pixelgym." + args[1])) && (!((List) this.plugin.queues.get(Integer.valueOf(gym))).contains(p.getUniqueId())))
					{
						p.sendMessage(Text.of(TextColors.GOLD, "The queue size for the " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Gym is " + ((List) this.plugin.queues.get(Integer.valueOf(gym))).size()));
						p.sendMessage(Text.of(TextColors.GOLD, "The first player of the queue is: (" + playerFirst.getName() + ")"));
					}
					else if ((((List) this.plugin.queues.get(Integer.valueOf(gym))).contains(p.getUniqueId())) && (p.hasPermission("pixelgym." + args[1])))
					{
						p.sendMessage(Text.of(TextColors.GOLD, "The queue size for the " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Gym is " + ((List) this.plugin.queues.get(Integer.valueOf(gym))).size()));
						p.sendMessage(Text.of(TextColors.GOLD, "The first player of the queue is: (" + playerFirst.getName() + ")"));
					}
					else
					{
						p.sendMessage(Text.of(TextColors.RED, "You are not in queue " + args[1]));
					}
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "There is no one in this queue!"));
				}
			}
			else if ((args[0].equalsIgnoreCase("leave")) || (args[0].equalsIgnoreCase("quit")))
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

					return;
				}

				if ((gym < 1) || (gym > 32))
				{
					return;
				}

				if (((List) this.plugin.queues.get(Integer.valueOf(gym))).contains(p.getUniqueId()))
				{
					if (!((List) this.plugin.inArena.get(Integer.valueOf(gym))).contains(p.getUniqueId()))
					{
						((List) this.plugin.queues.get(Integer.valueOf(gym))).remove(p.getUniqueId());

						World w = Sponge.getServer().getWorld(this.plugin.settings.getData().getString("warps.spawn.world")).orElse(null);
						double x = this.plugin.settings.getData().getDouble("warps.spawn.x");
						double y = this.plugin.settings.getData().getDouble("warps.spawn.y");
						double z = this.plugin.settings.getData().getDouble("warps.spawn.z");
						p.setLocation(new Location<>(w, x, y, z));

						p.sendMessage(Text.of(TextColors.GREEN, "You have successfully been removed from the gym" + gym + " queue!"));
					}
					else
					{
						p.sendMessage(Text.of(TextColors.RED, "You cannot leave the queue as you are in the area!"));
					}
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You are not in queue " + gym));
				}
			}
			else if (args[0].equalsIgnoreCase("remove"))
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
					return;
				}

				if ((gym < 1) || (gym > 32))
				{
					return;
				}

				if ((p.hasPermission("pixelgym.leader")) && ((p.hasPermission("pixelgym." + args[1])) || (p.hasPermission("pixelgym.admin"))))
				{
					UUID removeUUID = (UUID) ((List) this.plugin.queues.get(Integer.valueOf(gym))).get(0);
					Player toRemove = Sponge.getServer().getPlayer(removeUUID).orElse(null);

					if (this.plugin.settings.getData().get("warps.spawn") != null)
					{
						((List) this.plugin.queues.get(Integer.valueOf(gym))).remove(0);

						if (((List) this.plugin.inArena.get(Integer.valueOf(gym))).contains(removeUUID))
						{
							((List) this.plugin.inArena.get(Integer.valueOf(gym))).remove(removeUUID);
						}

						World w = Sponge.getServer().getWorld(this.plugin.settings.getData().getString("warps.spawn.world")).get();
						double x = this.plugin.settings.getData().getDouble("warps.spawn.x");
						double y = this.plugin.settings.getData().getDouble("warps.spawn.y");
						double z = this.plugin.settings.getData().getDouble("warps.spawn.z");
						toRemove.setLocation(new Location<>(w, x, y, z));
						toRemove.sendMessage(Text.of(TextColors.GREEN, "You have been removed from the queue by a gym leader! (", TextSerializers.FORMATTING_CODE.deserialize(new StringBuilder(String.valueOf(this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("colour").toString()))).append(TextStyles.BOLD)
							.append(this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).toString())).append(TextColors.GREEN)
							.append(") You can re-enter the queue, but make sure you are not Afk and co-operate with the gym leader!").toString())));

						p.sendMessage(Text.of(TextColors.GREEN, "Successfully telported " + toRemove.getName() + " out of your gym!"));
						p.sendMessage(Text.of(TextColors.GREEN, "You are now ready for your next battle, type: /gym next gym" + gym));
					}
					else
					{
						p.sendMessage(Text.of(TextColors.RED, "Warp point 'spawn' does not exist. Type: /gym setwarp spawn. (this is the teleport location to move challengers out of the gym."));
					}

				}

			}
			else if (args[0].equalsIgnoreCase("next"))
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
					return;
				}

				if ((gym < 1) || (gym > 32))
				{
					return;
				}

				int add = gym + 1;

				if (p.hasPermission("pixelgym.leader"))
				{
					if (p.hasPermission("pixelgym." + args[1]))
					{
						Player playerToTeleport = null;

						while (playerToTeleport == null)
						{
							if (!this.plugin.queues.get(Integer.valueOf(gym)).isEmpty())
							{
								if (this.plugin.settings.getData().get("warps." + args[1]) == null)
								{
									p.sendMessage(Text.of(TextColors.RED, "Warp " + args[1] + " does not exist!"));
									return;
								}

								UUID uuid = (UUID) ((List) this.plugin.queues.get(Integer.valueOf(gym))).get(0);
								playerToTeleport = Sponge.getServer().getPlayer(uuid).get();

								p.sendMessage(Text.of(TextColors.GREEN, "Getting first player from queue ", TextColors.GOLD, " (" + playerToTeleport.getName() + ")", TextColors.YELLOW, TextStyles.BOLD, gym, TextColors.BLACK, " (", TextSerializers.FORMATTING_CODE.deserialize(new StringBuilder(this.plugin.getConfig().getString(new StringBuilder("config.gym")
									.append(gym).append("colour").toString())).toString()), TextStyles.BOLD, this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).toString()), TextColors.BLACK, ")"));
								p.sendMessage(Text.of(TextColors.BLUE, "Make sure you type ", TextColors.RED, "/gym lost gym" + gym + " (player) ", TextColors.BLUE, "or ", TextColors.GREEN, "/gym win gym" + gym + " (player)", TextColors.BLUE, " after they have won or lost the battle. (They need it to join gym" + add + " queue (If they won.)) And it teleports them out of your gym!"));
							}
							else
							{
								p.sendMessage(Text.of(TextColors.RED, "There are currently no people in the queue ", TextColors.YELLOW, TextStyles.BOLD, gym, TextColors.BLACK, " (", TextSerializers.FORMATTING_CODE.deserialize(new StringBuilder(String.valueOf(this.plugin.getConfig().getString(new StringBuilder("config.gym")
									.append(gym).append("colour").toString()))).toString()), TextStyles.BOLD, this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).toString()), TextColors.BLACK, ")"));

								return;
							}
						}

						if (this.plugin.getConfig().getString("config.gymfee").equalsIgnoreCase("True"))
						{
							int fee = getConfig().getInt("config.gymfee");
							UniqueAccount account = this.plugin.getEconomy().getOrCreateAccount(playerToTeleport.getUniqueId()).get();
							UniqueAccount pAccount = this.plugin.getEconomy().getOrCreateAccount(p.getUniqueId()).get();

							if (account.getBalance(this.plugin.getEconomy().getDefaultCurrency()).doubleValue() >= fee)
							{
								Sponge.getCommandManager().process(playerToTeleport, "pay " + p.getName() + " " + getConfig().getString("config.gymfeeamount"));
								World w = Sponge.getServer().getWorld(this.plugin.settings.getData().getString("warps." + args[1] + ".world")).get();

								TransactionResult TookMoney = account.withdraw(this.plugin.getEconomy().getDefaultCurrency(), new BigDecimal(fee), Cause.of(NamedCause.notifier(p)));

								if (TookMoney.getResult() == ResultType.SUCCESS)
								{
									pAccount.deposit(this.plugin.getEconomy().getDefaultCurrency(), new BigDecimal(fee), Cause.of(NamedCause.notifier(p)));
								}
								else
								{
									// TODO ?
									return;
								}

								double x = this.plugin.settings.getData().getDouble("warps." + args[1] + ".x");
								double y = this.plugin.settings.getData().getDouble("warps." + args[1] + ".y");
								double z = this.plugin.settings.getData().getDouble("warps." + args[1] + ".z");

								playerToTeleport.setLocation(new Location<>(w, x, y, z));

								playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "Teleported to ", TextColors.YELLOW, TextStyles.BOLD, args[1] + "!"));
								
								playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "Welcome to ", TextSerializers.FORMATTING_CODE.deserialize(new StringBuilder(String.valueOf(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1])
										.append("colour").toString()))).toString()), TextStyles.BOLD, this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString()), " Gym!"));
								
								//playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "Welcome to ", TextSerializers.FORMATTING_CODE.deserialize(new StringBuilder(String.valueOf(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1])
									  //.append("colour").toString()))), TextStyles.BOLD,this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString())).append(" Gym!").toString()));
								playerToTeleport.sendMessage(Text.of(TextColors.GOLD, "----- " + getConfig().getString(new StringBuilder("config.gym").append(gym).toString()) + " Gym Rules -----"));
								playerToTeleport.sendMessage(Text.of(""));
								playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "1) " + getConfig().getString(new StringBuilder("config.gym").append(gym).append("rule1").toString())));
								playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "2) " + getConfig().getString(new StringBuilder("config.gym").append(gym).append("rule2").toString())));
								playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "3) " + getConfig().getString(new StringBuilder("config.gym").append(gym).append("rule3").toString())));
								playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "4) " + getConfig().getString(new StringBuilder("config.gym").append(gym).append("rule4").toString())));
								playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "5) " + getConfig().getString(new StringBuilder("config.gym").append(gym).append("rule5").toString())));
								Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokeheal " + playerToTeleport.getName().toString());
								Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokeheal " + p.getName().toString());
								playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "Your pixelmon have been healed!"));

								((List) this.plugin.inArena.get(Integer.valueOf(gym))).add(playerToTeleport.getUniqueId());
							}
							else
							{
								playerToTeleport.sendMessage(Text.of(TextColors.RED, "You do not have enough money to face the gym!"));
								((List) this.plugin.queues.get(Integer.valueOf(gym))).remove(0);
								p.sendMessage(Text.of(TextColors.RED, "First player did not have enough money, type /gym next gym" + gym + " to get the next player."));
								return;
							}

						}
						else
						{
							World w = Sponge.getServer().getWorld(this.plugin.settings.getData().getString("warps." + args[1] + ".world")).get();
							double x = this.plugin.settings.getData().getDouble("warps." + args[1] + ".x");
							double y = this.plugin.settings.getData().getDouble("warps." + args[1] + ".y");
							double z = this.plugin.settings.getData().getDouble("warps." + args[1] + ".z");
							playerToTeleport.setLocation(new Location<>(w, x, y, z));
							playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "Teleported to ", TextColors.YELLOW, TextStyles.BOLD, args[1] + "!"));
							playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "Welcome to " + TextSerializers.FORMATTING_CODE.deserialize(new StringBuilder(String.valueOf(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).append("colour").toString()))).append(TextStyles.BOLD).append(this.plugin.getConfig().getString(new StringBuilder("config.").append(args[1]).toString())).append(" Gym!").toString())));
							playerToTeleport.sendMessage(Text.of(TextColors.GOLD, "----- " + getConfig().getString(new StringBuilder("config.gym").append(gym).toString()) + " Gym Rules -----"));
							playerToTeleport.sendMessage(Text.of(""));
							playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "1) " + getConfig().getString(new StringBuilder("config.gym").append(gym).append("rule1").toString())));
							playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "2) " + getConfig().getString(new StringBuilder("config.gym").append(gym).append("rule2").toString())));
							playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "3) " + getConfig().getString(new StringBuilder("config.gym").append(gym).append("rule3").toString())));
							playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "4) " + getConfig().getString(new StringBuilder("config.gym").append(gym).append("rule4").toString())));
							playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "5) " + getConfig().getString(new StringBuilder("config.gym").append(gym).append("rule5").toString())));
							Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokeheal " + playerToTeleport.getName().toString());
							Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokeheal " + p.getName().toString());
							playerToTeleport.sendMessage(Text.of(TextColors.GREEN, "Your pixelmon have been healed!"));

							((List) this.plugin.inArena.get(Integer.valueOf(gym))).add(playerToTeleport.getUniqueId());
						}

					}
					else
					{
						p.sendMessage(Text.of(TextColors.RED, "You do not have permission to open gym" + gym + "!"));
					}

				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You are not a gym leader, you do not have permission to do this command!"));
				}
			}

			if (args[0].equalsIgnoreCase("showcase"))
			{
				if (args[1].equalsIgnoreCase("false"))
				{
					this.plugin.settings.getExtras().set("showcase." + p.getUniqueId(), "false");
					p.sendMessage(Text.of(TextColors.GREEN, "Gym Badge Showcase will no longer be given to you!"));
					this.plugin.settings.saveExtras();
				}

				if (args[1].equalsIgnoreCase("true"))
				{
					this.plugin.settings.getExtras().set("showcase." + p.getUniqueId(), "true");
					p.sendMessage(Text.of(TextColors.GREEN, "Gym Badge Showcase will now be given to you!"));
					this.plugin.settings.saveExtras();
				}
			}

			if ((args[0].equalsIgnoreCase("see")) || (args[0].equalsIgnoreCase("s")))
			{
				if (Sponge.getServer().getPlayer(args[1]).orElse(null) != null)
				{
					Player playerBadges = Sponge.getServer().getPlayer(args[1]).get();

					Inventory myInventory_ = Inventory.builder()
						.of(InventoryArchetypes.DOUBLE_CHEST)
						.property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of(TextColors.GREEN, "Badges!")))
						.build(this.plugin);

					for (int i = 1; i <= 32; i++)
					{
						int u = i - 1;

						if (this.plugin.settings.getBadge().get("Players." + playerBadges.getUniqueId() + ".Badges.gym" + i) != null)
						{
							if ((this.plugin.settings.getBadge().getString("Players." + playerBadges.getUniqueId() + ".Badges.gym" + i).equalsIgnoreCase("Won")) && (this.plugin.getConfig().getString("config.gym" + i + "badge") != null))
							{
								ItemStack badge = ItemStack.builder()
									.itemType(Sponge.getRegistry().getType(ItemType.class, this.plugin.getConfig().getString("config.gym" + i + "badge")).get())
									.build();

								badge.offer(Keys.DISPLAY_NAME, Text.of(TextColors.RED, TextSerializers.FORMATTING_CODE.deserialize(new StringBuilder(String.valueOf(this.plugin.getConfig().getString(new StringBuilder("config.gym")
									.append(i)
									.append("colour")
									.toString()))).toString()), TextStyles.BOLD, this.plugin.getConfig().getString(new StringBuilder("config.gym").append(i).toString()), " Badge!"));

								if (this.plugin.settings.getLogs().get("Challengers." + playerBadges.getUniqueId() + ".gym" + i) != null)
								{
									badge.offer(Keys.ITEM_LORE, Arrays.asList(new Text[] { Text.of(TextColors.GOLD, "Date/Time Won: ", TextColors.GREEN, this.plugin.settings.getLogs().getString(new StringBuilder("Challengers.").append(playerBadges.getUniqueId())
										.append(".").append("gym").append(i).append(".Date/Time Won").toString())), Text.of(TextColors.GOLD, "Gym Leader: ", TextColors.GREEN, this.plugin.settings.getLogs().getString(new StringBuilder("Challengers.")
											.append(playerBadges.getUniqueId()).append(".").append("gym").append(i).append(".Gym Leader").toString())) }));

									List<Inventory> slots = Lists.newArrayList(myInventory_.slots());
									slots.get(u).set(badge);
								}
								else
								{
									badge.offer(Keys.ITEM_LORE, Arrays.asList(new Text[] { Text.of(TextColors.RED, "Gym badge info un-known! (Badge won before feature implemented!)") }));

									List<Inventory> slots = Lists.newArrayList(myInventory_.slots());
									slots.get(u).set(badge);
								}
							}
						}
					}

					if (!this.plugin.hasOpen.contains(p.getName()))
					{
						this.plugin.hasOpen.add(p.getName());
					}

					p.openInventory(myInventory_, Cause.of(NamedCause.source(this.plugin.getContainer())));
					p.sendMessage(Text.of(TextColors.GREEN, "Opening " + playerBadges.getName() + "'s badge showcase!"));
				}
			}

			if (args[0].equalsIgnoreCase("sendrules"))
			{
				if (this.plugin.getConfig().getString("config." + args[1]) != null)
				{
					p.sendMessage(Text.of(TextColors.RED, "You need to specify a player that you want to send the rules to!"));
					p.sendMessage(Text.of(TextColors.DARK_RED, "Proper Usage: ", TextColors.RED, "/gym sendrules <gym#> (username)"));
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "There are only 32 gym's, please use a valid gym!"));
				}
			}

			if (args[0].equalsIgnoreCase("rules"))
			{
				if (this.plugin.getConfig().getString("config." + args[1]) != null)
				{
					if (this.plugin.getConfig().getString("config." + args[1] + "enabled").equalsIgnoreCase("true"))
					{
						p.sendMessage(Text.of(TextColors.GOLD, "----- " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Gym Rules -----"));
						p.sendMessage(Text.of(""));
						p.sendMessage(Text.of(TextColors.GREEN, "1) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule1").toString())));
						p.sendMessage(Text.of(TextColors.GREEN, "2) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule2").toString())));
						p.sendMessage(Text.of(TextColors.GREEN, "3) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule3").toString())));
						p.sendMessage(Text.of(TextColors.GREEN, "4) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule4").toString())));
						p.sendMessage(Text.of(TextColors.GREEN, "5) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule5").toString())));
					}
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "The gym " + args[1] + " does not exist!"));
					p.sendMessage(Text.of(TextColors.RED, "Try /gym rules gym1"));
				}
			}
			if (args[0].equalsIgnoreCase("open"))
			{
				if ((p.hasPermission("pixelgym.leader")) || (p.hasPermission("pixelgym.admin")))
				{
					if (this.plugin.getConfig().getString("config." + args[1]) != null)
					{
						if ((p.hasPermission("pixelgym." + args[1])) || (p.hasPermission("pixelgym.admin")))
						{
							if ((args[1].equalsIgnoreCase("e41")) || (args[1].equalsIgnoreCase("e42")) || (args[1].equalsIgnoreCase("e43")) || (args[1].equalsIgnoreCase("e44")))
							{
								p.sendMessage(Text.of(TextColors.RED, "To open the Elite 4, type /e4 open e4#. Not /gym open e4#"));
							}
							else if (this.plugin.getConfig().getString("config." + args[1] + "stat").equals("Open"))
							{
								p.sendMessage(Text.of(TextColors.RED, "The " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Gym is already open! "));
							}
							else if (this.plugin.getConfig().getString("config." + args[1] + "stat").equals("Closed"))
							{
								if (this.plugin.getConfig().getString("config." + args[1] + "enabled").equalsIgnoreCase("True"))
								{
									MessageChannel.TO_ALL.send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString(new StringBuilder("config.")
										.append(args[1]).append("colour").toString())).getChildren().get(0).getColor(), "The " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Gym is now ", TextColors.GREEN, "Open"));

									getConfig().set("config." + args[1] + "stat", "Open");

									p.sendMessage(Text.of(TextColors.GOLD, "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ "));

									if (this.plugin.getConfig().getString("config.scoreboard").equals("True"))
									{
										ScoreboardManager.updateScoreboard();
									}
								}
								else if (this.plugin.getConfig().getString("config." + args[1] + "enabled").equalsIgnoreCase("False"))
								{
									p.sendMessage(Text.of(TextColors.RED, "This gym, " + args[1] + " is disabled in the config. Please open an enabled gym."));
								}
							}
						}
						else
						{
							p.sendMessage(Text.of(TextColors.RED, "You do not have permission to open " + args[1]));
						}
					}
					else
					{
						p.sendMessage(Text.of(TextColors.RED, "The gym " + args[1] + " does not exist!"));
						p.sendMessage(Text.of(TextColors.RED, "Try /gym open gym1"));
					}
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You do not have permission to open a gym!"));
				}

			}
			else if (args[0].equalsIgnoreCase("close"))
			{
				if ((p.hasPermission("pixelgym.leader")) || (p.hasPermission("pixelgym.admin")))
				{
					if (this.plugin.getConfig().getString("config." + args[1]) != null)
					{
						if ((p.hasPermission("pixelgym." + args[1])) || (p.hasPermission("pixelgym.admin")))
						{
							if ((args[1].equalsIgnoreCase("e41")) || (args[1].equalsIgnoreCase("e42")) || (args[1].equalsIgnoreCase("e43")) || (args[1].equalsIgnoreCase("e44")))
							{
								p.sendMessage(Text.of(TextColors.RED, "To close the Elite 4, type /e4 close e4#. Not /gym close e4#"));
							}
							else if (this.plugin.getConfig().getString("config." + args[1] + "enabled").equalsIgnoreCase("True"))
							{
								if (this.plugin.getConfig().getString("config." + args[1] + "stat").equals("Closed"))
								{
									p.sendMessage(Text.of(TextColors.RED, "The " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " gym is already Closed!"));
								}
								else if (this.plugin.getConfig().getString("config." + args[1] + "stat").equals("Open"))
								{
									MessageChannel.TO_ALL.send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString(new StringBuilder("config.").append(args[1]).append("colour").toString())).getChildren().get(0).getColor(), "The " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Gym is now ", TextColors.RED, "Closed"));

									getConfig().set("config." + args[1] + "stat", "Closed");
									p.sendMessage(Text.of(TextColors.GOLD, "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ "));
									ScoreboardManager.updateScoreboard();
								}
							}
							else
							{
								p.sendMessage(Text.of(TextColors.RED, "The " + args[1] + " gym is not enabled in the config, please close an enabled gym."));
							}

						}
						else
						{
							p.sendMessage(Text.of(TextColors.RED, "You do not have permission to close " + args[1]));
						}
					}
					else
					{
						p.sendMessage(Text.of(TextColors.RED, "The gym " + args[1] + " does not exist!"));
						p.sendMessage(Text.of(TextColors.RED, "Try /gym close gym1"));
					}

				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You do not have permission to close a gym!"));
				}
			}

			if (args[0].equalsIgnoreCase("setwarp"))
			{
				if (p.hasPermission("pixelgym.admin"))
				{
					if (this.plugin.settings.getData().get("warps." + args[1]) != null)
					{
						p.sendMessage(Text.of(TextColors.RED, args[1] + " warp already exists. If you want to overwrite it, do /gym delwarp " + args[1] + ". And then re-set the new warp."));
					}
					else
					{
						this.plugin.settings.getData().set("warps." + args[1] + ".world", p.getLocation().getExtent().getName());
						this.plugin.settings.getData().set("warps." + args[1] + ".x", Double.valueOf(p.getLocation().getX()));
						this.plugin.settings.getData().set("warps." + args[1] + ".y", Double.valueOf(p.getLocation().getY()));
						this.plugin.settings.getData().set("warps." + args[1] + ".z", Double.valueOf(p.getLocation().getZ()));
						this.plugin.settings.saveData();
						p.sendMessage(Text.of(TextColors.GREEN, "Set warp " + args[1] + "!"));
					}
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You do not have permission to set a warp!"));
				}
			}

			if (args[0].equalsIgnoreCase("delwarp"))
			{
				if (p.hasPermission("pixelgym.admin"))
				{
					if (this.plugin.settings.getData().get("warps." + args[1]) == null)
					{
						p.sendMessage(Text.of(TextColors.RED, "Warp " + args[1] + " does not exist!"));
						return;
					}
					this.plugin.settings.getData().set("warps." + args[1], null);
					this.plugin.settings.saveData();
					p.sendMessage(Text.of(TextColors.GREEN, "Removed warp " + args[1] + "!"));
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You do not have permission to delete a warp!"));
				}
			}

			if (args[0].equalsIgnoreCase("warp"))
			{
				if ((p.hasPermission("pixelgym.admin")) || (p.hasPermission("pixelgym." + args[1])))
				{
					if (this.plugin.settings.getData().get("warps." + args[1]) == null)
					{
						p.sendMessage(Text.of(TextColors.RED, "Warp " + args[1] + " does not exist!"));
						return;
					}

					World w = Sponge.getServer().getWorld(this.plugin.settings.getData().getString("warps." + args[1] + ".world")).orElse(null);
					double x = this.plugin.settings.getData().getDouble("warps." + args[1] + ".x");
					double y = this.plugin.settings.getData().getDouble("warps." + args[1] + ".y");
					double z = this.plugin.settings.getData().getDouble("warps." + args[1] + ".z");
					p.setLocation(new Location(w, x, y, z));

					p.sendMessage(Text.of(TextColors.GREEN, "Teleported to " + args[1] + "!"));
				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You do not have permission to warp to a gym!"));
				}
			}

			if (args[0].equalsIgnoreCase("addtm"))
			{
				String tM = p.getItemInHand(HandTypes.MAIN_HAND).get().getItem().getId();

				if (p.hasPermission("pixelgym.admin"))
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
						return;
					}

					if ((gym < 1) || (gym > 32))
					{
						return;
					}

					for (int i = 1; i <= 100; i++)
					{
						int u = i - 1;

						if (i == 1)
						{
							if (this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + i) == null)
							{
								this.plugin.settings.getExtras().set("tms.gym" + gym + "TM." + i, Integer.valueOf(tM));
								this.plugin.settings.saveExtras();
								// System.out.println("i = 1, set tM as i"));
								// System.out.println(tM);
								// System.out.println(this.plugin.settings.getExtras().getInt("tms.gym" + gym + "TM." + i));
								// System.out.println(this.plugin.settings.getExtras().getString("tms.gym" + gym + "TM." + i));
								// System.out.println(this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + i));
								break;
							}

						}
						else if ((this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + i) == null) && (this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + u) != null))
						{
							this.plugin.settings.getExtras().set("tms.gym" + gym + "TM." + i, Integer.valueOf(tM));
							this.plugin.settings.saveExtras();
							// System.out.println("i is idk, u exists, set tM as i");
							break;
						}
					}

					p.sendMessage(Text.of(TextColors.GREEN, "Added item in hand as a TM for gym"));
				}
			}
		}
		else if (args.length == 3)
		{
			if (args[0].equalsIgnoreCase("sendrules"))
			{
				if (Sponge.getServer().getPlayer(args[2]).orElse(null) != null)
				{
					Player playerTarget = Sponge.getServer().getPlayer(args[2]).get();

					String gymArg = args[1].replace("gym", "");
					int gym = 0;

					try
					{
						gym = Integer.parseInt(gymArg);
					}
					catch (NumberFormatException nfe)
					{
						p.sendMessage(Text.of(TextColors.RED, args[1] + " is not a gym!"));
						return;
					}

					if ((gym < 1) || (gym > 32))
					{
						return;
					}

					if (this.plugin.getConfig().getString("config." + args[1]) != null)
					{
						if (p.hasPermission("pixelgym." + args[1]))
						{
							p.sendMessage(Text.of(TextColors.GOLD, "Sent " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Gym Rules to " + playerTarget.getName().toString()));
							playerTarget.sendMessage(Text.of(TextColors.GOLD, playerTarget.getName().toString() + ", Make sure you read the " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Gym rules propperly before facing the Gym!"));
							playerTarget.sendMessage(Text.of(TextColors.GREEN, "1) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule1").toString())));
							playerTarget.sendMessage(Text.of(TextColors.GREEN, "2) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule2").toString())));
							playerTarget.sendMessage(Text.of(TextColors.GREEN, "3) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule3").toString())));
							playerTarget.sendMessage(Text.of(TextColors.GREEN, "4) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule4").toString())));
							playerTarget.sendMessage(Text.of(TextColors.GREEN, "5) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule5").toString())));
						}
						else
						{
							p.sendMessage(Text.of(TextColors.RED, "You are not gym leader of this gym!"));
						}
					}
					else
					{
						p.sendMessage(Text.of(TextColors.RED, "The gym " + args[1] + " does not exist!"));
						p.sendMessage(Text.of(TextColors.RED, "Try /gym sendrules gym1 (player)"));
					}

				}
				else
				{
					p.sendMessage(Text.of(TextColors.RED, "You need to specify a player to send the rules to. Example: /gym sendrules gym1 (player)"));
				}
			}

			if (args[0].equalsIgnoreCase("giveTM"))
			{
				if (Sponge.getServer().getPlayer(args[2]).orElse(null) != null)
				{
					Player playerToGive = Sponge.getServer().getPlayer(args[2]).get();
					String gymArg = args[1].replace("gym", "");
					int gym = 0;

					try
					{
						gym = Integer.parseInt(gymArg);
					}
					catch (NumberFormatException nfe)
					{
						p.sendMessage(Text.of(TextColors.RED, args[1] + " is not a gym!"));
						return;
					}

					if ((gym < 1) || (gym > 32))
					{
						return;
					}

					if ((p.hasPermission("pixelgym.gym" + gym)) || (p.hasPermission("pixelgym.admin")))
					{
						Random random = new Random();

						int randomNum = random.nextInt(4) + 1;
						if (this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + randomNum) != null)
						{
							ItemStack tM = ItemStack.builder()
								.itemType(Sponge.getRegistry().getType(ItemType.class, this.plugin.settings.getExtras().getString("tms.gym" + gym + "TM." + randomNum)).get())
								.build();

							if (playerToGive.getInventory().offer(tM).getType() != InventoryTransactionResult.Type.SUCCESS)
							{
								playerToGive.sendMessage(Text.of(TextColors.RED, "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!"));
								p.sendMessage(Text.of(TextColors.RED, "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym" + gym + " (player) when they have an open spot in there inventory"));
							}
						}
						else
						{
							int randomNum2 = random.nextInt(29) + 1;
							if (this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + randomNum2) != null)
							{
								ItemStack tM = ItemStack.builder()
									.itemType(Sponge.getRegistry().getType(ItemType.class, this.plugin.settings.getExtras().getString("tms.gym" + gym + "TM." + randomNum2)).get())
									.build();

								if (playerToGive.getInventory().offer(tM).getType() != InventoryTransactionResult.Type.SUCCESS)
								{
									playerToGive.sendMessage(Text.of(TextColors.RED, "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!"));
									p.sendMessage(Text.of(TextColors.RED, "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym" + gym + " (player) when they have an open spot in there inventory"));
								}
							}
							else
							{
								int randomNum3 = random.nextInt(9) + 1;
								if (this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + randomNum3) != null)
								{

									ItemStack tM = ItemStack.builder()
										.itemType(Sponge.getRegistry().getType(ItemType.class, this.plugin.settings.getExtras().getString("tms.gym" + gym + "TM." + randomNum3)).get())
										.build();

									if (playerToGive.getInventory().offer(tM).getType() != InventoryTransactionResult.Type.SUCCESS)
									{
										playerToGive.sendMessage(Text.of(TextColors.RED, "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!"));
										p.sendMessage(Text.of(TextColors.RED, "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym" + gym + " (player) when they have an open spot in there inventory"));
									}
								}
								else
								{
									int randomNum4 = random.nextInt(1) + 1;
									if (this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + randomNum4) != null)
									{
										ItemStack tM = ItemStack.builder()
											.itemType(Sponge.getRegistry().getType(ItemType.class, this.plugin.settings.getExtras().getString("tms.gym" + gym + "TM." + randomNum4)).get())
											.build();

										if (playerToGive.getInventory().offer(tM).getType() != InventoryTransactionResult.Type.SUCCESS)
										{
											playerToGive.sendMessage(Text.of(TextColors.RED, "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!"));
											p.sendMessage(Text.of(TextColors.RED, "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym" + gym + " (player) when they have an open spot in there inventory"));
										}
									}
									else
									{
										int randomNum5 = random.nextInt(0) + 1;
										if (this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + randomNum5) != null)
										{
											ItemStack tM = ItemStack.builder()
												.itemType(Sponge.getRegistry()
													.getType(ItemType.class, this.plugin.settings.getExtras().getString("tms.gym" + gym + "TM." + randomNum5)).get())
												.build();

											if (playerToGive.getInventory().offer(tM).getType() != InventoryTransactionResult.Type.SUCCESS)
											{
												playerToGive.sendMessage(Text.of(TextColors.RED, "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!"));
												p.sendMessage(Text.of(TextColors.RED, "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym" + gym + " (player) when they have an open spot in there inventory"));
											}
										}
									}
								}
							}
						}
					}
				}
			}

			int u;

			if ((args[0].equalsIgnoreCase("winner")) || (args[0].equalsIgnoreCase("win")) || (args[0].equalsIgnoreCase("w")))
			{
				if (Sponge.getServer().getPlayer(args[2]).orElse(null) != null)
				{
					Player playerWinner = Sponge.getServer().getPlayer(args[2]).orElse(null);

					String gymArg = args[1].replace("gym", "");
					int gym = 0;
					try
					{
						gym = Integer.parseInt(gymArg);
					}
					catch (NumberFormatException nfe)
					{
						p.sendMessage(Text.of(TextColors.RED, args[1] + " is not a gym!"));
						return;
					}

					if ((gym < 1) || (gym > 32))
					{
						return;
					}

					u = gym + 1;

					Calendar c = Calendar.getInstance();

					if ((p.hasPermission("pixelgym." + args[1])) || (p.hasPermission("pixelgym.admin")))
					{
						Player o = playerWinner;
						UUID po = playerWinner.getUniqueId();

						if (((List) this.plugin.queues.get(Integer.valueOf(gym))).contains(o.getUniqueId()))
						{

							if (this.plugin.settings.getData().get("warps.spawn") != null)
							{
								World w = Sponge.getServer().getWorld(this.plugin.settings.getData().getString("warps.spawn.world")).orElse(null);
								double x = this.plugin.settings.getData().getDouble("warps.spawn.x");
								double y = this.plugin.settings.getData().getDouble("warps.spawn.y");
								double z = this.plugin.settings.getData().getDouble("warps.spawn.z");
								playerWinner.setLocation(new Location(w, x, y, z));
								playerWinner.sendMessage(Text.of(TextColors.GREEN, "Teleported out of ", TextColors.YELLOW, TextStyles.BOLD, "gym" + gym + "!"));
								playerWinner.sendMessage(Text.of(TextColors.GREEN, "Congrats, you won the gym" + gym + " badge! (", TextSerializers.FORMATTING_CODE.deserialize(new StringBuilder(String.valueOf(this.plugin.getConfig().getString(new StringBuilder("config.gym")
									.append(gym).append("colour").toString()))).toString()), TextStyles.BOLD, this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).toString()), TextColors.GREEN, ") " + "Now you can join the gym", u + " queue with /gym join gym" + u + ". (", TextSerializers.FORMATTING_CODE.deserialize(new StringBuilder(String.valueOf(this.plugin.getConfig().getString(new StringBuilder("config.gym")
										.append(u).append("colour").toString()))).toString()), TextStyles.BOLD, this.plugin.getConfig().getString(new StringBuilder("config.gym").append(u).toString()), TextColors.GREEN, ")"));

								((List) this.plugin.inArena.get(Integer.valueOf(gym))).remove(p.getUniqueId());
								Random random = new Random();
								int randomNum = random.nextInt(99) + 1;

								if (this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + randomNum) != null)
								{
									ItemStack tM = ItemStack.builder()
										.itemType(Sponge.getRegistry().getType(ItemType.class, this.plugin.settings.getExtras().getString("tms.gym" + gym + "TM." + randomNum)).get())
										.build();

									if (playerWinner.getInventory().offer(tM).getType() != InventoryTransactionResult.Type.SUCCESS)
									{
										playerWinner.sendMessage(Text.of(TextColors.RED, "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!"));
										p.sendMessage(Text.of(TextColors.RED, "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym" + gym + " (player) when they have an open spot in there inventory"));
									}
								}
								else
								{
									int randomNum2 = random.nextInt(29) + 1;
									if (this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + randomNum2) != null)
									{
										ItemStack tM = ItemStack.builder()
											.itemType(Sponge.getRegistry().getType(ItemType.class, this.plugin.settings.getExtras().getString("tms.gym" + gym + "TM." + randomNum2)).get())
											.build();

										if (playerWinner.getInventory().offer(tM).getType() != InventoryTransactionResult.Type.SUCCESS)
										{
											playerWinner.sendMessage(Text.of(TextColors.RED, "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!"));
											p.sendMessage(Text.of(TextColors.RED, "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym" + gym + " (player) when they have an open spot in there inventory"));
										}
									}
									else
									{
										int randomNum3 = random.nextInt(9) + 1;
										if (this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + randomNum3) != null)
										{
											ItemStack tM = ItemStack.builder()
												.itemType(Sponge.getRegistry().getType(ItemType.class, this.plugin.settings.getExtras().getString("tms.gym" + gym + "TM." + randomNum3)).get())
												.build();

											if (playerWinner.getInventory().offer(tM).getType() != InventoryTransactionResult.Type.SUCCESS)
											{
												playerWinner.sendMessage(Text.of(TextColors.RED, "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!"));
												p.sendMessage(Text.of(TextColors.RED, "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym" + gym + " (player) when they have an open spot in there inventory"));
											}
										}
										else
										{
											int randomNum4 = random.nextInt(1) + 1;
											if (this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + randomNum4) != null)
											{
												ItemStack tM = ItemStack.builder()
													.itemType(Sponge.getRegistry()
														.getType(ItemType.class, this.plugin.settings.getExtras().getString("tms.gym" + gym + "TM." + randomNum4)).get())
													.build();

												if (playerWinner.getInventory().offer(tM).getType() != InventoryTransactionResult.Type.SUCCESS)
												{
													playerWinner.sendMessage(Text.of(TextColors.RED, "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!"));
													p.sendMessage(Text.of(TextColors.RED, "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym" + gym + " (player) when they have an open spot in there inventory"));
												}
											}
											else
											{
												int randomNum5 = 1;

												if (this.plugin.settings.getExtras().get("tms.gym" + gym + "TM." + randomNum5) != null)
												{
													ItemStack tM = ItemStack.builder()
														.itemType(Sponge.getRegistry().getType(ItemType.class, this.plugin.settings.getExtras().getString("tms.gym" + gym + "TM." + randomNum5)).get())
														.build();

													if (playerWinner.getInventory().offer(tM).getType() != InventoryTransactionResult.Type.SUCCESS)
													{
														playerWinner.sendMessage(Text.of(TextColors.RED, "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!"));
														p.sendMessage(Text.of(TextColors.RED, "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym" + gym + " (player) when they have an open spot in there inventory"));
													}
												}
											}
										}
									}
								}

								p.sendMessage(Text.of(TextColors.GREEN, "Successfully telported " + playerWinner.getName() + " out of your gym!"));
								p.sendMessage(Text.of(TextColors.GREEN, "You are now ready for your next battle, type: /gym next gym" + gym));
							}
							else
							{
								p.sendMessage(Text.of(TextColors.RED, "Warp point 'spawn' does not exist. Type: /gym setwarp spawn. (this is the teleport location to move challengers out of the gym."));
							}

							((List) this.plugin.queues.get(Integer.valueOf(gym))).remove(po);

							this.plugin.settings.getLogs().set("Leaders." + p.getName() + "." + args[1] + ".[" + c.getTime() + "]", playerWinner.getName() + " - Won");
							this.plugin.settings.getLogs().set("Challengers." + playerWinner.getUniqueId() + "." + args[1] + ".Date/Time Won", "[" + c.getTime() + "]");
							this.plugin.settings.getLogs().set("Challengers." + playerWinner.getUniqueId() + "." + args[1] + ".Gym Leader", p.getName());

							this.plugin.settings.saveLogs();

							if (this.plugin.getConfig().getString("config.gymsound").equalsIgnoreCase("true"))
							{
								for (Player playersOnline : Sponge.getServer().getOnlinePlayers())
								{
									playersOnline.playSound(SoundTypes.ENTITY_FIREWORK_LARGE_BLAST, SoundCategories.AMBIENT, playersOnline.getLocation().getPosition(), 30.0, 1.0);
									playersOnline.playSound(SoundTypes.ENTITY_FIREWORK_LARGE_BLAST, SoundCategories.AMBIENT, playersOnline.getLocation().getPosition(), 30.0, 1.0);
								}
							}

							for (int i = 1; i <= 32; i++)
							{
								if (args[1].equalsIgnoreCase("gym" + i))
								{
									this.plugin.settings.getBadge().set("Players." + playerWinner.getUniqueId() + ".Badges." + args[1], "Won");
									this.plugin.settings.saveBadges();
								}
							}
							MessageChannel.TO_ALL.send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextColors.YELLOW, playerWinner.getName(), TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString(new StringBuilder("config.")
								.append(args[1])
								.append("colour").toString())), " has won the " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Gym Badge!"));
						}
						else
						{
							p.sendMessage(Text.of(TextColors.RED, "Player must be in the queue to win or lose!"));
						}
					}
					else
					{
						p.sendMessage(Text.of(TextColors.RED, "You do not have permission to set winner's of " + args[1]));
					}
				}
			}
			else if (args[0].equalsIgnoreCase("givebadge"))
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
					return;
				}

				if ((gym < 1) || (gym > 32))
				{
					return;
				}

				for (Player playerGive : Sponge.getServer().getOnlinePlayers())
				{
					if (playerGive.getName().equalsIgnoreCase(args[2]))
					{
						if (p.hasPermission("pixelgym.admin"))
						{
							for (int i = 1; i <= 32; i++)
							{
								if (args[1].equalsIgnoreCase("gym" + i))
								{
									this.plugin.settings.getBadge().set("Players." + playerGive.getUniqueId() + ".Badges." + args[1], "Won");
									this.plugin.settings.saveBadges();

									p.sendMessage(Text.of(TextColors.GREEN, "Gave " + playerGive.getName() + " the gym" + gym + " badge!"));
								}
							}
						}
						else
						{
							p.sendMessage(Text.of("You do not have permission to give gym badges!"));
						}
					}
				}
			}
			else if (args[0].equalsIgnoreCase("delbadge"))
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
					return;
				}

				if ((gym < 1) || (gym > 32))
				{
					return;
				}

				for (Player playerTake : Sponge.getServer().getOnlinePlayers())
				{
					if (playerTake.getName().equalsIgnoreCase(args[2]))
					{
						if (p.hasPermission("pixelgym.admin"))
						{
							if (this.plugin.settings.getBadge().get("Players." + playerTake.getUniqueId() + ".Badges." + args[1]) != null)
							{
								for (int i = 1; i <= 32; i++)
								{
									if (args[1].equalsIgnoreCase("gym" + i))
									{
										this.plugin.settings.getBadge().set("Players." + playerTake.getUniqueId() + ".Badges." + args[1], null);
										this.plugin.settings.saveBadges();

										p.sendMessage(Text.of(TextColors.GREEN, "Deleted the gym" + gym + " badge from " + playerTake.getName()));
									}

								}
							}
							else
							{
								p.sendMessage(Text.of(TextColors.RED, playerTake.getName() + " does not have the gym, " + args[1]));
							}
						}
						else
						{
							p.sendMessage(Text.of("You do not have permission to give gym badges!"));
						}

					}

				}

			}
			else if ((args[0].equalsIgnoreCase("sq")) || (args[0].equalsIgnoreCase("skipq")) || (args[0].equalsIgnoreCase("skip")))
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
					return;
				}

				if ((gym < 1) || (gym > 32))
				{
					return;
				}

				if (Sponge.getServer().getPlayer(args[2]).orElse(null) != null)
				{
					Player playerSkip = Sponge.getServer().getPlayer(args[2]).get();
					if ((p.hasPermission("pixelgym." + args[1])) || (p.hasPermission("pixelgym.admin")))
					{
						UUID po = playerSkip.getUniqueId();

						String gymArg3 = args[1].replace("gym", "");
						int gym3 = Integer.parseInt(gymArg3);

						((Map) this.plugin.cooldownTime.get(Integer.valueOf(gym3))).remove(po);
						((Map) this.plugin.cooldownTask.get(Integer.valueOf(gym3))).remove(po);
						((List) this.plugin.cooldownGym.get(Integer.valueOf(gym3))).remove(po);

						p.sendMessage(Text.of(TextColors.GREEN, "Removed " + playerSkip.getName() + "'s cooldown for gym" + gym3));
						playerSkip.sendMessage(Text.of(TextColors.GREEN, "Removed your cooldown for gym" + gym3));
					}

				}
			}
			else if ((args[0].equalsIgnoreCase("lost")) || (args[0].equalsIgnoreCase("l")) || (args[0].equalsIgnoreCase("lose")))
			{
				String gymArg = args[1].replace("gym", "");
				final int gym;

				try
				{
					gym = Integer.parseInt(gymArg);
				}
				catch (NumberFormatException nfe)
				{
					p.sendMessage(Text.of(TextColors.RED, args[1] + " is not a gym!"));
					return;
				}

				if ((gym < 1) || (gym > 32))
				{
					return;
				}

				if (Sponge.getServer().getPlayer(args[2]).orElse(null) != null)
				{
					Player playerLost = Sponge.getServer().getPlayer(args[2]).get();

					if ((p.hasPermission("pixelgym." + args[1])) || (p.hasPermission("pixelgym.admin")))
					{
						UUID po = playerLost.getUniqueId();
						int time = Integer.parseInt(this.plugin.getConfig().getString("config.cooldowntime"));

						if (((List) this.plugin.queues.get(Integer.valueOf(gym))).contains(po))
						{
							if (this.plugin.settings.getData().get("warps.spawn") != null)
							{
								World w = Sponge.getServer().getWorld(this.plugin.settings.getData().getString("warps.spawn.world")).orElse(null);
								double x = this.plugin.settings.getData().getDouble("warps.spawn.x");
								double y = this.plugin.settings.getData().getDouble("warps.spawn.y");
								double z = this.plugin.settings.getData().getDouble("warps.spawn.z");
								playerLost.setLocation(new Location(w, x, y, z));
								playerLost.sendMessage(Text.of(TextColors.GREEN, "Teleported out of ", TextColors.YELLOW, TextStyles.BOLD, "gym" + gym + "!"));
								playerLost.sendMessage(Text.of(TextColors.GREEN, "Unlucky! you lost that gym battle! (" + TextSerializers.FORMATTING_CODE.deserialize(new StringBuilder(String.valueOf(this.plugin.getConfig().getString(new StringBuilder("config.gym").append(gym).append("colour")
									.toString()))).append(TextStyles.BOLD).append(this.plugin.getConfig().getString(new StringBuilder("config.gym")
										.append(gym).toString())).append(TextColors.GREEN).append(") Not to worry! You can challenge the gym again in ")
										.append(time).append(" minutes! type: /gym join gym").append(gym).append(" When the cooldown has finished.").toString())));
								playerLost.sendMessage(Text.of(TextColors.BLUE, "To check how long you have left on your cooldown, type: /gym join gym" + gym));

								p.sendMessage(Text.of(TextColors.GREEN, "Successfully telported " + playerLost.getName() + " out of your gym!"));
								p.sendMessage(Text.of(TextColors.GREEN, "You are now ready for your next battle, type: /gym next gym" + gym));
							}
							else
							{
								p.sendMessage(Text.of(TextColors.RED, "Warp point 'spawn' does not exist. Type: /gym setwarp spawn. (this is the teleport location to move challengers out of the gym."));
							}

							String gymArg3 = args[1].replace("gym", "");
							int gym3 = Integer.parseInt(gymArg3);

							((List) this.plugin.queues.get(Integer.valueOf(gym))).remove(po);
							((List) this.plugin.inArena.get(Integer.valueOf(gym))).remove(po);

							this.plugin.settings.getLogs().set("Leaders." + p.getName() + "." + args[1] + ".[" + this.plugin.format.format(this.plugin.now) + "]", playerLost.getName() + " - Lost");
							this.plugin.settings.saveLogs();

							((Map) this.plugin.cooldownTime.get(Integer.valueOf(gym3))).put(po, Integer.valueOf(time));

							((List) this.plugin.cooldownGym.get(Integer.valueOf(gym))).add(po);

							this.plugin.cooldownTask.get(Integer.valueOf(gym)).put(po, Sponge.getScheduler().createTaskBuilder().execute(() -> {
								if (this.plugin.cooldownGym.get(gym3).contains(po))
								{
									this.plugin.cooldownTime.get(gym3).put(po, this.plugin.cooldownTime.get(gym3).get(po) - 1);

									if (this.plugin.cooldownTime.get(gym3).get(po) == 0)
									{
										this.plugin.cooldownTime.get(gym3).remove(po);
										this.plugin.cooldownTask.get(gym3).remove(po);
										this.plugin.cooldownGym.get(gym3).remove(po);

										Sponge.getScheduler().getTasksByName(String.valueOf(gym)).forEach(t -> t.cancel());
									}
								}
								else
								{
									Sponge.getScheduler().getTasksByName(String.valueOf(gym)).forEach(t -> t.cancel());
								}
							}).name(String.valueOf(gym)));
							((Task.Builder) ((Map) this.plugin.cooldownTask.get(Integer.valueOf(gym))).get(po)).delayTicks(20L).intervalTicks(1200L).submit(PixelGym.getInstance());
						}
						else
						{
							p.sendMessage(Text.of(TextColors.RED, "Player must be in the queue to win or lose!"));
						}
					}
					else
					{
						p.sendMessage(Text.of(TextColors.RED, "You do not have permission to set losers of " + args[1]));
					}

				}
			}
		}
	}

	private void handleE4(Player p, String[] args)
	{
		if (args.length == 0)
		{
			if (!p.hasPermission("pixelgym.e4leader"))
			{
				p.sendMessage(Text.of(TextColors.GOLD, "----- PixelmonGyms -----"));
				p.sendMessage(Text.of(""));
				p.sendMessage(Text.of(TextColors.GREEN, "/e4 list", TextColors.DARK_GREEN, " - Get a list of the E4 Level's and there status."));
				p.sendMessage(Text.of(TextColors.GREEN, "/e4 leaders", TextColors.DARK_GREEN, " - Get a list of the online E4 leaders."));
				p.sendMessage(Text.of(TextColors.GREEN, "/e4 rules <e4#>", TextColors.DARK_GREEN, " - Shows all the rules for the specified E4 Level."));
				p.sendMessage(Text.of(TextColors.GREEN, "/gym scoreboard", TextColors.DARK_GREEN, " - Toggle ScoreBoard for e4 and Gym."));
				p.sendMessage(Text.of(""));
				p.sendMessage(Text.of(TextColors.RED, "Plugin Made By ", TextColors.GOLD, "ABkayCkay"));
			}
			else if (p.hasPermission("pixelgym.e4leader"))
			{
				p.sendMessage(Text.of(TextColors.GOLD, "----- PixelmonGyms -----"));
				p.sendMessage(Text.of(""));
				p.sendMessage(Text.of(TextColors.GREEN, "/e4 list", TextColors.DARK_GREEN, " - Get a list of the E4 level's and there status."));
				p.sendMessage(Text.of(TextColors.GREEN, "/e4 leaders", TextColors.DARK_GREEN, " - Get a list of the online E4 leaders."));
				p.sendMessage(Text.of(TextColors.GREEN, "/e4 rules <e4#>", TextColors.DARK_GREEN, " - Shows all the rules for the specified E4 Level."));
				p.sendMessage(Text.of(TextColors.GREEN, "/e4 open <e4#>", TextColors.DARK_GREEN, " - Open a particular E4 level (e41, e42, e43 or e44)."));
				p.sendMessage(Text.of(TextColors.GREEN, "/e4 close <e4#>", TextColors.DARK_GREEN, " - Close a particular E4 level (e41, e42, e43 or e44)."));
				p.sendMessage(Text.of(TextColors.GREEN, "/e4 heal", TextColors.DARK_GREEN, " - Heals your pokemon."));

				p.sendMessage(Text.of(TextColors.GREEN, "/e4 sendrules <e4#> (username)", TextColors.DARK_GREEN, " - Force quit a e4 battle. In (Username) put either yours or the challengers IGN!"));
				p.sendMessage(Text.of(""));
				p.sendMessage(Text.of(TextColors.RED, "Plugin Made By ", TextColors.GOLD, "ABkayCkay"));
			}
			else if (p.hasPermission("pixelgym.admin"))
			{
				p.sendMessage(Text.of(TextColors.GOLD, "----- PixelmonGyms -----"));
				p.sendMessage(Text.of(""));
				p.sendMessage(Text.of(TextColors.GREEN, "/e4 list", TextColors.DARK_GREEN, " - Get a list of the E4 Level's and there status."));
				p.sendMessage(Text.of(TextColors.GREEN, "/e4 leaders", TextColors.DARK_GREEN, " - Get a list of the online E4 leaders."));
				p.sendMessage(Text.of(TextColors.GREEN, "/e4 rules <e4#>", TextColors.DARK_GREEN, " - Shows all the rules for the specified E4 Level."));
				p.sendMessage(Text.of(TextColors.GREEN, "/e4 closeall", TextColors.DARK_GREEN, " - Closes all Elite 4 Level's."));
				p.sendMessage(Text.of(""));
				p.sendMessage(Text.of(TextColors.RED, "Plugin Made By ", TextColors.GOLD, "ABkayCkay"));
			}

		}
		else if (args.length == 1)
		{
			if (args[0].equals("leaders"))
			{
				p.sendMessage(Text.of(TextColors.GOLD, "----- Online E4 Leaders -----"));
				p.sendMessage(Text.of(""));
				for (Player e4staff : Sponge.getServer().getOnlinePlayers())
				{
					if (e4staff.hasPermission("pixelgym.e41"))
					{
						if (this.plugin.enablee4.equalsIgnoreCase("true"))
						{
							p.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e41colour")) + e4staff.getName() + " - " + getConfig().getString("config.e41")));
						}
					}
					if (e4staff.hasPermission("pixelgym.e42"))
					{
						if (this.plugin.enablee4.equalsIgnoreCase("true"))
						{
							p.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e42colour")) + e4staff.getName() + " - " + getConfig().getString("config.e42")));
						}
					}
					if (e4staff.hasPermission("pixelgym.e43"))
					{
						if (this.plugin.enablee4.equalsIgnoreCase("true"))
						{
							p.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e43colour")) + e4staff.getName() + " - " + getConfig().getString("config.e43")));
						}
					}
					if (e4staff.hasPermission("pixelgym.e44"))
					{
						if (this.plugin.enablee4.equalsIgnoreCase("true"))
						{
							p.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e44colour")) + e4staff.getName() + " - " + getConfig().getString("config.e44")));
						}

					}

				}

			}
			else if (args[0].equals("list"))
			{
				p.sendMessage(Text.of(TextColors.GOLD, "----- PixelmonGyms -----"));
				p.sendMessage(Text.of(""));
				if (this.plugin.enablee4.equalsIgnoreCase("true"))
				{
					p.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e41colour")) + getConfig().getString("config.e41") + "  Elite 4 is: ", TextColors.BLUE, getConfig().getString("config.e41stat")));
					p.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e42colour")) + getConfig().getString("config.e42") + "  Elite 4 is: ", TextColors.BLUE, getConfig().getString("config.e42stat")));
					p.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e43colour")) + getConfig().getString("config.e43") + "  Elite 4 is: ", TextColors.BLUE, getConfig().getString("config.e43stat")));
					p.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e44colour")) + getConfig().getString("config.e44") + "  Elite 4 is: ", TextColors.BLUE, getConfig().getString("config.e41stat")));
				}

			}
			else if ((args[0].equals("open")) && (p.hasPermission("pixelgym.e4leader")))
			{
				p.sendMessage(Text.of(TextColors.DARK_RED, "Proper Usage: ", TextColors.RED, "/e4 open <e4#>"));
			}
			else if ((args[0].equals("close")) && (p.hasPermission("pixelgym.e4leader")))
			{
				p.sendMessage(Text.of(TextColors.DARK_RED, "Proper Usage: ", TextColors.RED, "/e4 close <e4#>"));
			}
			else if (args[0].equals("rules"))
			{
				p.sendMessage(Text.of(TextColors.DARK_RED, "Proper Usage: ", TextColors.RED, "/e4 rules <e4#>"));
			}
			else if ((args[0].equals("heal")) && (p.hasPermission("pixelgym.e4leader")) && (this.plugin.enableGymHeal.equalsIgnoreCase("true")))
			{
				Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokeheal " + p.getName().toString());
				p.sendMessage(Text.of("Your pixelmon have been healed!"));
			}
			else if ((args[0].equals("heal")) && (p.hasPermission("pixelgym.e4leader")) && (!this.plugin.enableGymHeal.equalsIgnoreCase("true")))
			{
				p.sendMessage(Text.of(TextColors.RED, "Gym/E4 Leader healing disabled in the plugin config"));
			}
			else if ((args[0].equals("quit")) && (p.hasPermission("pixelgym.e4leader")))
			{
				for (Player playerTarget : Sponge.getServer().getOnlinePlayers())
				{
					Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "endbattle " + playerTarget.getName().toString());
					p.sendMessage(Text.of(TextColors.GREEN, "You have successfully quit the battle!"));
				}

			}
			else if ((args[0].equalsIgnoreCase("closeall")) && (p.hasPermission("pixelgym.admin")))
			{
				MessageChannel.TO_ALL.send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextColors.YELLOW, "All " + getConfig().getString("config.e4") + " Level's are now closed."));

				getConfig().set("config.e41stat", "Closed");
				getConfig().set("config.e42stat", "Closed");
				getConfig().set("config.e43stat", "Closed");
				getConfig().set("config.e44stat", "Closed");

				ScoreboardManager.updateScoreboard();
			}
		}
		else if (args.length == 2)
		{
			if ((args[0].equals("rules")) && (args[1].equals("e41")))
			{
				if (this.plugin.enablee4.equalsIgnoreCase("true"))
				{
					p.sendMessage(Text.of(TextColors.GOLD, "----- PixelmonGyms -----"));
					p.sendMessage(Text.of(""));
					p.sendMessage(Text.of(TextColors.GREEN, "1) " + getConfig().getString("config.e41rule1")));
					p.sendMessage(Text.of(TextColors.GREEN, "2) " + getConfig().getString("config.e41rule2")));
					p.sendMessage(Text.of(TextColors.GREEN, "3) " + getConfig().getString("config.e41rule3")));
					p.sendMessage(Text.of(TextColors.GREEN, "4) " + getConfig().getString("config.e41rule4")));
					p.sendMessage(Text.of(TextColors.GREEN, "5) " + getConfig().getString("config.e41rule5")));
				}
			}
			else if ((args[0].equals("rules")) && (args[1].equals("e42")))
			{
				if (this.plugin.enablee4.equalsIgnoreCase("true"))
				{
					p.sendMessage(Text.of(TextColors.GOLD, "----- PixelmonGyms -----"));
					p.sendMessage(Text.of(""));
					p.sendMessage(Text.of(TextColors.GREEN, "1) " + getConfig().getString("config.e42rule1")));
					p.sendMessage(Text.of(TextColors.GREEN, "2) " + getConfig().getString("config.e42rule2")));
					p.sendMessage(Text.of(TextColors.GREEN, "3) " + getConfig().getString("config.e42rule3")));
					p.sendMessage(Text.of(TextColors.GREEN, "4) " + getConfig().getString("config.e42rule4")));
					p.sendMessage(Text.of(TextColors.GREEN, "5) " + getConfig().getString("config.e42rule5")));
				}
			}
			else if ((args[0].equals("rules")) && (args[1].equals("e43")))
			{
				if (this.plugin.enablee4.equalsIgnoreCase("true"))
				{
					p.sendMessage(Text.of(TextColors.GOLD, "----- PixelmonGyms -----"));
					p.sendMessage(Text.of(""));
					p.sendMessage(Text.of(TextColors.GREEN, "1) " + getConfig().getString("config.e43rule1")));
					p.sendMessage(Text.of(TextColors.GREEN, "2) " + getConfig().getString("config.e43rule2")));
					p.sendMessage(Text.of(TextColors.GREEN, "3) " + getConfig().getString("config.e43rule3")));
					p.sendMessage(Text.of(TextColors.GREEN, "4) " + getConfig().getString("config.e43rule4")));
					p.sendMessage(Text.of(TextColors.GREEN, "5) " + getConfig().getString("config.e43rule5")));
				}
			}
			else if ((args[0].equals("rules")) && (args[1].equals("e44")))
			{
				if (this.plugin.enablee4.equalsIgnoreCase("true"))
				{
					p.sendMessage(Text.of(TextColors.GOLD, "----- PixelmonGyms -----"));
					p.sendMessage(Text.of(""));
					p.sendMessage(Text.of(TextColors.GREEN, "1) " + getConfig().getString("config.e44rule1")));
					p.sendMessage(Text.of(TextColors.GREEN, "2) " + getConfig().getString("config.e44rule2")));
					p.sendMessage(Text.of(TextColors.GREEN, "3) " + getConfig().getString("config.e44rule3")));
					p.sendMessage(Text.of(TextColors.GREEN, "4) " + getConfig().getString("config.e44rule4")));
					p.sendMessage(Text.of(TextColors.GREEN, "5) " + getConfig().getString("config.e44rule5")));
				}
			}

			if (p.hasPermission("pixelgym.e4leader"))
			{
				if ((args[0].equalsIgnoreCase("open")) && (args[1].equalsIgnoreCase("e41")) && (p.hasPermission("pixelgym.e41")) && (this.plugin.enablee4.equalsIgnoreCase("true")))
				{
					MessageChannel.TO_ALL.send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e41colour")).getChildren().get(0).getColor(), "The " + getConfig().getString("config.e41") + " " + getConfig().getString("config.e4") + " is now ", TextColors.GREEN, "Open"));
					getConfig().set("config.e41stat", "Open");
					p.sendMessage(Text.of(TextColors.GOLD, "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ "));

					if (this.plugin.getConfig().getString("config.scoreboard").equals("True"))
					{
						ScoreboardManager.updateScoreboard();
					}
				}

				if ((args[0].equalsIgnoreCase("open")) && (args[1].equalsIgnoreCase("e42")) && (p.hasPermission("pixelgym.e42")) && (this.plugin.enablee4.equalsIgnoreCase("true")))
				{
					MessageChannel.TO_ALL.send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e42colour")).getChildren().get(0).getColor(), "The " + getConfig().getString("config.e42") + " " + getConfig().getString("config.e4") + " is now ", TextColors.GREEN, "Open"));
					getConfig().set("config.e42stat", "Open");
					p.sendMessage(Text.of(TextColors.GOLD, "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ "));

					if (this.plugin.getConfig().getString("config.scoreboard").equals("True"))
					{
						ScoreboardManager.updateScoreboard();
					}
				}

				if ((args[0].equalsIgnoreCase("open")) && (args[1].equalsIgnoreCase("e43")) && (p.hasPermission("pixelgym.e43")) && (this.plugin.enablee4.equalsIgnoreCase("true")))
				{
					MessageChannel.TO_ALL.send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e43colour")).getChildren().get(0).getColor(), "The " + getConfig().getString("config.e43") + " " + getConfig().getString("config.e4") + " is now ", TextColors.GREEN, "Open"));
					getConfig().set("config.e43stat", "Open");
					p.sendMessage(Text.of(TextColors.GOLD, "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ "));

					if (this.plugin.getConfig().getString("config.scoreboard").equals("True"))
					{
						ScoreboardManager.updateScoreboard();
					}
				}

				if ((args[0].equalsIgnoreCase("open")) && (args[1].equalsIgnoreCase("e44")) && (p.hasPermission("pixelgym.e44")) && (this.plugin.enablee4.equalsIgnoreCase("true")))
				{
					MessageChannel.TO_ALL.send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e44colour")).getChildren().get(0).getColor(), "The " + getConfig().getString("config.e44") + " " + getConfig().getString("config.e4") + " is now ", TextColors.GREEN, "Open"));
					getConfig().set("config.e44stat", "Open");
					p.sendMessage(Text.of(TextColors.GOLD, "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ "));
					if (this.plugin.getConfig().getString("config.scoreboard").equals("True"))
					{
						ScoreboardManager.updateScoreboard();
					}
				}
				else if ((args[0].equalsIgnoreCase("close")) && (args[1].equalsIgnoreCase("e41")) && (p.hasPermission("pixelgym.e41")) && (this.plugin.enablee4.equalsIgnoreCase("true")))
				{
					MessageChannel.TO_ALL.send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e41colour")).getChildren().get(0).getColor(), "The " + getConfig().getString("config.e41") + " " + getConfig().getString("config.e4") + " is now ", TextColors.RED, "Closed"));
					getConfig().set("config.e41stat", "Closed");
					p.sendMessage(Text.of(TextColors.GOLD, "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ "));
					ScoreboardManager.updateScoreboard();
				}
				else if ((args[0].equalsIgnoreCase("close")) && (args[1].equalsIgnoreCase("e42")) && (p.hasPermission("pixelgym.e42")) && (this.plugin.enablee4.equalsIgnoreCase("true")))
				{
					MessageChannel.TO_ALL.send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e42colour")).getChildren().get(0).getColor(), "The " + getConfig().getString("config.e42") + " " + getConfig().getString("config.e4") + " is now ", TextColors.RED, "Closed"));
					getConfig().set("config.e42stat", "Closed");
					p.sendMessage(Text.of(TextColors.GOLD, "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ "));
					ScoreboardManager.updateScoreboard();
				}
				else if ((args[0].equalsIgnoreCase("close")) && (args[1].equalsIgnoreCase("e43")) && (p.hasPermission("pixelgym.e43")) && (this.plugin.enablee4.equalsIgnoreCase("true")))
				{
					MessageChannel.TO_ALL.send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e43colour")).getChildren().get(0).getColor(), "The " + getConfig().getString("config.e43") + " " + getConfig().getString("config.e4") + " is now ", TextColors.RED, "Closed"));
					getConfig().set("config.e43stat", "Closed");
					p.sendMessage(Text.of(TextColors.GOLD, "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ "));
					ScoreboardManager.updateScoreboard();
				}
				else if ((args[0].equalsIgnoreCase("close")) && (args[1].equalsIgnoreCase("e44")) && (p.hasPermission("pixelgym.e44")) && (this.plugin.enablee4.equalsIgnoreCase("true")))
				{
					MessageChannel.TO_ALL.send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, getConfig().getString("config.title"), TextColors.DARK_GRAY, "] ", TextSerializers.FORMATTING_CODE.deserialize(getConfig().getString("config.e44colour")).getChildren().get(0).getColor(), "The " + getConfig().getString("config.e44") + " " + getConfig().getString("config.e4") + " is now ", TextColors.RED, "Closed"));
					getConfig().set("config.e44stat", "Closed");
					p.sendMessage(Text.of(TextColors.GOLD, "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ "));
					ScoreboardManager.updateScoreboard();
				}
			}

		}
		else if (args.length == 3)
		{
			for (Player playerTarget : Sponge.getServer().getOnlinePlayers())
			{
				if ((args[0].equalsIgnoreCase("sendrules")) && (playerTarget.getName().equalsIgnoreCase(args[2])) && (p.hasPermission("pixelgym." + args[1])))
				{
					p.sendMessage(Text.of(TextColors.GOLD, "Sent " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Elite 4 Rules to " + playerTarget.getName().toString()));
					playerTarget.sendMessage(Text.of(TextColors.GOLD, playerTarget.getName().toString() + ", Make sure you read the " + getConfig().getString(new StringBuilder("config.").append(args[1]).toString()) + " Elite 4 rules propperly before facing the Gym!"));
					playerTarget.sendMessage(Text.of(TextColors.GREEN, "1) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule1").toString())));
					playerTarget.sendMessage(Text.of(TextColors.GREEN, "2) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule2").toString())));
					playerTarget.sendMessage(Text.of(TextColors.GREEN, "3) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule3").toString())));
					playerTarget.sendMessage(Text.of(TextColors.GREEN, "4) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule4").toString())));
					playerTarget.sendMessage(Text.of(TextColors.GREEN, "5) " + getConfig().getString(new StringBuilder("config.").append(args[1]).append("rule5").toString())));
				}
			}
		}
	}

	private PluginConfiguration getConfig()
	{
		return this.plugin.getConfig();
	}
}
