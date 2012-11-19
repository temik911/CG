n = 500
m = 1000

with open('input.txt', 'w') as f:
    f.write("{0}\n".format(n * n))
    for x in range(n):
        for y in range(n):
            f.write('({0},{1})\n'.format(x, y))
    f.write("{0}\n".format(m))
    for i in range(m):
        f.write('(0,0,3,3)\n')