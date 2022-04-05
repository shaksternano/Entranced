package io.github.shaksternano.entranced.commonside.gui;

import io.github.shaksternano.entranced.commonside.Entranced;
import net.minecraft.util.Identifier;

public enum EnchantingCatalystPanel {

    INSTANCE(
            new Identifier(Entranced.MOD_ID, "textures/gui/screen/enchanting_catalyst_panel.png"),
            35,
            56,
            -30,
            47,
            9,
            8,
            7,
            29
    );

    /**
     * The {@link Identifier} pointing towards the GUI texture.
     */
    private final Identifier GUI_TEXTURE;
    /**
     * The width of the GUI texture.
     */
    private final int GUI_TEXTURE_WIDTH;
    /**
     * The height of the GUI texture.
     */
    private final int GUI_TEXTURE_HEIGHT;
    /**
     * The X co-ordinate of the top left corner of the slot.
     */
    private final int SLOT_X;
    /**
     * The Y co-ordinate of the top left corner of the slot.
     */
    private final int SLOT_Y;
    /**
     * The X co-ordinate of the top left corner of the slot texture relative to the GUI texture.
     */
    private final int SLOT_TEXTURE_RELATIVE_X;
    /**
     * The Y co-ordinate of the top left corner of the slot texture relative to the GUI texture.
     */
    private final int SLOT_TEXTURE_RELATIVE_Y;
    /**
     * The X co-ordinate of the top left corner of the button relative to the GUI texture.
     */
    private final int BUTTON_RELATIVE_X;
    /**
     * The Y co-ordinate of the top left corner of the button relative to the GUI texture.
     */
    private final int BUTTON_RELATIVE_Y;

    /**
     * Creates a new {@link EnchantingCatalystPanel} instance.
     * @param guiTexture The {@link Identifier} pointing towards the GUI texture.
     * @param guiTextureWidth The width of the GUI texture.
     * @param guiTextureHeight The height of the GUI texture.
     * @param slotX The X co-ordinate of the top left corner of the slot.
     * @param slotY The Y co-ordinate of the top left corner of the slot.
     * @param slotTextureRelativeX The X co-ordinate of the top left corner of the slot texture relative to the GUI texture.
     * @param slotTextureRelativeY The Y co-ordinate of the top left corner of the slot texture relative to the GUI texture.
     * @param buttonRelativeX The X co-ordinate of the top left corner of the button relative to the GUI texture.
     * @param buttonRelativeY The Y co-ordinate of the top left corner of the button relative to the GUI texture.
     */
    EnchantingCatalystPanel(Identifier guiTexture, int guiTextureWidth, int guiTextureHeight, int slotX, int slotY, int slotTextureRelativeX, int slotTextureRelativeY, int buttonRelativeX, int buttonRelativeY) {
        GUI_TEXTURE = guiTexture;
        GUI_TEXTURE_WIDTH = guiTextureWidth;
        GUI_TEXTURE_HEIGHT = guiTextureHeight;
        SLOT_X = slotX;
        SLOT_Y = slotY;
        SLOT_TEXTURE_RELATIVE_X = slotTextureRelativeX;
        SLOT_TEXTURE_RELATIVE_Y = slotTextureRelativeY;
        BUTTON_RELATIVE_X = buttonRelativeX;
        BUTTON_RELATIVE_Y = buttonRelativeY;
    }

    /**
     * Gets the {@link Identifier} pointing towards the GUI texture.
     * @return The {@link Identifier} pointing towards the GUI texture.
     */
    public Identifier getGuiTexture() {
        return GUI_TEXTURE;
    }

    /**
     * Gets the width of the GUI texture.
     * @return The width of the GUI texture.
     */
    public int getGuiTextureWidth() {
        return GUI_TEXTURE_WIDTH;
    }

    /**
     * Gets the height of the GUI texture.
     * @return The height of the GUI texture.
     */
    public int getGuiTextureHeight() {
        return GUI_TEXTURE_HEIGHT;
    }

    /**
     * Gets the X co-ordinate of the top left corner of the GUI texture.
     * @param screenX The X co-ordinate of the top left corner of the screen.
     * @return The X co-ordinate of the top left corner of the GUI texture.
     */
    public int getTextureX(int screenX) {
        return screenX + SLOT_X - SLOT_TEXTURE_RELATIVE_X;
    }

    /**
     * Gets the Y co-ordinate of the top left corner of the GUI texture.
     * @param screenY The Y co-ordinate of the top left corner of the screen.
     * @return The Y co-ordinate of the top left corner of the GUI texture.
     */
    public int getTextureY(int screenY) {
        return screenY + SLOT_Y - SLOT_TEXTURE_RELATIVE_Y;
    }

    /**
     * Gets the X co-ordinate of the top left corner of the slot.
     * @return The X co-ordinate of the top left corner of the slot.
     */
    public int getSlotX() {
        return SLOT_X;
    }

    /**
     * Gets the Y co-ordinate of the top left corner of the slot.
     * @return The Y co-ordinate of the top left corner of the slot.
     */
    public int getSlotY() {
        return SLOT_Y;
    }

    /**
     * Gets the X co-ordinate of the top left corner of the button.
     * @param screenX The X co-ordinate of the top left corner of the screen.
     * @return The X co-ordinate of the top left corner of the button.
     */
    public int getButtonX(int screenX) {
        return getTextureX(screenX) + BUTTON_RELATIVE_X;
    }

    /**
     * Gets the Y co-ordinate of the top left corner of the button.
     * @param screenY The Y co-ordinate of the top left corner of the screen.
     * @return The Y co-ordinate of the top left corner of the button.
     */
    public int getButtonY(int screenY) {
        return getTextureY(screenY) + BUTTON_RELATIVE_Y;
    }
}
