package android.os;

/** 编译桩：本地 Binder 基类。 */
public class Binder implements IBinder {

    @Override
    public boolean transact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        return false;
    }

    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        return false;
    }
}
