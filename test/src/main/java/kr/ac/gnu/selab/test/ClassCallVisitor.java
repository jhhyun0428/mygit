package kr.ac.gnu.selab.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;

public class ClassCallVisitor extends VoidVisitorAdapter<Void> {
	
	static List<String> file_find_paths ;
	static String Caller = ""; // caller name will be set later
	
	MethodParser methodParser ;
	
	ClassCallVisitor(MethodParser methodParser, String path) {
		this.methodParser = methodParser;
		file_find_paths = this.methodParser.findJavaFiles(path);
	}

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
			String Caller_f = methodParser.buildJavaFileName(Caller);
			System.out.println(Caller_f + " , " + methodParser.findFilePath(file_find_paths, Caller_f));
			String Callee_f = methodParser.buildJavaFileName(qualifiedName);
			System.out.println(Callee_f+ " , " + methodParser.findFilePath(file_find_paths, Callee_f));

		} catch (Exception e) {
			//                System.out.println("==> Could not resolve method call: " + n);
		}

		super.visit(n, arg);
	}
}

