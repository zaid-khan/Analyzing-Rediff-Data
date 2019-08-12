import csv

counter = 1

with open('datatemp.csv', 'r') as csvfile:
    reader = csv.reader(csvfile)
    #row1 = next(reader) # here you save your first line of the .csv file
    for row in reader:
        if row: # if row is not empty, write a file with this row
            filename = "datatempfolder/file_%s" % str(counter)
            with open(filename, 'w') as csvfile_out:
                writer = csv.writer(csvfile_out)
                #writer.writerow(row1) #here you write your row1 as first row of csvfile_out
                writer.writerow(row)
                counter = counter + 1