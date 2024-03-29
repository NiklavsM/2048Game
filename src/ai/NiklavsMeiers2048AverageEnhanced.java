package ai;

import eval.BonusEvaluator;
import model.AbstractState.MOVE;
import model.State;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NiklavsMeiers2048AverageEnhanced extends AbstractPlayer {

    private Random rng = new Random();

    @Override
    public MOVE getMove(State game) {
        List<Node> children = new LinkedList<>();
        // Delay for the view
        pause();
        // Get available moves
        List<MOVE> moves = game.getMoves();
        for (MOVE move : moves) {
            State tempState = game.copy();
            tempState.move(move);
            children.add(new Node(tempState, move));
        }
        double maxScore = Double.MIN_VALUE;
        Node bestChild = children.get(0);
        double childScore;
        for (Node child : children) {
            childScore = child.getScore(3);
            if (maxScore <= childScore) {
                maxScore = childScore;
                bestChild = child;
            }

        }

        return bestChild.getMove();
    }


    class Node {
        State state;
        MOVE move;

        public Node(State state, MOVE move) {
            this.state = state;
            this.move = move;
        }

        public MOVE getMove() {
            return move;
        }

        public State getState() {
            return state;
        }

        public double getScore(int depth) {
            BonusEvaluator be = new BonusEvaluator();
            if (depth > 0) {
                double bestScore = 0;
                for (MOVE move : state.getMoves()) {
                    double childScore = 0;
                    for (int i = 0; i < 3; i++) {
                        State tempState = state.copy();
                        tempState.move(move);
                        Node child = new Node(tempState, move);
                        childScore += child.getScore(depth - 1);
                    }
                    if (bestScore < childScore) {
                        bestScore = childScore;
                    }
                }
                if (bestScore == 0) return 0;
                return bestScore;
            } else {
                return be.evaluate(state);
            }
        }
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
