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

public class PathFinder  {
	static String Caller = ""; // will be set later
//	String Callee = ""; // will be set later
	
	static String path = "/Users/seonahlee/git/elasticsearch";
//	static String Path = "C:\\Users\\hyun\\Desktop\\elasticsearch-main";	
	
	static List<String> file_find_paths = findJavaFiles(path);
	
    public static void main(String[] args) throws Exception {
        // Setup TypeSolver to include both the current project and the external project
    	CombinedTypeSolver typeSolver = new CombinedTypeSolver(
//                new ReflectionTypeSolver()
//                new JavaParserTypeSolver(Paths.get("/Users/seonahlee/git/elasticsearch/server/src/main/java"))
//                new JavaParserTypeSolver(Paths.get("/Users/seonahlee/git/elasticsearch/server/src/main/java"))
        );
    	
    	
    	List<String> directories_test = new ArrayList<>();
    	
    	directories_test.add(Paths.get(path, "test", "framework", "src", "main", "java").toString());
    	directories_test.add(Paths.get(path, "test", "logger-usage", "src", "main", "java").toString());
    	directories_test.add(Paths.get(path, "test", "metadata-extractor", "src", "main", "java").toString());
    	directories_test.add(Paths.get(path, "test", "test-clusters", "src", "main", "java").toString());
    	directories_test.add(Paths.get(path, "test", "x-content", "src", "main", "java").toString());
    	directories_test.add(Paths.get(path, "test", "yaml-rest-runner", "src", "main", "java").toString());
    	
    	
    	for (String directory : directories_test) {
        	typeSolver.add(new JavaParserTypeSolver(Paths.get(directory)));

    	}

    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "benchmarks", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "build-conventions", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "build-tools", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "build-tools-internal", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "client", "benchmark", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "client", "client-benchmark-noop-api-plugin", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "client", "rest-high-level", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "client", "rest", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "client", "sniffer", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "cli", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "core", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "dissect", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "geo", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "build-tools", "reaper", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "grok", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "h3", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "logging", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "lz4", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "plugin-analysis-api", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "plugin-api", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "plugin-classloader", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "plugin-scanner", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "preallocate", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "secure-sm", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "ssl-config", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "tdigest", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "libs", "x-content", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "aggregations", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "analysis-common", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "apm", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "data-streams", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "health-shards-availability", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "ingest-attachment", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "ingest-common", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "ingest-geoip", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "ingest-user-agent", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "kibana", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "lang-expression", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "lang-mustache", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "lang-painless", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "legacy-geo", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "mapper-extras", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "parent-join", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "percolator", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "rank-eval", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "reindex", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "repository-azure", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "repository-gcs", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "repository-s3", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "repository-url", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "rest-root", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "runtime-fields-common", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "systemd", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "modules", "transport-netty4", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "analysis-icu", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "analysis-kuromoji", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "analysis-nori", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "analysis-phonetic", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "analysis-smartcn", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "analysis-stempel", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "analysis-ukrainian", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "discovery-azure-classic", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "discovery-ec2", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "discovery-gce", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "examples", "custom-settings", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "examples", "custom-significance-heuristic", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "examples", "custom-suggester", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "examples", "painless-whitelist", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "examples", "rescore", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "examples", "rest-handler", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "examples", "script-expert-scoring", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "examples", "security-authorization-engine", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "examples", "stable-analysis", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "mapper-annotated-text", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "mapper-murmur3", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "mapper-size", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "repository-hdfs", "src", "main", "java")));
    	typeSolver.add(new JavaParserTypeSolver(Paths.get(path, "plugins", "store-smb", "src", "main", "java")));


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
    
    public static class ClassCallVisitor extends VoidVisitorAdapter<Void> {
    	
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
                	System.out.println(Caller_f + " , " + findFilePath(file_find_paths, Caller_f));
                	String Callee_f = buildJavaFileName(qualifiedName);
                	System.out.println(Callee_f+ " , " +findFilePath(file_find_paths, Callee_f));
                	System.out.println();
                	System.out.println();
                	
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
			System.out.print
			("");
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
    
	public static String findFilePath(List<String> filePaths, String fileName) {
        for (String filePath : filePaths) {
            if (filePath.endsWith(fileName)) {
                return filePath;
            }
        }
        return null; // 파일을 찾지 못한 경우
    }
	
	public static String buildJavaFileName(String originalString) {
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
	
}
