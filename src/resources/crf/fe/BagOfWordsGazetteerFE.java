/**
 * 
 */
package crf.fe;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import edu.cmu.minorthird.text.Span;
import edu.cmu.minorthird.text.TextLabels;
import edu.cmu.minorthird.text.learn.SampleFE;
import edu.cmu.minorthird.text.learn.SpanGazetteerFE;
import edu.cmu.minorthird.util.SimpleGaz;
import gazetteer.Gazetteer;

/**
 * @author rex
 *
 */
public class BagOfWordsGazetteerFE extends SpanGazetteerFE implements Serializable{
	
	static final long serialVersionUID=20140310L;
	SimpleGaz sGaz;
	String type;
	public BagOfWordsGazetteerFE(String type) throws IOException{
		this.sGaz = new SimpleGaz("src/resources/hyer.txt", type);
		this.type = type;
	}
	@Override
	public void extractFeatures(TextLabels labels, Span s) {
		from(s).tokens().emit();
		try {
			from(s).tokens().eq().usewords(sGaz).emitGazetteer("Is.in.Gazetter." + type);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
