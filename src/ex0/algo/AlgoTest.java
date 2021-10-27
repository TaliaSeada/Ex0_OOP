package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AlgoTest {
    Building b1;
    Building b9;
    Algo algo;
    CallForElevator call;
    Elevator elev;

    public AlgoTest(){
        Simulator_A.initData(1,null);
        b1 = Simulator_A.getBuilding();
        Simulator_A.initData(9,null);
        b9 = Simulator_A.getBuilding();
        algo = new Algo(b9);
        elev = b9.getElevetor(0);
        call = new CallForElevator() {
            @Override
            public int getState() {
                return 0;
            }

            @Override
            public double getTime(int state) {
                return 0;
            }

            @Override
            public int getSrc() {
                return 35;
            }

            @Override
            public int getDest() {
                return 55;
            }

            @Override
            public int getType() {
                return CallForElevator.UP;
            }

            @Override
            public int allocatedTo() {
                return 0;
            }
        };

    }

    @Test
    void allocateAnElevator() {
        //This function gets a call and return the number of the elevator that was
        // allocated to take this call.

        //At the start of the process, all the elevators are empty
        //and elevator 3 is the fastest so this is why this is the expected outcome
        int elev = algo.allocateAnElevator(call);
        assertEquals(3,elev);
    }

    @Test
    void insert() {
        /* this function was built in order to insert the calls more efficiently
          by checking if the elevator goes up or down and inserting the source floor between
          two consecutive floors then insert the destination floor after the source
          by the same way the source floor was added */

        MyQueue expected = new MyQueue(); //[0,-3,-4,-5,-1,0,19,40,70,19]
        expected.add(0);  expected.add(-3);
        expected.add(-4); expected.add(-5);
        expected.add(-1); expected.add(0);
        expected.add(19); expected.add(40);
        expected.add(70); expected.add(19);
        /* In here we inserted the floors into the queue in the way they are supposed to
          be and then used our insert function to see if it inserts the floors correctly */
        int src1 = 0, src2 = -3, src3 = 70, src4 = 19, src5 = -1;
        int dest1 = -5, dest2 = -4, dest3 = 19, dest4 = 40, dest5 = 0;
        algo.insert(src1,dest1,0);
        algo.insert(src2,dest2,0);
        algo.insert( src3,dest3,0);
        algo.insert(src4,dest4,0);
        algo.insert(src5,dest5,0);

        //then compare between the results using the toString function we built
        assertEquals(expected.toString(),algo.getCalls()[0].toString());
    }

    @Test
    void timeToFloor() {
        /* This function get a floor and an elevator,and calculates the amount of time it will take the
          elevator to get to the floor, including all the floors that are currently in the
          elevator's queue */
        int src1 = 0, src2 = -3, src3 = 70;
        int dest1 = -5, dest2 = -4, dest3 = 19;
        algo.insert(src1,dest1,0);
        algo.insert(src2,dest2,0);
        algo.insert( src3,dest3,0);






    }

    @Test
    void cmdElevator() {

    }
}