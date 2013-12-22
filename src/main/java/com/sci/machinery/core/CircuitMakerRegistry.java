package com.sci.machinery.core;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import com.sci.machinery.api.IRecipe;
import com.sci.machinery.api.IRecipeRegistry;

public class CircuitMakerRegistry implements IRecipeRegistry
{
	private List<CircuitMakerRecipe> recipes;

	public CircuitMakerRegistry()
	{
		recipes = new ArrayList<CircuitMakerRecipe>();
	}

	@Override
	public boolean isValidRecipe(ItemStack[][] recipe)
	{
		boolean contains = false;
		for(CircuitMakerRecipe rRecipe : recipes)
		{
			if(recipesEqual(recipe, rRecipe))
				contains = true;
		}
		return contains;
	}

	@Override
	public void registerRecipe(IRecipe recipe)
	{
		if(!(recipe instanceof CircuitMakerRecipe))
			throw new IllegalArgumentException("Recipe is not a circuit maker!");
		boolean contains = false;
		for(CircuitMakerRecipe rRecipe : recipes)
		{
			if(recipesEqual(recipe, rRecipe))
				contains = true;
		}
		if(contains)
			throw new IllegalArgumentException("Recipe already registered!");
		recipes.add((CircuitMakerRecipe) recipe);
	}

	@Override
	public ItemStack getRecipeResult(ItemStack[][] recipe)
	{
		IRecipe found = null;
		for(CircuitMakerRecipe rRecipe : recipes)
		{
			if(recipesEqual(recipe, rRecipe))
				found = rRecipe;
		}
		if(found == null)
			return null;
		return found.getResult();
	}

	private boolean recipesEqual(IRecipe a, IRecipe b)
	{
		if(a.getWidth() != b.getWidth())
			return false;
		if(a.getHeight() != b.getHeight())
			return false;

		if(!a.getResult().equals(b.getResult()))
			return false;

		for(int x = 0; x < a.getWidth(); x++)
		{
			for(int y = 0; y < a.getHeight(); y++)
			{
				if(!a.getIngredient(x, y).equals(b.getIngredient(x, y))) { return false; }
			}
		}

		return true;
	}

	private boolean recipesEqual(ItemStack[][] a, IRecipe b)
	{
		for(int x = 0; x < a.length; x++)
		{
			for(int y = 0; y < a[x].length; y++)
			{
				if(b.getIngredient(x, y) == null && a[x][y] == null)
					continue;
				if((b.getIngredient(x, y) == null && a[x][y] != null) || (b.getIngredient(x, y) != null && a[x][y] == null)) { return false; }
				if(b.getIngredient(x, y).itemID != a[x][y].itemID) { return false; }
			}
		}

		return true;
	}

	@Override
	public IRecipe getRecipe(ItemStack[][] recipe)
	{
		for(CircuitMakerRecipe rRecipe : recipes)
		{
			if(recipesEqual(recipe, rRecipe))
				return rRecipe;
		}
		return null;
	}
}