package br.com.weavenmc.commons.velocity.redis;

import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.event.redis.ProxyRedisMessageEvent;
import redis.clients.jedis.JedisPubSub;

public class ProxyPubSubHandler extends JedisPubSub {

	private final VelocityMain plugin;

	public ProxyPubSubHandler(VelocityMain plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onMessage(String channel, String message) {
		plugin.getProxy().getEventManager().fireAndForget(new ProxyRedisMessageEvent(channel, message));
	}
}
