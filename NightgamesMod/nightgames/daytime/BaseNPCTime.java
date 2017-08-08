package nightgames.daytime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import nightgames.characters.Character;
import nightgames.characters.NPC;
import nightgames.characters.Trait;
import nightgames.global.Flag;
import nightgames.global.Formatter;
import nightgames.gui.GUI;
import nightgames.items.Item;
import nightgames.items.Loot;
import nightgames.items.clothing.Clothing;
import nightgames.requirements.RequirementWithDescription;

public abstract class BaseNPCTime extends Activity {
    protected NPC npc;
    String knownFlag = "";
    String noRequestedItems = "{self:SUBJECT} frowns when {self:pronoun} sees that you don't have the requested items.";
    String notEnoughMoney = "{self:SUBJECT} frowns when {self:pronoun} sees that you don't have the money required.";
    String giftedString = "\"Awww thanks!\"";
    String giftString = "\"A present? You shouldn't have!\"";
    String transformationOptionString = "Transformations";
    String loveIntro = "[Placeholder]<br/>LoveIntro";
    String transformationIntro = "[Placeholder]<br/>TransformationIntro";
    String transformationFlag = "";
    Trait advTrait = null;

    public BaseNPCTime(Character player, NPC npc) {
        super(npc.getTrueName(), player);
        this.npc = npc;
        buildTransformationPool();
    }

    @Override
    public boolean known() {
        return knownFlag.isEmpty() || Flag.checkFlag(knownFlag);
    }

    List<TransformationOption> options;

    public abstract void buildTransformationPool();

    public List<Loot> getGiftables() {
        List<Loot> giftables = new ArrayList<>();
        player.closet.stream().filter(article -> !npc.has(article)).forEach(article -> giftables.add(article));
        return giftables;
    }

    public abstract void subVisit(String choice);

    public abstract void subVisitIntro(String choice);

    public Optional<String> getAddictionOption() {
        return Optional.empty();
    }
    
    private String formatRequirementString(String description, boolean meets) {
        if (meets) {
            return String.format("<font color='rgb(90,210,100)'>%s</font>", description);
        } else {
            return String.format("<font color='rgb(210,90,90)'>%s</font>", description);
        }
    }
    @Override
    public void visit(String choice) {
        GUI.gui.clearText();
        GUI.gui.clearCommand();
        List<Loot> giftables = getGiftables();
        Optional<TransformationOption> optionalOption =
                        options.stream().filter(opt -> choice.equals(opt.option)).findFirst();
        Optional<Loot> optionalGiftOption = giftables.stream()
                        .filter(gift -> choice.equals(Formatter.capitalizeFirstLetter(gift.getName()))).findFirst();

        if (optionalOption.isPresent()) {
            TransformationOption option = optionalOption.get();
            boolean hasAll = option.ingredients.entrySet().stream()
                            .allMatch(entry -> player.has(entry.getKey(), entry.getValue()));
            int moneyCost = option.moneyCost.apply(this.player);
            if (!hasAll) {
                GUI.gui.message(Formatter.format(noRequestedItems, npc, player));
                choose("Back", GUI.gui);
            } else if (player.money < moneyCost) {
                GUI.gui.message(Formatter.format(notEnoughMoney, npc, player));
                choose("Back", GUI.gui);
            } else {
                GUI.gui.message(Formatter.format(option.scene, npc, player));
                option.ingredients.entrySet().stream().forEach(entry -> player.consume(entry.getKey(), entry.getValue(), false));
                option.effect.execute(null, player, npc);
                if (moneyCost > 0) {
                    player.modMoney(- moneyCost);
                }
                choose("Leave", GUI.gui);
            }
        } else if (optionalGiftOption.isPresent()) {
            GUI.gui.message(Formatter.format(giftedString, npc, player));
            if (optionalGiftOption.get() instanceof Clothing) {
                if (player.closet.contains(optionalGiftOption.get())) {
                    player.closet.remove(optionalGiftOption.get());
                }
                npc.closet.add((Clothing) optionalGiftOption.get());
            }
            player.gainAffection(npc, 2);
            npc.gainAffection(player, 2);
            choose("Back", GUI.gui);
        } else if (choice.equals("Gift")) {
            GUI.gui.message(Formatter.format(giftString, npc, player));
            giftables.stream().forEach(loot -> choose(Formatter.capitalizeFirstLetter(loot.getName()), GUI.gui));
            choose("Back", GUI.gui);
        } else if (choice.equals("Change Outfit")) {
            GUI.gui.changeClothes(npc, this, "Back");
        } else if (choice.equals(transformationOptionString)) {
            GUI.gui.message(Formatter.format(transformationIntro, npc, player));
            if (!transformationFlag.equals("")) {
                Flag.flag(transformationFlag);
            }
            options.stream()
                   .forEach(opt -> {
                boolean allowed = true;
                       GUI.gui.message(opt.option + ":");
                for (Map.Entry<Item, Integer> entry : opt.ingredients.entrySet()) {
                    String message = entry.getValue() + " " + entry.getKey().getName();
                    boolean meets = player.has(entry.getKey(), entry.getValue());
                    GUI.gui.message(formatRequirementString(message, meets));
                    allowed &= meets;
                }
                for (RequirementWithDescription req : opt.requirements) {
                    boolean meets = req.getRequirement().meets(null, player, npc);
                    GUI.gui.message(formatRequirementString(req.getDescription(), meets));
                    allowed &= meets;
                }
                int moneyCost = opt.moneyCost.apply(this.player);
                if (moneyCost > 0) {
                    boolean meets = player.money >= moneyCost;
                    GUI.gui.message(formatRequirementString(moneyCost + "$", meets));
                    allowed &= meets;
                }
                if (allowed) {
                    choose(opt.option, GUI.gui);
                }
                       GUI.gui.message("<br/>");
            });
            choose("Back", GUI.gui);
        } else if (choice.equals("Start") || choice.equals("Back")) {
            if (npc.getAffection(player) > 25 && (advTrait == null || npc.has(advTrait))) {
                GUI.gui.message(Formatter.format(loveIntro, npc, player));
                choose("Games", GUI.gui);
                choose("Sparring", GUI.gui);
                choose("Sex", GUI.gui);
                if (!options.isEmpty()) {
                    choose(transformationOptionString, GUI.gui);
                }
                if (npc.getAffection(player) > 30) {
                    choose("Gift", GUI.gui);
                }
                if (npc.getAffection(player) > 35) {
                    choose("Change Outfit", GUI.gui);
                }
                Optional<String> addictionOpt = getAddictionOption();
                if (addictionOpt.isPresent()) {
                    choose(addictionOpt.get(), GUI.gui);
                }
                choose("Leave", GUI.gui);
            } else {
                subVisitIntro(choice);
            }
        } else if (choice.equals("Leave")) {
            done(true);
        } else {
            subVisit(choice);
        }
    }

    @Override
    public void shop(Character paramCharacter, int paramInt) {
        paramCharacter.gainAffection(npc, 1);
        npc.gainAffection(paramCharacter, 1);

    }

}
