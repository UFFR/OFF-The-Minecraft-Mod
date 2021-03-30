package com.off.inventory.gui;

import com.off.inventory.container.ContainerKiln;
import com.off.tileentity.TileEntityKilnCore;
import com.off.util.Reference;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiKiln extends GuiContainer
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/kiln.png");
	private final InventoryPlayer player;
	private final TileEntityKilnCore tileEntityKiln;

	public GuiKiln(InventoryPlayer player, TileEntityKilnCore tileEntity)
	{
		super(new ContainerKiln(player, tileEntity));
		this.player = player;
		this.tileEntityKiln = tileEntity;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(getGuiLeft(), getGuiTop(), 0, 0, xSize, ySize);
		// Burning icon
		if (tileEntityKiln.isBurning())
		{
			this.drawTexturedModalRect(this.guiLeft + 57, this.guiTop + 37, 176, 0, 14, 14);
		}
		// Arrow
		int arrow = tileEntityKiln.getProgressScaled(24);
		this.drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 34, 176, 0, arrow, 17);
		// Fuel
		int fuelLevel = tileEntityKiln.getFuelRemainingScaled(52);
		this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 69 - fuelLevel, 177, 83 - fuelLevel, 16, fuelLevel);
		// Heat
		int heatLevel = tileEntityKiln.getHeatRemainingScaled(52);
		this.drawTexturedModalRect(this.guiLeft + 30, this.guiTop + 68 - heatLevel, 195, 83 - heatLevel, 16, heatLevel);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String tileName = this.tileEntityKiln.hasCustomInventoryName() ? this.tileEntityKiln.getInventoryName() : I18n.format(this.tileEntityKiln.getInventoryName());
		this.fontRenderer.drawString(tileName, (this.getXSize() / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
		String fuelLevel = String.format("Fuel: %s/%s", this.tileEntityKiln.getField(2), TileEntityKilnCore.maxFuel);
		String heatLevel = String.format("Heat: %s/%s°C", this.tileEntityKiln.getField(3), TileEntityKilnCore.maxHeat);
		//this.fontRenderer.drawString(fuelLevel, 73, 60, 4210752);
		//this.fontRenderer.drawString(heatLevel, 73, this.ySize - 96 + 2, 4210752);
		if ((mouseY >= this.guiTop + 17 && mouseY <= this.guiTop + 68))
		{
			if (mouseX >= this.guiLeft + 30 && mouseX <= this.guiLeft + 45)
			{
				this.drawHoveringText(heatLevel, mouseX - this.guiLeft, mouseY - this.guiTop);
			}
			if (mouseX >= this.guiLeft + 8 && mouseX <= this.guiLeft + 23)
			{
				this.drawHoveringText(fuelLevel, mouseX - this.guiLeft, mouseY - this.guiTop);
			}
		}
	}
}
