package ooo.foooooooooooo.velocitydiscord.discord.commands;

import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import ooo.foooooooooooo.velocitydiscord.VelocityDiscord;

public class WhitelistCommand implements ICommand {
  public static final String COMMAND_NAME = "whitelist";

  public WhitelistCommand() {}

  @Override
  public void handle(SlashCommandInteraction interaction) {
    // Grab the argument "username"
    OptionMapping usernameOption = interaction.getOption("username");
    if (usernameOption == null) {
      interaction.reply("❌ You must provide a Minecraft username.").setEphemeral(true).queue();
      return;
    }

    String username = usernameOption.getAsString();

    // Run the whitelist add command on the Velocity console
    VelocityDiscord.SERVER.getCommandManager().executeAsync(
        VelocityDiscord.SERVER.getConsoleCommandSource(),
        "whitelist add " + username
    ).thenRun(() -> {
      interaction.reply("✅ Added **" + username + "** to the whitelist.").queue();
    }).exceptionally(ex -> {
      interaction.reply("❌ Failed to whitelist **" + username + "**: " + ex.getMessage()).queue();
      return null;
    });
  }

  @Override
  public String description() {
    return "Whitelist a Minecraft username on the server";
  }
}
