package aoichan.crystal.engine.forge;

import aoichan.crystal.api.forge.ForgeResult;
import aoichan.crystal.engine.roll.ForgeRoll;
import aoichan.crystal.infrastructure.nbt.GemNBT;
import org.bukkit.inventory.ItemStack;

public class ForgeEngine {

    // [!] Code: Execute forge
    public ForgeResult forge(ItemStack item, ItemStack gem, double successRate) {

        boolean success = ForgeRoll.roll(successRate);

        if (success) {

            applyGem(item, gem);

            return ForgeResult.SUCCESS;
        }

        return ForgeResult.FAIL;
    }

    // [!] Code: Apply gem to item
    private void applyGem(ItemStack item, ItemStack gem) {

        String gemId = GemNBT.get(gem);

        if (gemId == null) return;

        // NOTE: Phase sau sẽ lưu gem vào NBT item
    }
} 
