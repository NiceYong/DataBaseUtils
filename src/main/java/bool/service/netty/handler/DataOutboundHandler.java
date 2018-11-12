/**
 * 
 */
package bool.service.netty.handler;

import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * 数据出站处理器
 * @author wangw
 */
@Sharable
public class DataOutboundHandler extends ChannelOutboundHandlerAdapter{
	private static final Logger logger = LoggerFactory.getLogger(DataOutboundHandler.class);
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		logger.info("添加数据出站处理器");
		
		super.handlerAdded(ctx);
	}
	
	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		logger.info("建立连接");
		
		super.connect(ctx, remoteAddress, localAddress, promise);
	}
	
	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		logger.info("断开连接");
		
		super.disconnect(ctx, promise);
	}
	
	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		logger.info("关闭连接");
		
		super.close(ctx, promise);
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		logger.info("写出数据");
		
		super.write(ctx, msg, promise);
	}
	
	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		logger.info("发送数据");
		
		super.flush(ctx);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("数据出站异常", cause);
		
//		super.exceptionCaught(ctx, cause);
	}
}