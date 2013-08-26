/*******************************************************************************
 *    ALMA - Atacama Large Millimiter Array
 *    (c) European Southern Observatory, 2002
 *    Copyright by ESO (in the framework of the ALMA collaboration)
 *    and Cosylab 2002, All rights reserved
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
 *    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 *
 *
 * "@(#) "
 *
 * who       when        what
 * --------  ----------  ----------------------------------------------
 * javarias  May 6, 2010  	 created
 */

#ifndef LOGGINGXMLLAYOUT_H_
#define LOGGINGXMLLAYOUT_H_

#include <ace/ACE.h>
#include <ace/OS.h>
#define LOG4CPP_FIX_ERROR_COLLISION 1
#include <log4cpp/Layout.hh>

namespace logging {
class ACSXmlLayout: public ::log4cpp::Layout {
public:
	ACSXmlLayout();
	~ACSXmlLayout();
	std::string format(const ::log4cpp::LoggingEvent& event);
	static void formatISO8601inUTC(const ACE_Time_Value &timestamp,
			ACE_TCHAR str[]);

private:
	std::string m_xml;
};
}

#endif /* LOGGINGXMLLAYOUT_H_ */
