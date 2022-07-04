package io.github.tt432.kitchenkarrot.client.cocktail;

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

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DustW
 **/
public class CocktailModelRegistry {

    private static final Map<ResourceLocation, BakedModel> MODEL_MAP = new HashMap<>();

    public static BakedModel get(ResourceLocation resourceLocation) {
        return MODEL_MAP.get(resourceLocation);
    }

    static ResourceLocation from(ModelResourceLocation modelResourceLocation) {
        return new ResourceLocation(modelResourceLocation.getNamespace(), modelResourceLocation.getPath().split("cocktail/")[1]);
    }

    static ModelResourceLocation to(ResourceLocation resourceLocation) {
        return new ModelResourceLocation(resourceLocation.getNamespace(), "cocktail/" + resourceLocation.getPath(), "inventory");
    }

    @SuppressWarnings("unused")
    public static void register(ModelRegistryEvent e) {
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        for (String namespace : manager.getNamespaces()) {
            try {
                ResourceLocation resourceName = new ResourceLocation(namespace, "cocktail/list.json");
                if (manager.hasResource(resourceName)) {
                    List<Resource> resources = manager.getResources(resourceName);
                    for (Resource resource : resources) {
                        InputStreamReader reader = new InputStreamReader(resource.getInputStream());
                        CocktailList list = JsonUtils.INSTANCE.noExpose.fromJson(reader, CocktailList.class);
                        CocktailList.INSTANCE.cocktails.addAll(list.cocktails);
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        for (String cocktailName : CocktailList.INSTANCE.cocktails) {
            ResourceLocation name = new ResourceLocation(cocktailName);
            String namespace = name.getNamespace();
            String path = name.getPath();
            String json = "{\"parent\": \"minecraft:item/generated\", \"textures\": {\"layer0\":\"" + namespace + ":item/cocktail/" + path + "\"}}";
            ForgeModelBakery.addSpecialModel(to(new ResourceLocation(cocktailName)));
            ForgeModelBakery instance = ForgeModelBakery.instance();
            BlockModel model = BlockModel.fromString(json);
            instance.unbakedCache.put(to(name), model);
            instance.topLevelModels.put(to(name), model);
        }
    }

    public static void bakeModel(ModelBakeEvent evt) {
        MODEL_MAP.clear();

        for (String cocktailName : CocktailList.INSTANCE.cocktails) {
            ModelResourceLocation modelName = to(new ResourceLocation(cocktailName));
            MODEL_MAP.put(from(modelName), evt.getModelManager().getModel(modelName));
        }

        evt.getModelRegistry().put(new ModelResourceLocation(
                Kitchenkarrot.MOD_ID,
                "cocktail",
                "inventory"
        ), new CocktailBakedModel());
    }

}