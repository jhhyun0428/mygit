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
	
	static List<String> file_paths ;
	static String Caller = ""; // caller name will be set later
	
	ClassCallVisitor(TestFileFinder fileFinder, String path) {
		file_paths = fileFinder.findJavaFiles(path);
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
			String Caller_f = buildJavaFileName(Caller);
			System.out.println(Caller_f + " , " + findFilePath(file_paths, Caller_f));
			String Callee_f = buildJavaFileName(qualifiedName);
			System.out.println(Callee_f+ " , " + findFilePath(file_paths, Callee_f));

		} catch (Exception e) {
			//                System.out.println("==> Could not resolve method call: " + n);
		}

		super.visit(n, arg);
	}
	
	public String buildJavaFileName(String originalString) {
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
	
	public String findFilePath(List<String> filePaths, String fileName) {
		for (String filePath : filePaths) {
			if (filePath.endsWith(fileName)) {
				return filePath;
			}
		}
		return null; // 파일을 찾지 못한 경우
	}
}

