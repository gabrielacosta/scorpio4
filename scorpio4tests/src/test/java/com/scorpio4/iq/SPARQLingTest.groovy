package com.scorpio4.iq
import com.scorpio4.assets.Asset
import com.scorpio4.iq.exec.SPARQLing
import com.scorpio4.test.Scorpio4TestCase
import com.scorpio4.vocab.COMMON
/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.iq
 * User  : lee
 * Date  : 17/06/2014
 * Time  : 10:22 PM
 *
 *
 */
class SPARQLingTest extends Scorpio4TestCase {

    void testWith() {
	    initialize();
        def sparqling = new SPARQLing(connection);
	    Asset query = new Asset("SELECT DISTINCT ?o WHERE {?s ?p ?o}", COMMON.MIME_SPARQL);
        def future = sparqling.execute(query, ["hello": "world"])
        assert future!=null;
        def result = future.get();
        assert result!=null;
        assert result instanceof Collection<Map>;
        assert !result.isEmpty();
	    finalize()
    }
}
