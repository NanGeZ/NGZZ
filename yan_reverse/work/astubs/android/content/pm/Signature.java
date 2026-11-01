package android.content.pm;

/** 编译桩：签名证书包装。 */
public final class Signature {

    private final byte[] mSignature;

    public Signature(byte[] signature) {
        mSignature = signature;
    }

    public byte[] toByteArray() {
        return mSignature == null ? null : mSignature.clone();
    }
}
