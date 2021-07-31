package hy.shadyaddons.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Utils {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean inSkyBlock = false;
    public static boolean inDungeon = false;

    /**
     * Remove Minecraft chat formatting from a message
     * @param input The string to clean
     * @return The cleaned string
     */
    public static String removeFormatting(String input) {
        return input.replaceAll("§[0-9a-fk-or]", "");
    }

    /**
     * Get SkyBlock ID
     * @param item The item to check
     * @return The item's ID, or null if it doesn't have one
     */
    public static String getSkyBlockID(ItemStack item) {
        if(item != null) {
            NBTTagCompound extraAttributes = item.getSubCompound("ExtraAttributes", false);
            if(extraAttributes != null && extraAttributes.hasKey("id")) {
                return extraAttributes.getString("id");
            }
        }
        return null;
    }

    /**
     * Check if player is looking at block using raytracing
     * @param block The block position to check
     * @return If the player is looking at the provided block
     */
    public static boolean facingBlock(BlockPos block, int range) {
        float stepSize = 0.15f;
        if(mc.thePlayer != null && mc.theWorld != null) {
            Vector3f position = new Vector3f((float) mc.thePlayer.posX, (float) mc.thePlayer.posY+mc.thePlayer.getEyeHeight(), (float) mc.thePlayer.posZ);

            Vec3 look = mc.thePlayer.getLook(0);

            Vector3f step = new Vector3f((float) look.xCoord, (float) look.yCoord, (float) look.zCoord);
            step.scale(stepSize/step.length());

            for(int i = 0; i < Math.floor(range/stepSize)-2; i++) {
                BlockPos blockAtPos = new BlockPos(position.x, position.y, position.z);
                if(blockAtPos.equals(block)) return true;
                position.translate(step.x, step.y, step.z);
            }
        }
        return false;
    }

    /**
     * Get inventory name
     * @return The inventory name, or "null" (as a string) if null
     */
    public static String getInventoryName() {
        String inventoryName = mc.thePlayer.openContainer.inventorySlots.get(0).inventory.getName();
        if(inventoryName == null) return "null";
        return inventoryName;
    }

    /**
     * Send chat message
     * @param message The text to be sent, can include § or & as formatting codes
     */
    public static void sendMessage(String message) {
        if(mc.thePlayer != null && mc.theWorld != null) {
            if(!message.contains("§")) {
                message = message.replace("&", "§");
            }
            mc.thePlayer.addChatMessage(new ChatComponentText(message));
        } else {
            System.out.println("Unable to send chat message, player is null: "+message);
        }
    }

    /**
     * Send mod message
     * @param message The message to be sent with the mod's prefix
     */
    public static void sendModMessage(String message) {
        if(message.contains("§")) {
            sendMessage("§3ShadyAddons > §f" + message);
        } else {
            sendMessage("&3ShadyAddons > &f" + message);
        }
    }

    /**
     * Add alpha (transparency) to a color
     * @param color The color to modify
     * @param alpha The alpha amount to set, as a percentage
     * @return The original color with the alpha channel
     */
    public static Color addAlpha(Color color, float alpha) {
        int alphaInt = Math.round(alpha * 256);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alphaInt);
    }

    /**
     * Check if block is interactable
     * @param block The block to check
     * @return Whether or not the block can be interacted with
     */
    public static boolean isInteractable(Block block) {
        return new ArrayList<Block>(Arrays.asList(
                Blocks.acacia_door,
                Blocks.anvil,
                Blocks.beacon,
                Blocks.bed,
                Blocks.birch_door,
                Blocks.brewing_stand,
                Blocks.command_block,
                Blocks.crafting_table,
                Blocks.chest,
                Blocks.dark_oak_door,
                Blocks.daylight_detector,
                Blocks.daylight_detector_inverted,
                Blocks.dispenser,
                Blocks.dropper,
                Blocks.enchanting_table,
                Blocks.ender_chest,
                Blocks.furnace,
                Blocks.hopper,
                Blocks.jungle_door,
                Blocks.lever,
                Blocks.noteblock,
                Blocks.powered_comparator,
                Blocks.unpowered_comparator,
                Blocks.powered_repeater,
                Blocks.unpowered_repeater,
                Blocks.standing_sign,
                Blocks.wall_sign,
                Blocks.trapdoor,
                Blocks.trapped_chest,
                Blocks.wooden_button,
                Blocks.stone_button,
                Blocks.oak_door,
                Blocks.skull)).contains(block);
    }

    /**
     * Sets the inSkyBlock variable based on the scoreboard title
     */
    private int ticks = 0;
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(ticks % 20 == 0) {
            if(mc.thePlayer != null && mc.theWorld != null) {
                ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
                if(scoreboardObj != null) {
                    inSkyBlock = removeFormatting(scoreboardObj.getDisplayName()).contains("SKYBLOCK");
                }

                inDungeon = inSkyBlock && ScoreboardUtils.scoreboardContains("The Catacombs");
            }
            ticks = 0;
        }
        ticks++;
    }

}
