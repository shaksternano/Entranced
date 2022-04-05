package io.github.shaksternano.entranced.client.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class EnchantingCatalystButton extends ButtonWidget {

    private static final int ITEM_WIDTH = 16;
    private static final int ITEM_HEIGHT = 16;

    private static final  int ITEM_PADDING = 2;

    private final Screen screen;
    private final ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
    @Nullable
    private final ItemStack icon;
    @Nullable
    private final Text tooltip;

    public EnchantingCatalystButton(int x, int y, int width, int height, @Nullable Text message, PressAction onPress, Screen screen, @Nullable ItemStack icon, @Nullable Text tooltip) {
        super(x, y, width < 0 ? ITEM_WIDTH + ITEM_PADDING * 2 : width, height < 0 ? ITEM_HEIGHT + ITEM_PADDING * 2 : height, Objects.requireNonNullElse(message, LiteralText.EMPTY), onPress);

        this.screen = screen;
        this.icon = icon;
        this.tooltip = tooltip;
    }

    public EnchantingCatalystButton(int x, int y, int width, int height, @Nullable Text message, PressAction onPress, Screen screen, @Nullable Item icon, @Nullable Text tooltip) {
        this(x, y, width, height, message, onPress, screen, icon == null ? null : icon.getDefaultStack(), tooltip);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        if (icon != null) {
            if (visible) {
                itemRenderer.renderInGui(icon, getCentreX() - ITEM_WIDTH / 2, getCentreY() - ITEM_HEIGHT / 2);
            }
        }
    }

    @Override
    public void renderTooltip(MatrixStack matrices, int mouseX, int mouseY) {
        if (tooltip != null) {
            if (hovered) {
                screen.renderTooltip(matrices, tooltip, mouseX, mouseY);
            }
        }

        super.renderTooltip(matrices, mouseX, mouseY);
    }

    private int getCentreX() {
        return x + width / 2;
    }

    private int getCentreY() {
        return y + height / 2;
    }
}
