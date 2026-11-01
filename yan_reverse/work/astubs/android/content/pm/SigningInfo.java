package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

/** 编译桩：Android 9+ 签名容器（内部 SigningDetails 由反射/Unsafe 访问）。 */
public class SigningInfo implements Parcelable {

    // 真实字段名，桩里也保留，便于反射路径调试
    private Object mSigningDetails;

    public static final Parcelable.Creator<SigningInfo> CREATOR = new Parcelable.Creator<SigningInfo>() {
        @Override
        public SigningInfo createFromParcel(Parcel source) {
            return new SigningInfo();
        }

        @Override
        public SigningInfo[] newArray(int size) {
            return new SigningInfo[size];
        }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {}
}
