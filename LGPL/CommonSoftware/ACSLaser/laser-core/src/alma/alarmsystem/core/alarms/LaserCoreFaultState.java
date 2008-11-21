/*
 *    ALMA - Atacama Large Millimiter Array
 *    (c) European Southern Observatory, 2008
 *    Copyright by ESO (in the framework of the ALMA collaboration),
 *    All rights reserved
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public
 *    License along with this library; if not, write to the Free Software
 *    Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *    MA 02111-1307  USA
 */
package alma.alarmsystem.core.alarms;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.Vector;

import alma.acs.container.ContainerServicesBase;

import com.cosylab.acs.jms.ACSJMSTextMessage;

import cern.laser.source.alarmsysteminterface.FaultState;
import cern.laser.source.alarmsysteminterface.impl.ASIMessageHelper;
import cern.laser.source.alarmsysteminterface.impl.FaultStateImpl;
import cern.laser.source.alarmsysteminterface.impl.XMLMessageHelper;
import cern.laser.source.alarmsysteminterface.impl.message.ASIMessage;

/**
 * An helper class to create fault states generated by the alarm component.
 * <P>
 * All the alarms generated by the alarm service belong to the <I>AlarmSystem</I> fault family
 * and to the <I>AlarmService</I> fault member.
 *   
 * @author acaproni
 *
 */
public class LaserCoreFaultState {
	
	/**
	 * The fault code of the alarms generated by the alarm service.
	 * 
	 * @author acaproni
	 *
	 */
	public enum LaserCoreFaultCodes {
		CDB_UNAVAILABLE( // Error getting CDB
				0,
				0,
				"Error getting DAL",
				"Invalid reference to DAL returned by container services",
				"Impossible to read CDB"), 
		ALARMS_CDB( // Error reading alarm definitions from the CDB
				1,
				0,
				"Wrong alarm defs in CDB",
				"The alarm definintions in the CDB are wrong",
				"Some/all alarms will be lost"), 
		CATEGORIES_CDB( // Error reading alarm definitions from the CDB
				2,
				0,
				"Wrong category defs in CDB",
				"The category definintions in the CDB are wrong",
				"Some/all alarms will not pbe visible in the GUI"),
		JMS_INIT( // error initializing JMS
				3,
				0,
				"JMS not responding",
				"ACS-JMS not responding",
				"Alarm service unusable"), 
		SOURCE_LISTENER( // Error setting JMS source listener
				4,
				0,
				"JMS listener not set",
				"Error connecting to the sourc notification channel",
				"Impossible to receive alarms from sources"); 
		
		/**
		 * The FC of the alarm
		 */
		public final int faultCode;
		
		/**
		 * The priority of the alarm
		 */
		public final int priority;
		
		/**
		 * The description of the alarm
		 */
		public final String description;
		
		/**
		 * The cause of the alarm
		 */
		public final String cause;
		
		/**
		 * The consequences of the alarm
		 */
		public final String consequences;
		
		/**
		 * Constructor
		 * 
		 * @param code The fault code of the alarm
		 */
		private LaserCoreFaultCodes(int code, int priority, String desc, String cause, String consequences) {
			this.faultCode=code;
			this.priority=priority;
			this.description=desc;
			this.cause=cause;
			this.consequences=consequences;
		}
	}
	
	/**
	 * The fault family of the alarms generated by the alarm service
	 */
	public static final String FaultFamily="AlarmSystem";
	
	/**
	 * The fault member of the alarms generated by the alarm service
	 */
	public static final String FaultMember="AlarmService";
	
	/**
	 * The host name 
	 */
	private static String hostName=null;
	
	/**
	 * Create a <code>FaultState</code> for a given fault code.
	 * 
	 * @param FC The fault code of the alarm
	 * @param active <code>true</code> if the alarm is active
	 * @return The <code>FaultState</code> for the passed code
	 */
	public static FaultState createFaultState(LaserCoreFaultCodes FC, boolean active) {
		if (FC==null) {
			throw new IllegalArgumentException("The fault code can't be null");
		}
		FaultStateImpl fs = new FaultStateImpl(FaultFamily,FaultMember,FC.faultCode);
		if (active) {
			fs.setDescriptor(FaultState.ACTIVE);
		} else {
			fs.setDescriptor(FaultState.TERMINATE);
		}
		fs.setUserTimestamp(new Timestamp(System.currentTimeMillis()));
		return fs;
	}
	
	/**
	 * Create a <code>FaultState</code> for a given fault code and a given
	 * set of user properties.
	 * 
	 * @param FC The fault code of the alarm
	 * @param props The user properties
	 * @param active <code>true</code> if the alarm is active
	 * @return The <code>FaultState</code> for the passed code
	 */
	public static FaultState createFaultState(LaserCoreFaultCodes FC, Properties props, boolean active) {
		FaultState fs = createFaultState(FC, active);
		fs.setUserProperties(props);
		return fs;
	}
	
	/**
	 * Create an acs text jms message containing the passed
	 * fault state in form of XML (ASIMessage) in the body.
	 *  
	 * @param fs The <code>FaultState</code> to set in the message
	 * @param cs The <code>ContainerServicesBase</code>
	 * @return The jms text message containing the fault state
	 */
	public static ACSJMSTextMessage createJMSMessage(FaultState fs, ContainerServicesBase cs) throws Exception {
		if (fs==null) {
			throw new IllegalArgumentException("The fault state can't be null");
		}
		if (cs==null) {
			throw new IllegalArgumentException("The container services can't be null");
		}
		Vector<FaultState> states = new Vector<FaultState>();
		states.add(fs);
		ASIMessage asiMessage = ASIMessageHelper.marshal(states);
		// Set the timestamp
		cern.laser.source.alarmsysteminterface.impl.message.Timestamp sourceTimestamp=new cern.laser.source.alarmsysteminterface.impl.message.Timestamp();
		sourceTimestamp.setSeconds(System.currentTimeMillis()/1000);
		sourceTimestamp.setMicroseconds((System.currentTimeMillis()%1000)*1000);
		asiMessage.setSourceTimestamp(sourceTimestamp);
		// Set the hostname
		if (hostName==null) {
			InetAddress iAddr = InetAddress.getLocalHost();
			hostName=iAddr.getHostName();
		}
		asiMessage.setSourceHostname(hostName);
		asiMessage.setSourceName("ALARM_SYSTEM_SOURCES");
		ACSJMSTextMessage textMessage = new ACSJMSTextMessage(cs);
		textMessage.setText(XMLMessageHelper.marshal(asiMessage));
		return textMessage;
	}
}
