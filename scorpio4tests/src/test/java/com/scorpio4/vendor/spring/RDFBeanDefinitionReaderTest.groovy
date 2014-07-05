package com.scorpio4.vendor.spring

import com.scorpio4.test.Scorpio4TestCase
import groovy.transform.Field
import org.openrdf.repository.RepositoryConnection
import org.openrdf.repository.sail.SailRepository
import org.openrdf.rio.RDFFormat
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer
import org.openrdf.sail.memory.MemoryStore
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericApplicationContext

/**
 * Scorpio (c) 2014
 * Module: com.scorpio4.vendor.spring
 * User  : lee
 * Date  : 30/06/2014
 * Time  : 11:33 PM
 *
 *
 */
class RDFBeanDefinitionReaderTest extends Scorpio4TestCase {
	def HELLO_WORLD = "com/scorpio4/vendor/spring/HelloWorld.n3";

	void testRegister() {
		provision(HELLO_WORLD);
		ApplicationContext applicationContext = new GenericApplicationContext();

		RDFBeanDefinitionReader beanie = new RDFBeanDefinitionReader(connection, applicationContext);
		assert 0 < beanie.loadBeanDefinitions("bean:com.scorpio4.vendor.spring.HelloWorld");
		BeanDefinition beanDef = beanie.beanFactory.getBeanDefinition("bean:com.scorpio4.vendor.spring.HelloWorld");
		println "Registered: "+beanDef;
		finalize()
	}

	void testDefineBean() {
		provision(HELLO_WORLD);
		ApplicationContext applicationContext = new GenericApplicationContext();

		RDFBeanDefinitionReader beanie = new RDFBeanDefinitionReader(connection, applicationContext);
		BeanDefinition beanDef = beanie.defineBean("com.scorpio4.vendor.spring.HelloWorld");
		println "Defined: "+beanDef;
		finalize()
	}

	void testLoadDefinitions() {
		provision(HELLO_WORLD);
		ApplicationContext applicationContext = new GenericApplicationContext();
		RDFBeanDefinitionReader beanie = new RDFBeanDefinitionReader(connection, applicationContext);
		def loaded = beanie.loadBeanDefinitions("bean:com.scorpio4.vendor.spring.HelloWorld");
		loaded+= beanie.loadBeanDefinitions("bean:com.scorpio4.vendor.spring.GreetingsEarthling");
		println "Loaded: "+loaded;
		def bean = applicationContext.getBean("bean:com.scorpio4.vendor.spring.HelloWorld");
		assert bean!=null;
		assert com.scorpio4.vendor.spring.HelloWorld.isInstance(bean);
		assert bean.isWelcomed();
		println "Hello Bean: "+bean;
		finalize()
	}
}
