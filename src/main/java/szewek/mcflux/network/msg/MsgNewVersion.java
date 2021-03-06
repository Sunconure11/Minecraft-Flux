package szewek.mcflux.network.msg;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import szewek.mcflux.L;

import java.io.IOException;

public final class MsgNewVersion extends FragileMsg {
	private String version = null;

	public static MsgNewVersion with(String v) {
		MsgNewVersion mnv = new MsgNewVersion();
		mnv.version = v;
		mnv.broken = false;
		return mnv;
	}

	@Override public void processMsg(PacketBuffer pb, EntityPlayer p, Side s) throws IOException {
		if (pb.readableBytes() < 2) {
			L.warn("Incompatible length");
		}
		version = pb.readString(32);
		broken = false;
		if (s == Side.CLIENT)
			p.sendMessage(new TextComponentTranslation("mcflux.update.newversion", version));
	}

	@Override public void saveBuffer(PacketBuffer pb) throws IOException {
		if (broken)
			return;
		pb.writeString(version);
	}
}
