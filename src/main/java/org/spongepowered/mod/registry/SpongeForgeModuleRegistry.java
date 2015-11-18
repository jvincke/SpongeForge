/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.mod.registry;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import org.spongepowered.api.GameProfile;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableSpawnableData;
import org.spongepowered.api.data.manipulator.mutable.item.SpawnableData;
import org.spongepowered.common.data.SpongeDataRegistry;
import org.spongepowered.common.data.manipulator.immutable.item.ImmutableSpongeSpawnableData;
import org.spongepowered.common.data.manipulator.mutable.item.SpongeSpawnableData;
import org.spongepowered.common.data.property.SpongePropertyRegistry;
import org.spongepowered.common.world.PlayerSimulatorFactory;
import org.spongepowered.mod.SpongeFakePlayer;
import org.spongepowered.mod.data.SpawnableDataProcessor;
import org.spongepowered.mod.data.SpawnableEntityTypeValueProcessor;

public class SpongeForgeModuleRegistry {

    public static void registerForgeData() {

        // Property registration
        final SpongePropertyRegistry registry = SpongePropertyRegistry.getInstance();
        // registry.register(MatterProperty.class, ForgeMatterProperty);


        // Data registration
        SpongeDataRegistry dataRegistry = SpongeDataRegistry.getInstance();
        final SpawnableDataProcessor spawnableDataProcessor = new SpawnableDataProcessor();
        dataRegistry.registerDataProcessorAndImpl(SpawnableData.class, SpongeSpawnableData.class, ImmutableSpawnableData.class,
                                                  ImmutableSpongeSpawnableData.class, spawnableDataProcessor);

        // Value registration
        dataRegistry.registerValueProcessor(Keys.SPAWNABLE_ENTITY_TYPE, new SpawnableEntityTypeValueProcessor());


        new PlayerSimulatorFactory() {

            @Override
            protected EntityPlayerMP createPlayer(WorldServer world, GameProfile profile) {
                return new SpongeFakePlayer(world, (com.mojang.authlib.GameProfile) profile);
            }
        };
    }

}
