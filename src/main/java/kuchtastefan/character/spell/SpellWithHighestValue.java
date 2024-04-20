package kuchtastefan.character.spell;

public class SpellWithHighestValue {

//    public Spell returnSpellWithTheHighestTotalDamage(GameCharacter gameCharacter, Class<Action> actionClass) {
//
//        Spell spellWithMaxDamage = gameCharacter.getCharacterSpellList().getFirst();
//
//        int totalValue = 0;
//        int maxTotalValue = 0;
//
//        for (Spell spell : gameCharacter.getCharacterSpellList()) {
//            if (spell.isCanSpellBeCasted()) {
//
//                // Calculate the total damage of the spell, including any damage over time effects
//                for (Action action : spell.getSpellActions()) {
//                    if (action.getClass().equals(actionClass)) {
//                        totalValue += action.returnTotalActionValue(spell.getBonusValueFromAbility(), gameCharacter);
//                    }
//
//                    if (action instanceof ActionDealDamageOverTime actionDealDamageOverTime) {
//                        int damageWithStacks = action.returnTotalActionValue(spell.getBonusValueFromAbility(), gameCharacter)
//                                * actionDealDamageOverTime.getActionMaxStacks();
//                        totalValue += damageWithStacks;
//                    }
//                }
//
//                // Update the spell with the highest total damage if necessary
//                if (totalValue > maxTotalValue) {
//                    maxTotalValue = totalValue;
//                    spellWithMaxDamage = spell;
//                }
//            }
//        }
//
//        return spellWithMaxDamage;
//    }

}
