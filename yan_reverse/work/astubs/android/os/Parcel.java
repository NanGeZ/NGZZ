package android.os;

/** 编译桩：Parcel 读写接口（仅方法签名，无实际缓冲区行为）。 */
public final class Parcel {

    public static Parcel obtain() { return new Parcel(); }

    public void recycle() {}
    public void setDataPosition(int pos) {}
    public int dataPosition() { return 0; }
    public void setDataSize(int size) {}
    public int dataSize() { return 0; }
    public void appendFrom(Parcel src, int offset, int length) {}
    public int dataAvail() { return 0; }

    public int readInt() { return 0; }
    public void writeInt(int value) {}
    public String readString() { return null; }
    public Object readValue(ClassLoader loader) { return null; }

    public void writeNoException() {}
    public void writeStrongBinder(IBinder binder) {}

    public <T> T readTypedObject(Parcelable.Creator<T> creator) { return null; }
    public void writeTypedObject(Parcelable value, int flags) {}

    private Parcel() {}
}
