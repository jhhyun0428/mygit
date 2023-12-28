from prettytable import PrettyTable
 
 
# open csv file
f = open("data.csv", 'r', encoding= "UTF-8")
 
# read the csv file
f = f.readlines()
 
# headers for table
t = PrettyTable()
t.field_names = ["Pull 여부", "Issue Number", "Source Code"]
 
# Adding the data
for i in range(0, 10):
    t.add_row(f[i].split(','))
    

#t.format = True
t.align = "c"
t.align["Source Code"] = "l"
t.padding_width = 1.5
#t.border = True

code = t.get_html_string(attributes={"name":"code_table", "border":"1", "cellspacing":"0"})
html_file = open('Table.html', 'w')
html_file = html_file.write(code)