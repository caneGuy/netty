/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

/**
 * The default {@link SpdySynStreamFrame} implementation.
 */
public class DefaultSpdySynStreamFrame extends DefaultSpdyHeadersFrame
        implements SpdySynStreamFrame {

    private int associatedStreamId;
    private byte priority;
    private boolean unidirectional;

    /**
     * Creates a new instance.
     *
     * @param streamId           the Stream-ID of this frame
     * @param associatedStreamId the Associated-To-Stream-ID of this frame
     * @param priority           the priority of the stream
     */
    public DefaultSpdySynStreamFrame(int streamId, int associatedStreamId, byte priority) {
        super(streamId);
        setAssociatedStreamId(associatedStreamId);
        setPriority(priority);
    }

    @Override
    public SpdySynStreamFrame setStreamId(int streamId) {
        super.setStreamId(streamId);
        return this;
    }

    @Override
    public SpdySynStreamFrame setLast(boolean last) {
        super.setLast(last);
        return this;
    }

    @Override
    public SpdySynStreamFrame setInvalid() {
        super.setInvalid();
        return this;
    }

    @Override
    @Deprecated
    public int getAssociatedToStreamId() {
        return associatedStreamId();
    }

    @Override
    public int associatedStreamId() {
        return associatedStreamId;
    }

    @Override
    @Deprecated
    public SpdySynStreamFrame setAssociatedToStreamId(int associatedToStreamId) {
        return setAssociatedStreamId(associatedToStreamId);
    }

    @Override
    public SpdySynStreamFrame setAssociatedStreamId(int associatedStreamId) {
        if (associatedStreamId < 0) {
            throw new IllegalArgumentException(
                    "Associated-To-Stream-ID cannot be negative: " +
                    associatedStreamId);
        }
        this.associatedStreamId = associatedStreamId;
        return this;
    }

    @Override
    @Deprecated
    public byte getPriority() {
        return priority();
    }

    @Override
    public byte priority() {
        return priority;
    }

    @Override
    public SpdySynStreamFrame setPriority(byte priority) {
        if (priority < 0 || priority > 7) {
            throw new IllegalArgumentException(
                    "Priority must be between 0 and 7 inclusive: " + priority);
        }
        this.priority = priority;
        return this;
    }

    @Override
    public boolean isUnidirectional() {
        return unidirectional;
    }

    @Override
    public SpdySynStreamFrame setUnidirectional(boolean unidirectional) {
        this.unidirectional = unidirectional;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(StringUtil.simpleClassName(this));
        buf.append("(last: ");
        buf.append(isLast());
        buf.append("; unidirectional: ");
        buf.append(isUnidirectional());
        buf.append(')');
        buf.append(StringUtil.NEWLINE);
        buf.append("--> Stream-ID = ");
        buf.append(streamId());
        buf.append(StringUtil.NEWLINE);
        if (associatedStreamId != 0) {
            buf.append("--> Associated-To-Stream-ID = ");
            buf.append(associatedStreamId());
            buf.append(StringUtil.NEWLINE);
        }
        buf.append("--> Priority = ");
        buf.append(priority());
        buf.append(StringUtil.NEWLINE);
        buf.append("--> Headers:");
        buf.append(StringUtil.NEWLINE);
        appendHeaders(buf);

        // Remove the last newline.
        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }
}
