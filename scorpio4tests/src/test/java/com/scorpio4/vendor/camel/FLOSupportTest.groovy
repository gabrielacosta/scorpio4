package com.scorpio4.vendor.camel

import com.scorpio4.test.Scorpio4TestCase
import com.scorpio4.vendor.camel.planner.FLOSupport
/**
 * Scorpio (c) 2014
 * Module: com.scorpio4.vendor.camel
 * User  : lee
 * Date  : 19/06/2014
 * Time  : 11:46 AM
 *
 *
 */
class FLOSupportTest extends Scorpio4TestCase {

	void testRoute() {
		FLOSupport routing = new FLOSupport();
		routing.start()
		def plan = routing.plan("direct://test", "file://temp/to.test/");
		assert plan!=null;

		log.println("="*40)
		def result = routing.trigger("direct://test", "TESTING #2\n"+new Date() );
		assert result!=null;
		println result;
	}
}
