/**
 * 
 */
package bool.service.netty.channel;

import bool.service.netty.handler.ChannelStateHandler;
import bool.service.netty.handler.DataInboundFilterHandler;
import bool.service.netty.handler.DataInboundHandler;
import bool.service.netty.handler.DataOutboundFilterHandler;
import bool.service.netty.handler.DataOutboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Netty通道
 * @author wangw
 */
public abstract class NettyChannel extends ChannelInitializer<SocketChannel>{
	/**
	 * 数据入站处理器
	 */
	protected final DataInboundHandler dataInboundHandler = new DataInboundHandler();
	
	/**
	 * 数据出站处理器
	 */
	protected final DataOutboundHandler dataOutboundHandler = new DataOutboundHandler();
	
	/**
	 * 数据入站过滤处理器
	 */
	protected final DataInboundFilterHandler dataInboundFilterHandler = new DataInboundFilterHandler();
	
	/**
	 * 数据出站过滤处理器
	 */
	protected final DataOutboundFilterHandler dataOutboundFilterHandler = new DataOutboundFilterHandler();
	
	/**
	 * 通道状态处理器
	 */
	protected final ChannelStateHandler channelStateHandler = new ChannelStateHandler();
}