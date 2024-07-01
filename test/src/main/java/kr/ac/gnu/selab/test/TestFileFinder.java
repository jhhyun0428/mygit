package kr.ac.gnu.selab.test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.resolution.UnsolvedSymbolException;

public class TestFileFinder {
	
	String path;
	
	TestFileFinder(String path) {
		this.path = path;
	}
	
	
	public void analyzeTestFiles() {
		List<String> test_directories = new ArrayList<>();
		test_directories.add(Paths.get(path, "test", "framework", "src", "main", "java").toString());

		for (String test_file_paths : test_directories) {
			List<String> file_paths = this.findJavaFiles(test_file_paths); // jave files???
			for (String file_path : file_paths) {
				this.findCalls(file_path);
			}
		}
	}
	
	// Now parse a file and resolve symbols
	public void findCalls(String test_path) {
		try {
			CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(test_path));
			// Use visitors or other methods to analyze the code and resolve symbols
			cu.accept(new ClassCallVisitor(this, this.path), null);
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
	
	public List<String> findJavaFiles(String folderPath) {
		List<String> javaPaths = new ArrayList<>();
		findJavaFilesInFolder(new File(folderPath), javaPaths);
		return javaPaths;
	}

	private void findJavaFilesInFolder(File directory, List<String> javaPaths) {
		// 폴더 내의 모든 파일과 하위 폴더 확인
		File[] files = directory.listFiles();
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
	

}
