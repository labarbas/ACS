#ifndef baciROdouble_H
#define baciROdouble_H

/*******************************************************************
* ALMA - Atacama Large Millimiter Array
* (c) European Southern Observatory, 2003 
*
*This library is free software; you can redistribute it and/or
*modify it under the terms of the GNU Lesser General Public
*License as published by the Free Software Foundation; either
*version 2.1 of the License, or (at your option) any later version.
*
*This library is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
*Lesser General Public License for more details.
*
*You should have received a copy of the GNU Lesser General Public
*License along with this library; if not, write to the Free Software
*Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
*
* "@(#) $Id: baciROdouble.h,v 1.95 2006/09/01 02:20:54 cparedes Exp $"
*
* who       when        what
* --------  ----------  ----------------------------------------------
* oat       2003/01/21  added baciMonitor_T (templates)
* bjeram    2002/02/25  added support for DevIO 
* msekoran  2001/03/09  modified
*/

/** 
 * @file 
 * Header file for BACI Read-only Double Property.
 */

#ifndef __cplusplus
#error This is a C++ include file and cannot be used from plain C
#endif

#include <baciMonitor_T.h>
#include <baciROcontImpl_T.h>

namespace baci {

/** @defgroup MonitordoubleTemplate Monitordouble Class
 * The Monitordouble class is a templated typedef so there is no actual inline doc generated for it per-se.
 *  @{
 * The Monitordouble class is an implementation of the ACS::Monitordouble IDL interface.
 */
typedef  Monitor<ACS_MONITOR(double, CORBA::Double)> Monitordouble;
/** @} */

/** @defgroup ROdoubleTemplate ROdouble Class
 * The ROdouble class is a templated typedef so there is no actual inline doc generated for it per-se.
 *  @{
 * The ROdouble class is an implementation of the ACS::ROdouble IDL interface.
 */
typedef  ROcontImpl<ACS_RO_T(double, CORBA::Double)> ROdouble;
/** @} */


 }; 

#endif

