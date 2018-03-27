package ai;

import eval.BonusEvaluator;
import model.AbstractState.MOVE;
import model.State;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NiklavsMeiers2048Average extends AbstractPlayer {

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
        double maxScore = 0;
        Node bestChild;
        double childScore;
        bestChild = children.get(0);
        for (Node child : children) {
            childScore = child.getAverage(4);
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

        public double getAverage(int depth) {
            BonusEvaluator be = new BonusEvaluator();
            double score = 1;
            if (depth > 0) {
                List<MOVE> moves = state.getMoves();
                int numberOfChildren = 0;
                for (MOVE move : moves) {
                    State tempState = state.copy();
                    tempState.move(move);
                    Node child = new Node(tempState, move);
                  //  for (int i = 0; i < 1; i++) {
                        score += child.getAverage(depth - 1);//scould calculate average for best 2 maybe
                   // }
                    numberOfChildren++;
                }
                if (numberOfChildren == 0) return 0;
                return score / numberOfChildren;
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
