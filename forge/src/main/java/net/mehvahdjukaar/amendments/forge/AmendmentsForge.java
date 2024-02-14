package net.mehvahdjukaar.amendments.forge;

import net.mehvahdjukaar.amendments.Amendments;
import net.mehvahdjukaar.amendments.events.ModEvents;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.quark.base.handler.ToolInteractionHandler;

import static net.mehvahdjukaar.amendments.Amendments.MOD_ID;

/**
 * Author: MehVahdJukaar
 */
@Mod(MOD_ID)
public class AmendmentsForge {

    public AmendmentsForge() {
        Amendments.init();
        MinecraftForge.EVENT_BUS.register(this);
        if (PlatHelper.getPhysicalSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(ClientEvents.class);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onUseBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!event.isCanceled()) {
            var ret = ModEvents.onRightClickBlock(event.getEntity(), event.getLevel(), event.getHand(), event.getHitVec());
            if (ret != InteractionResult.PASS) {
                event.setCanceled(true);
                event.setCancellationResult(ret);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onUseBlockHP(PlayerInteractEvent.RightClickBlock event) {
        if (!event.isCanceled()) {
            var ret = ModEvents.onRightClickBlockHP(event.getEntity(), event.getLevel(), event.getHand(), event.getHitVec());
            if (ret != InteractionResult.PASS) {
                event.setCanceled(true);
                event.setCancellationResult(ret);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onUseItem(PlayerInteractEvent.RightClickItem event) {
        if (!event.isCanceled()) {
            var ret = ModEvents.onUseItem(event.getEntity(), event.getLevel(), event.getHand());
            if (ret.getResult() != InteractionResult.PASS) {
                event.setCanceled(true);
                event.setCancellationResult(ret.getResult());
            }
        }
    }

    @SubscribeEvent
    public void onTagUpdate(TagsUpdatedEvent event) {
        Amendments.onCommonTagUpdate(event.getRegistryAccess(),
                event.getUpdateCause() == TagsUpdatedEvent.UpdateCause.CLIENT_PACKET_RECEIVED);
    }
}
