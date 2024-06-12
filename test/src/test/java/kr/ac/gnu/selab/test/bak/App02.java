package kr.ac.gnu.selab.test.bak;


import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;

import kr.ac.gnu.selab.test.MethodVisitor;

import com.github.javaparser.symbolsolver.JavaSymbolSolver;

/**
 * Hello world!
 *
 */
public class App02
{
	public static void main(String[] args ) throws IOException 
	{
		final Path SOURCE_ROOT_PATH = Path.of(System.getProperty("user.dir"), "src", "main", "java");
		final Path TARGET_PATH = Path.of(System.getProperty("user.dir"),"src","main", "java", "Target.java");
		System.out.println(TARGET_PATH);

		JavaSymbolSolver javaSymbolSolver = new JavaSymbolSolver(new JavaParserTypeSolver(SOURCE_ROOT_PATH));
		StaticJavaParser.getParserConfiguration().setSymbolResolver(javaSymbolSolver);


		CompilationUnit cu 
		= StaticJavaParser.parse(TARGET_PATH);
		Optional<ClassOrInterfaceDeclaration> classA
		= cu.getClassByName("A");

//		cu.findAll(FieldDeclaration.class)
//		.stream()
//		.filter(f -> f.isPublic() && !f.isStatic())
//		.forEach(f -> System.out.println("Check field at line " +
//				f.getRange().map(r -> r.begin.line).orElse(-1)));

		MethodVisitor visitor = new MethodVisitor();


		List<MethodDeclaration> methodDeclarationList = new ArrayList<>();
		visitor.visit(cu, methodDeclarationList);

		methodDeclarationList.forEach(md -> {
			List<MethodCallExpr> methodCallExprList = md.findAll(MethodCallExpr.class);
			methodCallExprList.forEach(methodCallExpr -> {
				System.out.println(md.resolve().getQualifiedSignature() + ", " 
						+ methodCallExpr.resolve().getQualifiedSignature());
			});
		});
	}
}
