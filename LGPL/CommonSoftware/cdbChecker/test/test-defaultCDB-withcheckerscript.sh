#!/bin/bash

#*******************************************************************************
# ALMA - Atacama Large Millimeter Array
# Copyright (c) UTFSM - Universidad Tecnica Federico Santa Maria, 2011
# (in the framework of the ALMA collaboration).
# All rights reserved.
# 
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 2.1 of the License, or (at your option) any later version.
# 
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
# Lesser General Public License for more details.
# 
# You should have received a copy of the GNU Lesser General Public
# License along with this library; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
#*******************************************************************************
# Here we use the cdbChecker script and the standard search path for schema files
export ACS_CDB=$PWD/testdata/defaultCDB
cdbChecker -v --network $PWD/testdata/defaultCDB/
echo return code is: $?

# _oOo_
