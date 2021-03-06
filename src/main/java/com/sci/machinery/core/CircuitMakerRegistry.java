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
	public boolean isValidRecipe(ItemStack[] recipe)
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

	private boolean recipesEqual(IRecipe a, IRecipe b)
	{
		if(a.getIngredients().length != b.getIngredients().length)
			return false;

		if(!ItemStack.areItemStacksEqual(a.getResult(), b.getResult()))
			return false;

		return recipesEqual(a.getIngredients(), b);
	}

	private boolean recipesEqual(ItemStack[] a, IRecipe b)
	{
		for(int i = 0; i < a.length; i++)
		{
			if(a[i] == null && b.getIngredient(i) == null)
				continue;
			if((a[i] == null && b.getIngredient(i) != null) || (a[i] != null && b.getIngredient(i) == null))
				return false;

			ItemStack s1 = a[i].copy();
			ItemStack s2 = b.getIngredient(i).copy();
			s2.stackSize = s1.stackSize;

			if(!ItemStack.areItemStacksEqual(s1, s2))
				return false;
		}

		return true;
	}

	@Override
	public IRecipe getRecipe(ItemStack[] recipe)
	{
		for(CircuitMakerRecipe rRecipe : recipes)
		{
			if(recipesEqual(recipe, rRecipe))
				return rRecipe;
		}
		return null;
	}
}