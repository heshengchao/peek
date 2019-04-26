package org.peek.protocol;


import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.peek.logger.LOG;


public class ClientMinaEncoder extends ProtocolEncoderAdapter {  
//	private static Logger logger = LoggerFactory.getLogger(ClientMinaEncoder.class);
    private final Charset charset;  
    private static final int HEAD_SIZE=8;

    
    public ClientMinaEncoder(Charset charset){  
        this.charset = charset;  
    }  
      
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {  
    	
		if (message instanceof WriteBean) {
			WriteBean wb=(WriteBean)message;
			
			byte[] bytes=wb.getXmlMsg().getBytes(charset);
			
			IoBuffer buffer = IoBuffer.allocate(bytes.length + HEAD_SIZE + 16).setAutoExpand(true);

			if(LOG.isDebugEnabled())
				LOG.debug("发送长度："+bytes.length+",CMD:"+wb.getCmd()+",seq:"+wb.getSeq()+",XML数据："+wb.getXmlMsg());
			buffer.put(convertHeaderByte(bytes.length, wb.getCmd(), wb.getSeq(),(short)1));
			
			buffer.put(bytes);
			
			buffer.flip();
			out.write(buffer);
    	}else{
    		LOG.warn("异常数据："+message);
    	}
    }

	/**
	 * @param version
	 * @param length
	 * @param cmd
	 * @param cmdNo
	 * @param from
	 * @param to
	 * @return
	 */
	private byte[] convertHeaderByte(int length,short cmd, short seq,short version) {
		
		byte[] header_byte=new byte[HEAD_SIZE];
		//起始字头
		header_byte[0]=(byte)((version  >> 8)	& 0xff);
		header_byte[1]=(byte)(version	& 0xff);
		//命令字
		header_byte[2]=(byte)cmd;
		 //流水号
		header_byte[3]=(byte)((seq  >> 8)	& 0xff);
		header_byte[4]=(byte)(seq	& 0xff);
		//保留项
		header_byte[5]=(byte)0;
		 //数据长度
		header_byte[6]=(byte)((length >> 8)& 0xff);
		header_byte[7]=(byte)(length  & 0xff);

		return header_byte;
	} 
}  
