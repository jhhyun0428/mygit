package kr.ac.gnu.selab.test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;

public class App01 {

	public static void main(String[] args) throws IOException {
		final Path SOURCE_ROOT_PATH = Path.of("/Users/seonahlee/git/elasticsearch/test/framework/src/main/java/org/elasticsearch/search");
		final Path TARGET_PATH =      Path.of("/Users/seonahlee/git/elasticsearch/test/framework/src/test/java/org/elasticsearch/search/MockSearchServiceTests.java");

		JavaSymbolSolver javaSymbolSolver = new JavaSymbolSolver(new JavaParserTypeSolver(SOURCE_ROOT_PATH));
		StaticJavaParser.getParserConfiguration().setSymbolResolver(javaSymbolSolver);


		CompilationUnit cu 
		= StaticJavaParser.parse(TARGET_PATH);
		Optional<ClassOrInterfaceDeclaration> classA
		= cu.getClassByName("A");

		//	      cu.findAll(FieldDeclaration.class)
		//	      .stream()
		//	      .filter(f -> f.isPublic() && !f.isStatic())
		//	      .forEach(f -> System.out.println("Check field at line " +
		//	            f.getRange().map(r -> r.begin.line).orElse(-1)));

		MethodVisitor visitor = new MethodVisitor();


		List<MethodDeclaration> methodDeclarationList = new ArrayList<>();
		visitor.visit(cu, methodDeclarationList);

		methodDeclarationList.forEach(md -> {
			List<MethodCallExpr> methodCallExprList = md.findAll(MethodCallExpr.class);
			methodCallExprList.forEach(methodCallExpr -> {
				try {
					if (md.resolve() == null) {
						return;
					}
					if (methodCallExpr.resolve() == null) {
						return;
					}

				}
				catch (UnsolvedSymbolException e) {
					;
				}
				try {
					System.out.println(md.resolve().getQualifiedSignature() + ", " 
							+ methodCallExpr.resolve().getQualifiedSignature() );
				}
				catch (UnsolvedSymbolException e) {
					;
				}
			});
		});
	}

}
