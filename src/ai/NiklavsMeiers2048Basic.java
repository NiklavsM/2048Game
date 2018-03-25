package ai;

import java.util.List;
import java.util.Random;

import eval.BonusEvaluator;
import eval.FastEvaluator;
import eval.ScoreEvaluator;
import model.AbstractState.MOVE;
import model.State;

public class NiklavsMeiers2048Basic extends AbstractPlayer {

    private Random rng = new Random();

    @Override
    public MOVE getMove(State game) {
        System.out.println(game);
        double highScore = 0;
        double currentScore;
        MOVE bestMove = null;
        // Delay for the view
        pause();
        // Get available moves
        List<MOVE> moves = game.getMoves();
        BonusEvaluator be = new BonusEvaluator();
        for(MOVE move : moves){
            State tempState = game.copy();
            tempState.move(move);
            currentScore = be.evaluate(tempState);
            if(currentScore > highScore){
                highScore = currentScore;
                bestMove = move;
            }
        }

        return bestMove;
    }

    @Override
    public int studentID() {
        return 201546210;
    }

    @Override
    public String studentName() {
        return "Niklavs Meiers";
    }
}
