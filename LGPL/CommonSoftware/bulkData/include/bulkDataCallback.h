#ifndef _BULKDATA_CALLBACK_H
#define _BULKDATA_CALLBACK_H

#ifndef __cplusplus
#error This is a C++ include file and cannot be used from plain C
#endif

#include "orbsvcs/AV/AVStreams_i.h"
#include "orbsvcs/AV/Endpoint_Strategy.h"
#include "orbsvcs/AV/Protocol_Factory.h"
#include "orbsvcs/AV/Flows_T.h"
#include "orbsvcs/AV/Transport.h"
#include "orbsvcs/AV/Policy.h"

#include <baci.h>

#include "ACSBulkDataError.h"
#include "ACSBulkDataStatus.h"

#include <iostream>

using namespace std;
using namespace ACSBulkDataError;
using namespace ACSBulkDataStatus;

const CORBA::ULong maxErrorRepetition = 3;

class BulkDataCallback : public TAO_AV_Callback
{

  public:

    enum Cb_State
    {
	CB_UNS,
	CB_SEND_PARAM,
	CB_SEND_DATA
    };

    enum Cb_SubState
    {
	CB_SUB_UNS,
	CB_SUB_INIT
    };


    BulkDataCallback();

    virtual ~BulkDataCallback();

    virtual int handle_start(void);

    virtual int handle_stop (void);

    virtual int handle_destroy (void);

    virtual int receive_frame (ACE_Message_Block *frame, TAO_AV_frame_info *frame_info, const ACE_Addr &);

    virtual void setFlowname (const char*);

    virtual void setSleepTime(ACE_Time_Value locWaitPeriod);

    virtual void setSafeTimeout(CORBA::ULong locLoop);

    virtual CORBA::Boolean isTimeout();
    virtual CORBA::Boolean isWorking();
    virtual CORBA::Boolean isError();

    /********************* methods to be implemented by the user *****************/

    virtual int cbStart(ACE_Message_Block * userParam_p = 0) = 0;

    virtual int cbReceive(ACE_Message_Block * frame_p) = 0;

    virtual int cbStop() = 0;

  protected:

    ACE_CString flowname_m;

    CORBA::ULong flowNumber_m;

  private:

    ACE_Time_Value waitPeriod_m;

    CORBA::ULong loop_m;

    Cb_State state_m;
    Cb_SubState substate_m;

    CORBA::Long dim_m;

    CORBA::Long count_m;

    CORBA::Long frameCount_m;

    CORBA::ULong errorCounter;

    ACE_Message_Block *bufParam_p;

    CORBA::Boolean timeout_m;
    CORBA::Boolean working_m;
    CORBA::Boolean error_m;

//    AVCbErrorCompletion *erComp_p;
};


#endif /*!_BULKDATA_CALLBACK_H*/
