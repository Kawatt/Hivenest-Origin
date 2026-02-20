package io.github.kawatt.hivenestkwt;

import io.github.kawatt.hivenestkwt.factories.ExampleTypeRegistry;
import io.github.apace100.apoli.util.IdentifierAlias;
import io.github.apace100.calio.resource.OrderedResourceListenerInitializer;
import io.github.apace100.calio.resource.OrderedResourceListenerManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hivenest implements ModInitializer, OrderedResourceListenerInitializer {

	public static final String MODID = "hivenestkwt";
	public static String VERSION = "";
	public static final Logger LOGGER = LogManager.getLogger(Hivenest.class);

	@Override
	public void onInitialize() {
		IdentifierAlias.addNamespaceAlias(MODID, "apoli");
		ExampleTypeRegistry.register();
	}

	public static Identifier identifier(String path) {
		return new Identifier(Hivenest.MODID, path);
	}

	@Override
	public void registerResourceListeners(OrderedResourceListenerManager manager) {
	}

}
