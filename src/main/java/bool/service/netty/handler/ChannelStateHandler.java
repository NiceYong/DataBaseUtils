/**
 * 
 */
package bool.service.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 通道状态处理器
 * @author wangw
 */
@Sharable
public class ChannelStateHandler extends ChannelDuplexHandler{
	private static final Logger logger = LoggerFactory.getLogger(ChannelStateHandler.class);
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		logger.info("添加通道状态处理器");
		
		super.handlerAdded(ctx);
	}
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("注册通道状态处理器");
		
		super.channelRegistered(ctx);
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			
			IdleState state = event.state();
			if(state == IdleState.READER_IDLE) {
				logger.info("长时间无接收数据，开始关闭通道");
				
				ctx.close();
			}
		}
		
		super.userEventTriggered(ctx, evt);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("监控异常", cause);
		
//		super.exceptionCaught(ctx, cause);
	}
}