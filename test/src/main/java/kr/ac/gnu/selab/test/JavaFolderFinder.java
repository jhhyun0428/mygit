package kr.ac.gnu.selab.test;

import java.io.File;
import java.util.Set;

public class JavaFolderFinder {
	
	public void findJavaFolders(File directory, Set<String> javaPaths) {
		File[] files = directory.listFiles();
		if (files == null) {
			return;
		}

		for (File file : files) {
			if (file.isDirectory()) {
				findJavaFolders(file, javaPaths); // recursive
			} else if (file.getName().endsWith(".java")) {
				String pathToJavaFolder = this.findNearestJavaFolder(file.getParentFile());
				if (pathToJavaFolder != null) {
					javaPaths.add(pathToJavaFolder); // add
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

}
