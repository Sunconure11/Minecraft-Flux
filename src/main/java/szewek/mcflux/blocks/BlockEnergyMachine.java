package szewek.mcflux.blocks;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import szewek.mcflux.U;
import szewek.mcflux.tileentities.TileEntityEnergyMachine;

import java.util.ArrayList;
import java.util.List;

public final class BlockEnergyMachine extends BlockMCFluxContainer {
	private static final AxisAlignedBB DEF_AABB = new AxisAlignedBB(0.25, 0.25, 0.25, 0.75, 0.75, 0.75);
	public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

	public BlockEnergyMachine() {
		super();
		setHardness(0.5F);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(this, 1, state.getValue(VARIANT).ordinal()));
		return list;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(VARIANT).ordinal();
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		TileEntityEnergyMachine teem = new TileEntityEnergyMachine();
		teem.setModuleId(m);
		return teem;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
		for (int i = 0; i < Variant.ALL_VARIANTS.length; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, Variant.ALL_VARIANTS[meta % Variant.ALL_VARIANTS.length]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).ordinal();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return DEF_AABB;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World w, BlockPos bp, IBlockState ibs, EntityPlayer p, EnumHand h, EnumFacing f, float x, float y, float z) {
		boolean b = h == EnumHand.MAIN_HAND && U.isItemEmpty(p.getHeldItem(h));
		if (b && !w.isRemote) {
			TileEntity te = w.getTileEntity(bp);
			if (te != null && te instanceof TileEntityEnergyMachine)
				((TileEntityEnergyMachine) te).switchSideTransfer(f);
		}
		return b;
	}

	public enum Variant implements IStringSerializable {
		ENERGY_DIST("energy_dist"), CHUNK_CHARGER("chunk_charger"), FLAVOR_DIST("flavor_dist"), CHUNK_SPRAYER("chunk_sprayer");

		public static final Variant[] ALL_VARIANTS;
		public final String name;

		Variant(String n) {
			name = n;
		}

		@Override
		public String getName() {
			return name;
		}

		public static String nameFromStack(ItemStack is) {
			return ALL_VARIANTS[is.getMetadata() % ALL_VARIANTS.length].name;
		}

		static {
			ALL_VARIANTS = Variant.values();
		}
	}
}
