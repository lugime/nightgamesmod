package nightgames.global;

import nightgames.characters.Trait;
import nightgames.characters.body.BodyPart;
import nightgames.characters.body.StraponPart;

import java.util.HashMap;

public class Formatter {
    public static void buildParser() {
        Global.matchActions = new HashMap<String, Match.MatchAction>();
        Global.matchActions.put("possessive", (self, first, second, third) -> {
            if (self != null) {
                return self.possessiveAdjective();
            }
            return "";
        });
        Global.matchActions.put("name-possessive", (self, first, second, third) -> {
            if (self != null) {
                return self.nameOrPossessivePronoun();
            }
            return "";
        });
        Global.matchActions.put("name", (self, first, second, third) -> {
            if (self != null) {
                return self.getName();
            }
            return "";
        });
        Global.matchActions.put("subject-action", (self, first, second, third) -> {
            if (self != null && third != null) {
                String verbs[] = third.split("\\|");
                if (verbs.length > 1) {
                    return self.subjectAction(verbs[0], verbs[1]);
                } else {
                    return self.subjectAction(verbs[0]);
                }
            }
            return "";
        });
        Global.matchActions.put("pronoun-action", (self, first, second, third) -> {
            if (self != null && third != null) {
                String verbs[] = third.split("\\|");
                if (verbs.length > 1) {
                    return self.pronoun() + " " + self.action(verbs[0], verbs[1]);
                } else {
                    return self.pronoun() + " " + self.action(verbs[0]);
                }
            }
            return "";
        });
        Global.matchActions.put("action", (self, first, second, third) -> {
            if (self != null && third != null) {
                String verbs[] = third.split("\\|");
                if (verbs.length > 1) {
                    return self.action(verbs[0], verbs[1]);
                } else {
                    return self.action(verbs[0]);
                }
            }
            return "";
        });
        Global.matchActions.put("if-female", (self, first, second, third) -> {
            if (self != null && third != null) {
                return self.useFemalePronouns() ? third : "";
            }
            return "";
        });
        Global.matchActions.put("if-male", (self, first, second, third) -> {
            if (self != null && third != null) {
                return self.useFemalePronouns() ? "" : third;
            }
            return "";
        });
        Global.matchActions.put("if-human", (self, first, second, third) -> {
            if (self != null && third != null) {
                return self.human() ? third : "";
            }
            return "";
        });

        Global.matchActions.put("if-nonhuman", (self, first, second, third) -> {
            if (self != null && third != null) {
                return !self.human() ? third : "";
            }
            return "";
        });
        Global.matchActions.put("subject", (self, first, second, third) -> {
            if (self != null) {
                return self.subject();
            }
            return "";
        });
        Global.matchActions.put("direct-object", (self, first, second, third) -> {
            if (self != null) {
                return self.directObject();
            }
            return "";
        });
        Global.matchActions.put("name-do", (self, first, second, third) -> {
            if (self != null) {
                return self.nameDirectObject();
            }
            return "";
        });
        Global.matchActions.put("body-part", (self, first, second, third) -> {
            if (self != null && third != null) {
                BodyPart part = self.body.getRandom(third);
                if (part == null && third.equals("cock") && self.has(Trait.strapped)) {
                    part = StraponPart.generic;
                }
                if (part != null) {
                    return part.describe(self);
                }
            }
            return "";
        });
        Global.matchActions.put("pronoun", (self, first, second, third) -> {
            if (self != null) {
                return self.pronoun();
            }
            return "";
        });
        Global.matchActions.put("reflective", (self, first, second, third) -> {
            if (self != null) {
                return self.reflectivePronoun();
            }
            return "";
        });

        Global.matchActions.put("main-genitals", (self, first, second, third) -> {
            if (self != null) {
                if (self.hasDick()) {
                    return "dick";
                } else if (self.hasPussy()) {
                    return "pussy";
                } else {
                    return "crotch";
                }
            }
            return "";
        });

        Global.matchActions.put("balls-vulva", (self, first, second, third) -> {
            if (self != null) {
                if (self.hasBalls()) {
                    return "testicles";
                } else if (self.hasPussy()) {
                    return "vulva";
                } else {
                    return "crotch";
                }
            }
            return "";
        });

        Global.matchActions.put("master", (self, first, second, third) -> {
            if (self.useFemalePronouns()) {
                return "mistress";
            } else {
                return "master";
            }
        });

        Global.matchActions.put("mister", (self, first, second, third) -> {
            if (self.useFemalePronouns()) {
                return "miss";
            } else {
                return "mister";
            }
        });

        Global.matchActions.put("true-name", (self, first, second, third) -> {
            return self.getTrueName();
        });

        Global.matchActions.put("girl", (self, first, second, third) -> {
            return self.guyOrGirl();
        });
        Global.matchActions.put("guy", (self, first, second, third) -> {
            return self.guyOrGirl();
        });
        Global.matchActions.put("man", (self, first, second, third) -> {
            return self.useFemalePronouns() ? "woman" : "man";
        });
        Global.matchActions.put("boy", (self, first, second, third) -> {
            return self.boyOrGirl();
        });
        Global.matchActions.put("poss-pronoun", (self, first, second, third) -> {
            if (self != null) {
                return self.possessivePronoun();
            }
            return "";
        });
    }
}
