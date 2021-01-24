package com.off.inventory.gui;

import com.off.blocks.container.ContainerCompactor;
import com.off.tileentity.TileEntityCompactor;
import com.off.util.Reference;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCompactor extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/compactor.png");
	private final InventoryPlayer player;
	private final TileEntityCompactor tileEntity;
	
	public GuiCompactor(InventoryPlayer player, TileEntityCompactor tileEntity)
	{
		super(new ContainerCompactor(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(getGuiLeft(), getGuiTop(), 0, 0, xSize, ySize);
		
		if (TileEntityCompactor.isActive(tileEntity))
		{
			Gui.drawModalRectWithCustomSizedTexture(49, 50, 176, 12, 32, 18, 32, 18);
		}
		
		int l = getProgressScaled(24);
		this.drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 34, 176, 14, l + 1, 16);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String tileName = this.tileEntity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.getXSize() / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 122, this.ySize - 96 + 2, 4210752);
	}

	private int getProgressScaled(int pixels)
	{
		int i = this.tileEntity.getField(1);
		if (i == 0)
			i = 200;
		return this.tileEntity.getField(0) * pixels / i;
	}
}
