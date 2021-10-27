package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.*;

public class Algo implements ElevatorAlgo {
    public static final int UP = 1, DOWN = -1, LEVEL = 0;
    private int _direction; //UP,DOWN,LEVEL
    private Building _building;
    //checks which elevators are resting and which are not, so we can use them
    private boolean[] resting;
    //array that holds all the calls in a queue for all the elevators
    private MyQueue[] calls;

    public MyQueue[] getCalls(){
        return this.calls;
    }


    //Constructor
    public Algo(Building b) {
        this._building = b;
        //all elevators start in the entrance floors
        this._direction = UP;
        resting = new boolean[this._building.numberOfElevetors()];
        // all elevators are resting at the beginning
        for (int i = 0; i < this._building.numberOfElevetors(); i++) {
            resting[i] = true;
        }
        //set all queues in the array
        this.calls = new MyQueue[this._building.numberOfElevetors()];
        for (int i = 0; i < this._building.numberOfElevetors(); i++) {
            this.calls[i] = new MyQueue();
        }
    }

    @Override
    public Building getBuilding() {
        return this._building;
    }

    @Override
    public String algoName() {
        return "On the way to being The best!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! algo";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        double minTime = Double.MAX_VALUE;
        double currentTime = 0;
        int elevNum = -1;
        int numOfElev = this._building.numberOfElevetors();
        //one elevator
        if (numOfElev == 1) {
            insert(c.getSrc(), c.getDest(), 0);
            resting[0] = false;
            return 0;
        }
        //more than one elevator
        else {
            // check if there is a resting elevator,if there is, we send it
            for (int i = 0; i < numOfElev; i++) {
                if (resting[i]) {
                    currentTime = timeToFloor(c.getSrc(), i);
                    if (currentTime < minTime) {
                        elevNum = i;
                        minTime = currentTime;
                    }

                }
            }
            if (elevNum != -1) {
                resting[elevNum] = false;
                insert(c.getSrc(), c.getDest(), elevNum);
                return elevNum;
            }
            // there are no resting elevators
            for (int i = 0; i < numOfElev; i++) {
                currentTime = timeToFloor(c.getSrc(), i);
                if (currentTime < minTime) {
                    minTime = currentTime;
                    elevNum = i;
                }
            }
        }
        insert(c.getSrc(), c.getDest(), elevNum);
        return elevNum;
    }
    private int maxValIndex(MyQueue q) {
        int max = Integer.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < q.size(); i++) {
            if (q.get(i) > max) {
                max = q.get(i);
                index = i;
            }
        }
        return index;
    }
    /* this function gets a source and destination floors, and number of an elevator
     this function inserts the source and destination in the middle between floors
     are in the same direction */
    public void insert(int src, int dest, int elev) {
        int index = -1;
        //if call queue is empty
        if (this.calls[elev].size() == 0) {
            this.calls[elev].add(src);
            this.calls[elev].add(dest);
            return;
        } else {
            //src
            for (int i = 0; i < this.calls[elev].size() - 1; i++) {
                if (src > dest) { //down
                    if (this.calls[elev].get(i) >= src && this.calls[elev].get(i + 1) <= src) {
                        this.calls[elev].addAt(src, i + 1);
                        index = i + 1;
                        break;
                    }
                }
                if (src < dest) { //up
                    if (this.calls[elev].get(i) <= src && this.calls[elev].get(i + 1) >= src) {
                        this.calls[elev].addAt(src, i + 1);
                        index = i + 1;
                        break;
                    }
                }
            }
            //if src is bigger than all
            if (index == -1) {
                this.calls[elev].add(src);
                this.calls[elev].add(dest);
                return;
            }


            //dest
            for (int i = index; i < this.calls[elev].size() - 1; i++) {
                if (src > dest) { //down
                    if (this.calls[elev].get(i) >= dest && this.calls[elev].get(i + 1) <= dest) {
                        this.calls[elev].addAt(dest, i + 1);
                        return;
                    }
                }
                if (src < dest) { //up
                    if (this.calls[elev].get(i) <= dest && this.calls[elev].get(i + 1) >= dest) {
                        this.calls[elev].addAt(dest, i + 1);
                        return;
                    }
                }
            }
            //need to add dest at the end of the queue
            this.calls[elev].add(dest);

        }
    }

    private double timeToFloor(int src, int elev) {
        double ans = -1;
        Elevator thisElev = this._building.getElevetor(elev);
        int pos = thisElev.getPos();
        double speed = thisElev.getSpeed();
        int direction = thisElev.getState();
//        int direction = thisElev.getState();
        double numberOfFloors = Math.abs(pos - src) / speed;
        double floorTime = thisElev.getStopTime() + thisElev.getStartTime() + thisElev.getTimeForOpen() + thisElev.getTimeForClose();
        LinkedList<Integer> floors = new LinkedList<Integer>();
        double numOfStops = 0;
        //if the elevator is resting the number of stops will still be 0
        for (int i = 0; i < this.calls[elev].size(); i++) {
            //going up
            if (direction == 1) {// add only stops on the way, won't count stops under the elevator
                if (this.calls[elev].get(i) > pos) {
                    floors.add(this.calls[elev].get(i));
                }
            }
            //going down
            else if (direction == -1) {
                if (this.calls[elev].get(i) < pos) {
                    floors.add(this.calls[elev].get(i));
                }
            }
        }
        floors.add(src);
        numOfStops = floors.size();
        double time = numberOfFloors + (floorTime * numOfStops);
        return time;
    }

    @Override
    public void cmdElevator(int elev) {
        this.calls[elev].removeDuplicateAfter();
        Elevator elevator = this._building.getElevetor(elev);
        if (this.calls[elev].size() > 0) {
            if (this._building.getElevetor(elev).getPos() == this.calls[elev].peek()) {
                this.calls[elev].poll();
                if (this.calls[elev].size() != 0) {
                    elevator.goTo(this.calls[elev].peek());
                }
            }
            else {
                elevator.goTo(this.calls[elev].peek());
            }
        }
        else {
            this.resting[elev] = true;
        }
    }


}