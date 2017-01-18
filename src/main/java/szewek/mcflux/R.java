package szewek.mcflux;

public final class R {
	public static final String
		MF_VERSION = "@VERSION@",
		MF_NAME = "mcflux",
		MF_FULL_NAME = "Minecraft-Flux",
		MF_DEPENDENCIES = "",
		MF_API = "mcfluxAPI",
		MF_API_EX = MF_API + "|ex",
		MF_API_FE = MF_API + "|fe",
		MF_API_ASSISTANT = MF_API + "|assistant",
		MF_PKG = "szewek.mcflux",
		MF_UPDATE = "https://raw.githubusercontent.com/Szewek/Minecraft-Flux/master/update.json",
		MF_REPORT_URL = "@REPORT_URL@",
		GUI_FACTORY = MF_PKG + ".client.MCFluxGuiFactory",
		PROXY_SERVER = MF_PKG + ".proxy.ProxyCommon",
		PROXY_CLIENT = MF_PKG + ".proxy.ProxyClient",
		WAILA_REGISTER = MF_PKG + ".compat.waila.MCFluxWailaProvider.callbackRegister";

	private R() {}
}
