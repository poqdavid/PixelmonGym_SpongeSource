package me.Ckay.gym.scoreboard;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import com.google.common.collect.Lists;

import me.Ckay.gym.PixelGym;
import me.Ckay.gym.config.PluginConfiguration;

public class ScoreboardManager
{
	public static Scoreboard board, clear;
	public static Objective none, obj;
	public static Score gym1, gym2, gym3, gym4, gym5, gym6, gym7, gym8, gym9, gym10, gym11, gym12, gym13, gym14, gym15, gym16, gym17, gym18, gym19, gym20, gym21, gym22, gym23, gym24, gym25, gym26, gym27, gym28, gym29, gym30, gym31, gym32, e41, e42, e43, e44, leaders;

	private PixelGym plugin;

	public ScoreboardManager(PixelGym plugin)
	{
		this.plugin = plugin;

		this.init();
	}

	private void init()
	{
		none = Objective.builder()
			.name("test")
			.displayName(Text.of("test"))
			.criterion(Criteria.DUMMY)
			.build();

		obj = Objective.builder()
			.name("test")
			.displayName(Text.of(TextColors.GREEN, "Open Gyms"))
			.criterion(Criteria.DUMMY)
			.build();

		gym1 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym1colour"))
			.getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym1")));

		gym2 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym2colour"))
			.getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym2") + " "));

		gym3 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym3colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym3")));
		gym4 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym4colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym4")));
		gym5 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym5colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym5")));
		gym6 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym6colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym6") + " "));
		gym7 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym7colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym7")));
		gym8 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym8colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym8")));
		gym9 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym9colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym9")));
		gym10 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym10colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym10")));
		gym11 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym11colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym11")));
		gym12 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym12colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym12")));
		gym13 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym13colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym13")));
		gym14 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym14colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym14")));
		gym15 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym15colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym15")));
		gym16 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym16colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym16")));
		gym17 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym17colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym17")));
		gym18 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym18colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym18")));
		gym19 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym19colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym19")));
		gym20 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym20colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym20")));
		gym21 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym21colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym21")));
		gym22 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym22colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym22")));
		gym23 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym23colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym23")));
		gym24 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym24colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym24")));
		gym25 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym25colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym25")));
		gym26 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym26colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym26")));
		gym27 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym27colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym27")));
		gym28 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym28colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym28")));
		gym29 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym29colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym29")));
		gym30 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym30colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym30")));
		gym31 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym31colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym31")));
		gym32 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.gym32colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.gym32")));

		e41 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.e41colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.e41") + " " + plugin.getConfig().getString("config.e4ab")));
		e42 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.e42colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.e42") + " " + plugin.getConfig().getString("config.e4ab")));
		e43 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.e43colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.e43") + " " + plugin.getConfig().getString("config.e4ab")));
		e44 = obj.getOrCreateScore(Text.of(TextSerializers.FORMATTING_CODE.deserialize(plugin.getConfig().getString("config.e44colour")).getChildren().get(0).getColor(), plugin.getConfig().getString("config.e44") + " " + plugin.getConfig().getString("config.e4ab")));

		board = Scoreboard.builder()
			.objectives(Lists.newArrayList(obj))
			.build();

		board.updateDisplaySlot(obj, DisplaySlots.SIDEBAR);

		clear = Scoreboard.builder()
			.objectives(Lists.newArrayList(none))
			.build();

		clear.updateDisplaySlot(none, DisplaySlots.SIDEBAR);
	}

	public static void removeScores(Score score)
	{
		obj.removeScore(score.getName());
	}

	public static void addAndUpdateScore(Score score, int val)
	{
		if (!obj.hasScore(score.getName()))
		{
			// System.out.println(score.getName().getChildren().size());
			Score sc = obj.getOrCreateScore(score.getName());

			sc.setScore(val);
		}
		else
		{
			score.setScore(val);
		}
	}

	public static void updateScoreboard()
	{
		for (Player online : Sponge.getServer().getOnlinePlayers())
		{
			online.setScoreboard(ScoreboardManager.board);
		}

		if ((getConfig().getString("config.e41stat").equals("Open")) && (PixelGym.getInstance().enablee4.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.e41, 101);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.e41);
		}

		if ((getConfig().getString("config.e42stat").equals("Open")) && (PixelGym.getInstance().enablee4.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.e42, 102);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.e42);
		}

		if ((getConfig().getString("config.e43stat").equals("Open")) && (PixelGym.getInstance().enablee4.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.e43, 103);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.e43);
		}

		if ((getConfig().getString("config.e44stat").equals("Open")) && (PixelGym.getInstance().enablee4.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.e44, 104);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.e44);
		}

		if ((getConfig().getString("config.gym1stat").equals("Open")) && (PixelGym.getInstance().enablegym1.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym1, 1);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym1);
		}

		if ((getConfig().getString("config.gym2stat").equals("Open")) && (PixelGym.getInstance().enable2.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym2, 2);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym2);
		}

		if ((getConfig().getString("config.gym3stat").equals("Open")) && (PixelGym.getInstance().enable3.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym3, 3);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym3);
		}

		if ((getConfig().getString("config.gym4stat").equals("Open")) && (PixelGym.getInstance().enable4.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym4, 4);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym4);
		}

		if ((getConfig().getString("config.gym5stat").equals("Open")) && (PixelGym.getInstance().enable5.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym5, 5);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym5);
		}

		if ((getConfig().getString("config.gym6stat").equals("Open")) && (PixelGym.getInstance().enable6.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym6, 6);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym6);
		}

		if ((getConfig().getString("config.gym7stat").equals("Open")) && (PixelGym.getInstance().enable7.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym7, 7);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym7);
		}

		if ((getConfig().getString("config.gym8stat").equals("Open")) && (PixelGym.getInstance().enable8.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym8, 8);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym8);
		}

		if ((getConfig().getString("config.gym9stat").equals("Open")) && (PixelGym.getInstance().enable9.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym9, 9);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym9);
		}

		if ((getConfig().getString("config.gym10stat").equals("Open")) && (PixelGym.getInstance().enablegym10.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym10, 10);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym10);
		}

		if ((getConfig().getString("config.gym11stat").equals("Open")) && (PixelGym.getInstance().enablegym11.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym11, 11);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym11);
		}

		if ((getConfig().getString("config.gym12stat").equals("Open")) && (PixelGym.getInstance().enablegym12.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym12, 12);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym12);
		}

		if ((getConfig().getString("config.gym13stat").equals("Open")) && (PixelGym.getInstance().enablegym13.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym13, 13);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym13);
		}

		if ((getConfig().getString("config.gym14stat").equals("Open")) && (PixelGym.getInstance().enablegym14.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym14, 14);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym14);
		}

		if ((getConfig().getString("config.gym15stat").equals("Open")) && (PixelGym.getInstance().enablegym15.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym15, 15);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym15);
		}

		if ((getConfig().getString("config.gym16stat").equals("Open")) && (PixelGym.getInstance().enablegym16.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym16, 16);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym16);
		}

		if ((getConfig().getString("config.gym17stat").equals("Open")) && (PixelGym.getInstance().enablegym17.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym17, 17);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym17);
		}

		if ((getConfig().getString("config.gym18stat").equals("Open")) && (PixelGym.getInstance().enablegym18.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym18, 18);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym18);
		}

		if ((getConfig().getString("config.gym19stat").equals("Open")) && (PixelGym.getInstance().enablegym19.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym19, 19);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym19);
		}

		if ((getConfig().getString("config.gym20stat").equals("Open")) && (PixelGym.getInstance().enable20.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym20, 20);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym20);
		}

		if ((getConfig().getString("config.gym21stat").equals("Open")) && (PixelGym.getInstance().enable21.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym21, 21);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym21);
		}

		if ((getConfig().getString("config.gym22stat").equals("Open")) && (PixelGym.getInstance().enable22.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym22, 22);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym22);
		}

		if ((getConfig().getString("config.gym23stat").equals("Open")) && (PixelGym.getInstance().enable23.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym23, 23);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym23);
		}

		if ((getConfig().getString("config.gym24stat").equals("Open")) && (PixelGym.getInstance().enable24.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym24, 24);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym24);
		}

		if ((getConfig().getString("config.gym25stat").equals("Open")) && (PixelGym.getInstance().enable25.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym25, 25);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym25);
		}

		if ((getConfig().getString("config.gym26stat").equals("Open")) && (PixelGym.getInstance().enable26.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym26, 26);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym26);
		}

		if ((getConfig().getString("config.gym27stat").equals("Open")) && (PixelGym.getInstance().enable27.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym27, 27);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym27);
		}

		if ((getConfig().getString("config.gym28stat").equals("Open")) && (PixelGym.getInstance().enable28.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym28, 28);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym28);
		}

		if ((getConfig().getString("config.gym29stat").equals("Open")) && (PixelGym.getInstance().enable29.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym29, 29);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym29);
		}

		if ((getConfig().getString("config.gym30stat").equals("Open")) && (PixelGym.getInstance().enable30.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym30, 30);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym30);
		}

		if ((getConfig().getString("config.gym31stat").equals("Open")) && (PixelGym.getInstance().enable31.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym31, 31);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym31);
		}

		if ((getConfig().getString("config.gym32stat").equals("Open")) && (PixelGym.getInstance().enable32.equalsIgnoreCase("true")))
		{
			ScoreboardManager.addAndUpdateScore(ScoreboardManager.gym32, 32);
		}
		else
		{
			ScoreboardManager.removeScores(ScoreboardManager.gym32);
		}
	}

	public static PluginConfiguration getConfig()
	{
		return PixelGym.getInstance().getConfig();
	}
}
