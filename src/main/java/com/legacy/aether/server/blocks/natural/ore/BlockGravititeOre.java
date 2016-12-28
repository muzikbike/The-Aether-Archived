package com.legacy.aether.server.blocks.natural.ore;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import com.legacy.aether.server.blocks.util.BlockFloating;

public class BlockGravititeOre extends BlockFloating
{

	public BlockGravititeOre() 
	{
		super(Material.ROCK, false);

		this.setHardness(3F);
		this.setSoundType(SoundType.STONE);
	}

}