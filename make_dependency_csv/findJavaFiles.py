import os

def find_all_java_files(directory):
    java_files = []
    
    # os.walk를 사용하여 디렉토리와 하위 디렉토리를 순회
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith(".java"):
                java_files.append(os.path.join(root, file))
    
    return java_files

def find_test_files (folder_path):
    test_files = set()

    def search_files(folder):
        for root, dirs, files in os.walk(folder):
            for file in files:
                if file.endswith(".java"):
                    file_path = os.path.join(root, file)
                    # 파일 이름 또는 경로에 'Test' 또는 'test'가 들어가는 경우
                    if 'Test' in file or 'test' in root or 'Test' in root:
                        test_files.add(file_path)

    search_files(folder_path)
    return list(test_files)

def find_matching_paths(paths, partial_path):
    matching_paths = [path for path in paths if partial_path in path]

    if matching_paths:
        return matching_paths[0]
