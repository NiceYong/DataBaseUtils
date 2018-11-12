/**
 * 
 */
package bool.service.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * 数据入站处理器
 * @author wangw
 */
@Sharable
public class DataInboundHandler extends ChannelInboundHandlerAdapter{
	private static final Logger logger = LoggerFactory.getLogger(DataInboundHandler.class);
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		logger.info("添加数据入站处理器");
		
		super.handlerAdded(ctx);
	}
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("注册数据入站处理器");
		
		super.channelRegistered(ctx);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("激活通道");
		
		super.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("关闭通道");
		
		super.channelInactive(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info("读取数据");
		
		super.channelRead(ctx, msg);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		logger.info("读取数据完成");
		
		super.channelReadComplete(ctx);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		try {
			logger.error("数据入站异常   " + cause.toString() + "   连接地址："+ctx.channel().remoteAddress());
		}catch (Exception e){

		}
//		logger.error("数据入站异常", cause);

//		super.exceptionCaught(ctx, cause);
	}
}