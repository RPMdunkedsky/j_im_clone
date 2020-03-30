/**
 * 
 */
package org.jim.server.command;

import org.jim.common.ImChannelContext;
import org.jim.common.ImConst;
import org.jim.common.config.ImConfig;
import org.jim.server.command.handler.processor.SingleProtocolCmdProcessor;
import org.jim.server.command.handler.processor.MultiProtocolCmdProcessor;
import org.jim.server.config.ImServerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 版本: [1.0]
 * 功能说明: 
 * @author: WChao 创建时间: 2017年9月11日 下午2:07:44
 */
public abstract class AbstractCmdHandler implements CmdHandler, ImConst {
	/**
	 * 单协议业务处理器
	 */
	private SingleProtocolCmdProcessor singleProcessor;
	/**
	 * 多协议业务处理器
	 */
	private List<MultiProtocolCmdProcessor> multiProcessors = new ArrayList<>();
	/**
	 * IM相关配置类
	 */
	private ImServerConfig imConfig = ImConfig.Global.get();
	
	public AbstractCmdHandler() {};

	public ImServerConfig getImConfig() {
		return imConfig;
	}

	public SingleProtocolCmdProcessor getSingleProcessor() {
		return singleProcessor;
	}

	public AbstractCmdHandler setSingleProcessor(SingleProtocolCmdProcessor singleProcessor) {
		this.singleProcessor = singleProcessor;
		return this;
	}

	public <T> T getSingleProcessor(Class<T> clazz) {
		return (T)singleProcessor;
	}

	public AbstractCmdHandler addMultiProtocolProcessor(MultiProtocolCmdProcessor processor) {
		this.multiProcessors.add(processor);
		return this;
	}

	/**
	 * 根据当前通道所属协议获取cmd业务处理器
	 * @param imChannelContext
	 * @return
	 */
	public <T> T getMultiProcessor(ImChannelContext imChannelContext, Class<T> clazz){
		T multiCmdProcessor = null;
		for(MultiProtocolCmdProcessor multiProcessor : multiProcessors){
			if(multiProcessor.isProtocol(imChannelContext)){
				multiCmdProcessor = (T)multiProcessor;
			}
		}
		return multiCmdProcessor;
	}

}
