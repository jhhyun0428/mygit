import re


path = "C:/Users/hyun/Desktop/elasticsearch-main/qa/logging-spi/src/javaRestTest/java/org/elasticsearch/common/logging/DynamicContextDataProviderIT.java"

def process_import_statements(java_file):
    import_statements = []
    
    # java 파일 읽기
    with open(java_file, 'r', encoding='utf-8') as file:
        lines = file.readlines()
        
        # 각 줄을 확인하며 import 문을 추출
        for line in lines:
            match = re.match(r'import\s+(org\.elasticsearch\.[\w\.]+);', line)
            if match:
                import_statement = match.group(1)
                # '.'을 '\\'로 바꾸고 마지막 단어 뒤에 '.java'를 붙임
                modified_import = import_statement.replace('.', '\\') + '.java'
                import_statements.append(modified_import)
    
    return import_statements
