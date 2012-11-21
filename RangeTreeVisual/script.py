n = 1000
m = 1000

with open('input.txt', 'w') as f:
    f.write("{0}\n".format(n * n))
    for y in range(n):
        for x in range(n):
            f.write('({0},{1})\n'.format(x, y))

    f.write("{0}\n".format(m))
    for i in range(m):
        f.write('(0,1000,1000,998)\n')