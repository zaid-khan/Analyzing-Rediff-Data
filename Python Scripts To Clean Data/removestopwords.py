import nltk
import string
import re
from nltk.corpus import stopwords 

nltk.download('stopwords')
#word_tokenize accepts a string as an input, not a file. 
stop_words = set(stopwords.words('english')) 
file1 = open(r"C:\Users\BDACC-02\Desktop\DataExtracted\outputextractedlatest.csv", encoding="utf-8") 

#line = file1.read()# Use this to read file content as a stream: 
#words = line.split() 
#for r in words: 
#    if not r in stop_words: 
#        appendFile = open(r"C:\Users\BDACC-02\Desktop\filtereddata.csv",'a') 
#        appendFile.write(" "+r) 
#        appendFile.close()
        
appendFile = open(r"C:\Users\BDACC-02\Desktop\DataExtracted\filtereddatanew.csv",'a', encoding="utf-8") 
for line in file1:
    words = line.split()
    templist = []
    for r in words: 
        if not r in stop_words: 
            templist.append(r)
    lineoutput = ' '.join(map(str, templist))
    lineoutput = re.sub(r'[^\w\s]',' ',lineoutput)
    appendFile.write(lineoutput + "\n")
appendFile.close()    