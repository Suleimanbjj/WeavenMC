package br.com.weavenmc.commons.velocity.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class YamlConfig {

	private final Map<String, Object> root;

	@SuppressWarnings("unchecked")
	private YamlConfig(Object root) {
		this.root = root instanceof Map ? (Map<String, Object>) root : Collections.emptyMap();
	}

	public static YamlConfig fromFile(Path file) throws IOException {
		try (Reader reader = Files.newBufferedReader(file)) {
			return new YamlConfig(new Yaml().load(reader));
		}
	}

	public static void copyDefault(Path target, InputStream input) throws IOException {
		if (Files.exists(target)) {
			return;
		}
		Files.createDirectories(target.getParent());
		Files.copy(input, target);
	}

	public String getString(String path) {
		Object obj = get(path);
		return obj == null ? "" : String.valueOf(obj);
	}

	public int getInt(String path) {
		Object obj = get(path);
		if (obj instanceof Number) {
			return ((Number) obj).intValue();
		}
		try {
			return Integer.parseInt(String.valueOf(obj));
		} catch (Exception ex) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getStringList(String path) {
		Object obj = get(path);
		if (!(obj instanceof List)) {
			return Collections.emptyList();
		}
		List<String> result = new ArrayList<>();
		for (Object item : (List<Object>) obj) {
			result.add(String.valueOf(item));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private Object get(String path) {
		Object current = root;
		for (String key : path.split("\\.")) {
			if (!(current instanceof Map)) {
				return null;
			}
			current = ((Map<String, Object>) current).get(key);
			if (current == null) {
				return null;
			}
		}
		return current;
	}
}
