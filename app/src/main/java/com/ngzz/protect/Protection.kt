package com.ngzz.protect

enum class Protection(
    val id: String,
    val title: String,
    val subtitle: String,
    val enabled: Boolean = true
) {
    SIGNATURE("sign", "签名校验", "检测应用是否被篡改"),
    ANTI_DEBUG("anti_debug", "反调试", "检测: frida/lsposed/xposed/gg..."),
    PACKAGE("package", "包名检测", "检测包名是否被篡改"),
    PROXY("proxy", "抓包检测", "检测到VPN/WIFI代理就退出程序"),
    ROOT("root", "Root检测", "检测设备是否已 Root"),
    NETWORK("network", "网络检测", "如果断网则退出程序"),
    EXPIRY("expiry", "到期退出", "到达设定的时间就退出程序")
}
