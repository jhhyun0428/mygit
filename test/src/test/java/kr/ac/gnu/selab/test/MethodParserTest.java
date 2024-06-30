package kr.ac.gnu.selab.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class MethodParserTest {

	@Test
	void testFindJavaFiles() {
		
		String path = "/Users/seonahlee/git/elasticsearch";
		String pathAfter = "/plugins/mapper-size/src/internalClusterTest/java/org/elasticsearch/index/mapper/size";
		
//		String path = "C:\\Users\\user\\Desktop\\elasticsearch-main";
//		String pathAfter = "\\plugins\\mapper-size\\src\\internalClusterTest\\java\\org\\elasticsearch\\index\\mapper\\size";
		
		MethodParser methodParser = new MethodParser();
		List<String> testJavaFolder = new ArrayList<>();
		testJavaFolder = methodParser.findJavaFiles(path+pathAfter);
		
		assertEquals(1, testJavaFolder.size());//폴더 안에 파일 하나.
	}

}
