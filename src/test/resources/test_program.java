double arr = new double[10];
for (int i = 0; i < arr.length; i++) arr[i] = ++i * 2;

for (double a : arr) {
    int index = (int) (a % arr.length);
    arr[index] /= 2;

    if (arr[index] == 0) arr[index]--;
}

int i = 0;
while (i < arr.length) {
    if (arr[i] == 0) throw new RuntimeException("Invalid value arr[" + i + "]");
    i++;
}

// Custom class that should be provided by tests
String output = new it.fulminazzo.mojito.parser.Printer().print(arr);

return output