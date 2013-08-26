#ifndef _DDS_PUBLISHER_H
#define _DDS_PUBLISHER_H

#include <DDSHelper.h>
#include <dds/DCPS/PublisherImpl.h>
#include "acsddsncCDBProperties.h"

namespace ddsnc{

	/**
	 * The Publisher implementation of ACS Notification Channel based on DDS.
	 *
	 * The template requires the DataWriter_var class
	 * that is specific to the data type defined
	 * in the idl and registered in the topic.
	 * 
	 * @see DDSHelper
	 * @author Jorge Avarias <javarias[at]inf.utfsm.cl>
	 */
//	template<class DWVAR>
	class DDSPublisher : public ::ddsnc::DDSHelper{
		private:
		DDS::Publisher_var pub;
		OpenDDS::DCPS::PublisherImpl *pub_impl;
		DDS::DataWriter_var dw;
//		DWVAR dataWriter;	
		DDS::InstanceHandle_t handler;

		int attachToTransport();

		/**
		 * Create the participant, initialize the publisher with the partition
		 * provided in the channelName constructor parameter, if there is not
		 * partition initialize the publisher with default QoS properties,
		 * initialize the transport for the participant and finally initilize
		 * the data writer QoS to default.
		 *
		 */
		void initialize();

		/**
		 * Initialize the DataWriter with QoS
		 *
		 * @tparam <type>DataWriter class
		 * @see dwQos
		 */
		void initializeDataWriter();

		/**
		 * Creates the publisher with default QoS or Qos with a partition
		 *
		 * @see initialize()
		 */
		int createPublisher();

		protected:
		DDS::PublisherQos pubQos;

		public:
		DDS::DataWriterQos dwQos; /**< Data Writer Qos, can be modified according
		OpenDDS API, the Qos properties will be 
		applied when is called publishData for
		first time and they cannot be changed after
		that.

		@see initializeDataWriter()
		@see publishData(D data)
		*/
		
		/**
		 * Constructor for DDSPublisher class, initializes the common
		 * objects required by a DDS Publisher.
		 *
		 * @see DDSHelper
		 * @see initialize()
		 */
		DDSPublisher(const char *channelName): 
			::ddsnc::DDSHelper(channelName)
		{
			initialize();
		}


/**
 * Publish the data to be sended to the subscribers, the data type must
 * be defined in the idl file. If the class has been not initialized, it are
 * initialized at this moment and it set as initialized.
 * 
 * The <type> corresponds to the name of data type (without namespace)
 * defined in the idl file, the classes required by the template are 
 * autogenerated by dcps_ts.pl tools and are specific to the data type defined.
 *
 * @param data the data to be published
 * @tparam D <type> class
 * @tparam DW <type>DataWriter class 
 * @tparam TSV <type>TypeSupport_var class 
 * @tparam TSI <type>TypeSupportImpl class
 */ 
		template <class D, class DW, class DWVAR, class TSV, class TSI>
			void publishData(D data)
			{
				if(initialized==false){
					/*Initialize Type Support*/
					TSV ts;
					ts = new TSI();
					if(DDS::RETCODE_OK != ts->register_type(participant.in(),""))
						std::cerr << "register_type failed" << std::endl;
					/*Initialize the Topic*/
					initializeTopic(ts->get_type_name());
					if(CORBA::is_nil(topic.in()))
							std::cerr<< "Topic is nil" << std::endl;
					initializeDataWriter();
					initialized=true;
				}
				DWVAR dataWriter = DW::_narrow(dw.in());
				handler = dataWriter->_cxx_register(data);
				dataWriter->write(data,handler);
			}

		~DDSPublisher()
		{
		}


	};
}

#define PUBLISH_DATA(publisher_p, idlStruct, message) \
{ \
	publisher_p->publishData<idlStruct, idlStruct##DataWriter, \
	idlStruct##DataWriter_var, \
	idlStruct##TypeSupport_var, idlStruct##TypeSupportImpl> (message); \
}
#endif
