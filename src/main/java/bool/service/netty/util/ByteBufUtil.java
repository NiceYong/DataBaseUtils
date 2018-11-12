/**
 * 
 */
package bool.service.netty.util;

import io.netty.buffer.ByteBuf;

/**
 * ByteBuf工具类
 * @author wangw
 */
public class ByteBufUtil {
	public static int indexOf(ByteBuf haystack, ByteBuf needle) {
		for(int i=haystack.readerIndex(); i<haystack.writerIndex(); i++) {
			int haystackIndex = i;
			
			int needleIndex;
			for(needleIndex=0; needleIndex<needle.capacity(); needleIndex++) {
				if(haystack.getByte(haystackIndex) != needle.getByte(needleIndex)) {
					break;
				}else {
					haystackIndex++;
					if(haystackIndex==haystack.writerIndex() && needleIndex!=needle.capacity()-1) {
						return -1;
					}
				}
			}
			
			if(needleIndex == needle.capacity()) {
				return i-haystack.readerIndex();
			}
		}
		
		return -1;
	}
	
	/**
	 * 获得字节数组
	 * @param in
	 * @return
	 */
	public static byte[] getBytes(ByteBuf in) {
		if(in==null || in.readableBytes()<=0) {
			return null;
		}
		
		byte[] bytes = new byte[in.readableBytes()];
		in.readBytes(bytes);
		
		return bytes;
	}
}