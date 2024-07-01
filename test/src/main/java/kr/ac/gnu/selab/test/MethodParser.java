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
	//		static String path = "C:\\Users\\hyun\\Desktop\\elasticsearch-main";	

	public static void main(String[] args) {

		MethodParser methodParser = new MethodParser();
		JavaFolderFinder javaFolderFinder = new JavaFolderFinder(path);
		TestFileFinder testFileFinder = new TestFileFinder(path);


		//add source directory to CombinedTypeSolver
		javaFolderFinder.analyzeJavaFolders();		

		//find and add test files
		testFileFinder.analyzeTestFiles();

	}
}
