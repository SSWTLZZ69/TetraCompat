package com.sswltzz.tetracompat.integration;

import net.minecraft.world.item.Item;
import thedarkcolour.exdeorum.recipe.RecipeUtil;
import thedarkcolour.exdeorum.recipe.hammer.HammerRecipe;

public class ExDeorumUtil {
    public static HammerRecipe getHammerRecipe(Item item) {
        try {
            Class.forName("thedarkcolour.exdeorum.recipe.RecipeUtil");
            return RecipeUtil.getHammerRecipe(item);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
