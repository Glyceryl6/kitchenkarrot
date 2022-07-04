package io.github.tt432.kitchenkarrot.client.plate;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.util.json.JsonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DustW
 **/
public class PlateModelRegistry {
    private static final Map<ResourceLocation, BakedModel> MODEL_MAP = new HashMap<>();

    public static ResourceLocation DEFAULT_NAME = new ResourceLocation(Kitchenkarrot.MOD_ID, "plate");
    public static BakedModel DEFAULT_MODEL;

    public static BakedModel get(ResourceLocation resourceLocation) {
        return MODEL_MAP.getOrDefault(resourceLocation,
                DEFAULT_MODEL != null ? DEFAULT_MODEL : (DEFAULT_MODEL = MODEL_MAP.get(DEFAULT_NAME))
        );
    }

    static ResourceLocation from(ModelResourceLocation modelResourceLocation) {
        return new ResourceLocation(modelResourceLocation.getNamespace(), modelResourceLocation.getPath().split("plates/")[1]);
    }

    static ModelResourceLocation to(ResourceLocation resourceLocation) {
        return new ModelResourceLocation(resourceLocation.getNamespace(), "plates/" + resourceLocation.getPath(), "inventory");
    }

    public static void register(ModelRegistryEvent e) {
        ResourceManager manager = Minecraft.getInstance().getResourceManager();

        for (String namespace : manager.getNamespaces()) {
            try {
                var resourceName = new ResourceLocation(namespace, "plate/list.json");

                if (manager.hasResource(resourceName)) {
                    var resources = manager.getResources(resourceName);

                    for (Resource resource : resources) {
                        var reader = new InputStreamReader(resource.getInputStream());
                        var list = JsonUtils.INSTANCE.noExpose.fromJson(reader, PlateList.class);

                        PlateList.INSTANCE.plates.addAll(list.plates);
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        var instance = ForgeModelBakery.instance();

        var defaultUnbakedModel = getModel(DEFAULT_NAME.toString());

        instance.unbakedCache.put(DEFAULT_NAME, defaultUnbakedModel);
        instance.topLevelModels.put(DEFAULT_NAME, defaultUnbakedModel);

        for (var info : PlateList.INSTANCE.plates) {
            var model = getModel(info);

            instance.unbakedCache.put(to(new ResourceLocation(info)), model);
            instance.topLevelModels.put(to(new ResourceLocation(info)), model);
        }
    }

    static BlockModel getModel(String info) {
        ResourceManager manager = Minecraft.getInstance().getResourceManager();

        var name = new ResourceLocation(info);
        var namespace = name.getNamespace();
        var path = name.getPath();

        ForgeModelBakery.addSpecialModel(to(new ResourceLocation(info)));

        var modelName = new ResourceLocation(namespace, "models/plates/" + path + ".json");

        if (manager.hasResource(modelName)) {
            try {
                var resource = manager.getResource(modelName);

                var json = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
                return BlockModel.fromString(json);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    public static void bakeModel(ModelBakeEvent evt) {
        MODEL_MAP.clear();

        MODEL_MAP.put(DEFAULT_NAME, evt.getModelManager().getModel(DEFAULT_NAME));

        for (String info : PlateList.INSTANCE.plates) {
            var modelName = to(new ResourceLocation(info));
            MODEL_MAP.put(from(modelName), evt.getModelManager().getModel(modelName));
        }
    }
}
