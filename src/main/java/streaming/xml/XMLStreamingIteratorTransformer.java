package streaming.xml;

import java.io.InputStream;
import java.util.Iterator;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.MessageFactory;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transformer.types.DataTypeFactory;

public class XMLStreamingIteratorTransformer extends AbstractMessageTransformer implements DiscoverableTransformer {

	private int priorityWeighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

	private String recordTagName;
	
	public String getRecordTagName() {
		return recordTagName;
	}

	public void setRecordTagName(String recordTagName) {
		this.recordTagName = recordTagName;
	}

	public XMLStreamingIteratorTransformer() {
		super();
		registerSourceType(DataTypeFactory.INPUT_STREAM);
		setReturnDataType(DataTypeFactory.create(XMLStreamingIterator.class));
	}
	
	@Override
	public int getPriorityWeighting() {
		return priorityWeighting;
	}

	@Override
	public void setPriorityWeighting(int priorityWeighting) {
		this.priorityWeighting = priorityWeighting;
	}

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		Object payload = message.getOriginalPayload();
		try{
			return new XMLStreamingIterator((InputStream) payload, recordTagName);
		} catch (Exception e) {
			throw new TransformerException(MessageFactory.createStaticMessage("Unable to convert " + InputStream.class.getCanonicalName() + " to " + Iterator.class.getCanonicalName() + ": " + e.getMessage()), this);
		}
	}

}
