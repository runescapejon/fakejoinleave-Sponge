package Me.Runescapejon.FakeJoinLeave;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class Language {
	@Setting(value = "YouLeftTheServer", comment = "You can edit FakeLeave message. Placeholder for player name is %player%")
	public static String YouLeftTheServer = "&e%player% left the game.";

	@Setting(value = "OtherNameLeftTheServer", comment = "Placeholder for argument other playername is %other%")
	public static String OtherNameLeftTheServer = "&e%other% left the game.";

	@Setting(value = "YouJoinedTheServer")
	public static String YouJoinedTheServer = "&e%player% joined the game.";

	@Setting(value = "OtherNameJoinedTheServer")
	public static String OtherNameJoinedTheServer = "&e%other% joined the game.";
}
