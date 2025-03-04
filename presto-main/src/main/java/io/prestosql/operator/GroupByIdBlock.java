/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.operator;

import io.airlift.slice.Slice;
import io.prestosql.spi.block.Block;
import io.prestosql.spi.block.BlockBuilder;
import org.openjdk.jol.info.ClassLayout;

import java.util.function.BiConsumer;

import static com.google.common.base.MoreObjects.toStringHelper;
import static io.prestosql.spi.type.BigintType.BIGINT;
import static java.util.Objects.requireNonNull;

public class GroupByIdBlock
        implements Block
{
    private static final int INSTANCE_SIZE = ClassLayout.parseClass(GroupByIdBlock.class).instanceSize();

    private final long groupCount;
    private final Block block;

    public GroupByIdBlock(long groupCount, Block block)
    {
        requireNonNull(block, "block is null");
        this.groupCount = groupCount;
        this.block = block;
    }

    public long getGroupCount()
    {
        return groupCount;
    }

    public long getGroupId(int position)
    {
        return BIGINT.getLong(block, position);
    }

    @Override
    public Block getRegion(int positionOffset, int length)
    {
        return block.getRegion(positionOffset, length);
    }

    @Override
    public long getRegionSizeInBytes(int positionOffset, int length)
    {
        return block.getRegionSizeInBytes(positionOffset, length);
    }

    @Override
    public long getPositionsSizeInBytes(boolean[] positions)
    {
        return block.getPositionsSizeInBytes(positions);
    }

    @Override
    public Block copyRegion(int positionOffset, int length)
    {
        return block.copyRegion(positionOffset, length);
    }

    @Override
    public int getSliceLength(int position)
    {
        return block.getSliceLength(position);
    }

    @Override
    public byte getByte(int position, int offset)
    {
        return block.getByte(position, offset);
    }

    @Override
    public short getShort(int position, int offset)
    {
        return block.getShort(position, offset);
    }

    @Override
    public int getInt(int position, int offset)
    {
        return block.getInt(position, offset);
    }

    @Override
    public long getLong(int position, int offset)
    {
        return block.getLong(position, offset);
    }

    @Override
    public Slice getSlice(int position, int offset, int length)
    {
        return block.getSlice(position, offset, length);
    }

    @Override
    public <T> T getObject(int position, Class<T> clazz)
    {
        return block.getObject(position, clazz);
    }

    @Override
    public boolean bytesEqual(int position, int offset, Slice otherSlice, int otherOffset, int length)
    {
        return block.bytesEqual(position, offset, otherSlice, otherOffset, length);
    }

    @Override
    public int bytesCompare(int position, int offset, int length, Slice otherSlice, int otherOffset, int otherLength)
    {
        return block.bytesCompare(position, offset, length, otherSlice, otherOffset, otherLength);
    }

    @Override
    public void writeBytesTo(int position, int offset, int length, BlockBuilder blockBuilder)
    {
        block.writeBytesTo(position, offset, length, blockBuilder);
    }

    @Override
    public void writePositionTo(int position, BlockBuilder blockBuilder)
    {
        block.writePositionTo(position, blockBuilder);
    }

    @Override
    public boolean equals(int position, int offset, Block otherBlock, int otherPosition, int otherOffset, int length)
    {
        return block.equals(position, offset, otherBlock, otherPosition, otherOffset, length);
    }

    @Override
    public long hash(int position, int offset, int length)
    {
        return block.hash(position, offset, length);
    }

    @Override
    public int compareTo(int leftPosition, int leftOffset, int leftLength, Block rightBlock, int rightPosition, int rightOffset, int rightLength)
    {
        return block.compareTo(leftPosition, leftOffset, leftLength, rightBlock, rightPosition, rightOffset, rightLength);
    }

    @Override
    public Block getSingleValueBlock(int position)
    {
        return block.getSingleValueBlock(position);
    }

    @Override
    public boolean isNull(int position)
    {
        return block.isNull(position);
    }

    @Override
    public int getPositionCount()
    {
        return block.getPositionCount();
    }

    @Override
    public long getSizeInBytes()
    {
        return block.getSizeInBytes();
    }

    @Override
    public long getRetainedSizeInBytes()
    {
        return INSTANCE_SIZE + block.getRetainedSizeInBytes();
    }

    @Override
    public long getEstimatedDataSizeForStats(int position)
    {
        return block.getEstimatedDataSizeForStats(position);
    }

    @Override
    public void retainedBytesForEachPart(BiConsumer<Object, Long> consumer)
    {
        consumer.accept(block, block.getRetainedSizeInBytes());
        consumer.accept(this, (long) INSTANCE_SIZE);
    }

    @Override
    public String getEncodingName()
    {
        throw new UnsupportedOperationException("GroupByIdBlock does not support serialization");
    }

    @Override
    public Block copyPositions(int[] positions, int offset, int length)
    {
        return block.copyPositions(positions, offset, length);
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("groupCount", groupCount)
                .add("positionCount", getPositionCount())
                .toString();
    }

    @Override
    public boolean isLoaded()
    {
        return block.isLoaded();
    }

    @Override
    public Block getLoadedBlock()
    {
        return block.getLoadedBlock();
    }
}
