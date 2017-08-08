package nightgames.gui;

import nightgames.items.clothing.Clothing;

import javax.swing.*;

public class ClothingList extends JList<Clothing> {
    /**
     *
     */
    private static final long serialVersionUID = -4137559825944381962L;

    public ClothingList(DefaultListModel<Clothing> model) {
        super(model);
    }

    @Override
    public String getToolTipText(java.awt.event.MouseEvent event) {
        int location = locationToIndex(event.getPoint());
        Clothing article = getModel().getElementAt(location);
        return article.getDesc();
    }
}
