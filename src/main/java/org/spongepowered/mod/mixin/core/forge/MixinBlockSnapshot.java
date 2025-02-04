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
package org.spongepowered.mod.mixin.core.forge;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.common.block.SpongeBlockSnapshotBuilder;
import org.spongepowered.common.util.VecHelper;
import org.spongepowered.mod.interfaces.IMixinBlockSnapshot;

@NonnullByDefault
@Mixin(value = net.minecraftforge.common.util.BlockSnapshot.class, remap = false)
public abstract class MixinBlockSnapshot implements IMixinBlockSnapshot {

    @Shadow public transient IBlockState replacedBlock;
    @Shadow public BlockPos pos;
    @Shadow public transient net.minecraft.world.World world;
    @Shadow private NBTTagCompound nbt;
    @Shadow public int flag;

    @Shadow
    public abstract void writeToNBT(NBTTagCompound compound);

    @Override
    public BlockSnapshot createSpongeBlockSnapshot() {
        Location<World> location = new Location<>((World) this.world, VecHelper.toVector(this.pos));
        SpongeBlockSnapshotBuilder builder = new SpongeBlockSnapshotBuilder();
        builder.blockState((BlockState) this.replacedBlock)
            .worldId(location.getExtent().getUniqueId())
            .position(location.getBlockPosition());
        if (this.nbt != null) {
            builder.unsafeNbt(this.nbt);
        }
        return builder.build();
    }
}
