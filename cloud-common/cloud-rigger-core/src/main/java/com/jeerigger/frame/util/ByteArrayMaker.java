package com.jeerigger.frame.util;




import java.io.ByteArrayOutputStream;


public class ByteArrayMaker extends ByteArrayOutputStream {

    static boolean collect = false;

    static {
        String collectString = System.getProperty(MakerStats.class.getName());

        if (collectString != null) {
            if (collectString.equals("true")) {
                collect = true;
            }
        }
    }

    static MakerStats stats = null;

    static {
        if (collect) {
            stats = new MakerStats(ByteArrayMaker.class.toString());
        }
    }

    static int defaultInitSize = 8000;

    static {
        String defaultInitSizeString = System.getProperty(
                ByteArrayMaker.class.getName() + ".initial.size");

        if (defaultInitSizeString != null) {
            try {
                defaultInitSize = Integer.parseInt(defaultInitSizeString);
            } catch (Exception e) {
                //解析失败使用默认值
                defaultInitSize=8000;
            }
        }
    }

    public static MakerStats getStatistics() {
        return stats;
    }

    public ByteArrayMaker() {
        super(defaultInitSize);

        if (collect) {
            _getInfo(new Throwable());
        }
    }

    public ByteArrayMaker(int size) {
        super(size);

        if (collect) {
            _getInfo(new Throwable());
        }
    }

    public synchronized byte[] toByteArray() {
        if (collect) {
            stats.add(_caller, _initSize, count);
        }

        return super.toByteArray();
    }

    @Override
    public synchronized String toString() {
        return super.toString();
    }

    private void _getInfo(Throwable t) {
        _initSize = buf.length;

        StackTraceElement[] elements = t.getStackTrace();

        if (elements.length > 1) {
            StackTraceElement el = elements[1];

            _caller =
                    el.getClassName() + StringPool.PERIOD + el.getMethodName() +
                            StringPool.COLON + el.getLineNumber();
        }
    }

    private int _initSize;
    private String _caller;
}
