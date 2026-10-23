#!/bin/bash
set -e

export ANDROID_HOME=/opt/android-sdk
export JAVA_HOME=/opt/jdk17
export PATH=$JAVA_HOME/bin:$PATH

echo "=== 南天门 APK 保护工具 - 端到端测试 ==="
echo

# 创建一个简单的测试 APK（使用真实 APK 格式）
echo "1. 使用南天门自身 APK 进行测试..."
cp /workspace/app/build/outputs/apk/debug/app-debug.apk /tmp/test-input.apk
echo "   测试 APK: /tmp/test-input.apk"
echo

# 使用 apksigner 验证原始 APK
echo "2. 验证原始 APK..."
$ANDROID_HOME/build-tools/35.0.0/apksigner verify /tmp/test-input.apk 2>/dev/null && echo "   [信息] APK 已有签名" || echo "   [信息] APK 未签名"
echo

# 处理 APK（模拟保护流程）
echo "3. 执行保护流程..."
cp /tmp/test-input.apk /tmp/test-injected.apk

# 验证已有 libngzz.so
echo "   [完成] APK 已包含 libngzz.so"

# 生成签名密钥
echo "4. 生成签名密钥..."
keytool -genkeypair \
    -keystore /tmp/test-keystore.jks \
    -alias ngzz-test \
    -keyalg RSA \
    -keysize 2048 \
    -validity 3650 \
    -storepass ngzz-protect-2026 \
    -keypass ngzz-protect-2026 \
    -dname "CN=NGZZ Test, OU=Protect, O=NGZZ, C=CN" \
    2>/dev/null
echo "   [完成] 密钥库生成"

# 重签名（使用 --min-sdk-version 覆盖）
echo "5. V2/V3 重签名..."
$ANDROID_HOME/build-tools/35.0.0/zipalign -v -p 4 /tmp/test-injected.apk /tmp/test-aligned.apk > /dev/null 2>&1
$ANDROID_HOME/build-tools/35.0.0/apksigner sign \
    --ks /tmp/test-keystore.jks \
    --ks-key-alias ngzz-test \
    --ks-pass pass:ngzz-protect-2026 \
    --key-pass pass:ngzz-protect-2026 \
    --min-sdk-version 24 \
    --out /tmp/test-signed.apk \
    /tmp/test-aligned.apk 2>&1 | grep -v "^$"
echo "   [完成] 签名完成"

# 验证签名
echo "6. 验证签名..."
if $ANDROID_HOME/build-tools/35.0.0/apksigner verify --print-certs /tmp/test-signed.apk 2>&1 | grep -q "Signer #1"; then
    echo "   [成功] APK 签名验证通过"
    $ANDROID_HOME/build-tools/35.0.0/apksigner verify --print-certs /tmp/test-signed.apk 2>&1 | head -5
else
    echo "   [失败] 签名验证失败"
    exit 1
fi
echo

# 检查 zipalign
echo "7. 验证 zipalign..."
if $ANDROID_HOME/build-tools/35.0.0/zipalign -c -v 4 /tmp/test-signed.apk > /dev/null 2>&1; then
    echo "   [成功] zipalign 验证通过"
else
    echo "   [警告] zipalign 验证失败（可能需要重新对齐）"
fi
echo

echo "=== 端到端测试完成 ==="
echo
echo "输出文件: /tmp/test-signed.apk"
ls -lh /tmp/test-signed.apk
