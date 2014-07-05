package com.scorpio4.demo
import com.scorpio4.test.Scorpio4TestCase
import com.scorpio4.vendor.spring.RDFBeanDefinitionReader
/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.demo
 * User  : lee
 * Date  : 3/07/2014
 * Time  : 12:51 PM
 *
 *
 */
public class Example1_1Test extends Scorpio4TestCase {

	public void testInnerOuterBeans() {
		provision("classpath:scorpio4/bean/Example1_1.n3");

		RDFBeanDefinitionReader springyBeans = new RDFBeanDefinitionReader(connection, applicationContext);

		def innerBeanName = "bean:com.scorpio4.demo.Example1_1";
		def outerBeanName = "urn:scorpio4demo:Example1_1";

		springyBeans.loadBeanDefinitions(innerBeanName);
		def innerBean = springyBeans.getBean(innerBeanName);
		assert innerBean!=null;
		println "INNER: ${innerBean}"

		springyBeans.loadBeanDefinitions(outerBeanName);
		def beanDef = springyBeans.getBeanFactory().getBeanDefinition(outerBeanName);
		assert beanDef!=null;

		def outerBean = springyBeans.getBean(outerBeanName);
		println "OUTER: ${outerBean}"
		assert outerBean!=null;
		assert "Hello World @ Example1_1" == outerBean.toString()

		finalize();
	}
}
