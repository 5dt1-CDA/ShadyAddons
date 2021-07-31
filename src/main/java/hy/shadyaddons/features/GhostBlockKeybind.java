package hy.shadyaddons.features;

import hy.shadyaddons.config.Config;
import hy.shadyaddons.utils.KeybindUtils;
import hy.shadyaddons.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class GhostBlockKeybind {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public GhostBlockKeybind() {
        KeybindUtils.register("Create Ghost Block", Keyboard.KEY_G);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(Config.isEnabled(Config.Setting.GHOST_BLOCK_KEYBIND) && KeybindUtils.get("Create Ghost Block").isPressed()) {
            BlockPos lookingAtPos = mc.objectMouseOver.getBlockPos();
            if(lookingAtPos != null) {
                Block lookingAtblock = mc.theWorld.getBlockState(lookingAtPos).getBlock();
                if(!Utils.isInteractable(lookingAtblock)) {
                    mc.theWorld.setBlockToAir(lookingAtPos);
                }
            }
        }
    }

}
