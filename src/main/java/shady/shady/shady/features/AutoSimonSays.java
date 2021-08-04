package shady.shady.shady.features;

import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.Shady;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.ReflectionUtils;
import shady.shady.shady.utils.Utils;

public class AutoSimonSays {
    
    private boolean clicking = false;

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if(Config.isEnabled(Setting.AUTO_SIMON_SAYS) && !clicking && Utils.inDungeon && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            if(Shady.mc.theWorld.getBlockState(event.pos).getBlock() == Blocks.stone_button) {
                int x = event.pos.getX();
                int y = event.pos.getY();
                int z = event.pos.getZ();

                if(x == 309 && y >= 120 && y <= 123 && z >= 291 && z <= 294) {
                    clicking = true;
                    for(int i = 0; i < 4; i++) {
                        // DEV: ReflectionUtils.invoke(Shady.mc.getClass(), "rightClickMouse");
                        ReflectionUtils.invoke(Shady.mc.getClass(), "func_147121_ag");
                    }
                    clicking = false;
                }
            }
        }
    }

}
