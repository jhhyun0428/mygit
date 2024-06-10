package kr.ac.gnu.selab.test;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ExternalProjectResolver {
	static String Caller = "";
//	String Callee = "";
	
    public static void main(String[] args) throws Exception {
        // Setup TypeSolver to include both the current project and the external project
    	CombinedTypeSolver typeSolver = new CombinedTypeSolver(
//                new ReflectionTypeSolver()
//                new JavaParserTypeSolver(Paths.get("/Users/seonahlee/git/elasticsearch/server/src/main/java"))
//                new JavaParserTypeSolver(Paths.get("/Users/seonahlee/git/elasticsearch/server/src/main/java"))
        );
    	
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("/Users/seonahlee/git/elasticsearch/test/framework/src/main/java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("/Users/seonahlee/git/elasticsearch/test/logger-usage/src/main/java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("/Users/seonahlee/git/elasticsearch/test/metadata-extractor/src/main/java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("/Users/seonahlee/git/elasticsearch/test/test-clusters/src/main/java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("/Users/seonahlee/git/elasticsearch/test/x-content/src/main/java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("/Users/seonahlee/git/elasticsearch/test/yaml-rest-runner/src/main/java")));

    	typeSolver.add(new JavaParserTypeSolver(Paths.get("/Users/seonahlee/git/elasticsearch/benchmarks/src/main/java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("/Users/seonahlee/git/elasticsearch/build-conventions/src/main/java")));
//    	typeSolver.add(new JavaParserTypeSolver(Paths.get("/Users/seonahlee/git/elasticsearch/server/src/main/java")));    	

        // Configure JavaParser to use the TypeSolver
        StaticJavaParser.getParserConfiguration().setSymbolResolver(new JavaSymbolSolver(typeSolver));

        // Now parse a file and resolve symbols
        try {
			CompilationUnit cu = StaticJavaParser.parse(new FileInputStream("/Users/seonahlee/git/elasticsearch/test/framework/src/test/java/org/elasticsearch/search/MockSearchServiceTests.java"));

	        // Use visitors or other methods to analyze the code and resolve symbols
	        cu.accept(new ClassCallVisitor(), null);
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
            System.out.println("Class Instance Created: " + n.getType().getName());
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodCallExpr n, Void arg) {
//            n.getScope().ifPresent(scope -> System.out.println("Method Call: " + scope + "." + n.getName()));
            
            try {
                String qualifiedName = n.resolve().getQualifiedSignature();
//                if (!qualifiedName.contains("java.lang"))
                	System.out.println(Caller + ", " + qualifiedName);
            } catch (Exception e) {
//                System.out.println("==> Could not resolve method call: " + n);
            }
            
            super.visit(n, arg);
        }
    }
}
