/**
 * 
 */
package bool.service.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * 数据入站过滤处理器
 * @author wangw
 */
@Sharable
public class DataInboundFilterHandler extends ChannelInboundHandlerAdapter{
	private static final Logger logger = LoggerFactory.getLogger(DataInboundFilterHandler.class);
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		logger.info("添加数据入站过滤处理器");
		
		super.handlerAdded(ctx);
	}
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("注册数据入站过滤处理器");
		
		super.channelRegistered(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		
		int bytesNumber = in.readableBytes();
		if(bytesNumber <= 0) {
			logger.info("没有数据");
			
			return;
		}
		
		super.channelRead(ctx, msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

		try {
			logger.error("数据入站过滤异常   " + cause.toString() + "   连接地址："+ctx.channel().remoteAddress());
		}catch (Exception e){

		}
//		super.exceptionCaught(ctx, cause);
	}
}