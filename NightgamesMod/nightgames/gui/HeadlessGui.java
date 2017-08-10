package nightgames.gui;

import nightgames.global.GameState;

import java.util.concurrent.CompletableFuture;

public class HeadlessGui extends GUI {
    private static final long serialVersionUID = 1L;

    public HeadlessGui(CompletableFuture<GameState> stateFuture) {
        super(stateFuture);
        this.setVisible(false);
    }
    
    @Override
    public void message(String msg) {
        
    }
    
}
