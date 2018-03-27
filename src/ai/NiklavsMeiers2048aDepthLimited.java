package ai;

import eval.BonusEvaluator;
import model.AbstractState.MOVE;
import model.State;

import java.util.*;

public class NiklavsMeiers2048aDepthLimited extends AbstractPlayer {

    private Random rng = new Random();

    @Override
    public MOVE getMove(State game) {
        int iterations = 100;
        List<Node> children = new LinkedList<>();
        Map<MOVE, Double> moveScores = new HashMap<>();
        // Delay for the view
        pause();
        // Get available moves
        List<MOVE> moves = game.getMoves();
        for (int i = 0; i < iterations; i++) {
            for (MOVE move : moves) {
                State tempState = game.copy();
                tempState.move(move);
                Node child = new Node(tempState, move);
                if (moveScores.containsKey(move)) {
                    moveScores.put(move, (moveScores.get(move) + child.getScore(10)));
                } else {
                    moveScores.put(move, child.getScore(10));
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

        public double rollout(State state, int depth) {
            BonusEvaluator be = new BonusEvaluator();
            Random rng = new Random();
            State stateToRoll = state.copy();
            for (int i = 0; i < depth; i++) {
                List<MOVE> moves = stateToRoll.getMoves();
                // Pick a move at random
                if (moves.isEmpty()) {
                    return be.evaluate(stateToRoll);
                }
                stateToRoll.move(moves.get(rng.nextInt(moves.size())));
            }


            return be.evaluate(stateToRoll);
        }

        public double getScore(int depth) {
                double bestScore = 0;

                for (MOVE move : state.getMoves()) {
                    State tempState = state.copy();
                    double childScore;
                    tempState.move(move);
                    Node child = new Node(tempState, move);
                    childScore = child.rollout(tempState, depth);//scould calculate average for best 2 maybe
                    if (bestScore < childScore) {
                        bestScore = childScore;
                    }
                }
                return bestScore;
            }

//        public double getAverage(int depth) {
//            BonusEvaluator be = new BonusEvaluator();
//            double score = 1;
//            if (depth > 0) {
//                List<MOVE> moves = state.getMoves();
//                int numberOfChildren = 0;
//                for (MOVE move : moves) {
//                    State tempState = state.copy();
//                    tempState.move(move);
//                    Node child = new Node(tempState, move);
//                    //  for (int i = 0; i < 1; i++) {
//                    score += child.getAverage(depth - 1);//scould calculate average for best 2 maybe
//                    // }
//                    numberOfChildren++;
//                }
//                if (numberOfChildren == 0) return 0;
//                return score / numberOfChildren;
//            } else {
//                return be.evaluate(state);
//            }
//        }
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
