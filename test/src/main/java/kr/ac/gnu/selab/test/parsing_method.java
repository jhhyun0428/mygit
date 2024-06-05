package kr.ac.gnu.selab.test;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

  //

public class parsing_method {
    public static void main(String[] args) {
    	
    	    	List<String> method_list = new ArrayList<>();

        String filePath = "C:\\Users\\user\\Desktop\\elasticsearch-main\\build-tools\\src\\main\\java\\org\\elasticsearch\\gradle\\AbstractLazyPropertyCollection.java"; // 여기에 분석하고 싶은 파일의 경로를 지정하세요.

        JavaParser parser = new JavaParser(new ParserConfiguration()
            .setSymbolResolver(new JavaSymbolSolver(new ReflectionTypeSolver())));

        try (FileInputStream in = new FileInputStream(filePath)) {
            ParseResult<CompilationUnit> result = parser.parse(in);
            if (result.isSuccessful() && result.getResult().isPresent()) {
                CompilationUnit cu = result.getResult().get();
                String packageName = cu.getPackageDeclaration().map(pd -> pd.getName().toString()).orElse("");
                cu.findAll(MethodDeclaration.class).forEach(method -> {
                    String className = findClassName(method);
                    // 전체 패스를 포함한 메소드 이름 출력
                    String m_name = packageName + "." + className + "." + method.getName().toString();
                    method_list.add(m_name);
                });
            } else {
                System.err.println("파싱에 실패했습니다.");
            }
        } catch (Exception e) {
            System.err.println("오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
        }
        
        
        for (String me_name : method_list) {
        	System.out.println(me_name);
        }
    }

    // 메소드가 선언된 클래스의 이름을 찾는 메소드
    private static String findClassName(MethodDeclaration method) {
        Node currentNode = method.getParentNode().orElse(null);
        while (currentNode != null) {
            if (currentNode instanceof ClassOrInterfaceDeclaration) {
                return ((ClassOrInterfaceDeclaration) currentNode).getNameAsString();
            }
            currentNode = currentNode.getParentNode().orElse(null);
        }
        return "";
    }
}
