package org.opennms.sms.monitor.internal.config;

import javax.xml.bind.annotation.XmlRootElement;

import org.opennms.core.utils.LogUtils;
import org.opennms.sms.monitor.MobileSequenceSession;
import org.opennms.sms.reflector.smsservice.MobileMsgRequest;
import org.opennms.sms.reflector.smsservice.MobileMsgResponse;
import org.opennms.sms.reflector.smsservice.MobileMsgResponseMatcher;
import org.opennms.sms.reflector.smsservice.SmsRequest;
import org.opennms.sms.reflector.smsservice.SmsResponse;

@XmlRootElement(name="from-recipient")
public class SmsFromRecipientResponseMatcher extends SequenceResponseMatcher {

	@Override
	public MobileMsgResponseMatcher getMatcher(MobileSequenceSession session) {
		return new MobileMsgResponseMatcher() {
			
			public boolean matches(MobileMsgRequest request, MobileMsgResponse response) {
				LogUtils.tracef(this, "smsFromRecipient.matches(%s, %s)", request, response);
				if (request instanceof SmsRequest && response instanceof SmsResponse) {
					return equals(((SmsResponse)response).getOriginator(), ((SmsRequest)request).getRecipient());
				}
		
				return false;
			}

			private boolean equals(String orig, String recip) {
				return orig.replaceFirst("^\\+", "").equals(recip.replaceFirst("^\\+", ""));
			}
			
			public String toString() {
				return "smsFromRecipient()";
			}
		};
	}

}
