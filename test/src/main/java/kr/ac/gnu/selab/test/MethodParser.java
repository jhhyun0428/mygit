package kr.ac.gnu.selab.test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

  //

public class MethodParser {
	
	static String Caller = ""; // caller name will be set later
	
	static String Path = "/Users/seonahlee/git/elasticsearch";
//	static String Path = "C:\\Users\\hyun\\Desktop\\elasticsearch-main\\";	
	
	static boolean Os = true; // false: windows, true: Mac
	
	static List<String> file_find_paths = findJavaFiles(Path);
	
	//static List<String> file_find_paths = findJavaFiles("C:\\Users\\hyun\\Desktop\\elasticsearch-main");
	
    public static void main(String[] args) {

    	String startDirectory = Path;  // 시작할 디렉토리 경로 입력
//    	String startDirectory = "C:\\Users\\hyun\\Desktop\\elasticsearch-main";  // 시작할 디렉토리 경로 입력
        Set<String> javaPaths = new HashSet<>();
        findJavaFiles(new File(startDirectory), javaPaths);
        
        CombinedTypeSolver typeSolver = new CombinedTypeSolver();
        typeSolver.add(new ReflectionTypeSolver());
        
        // 결과 출력
        List<String> sortedPaths = new ArrayList<>(javaPaths);
        sortedPaths.sort(String::compareTo);
        for (String path : sortedPaths) {
        	typeSolver.add(new JavaParserTypeSolver(Paths.get(path)));
        }
        
    	List<String> directories_test = new ArrayList<>();
    	
    	directories_test.add(Path + File.separator + "test" + File.separator + "framework" + File.separator + "src" + File.separator + "main" + File.separator + "java");
    	
//    	directories_test.add("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\test\\framework\\src\\main\\java");

        StaticJavaParser.getParserConfiguration().setSymbolResolver(new JavaSymbolSolver(typeSolver));

        
        for (String path_d : directories_test) {
        	
        	List<String> file_paths = findJavaFiles(path_d);
        	
        	
        	for (String fp : file_paths) {
//       		System.out.println(fp);
        		finder(fp);
        	}
        	
        }
    	/*
    	List<String> method_list = new ArrayList<>();
    	    	

        String filePath = "C:\\Users\\hyun\\Desktop\\elasticsearch-8.11.3\\build-tools\\src\\main\\java\\org\\elasticsearch\\gradle\\AbstractLazyPropertyCollection.java"; // 여기에 분석하고 싶은 파일의 경로를 지정하세요.

        
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
        }*/

    }

    private static void findJavaFiles(File directory, Set<String> javaPaths) {
        File[] files = directory.listFiles();

        if (files == null) return;  // 파일이나 디렉토리가 없는 경우

        for (File file : files) {
            if (file.isDirectory()) {
                findJavaFiles(file, javaPaths);
            } else if (file.getName().endsWith(".java")) {
                String pathToJavaFolder = findNearestJavaFolder(file.getParentFile());
                if (pathToJavaFolder != null) {
                    javaPaths.add(pathToJavaFolder);
                }
            }
        }
    }

    private static String findNearestJavaFolder(File directory) {
        while (directory != null) {
            if (directory.getName().equalsIgnoreCase("java")) {
                return directory.getPath();
            }
            directory = directory.getParentFile();
        }
        return null;  // "java" 폴더를 찾지 못함
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

    private static class ClassCallVisitor extends VoidVisitorAdapter<Void> {
    	
        @Override
        public void visit(MethodDeclaration n, Void arg) {
//            System.out.println("Method Name: " + n.resolve().getQualifiedSignature());
            Caller = n.resolve().getQualifiedSignature();
            super.visit(n, arg);
        }
        
        @Override
        public void visit(ObjectCreationExpr n, Void arg) {
            //System.out.println("Class Instance Created: " + n.getType().getName());
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodCallExpr n, Void arg) {
//            n.getScope().ifPresent(scope -> System.out.println("Method Call: " + scope + "." + n.getName()));
            
            try {
                String qualifiedName = n.resolve().getQualifiedSignature();
//                if (!qualifiedName.contains("java.lang"))
                	System.out.println(Caller + ", " + qualifiedName);
                	String Caller_f = buildJavaFileName(Caller);
                	System.out.println(Caller_f + " , " + findFilePath(file_find_paths, Caller_f));
                	String Callee_f = buildJavaFileName(qualifiedName);
                	System.out.println(Callee_f+ " , " +findFilePath(file_find_paths, Callee_f));
                	
            } catch (Exception e) {
//                System.out.println("==> Could not resolve method call: " + n);
            }
            
            super.visit(n, arg);
        }
    }
    
    public static void finder(String path_s) {
    	
    	
        // Now parse a file and resolve symbols
        try {
			CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(path_s));

	        // Use visitors or other methods to analyze the code and resolve symbols
	        cu.accept(new ClassCallVisitor(), null);
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(UnsolvedSymbolException e) {
			System.out.println("error");
		} catch (ParseProblemException e) {
			System.out.println("error");
		} catch (IllegalArgumentException e) {
			System.out.println("error");
		}
    }
    
    public static List<String> findJavaFiles(String folderPath) {
        List<String> javaFiles = new ArrayList<>();
        findJavaFilesInFolder(new File(folderPath), javaFiles);
        return javaFiles;
    }

    private static void findJavaFilesInFolder(File folder, List<String> javaFiles) {
        // 폴더 내의 모든 파일과 하위 폴더 확인
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 하위 폴더에 대해 재귀적으로 호출
                    findJavaFilesInFolder(file, javaFiles);
                } else {
                    // Java 파일인 경우 리스트에 추가
                    if (file.getName().endsWith(".java")) {
                        javaFiles.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }
    
	public static String findFilePath(List<String> filePaths, String fileName) {
        for (String filePath : filePaths) {
            if (filePath.endsWith(fileName)) {
                return filePath;
            }
        }
        return null; // 파일을 찾지 못한 경우
    }
	
	public static String buildJavaFileName(String originalString) {
        // 문자열을 '.'을 기준으로 분할
		String regex = "\\([^)]*\\)";
		String stringWithoutParentheses = originalString.replaceAll(regex, "");
		String[] parts = stringWithoutParentheses.split("\\.");

        // 끝에서 두 번째 단어 뒤에 ".java"를 붙여서 반환
        if (parts.length >= 2) {
            StringBuilder javaFileNameBuilder = new StringBuilder();
            javaFileNameBuilder.append(parts[parts.length - 2]);
            javaFileNameBuilder.append(".java");
            return javaFileNameBuilder.toString();
        } else {
            // 분할된 단어의 개수가 2보다 작을 경우 예외 처리
            throw new IllegalArgumentException("Invalid input string");
        }
    }
}
