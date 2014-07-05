package com.scorpio4.vendor.sesame.fn
import com.scorpio4.test.Scorpio4TestCase
import com.scorpio4.vocab.COMMON
import org.openrdf.model.ValueFactory
import org.openrdf.model.impl.ValueFactoryImpl
/**
 * Scorpio4 (c) 2012
 * User: lee
 * Date: 2/08/13
 * Time: 3:19 PM
 * 
 * This code does something useful
 */
class ContriveTest extends Scorpio4TestCase {
	ValueFactory vf = new ValueFactoryImpl();

	void testGetURI() {
		Contrive contrive = new Contrive();
		assert contrive.getURI().startsWith(COMMON.FN+"contrive");
	}

	void testEvaluateContrive() {
		Contrive contrive = new Contrive();
		String contrived = contrive._evaluate("urn:example:", vf.createLiteral("123"));
		assert contrived == "urn:example:da39a3ee5e6b4b0d3255bfef95601890afd80709";
	}

//	void testEvaluateTimed() {
//		Contrive contrive = new Contrive();
//		def stopwatch = new Stopwatch();
//		int count = 1000000;
//		for(int i=0;i<count;i++) {
//			contrive._evaluate("urn:example:", vf.createLiteral(i));
//		}
//		double avgTime = stopwatch.getTotalTime()/count;
//		println "Contrived ${count} URIs in: ${stopwatch} -> ${avgTime}ms each";
//	}
}
