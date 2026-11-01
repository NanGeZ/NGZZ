package android.os;

/** 编译桩：Binder 事务接口。 */
public interface IBinder {
    boolean transact(int code, Parcel data, Parcel reply, int flags) throws RemoteException;
}
