package Me.Runescapejon.FakeJoinLeave;

import java.io.File;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.google.common.reflect.TypeToken;

import Me.Runescapejon.FakeJoinLeave.cmds.FakeJoin;
import Me.Runescapejon.FakeJoinLeave.cmds.FakeLeave;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;

@Plugin(id = "fakejoinleave", name = "fakejoinleave", description = "you can use this plugin to fake join or leave to display its message make it appear your not online or not", version = "1.1")
public class FakeJoinLeave {
	public static FakeJoinLeave instance;
	private FakeJoinLeave plugin;
	private Logger logger;
	private Language configmsg;
	GuiceObjectMapperFactory factory;
	private final File configDirectory;

	@Inject
	public FakeJoinLeave(Logger logger, Game game, @ConfigDir(sharedRoot = false) File configDir,
			GuiceObjectMapperFactory factory) {
		this.logger = logger;
		this.configDirectory = configDir;
		this.factory = factory;
		instance = this;
	}

	public Logger getLogger() {
		return logger;
	}

	public GuiceObjectMapperFactory getFactory() {
		return factory;
	}

	public File getConfigDirectory() {
		return configDirectory;
	}

	public Language getLangCfg() {
		return configmsg;
	}

	@Listener
	public void onGameInitlization(GameInitializationEvent event) {
		plugin = this;
		loadConfig();
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
	   @Listener
	    public void onReload(GameReloadEvent event) {
		   loadConfig();
	   }
	public boolean loadConfig() {
		if (!plugin.getConfigDirectory().exists()) {
			plugin.getConfigDirectory().mkdirs();
		}
		try {
			File configFile = new File(getConfigDirectory(), "messages.conf");
			if (!configFile.exists()) {
				configFile.createNewFile();
				logger.info("Creating Config for fakejoinleave");
			}
			ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder()
					.setFile(configFile).build();
			CommentedConfigurationNode config = loader.load(ConfigurationOptions.defaults()
					.setObjectMapperFactory(plugin.getFactory()).setShouldCopyDefaults(true));
			configmsg = config.getValue(TypeToken.of(Language.class), new Language());
			loader.save(config);
			return true;
		} catch (Exception error) {
			getLogger().error("coudnt make the config", error);
			return false;
		}
	}

}