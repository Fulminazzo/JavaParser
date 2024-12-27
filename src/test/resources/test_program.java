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
String sW = "Hello world";

// Binary operations
i = i + 1 - 1 * 1 / 1 % 1 & 1 | 1 ^ 1 << 2 >> 1 >>> 1;
l = l + 1 - 1 * 1 / 1 % 1 & 1 | 1 ^ 1 << 2 >> 1 >>> 1;
f = f + 1 - 1 * 1 / 1 % 1 & 1 | 1 ^ 1 << 2 >> 1 >>> 1;
d = d + 1 - 1 * 1 / 1 % 1 & 1 | 1 ^ 1 << 2 >> 1 >>> 1;
bo = true == false != true && false || true;
bo = d == f != i && bo || false;
bo = i < d || i <= d || f >= d || f > d;

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

// Method call
System.out.printf("%s %s\n", "Hello", "world");

// New keyword
String string = new String("Hello");
int[] staticArray = new int[1];
int[] dynamicArray = new int[]{1, 2};
String[] stringStaticArray = new String[1];
String[] stringDynamicArray = new String[]{"1", "2"};

// If statement
int n;

if (true) {
    n = 1;
}

if (true) {
    n = 1;
} else if (false) {
    n = 2;
}

if (true) {
    n = 1;
} else if (false) {
    n = 2;
} else {
    n = 3;
}

// While statement
n = 10;
while (n > 0) {
    n--;
}

n = 10;
while (n > 0) n--;

// Do statement
n = 10;
do {
    n--;
} while (n > 0);

n = 10;
do n--;
while (n > 0);

// For statement
int j;
for (j = 1; j < 10; j++) {
    break;
}
for (int k = 0; k < 10; k++) break;

for (; j < 10; j++) break;
for (int k = 0; ; k++) break;
for (int k = 0; k < 10; ) break;
for (int k = 0; ; ) break;
for (; ; j++) break;
for (; j < 10;) break;
