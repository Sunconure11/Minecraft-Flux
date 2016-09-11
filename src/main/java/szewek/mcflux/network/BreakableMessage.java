package szewek.mcflux.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

abstract class BreakableMessage implements IMessage {
	protected boolean broken = true;

	boolean isBroken() {
		return broken;
	}
}