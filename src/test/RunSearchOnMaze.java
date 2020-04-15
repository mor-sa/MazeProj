package test;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import java.util.ArrayList;
import java.util.List;

public class RunSearchOnMaze {
    public static void main(String[] args) {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(30, 30);
//        for(int i = 0; i< maze.getRowsNum(); i++){
//            for(int j = 0; j< maze.getColsNum(); j++){
//                if ((i == maze.getGoalPosition().getRowIndex()) || j == maze.getGoalPosition().getColumnIndex()){
//                    maze.setValue(new Position(i,j),1);
//                }
//            }
//        }
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        solveProblem(searchableMaze, new BreadthFirstSearch());
        solveProblem(searchableMaze, new DepthFirstSearch());
        solveProblem(searchableMaze, new BestFirstSearch());
    }
    private static void solveProblem(ISearchable domain, ISearchingAlgorithm
            searcher) {
        //Solve a searching problem with a searcher
        Solution solution = searcher.solve(domain);
        ArrayList<Position> path = new ArrayList<>();
        path.clear();
        for (AState state : solution.getSolutionPath()){
            MazeState cur = new MazeState(state.getPrev(), state.getState_str(), state.getCost());
            path.add(cur.getPos());
        }

        domain.printColorSolution(path);


        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
                //Printing Solution Path
                System.out.println("Solution path:");
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s.%s",i,solutionPath.get(i)));
        }

    }
}