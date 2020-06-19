package com.legacy.aether.events;

import com.legacy.aether.AetherConfig;
import com.legacy.aether.world.TeleporterAether;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import java.util.List;

public class AetherEntityEvents {

    @SubscribeEvent
    public void onEntityUpdate(TickEvent.WorldTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END)
        {
            if (!event.world.isRemote)
            {
                List entityList = event.world.loadedEntityList;

                for (int i = 0; i < entityList.size(); i++)
                {
                    entityUpdateEvents((Entity) entityList.get(i));
                }
            }
        }
    }

    private void entityUpdateEvents(Entity entity)
    {
        if (entity.riddenByEntity != null)
        {
            if (entity.riddenByEntity.isRiding())
            {
                if (entity.dimension == AetherConfig.getAetherDimensionID() && !entity.worldObj.isRemote)
                {
                    MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                    int previousDimension = entity.dimension;
                    int transferDimension = previousDimension == AetherConfig.getAetherDimensionID() ? 0 : AetherConfig.getAetherDimensionID();

                    if (entity.posY <= 0)
                    {
                        if (entity.riddenByEntity != null)
                        {
                            entity.riddenByEntity = null;
                        }

                        if (entity.ridingEntity != null)
                        {
                            entity.ridingEntity = null;
                        }

                        entity.timeUntilPortal = 300;
                        transferEntity(false, entity, server.worldServerForDimension(previousDimension), server.worldServerForDimension(transferDimension));
                    }
                }
            }
        }
    }

    public static void transferEntity(boolean shouldSpawnPortal, Entity entity, WorldServer previousWorldIn, WorldServer newWorldIn)
    {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        entity.dimension = newWorldIn.provider.dimensionId;
        previousWorldIn.removePlayerEntityDangerously(entity);
        entity.isDead = false;

        server.getConfigurationManager().transferEntityToWorld(entity, previousWorldIn.provider.dimensionId, previousWorldIn, newWorldIn, new TeleporterAether(shouldSpawnPortal, newWorldIn));
    }

}