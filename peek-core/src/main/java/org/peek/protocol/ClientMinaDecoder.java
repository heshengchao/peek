package org.peek.protocol;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ClientMinaDecoder extends CumulativeProtocolDecoder {  
	private static Logger logger = LoggerFactory.getLogger(ClientMinaDecoder.class); 
  
	/**文字编码*/
	private final Charset charset;  
    private final static int headerSize=8;
    
    public ClientMinaDecoder(Charset charset){
    	this.charset=charset;
    }

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		if(logger.isDebugEnabled())
			logger.debug("数据大小："+in.limit());
		
		int oldPos = in.position();
		if(in.hasRemaining()){
			int totleDataSize=in.remaining();
			if(headerSize>totleDataSize){//数据并未发送完全
				in.setAutoExpand(true);
				return false;
			}
				
			byte[] headers=new byte[headerSize];
			in.get(headers);
				
			CustomPotocolMeta meta = getDataHeader(headers);
			if(logger.isDebugEnabled())
				logger.debug("数据包头信息："+meta.toJsonString());
			//数据并未发送完全
			if( (meta.length+headerSize)>totleDataSize ){
				in.position(oldPos);
				in.setAutoExpand(true);
				if(logger.isDebugEnabled())
					logger.debug("数据接收未完成，等待下一包数据");
				return false;
			}else{
				byte[] tmp=new byte[meta.length];
				in.get(tmp);
				String xml=new String(tmp, charset);
				
//				logger.debug("得到返回XML："+xml);
				
				WriteBean wb=new WriteBean();
				wb.setXmlMsg(xml);
				wb.setCmd(meta.cmd);
				out.write(wb); 
				if(in.hasRemaining()){
					in.get(new byte[in.remaining()]);
				}
				if(logger.isDebugEnabled())
					logger.debug("数据接收完成，传入业务");
			}
		}
		return true;
	}
	
  
    private CustomPotocolMeta getDataHeader(byte[] headers) {
		CustomPotocolMeta meta=new CustomPotocolMeta();
		meta.cmd	=	(short)( headers[2]&0xFF );//命令
		meta.seralNo =	(short)( headers[3]<< 8 &0xFF00  | (headers[4]&0xFF));//数据类型
		meta.length=	(int)( headers[6]<< 8 &0xFF00 | (headers[7] & 0xFF));
//		meta.length=	(int)( headers[6]&0xFF | (headers[7]<< 8&0xFF00) | (headers[8]<<9) &0xFF0000  | (headers[9]<<24)&0xFF000000);//分包数
//		meta.length=	(int)( headers[9]&0xFF | (headers[8]<< 8&0xFF00) | (headers[7]<<9) &0xFF0000  | (headers[6]<<24)&0xFF000000);//分包数
		return meta;
	}
    
	/**版本号（4Byte）	
	 * <br >指令长度（4Byte）	
	 * <br >指令编号（4Byte）	
	 * <br >指令序号（4Byte）	
	 * <br >源ID（4Byte） 
	 * <br >目的ID（4Byte）
	 * <br >成功标识（4Byte）
	 * @author heshengchao@qq.com 
	 * @since  2013-7-25 下午7:44:47
	 */
	class CustomPotocolMeta {
		
		/**长度*/
		public int length;
		public short seralNo;
		/**命令编码*/
		public short cmd;
		
		
		public String toJsonString(){
			return "seralNo:"+seralNo+",cmd:"+cmd+",length:"+length;
		}
	}
  
}  
