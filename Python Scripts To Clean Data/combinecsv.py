fout=open(r"C:\Users\BDACC-02\Desktop\Bigdata\Alldata\out12345.csv","a",encoding="utf8")
# first file:
for line in open(r"C:\Users\BDACC-02\Desktop\Bigdata\Alldata\sh1.csv",encoding="utf8"):
    fout.write(line)
# now the rest:    
for num in range(2,5):
    f = open("C:\\Users\\BDACC-02\\Desktop\\Bigdata\\Alldata\\sh"+str(num)+".csv", encoding="utf8")
    for line in f:
         fout.write(line)
    f.close() # not really needed
fout.close()
