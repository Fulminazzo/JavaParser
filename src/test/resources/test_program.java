// Assignment of all primitive types
byte b = 1;
short s = 2;
char c = 'c';
int i = 4;
long l = 5L;
float f = 6.0f;
double d = 7.0d;
boolean bo = true;

// Re-assignments
i = -4;
l = i;
l = 5;
f = i;
f = l;
f = 6;
d = i;
d = l;
d = f;
d = 7;
d = 7.0f;
d = 7.0;
bo = false;
bo = !bo;

// Assignment of all wrapper types
Byte bW = 1;
Short sW = 2;
Character cW = 'c';
Integer iW = 4;
Long lW = 5L;
Float fW = 6.0f;
Double dW = 7.0d;
Boolean boW = true;
String string = "Hello world";

// Binary operations
i = i + 1 - 1 * 1 / 1 % 1 & 1 | 1 ^ 1 << 2 >> 1 >>> 1
l = l + 1 - 1 * 1 / 1 % 1 & 1 | 1 ^ 1 << 2 >> 1 >>> 1
f = f + 1 - 1 * 1 / 1 % 1 & 1 | 1 ^ 1 << 2 >> 1 >>> 1
d = d + 1 - 1 * 1 / 1 % 1 & 1 | 1 ^ 1 << 2 >> 1 >>> 1
bo = true == false != true && false || true
bo = d == f != i && bo || false
bo = i < d || i <= d || f >= d || f > d

// Re-Assignment operations
i++;
i--;
++i;
--i;
i += 1;
i -= 1;
i *= 1;
i /= 1;
i %= 1;
i &= 1;
i |= 1;
i ^= 2;
i <<= 1;
i >>= 1;
i >>>= 1;
