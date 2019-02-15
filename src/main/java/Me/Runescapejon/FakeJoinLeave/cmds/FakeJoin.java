package Me.Runescapejon.FakeJoinLeave.cmds;

import java.util.Optional;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

import Me.Runescapejon.FakeJoinLeave.Language;

public class FakeJoin implements CommandExecutor {
	public CommandResult execute(CommandSource src, CommandContext args) {
		Optional<String> target = args.<String>getOne("target");
		if (!target.isPresent()) {
			Player player = (Player) src;
			MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(Language.YouJoinedTheServer.replace("%player%", player.getName())));
		} else if (src.hasPermission("fakejoin.message.other") && target.isPresent()) {
			String targ = target.get();
			MessageChannel.TO_ALL.send(TextSerializers.FORMATTING_CODE.deserialize(Language.OtherNameJoinedTheServer.replace("%other%", targ)));
		}
		return CommandResult.success();
	}

}
