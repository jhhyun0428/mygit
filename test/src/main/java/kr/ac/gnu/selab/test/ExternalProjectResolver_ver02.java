package kr.ac.gnu.selab.test;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
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
    	
    	
    	List<String> directories_test = new ArrayList<>();
    	
    	directories_test.add("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\test\\framework\\src\\main\\java");
    	directories_test.add("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\test\\logger-usage\\src\\main\\java");
    	directories_test.add("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\test\\metadata-extractor\\src\\main\\java");
    	directories_test.add("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\test\\test-clusters\\src\\main\\java");
    	directories_test.add("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\test\\x-content\\src\\main\\java");
    	directories_test.add("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\test\\yaml-rest-runner\\src\\main\\java");
    	
    	
    	for (String directory : directories_test) {
        	typeSolver.add(new JavaParserTypeSolver(Paths.get(directory)));

    	}

    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\benchmarks\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\build-conventions\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\build-tools\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\build-tools-internal\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\client\\benchmark\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\client\\client-benchmark-noop-api-plugin\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\client\\rest-high-level\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\client\\rest\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\client\\sniffer\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\cli\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\core\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\dissect\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\geo\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\build-tools\\reaper\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\grok\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\h3\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\logging\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\lz4\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\plugin-analysis-api\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\plugin-api\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\plugin-classloader\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\plugin-scanner\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\preallocate\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\secure-sm\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\ssl-config\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\tdigest\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\libs\\x-content\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\aggregations\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\analysis-common\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\apm\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\data-streams\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\health-shards-availability\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\ingest-attachment\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\ingest-common\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\ingest-geoip\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\ingest-user-agent\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\kibana\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\lang-expression\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\lang-mustache\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\lang-painless\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\legacy-geo\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\mapper-extras\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\parent-join\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\percolator\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\rank-eval\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\reindex\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\repository-azure\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\repository-gcs\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\repository-s3\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\repository-url\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\rest-root\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\runtime-fields-common\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\systemd\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\modules\\transport-netty4\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\analysis-icu\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\analysis-kuromoji\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\analysis-nori\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\analysis-phonetic\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\analysis-smartcn\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\analysis-stempel\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\analysis-ukrainian\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\discovery-azure-classic\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\discovery-ec2\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\discovery-gce\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\examples\\custom-settings\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\examples\\custom-significance-heuristic\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\examples\\custom-suggester\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\examples\\painless-whitelist\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\examples\\rescore\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\examples\\rest-handler\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\examples\\script-expert-scoring\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\examples\\security-authorization-engine\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\examples\\stable-analysis\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\mapper-annotated-text\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\mapper-murmur3\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\mapper-size\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\repository-hdfs\\src\\main\\java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\plugins\\store-smb\\src\\main\\java")));





        // Configure JavaParser to use the TypeSolver
        StaticJavaParser.getParserConfiguration().setSymbolResolver(new JavaSymbolSolver(typeSolver));

        
        for (String path_d : directories_test) {
        	
        	List<String> file_paths = findJavaFiles(path_d);
        	
        	
        	for (String fp : file_paths) {
//       		System.out.println(fp);
        		finder(fp);
        	}
        	
        }
        
//        finder("C:\\Users\\hyun\\Desktop\\elasticsearch-8.11.3\\test\\framework\\src\\test\\java\\org\\elasticsearch\\search\\MockSearchServiceTests.java");
//        finder("C:\\Users\\hyun\\Desktop\\elasticsearch-main\\build-tools-internal\\src\\test\\java\\org\\elasticsearch\\gradle\\internal\\ConcatFilesTaskTests.java");

        
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
            } catch (Exception e) {
//                System.out.println("==> Could not resolve method call: " + n);
            }
            
            super.visit(n, arg);
        }
    }
    
    public static void finder(String path_s) {
    	
    	
        // Now parse a file and resolve symbols
        try {
			CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(path_s));

	        // Use visitors or other methods to analyze the code and resolve symbols
	        cu.accept(new ClassCallVisitor(), null);
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(UnsolvedSymbolException e) {
			System.out.print("");
		} catch (ParseProblemException e) {
			System.out.print("");
		} catch (IllegalArgumentException e) {
			System.out.print("");
		}
    }
    
    public static List<String> findJavaFiles(String folderPath) {
        List<String> javaFiles = new ArrayList<>();
        findJavaFilesInFolder(new File(folderPath), javaFiles);
        return javaFiles;
    }

    private static void findJavaFilesInFolder(File folder, List<String> javaFiles) {
        // 폴더 내의 모든 파일과 하위 폴더 확인
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 하위 폴더에 대해 재귀적으로 호출
                    findJavaFilesInFolder(file, javaFiles);
                } else {
                    // Java 파일인 경우 리스트에 추가
                    if (file.getName().endsWith(".java")) {
                        javaFiles.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }
    
}
