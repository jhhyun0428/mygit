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


public class MethodParser {

	static String path = "/Users/seonahlee/git/elasticsearch";
	//	static String Path = "C:\\Users\\hyun\\Desktop\\elasticsearch-main";	

	public static void main(String[] args) {

		MethodParser methodParser = new MethodParser();

		Set<String> javaPaths = new HashSet<>();
		
		String startPath = path;
		
		methodParser.findJavaFiles(new File(startPath), javaPaths);

		CombinedTypeSolver typeSolver = new CombinedTypeSolver();
		typeSolver.add(new ReflectionTypeSolver());

		// 결과 출력
		List<String> sortedPaths = new ArrayList<>(javaPaths);
		sortedPaths.sort(String::compareTo);
		for (String path : sortedPaths) {
			typeSolver.add(new JavaParserTypeSolver(Paths.get(path)));
		}

		List<String> test_directories = new ArrayList<>();

		test_directories.add(Paths.get(path, "test", "framework", "src", "main", "java").toString());

		StaticJavaParser.getParserConfiguration().setSymbolResolver(new JavaSymbolSolver(typeSolver));


		for (String test_file_paths : test_directories) {
			List<String> file_paths =methodParser.findJavaFiles(test_file_paths);
			for (String file_path : file_paths) {
				methodParser.findCalls(file_path);
			}

		}

	}

	private void findJavaFiles(File directory, Set<String> javaPaths) {
		File[] files = directory.listFiles();
		if (files == null) {
			return;
		}

		for (File file : files) {
			if (file.isDirectory()) {
				findJavaFiles(file, javaPaths);
			} else if (file.getName().endsWith(".java")) {
				String pathToJavaFolder = this.findNearestJavaFolder(file.getParentFile());
				if (pathToJavaFolder != null) {
					javaPaths.add(pathToJavaFolder);
				}
			}
		}
	}

	public List<String> findJavaFiles(String folderPath) {
		List<String> javaFiles = new ArrayList<>();
		findJavaFilesInFolder(new File(folderPath), javaFiles);
		return javaFiles;
	}

	private void findJavaFilesInFolder(File folder, List<String> javaPaths) {
		// 폴더 내의 모든 파일과 하위 폴더 확인
		File[] files = folder.listFiles();
		if (files == null) {
			return;
		}

		for (File file : files) {
			if (file.isDirectory()) {
				// 하위 폴더에 대해 재귀적으로 호출
				findJavaFilesInFolder(file, javaPaths);
			} else {
				// Java 파일인 경우 리스트에 추가
				if (file.getName().endsWith(".java")) {
					javaPaths.add(file.getAbsolutePath());
				}
			}
		}

	}

	private String findNearestJavaFolder(File directory) {
		while (directory != null) {
			if (directory.getName().equalsIgnoreCase("java")) {
				return directory.getPath();
			}
			directory = directory.getParentFile();
		}
		return null;  // "java" 폴더를 찾지 못함
	}

	// Now parse a file and resolve symbols
	public void findCalls(String test_path) {
		try {
			CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(test_path));
			// Use visitors or other methods to analyze the code and resolve symbols
			cu.accept(new ClassCallVisitor(this, path), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(UnsolvedSymbolException e) {
			System.out.println("error");
		} catch (ParseProblemException e) {
			System.out.println("error");
		} catch (IllegalArgumentException e) {
			System.out.println("error");
		}
		catch (UnsupportedOperationException e) { //added an exception
			System.out.println("error");
		}
	}

}
