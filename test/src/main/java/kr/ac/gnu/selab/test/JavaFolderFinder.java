package kr.ac.gnu.selab.test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class JavaFolderFinder {
	
	String path;
	
	JavaFolderFinder(String path) {
		this.path = path;
	}
	
	public void analyzeJavaFolders() {
		Set<String> javaPaths = new HashSet<>();		
		String startPath = path;
		this.findJavaFolders(new File(startPath), javaPaths); // javaPath

		// 결과 출력
		List<String> sortedPaths = new ArrayList<>(javaPaths); // javaPath, again
		sortedPaths.sort(String::compareTo);

		//add source directory to CombinedTypeSolver
		CombinedTypeSolver typeSolver = new CombinedTypeSolver();
		typeSolver.add(new ReflectionTypeSolver());
		for (String path : sortedPaths) {
			typeSolver.add(new JavaParserTypeSolver(Paths.get(path)));
		}
		StaticJavaParser.getParserConfiguration().setSymbolResolver(new JavaSymbolSolver(typeSolver));
	}
	
	public void findJavaFolders(File directory, Set<String> javaPaths) {
		File[] files = directory.listFiles();
		if (files == null) {
			return;
		}

		for (File file : files) {
			if (file.isDirectory()) {
				findJavaFolders(file, javaPaths); // recursive
			} else if (file.getName().endsWith(".java")) {
//				System.out.println(file.getName());
				
				String pathToJavaFolder = this.findNearestJavaFolder(file.getParentFile());
				
				if (pathToJavaFolder != null) {
					javaPaths.add(pathToJavaFolder); // add
//					System.out.println(pathToJavaFolder.toString());
					return;
				}
//				else {
//					System.out.println("null");
//					return;
//				}
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

}
