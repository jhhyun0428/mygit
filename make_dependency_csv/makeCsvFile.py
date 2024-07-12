import sys
from pathlib import Path
import os
import re

import findJavaFiles
import analyzeJavaFiles

def get_desktop_path():
    # Windows 환경
    if os.name == 'nt':
        desktop_path = os.path.join(os.path.join(os.environ['USERPROFILE']), 'Desktop')
    # macOS/Linux 환경
    else:
        desktop_path = os.path.join(os.path.join(os.path.expanduser('~')), 'Desktop')
    
    return desktop_path

def remove_base_path(full_path, base_path):
    if full_path.startswith(base_path):
        retun_path =  "\\" + full_path[len(base_path):].lstrip("\\/")
    return retun_path.replace("\\", "/")

def write_in_file(file_path, text):
    original_stdout = sys.stdout  
    with open(file_path, 'a', encoding='utf-8') as file:
        file.write(text + '\n')

def get_filename(full_path):
    # \ 또는 /를 기준으로 경로를 분할
    parts = full_path.split('\\')
    # 분할된 리스트의 마지막 요소를 반환
    return parts[-1]

def contains_none(lst):
    return None in lst



path = get_desktop_path() + "\\elasticsearch-main"
csv_path = get_desktop_path() + "\\dependency_result.csv"

java_list = findJavaFiles.find_all_java_files(path)
java_test_list = findJavaFiles.find_test_files(path)

print(contains_none(java_list)) 
print(contains_none(java_test_list)) 

i = 0


for test_file in java_test_list:
    if test_file:
        test_import_list = analyzeJavaFiles.process_import_statements(test_file)

        if test_import_list:
            for import_file in test_import_list:
                source_file_path = findJavaFiles.find_matching_paths(java_list, import_file)
                if source_file_path:
                    data = get_filename(test_file) + "," + get_filename(source_file_path) + "," + remove_base_path(test_file, path) + "," + remove_base_path(source_file_path, path)
                    #write_in_file(csv_path, data)

                    i += 1
                    if i%100 == 0:
                        print(i)