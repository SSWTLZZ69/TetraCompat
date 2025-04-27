package com.sswltzz.tetracompat.integration;

import com.google.gson.JsonObject;
import com.sswltzz.tetracompat.TetraCompat;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import se.mickelus.tetracelium.TetraceliumMod;
import se.mickelus.tetracelium.compat.farmersdelight.FarmersDelightToolActions;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.common.crafting.ingredient.ToolActionIngredient;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.registry.ModSounds;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class FarmersDelightCompat {
    public static void injectRecipes(Map<ResourceLocation, Recipe<?>> recipes, BiConsumer<ResourceLocation, Recipe<?>> add) {

        Map<ResourceLocation, CuttingBoardRecipe> cuttingRecipes = recipes.entrySet().stream()
                .filter(resourceLocationRecipeEntry -> resourceLocationRecipeEntry.getValue() instanceof CuttingBoardRecipe)
                .filter(resourceLocationRecipeEntry -> !resourceLocationRecipeEntry.getKey().getNamespace().equals(FarmersDelight.MODID))
                .filter(resourceLocationRecipeEntry -> !resourceLocationRecipeEntry.getKey().getNamespace().equals(TetraceliumMod.MOD_ID))
                .filter(resourceLocationRecipeEntry -> ((JsonObject) ((CuttingBoardRecipe) resourceLocationRecipeEntry.getValue()).getTool().toJson()).get("tag").getAsString().equals("forge:tools/knives"))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (CuttingBoardRecipe) entry.getValue()
                ));
        cuttingRecipes.forEach((originalID, recipe) -> {
            var id = new ResourceLocation(TetraCompat.MODID, String.format("cutting/tetracelium_%s", originalID.getPath()));
            NonNullList<ChanceResult> chanceResults = recipe.getRollableResults();
            Ingredient ingredient = Ingredient.merge(recipe.getIngredients());
            CuttingBoardRecipe cuttingBoardRecipe = new CuttingBoardRecipe(id, "", ingredient, new ToolActionIngredient(FarmersDelightToolActions.bladeCut), chanceResults, ModSounds.BLOCK_CUTTING_BOARD_KNIFE.getId().toString());
            add.accept(id, cuttingBoardRecipe);
        });
    }
}
