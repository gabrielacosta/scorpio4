package com.scorpio4.iq

import com.scorpio4.assets.Asset
import com.scorpio4.iq.exec.Templating
import com.scorpio4.test.Scorpio4TestCase

/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.iq
 * User  : lee
 * Date  : 17/06/2014
 * Time  : 10:02 PM
 *
 *
 */
class PublishingTest extends Scorpio4TestCase {

    void testWith() {
        def publisher = new Templating();
	    Asset template = new Asset("hello \${greeting}");
        def future = publisher.execute(template, [ "greeting": "world"])
        assert future!=null;
        Writable writable = future.get();
        assert writable!=null;
        assert writable.toString().equals("hello world");
    }
}
