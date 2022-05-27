package com.tsu.slat.utils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPOutputStream;

class Base64Util {
    public static final int NO_OPTIONS = 0;
    public static final int ENCODE = 1;
    public static final int DECODE = 0;
    public static final int GZIP = 2;
    public static final int DONT_BREAK_LINES = 8;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte EQUALS_SIGN = 61;
    private static final byte NEW_LINE = 10;
    private static final String PREFERRED_ENCODING = "UTF-8";
    private static final byte[] ALPHABET;
    private static final byte[] _NATIVE_ALPHABET = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] DECODABET;
    private static final byte WHITE_SPACE_ENC = -5;

    static {
        byte[] __bytes;
        try {
            __bytes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".getBytes("UTF-8");
        } catch (UnsupportedEncodingException var2) {
            __bytes = _NATIVE_ALPHABET;
        }

        ALPHABET = __bytes;
        DECODABET = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9};
    }

    private Base64Util() {
    }

    public static String encodeBytes(byte[] source) {
        return encodeBytes(source, 0, source.length, 0);
    }

    public static String encodeBytes(byte[] source, int off, int len, int options) {
        int dontBreakLines = options & 8;
        int gzip = options & 2;
        if (gzip == 2) {
            ByteArrayOutputStream baos = null;
            GZIPOutputStream gzos = null;
            Base64UtilOutputStream b64os = null;

            label191: {
                try {
                    baos = new ByteArrayOutputStream();
                    b64os = new Base64UtilOutputStream(baos, 1 | dontBreakLines);
                    gzos = new GZIPOutputStream(b64os);
                    gzos.write(source, off, len);
                    gzos.close();
                    break label191;
                } catch (IOException var32) {
                    var32.printStackTrace();
                } finally {
                    try {
                        gzos.close();
                    } catch (Exception var29) {
                    }

                    try {
                        b64os.close();
                    } catch (Exception var28) {
                    }

                    try {
                        baos.close();
                    } catch (Exception var27) {
                    }

                }

                return null;
            }

            try {
                return new String(baos.toByteArray(), "UTF-8");
            } catch (UnsupportedEncodingException var30) {
                return new String(baos.toByteArray());
            }
        } else {
            boolean breakLines = dontBreakLines == 0;
            int len43 = len * 4 / 3;
            byte[] outBuff = new byte[len43 + (len % 3 > 0 ? 4 : 0) + (breakLines ? len43 / 76 : 0)];
            int d = 0;
            int e = 0;
            int len2 = len - 2;

            for(int lineLength = 0; d < len2; e += 4) {
                encode3to4(source, d + off, 3, outBuff, e);
                lineLength += 4;
                if (breakLines && lineLength == 76) {
                    outBuff[e + 4] = 10;
                    ++e;
                    lineLength = 0;
                }

                d += 3;
            }

            if (d < len) {
                encode3to4(source, d + off, len - d, outBuff, e);
                e += 4;
            }

            try {
                return new String(outBuff, 0, e, "UTF-8");
            } catch (UnsupportedEncodingException var31) {
                return new String(outBuff, 0, e);
            }
        }
    }

    private static byte[] encode3to4(byte[] b4, byte[] threeBytes, int numSigBytes) {
        encode3to4(threeBytes, 0, numSigBytes, b4, 0);
        return b4;
    }

    private static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset) {
        int inBuff = (numSigBytes > 0 ? source[srcOffset] << 24 >>> 8 : 0) | (numSigBytes > 1 ? source[srcOffset + 1] << 24 >>> 16 : 0) | (numSigBytes > 2 ? source[srcOffset + 2] << 24 >>> 24 : 0);
        switch(numSigBytes) {
            case 1:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[inBuff >>> 12 & 63];
                destination[destOffset + 2] = 61;
                destination[destOffset + 3] = 61;
                return destination;
            case 2:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[inBuff >>> 12 & 63];
                destination[destOffset + 2] = ALPHABET[inBuff >>> 6 & 63];
                destination[destOffset + 3] = 61;
                return destination;
            case 3:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[inBuff >>> 12 & 63];
                destination[destOffset + 2] = ALPHABET[inBuff >>> 6 & 63];
                destination[destOffset + 3] = ALPHABET[inBuff & 63];
                return destination;
            default:
                return destination;
        }
    }

    private static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset) {
        int outBuff;
        if (source[srcOffset + 2] == 61) {
            outBuff = (DECODABET[source[srcOffset]] & 255) << 18 | (DECODABET[source[srcOffset + 1]] & 255) << 12;
            destination[destOffset] = (byte)(outBuff >>> 16);
            return 1;
        } else if (source[srcOffset + 3] == 61) {
            outBuff = (DECODABET[source[srcOffset]] & 255) << 18 | (DECODABET[source[srcOffset + 1]] & 255) << 12 | (DECODABET[source[srcOffset + 2]] & 255) << 6;
            destination[destOffset] = (byte)(outBuff >>> 16);
            destination[destOffset + 1] = (byte)(outBuff >>> 8);
            return 2;
        } else {
            try {
                outBuff = (DECODABET[source[srcOffset]] & 255) << 18 | (DECODABET[source[srcOffset + 1]] & 255) << 12 | (DECODABET[source[srcOffset + 2]] & 255) << 6 | DECODABET[source[srcOffset + 3]] & 255;
                destination[destOffset] = (byte)(outBuff >> 16);
                destination[destOffset + 1] = (byte)(outBuff >> 8);
                destination[destOffset + 2] = (byte)outBuff;
                return 3;
            } catch (Exception var5) {
                System.out.println(source[srcOffset] + ": " + DECODABET[source[srcOffset]]);
                System.out.println(source[srcOffset + 1] + ": " + DECODABET[source[srcOffset + 1]]);
                System.out.println(source[srcOffset + 2] + ": " + DECODABET[source[srcOffset + 2]]);
                System.out.println(source[srcOffset + 3] + ": " + DECODABET[source[srcOffset + 3]]);
                return -1;
            }
        }
    }
}