package com.scorpio4.tools

import com.scorpio4.iq.exec.Executor
import com.scorpio4.test.Scorpio4TestCase
/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.tools
 * User  : lee
 * Date  : 18/06/2014
 * Time  : 4:47 PM
 *
 *
 */
class ExecutorTest extends Scorpio4TestCase {

    void testDoRun() {
	    initialize()
        def toolChain = new Executor(factSpace);
        def executed = toolChain.run("urn:BlankProject:", [:])
	    assert executed!=null;
	    println "Executed: "+executed;
	    finalize()
    }
}
