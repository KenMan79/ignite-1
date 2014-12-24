/* @java.file.header */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package org.gridgain.grid.kernal.processors.cache;

import org.apache.ignite.marshaller.optimized.*;
import org.gridgain.grid.util.typedef.internal.*;
import org.gridgain.grid.util.tostring.*;
import org.jetbrains.annotations.*;

import javax.cache.processor.*;
import java.io.*;
import java.util.*;

/**
 * Return value for cases where both, value and success flag need to be returned.
 */
public class GridCacheReturn<V> implements Externalizable, IgniteOptimizedMarshallable {
    /** */
    private static final long serialVersionUID = 0L;

    /** */
    @SuppressWarnings({"NonConstantFieldWithUpperCaseName", "JavaAbbreviationUsage", "UnusedDeclaration"})
    private static Object GG_CLASS_ID;

    /** Value. */
    @GridToStringInclude
    private volatile V v;

    /** Success flag. */
    private volatile boolean success;

    /**
     * Empty constructor.
     */
    public GridCacheReturn() {
        // No-op.
    }

    /**
     *
     * @param success Success flag.
     */
    public GridCacheReturn(boolean success) {
        this.success = success;
    }

    /**
     *
     * @param v Value.
     * @param success Success flag.
     */
    public GridCacheReturn(V v, boolean success) {
        this.v = v;
        this.success = success;
    }

    /**
     * @return Value.
     */
    @Nullable public V value() {
        return v;
    }

    /**
     * Checks if value is not {@code null}.
     *
     * @return {@code True} if value is not {@code null}.
     */
    public boolean hasValue() {
        return v != null;
    }

    /**
     * @param v Value.
     * @return This instance for chaining.
     */
    public GridCacheReturn<V> value(V v) {
        this.v = v;

        return this;
    }

    /**
     * @return Success flag.
     */
    public boolean success() {
        return success;
    }

    /**
     * @param v Value to set.
     * @param success Success flag to set.
     * @return This instance for chaining.
     */
    public GridCacheReturn<V> set(@Nullable V v, boolean success) {
        this.v = v;
        this.success = success;

        return this;
    }

    /**
     * @param success Success flag.
     * @return This instance for chaining.
     */
    public GridCacheReturn<V> success(boolean success) {
        this.success = success;

        return this;
    }

    /**
     * @param key Key.
     * @param res Result.
     */
    @SuppressWarnings("unchecked")
    public synchronized void addEntryProcessResult(Object key, EntryProcessorResult<?> res) {
        assert v == null || v instanceof Map : v;
        assert key != null;
        assert res != null;

        HashMap<Object, EntryProcessorResult> resMap = (HashMap<Object, EntryProcessorResult>)v;

        if (resMap == null) {
            resMap = new HashMap<>();

            v = (V)resMap;
        }

        resMap.put(key, res);
    }

    /** {@inheritDoc} */
    @Override public Object ggClassId() {
        return GG_CLASS_ID;
    }

    /** {@inheritDoc} */
    @Override public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(success);
        out.writeObject(v);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        success = in.readBoolean();
        v = (V)in.readObject();
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(GridCacheReturn.class, this);
    }
}
