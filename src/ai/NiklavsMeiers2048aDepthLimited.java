package ai;

import eval.BonusEvaluator;
import model.AbstractState.MOVE;
import model.State;

import java.util.*;

public class NiklavsMeiers2048aDepthLimited extends AbstractPlayer {

    @Override
    public MOVE getMove(State game) {
        int iterations = 300;
        Map<MOVE, Double> moveScores = new HashMap<>();
        // Delay for the view
        pause();
        // Get available moves
        List<MOVE> moves = game.getMoves();
        for (int i = 0; i < iterations; i++) {
            for (MOVE move : moves) {
                State tempState = game.copy();
                tempState.move(move);
                Node child = new Node(tempState);
                if (moveScores.containsKey(move)) {
                    moveScores.put(move, (moveScores.get(move) + child.getScore()));
                } else {
                    moveScores.put(move, child.getScore());
                }
            }
        }
        MOVE bestMove = null;
        double bestScore = 0;
        for (MOVE move : moveScores.keySet()) {
            double moveScore = moveScores.get(move);
            if (bestScore < moveScore) {
                bestScore = moveScore;
                bestMove = move;
            }
        }

        return bestMove;
    }


    class Node {
        State state;

        public Node(State state) {
            this.state = state;
        }

        public State getState() {
            return state;
        }

        private double rollout(State state) {
            BonusEvaluator be = new BonusEvaluator();
            Random rng = new Random();
            State stateToRoll = state.copy();
            for (int i = 0; i < 10; i++) {
                List<MOVE> moves = stateToRoll.getMoves();
                if (moves.isEmpty()) {
                    return be.evaluate(stateToRoll);
                }
                stateToRoll.move(moves.get(rng.nextInt(moves.size())));
            }
            return be.evaluate(stateToRoll);
        }

        public double getScore() {
                double bestScore = 0;
                for (MOVE move : state.getMoves()) {
                    State tempState = state.copy();
                    tempState.move(move);
                    double childScore = rollout(tempState);
                    if (bestScore < childScore) {
                        bestScore = childScore;
                    }
                }
                return bestScore;// try average?
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
