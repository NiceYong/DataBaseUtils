/**
 * 
 */
package bool.service.netty.handler;

import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 数据出站过滤处理器
 * @author wangw
 */
@Sharable
public class DataOutboundFilterHandler extends ChannelOutboundHandlerAdapter{
	private static final Logger logger = LoggerFactory.getLogger(DataOutboundFilterHandler.class);
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		logger.info("添加数据出站过滤处理器");
		
		super.handlerAdded(ctx);
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		
		int bytesNumber = in.readableBytes();
		if(bytesNumber <= 0) {
			logger.info("没有数据");
			
			return;
		}
		
		super.write(ctx, msg, promise);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("数据出站过滤异常", cause);
		
//		super.exceptionCaught(ctx, cause);
	}
}