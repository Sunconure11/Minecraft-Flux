package szewek.mcflux.special;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import szewek.mcflux.MCFluxResources;
import szewek.mcflux.U;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandSpecialGive extends CommandBase {
	@Override public String getName() {
		return "sgive";
	}

	@Override public String getUsage(ICommandSender sender) {
		return "mcflux.cmd.sgive.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 2)
			throw new WrongUsageException("mcflux.cmd.sgive.usage");
		else {
			EntityPlayer player = getEntity(server, sender, args[0], EntityPlayer.class);
			long l = parseLong(args[1]);
			SpecialEvent se = SpecialEventHandler.getEvent(l);
			if (se == null)
				throw new CommandException("mcflux.cmd.sgive.noEvent", l);
			ItemStack is = new ItemStack(MCFluxResources.SPECIAL);
			is.setTagInfo("seid", new NBTTagLong(l));
			U.giveItemToPlayer(is, player);
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
	}
}
