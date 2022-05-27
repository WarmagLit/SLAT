package com.tsu.slat.utils;


import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64UtilOutputStream extends FilterOutputStream {
    private boolean encode;
    private int position;
    private byte[] buffer;
    private int bufferLength;
    private int lineLength;
    private boolean breakLines;
    private byte[] b4;
    private boolean suspendEncoding;

    public Base64UtilOutputStream(OutputStream out) {
        this(out, 1);
    }

    public Base64UtilOutputStream(OutputStream out, int options) {
        super(out);
        this.breakLines = (options & 8) != 8;
        this.encode = (options & 1) == 1;
        this.bufferLength = this.encode ? 3 : 4;
        this.buffer = new byte[this.bufferLength];
        this.position = 0;
        this.lineLength = 0;
        this.suspendEncoding = false;
        this.b4 = new byte[4];
    }

    public void write(int theByte) throws IOException {
        if (this.suspendEncoding) {
            super.out.write(theByte);
        } else {
            /*
            if (this.encode) {
                this.buffer[this.position++] = (byte)theByte;
                if (this.position >= this.bufferLength) {
                    this.out.write(Base64Util.access$0(this.b4, this.buffer, this.bufferLength));
                    this.lineLength += 4;
                    if (this.breakLines && this.lineLength >= 76) {
                        this.out.write(10);
                        this.lineLength = 0;
                    }

                    this.position = 0;
                }
            } else if (Base64Util.access$1()[theByte & 127] > -5) {
                this.buffer[this.position++] = (byte)theByte;
                if (this.position >= this.bufferLength) {
                    int len = Base64Util.access$2(this.buffer, 0, this.b4, 0);
                    this.out.write(this.b4, 0, len);
                    this.position = 0;
                }
            } else if (Base64Util.access$1()[theByte & 127] != -5) {
                throw new IOException("Invalid character in Base64Util data.");
            }
*/
        }
    }

    public void write(byte[] theBytes, int off, int len) throws IOException {
        if (this.suspendEncoding) {
            super.out.write(theBytes, off, len);
        } else {
            for(int i = 0; i < len; ++i) {
                this.write(theBytes[off + i]);
            }

        }
    }

    public void flushBase64() throws IOException {
        if (this.position > 0) {
            if (!this.encode) {
                throw new IOException("Base64Util input not properly padded.");
            }

            //this.out.write(Base64Util.access$0(this.b4, this.buffer, this.position));
            this.position = 0;
        }

    }

    public void close() throws IOException {
        this.flushBase64();
        super.close();
        this.buffer = null;
        this.out = null;
    }

    public void suspendEncoding() throws IOException {
        this.flushBase64();
        this.suspendEncoding = true;
    }

    public void resumeEncoding() {
        this.suspendEncoding = false;
    }
}
