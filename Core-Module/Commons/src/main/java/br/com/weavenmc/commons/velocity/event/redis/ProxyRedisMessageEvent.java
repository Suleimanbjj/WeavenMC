package br.com.weavenmc.commons.velocity.event.redis;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProxyRedisMessageEvent {

	@NonNull
	private final String channel;
	@NonNull
	private final String message;
}
