package Me.Runescapejon.FakeJoinLeave;

import java.util.Optional;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;

public class FakeLeave implements CommandExecutor {
	public CommandResult execute(CommandSource src, CommandContext args) {
		Optional<String> target = args.<String>getOne("target");
		if (!target.isPresent()) {
			Player player = (Player) src;
			MessageChannel.TO_ALL.send(Text.of(TextColors.YELLOW, player.getName(), " left the game."));
		} else if (src.hasPermission("fakeleave.message.other") && target.isPresent()) {
			String targ = target.get();
			MessageChannel.TO_ALL.send(Text.of(TextColors.YELLOW, targ, " left the game."));
		}
		return CommandResult.success();
	}

}
