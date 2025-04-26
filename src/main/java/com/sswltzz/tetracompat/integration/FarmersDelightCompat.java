package com.sswltzz.tetracompat.integration;

import com.sswltzz.tetracompat.TetraCompat;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import se.mickelus.tetracelium.TetraceliumMod;
import se.mickelus.tetracelium.compat.farmersdelight.FarmersDelightToolActions;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.common.crafting.ingredient.ToolActionIngredient;
import vectorwing.farmersdelight.common.registry.ModSounds;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class FarmersDelightCompat {
    public static void injectRecipes(Map<ResourceLocation, Recipe<?>> recipes, BiConsumer<ResourceLocation, Recipe<?>> add) {

        var cuttingRecipes = recipes.entrySet().stream()
                .filter(resourceLocationRecipeEntry -> resourceLocationRecipeEntry.getValue() instanceof CuttingBoardRecipe)
                .filter(resourceLocationRecipeEntry -> !resourceLocationRecipeEntry.getKey().getNamespace().equals(FarmersDelight.MODID))
                .filter(resourceLocationRecipeEntry -> !resourceLocationRecipeEntry.getKey().getNamespace().equals(TetraceliumMod.MOD_ID))
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
