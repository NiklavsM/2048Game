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
            childScore = child.getAverage(2);
            if (maxScore <= childScore) {
                maxScore = childScore;
                bestChild = child;
            }

        }
        if(bestChild==null){
            System.out.println("here");
        }

//        for (Node child : children) {
//            childScore = child.getAverage(3);
//            if (maxScore <= childScore) {
//                maxScore = childScore;
//                bestChild = child;
//            }
//
//        }

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
            double score = 0;
            if(depth > 0) {
                List<Node> children = new LinkedList<>();
                List<MOVE> moves = state.getMoves();
                for (MOVE move : moves) {
                    State tempState = state.copy();
                    tempState.move(move);
                    children.add(new Node(tempState, move));
                }
                for(Node child : children){
                    score += child.getAverage(depth-1);
                }
                if(children.isEmpty()){
                    be.evaluate(state);
                }
                return score/children.size();
            }else{

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
