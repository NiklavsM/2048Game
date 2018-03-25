package ai;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import eval.BonusEvaluator;
import eval.FastEvaluator;
import eval.ScoreEvaluator;
import model.AbstractState;
import model.AbstractState.MOVE;
import model.State;

import static java.lang.Math.log;
import static java.lang.StrictMath.sqrt;

public class NiklavsMeiers2048MCT extends AbstractPlayer {

    private Random rng = new Random();
    private List<Node> montecarloTree = new LinkedList<>();

    @Override
    public MOVE getMove(State game) {
        // Delay for the view
        pause();
        // Get available moves

        return MonteCarloTreeSearch(game);
    }

    public MOVE MonteCarloTreeSearch(State state) {
        Node rootNode = new Node(null, state, null);
        rootNode.generateChildren();

        while (rootNode.timesVisited < 10000) {
            double maxUCB1 = 0;
            Node nodeToGo = null;
            for (Node child : rootNode.getChildren()) {
                double childUcb1 = child.getUCB1();
                if (maxUCB1 < childUcb1) {
                    maxUCB1 = childUcb1;
                    nodeToGo = child;
                }
            }
            nodeToGo.diveIn();
        }
        double maxUCB1 = 0;
        MOVE bestMove = null;
        for (Node child : rootNode.getChildren()) {
            double childUCB1 = child.getUCB1();
            if (maxUCB1 < childUCB1) {
                maxUCB1 = childUCB1;
                bestMove = child.getMove();
            }
        }
        return bestMove;
    }

    ;

    class Node {
        int timesVisited = 0;
        double value = 0;
        Node parentNode;
        State state;
        MOVE move;
        List<Node> children = new LinkedList<>();


        public Node(Node parentNode, State state, MOVE move) {
            this.parentNode = parentNode;
            this.state = state;
            this.move = move;
        }

        public void diveIn() {
            if (timesVisited == 0) {
                rollOut();
                updateParentValue();
            } else if (timesVisited == 1) {
                generateChildren();
                children.get(0).diveIn();
            } else {
                double maxUCB1 = 0;
                Node nodeToGo = null;
                for (Node child : children) {
                    double childUcb1 = child.getUCB1();
                    if (maxUCB1 < childUcb1) {
                        maxUCB1 = childUcb1;
                        nodeToGo = child;
                    }
                }
                nodeToGo.diveIn();
            }
        }

        public void rollOut() {
            int i = 0;
            BonusEvaluator be = new BonusEvaluator();
            State tempState = state.copy();
            while (i < 10) {
                i++;
                List<MOVE> moves = tempState.getMoves();
                if (moves.isEmpty()) {
                    break;
                }
//                double bestScore = 0;
//                MOVE bestMove = null;
//                for (MOVE move : moves) {
//                    State tempTempState = tempState.copy();
//                    tempTempState.move(move);
//                    double tempTempStateScore = be.evaluate(tempTempState);
//                    if (bestScore < tempTempStateScore) {
//                        bestScore = tempTempStateScore;
//                        bestMove = move;
//                    }
//                }
                tempState.move(moves.get(0));

            }

            value = be.evaluate(tempState);
        }

        public void updateParentValue() {
            if (parentNode != null) {
                parentNode.addToValue(value);
            }
        }

        private void addToValue(double childValue) {
            value += childValue;
            timesVisited++;
        }

        public double getUCB1() {
            if (timesVisited == 0) return Double.MAX_VALUE;
            return (value / timesVisited) + 2 * sqrt(log(parentNode.getTimesVisited()) / timesVisited);
        }

        public int getTimesVisited() {
            return timesVisited;
        }

        public void generateChildren() {
            List<MOVE> moves = state.getMoves();
            moves.forEach(move -> {
                State tempState = state.copy();
                tempState.move(move);
                children.add(new Node(this, tempState, move));
            });
        }

        public List<Node> getChildren() {
            return children;
        }

        public MOVE getMove() {
            return move;
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
