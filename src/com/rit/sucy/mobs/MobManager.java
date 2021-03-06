/**
 * MCCore
 * com.rit.sucy.mobs.MobManager
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Steven Sucy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software") to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.rit.sucy.mobs;

import com.rit.sucy.text.TextFormatter;
import org.bukkit.entity.*;

import java.util.Hashtable;

/**
 * Contains tools for converting names of mobs
 */
public class MobManager
{

    /**
     * Gets the simple name of a mob
     *
     * @param entity entity to get the name of
     *
     * @return basic name
     */
    public static String getName(LivingEntity entity)
    {
        String name = entity.getType().getName();

        if (table.contains(name.toLowerCase()))
            return table.get(name.toLowerCase());

        if (entity instanceof Skeleton)
        {
            if (((Skeleton) entity).getSkeletonType() == Skeleton.SkeletonType.WITHER)
                name = "wither_" + name;
        }

        return TextFormatter.format(name);
    }

    /**
     * Gets the vanilla entity name along with various attributes such as:
     * <ul>
     * <li>Baby</li>
     * <li>Sheep Color</li>
     * <li>Villager Profession</li>
     * <li>Slime Size</li>
     * <li>Wolf/Ocelot tamed status</li>
     * <li>Zombie Type</li>
     * </ul>
     *
     * @param entity entity to get the name of
     *
     * @return detailed name
     */
    public static String getDetailedName(LivingEntity entity)
    {
        String basic = getName(entity);

        // Wolves and ocelots are described with whether or not they are tamed
        if (entity instanceof Tameable)
            basic = ((Tameable) entity).isTamed() ? "Tamed " + basic : "Wild " + basic;

            // Slimes are described with their size
        else if (entity instanceof Slime)
        {
            switch (((Slime) entity).getSize())
            {
                case 4:
                    basic = "Big " + basic;
                    break;
                case 2:
                    basic = "Small " + basic;
                    break;
                case 1:
                    basic = "Tiny " + basic;
                    break;
            }
        }

        // Sheep are described with their color
        else if (entity instanceof Sheep)
        {
            String color = ((Sheep) entity).getColor().name().toLowerCase();
            basic = TextFormatter.format(color) + " " + basic;
        }

        // Villagers are described with their profession
        else if (entity instanceof Villager)
        {
            String profession = ((Villager) entity).getProfession().name();
            basic = TextFormatter.format(profession) + " " + basic;
        }

        // Zombies are described with whether or not they are a villager zombie and if they are a baby
        else if (entity instanceof Zombie)
        {
            String extra = ((Zombie) entity).isBaby() ? "Baby " : "";
            extra += ((Zombie) entity).isVillager() ? "Villager " : "";
            basic = extra + basic;
        }

        // Animals are described with whether or not they are a baby
        if (entity instanceof Ageable)
        {
            basic = ((Ageable) entity).isAdult() ? basic : "Baby " + basic;
        }

        return basic;
    }

    private static final Hashtable<String, String> table = new Hashtable<String, String>()
    {{
            put("mushroom_cow", "Mooshroom");
            put("pig_zombie", "Zombie Pigman");
            put("snowman", "Snow Golem");
        }};
}
