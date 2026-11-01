package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

/** 编译桩。 */
public class PackageInfo implements Parcelable {

    public String packageName;
    public Signature[] signatures;        // 旧签名字段（GET_SIGNATURES）
    public SigningInfo signingInfo;       // 新签名容器（GET_SIGNING_CERTIFICATES）
    public ApplicationInfo applicationInfo;
    public ProviderInfo[] providers;      // 反侦察时置 null

    public static final Parcelable.Creator<PackageInfo> CREATOR = new Parcelable.Creator<PackageInfo>() {
        @Override
        public PackageInfo createFromParcel(Parcel source) {
            return new PackageInfo();
        }

        @Override
        public PackageInfo[] newArray(int size) {
            return new PackageInfo[size];
        }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {}
}
