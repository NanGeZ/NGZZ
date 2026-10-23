'use strict';

module.exports = [
  {
    id: 'signature',
    name: '签名校验',
    desc: '检测应用是否被篡改',
    detail: '启动时比对 APK 签名证书 SHA-256 指纹，不一致立即退出，防止二次打包与代码篡改。',
    hasExtra: false,
  },
  {
    id: 'antiDebug',
    name: '反调试',
    desc: '检测: frida/lsposed/xposed/gg...',
    detail: '检测调试器连接、TracerPid 注入、Frida/Xposed/LSPosed/Magisk 等 Hook 框架特征与常用调试端口，并周期轮询。',
    hasExtra: false,
  },
  {
    id: 'packageCheck',
    name: '包名检测',
    desc: '检测包名是否被篡改',
    detail: '校验运行时包名与原始包名一致，防止马甲包、重打包与渠道篡改。',
    hasExtra: false,
  },
  {
    id: 'antiCapture',
    name: '抓包检测',
    desc: '检测到VPN/WIFI代理就退出程序',
    detail: '检测 VPN 通道、系统/应用层 HTTP 代理与 WiFi 代理配置，发现抓包环境即退出。',
    hasExtra: true,
    extraLabel: '检测到抓包环境立即退出程序',
  },
  {
    id: 'rootCheck',
    name: 'Root检测',
    desc: '检测设备是否已 Root',
    detail: '扫描 su 二进制、Magisk/SuperSU 等 Root 管理器、test-keys 系统签名等 Root 特征。',
    hasExtra: false,
  },
  {
    id: 'networkCheck',
    name: '网络检测',
    desc: '如果断网则退出程序',
    detail: '持续检测网络连通性，断网时退出程序（可配置超时与重试次数）。',
    hasExtra: true,
    extraType: 'network',
    extraLabel: '网络探测超时与重试',
  },
];
