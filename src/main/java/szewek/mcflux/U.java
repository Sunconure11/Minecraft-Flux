package szewek.mcflux;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import szewek.mcflux.api.CapabilityEnergy;
import szewek.mcflux.api.IEnergyConsumer;
import szewek.mcflux.api.IEnergyHolder;
import szewek.mcflux.api.IEnergyProducer;

public class U {
	@SideOnly(Side.CLIENT)
	private static ItemModelMesher imm = null;

	public static String formatMF(int n, int nc) {
		return n + " / " + nc + " MF";
	}

	public static IEnergyHolder getEnergyHolderTile(TileEntity te, EnumFacing f) {
		if (te.hasCapability(CapabilityEnergy.ENERGY_CONSUMER, f)) {
			return te.getCapability(CapabilityEnergy.ENERGY_CONSUMER, f);
		} else if (te.hasCapability(CapabilityEnergy.ENERGY_PRODUCER, f)) {
			return te.getCapability(CapabilityEnergy.ENERGY_PRODUCER, f);
		}
		return null;
	}

	public static IEnergyHolder getEnergyHolderEntity(Entity e) {
		if (e.hasCapability(CapabilityEnergy.ENERGY_CONSUMER, null)) {
			return e.getCapability(CapabilityEnergy.ENERGY_CONSUMER, null);
		} else if (e.hasCapability(CapabilityEnergy.ENERGY_PRODUCER, null)) {
			return e.getCapability(CapabilityEnergy.ENERGY_PRODUCER, null);
		}
		return null;
	}
	
	public static int transferEnergy(IEnergyProducer from, IEnergyConsumer to, final int amount) {
		int r = to.consumeEnergy(from.extractEnergy(amount, true), true);
		if (r > 0)
			return to.consumeEnergy(from.extractEnergy(r, false), false);
		return 0;
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemModels(Item... items) {
		if (imm == null)
			imm = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for (Item i : items) {
			ModelResourceLocation mrl = new ModelResourceLocation(i.getRegistryName(), "inventory");
			imm.register(i, 0, mrl);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemMultiModels(Item item, int m) {
		ResourceLocation rl = item.getRegistryName();
		final String dom = rl.getResourceDomain();
		for (int i = 0; i < m; i++) {
			ItemStack is = new ItemStack(item, 1, i);
			ModelResourceLocation mrl = new ModelResourceLocation(dom + ':' + item.getUnlocalizedName(is).substring(5), "inventory");
			net.minecraftforge.client.model.ModelLoader.setCustomModelResourceLocation(item, i, mrl);
		}
	}

	private U() {
	}
}