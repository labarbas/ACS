/*******************************************************************************
 * ALMA - Atacama Large Millimeter Array
 * Copyright (c) ESO - European Southern Observatory, 2011
 * (in the framework of the ALMA collaboration).
 * All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 *******************************************************************************/
package alma.acs.eventbrowser.model;

/**
 * $Author: jschwarz $
 * $Date: 2011/10/28 11:56:21 $
 * $Id: AbstractEventData.java,v 1.2 2011/10/28 11:56:21 jschwarz Exp $
 */

public abstract class AbstractEventData implements java.io.Serializable {

	private static final long serialVersionUID = -8199180183031190929L;

	protected Long timestamp;

	public AbstractEventData() {
		super();
	}

	public Long getTimestamp() {
		return timestamp;
	}
	

}