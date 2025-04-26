package com.sswltzz.tetracompat.integration;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.sswltzz.tetracompat.TCConfig;
import com.sswltzz.tetracompat.mixin.RecipeManagerAccessor;
import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import se.mickelus.tetracelium.TetraceliumMod;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.Map;

public class RecipeInjection {

    public static void injectRecipes(RecipeManagerAccessor manager) {
        // 创建 byNameBuilder，添加现有的 recipes
        ImmutableMap.Builder<ResourceLocation, Recipe<?>> byNameBuilder = ImmutableMap.builder();
        byNameBuilder.putAll(manager.getByName());

        // 创建 recipesBuilder，用于分类 RecipeType
        Map<RecipeType<?>, ImmutableMap.Builder<ResourceLocation, Recipe<?>>> recipesBuilder = Maps.newHashMap();


        // 注入新的配方

        if (ModList.get().isLoaded(FarmersDelight.MODID) && ModList.get().isLoaded(TetraceliumMod.MOD_ID) && TCConfig.tetraceliumKnifeCompat)
            FarmersDelightCompat.injectRecipes(manager.getByName(), byNameBuilder::put);

        // 构建新 byName
        ImmutableMap<ResourceLocation, Recipe<?>> newByName = byNameBuilder.build();

        // 根据 RecipeType 分类并构建新的结构
        newByName.forEach((id, recipe) -> {
            RecipeType<?> type = recipe.getType();
            recipesBuilder.computeIfAbsent(type, k -> ImmutableMap.builder()).put(id, recipe);
        });

        // 更新 manager 的 byName 和 recipes
        manager.setByName(newByName);
        manager.setRecipes(Maps.transformValues(recipesBuilder, ImmutableMap.Builder::build));
    }
}
