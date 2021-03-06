package rina.turok.bope.bopemod.hacks.combat;

import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.Packet;
import net.minecraft.item.ItemBow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

// Guiscreen.
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;

// Modules.
import rina.turok.bope.bopemod.hacks.BopeCategory;

// Data.
import rina.turok.bope.bopemod.BopeMessage;
import rina.turok.bope.bopemod.BopeModule;

// Core.
import rina.turok.bope.Bope;

/**
* @author Rina
*
* Created by Rina.
* 11/05/20.
*
*/
public class BopeFastUtil extends BopeModule {
	BopeSetting place   = create("Place", "BopeFastPlace", true);
	BopeSetting break_  = create("Break", "BopeFastBreak", true);
	BopeSetting use     = create("Use", "BopeFastUse", true);
	BopeSetting crystal = create("Crystal", "BopeFastCrystal", true);
	BopeSetting exp     = create("EXP", "BopeFastExp", true);
	BopeSetting bow     = create("Bow", "BopeFastBow", false);

	public BopeFastUtil() {
		super(BopeCategory.BOPE_COMBAT);

		// Info.
		this.name        = "Fast Util"; 
		this.tag         = "FastUtil";
		this.description = "Fast util for events and things in Minecraft.";

		// Release the module.
		release("B.O.P.E - Module - B.O.P.E");
	}

	@Override
	public void update() {
		// Get the main actual items.
		Item main = mc.player.getHeldItemMainhand().getItem();
		Item off  = mc.player.getHeldItemOffhand().getItem();

		// Insteaof with the actual item if.
		boolean main_exp = main instanceof ItemExpBottle;
		boolean off_exp  = off instanceof ItemExpBottle;
		boolean main_cry = main instanceof ItemEndCrystal;
		boolean off_cry  = off instanceof ItemEndCrystal;

		if (main_exp | off_exp && exp.get_value(true)) {
			mc.rightClickDelayTimer = 0;
		}

		if (main_cry | off_cry && crystal.get_value(true)) {
			mc.rightClickDelayTimer = 0;
		}

		if (!(main_exp | off_exp | main_cry | off_cry) && place.get_value(true)) {
			mc.rightClickDelayTimer = 0;
		}

		if (break_.get_value(true)) {
			mc.playerController.blockHitDelay = 0;
		}

		if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && bow.get_value(true)) {
			if (mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
				mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
				mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
				mc.player.stopActiveHand();
			}
		}
	}
}