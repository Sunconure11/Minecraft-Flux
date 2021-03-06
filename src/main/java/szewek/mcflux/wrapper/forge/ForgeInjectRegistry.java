package szewek.mcflux.wrapper.forge;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import szewek.mcflux.U;
import szewek.mcflux.tileentities.TileEntityFluxGen;
import szewek.mcflux.util.MCFluxReport;
import szewek.mcflux.util.IInjectRegistry;
import szewek.mcflux.util.InjectCond;
import szewek.mcflux.util.InjectRegistry;
import szewek.mcflux.util.ErrMsg;
import szewek.mcflux.wrapper.*;

@InjectRegistry(requires = InjectCond.CLASS, args = "net.minecraftforge.energy.IEnergyStorage")
public final class ForgeInjectRegistry implements IInjectRegistry {
	@Override public void registerInjects() {
		InjectCollector ic = InjectWrappers.getCollector();
		if (ic == null)
			return;
		ic.addTileWrapperInject(ForgeInjectRegistry::wrapGlobal);
		ic.addEntityWrapperInject(ForgeInjectRegistry::wrapGlobal);
		ic.addItemWrapperInject(ForgeInjectRegistry::wrapGlobal);
	}

	private static <T extends ICapabilityProvider> void wrapGlobal(T icp, WrapperRegistry reg) {
		if (icp instanceof TileEntityFluxGen)
			return;
		EnumFacing f = null;
		try {
			for (int i = 0; i < U.FANCY_FACING.length; i++) {
				f = U.FANCY_FACING[i];
				if (icp.hasCapability(CapabilityEnergy.ENERGY, f)) {
					reg.add(EnergyType.FORGE_ENERGY, new EnergyCapabilityProvider(icp, ForgeEnergySided::new, null));
					return;
				}
			}
		} catch (Exception e) {
			MCFluxReport.addErrMsg(new ErrMsg.BadImplementation("Forge Energy", icp.getClass(), e, f));
		}
	}
}
