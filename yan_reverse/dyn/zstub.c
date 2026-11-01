/* libz 桩：真实 zlib 未交叉编译，先用空实现(必要时再换真zlib) */
typedef void* z_streamp;
int inflateInit2_(z_streamp s, int w, const char* v, int n) { (void)s;(void)w;(void)v;(void)n; return -1; }
int inflate(z_streamp s, int f) { (void)s;(void)f; return -1; }
int inflateEnd(z_streamp s) { (void)s; return -1; }
int deflateInit2_(z_streamp s, int lvl, int meth, int win, int mem, const char* v, int n) { (void)s;(void)lvl;(void)meth;(void)win;(void)mem;(void)v;(void)n; return -1; }
int deflate(z_streamp s, int f) { (void)s;(void)f; return -1; }
int deflateEnd(z_streamp s) { (void)s; return -1; }
unsigned long crc32(unsigned long c, const unsigned char* b, unsigned n) { (void)b; return c ^ 0xffffffffUL ^ (n? 0:0); }
