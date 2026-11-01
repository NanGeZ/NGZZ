package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

/** 编译桩。 */
public class ApplicationInfo implements Parcelable {

    public String packageName;
    public String sourceDir;
    public Signature[] signatures;   // 旧签名字段（反射/Unsafe 改写目标）

    public static final Parcelable.Creator<ApplicationInfo> CREATOR = new Parcelable.Creator<ApplicationInfo>() {
        @Override
        public ApplicationInfo createFromParcel(Parcel source) {
            return new ApplicationInfo();
        }

        @Override
        public ApplicationInfo[] newArray(int size) {
            return new ApplicationInfo[size];
        }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {}
}
