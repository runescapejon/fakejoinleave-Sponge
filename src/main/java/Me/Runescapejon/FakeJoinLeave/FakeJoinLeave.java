package Me.Runescapejon.FakeJoinLeave;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

@Plugin(id = "fakejoinleave", name = "fakejoinleave", description = "you can use this plugin to fake join or leave to display its message make it appear your not online or not", version = "1.0")
public class FakeJoinLeave {

	@Listener
	public void onGameInitlization(GameInitializationEvent event) {
		CommandSpec FakeJoinSpc = CommandSpec.builder().description(Text.of("Display fake join mesage"))
				.permission("fakejoin.message").executor(new FakeJoin())
				.arguments(GenericArguments.firstParsing(GenericArguments.flags()
						.buildWith(GenericArguments
								.firstParsing(GenericArguments.optional(GenericArguments.string(Text.of("target")))))))
				.build();
		Sponge.getCommandManager().register(this, FakeJoinSpc, "fakejoin");

		CommandSpec fakeleave = CommandSpec.builder().description(Text.of("Display fake leave mesage"))
				.permission("fakeleave.message")
				.arguments(GenericArguments.firstParsing(GenericArguments.flags()
						.buildWith(GenericArguments
								.firstParsing(GenericArguments.optional(GenericArguments.string(Text.of("target")))))))
				.executor(new FakeLeave()).build();
		Sponge.getCommandManager().register(this, fakeleave, "fakeleave", "fakeleft");
	}

}