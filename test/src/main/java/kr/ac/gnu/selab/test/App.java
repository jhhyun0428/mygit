package kr.ac.gnu.selab.test;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

import com.github.javaparser.ParserConfiguration.LanguageLevel;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;

/**
 * Hello world!
 *
 */
public class App 
{
   public static void main(String[] args ) throws IOException 
   {
      final Path SOURCE_ROOT_PATH = Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-8.11.3");
      List<Path> javaFiles = findJavaFiles(SOURCE_ROOT_PATH);
      
      System.out.println(SOURCE_ROOT_PATH);
      System.out.println(javaFiles.get(13000));

      
      //for (Path files : javaFiles) {
    	  
    	  try {
        	  traceMethod(SOURCE_ROOT_PATH, javaFiles.get(13000));
          } catch (UnsolvedSymbolException e) {
        	  System.out.println("실패함");
          }
    	  
    //  }
      
      
            
      
   }
   
    private static List<Path> findJavaFiles(Path sourceRootPath) {
//        return Files.walk(sourceRootPath)
//                .filter(Files::isRegularFile)
//                .filter(file -> file.toString().endsWith(".java"))
//                .collect(Collectors.toList());
    	return null;
    }
   
   public static void traceMethod (Path sourcepath, Path targetpath) throws IOException {
      JavaSymbolSolver javaSymbolSolver = new JavaSymbolSolver(new JavaParserTypeSolver(sourcepath));
      StaticJavaParser.getParserConfiguration().setSymbolResolver(javaSymbolSolver);


      CompilationUnit cu 
      = StaticJavaParser.parse(targetpath);
      
      StaticJavaParser.getParserConfiguration().setLanguageLevel(LanguageLevel.JAVA_12);
      
//      Optional<ClassOrInterfaceDeclaration> classA
//      = cu.getClassByName("A");

//      cu.findAll(FieldDeclaration.class)
//      .stream()
//      .filter(f -> f.isPublic() && !f.isStatic())
//      .forEach(f -> System.out.println("Check field at line " +
//            f.getRange().map(r -> r.begin.line).orElse(-1)));

      MethodVisitor visitor = new MethodVisitor();


      List<MethodDeclaration> methodDeclarationList = new ArrayList<>();
      visitor.visit(cu, methodDeclarationList);

//      methodDeclarationList.forEach(md -> {
//         List<MethodCallExpr> methodCallExprList = md.findAll(MethodCallExpr.class);
//         methodCallExprList.forEach(methodCallExpr -> {
//            System.out.println(md.resolve().getQualifiedSignature() + ", " 
//                  + methodCallExpr.resolve().getQualifiedSignature());
//         });
//      });

   }
}